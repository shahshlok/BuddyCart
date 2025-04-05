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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private Button btnGoToShoppingCart;

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

        // Initialize Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Write a test message to the database
        mDatabase.child("test").setValue("Hello, Firebase! again");

        // Initialize the button
        btnGoToShoppingCart = findViewById(R.id.btnGoToShoppingCart);

        // Set onClickListener for the button
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
    }
}