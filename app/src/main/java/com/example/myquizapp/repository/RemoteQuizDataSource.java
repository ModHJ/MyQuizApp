package com.example.myquizapp.repository;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import com.example.myquizapp.model.QuizQuestion;
import com.example.myquizapp.network.ApiClient;
import com.example.myquizapp.network.QuizApiService;
import com.example.myquizapp.network.dto.OpenTriviaQuestionDto;
import com.example.myquizapp.network.dto.OpenTriviaResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteQuizDataSource implements QuizDataSource {

    @Override
    public void getQuestions(Context context, QuizCallback callback) {
        QuizApiService apiService = ApiClient.getInstance().create(QuizApiService.class);
        Call<OpenTriviaResponse> call = apiService.getQuestions(10, "multiple");

        call.enqueue(new Callback<OpenTriviaResponse>() {
            @Override
            public void onResponse(Call<OpenTriviaResponse> call, Response<OpenTriviaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<QuizQuestion> questions = new ArrayList<>();
                    for (OpenTriviaQuestionDto dto : response.body().results) {
                        List<String> allAnswers = new ArrayList<>(dto.incorrect_answers);
                        allAnswers.add(dto.correct_answer);
                        Collections.shuffle(allAnswers);

                        if (allAnswers.size() != 4) continue;

                        questions.add(new QuizQuestion(
                                decodeHtml(dto.question),
                                decodeHtml(dto.correct_answer),
                                decodeHtmlList(allAnswers),
                                dto.category,
                                dto.difficulty
                        ));
                    }
                    callback.onQuestionsLoaded(questions);
                } else {
                    Log.e("RemoteQuizDataSource", "Empty response or API error");
                            callback.onQuestionsLoaded(Collections.emptyList());
                }
            }

            @Override
            public void onFailure(Call<OpenTriviaResponse> call, Throwable t) {
                Log.e("RemoteQuizDataSource", "API call failed: " + t.getMessage());
                        callback.onQuestionsLoaded(Collections.emptyList());
            }
        });
    }

    private String decodeHtml(String html) {
        Spanned spanned;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            spanned = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            spanned = Html.fromHtml(html);
        }
        return spanned.toString().trim();
    }

    private List<String> decodeHtmlList(List<String> rawList) {
        List<String> decoded = new ArrayList<>();
        for (String s : rawList) {
            decoded.add(decodeHtml(s));
        }
        return decoded;
    }

}
