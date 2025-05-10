package com.example.myquizapp.data.local.storage;

import com.example.myquizapp.model.User;

public interface IUserStorage {
    boolean isUsernameTaken(String username);
    boolean isEmailTaken(String email);
    void saveUser(User user);
    User getUserByEmail(String email);
    void updateUser(User user);
}
