package com.example.ritu.a2_2016078;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;

import java.io.FileInputStream;
import java.io.IOException;

public class FirstService_A2_2016078 extends Service {

    public static final String ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE";
    public static final String ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE";
    public static final int ONGOING_NOTIFICATION_ID = 195;
    PendingIntent pendingIntent;
    public SongMap_A2_2016078 songMap;
    private MediaPlayer mediaPlayer;

    public FirstService_A2_2016078() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        songMap = new SongMap_A2_2016078();
        songMap.initialiseSongList();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();

            if (action.equals(ACTION_START_FOREGROUND_SERVICE)) {
                String songname = intent.getStringExtra("song");
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                if (songname != null && songMap.getHashMap().get(songname) != null)
                    mediaPlayer = MediaPlayer.create(this, Uri.parse(songMap.getHashMap().get(songname)));
                else if (songname != null){
                    try {
                        FileInputStream fileInputStream = openFileInput(songname);
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(fileInputStream.getFD());
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    mediaPlayer = MediaPlayer.create(this, Uri.parse(songMap.getHashMap().get(songMap.getKeys().get(0))));
                }
                Intent notificationIntent = new Intent(this, FirstActivity_A2_2016078.class);
                pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

                Intent playIntent = new Intent(this, FirstService_A2_2016078.class);
                playIntent.setAction(ACTION_START_FOREGROUND_SERVICE);
                playIntent.putExtra("song", songMap.getKeys().get(0));
                PendingIntent pendingPlayIntent = PendingIntent.getService(this, 0, playIntent, 0);

                Intent stopIntent = new Intent(this, FirstService_A2_2016078.class);
                stopIntent.setAction(ACTION_STOP_FOREGROUND_SERVICE);
                PendingIntent pendingStopIntent = PendingIntent.getService(this, 0, stopIntent, 0);

                Notification.Builder notification = new Notification.Builder(this)
                        .setContentTitle(getText(R.string.Notfication_title))
                        .setTicker(getText(R.string.Notfication_title))
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentIntent(pendingIntent)
                        .setOngoing(true)
                        .addAction(0, "Play", pendingPlayIntent)
                        .addAction(0, "Stop", pendingStopIntent);
                if (songname != null)
                    notification.setContentText("Playing - " + songname);
                else
                    notification.setContentText("Playing - " + songMap.getKeys().get(0));
                startForeground(ONGOING_NOTIFICATION_ID, notification.build());
                stopForeground(false);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.seekTo(0);
                        mediaPlayer.start();
                    }
                });
            }
            else if (action.equals(ACTION_STOP_FOREGROUND_SERVICE)) {
                stopForeground(true);
                stopSelf();
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer = null;
        }
        stopForeground(true);
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(ONGOING_NOTIFICATION_ID);
        if (pendingIntent != null)
            pendingIntent.cancel();
        stopSelf();
    }
}
