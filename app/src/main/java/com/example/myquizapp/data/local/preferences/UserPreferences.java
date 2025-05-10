package com.example.myquizapp.data.local.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreferences {
    private final SharedPreferences prefs;
    private static final String PREF_NAME = "user_prefs";
    private static final String KEY_REMEMBER_ME = "remember_me";
    private static final String KEY_REMEMBERED_EMAIL = "remembered_email";
    private static final String KEY_SOUND_ON = "sound_fx_on";
    private static final String KEY_MUSIC_ON = "music_on";


    public UserPreferences(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public String getRememberedEmail() {
        return prefs.getString(KEY_REMEMBERED_EMAIL, null);
    }

    public boolean isRememberMeEnabled() {
        return prefs.getBoolean(KEY_REMEMBER_ME, false);
    }

    public void setRememberMe(boolean value, String email) {
        prefs.edit()
                .putBoolean(KEY_REMEMBER_ME, value)
                .putString(KEY_REMEMBERED_EMAIL, value ? email : null)
                .apply();
    }

    public void setSoundOn(boolean value) {
        prefs.edit().putBoolean(KEY_SOUND_ON, value).apply();
    }

    public boolean isSoundOn() {
        return prefs.getBoolean(KEY_SOUND_ON, true); // default = on
    }

    public void setMusicOn(boolean value) {
        prefs.edit().putBoolean(KEY_MUSIC_ON, value).apply();
    }

    public boolean isMusicOn() {
        return prefs.getBoolean(KEY_MUSIC_ON, true); // default = on
    }
}
