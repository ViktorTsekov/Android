package com.example.gameproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

public class Sound {
    private MediaPlayer mp;
    private Context context;

    public Sound(Context context) {
        this.context = context;
        mp = MediaPlayer.create(context, R.raw.soundtrack1);
    }

    public void playSoundtrack() {
        setVolume();
        mp.setLooping(true);
        mp.start();
    }

    public void stopSoundtrack() {
        mp.stop();
        mp.release();
    }

    private void setVolume() {
        SharedPreferences preferences = context.getSharedPreferences( context.getPackageName() + "_preferences", context.MODE_PRIVATE);
        float vol = (float) preferences.getInt("volume", 100) / 100;

        mp.setVolume(vol, vol);
    }

}
