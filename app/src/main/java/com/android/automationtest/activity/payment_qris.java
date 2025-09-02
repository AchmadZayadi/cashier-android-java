package com.android.automationtest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.automationtest.R;

import java.text.DecimalFormat;
import java.util.Locale;

public class payment_qris extends AppCompatActivity {
    Button btnCancelPayment;
    TextView  tvTotal,tvTimer;
    String totalPaymanet;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 60000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qris_activity);

        btnCancelPayment = findViewById(R.id.btn_cancel_payment);
        tvTotal = findViewById(R.id.tv_total_payment_amount);
        tvTimer =findViewById(R.id.tv_timer);



        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            DecimalFormat currencyFormat = new DecimalFormat("'Rp ' #,##0");
           totalPaymanet=(String) b.get("total_payment");
           tvTotal.setText(totalPaymanet);

        }
        // Contoh: Menerima total harga dari intent jika ada
        // double totalAmount = getIntent().getDoubleExtra("total_amount", 0.0);

        btnCancelPayment.setOnClickListener(v -> {
            Toast.makeText(this, "Transaksi dibatalkan.", Toast.LENGTH_SHORT).show();
            finish(); // Menutup activity ini
        });

        startCountdown();


        // Di sini Anda bisa menambahkan logika untuk memproses pembayaran
        // Misalnya, menampilkan timer atau menunggu respons dari API
    }

    private void startCountdown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                tvTimer.setText("Selesai!");
            }
        }.start();
    }

    private void updateCountdownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        tvTimer.setText(timeLeftFormatted);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Hentikan timer untuk menghindari memory leaks saat Activity ditutup
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}