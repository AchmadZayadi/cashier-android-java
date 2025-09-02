package com.android.automationtest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.automationtest.R;


public class Dashboard extends AppCompatActivity {

    private CardView cardProduct,cardPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        cardPayment = findViewById(R.id.card_view_report);
        cardProduct = findViewById(R.id.card_view_product);


        cardPayment.setOnClickListener(view -> {
            Intent mainIntent = new Intent(Dashboard.this, payment.class);
            startActivity(mainIntent);

        });

        cardProduct.setOnClickListener(view -> {
            Intent mainIntent = new Intent(Dashboard.this, product_list_activity.class);
            startActivity(mainIntent);

        });

    }


}