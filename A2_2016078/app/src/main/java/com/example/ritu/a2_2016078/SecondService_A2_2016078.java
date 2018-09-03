package com.example.ritu.a2_2016078;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SecondService_A2_2016078 extends Service {

    private String songurl = "http://faculty.iiitd.ac.in/~mukulika/s1.mp3";
    public SecondService_A2_2016078() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadSongTask().execute(songurl);
        } else {
            Intent broadcastIntent = new Intent("message");
            broadcastIntent.putExtra("DOWNLOAD","Failure");
            LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
        }
        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private class DownloadSongTask extends AsyncTask<String, String, File> {

        int progress = 0;
        Notification.Builder notificationBuilder;
        NotificationManager notificationManager;
        PendingIntent pendingIntent;
        int id = 1;
        String fileName;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Intent notificationIntent = new Intent(getApplicationContext(), FirstActivity_A2_2016078.class);
            pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);

            notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationBuilder = new Notification.Builder(getApplicationContext())
                    .setPriority(Notification.PRIORITY_MAX)
                    .setProgress(100, 0, false)
                    .setContentTitle("Downloading music")
                    .setSmallIcon(R.mipmap.ic_launcher  )
                    .setOngoing(true)
                    .setContentIntent(pendingIntent);
            notificationManager.notify(id, notificationBuilder.build());
        }

        @Override
        protected File doInBackground(String... urls) {
            int count = 0;
            File file;
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoOutput(true);
                connection.connect();

                String[] path = urls[0].split("/");
                String myfile = path[path.length - 1];
                int len = connection.getContentLength();
                fileName = myfile;

                file = new File(getFilesDir() , fileName);
                FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                InputStream inputStream = connection.getInputStream();
                notificationBuilder.setContentTitle("Downloading music - " + fileName);
                notificationManager.notify(id, notificationBuilder.build());

                byte data[] = new byte[1024];
                long total = 0;
                while ((count = inputStream.read(data)) != -1) {
                    total += count;
                    publishProgress("" + Integer.toString((int) (total * 100) / len));
                    outputStream.write(data, 0, count);
                }
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            } catch (Exception e) {
                return null;
            }
            return file;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            notificationBuilder.setContentText(""+values[0]+"%");
            progress = Integer.parseInt(values[0]);
            notificationBuilder.setProgress(100, progress, false);
            notificationManager.notify(id, notificationBuilder.build());
        }

        @Override
        protected void onPostExecute(File file) {
            super.onPostExecute(file);
            notificationBuilder.setContentText("Download Complete");
            notificationBuilder.setOngoing(false);
            notificationBuilder.setAutoCancel(true);
            notificationManager.notify(id, notificationBuilder.build());
            notificationManager.cancel(id);
            pendingIntent.cancel();
            Intent broadcastIntent = new Intent("message");
            broadcastIntent.putExtra("DOWNLOAD", "Success");
            broadcastIntent.putExtra("filename", fileName);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcastIntent);
        }
    }
}
