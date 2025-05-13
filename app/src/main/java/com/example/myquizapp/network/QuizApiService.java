package com.example.myquizapp.network;


import com.example.myquizapp.network.dto.OpenTriviaResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface QuizApiService {

    @GET("api.php")
    Call<OpenTriviaResponse> getQuestions(
            @Query("amount") int amount,
            @Query("type") String type,
            @Query("difficulty") String difficulty
    );
}
