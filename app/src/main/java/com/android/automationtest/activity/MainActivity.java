package com.android.automationtest.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.automationtest.R;
import com.android.automationtest.response.LoginResponse;
import com.android.automationtest.services.ApiService;
import com.google.android.material.textfield.TextInputEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    String Email;
    String Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);


        final TextInputEditText emailEditText = findViewById(R.id.email_edit_text);
        final TextInputEditText passwordEditText = findViewById(R.id.password_edit_text);
        Button loginButton = findViewById(R.id.login_button);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 Email = emailEditText.getText().toString().trim();
                 Password = passwordEditText.getText().toString().trim();


                if (Email.isEmpty() || Password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Email dan password tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else {
                    performLogin();

                }
            }
        });
    }

    private void performLogin() {
        // Alamat dasar API Anda
        String BASE_URL = "http://localhost:3006/login";

        // 1. Inisialisasi Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // 2. Buat instance dari service interface
        ApiService service = retrofit.create(ApiService.class);

        // 3. Panggil API secara asynchronous
        Call<LoginResponse> call = service.loginUser(Email, Password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Berhasil: Tangani respons di sini
                    Toast.makeText(MainActivity.this, "Login berhasil!", Toast.LENGTH_SHORT).show();
                } else {
                    // Gagal: Tangani error respons
                    Toast.makeText(MainActivity.this, "Login Gagal: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Sangat Gagal: Tangani error koneksi
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}