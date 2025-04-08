package com.example.buddycart;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class StoreAActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_a);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create sample product list for Walmart
        List<Product> products = new ArrayList<>();
        products.add(new Product(
            "Great Value Whole Milk",
            4.48,
            R.drawable.walmart_milk,
            "Fresh whole milk, perfect for drinking and cooking. 1 Gallon"
        ));
        products.add(new Product(
            "Fresh Bananas",
            2.99,
            R.drawable.walmart_bananas,
            "Fresh yellow bananas. Approximately 6-7 bananas per bunch"
        ));
        products.add(new Product(
            "Wonder Bread Classic White",
            3.48,
            R.drawable.walmart_bread,
            "Soft white bread, perfect for sandwiches. 20 oz loaf"
        ));
        products.add(new Product(
            "Organic Large Brown Eggs",
            5.97,
            R.drawable.walmart_eggs,
            "Farm fresh organic brown eggs. 12 count"
        ));
        products.add(new Product(
            "Great Value Chocolate Chip Cookies",
            2.98,
            R.drawable.walmart_cookies,
            "Delicious chocolate chip cookies. Family size pack"
        ));

        adapter = new ProductAdapter(products);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
} 