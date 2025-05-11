package com.example.myquizapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.myquizapp.R;
import com.example.myquizapp.data.local.preferences.UserPreferences;
import com.example.myquizapp.data.local.session.AppSession;
import com.example.myquizapp.model.User;

public class HomeActivity extends AppCompatActivity {

    private TextView tvHighScore;
    private AppCompatButton btnStartQuiz, btnLogout;
    private SoundControls soundControls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        UserPreferences userPreferences = new UserPreferences(this);

        // SoundControls setup (using included view)
        View soundControlsView = findViewById(R.id.sound_controls);
        soundControls = new SoundControls(this, soundControlsView, R.raw.home_background_music, true);

        // High score
        tvHighScore = findViewById(R.id.tv_high_score);
        User user = AppSession.getLoggedInUser();
        if (user != null) {
            tvHighScore.setText("High Score: " + user.getHighScore());
        }

        // Buttons
        btnStartQuiz = findViewById(R.id.btn_start_quiz);
        btnLogout = findViewById(R.id.btn_logout);

        btnStartQuiz.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, QuizActivity.class));
            finish(); // prevent back nav to home during quiz
        });

        btnLogout.setOnClickListener(v -> {
            AppSession.clear();                         // clear current user
            userPreferences.setRememberMe(false, null);       // remove auto-login
            startActivity(new Intent(this, LoginActivity.class));
            finish();                                   // prevent back nav to Home
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (soundControls != null) {
            soundControls.stopMusic();
        }
    }
}
