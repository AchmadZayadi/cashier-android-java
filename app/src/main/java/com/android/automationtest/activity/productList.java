package com.android.automationtest.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.automationtest.R;

import java.util.ArrayList;

public class productList extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list);

        ListView productListView = findViewById(R.id.product_list_view);

        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Sepatu Keren", 150000, R.drawable.ic_launcher_background));
        products.add(new Product("Jam Tangan Digital", 250000, R.drawable.ic_launcher_background));
        products.add(new Product("Kemeja Kasual", 120000, R.drawable.ic_launcher_background));

        ProductAdapter adapter = new ProductAdapter(products);
        productListView.setAdapter(adapter);

        // Tambahkan Item Click Listener di sini
        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Dapatkan produk yang diklik
                Product selectedProduct = products.get(position);

                // Buat Intent untuk berpindah ke DetailActivity
                Intent detailIntent = new Intent(productList.this, detail_product.class);

                // Masukkan data produk ke dalam Intent
                detailIntent.putExtra("product_name", selectedProduct.getName());
                detailIntent.putExtra("product_price", selectedProduct.getPrice());
                detailIntent.putExtra("product_image", selectedProduct.getImageResId());

                // Jalankan Intent
                startActivity(detailIntent);
            }
        });
    }

    // Kelas Product dan ProductAdapter tetap sama seperti sebelumnya
    public class Product {
        private String name;
        private double price;
        private int imageResId;

        public Product(String name, double price, int imageResId) {
            this.name = name;
            this.price = price;
            this.imageResId = imageResId;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public int getImageResId() {
            return imageResId;
        }
    }

    public class ProductAdapter extends BaseAdapter {
        private ArrayList<Product> productList;

        public ProductAdapter(ArrayList<Product> productList) {
            this.productList = productList;
        }

        @Override
        public int getCount() {
            return productList.size();
        }

        @Override
        public Object getItem(int position) {
            return productList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            }

            ImageView productImage = convertView.findViewById(R.id.product_image);
            TextView productName = convertView.findViewById(R.id.product_name);
            TextView productPrice = convertView.findViewById(R.id.product_price);

            Product currentProduct = productList.get(position);

            productImage.setImageResource(currentProduct.getImageResId());
            productName.setText(currentProduct.getName());
            productPrice.setText("Rp " + String.format("%,.0f", currentProduct.getPrice()));

            return convertView;
        }

    }
}