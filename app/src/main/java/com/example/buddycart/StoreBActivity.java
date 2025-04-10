package com.example.buddycart;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class StoreBActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_b);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create sample product list for NoFrills
        List<Product> products = new ArrayList<>();
        products.add(new Product(
            "No Name Apple Juice",
            3.49,
            R.drawable.nofrills_juice,
            "100% pure apple juice from concentrate. 2L bottle"
        ));
        products.add(new Product(
            "No Name Potato Chips",
            1.99,
            R.drawable.nofrills_chips,
            "Classic salted potato chips. Large family size bag"
        ));
        products.add(new Product(
            "Fresh Strawberries",
            4.99,
            R.drawable.nofrills_strawberries,
            "Sweet and juicy strawberries. 1lb package"
        ));
        products.add(new Product(
            "No Name Pasta",
            1.29,
            R.drawable.nofrills_pasta,
            "Spaghetti pasta. 500g package"
        ));
        products.add(new Product(
            "Fresh Ground Nuts",
            6.99,
            R.drawable.nofrills_nuts,
            "Lean ground Nuts. 1lb package"
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