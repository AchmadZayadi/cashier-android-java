package com.android.automationtest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.automationtest.R;
import com.android.automationtest.adapter.ProductAdapter;
import com.android.automationtest.response.productListResponse;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class product_list_activity extends AppCompatActivity implements OnQuantityChangeListener {

    private TextView tvOrderAmount, tvTax, tvTotalPayment, tvItemCount;
    private Button btnCheckout;
    private RecyclerView recyclerView;
    private DecimalFormat currencyFormat;
    private List<productListResponse> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity);


        recyclerView = findViewById(R.id.product_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("id", "ID"));
        symbols.setGroupingSeparator('.');
        symbols.setMonetaryDecimalSeparator(',');
        currencyFormat = new DecimalFormat("Rp #,##0", symbols);

        tvOrderAmount = findViewById(R.id.tv_order_amount);
        tvTax = findViewById(R.id.tv_tax);
        tvTotalPayment = findViewById(R.id.tv_total_payment);
        tvItemCount = findViewById(R.id.tv_item_count);
        btnCheckout = findViewById(R.id.btn_checkout);


        productList = new ArrayList<>();

        productList.add(new productListResponse("Mie Ayam", 40000, R.drawable.menu_1));
        productList.add(new productListResponse("Es Teh Manis", 35000, R.drawable.menu_2));
        productList.add(new productListResponse("Bakso", 50000, R.drawable.menu_3));





        ProductAdapter adapter = new ProductAdapter(productList,this);
        recyclerView.setAdapter(adapter);

        updateSummary();
        btnCheckout.setOnClickListener(v -> {


            List<productListResponse> selectedProducts = new ArrayList<>();

            // Perbaiki loop: Kirim produk dengan kuantitas > 0
            for (productListResponse product : productList) {
                // Asumsi productListResponse memiliki method getQuantity()
                if (product.getQuantity() > 0) {
                    selectedProducts.add(product);
                }
            }

            if (!selectedProducts.isEmpty()) {
                Intent intent = new Intent(product_list_activity.this, OrderDetailActivity.class);
                intent.putExtra("selected_products", (Serializable) selectedProducts);
                startActivity(intent);
            } else {
                Toast.makeText(product_list_activity.this, "Pilih setidaknya satu produk!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onQuantityChanged() {
        updateSummary();
    }
    private void updateSummary() {
        double orderAmount = 0;
        int itemCount = 0;

        // Hitung total harga dan kuantitas dari semua produk
        for (productListResponse product : productList) {
            orderAmount += product.getPrice() * product.getQuantity();
            itemCount += product.getQuantity();
        }

        double tax = 0;
        double totalPayment = 0;

        // Pengecekan: Jika tidak ada item, set semua total menjadi 0
        if (itemCount > 0) {
            // Jika ada item, hitung pajak dan total pembayaran
            tax = orderAmount * 0.1; // Contoh: Pajak 10%
            totalPayment = orderAmount + tax;
        }

        // Perbarui UI dengan nilai yang sudah dihitung
        tvOrderAmount.setText(currencyFormat.format(orderAmount));
        tvTax.setText(currencyFormat.format(tax));
        tvTotalPayment.setText(currencyFormat.format(totalPayment));
        tvItemCount.setText("(" + itemCount + " items)");
    }

}
