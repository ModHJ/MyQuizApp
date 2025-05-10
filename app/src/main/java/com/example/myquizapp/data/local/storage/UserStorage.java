/*package com.example.myquizapp.data.local.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class UserStorage implements IUserStorage {

    private static final String PREFS_NAME = "user_prefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    private final SharedPreferences prefs;

    public UserStorage(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveUser(String username, String email, String password) {
        prefs.edit()
                .putString(KEY_USERNAME, username)
                .putString(KEY_EMAIL, email)
                .putString(KEY_PASSWORD, password)
                .apply();
    }

    public String getUsername() {
        return prefs.getString(KEY_USERNAME, "");
    }

    public String getEmail() {
        return prefs.getString(KEY_EMAIL, "");
    }

    public boolean validateLogin(String email, String password) {
        String storedEmail = getEmail();
        String storedPassword = prefs.getString(KEY_PASSWORD, "");
        return email.equals(storedEmail) && password.equals(storedPassword);
    }and

    public boolean isRememberMeEnabled() {
        return prefs.getBoolean("remember_me", false);
    }

    public void setRememberMe(boolean rememberMe) {
        prefs.edit().putBoolean("remember_me", rememberMe).apply();
    }
}*/
