package com.android.automationtest.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.automationtest.R;
import com.android.automationtest.adapter.OrderedProductAdapter;
import com.android.automationtest.response.productListResponse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderedProductAdapter adapter;
    private List<productListResponse> finalOrderList;

    private TextView tvSubtotal, tvTax, tvGrandTotal;
    private Button btnQris, btnCash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details_activity);

        recyclerView = findViewById(R.id.order_items_recycler_view);
        tvSubtotal = findViewById(R.id.tv_subtotal);
        tvTax = findViewById(R.id.tv_tax);
        tvGrandTotal = findViewById(R.id.tv_grand_total);
        btnQris = findViewById(R.id.btn_qris);
        btnCash = findViewById(R.id.btn_cash);

        // Menerima data dari Intent
        if (getIntent().getSerializableExtra("selected_products") != null) {
            finalOrderList = (List<productListResponse>) getIntent().getSerializableExtra("selected_products");
        } else {
            // Jika data tidak ada, inisialisasi list kosong
            finalOrderList = new ArrayList<>();
        }

        // Konfigurasi RecyclerView dan set adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OrderedProductAdapter(finalOrderList); // Hubungkan list ke adapter
        recyclerView.setAdapter(adapter); // Set adapter ke RecyclerVifinalOrderListew

        // Hitung dan perbarui total


        // OnClickListener untuk tombol pembayaran
        btnQris.setOnClickListener(v -> {
            Toast.makeText(this, "Membuka halaman pembayaran QRIS...", Toast.LENGTH_SHORT).show();
            // Implementasi intent untuk halaman QRIS
        });

        btnCash.setOnClickListener(v -> {
            Toast.makeText(this, "Pembayaran tunai berhasil!", Toast.LENGTH_SHORT).show();
            // Implementasi intent untuk halaman konfirmasi
        });

        calculateAndDisplayTotals();
    }

    private void calculateAndDisplayTotals() {
        double subtotal = 0;
        for (productListResponse product : finalOrderList) {
            subtotal += product.getPrice() * product.getQuantity();
        }

        double tax = subtotal * 0.1;
        double grandTotal = subtotal + tax;

        DecimalFormat currencyFormat = new DecimalFormat("'Rp ' #,##0");

        tvSubtotal.setText(currencyFormat.format(subtotal));
        tvTax.setText(currencyFormat.format(tax));
        tvGrandTotal.setText(currencyFormat.format(grandTotal));
    }

}