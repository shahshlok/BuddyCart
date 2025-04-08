package com.example.buddycart;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardView btnStoreA = findViewById(R.id.btnStoreA);
        CardView btnStoreB = findViewById(R.id.btnStoreB);

        btnStoreA.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StoreAActivity.class);
            startActivity(intent);
        });

        btnStoreB.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StoreBActivity.class);
            startActivity(intent);
        });
    }
}