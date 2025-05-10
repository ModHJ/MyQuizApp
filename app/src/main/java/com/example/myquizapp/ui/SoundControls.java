package com.example.myquizapp.ui;

import android.app.Activity;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageButton;

import com.example.myquizapp.R;
import com.example.myquizapp.data.local.preferences.UserPreferences;

public class SoundControls {

    private final ImageButton btnMusic;
    private final ImageButton btnSound;
    private final UserPreferences prefs;
    private MediaPlayer musicPlayer;
    private boolean isMusicOn, isSoundOn;

    public SoundControls(Activity activity, View viewGroup, int musicResId) {
        prefs = new UserPreferences(activity);

        btnMusic = viewGroup.findViewById(R.id.btn_music);
        btnSound = viewGroup.findViewById(R.id.btn_sound);

        isMusicOn = prefs.isMusicOn();
        isSoundOn = prefs.isSoundOn();

        updateIcons();

        btnMusic.setOnClickListener(v -> {
            isMusicOn = !isMusicOn;
            prefs.setMusicOn(isMusicOn);
            updateIcons();

            if (musicPlayer != null) {
                if (isMusicOn) {
                    if (!musicPlayer.isPlaying()) {
                        musicPlayer.start();
                    }
                } else {
                    musicPlayer.pause();
                }
            }
        });

        btnSound.setOnClickListener(v -> {
            isSoundOn = !isSoundOn;
            prefs.setSoundOn(isSoundOn);
            updateIcons();
        });

        musicPlayer = MediaPlayer.create(activity, musicResId);
        if (musicPlayer != null) {
            musicPlayer.setLooping(true);
            if (isMusicOn) musicPlayer.start();
        }
    }

    private void updateIcons() {
        btnMusic.setImageResource(isMusicOn ? R.drawable.ic_music_on : R.drawable.ic_music_off);
        btnSound.setImageResource(isSoundOn ? R.drawable.ic_fx_on : R.drawable.ic_fx_off);
    }

    public void release() {
        if (musicPlayer != null) {
            musicPlayer.release();
            musicPlayer = null;
        }
    }

    public boolean isSoundOn() {
        return isSoundOn;
    }

    public boolean isMusicOn() {
        return isMusicOn;
    }
}

