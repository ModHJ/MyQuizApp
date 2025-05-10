package com.example.myquizapp.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.myquizapp.data.local.preferences.UserPreferences;

public class SoundUtils {

    public static void playFx(Context context, int soundResId) {
        if (new UserPreferences(context).isSoundOn()) {
            MediaPlayer mp = MediaPlayer.create(context, soundResId);
            if (mp != null) {
                mp.setOnCompletionListener(MediaPlayer::release);
                mp.start();
            }
        }
    }
}
