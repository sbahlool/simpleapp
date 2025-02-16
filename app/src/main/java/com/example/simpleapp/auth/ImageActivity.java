package com.example.simpleapp.auth;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpleapp.adapters.ImageAdapter;
import com.example.simpleapp.api.ApiClient;
import com.example.simpleapp.api.ApiService;
import com.example.simpleapp.models.Image;
import com.example.simpleapp.models.ImageResponse;
import com.example.simpleapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private ProgressBar progressBar;
    private final List<Image> imageList = new ArrayList<>();
    private ApiService apiService;
    private int page = 1;
    private boolean isLoading = false;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        imageAdapter = new ImageAdapter(imageList);
        recyclerView.setAdapter(imageAdapter);

        apiService = ApiClient.getPixabayClient().create(ApiService.class);

        loadImages(page);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && (visibleItemCount + firstVisibleItemPosition >= totalItemCount)
                        && firstVisibleItemPosition >= 0) {
                    loadMoreImages();
                }
            }
        });
    }

    private void loadImages(int page) {
        isLoading = true;
        progressBar.setVisibility(View.VISIBLE);

        String apiKey = "48876379-e4ce1aac4c828503f7c4fee1d";

        apiService.getImages(apiKey, page, 20).enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    imageList.addAll(response.body().getHits());
                    imageAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ImageActivity.this, "Failed to load images", Toast.LENGTH_SHORT).show();
                }
                isLoading = false;
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                isLoading = false;
                Toast.makeText(ImageActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMoreImages() {
        page++;
        loadImages(page);
    }
}
