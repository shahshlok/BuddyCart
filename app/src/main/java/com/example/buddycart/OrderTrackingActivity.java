        package com.example.buddycart;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class OrderTrackingActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView tvEditOrModify,btnSwitchToDriver;
    private GoogleMap mMap;
    private String deliveryAddress; // The address passed from PaymentActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);

        // Retrieve the delivery address from the Intent extras.
        deliveryAddress = getIntent().getStringExtra("deliveryAddress");
        if (deliveryAddress == null || deliveryAddress.isEmpty()) {
            // Fallback address if none is provided.
            deliveryAddress = "1600 Amphitheatre Parkway, Mountain View, CA";
        }

        // Bind the "Edit or Modify your Order" TextView and set its click listener
        tvEditOrModify = findViewById(R.id.tvEditOrModify);
        tvEditOrModify.setOnClickListener(v -> {
            // Navigate back to the ShoppingCart screen
            Intent intent = new Intent(OrderTrackingActivity.this, ShoppingCart.class);
            startActivity(intent);
            finish();
        });

        // Set up the Google Map fragment
        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.mapFragmentContainer);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.mapFragmentContainer, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        btnSwitchToDriver = findViewById(R.id.btnSwitchToDriver);
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

        // Use the Geocoder to get a location for the delivery address.
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(deliveryAddress, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                double lat = address.getLatitude();
                double lng = address.getLongitude();
                LatLng deliveryLatLng = new LatLng(lat, lng);

                // Add a marker (pin) on the map at the address
                mMap.addMarker(new MarkerOptions().position(deliveryLatLng).title("Delivery Location"));

                // Move the camera to show the approximate area (zoom level 12 shows a broader area)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(deliveryLatLng, 12f));
            } else {
                Toast.makeText(this, "Unable to determine location for: " + deliveryAddress, Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Toast.makeText(this, "Error retrieving location: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}