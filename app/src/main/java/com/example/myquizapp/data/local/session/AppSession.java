package com.example.myquizapp.data.local.session;

import com.example.myquizapp.model.User;

import java.util.HashSet;
import java.util.Set;

public class AppSession {

    private static final Set<String> sessionFlags = new HashSet<>();
    private static User currentUser;

    public static void setLoggedIn(boolean value) {
        if (value) sessionFlags.add("logged_in");
        else sessionFlags.remove("logged_in");
    }

    public static boolean isLoggedIn() {
        return sessionFlags.contains("logged_in");
    }

    public static void setLoggedInUser(User user) {
        currentUser = user;
        setLoggedIn(true); // mark user as logged in too
    }

    public static User getLoggedInUser() {
        return currentUser;
    }

    public static void clear() {
        sessionFlags.clear();
        currentUser = null;
    }
}
