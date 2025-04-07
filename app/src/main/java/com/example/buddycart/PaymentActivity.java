package com.example.buddycart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    private static final String TAG = "PaymentActivity";

    // Header views
    private ImageView ivBack;
    private TextView tvTitle;

    // Delivery fields
    private EditText address1, address2, etDeliveryInstructions, etPhoneNumber;

    // Payment method headers and details
    private TextView tvPaymentCreditCard, tvPaymentApplePay, tvPaymentPayPal;
    private LinearLayout llCreditCardDetails, llApplePayDetails, llPayPalDetails;
    private EditText etCcNumber, etCcExpiry, etCcCvv;

    // Summary and checkout
    private TextView orderSummary;
    private Button checkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // ===== HEADER SETUP =====
        ivBack = findViewById(R.id.ivBack);
        tvTitle = findViewById(R.id.tvTitle);
        ivBack.setOnClickListener(v -> finish());  // Back button returns to ShoppingCart

        // ===== DELIVERY FIELDS =====
        address1 = findViewById(R.id.address1);
        address2 = findViewById(R.id.address2);
        etDeliveryInstructions = findViewById(R.id.etDeliveryInstructions);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);

        // ===== PAYMENT METHODS =====
        tvPaymentCreditCard = findViewById(R.id.tvPaymentCreditCard);
        llCreditCardDetails = findViewById(R.id.llCreditCardDetails);
        tvPaymentApplePay = findViewById(R.id.tvPaymentApplePay);
        llApplePayDetails = findViewById(R.id.llApplePayDetails);
        tvPaymentPayPal = findViewById(R.id.tvPaymentPayPal);
        llPayPalDetails = findViewById(R.id.llPayPalDetails);

        // ===== CREDIT CARD FIELDS =====
        etCcNumber = findViewById(R.id.etCcNumber);
        etCcExpiry = findViewById(R.id.etCcExpiry);
        etCcCvv = findViewById(R.id.etCcCvv);

        // Toggle Credit Card details when its header is clicked
        tvPaymentCreditCard.setOnClickListener(v -> {
            if (llCreditCardDetails.getVisibility() == View.GONE) {
                llCreditCardDetails.setVisibility(View.VISIBLE);
            } else {
                llCreditCardDetails.setVisibility(View.GONE);
            }
        });

        // Toggle Apple Pay details when its header is clicked
        tvPaymentApplePay.setOnClickListener(v -> {
            if (llApplePayDetails.getVisibility() == View.GONE) {
                llApplePayDetails.setVisibility(View.VISIBLE);
            } else {
                llApplePayDetails.setVisibility(View.GONE);
            }
        });

        // Toggle PayPal details when its header is clicked
        tvPaymentPayPal.setOnClickListener(v -> {
            if (llPayPalDetails.getVisibility() == View.GONE) {
                llPayPalDetails.setVisibility(View.VISIBLE);
            } else {
                llPayPalDetails.setVisibility(View.GONE);
            }
        });

        // ===== SUMMARY & CHECKOUT =====
        orderSummary = findViewById(R.id.orderSummary);
        checkoutButton = findViewById(R.id.checkoutButton);

        // Retrieve order summary passed from ShoppingCart (if available)
        String summary = getIntent().getStringExtra("orderSummary");
        if (summary != null && !summary.isEmpty()) {
            orderSummary.setText(summary);
        }

        // Checkout button listener: process payment and navigate to OrderTrackingActivity
        checkoutButton.setOnClickListener(v -> {
            Log.d(TAG, "Checkout button pressed");
            Toast.makeText(PaymentActivity.this, "Checkout button pressed", Toast.LENGTH_SHORT).show();

            // Check if the Credit Card section is visible; if not, prompt the user.
            if (llCreditCardDetails.getVisibility() != View.VISIBLE) {
                llCreditCardDetails.setVisibility(View.VISIBLE);
                Toast.makeText(PaymentActivity.this, "Please fill in your credit card details", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Credit Card details section was not visible. Made it visible now.");
                return;
            }

            // Validate credit card fields
            String ccNumber = etCcNumber.getText().toString().trim();
            String ccExpiry = etCcExpiry.getText().toString().trim();
            String ccCvv = etCcCvv.getText().toString().trim();
            if (ccNumber.isEmpty() || ccExpiry.isEmpty() || ccCvv.isEmpty()) {
                Toast.makeText(PaymentActivity.this, "Please fill in your credit card details", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Credit Card details validation failed");
                return;
            }

            // Capture additional delivery info
            String addressLine1 = address1.getText().toString().trim();
            String addressLine2 = address2.getText().toString().trim();
            String deliveryInstr = etDeliveryInstructions.getText().toString().trim();
            String phoneNumber = etPhoneNumber.getText().toString().trim();

            // Build full address string (adjust formatting as needed)
            String fullAddress = addressLine1 + ", " + addressLine2;
            Log.d(TAG, "Full address: " + fullAddress);

            // Payment is considered processed successfully at this point.
            // Launch OrderTrackingActivity, passing necessary information.
            Intent intent = new Intent(PaymentActivity.this, OrderTrackingActivity.class);
            intent.putExtra("deliveryAddress", fullAddress);
            intent.putExtra("deliveryInstructions", deliveryInstr);
            intent.putExtra("phoneNumber", phoneNumber);
            intent.putExtra("orderSummary", orderSummary.getText().toString());
            Log.d(TAG, "Launching OrderTrackingActivity with extras.");
            startActivity(intent);
        });
    }
}
