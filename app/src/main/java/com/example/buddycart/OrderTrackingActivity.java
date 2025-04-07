package com.example.buddycart;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class OrderTrackingActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView tvBackToShopping, tvChatWithRider, tvEnableNotifications;
    private TextView tvWeGotYourOrder, tvEstimatedArrival, tvEditOrModify, tvGetHelp;
    private TextView tvSurveyPrompt, tvEmojiSad, tvEmojiNeutral, tvEmojiHappy;
    private Button btnSwitchToDriver;

    private GoogleMap mMap;
    private String deliveryAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);

        // Retrieve the address from Intent (passed from PaymentActivity)
        deliveryAddress = getIntent().getStringExtra("deliveryAddress");
        if (deliveryAddress == null) {
            deliveryAddress = "1600 Amphitheatre Parkway, Mountain View, CA"; // fallback example
        }

        // Bind views
        tvBackToShopping = findViewById(R.id.tvBackToShopping);
        tvChatWithRider = findViewById(R.id.tvChatWithRider);
        tvEnableNotifications = findViewById(R.id.tvEnableNotifications);
        btnSwitchToDriver = findViewById(R.id.btnSwitchToDriver);

        tvWeGotYourOrder = findViewById(R.id.tvWeGotYourOrder);
        tvEstimatedArrival = findViewById(R.id.tvEstimatedArrival);
        tvEditOrModify = findViewById(R.id.tvEditOrModify);
        tvGetHelp = findViewById(R.id.tvGetHelp);

        tvSurveyPrompt = findViewById(R.id.tvSurveyPrompt);
        tvEmojiSad = findViewById(R.id.tvEmojiSad);
        tvEmojiNeutral = findViewById(R.id.tvEmojiNeutral);
        tvEmojiHappy = findViewById(R.id.tvEmojiHappy);

        // Set up map fragment
        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.mapFragmentContainer);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.mapFragmentContainer, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        // Example: handle clicks
        tvBackToShopping.setOnClickListener(v -> {
            // Go back to main shopping screen or open your MainActivity
            finish();
        });

        tvChatWithRider.setOnClickListener(v -> {
            // Placeholder for a chat feature
            Toast.makeText(this, "Chat feature not implemented yet.", Toast.LENGTH_SHORT).show();
        });

        tvEnableNotifications.setOnClickListener(v -> {
            // Placeholder for enabling notifications
            Toast.makeText(this, "Notifications enabled (placeholder).", Toast.LENGTH_SHORT).show();
        });

        tvEditOrModify.setOnClickListener(v -> {
            // Possibly open the cart or a modify screen
            Toast.makeText(this, "Edit/Modify order placeholder.", Toast.LENGTH_SHORT).show();
        });

        tvGetHelp.setOnClickListener(v -> {
            // Possibly open a help page or dial support
            Toast.makeText(this, "Get Help placeholder.", Toast.LENGTH_SHORT).show();
        });

        // Survey clicks
        tvEmojiSad.setOnClickListener(v -> {
            Toast.makeText(this, "Selected Sad 😞", Toast.LENGTH_SHORT).show();
        });
        tvEmojiNeutral.setOnClickListener(v -> {
            Toast.makeText(this, "Selected Neutral 😐", Toast.LENGTH_SHORT).show();
        });
        tvEmojiHappy.setOnClickListener(v -> {
            Toast.makeText(this, "Selected Happy 😊", Toast.LENGTH_SHORT).show();
        });

        // Set up driver view button click
        btnSwitchToDriver.setOnClickListener(v -> {
            Intent intent = new Intent(OrderTrackingActivity.this, CompleteCustomerDelivery.class);
            // Pass all the data received from PaymentActivity
            intent.putExtra("deliveryAddress", deliveryAddress);
            intent.putExtra("deliveryInstructions", getIntent().getStringExtra("deliveryInstructions"));
            intent.putExtra("phoneNumber", getIntent().getStringExtra("phoneNumber"));
            intent.putExtra("orderSummary", getIntent().getStringExtra("orderSummary"));
            intent.putExtra("orderTotal", getIntent().getStringExtra("orderTotal"));
            startActivity(intent);
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Attempt to geocode the address and move the camera
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(deliveryAddress, 1);
            if (addresses != null && !addresses.isEmpty()) {
                double lat = addresses.get(0).getLatitude();
                double lng = addresses.get(0).getLongitude();

                // Move camera to the geocoded location
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new com.google.android.gms.maps.model.LatLng(lat, lng), 14f
                ));
            } else {
                Toast.makeText(this, "Could not find location for: " + deliveryAddress, Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error geocoding address: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
