package com.example.myquizapp.repository;

import android.content.Context;

import com.example.myquizapp.model.QuizQuestion;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MockQuizDataSource implements QuizDataSource {

    @Override
    public void getQuestions(Context context, QuizCallback callback) {
        List<QuizQuestion> dummy = Arrays.asList(
                new QuizQuestion(
                        "What is the capital of France?",
                        "Paris",
                        Arrays.asList("Paris", "Berlin", "Rome", "Madrid"),
                        "Geography", "Easy"
                ),
                new QuizQuestion(
                        "Which planet is known as the Red Planet?",
                        "Mars",
                        Arrays.asList("Venus", "Mars", "Jupiter", "Saturn"),
                        "Science", "Easy"
                )
        );

        // Shuffle answers inside each question if needed
        for (QuizQuestion q : dummy) {
            Collections.shuffle(q.getAllAnswers());
        }

        callback.onQuestionsLoaded(dummy);
    }
}
