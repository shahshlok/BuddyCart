package com.example.buddycart;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList; // Add ArrayList import

public class ShoppingCart extends AppCompatActivity {

    private RecyclerView rvCartItems;
    private CartAdapter cartAdapter;
    private ArrayList<CartItem> cartItems;
    
    private TextView tvItemsCount;
    private TextView tvTotal;
    private Button btnCheckout;
    private ImageView ivClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shopping_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rvCartItems = findViewById(R.id.rvCartItems);
        tvItemsCount = findViewById(R.id.tvItemsCount);
        tvTotal = findViewById(R.id.tvTotal);
        btnCheckout = findViewById(R.id.btnCheckout);
        ivClose = findViewById(R.id.ivClose);

        rvCartItems.setLayoutManager(new LinearLayoutManager(this));
        
        createCartData();
        
        handleProductAddition();

        cartAdapter = new CartAdapter(cartItems, this);
        rvCartItems.setAdapter(cartAdapter);

        updateCartSummary();
        
        btnCheckout.setOnClickListener(v -> {
            Toast.makeText(ShoppingCart.this, "Checkout clicked", Toast.LENGTH_SHORT).show();
        });
        
        ivClose.setOnClickListener(v -> {
            finish();
        });
    }

    private void createCartData() {
        cartItems = new ArrayList<>();

        addToCart(new CartItem("Cookies", "$3.99", 6));
        addToCart(new CartItem("Blueberries", "$9.99", 4));
        addToCart(new CartItem("Chocolate", "$15.99", 3));
    }
    
    private void handleProductAddition() {

        if (getIntent().hasExtra("product_name") && 
            getIntent().hasExtra("product_price") && 
            getIntent().hasExtra("product_quantity")) {
            
            String productName = getIntent().getStringExtra("product_name");
            String productPrice = getIntent().getStringExtra("product_price");
            int productQuantity = getIntent().getIntExtra("product_quantity", 1);

            boolean productFound = false;
            for (CartItem item : cartItems) {
                if (item.getName().equals(productName)) {
                    productFound = true;
                    break;
                }
            }
            

            if (!productFound) {
                addToCart(new CartItem(productName, productPrice, productQuantity));
                Toast.makeText(this, productQuantity + " " + productName + " added to cart", Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    private void addToCart(CartItem item) {
        cartItems.add(item);
    }
    

    
    public void removeFromCart(int position) {
        if (position >= 0 && position < cartItems.size()) {
            cartItems.remove(position); 
        }
        
        updateCartSummary();
    }
    
    private void updateCartSummary() {
        int totalItems = 0;
        double totalPrice = 0.0;
        
        for (CartItem item : cartItems) { 
            totalItems += item.getQuantity();
            double price = Double.parseDouble(item.getPrice().replace("$", ""));
            totalPrice += price * item.getQuantity();
        }
        
        tvItemsCount.setText("Items: " + totalItems);
        tvTotal.setText(String.format("Total: $%.2f", totalPrice));
    }
}
