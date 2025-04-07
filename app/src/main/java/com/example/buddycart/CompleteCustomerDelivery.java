package com.example.buddycart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CompleteCustomerDelivery extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView timeRemainingText;
    private CountDownTimer timer;
    
    // Variables to store real data from previous activities
    private String customerAddress;
    private String customerPhone;
    private String deliveryInstructions;
    private String orderSummary;
    private String orderTotal;
    private LatLng deliveryLocation;
    private final int INITIAL_TIME_REMAINING = 600; // 10 minutes in seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_customer_delivery);

        // Get data from intent
        Intent intent = getIntent();
        customerAddress = intent.getStringExtra("deliveryAddress");
        customerPhone = intent.getStringExtra("phoneNumber");
        deliveryInstructions = intent.getStringExtra("deliveryInstructions");
        orderSummary = intent.getStringExtra("orderSummary");
        orderTotal = intent.getStringExtra("orderTotal");

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

        // Set real data
        deliveryToText.setText(String.format("Delivering to: %s", customerAddress));
        instructionsText.setText(deliveryInstructions != null ? deliveryInstructions : "No special instructions provided");

        // Setup countdown timer
        setupTimer();

        // Setup button clicks
        setupButtonListeners();

        // Geocode the address to get coordinates
        geocodeAddress();
    }

    private void geocodeAddress() {
        if (customerAddress != null && !customerAddress.isEmpty()) {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocationName(customerAddress, 1);
                if (addresses != null && !addresses.isEmpty()) {
                    double lat = addresses.get(0).getLatitude();
                    double lng = addresses.get(0).getLongitude();
                    deliveryLocation = new LatLng(lat, lng);
                    
                    // If map is ready, update it
                    if (mMap != null) {
                        updateMap();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error finding location: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
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
            if (deliveryLocation != null) {
                String uri = "google.navigation:q=" + deliveryLocation.latitude + "," + deliveryLocation.longitude;
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            } else {
                Toast.makeText(this, "Delivery location not available", Toast.LENGTH_SHORT).show();
            }
        });

        // Items button
        findViewById(R.id.itemsButton).setOnClickListener(v -> {
            if (orderSummary != null && !orderSummary.isEmpty()) {
                showOrderItemsDialog();
            } else {
                Toast.makeText(this, "Order details not available", Toast.LENGTH_SHORT).show();
            }
        });

        // Call button
        findViewById(R.id.callButton).setOnClickListener(v -> {
            if (customerPhone != null && !customerPhone.isEmpty()) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + customerPhone));
                startActivity(callIntent);
            } else {
                Toast.makeText(this, "Phone number not available", Toast.LENGTH_SHORT).show();
            }
        });

        // Message button
        findViewById(R.id.messageButton).setOnClickListener(v -> {
            if (customerPhone != null && !customerPhone.isEmpty()) {
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setData(Uri.parse("sms:" + customerPhone));
                startActivity(smsIntent);
            } else {
                Toast.makeText(this, "Phone number not available", Toast.LENGTH_SHORT).show();
            }
        });

        // Confirm arrival button
        findViewById(R.id.confirmArrivalButton).setOnClickListener(v -> {
            Toast.makeText(this, "Delivery confirmed!", Toast.LENGTH_LONG).show();
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
        if (deliveryLocation != null) {
            updateMap();
        }
    }

    private void updateMap() {
        if (mMap != null && deliveryLocation != null) {
            // Add a marker for the delivery location
            mMap.addMarker(new MarkerOptions()
                    .position(deliveryLocation)
                    .title("Delivery Location"));

            // Move camera to delivery location
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(deliveryLocation, 15));
        }
    }

    private void showOrderItemsDialog() {
        // Create the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_order_items, null);
        builder.setView(dialogView);

        // Get references to views
        TextView tvOrderItems = dialogView.findViewById(R.id.tvOrderItems);
        Button btnClose = dialogView.findViewById(R.id.btnClose);

        // Set the order items text
        tvOrderItems.setText(orderSummary);

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        
        // Set up close button
        btnClose.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}