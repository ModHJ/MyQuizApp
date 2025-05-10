package com.example.myquizapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.myquizapp.R;
import com.example.myquizapp.data.local.preferences.UserPreferences;
import com.example.myquizapp.data.local.session.AppSession;
import com.example.myquizapp.model.LoginResult;
import com.example.myquizapp.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private UserPreferences userPreferences;
    private EditText etEmail, etPassword;
    private LoginViewModel loginViewModel;

    private CheckBox cbRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Bind views
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnRegister = findViewById(R.id.btn_register);
        cbRememberMe = findViewById(R.id.cb_remember_me);
        userPreferences = new UserPreferences(getApplicationContext());

        // ViewModel
        loginViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()))
                .get(LoginViewModel.class);

        // Check session or remember-me using AutoLogin
        if (loginViewModel.tryAutoLogin(userPreferences)) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            return;
        }

        // Login button logic
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLogin();
            }
        });

        // Register button logic
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void handleLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();

        LoginResult result = loginViewModel.login(email, password);

        switch (result) {
            case EMPTY_FIELDS:
                showToast("Please enter both email and password.");
                break;
            case INVALID_CREDENTIALS:
                showToast("Incorrect email or password.");
                break;
            case SUCCESS:
                AppSession.setLoggedIn(true);
                userPreferences.setRememberMe(cbRememberMe.isChecked(), email);
                showToast("Login successful!");
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish(); //close login screen so user can't go back
                break;
        }
    }

    private void showToast(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}