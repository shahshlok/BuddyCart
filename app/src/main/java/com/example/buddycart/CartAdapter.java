package com.example.buddycart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItems;
    private ShoppingCart shoppingCart;


    public CartAdapter(List<CartItem> cartItems, ShoppingCart shoppingCart) {
        this.cartItems = cartItems;
        this.shoppingCart = shoppingCart;
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
        private Button btnDelete;
        private ImageView imageView;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemPrice = itemView.findViewById(R.id.tvItemPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            imageView = itemView.findViewById(R.id.imageView);
        }

        public void bind(CartItem item, int position) {
            tvItemName.setText(item.getName());
            tvItemPrice.setText(item.getPrice());
            tvQuantity.setText(String.valueOf(item.getQuantity()));

            int imageResource;
            switch (position % 7) {
                case 0:
                    imageResource = R.mipmap.apple1;
                    break;
                case 1:
                    imageResource = R.mipmap.banana;
                    break;
                case 2:
                    imageResource = R.mipmap.bread;
                    break;
                case 3:
                    imageResource = R.mipmap.milk;
                    break;
                case 4:
                    imageResource = R.mipmap.cookies;
                    break;
                case 5:
                    imageResource = R.mipmap.blueberries;
                    break;
                default:
                    imageResource = R.mipmap.chocolate;
                    break;
            }
            imageView.setImageResource(imageResource);
            
            btnDelete.setOnClickListener(v -> {
                Toast.makeText(v.getContext(), "Delete item: " + item.getName(), Toast.LENGTH_SHORT).show();
                if(shoppingCart != null){
                    shoppingCart.removeFromCart(position);
                }
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartItems.size());
            });
        }
    }
} 