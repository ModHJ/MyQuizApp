package com.example.myquizapp.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.myquizapp.data.local.preferences.UserPreferences;
import com.example.myquizapp.model.User;
import com.example.myquizapp.data.database.AppDatabase;
import com.example.myquizapp.data.local.session.AppSession;
import com.example.myquizapp.data.local.storage.RoomUserStorage;
import com.example.myquizapp.model.LoginResult;
import com.example.myquizapp.repository.UserRepository;

public class LoginViewModel extends AndroidViewModel {
    private final UserRepository userRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);

        userRepository = new UserRepository(
                new RoomUserStorage(
                        AppDatabase.getInstance(application.getApplicationContext()).userDao()
                )
        );
    }

    public boolean tryAutoLogin(UserPreferences userPreferences) {
        if (AppSession.getLoggedInUser() != null) {
            return true;
        }

        if (userPreferences.isRememberMeEnabled()) {
            String email = userPreferences.getRememberedEmail();
            if (email != null) {
                User user = userRepository.getUserByEmail(email);
                if (user != null) {
                    AppSession.setLoggedInUser(user);
                    return true;
                }
            }
        }

        return false;
    }

    public LoginResult login(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            return LoginResult.EMPTY_FIELDS;
        }

        boolean isValid = userRepository.validateLogin(email, password);

        if (isValid) {
            User user = userRepository.getUserByEmail(email);
            AppSession.setLoggedInUser(user); // save user in AppSession
            return LoginResult.SUCCESS;
        } else {
            return LoginResult.INVALID_CREDENTIALS;
        }
    }

}