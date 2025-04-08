package com.example.buddycart;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> products;

    public ProductAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProductImage;
        TextView tvProductName;
        TextView tvProductPrice;
        Button btnAddToCart;

        ProductViewHolder(View itemView) {
            super(itemView);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }

        void bind(Product product) {
            tvProductName.setText(product.getName());
            tvProductPrice.setText(String.format(Locale.getDefault(), "$%.2f", product.getPrice()));
            ivProductImage.setImageResource(product.getImageResource());

            // Set click listener for the entire item to open product details
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
                intent.putExtra("product_name", product.getName());
                intent.putExtra("product_price", String.format(Locale.getDefault(), "$%.2f", product.getPrice()));
                intent.putExtra("product_image", product.getImageResource());
                intent.putExtra("product_description", product.getDescription());
                v.getContext().startActivity(intent);
            });

            // Set click listener for the Add to Cart button
            btnAddToCart.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), ShoppingCart.class);
                intent.putExtra("product_name", product.getName());
                intent.putExtra("product_price", String.format(Locale.getDefault(), "$%.2f", product.getPrice()));
                intent.putExtra("product_quantity", 1);
                v.getContext().startActivity(intent);
            });
        }
    }
} 