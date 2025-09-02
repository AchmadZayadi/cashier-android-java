package com.android.automationtest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.automationtest.R;
import com.android.automationtest.activity.OnQuantityChangeListener;
import com.android.automationtest.response.productListResponse;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class ProductAdapter  extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<productListResponse> productList;
    private OnQuantityChangeListener listener;
    private final DecimalFormat currencyFormat; // Dibuat sebagai variabel kelas

    public ProductAdapter(List<productListResponse> productList, OnQuantityChangeListener listener) {
        this.productList = productList;
        this.listener = listener;

        // Inisialisasi DecimalFormat sekali di konstruktor
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("id", "ID"));
        symbols.setGroupingSeparator('.');
        symbols.setMonetaryDecimalSeparator(',');
        this.currencyFormat = new DecimalFormat("Rp #,##0", symbols);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        productListResponse product = productList.get(position);
        holder.productName.setText(product.getName());
//        holder.productPrice.setText(String.valueOf(product.getPrice()));
        holder.productPrice.setText(currencyFormat.format(product.getPrice()));
        holder.productImage.setImageResource(product.getImageResId());

        // Atur kuantitas dari data produk
        holder.productQuantity.setText(String.valueOf(product.getQuantity()));

        // Atur OnClickListener untuk tombol plus dan minus
        holder.btnPlus.setOnClickListener(v -> {
            product.setQuantity(product.getQuantity() + 1);
            holder.productQuantity.setText(String.valueOf(product.getQuantity()));
            listener.onQuantityChanged(); // <-- Panggil
        });

        holder.btnMinus.setOnClickListener(v -> {

                if (product.getQuantity() == 0){

                }else {
                    product.setQuantity(product.getQuantity() - 1);
                    holder.productQuantity.setText(String.valueOf(product.getQuantity()));
                    // Panggil listener untuk update total di MainActivity
                    listener.onQuantityChanged();

                }

        });

        holder.itemView.setOnClickListener(v -> {
            product.setSelected(true);
        });
        // Set gambar, stock, dll.
    }



    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        TextView productQuantity;
        ImageView btnPlus,btnMinus;

        // Tambahkan elemen UI lainnya di sini

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            btnPlus = itemView.findViewById(R.id.btn_plus);
            btnMinus = itemView.findViewById(R.id.btn_minus);



            // Inisialisasi elemen UI lainnya
        }
    }
}