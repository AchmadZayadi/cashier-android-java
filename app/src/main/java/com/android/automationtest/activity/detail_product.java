package com.android.automationtest.activity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.automationtest.R;

public class detail_product extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_product);

        ImageView detailProductImage = findViewById(R.id.detail_product_image);
        TextView detailProductName = findViewById(R.id.detail_product_name);
        TextView detailProductPrice = findViewById(R.id.detail_product_price);

        // Dapatkan referensi ke tombol "Beli Sekarang"
        Button buyButton = findViewById(R.id.buy_button);

        // Menerima data dari Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("product_name");
            double price = extras.getDouble("product_price");
            int imageResId = extras.getInt("product_image");

            // Menampilkan data di UI
            detailProductName.setText(name);
            detailProductPrice.setText("Rp " + String.format("%,.0f", price));
            detailProductImage.setImageResource(imageResId);

            // Tambahkan OnClickListener untuk tombol beli
            buyButton.setOnClickListener(v -> {
                // Tampilkan pesan konfirmasi
                Toast.makeText(this, "Anda berhasil membeli " + name + "!", Toast.LENGTH_SHORT).show();
            });
        }
    }
}





