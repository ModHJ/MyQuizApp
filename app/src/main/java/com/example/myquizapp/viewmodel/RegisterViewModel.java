package com.example.myquizapp.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.myquizapp.model.RegistrationResult;
import com.example.myquizapp.model.User;
import com.example.myquizapp.repository.UserRepository;
import com.example.myquizapp.utils.PasswordUtils;

import java.util.Date;

public class RegisterViewModel extends AndroidViewModel {

    private final UserRepository userRepository;

    public RegisterViewModel(@NonNull Application application) {
        super(application);

        // Inject with RoomUserStorage instead of old SharedPrefs
        userRepository = new UserRepository(
                new com.example.myquizapp.data.local.storage.RoomUserStorage(
                        com.example.myquizapp.data.database.AppDatabase
                                .getInstance(application.getApplicationContext())
                                .userDao()
                )
        );
    }

    public RegistrationResult registerUser(String username, String email, String password, String confirmPassword) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            return RegistrationResult.EMPTY_FIELDS;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return RegistrationResult.INVALID_EMAIL;
        }

        if (password.length() < 6) {
            return RegistrationResult.PASSWORD_TOO_SHORT;
        }

        if (!password.equals(confirmPassword)) {
            return RegistrationResult.PASSWORD_MISMATCH;
        }

        if (userRepository.usernameExists(username)) {
            return RegistrationResult.USERNAME_ALREADY_EXISTS;
        }

        if (userRepository.emailExists(email)) {
            return RegistrationResult.EMAIL_ALREADY_EXISTS;
        }

        String hashedPassword = PasswordUtils.hashPassword(password);
        User user = new User(username, email, hashedPassword, new Date());
        userRepository.saveUser(user);

        return RegistrationResult.SUCCESS;
    }
}
