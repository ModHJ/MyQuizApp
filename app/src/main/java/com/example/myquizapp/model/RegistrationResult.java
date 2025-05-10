package com.example.myquizapp.model;

public enum RegistrationResult {
    SUCCESS,
    EMPTY_FIELDS,
    INVALID_EMAIL,
    PASSWORD_TOO_SHORT,
    PASSWORD_MISMATCH,
    EMAIL_ALREADY_EXISTS,
    USERNAME_ALREADY_EXISTS
}
