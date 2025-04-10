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
    private String productName;
    private String productPrice;
    private int productImage;
    private String productDescription;

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

        Intent intent = getIntent();
        if (intent != null) {
            productName = intent.getStringExtra("product_name");
            productPrice = intent.getStringExtra("product_price");
            productImage = intent.getIntExtra("product_image", android.R.color.darker_gray);
            productDescription = intent.getStringExtra("product_description");
        }

        initViews();
        
        setProductDetails();
        
        setupClickListeners();

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
        ivProductImage.setImageResource(productImage);
        
        tvProductName.setText(productName);
        
        tvProductPrice.setText(productPrice);
        
        tvProductDescription.setText(productDescription);
        
        tvQuantity.setText(String.valueOf(quantity));
        
        btnAddToCart.setText(getString(R.string.add_to_cart));
    }

    private void setupClickListeners() {
        btnDecrease.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                tvQuantity.setText(String.valueOf(quantity));
            }
        });

        btnIncrease.setOnClickListener(v -> {
            quantity++;
            tvQuantity.setText(String.valueOf(quantity));
        });

        btnAddToCart.setOnClickListener(v -> {
            Toast.makeText(ProductDetailActivity.this, quantity + " " + productName + " added to cart", Toast.LENGTH_SHORT).show();
            
            Intent intent = new Intent(ProductDetailActivity.this, ShoppingCart.class);
            
            intent.putExtra("product_name", productName);
            intent.putExtra("product_price", productPrice);
            intent.putExtra("product_quantity", quantity);
            
            startActivity(intent);
        });
    }

    public void setupCloseButton() {
        ivClose = findViewById(R.id.ivCloseProduct);
        ivClose.setOnClickListener(v -> finish());
    }
} 