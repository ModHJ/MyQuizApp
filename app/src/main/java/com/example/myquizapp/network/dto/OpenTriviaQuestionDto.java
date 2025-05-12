package com.example.myquizapp.network.dto;

import java.util.List;

public class OpenTriviaQuestionDto {
    public String category;
    public String type;
    public String difficulty;
    public String question;
    public String correct_answer;
    public List<String> incorrect_answers;
}
