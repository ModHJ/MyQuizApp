package com.example.myquizapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.myquizapp.model.QuizQuestion;
import com.example.myquizapp.repository.QuizDataSource;

import java.util.List;

public class QuizViewModel extends AndroidViewModel {

    private final QuizDataSource quizDataSource;

    private List<QuizQuestion> questions;
    private int currentIndex = 0;
    private int score = 0;

    public QuizViewModel(@NonNull Application application, QuizDataSource quizDataSource) {
        super(application);
        this.quizDataSource = quizDataSource;
    }

    public void loadQuestions(QuizDataSource.QuizCallback callback) {
        quizDataSource.getQuestions(getApplication(), loaded -> {
            questions = loaded;
            currentIndex = 0;
            score = 0;
            callback.onQuestionsLoaded(loaded); // Let Activity know it's ready
        });
    }

    public QuizQuestion getCurrentQuestion() {
        if (questions == null || questions.isEmpty()) return null;
        return questions.get(currentIndex);
    }

    public void confirmAnswer(String selectedAnswer) {
        QuizQuestion current = getCurrentQuestion();
        if (current != null && selectedAnswer.equals(current.getCorrectAnswer())) {
            score++;
        }
    }

    public boolean hasNextQuestion() {
        return questions != null && currentIndex + 1 < questions.size();
    }

    public void moveToNextQuestion() {
        currentIndex++;
    }

    public int getScore() {
        return score;
    }

    public int getCurrentQuestionIndex() {
        return currentIndex;
    }

    public int getTotalQuestions() {
        return questions != null ? questions.size() : 0;
    }
}
