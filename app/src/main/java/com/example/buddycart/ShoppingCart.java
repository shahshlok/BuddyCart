package com.example.buddycart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart extends AppCompatActivity {

    private RecyclerView rvCartItems;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItems;
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

        // Initialize views
        rvCartItems = findViewById(R.id.rvCartItems);
        tvItemsCount = findViewById(R.id.tvItemsCount);
        tvTotal = findViewById(R.id.tvTotal);
        btnCheckout = findViewById(R.id.btnCheckout);
        ivClose = findViewById(R.id.ivClose);

        // Setup RecyclerView
        rvCartItems.setLayoutManager(new LinearLayoutManager(this));

        // Create dummy data
        createDummyData();

        // Setup adapter
        cartAdapter = new CartAdapter(cartItems);
        rvCartItems.setAdapter(cartAdapter);

        // Update UI elements
        updateCartSummary();

        // Setup checkout click listener
        btnCheckout.setOnClickListener(v -> {
            // Build order summary string
            StringBuilder summaryBuilder = new StringBuilder();
            for (CartItem item : cartItems) {
                double price = Double.parseDouble(item.getPrice().replace("$", ""));
                double subtotal = price * item.getQuantity();
                summaryBuilder.append(item.getName())
                        .append(" (x")
                        .append(item.getQuantity())
                        .append(") - $")
                        .append(String.format("%.2f", subtotal))
                        .append("\n");
            }
            String orderSummary = summaryBuilder.toString();

            // Calculate total price
            int totalItems = 0;
            double totalPrice = 0.0;
            for (CartItem item : cartItems) {
                totalItems += item.getQuantity();
                double price = Double.parseDouble(item.getPrice().replace("$", ""));
                totalPrice += price * item.getQuantity();
            }
            String totalString = "$" + String.format("%.2f", totalPrice);

            // Launch PaymentActivity with extras
            Intent intent = new Intent(ShoppingCart.this, PaymentActivity.class);
            intent.putExtra("orderSummary", orderSummary);
            intent.putExtra("orderTotal", totalString);
            startActivity(intent);
        });

        ivClose.setOnClickListener(v -> finish());
    }

    private void createDummyData() {
        cartItems = new ArrayList<>();
        cartItems.add(new CartItem("Apples", "$10.99", 2));
        cartItems.add(new CartItem("Banana", "$5.99", 1));
        cartItems.add(new CartItem("Bread", "$7.50", 5));
        cartItems.add(new CartItem("Milk", "$12.99", 2));
        cartItems.add(new CartItem("Cookies", "$3.99", 6));
        cartItems.add(new CartItem("Blueberries", "$9.99", 4));
        cartItems.add(new CartItem("Chocolate", "$15.99", 3));
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