package com.example.simpleapp.api;

import com.example.simpleapp.models.RandomUserResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RandomUserService {
    @GET("api/")
    Call<RandomUserResponse> getRandomUser();
}
