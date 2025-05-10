package com.example.myquizapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myquizapp.R;
import com.example.myquizapp.model.RegistrationResult;
import com.example.myquizapp.viewmodel.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {

    private RegisterViewModel registerViewModel;

    private EditText etUsername, etEmail, etPassword, etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Bind UI elements
        etUsername = findViewById(R.id.et_register_username);
        etEmail = findViewById(R.id.et_register_email);
        etPassword = findViewById(R.id.et_register_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        Button btnRegister = findViewById(R.id.btn_register_submit);
        TextView tvBackToLogin = findViewById(R.id.tv_back_to_login);

        tvBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go back to LoginActivity
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // closes RegisterActivity so it doesn't stay in the backstack
            }
        });

        // Get ViewModel
        registerViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()))
                .get(RegisterViewModel.class);

        // Register button click
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleRegistration();
            }
        });
    }

    private void handleRegistration() {
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        RegistrationResult result = registerViewModel.registerUser(username, email, password, confirmPassword);

        switch (result) {
            case EMPTY_FIELDS:
                showToast("Please fill in all fields.");
                break;
            case INVALID_EMAIL:
                showToast("Enter a valid email address.");
                break;
            case PASSWORD_TOO_SHORT:
                showToast("Password must be at least 6 characters.");
                break;
            case PASSWORD_MISMATCH:
                showToast("Passwords do not match.");
                break;
            case EMAIL_ALREADY_EXISTS:
                showToast("Email is already registered.");
                break;
            case USERNAME_ALREADY_EXISTS:
                showToast("Username is taken.");
                break;
            case SUCCESS:
                showToast("Registration successful!");
                finish(); // Optional: go back to login screen
                break;
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}