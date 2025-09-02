package com.android.automationtest.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.automationtest.R;
import com.android.automationtest.response.CheckVersionResponse;
import com.android.automationtest.services.ApiService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    // Durasi waktu tunggu dalam milidetik (5000 ms = 5 detik)
    private static final int SPLASH_TIME_OUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen_activity);
//        hideSystemBars();
        checkVersion();
       }

    private void checkVersion() {
        // Alamat dasar API Anda
        String BASE_URL = "http://localhost:3006/";

        // 1. Inisialisasi Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // 2. Buat instance dari service interface
        ApiService service = retrofit.create(ApiService.class);

        // 3. Panggil API secara asynchronous
        Call<CheckVersionResponse> call = service.checkVersion();
        call.enqueue(new Callback<CheckVersionResponse>() {
            @Override
            public void onResponse(@NonNull Call<CheckVersionResponse> call, @NonNull Response<CheckVersionResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Berhasil: Tangani respons di sini
                    Toast.makeText(SplashScreenActivity.this, "Login berhasil!", Toast.LENGTH_SHORT).show();
                    Intent mainIntent = new Intent(SplashScreenActivity.this, Dashboard.class);
                    startActivity(mainIntent);

                } else {
                    // Gagal: Tangani error respons
                    Toast.makeText(SplashScreenActivity.this, "Login Gagal: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CheckVersionResponse> call, @NonNull Throwable t) {
                // Sangat Gagal: Tangani error koneksi
                Toast.makeText(SplashScreenActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("hasilnyaa" , Objects.requireNonNull(t.getMessage()));
            }
        });

    }


}