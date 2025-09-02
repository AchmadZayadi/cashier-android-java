package com.android.automationtest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.automationtest.R;
import com.android.automationtest.response.productListResponse;

import java.text.DecimalFormat;
import java.util.List;

public class OrderedProductAdapter extends RecyclerView.Adapter<OrderedProductAdapter.ProductViewHolder> {

    private List<productListResponse> productList;

    public OrderedProductAdapter(List<productListResponse> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_ordered_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        productListResponse product = productList.get(position);
        DecimalFormat currencyFormat = new DecimalFormat("Rp #,##0");

        // Set data ke elemen UI
        holder.productImage.setImageResource(product.getImageResId());
        holder.productName.setText(product.getName());
        holder.productQuantity.setText("x" + product.getQuantity());

        // Hitung dan tampilkan harga per item (harga * kuantitas)
        double itemTotalPrice = product.getPrice() * product.getQuantity();
        holder.productPrice.setText(currencyFormat.format(itemTotalPrice));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productQuantity;
        TextView productPrice;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            productPrice = itemView.findViewById(R.id.product_price);
        }
    }
}