package com.example.myquizapp.model;

import java.util.List;

public class QuizQuestion {
    private String question;
    private String correctAnswer;
    private List<String> allAnswers; // includes correct + incorrect
    private String category;
    private String difficulty;

    public QuizQuestion(String question, String correctAnswer, List<String> allAnswers,
                        String category, String difficulty) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.allAnswers = allAnswers;
        this.category = category;
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getAllAnswers() {
        return allAnswers;
    }

    public String getCategory() {
        return category;
    }

    public String getDifficulty() {
        return difficulty;
    }
}