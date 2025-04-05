package com.example.buddycart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class CompleteCustomerDelivery extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView timeRemainingText;
    private CountDownTimer timer;
    
    // Mock data - In real app, these would come from the backend
    private final String CUSTOMER_NAME = "John Doe";
    private final String CUSTOMER_PHONE = "1234567890";
    private final String DELIVERY_INSTRUCTIONS = "Enter through the front door, come to 2nd floor and my door is the first one on the left";
    private final LatLng DELIVERY_LOCATION = new LatLng(49.941, -119.395); // Kelowna coordinates
    private final int INITIAL_TIME_REMAINING = 600; // 10 minutes in seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_customer_delivery);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Initialize map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Initialize views
        timeRemainingText = findViewById(R.id.timeRemaining);
        TextView deliveryToText = findViewById(R.id.deliveryTo);
        TextView instructionsText = findViewById(R.id.instructions);
        Button confirmArrivalButton = findViewById(R.id.confirmArrivalButton);

        // Set mock data
        deliveryToText.setText(String.format("Delivering to: %s", CUSTOMER_NAME));
        instructionsText.setText(DELIVERY_INSTRUCTIONS);

        // Setup countdown timer
        setupTimer();

        // Setup button clicks
        setupButtonListeners();
    }

    private void setupTimer() {
        timer = new CountDownTimer(INITIAL_TIME_REMAINING * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                timeRemainingText.setText(String.format("Time Remaining: %d:%02d mins", minutes, seconds));
            }

            @Override
            public void onFinish() {
                timeRemainingText.setText("Time Remaining: 0:00 mins");
            }
        }.start();
    }

    private void setupButtonListeners() {
        // Navigate button
        findViewById(R.id.navigateButton).setOnClickListener(v -> {
            String uri = "google.navigation:q=" + DELIVERY_LOCATION.latitude + "," + DELIVERY_LOCATION.longitude;
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            }
        });

        // Items button
        findViewById(R.id.itemsButton).setOnClickListener(v -> 
            Toast.makeText(this, "Order items will be shown here", Toast.LENGTH_SHORT).show());

        // Call button
        findViewById(R.id.callButton).setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + CUSTOMER_PHONE));
            startActivity(callIntent);
        });

        // Message button
        findViewById(R.id.messageButton).setOnClickListener(v -> {
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setData(Uri.parse("sms:" + CUSTOMER_PHONE));
            startActivity(smsIntent);
        });

        // Confirm arrival button
        findViewById(R.id.confirmArrivalButton).setOnClickListener(v -> {
            Toast.makeText(this, "Delivery confirmed!", Toast.LENGTH_LONG).show();
            // In real app, this would update the backend and navigate to the next screen
            finish();
        });

        // Toolbar buttons
        findViewById(R.id.menuButton).setOnClickListener(v -> 
            Toast.makeText(this, "Menu clicked", Toast.LENGTH_SHORT).show());
        
        findViewById(R.id.notificationButton).setOnClickListener(v -> 
            Toast.makeText(this, "Notifications clicked", Toast.LENGTH_SHORT).show());
        
        findViewById(R.id.shareButton).setOnClickListener(v -> 
            Toast.makeText(this, "Share clicked", Toast.LENGTH_SHORT).show());
        
        findViewById(R.id.searchButton).setOnClickListener(v -> 
            Toast.makeText(this, "Search clicked", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker for the delivery location
        mMap.addMarker(new MarkerOptions()
                .position(DELIVERY_LOCATION)
                .title("Delivery Location"));

        // Move camera to delivery location
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DELIVERY_LOCATION, 15));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}