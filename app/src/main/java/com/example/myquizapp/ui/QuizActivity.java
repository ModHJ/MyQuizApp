package com.example.myquizapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.myquizapp.R;
import com.example.myquizapp.viewmodel.QuizViewModel;
import com.example.myquizapp.model.QuizQuestion;

import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private QuizViewModel quizViewModel;

    private SoundControls soundControls;
    private ProgressBar progress;
    private TextView tvTimer, tvQuestionNumber, tvQuestion;
    private AppCompatButton[] answerButtons;
    private AppCompatButton btnConfirm;

    private int selectedAnswerIndex = -1;
    private CountDownTimer countDownTimer;
    private final int TOTAL_TIME = 30; // seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Init UI references
        soundControls = new SoundControls(this,
                            findViewById(R.id.sound_controls),
                            R.raw.quiz_countdown, false);
        tvTimer = findViewById(R.id.tv_timer);
        tvQuestionNumber = findViewById(R.id.tv_question_number);
        tvQuestion = findViewById(R.id.tv_question);
        btnConfirm = findViewById(R.id.btn_confirm);

        answerButtons = new AppCompatButton[] {
                findViewById(R.id.btn_answer_a),
                findViewById(R.id.btn_answer_b),
                findViewById(R.id.btn_answer_c),
                findViewById(R.id.btn_answer_d)
        };
        LinearLayout quizContainer = findViewById(R.id.quiz_container);
        quizContainer.setVisibility(View.GONE); // hide initially

        setupAnswerSelection();
        setupConfirmButton();

        // Create ViewModel
        quizViewModel = new QuizViewModel(getApplication());

        // Load questions
        quizViewModel.loadQuestions(questions -> {
            if (questions == null || questions.isEmpty()) {
                showNoQuestionsPopup(); // only show this when actual load failed or nothing valid
            } else {
                findViewById(R.id.quiz_container).setVisibility(View.VISIBLE);
                showCurrentQuestion();
            }
        });
    }

    private void showCurrentQuestion() {
        QuizQuestion q = quizViewModel.getCurrentQuestion();
        if (q == null) return;
        soundControls.resetAndPlayMusicFromStart();
        resetAnswerSelection();

        tvQuestion.setText(q.getQuestion());
        tvQuestionNumber.setText("Question " + (quizViewModel.getCurrentQuestionIndex() + 1));

        List<String> answers = q.getAllAnswers();
        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i].setText(answers.get(i));
        }

        startTimer();
    }

    private void setupAnswerSelection() {
        for (int i = 0; i < answerButtons.length; i++) {
            final int index = i;
            answerButtons[i].setOnClickListener(v -> {
                resetAnswerSelection();
                selectedAnswerIndex = index;
                answerButtons[index].setBackgroundResource(R.drawable.bg_answer_selected);
            });
        }
    }

    private void resetAnswerSelection() {
        for (AppCompatButton btn : answerButtons) {
            btn.setBackgroundResource(R.drawable.bg_answer_unselected);
        }
        selectedAnswerIndex = -1;
    }

    private void setupConfirmButton() {
        btnConfirm.setOnClickListener(v -> {
            if (selectedAnswerIndex == -1) return;

            String selected = answerButtons[selectedAnswerIndex].getText().toString();
            countDownTimer.cancel();

            boolean isCorrect = quizViewModel.confirmAnswer(selected);

            if (isCorrect) {
                if (quizViewModel.hasNextQuestion()) {
                    quizViewModel.moveToNextQuestion();
                    showCurrentQuestion();
                } else {
                    quizViewModel.submitFinalScore();
                    showGameOver();
                }
            } else {
                quizViewModel.submitFinalScore();
                showGameOver(); // Sudden death
            }
        });
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(TOTAL_TIME * 1000L, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsLeft = (int) (millisUntilFinished / 1000);
                tvTimer.setText(secondsLeft + "s");
                progress = findViewById(R.id.progress_timer);
                progress.setProgress(secondsLeft);
            }

            @Override
            public void onFinish() {
                tvTimer.setText("0s");
                quizViewModel.submitFinalScore();
                // Timeout logic: treat as wrong and move on
                showGameOver();
            }
        }.start();
    }

    private void showGameOver() {
        soundControls.stopMusic();

        RelativeLayout gameOver = findViewById(R.id.game_over_container);
        TextView scoreText = findViewById(R.id.tv_final_score);

        scoreText.setText("Score: " + quizViewModel.getScore());
        gameOver.setVisibility(View.VISIBLE);

        findViewById(R.id.btn_try_again).setOnClickListener(v -> {
            recreate(); // restart activity
        });

        findViewById(R.id.btn_back_home).setOnClickListener(v -> {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        });
    }

    private void showNoQuestionsPopup() {
        soundControls.stopMusic();

        RelativeLayout gameOver = findViewById(R.id.game_over_container);
        TextView scoreText = findViewById(R.id.tv_final_score);
        scoreText.setText("No questions found.\nCheck your internet and try again.");
        gameOver.setVisibility(View.VISIBLE);

        findViewById(R.id.btn_try_again).setOnClickListener(v -> recreate());
        findViewById(R.id.btn_back_home).setOnClickListener(v -> {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
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