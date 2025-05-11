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
    private final View viewGroup;
    private final int musicResId;
    private final boolean shouldLoop;
    private MediaPlayer musicPlayer;
    private boolean isMusicOn, isSoundOn;

    public SoundControls(Activity activity, View viewGroup, int musicResId, boolean shouldLoop) {
        prefs = new UserPreferences(activity);

        this.viewGroup = viewGroup;
        this.musicResId = musicResId;
        this.shouldLoop = shouldLoop;

        btnMusic = viewGroup.findViewById(R.id.btn_music);
        btnSound = viewGroup.findViewById(R.id.btn_sound);

        isMusicOn = prefs.isMusicOn();
        isSoundOn = prefs.isSoundOn();

        updateIcons();

        musicPlayer = MediaPlayer.create(activity, musicResId);
        musicPlayer.setLooping(shouldLoop);
        float initialVolume = isMusicOn ? 1.0f : 0.0f;
        musicPlayer.setVolume(initialVolume, initialVolume);
        musicPlayer.start();

        btnMusic.setOnClickListener(v -> {
            isMusicOn = !isMusicOn;
            prefs.setMusicOn(isMusicOn);
            updateIcons();

            if (musicPlayer != null) {
                float volume = isMusicOn ? 1.0f : 0.0f;
                musicPlayer.setVolume(volume, volume);
                if (!musicPlayer.isPlaying()) {
                    musicPlayer.start();
                }
            }
        });

        btnSound.setOnClickListener(v -> {
            isSoundOn = !isSoundOn;
            prefs.setSoundOn(isSoundOn);
            updateIcons();
        });
    }

    private void updateIcons() {
        btnMusic.setImageResource(isMusicOn ? R.drawable.ic_music_on : R.drawable.ic_music_off);
        btnSound.setImageResource(isSoundOn ? R.drawable.ic_fx_on : R.drawable.ic_fx_off);
    }

    public void stopMusic() {
        if (musicPlayer != null) {
            musicPlayer.stop();
            musicPlayer.release();
            musicPlayer = null;
        }
    }

    public void resetAndPlayMusicFromStart() {
        if (musicPlayer != null) {
            musicPlayer.stop();
            musicPlayer.release();
        }

        musicPlayer = MediaPlayer.create(viewGroup.getContext(), musicResId);
        musicPlayer.setLooping(shouldLoop);

        float volume = isMusicOn ? 1.0f : 0.0f;
        musicPlayer.setVolume(volume, volume);
        musicPlayer.start();
    }

    public boolean isSoundOn() {
        return isSoundOn;
    }

    public boolean isMusicOn() {
        return isMusicOn;
    }
}
