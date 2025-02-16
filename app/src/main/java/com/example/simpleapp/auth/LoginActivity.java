package com.example.simpleapp.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.simpleapp.R;
import com.example.simpleapp.api.RandomUserService;
import com.example.simpleapp.models.RandomUserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import androidx.core.content.ContextCompat;


public class LoginActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button loginButton;
    private ProgressBar progressBar;

    private String generatedEmail;
    private String generatedPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar);

        // Fetch random user credentials
        fetchRandomUser();

        loginButton.setOnClickListener(v -> handleLogin());
    }

    private void fetchRandomUser() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://randomuser.me/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RandomUserService apiService = retrofit.create(RandomUserService.class);

        apiService.getRandomUser().enqueue(new Callback<RandomUserResponse>() {
            @Override
            public void onResponse(Call<RandomUserResponse> call, Response<RandomUserResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getResults().size() > 0) {
                    generatedEmail = response.body().getResults().get(0).getEmail();
                    generatedPassword = response.body().getResults().get(0).getLogin().getPassword();

                    emailInput.setText(generatedEmail);
                    passwordInput.setText(generatedPassword);

                    Log.d("LoginActivity", "Fetched credentials: " + generatedEmail + " / " + generatedPassword);
                    Toast.makeText(LoginActivity.this, "Generated user: " + generatedEmail, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Failed to fetch user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RandomUserResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error fetching user", Toast.LENGTH_SHORT).show();
                Log.e("LoginActivity", "Fetch error: " + t.getMessage());
            }
        });
    }

    private void handleLogin() {
        String enteredEmail = emailInput.getText().toString().trim();
        String enteredPassword = passwordInput.getText().toString().trim();

        if (generatedEmail == null || generatedPassword == null) {
            Toast.makeText(this, "User data not loaded yet!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (enteredEmail.equals(generatedEmail) && enteredPassword.equals(generatedPassword)) {
            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
            Log.d("LoginActivity", "Login success for: " + enteredEmail);
            startActivity(new Intent(LoginActivity.this, ImageActivity.class));
        } else {
            Toast.makeText(this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
            emailInput.setError("invalid credentials");
            emailInput.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.error_red));
            passwordInput.setError("invalid credentials");
            passwordInput.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.error_red));
            Log.e("LoginActivity", "Login failed for: " + enteredEmail);
        }
    }
}
