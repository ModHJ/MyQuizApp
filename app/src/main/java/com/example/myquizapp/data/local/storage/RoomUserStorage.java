package com.example.myquizapp.data.local.storage;

import com.example.myquizapp.data.dao.UserDao;
import com.example.myquizapp.model.User;

public class RoomUserStorage implements IUserStorage {

    private final UserDao userDao;

    public RoomUserStorage(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean isUsernameTaken(String username) {
        return userDao.getUserByUsername(username) != null;
    }

    @Override
    public boolean isEmailTaken(String email) {
        return userDao.getUserByEmail(email) != null;
    }

    @Override
    public void saveUser(User user) {
        userDao.insertUser(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }
}
