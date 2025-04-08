package com.example.buddycart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button btnGoToShoppingCart;
    private Button btnViewProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the buttons

        // Initialize the button
        btnGoToShoppingCart = findViewById(R.id.btnGoToShoppingCart);
        btnViewProduct = findViewById(R.id.btnViewProduct);

        // Set onClickListener for the shopping cart button
        if (btnGoToShoppingCart != null) {
            btnGoToShoppingCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start the ShoppingCart activity
                    Intent intent = new Intent(MainActivity.this, ShoppingCart.class);
                    startActivity(intent);
                }
            });
        }

        // Set onClickListener for the view product button
        if (btnViewProduct != null) {
            btnViewProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start the ProductDetailActivity
                    Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
                    startActivity(intent);
                }
            });
        }

    }
}