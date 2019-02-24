package com.android.project.abcappen.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.android.project.abcappen.R;

public class BackgroundMusicService extends Service {

    MediaPlayer backgroundPlayer;
    public IBinder onBind(Intent arg0){
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        backgroundPlayer = MediaPlayer.create(this, R.raw.despicable);
        backgroundPlayer.setLooping(true);
        backgroundPlayer.setVolume(100, 100);

    }
    public  int onStartCommand(Intent intent,int flags,int startId){

        backgroundPlayer.start();
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        backgroundPlayer.stop();
        backgroundPlayer.release();

    }

    @Override
    public void onLowMemory() {

    }
}

