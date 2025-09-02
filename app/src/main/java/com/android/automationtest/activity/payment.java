package com.android.automationtest.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.android.automationtest.R;
import com.google.android.material.textfield.TextInputEditText;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class payment extends AppCompatActivity {

    TextInputEditText nominalEditText;
    Button paymentQris,paymentCash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_activity);


        nominalEditText = findViewById(R.id.nominal_edit_text);
        paymentQris = findViewById(R.id.btn_qr_payment);
        paymentCash = findViewById(R.id.btn_cash_payment);


        paymentCash.setOnClickListener(view -> {
            Intent mainIntent = new Intent(payment.this, product_list_activity.class);
            startActivity(mainIntent);
        });

        paymentQris.setOnClickListener(view -> {
            Intent mainIntent = new Intent(payment.this, payment_qris.class);
            startActivity(mainIntent);
        });

        setupCurrencyFormatter();
    }

    private void setupCurrencyFormatter() {
        nominalEditText.addTextChangedListener(new TextWatcher() {
            String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    nominalEditText.removeTextChangedListener(this);

                    // Hapus format mata uang yang ada
                    String cleanString = s.toString().replaceAll("[,.]", "");

                    // Format ulang ke mata uang
                    DecimalFormat format = new DecimalFormat("#,###", new DecimalFormatSymbols(new Locale("id", "ID")));
                    String formatted = "";
                    try {
                        double parsed = Double.parseDouble(cleanString);
                        formatted = format.format(parsed);
                    } catch (NumberFormatException e) {
                        formatted = "";
                    }

                    current = formatted;
                    nominalEditText.setText(formatted);
                    nominalEditText.setSelection(formatted.length()); // Pindahkan kursor ke akhir
                    nominalEditText.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    // Untuk mendapatkan nominal tanpa format
    private double getUnformattedNominal() {
        String cleanString = nominalEditText.getText().toString().replaceAll("[,.]", "");
        if (cleanString.isEmpty()) {
            return 0;
        }
        return Double.parseDouble(cleanString);
    }
}
