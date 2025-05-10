package com.example.myquizapp.repository;

import com.example.myquizapp.data.local.storage.IUserStorage;
import com.example.myquizapp.model.User;
import com.example.myquizapp.utils.PasswordUtils;


public class UserRepository {

    private final IUserStorage userStorage;

    public UserRepository(IUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void saveUser(User user) {
        userStorage.saveUser(user);
    }

    public boolean validateLogin(String email, String plainPassword) {
        User user = userStorage.getUserByEmail(email);
        return user != null && PasswordUtils.checkPassword(plainPassword, user.getHashedPassword());
    }

    public User getUserByEmail(String email) {
        return userStorage.getUserByEmail(email);
    }

    public boolean usernameExists(String username) {
        return userStorage.isUsernameTaken(username);
    }

    public boolean emailExists(String email) {
        return userStorage.isEmailTaken(email);
    }

    public void updateUser(User user) {
        userStorage.updateUser(user);
    }
}