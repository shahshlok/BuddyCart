package com.example.buddycart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItems;

    public CartAdapter(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        holder.bind(item, position);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemName;
        private TextView tvItemPrice;
        private TextView tvQuantity;
        private Button btnEdit;
        private Button btnDelete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemPrice = itemView.findViewById(R.id.tvItemPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        public void bind(CartItem item, int position) {
            // Set the item details
            tvItemName.setText(item.getName());
            tvItemPrice.setText(item.getPrice());
            tvQuantity.setText(String.valueOf(item.getQuantity()));
            
            // Setup click listeners for edit and delete buttons
            btnEdit.setOnClickListener(v -> {
                Toast.makeText(v.getContext(), "Edit item: " + item.getName(), Toast.LENGTH_SHORT).show();
                // Add your edit logic here
            });
            
            btnDelete.setOnClickListener(v -> {
                Toast.makeText(v.getContext(), "Delete item: " + item.getName(), Toast.LENGTH_SHORT).show();
                // Remove the item from the list
                cartItems.remove(position);
                // Notify the adapter that an item has been removed
                notifyItemRemoved(position);
                // Notify the adapter that all subsequent items have shifted positions
                notifyItemRangeChanged(position, cartItems.size());
            });
        }
    }
} 