package com.example.myquizapp.repository;

import android.content.Context;

import com.example.myquizapp.model.QuizQuestion;

import java.util.List;

public interface QuizDataSource {
    void getQuestions(Context context, QuizCallback callback);

    interface QuizCallback {
        void onQuestionsLoaded(List<QuizQuestion> questions);
    }
}
