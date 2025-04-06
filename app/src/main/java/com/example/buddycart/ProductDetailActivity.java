package com.example.buddycart;

import android.content.Intent;
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

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView ivProductImage;
    private TextView tvProductName;
    private TextView tvProductPrice;
    private TextView tvProductDescription;
    private TextView tvQuantity;
    private Button btnDecrease;
    private Button btnIncrease;
    private Button btnAddToCart;

    private ImageView ivClose;
    
    private int quantity = 1;
    private String productName = "Chocolate";
    private String productPrice = "$15.99";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        initViews();
        
        // Set product details
        setProductDetails();
        
        // Set up click listeners
        setupClickListeners();

        // Set up close button
        setupCloseButton();
    }

    private void initViews() {
        ivProductImage = findViewById(R.id.ivProductImage);

        tvProductName = findViewById(R.id.tvProductName);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        tvProductDescription = findViewById(R.id.tvProductDescription);
        tvQuantity = findViewById(R.id.tvQuantity);
        btnDecrease = findViewById(R.id.btnDecrease);
        btnIncrease = findViewById(R.id.btnIncrease);
        btnAddToCart = findViewById(R.id.btnAddToCart);
    }

    private void setProductDetails() {
        // Set product image - already set in the layout XML
        ivProductImage.setImageResource(R.drawable.chocolate);

        
        // Set product name
        tvProductName.setText(productName);
        
        // Set product price
        tvProductPrice.setText(productPrice);
        
        // Set product description
        tvProductDescription.setText("Rich and creamy chocolate made from the finest cocoa beans. Perfect for snacking or baking your favorite desserts.");
        
        // Set initial quantity
        tvQuantity.setText(String.valueOf(quantity));
        
        // Set the add to cart button text
        btnAddToCart.setText(getString(R.string.add_to_cart));
    }

    private void setupClickListeners() {
        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1) {
                    quantity--;
                    tvQuantity.setText(String.valueOf(quantity));
                }
            }
        });

        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                tvQuantity.setText(String.valueOf(quantity));
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProductDetailActivity.this, quantity + " " + productName + " added to cart", Toast.LENGTH_SHORT).show();
                
                // Create an intent to open the ShoppingCart activity
                Intent intent = new Intent(ProductDetailActivity.this, ShoppingCart.class);
                
                // Pass the product details to the cart
                intent.putExtra("product_name", productName);
                intent.putExtra("product_price", productPrice);
                intent.putExtra("product_quantity", quantity);
                
                // Start the ShoppingCart activity
                startActivity(intent);
            }
        });
    }

    public void setupCloseButton(){
        ivClose = findViewById(R.id.ivCloseProduct);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                }
            });
    }
} 