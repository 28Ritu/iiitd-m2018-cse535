package com.example.ritu.a3_2016078;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class FirstService_A3_2016078 extends Service {

    private String serverUrl = "http://192.168.43.203/UploadToServer.php";
    private QuestionDBHelper_A3_2016078 questionDBHelper;

    public FirstService_A3_2016078() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        questionDBHelper = QuestionDBHelper_A3_2016078.get(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new UploadDBTask().execute(serverUrl);
        } else {
            Intent broadcastIntent = new Intent("message");
            broadcastIntent.putExtra("UPLOAD","Failure");
            LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
        }
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private class UploadDBTask extends AsyncTask<String, String, Void> {

        int progress = 0;
        Notification.Builder notificationBuilder;
        NotificationManager notificationManager;
        int id = 1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationBuilder = new Notification.Builder(getApplicationContext())
                    .setPriority(Notification.PRIORITY_MAX)
                    .setProgress(100, 0, false)
                    .setContentTitle("Uploading File")
                    .setSmallIcon(R.mipmap.ic_launcher  )
                    .setOngoing(true);
            notificationManager.notify(id, notificationBuilder.build());
        }

        @Override
        protected Void doInBackground(String... urls) {
            String filename = "QuizDatabase.csv";
            File file = new File(getFilesDir(), filename);

            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getApplicationContext().openFileOutput(filename, Context.MODE_PRIVATE));
                Cursor cursor = questionDBHelper.getCursor(1);

                String[] columns = cursor.getColumnNames();
                int i = 0;
                for (i = 0; i < columns.length - 1; i++) {
                    outputStreamWriter.append(columns[i] + ", ");
                }
                outputStreamWriter.append(columns[i] + "\n");

                for (i = 1; i <= 30; i++) {
                    cursor = questionDBHelper.getCursor(i);
                    outputStreamWriter.append(Integer.toString(cursor.getInt(0)) + ", ");
                    outputStreamWriter.append(cursor.getString(1) + ", ");
                    outputStreamWriter.append(cursor.getString(2) + "\n");
                }
                outputStreamWriter.close();

                String lineEnd = "\r\n";
                String twoHyphens = "--";
                String boundary = "*****";
                int bytesRead, bufferSize;
                byte[] buffer;

                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty("fileToUpload", filename);

                DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"fileToUpload\";filename=\""
                        + filename + "\"" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);


                FileInputStream fileInputStream = openFileInput(filename);
                notificationBuilder.setContentTitle("Uploading File - " + file.getName());
                notificationManager.notify(id, notificationBuilder.build());

                bufferSize = (int) file.length()/1024;
                buffer = new byte[bufferSize];

                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                long total = 0;

                while (bytesRead > 0) {
                    dataOutputStream.write(buffer, 0, bufferSize);
                    total += bufferSize;
                    publishProgress("" + Long.toString((int) (total * 100) / file.length()));
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                fileInputStream.close();
                dataOutputStream.flush();

                int serverResponseCode = connection.getResponseCode();
                String serverResponseMessage = connection.getResponseMessage();

                Log.d("Message", serverResponseMessage + " " + serverResponseCode + " " + connection.getURL());

                dataOutputStream.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    Log.d("message", line);
                }

            } catch (Exception e) {
                Log.d("upload", "not complete "+ e.toString());
            }
            return null;
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
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            notificationBuilder.setContentText("Upload Complete");
            notificationBuilder.setOngoing(false);
            notificationBuilder.setAutoCancel(true);
            notificationManager.notify(id, notificationBuilder.build());

            Intent broadcastIntent = new Intent("message");
            broadcastIntent.putExtra("UPLOAD", "Success");
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcastIntent);
        }
    }
}
