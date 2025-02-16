package com.example.simpleapp.api;

import com.example.simpleapp.models.ImageResponse;
import retrofit2.Call;


import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiService {

    @GET("api/")
    Call<ImageResponse> getImages(
            @Query("key") String apiKey,
            @Query("page") int page,
            @Query("per_page") int perPage
    );
}
