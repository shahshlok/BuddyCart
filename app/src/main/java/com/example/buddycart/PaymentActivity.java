package com.example.buddycart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private EditText etCardNumber, etExpiryDate, etCvv;

    // Summary and checkout
    private TextView orderSummary, tvOrderTotal;
    private Button checkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // ===== HEADER SETUP =====
        ivBack = findViewById(R.id.ivBack);
        tvTitle = findViewById(R.id.tvTitle);
        ivBack.setOnClickListener(v -> finish());  // Return to ShoppingCart

        // ===== DELIVERY FIELDS =====
        address1 = findViewById(R.id.address1);
        address2 = findViewById(R.id.address2);
        etDeliveryInstructions = findViewById(R.id.etDeliveryInstructions);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);

        // ===== PAYMENT METHODS & DETAILS =====
        tvPaymentCreditCard = findViewById(R.id.tvPaymentCreditCard);
        llCreditCardDetails = findViewById(R.id.llCreditCardDetails);
        tvPaymentApplePay = findViewById(R.id.tvPaymentApplePay);
        llApplePayDetails = findViewById(R.id.llApplePayDetails);
        tvPaymentPayPal = findViewById(R.id.tvPaymentPayPal);
        llPayPalDetails = findViewById(R.id.llPayPalDetails);

        // ===== CREDIT CARD FIELDS (with XML constraints) =====
        etCardNumber = findViewById(R.id.etCardNumber);
        etExpiryDate = findViewById(R.id.etExpiryDate);
        etCvv = findViewById(R.id.etCvv);

        // Toggle Credit Card details
        tvPaymentCreditCard.setOnClickListener(v -> {
            llCreditCardDetails.setVisibility(llCreditCardDetails.getVisibility() == LinearLayout.GONE ?
                    LinearLayout.VISIBLE : LinearLayout.GONE);
        });
        // Toggle Apple Pay details
        tvPaymentApplePay.setOnClickListener(v -> {
            llApplePayDetails.setVisibility(llApplePayDetails.getVisibility() == LinearLayout.GONE ?
                    LinearLayout.VISIBLE : LinearLayout.GONE);
        });
        // Toggle PayPal details
        tvPaymentPayPal.setOnClickListener(v -> {
            llPayPalDetails.setVisibility(llPayPalDetails.getVisibility() == LinearLayout.GONE ?
                    LinearLayout.VISIBLE : LinearLayout.GONE);
        });

        // ===== SUMMARY & CHECKOUT =====
        orderSummary = findViewById(R.id.orderSummary);
        tvOrderTotal = findViewById(R.id.tvOrderTotal);
        checkoutButton = findViewById(R.id.checkoutButton);

        // Retrieve order summary and total from intent extras (if available)
        String summary = getIntent().getStringExtra("orderSummary");
        if (summary != null && !summary.isEmpty()) {
            orderSummary.setText(summary);
        }
        String orderTotal = getIntent().getStringExtra("orderTotal");
        if (orderTotal != null && !orderTotal.isEmpty()) {
            tvOrderTotal.setText("Total: " + orderTotal);
        }

        // Checkout button listener with runtime validation for credit card inputs and phone number
        checkoutButton.setOnClickListener(v -> {
            Log.d(TAG, "Checkout button pressed");

            // Ensure Credit Card details are visible; if not, prompt the user.
            if (llCreditCardDetails.getVisibility() != LinearLayout.VISIBLE) {
                llCreditCardDetails.setVisibility(LinearLayout.VISIBLE);
                Toast.makeText(PaymentActivity.this, "Please fill in your credit card details", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate credit card number (16 digits)
            String cardNumber = etCardNumber.getText().toString().trim();
            if (cardNumber.length() < 16) {
                Toast.makeText(this, "Card number must be 16 digits", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate expiry date format (basic check: 5 characters with a slash)
            String expiryDate = etExpiryDate.getText().toString().trim();
            if (expiryDate.length() != 5 || !expiryDate.contains("/")) {
                Toast.makeText(this, "Expiry date must be in MM/YY format", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate CVV (minimum 3 digits)
            String cvv = etCvv.getText().toString().trim();
            if (cvv.length() < 3) {
                Toast.makeText(this, "CVV must be 3 or 4 digits", Toast.LENGTH_SHORT).show();
                return;
            }

            // Capture additional delivery info
            String addressLine1 = address1.getText().toString().trim();
            String addressLine2 = address2.getText().toString().trim();
            String deliveryInstr = etDeliveryInstructions.getText().toString().trim();
            String phoneNumber = etPhoneNumber.getText().toString().trim();

            // Validate phone number: ensure it is not more than 10 digits
            if (phoneNumber.length() > 10) {
                Toast.makeText(this, "Phone number must not exceed 10 digits", Toast.LENGTH_SHORT).show();
                return;
            }

            String fullAddress = addressLine1 + ", " + addressLine2;
            Log.d(TAG, "Full address: " + fullAddress);

            // Payment is processed; proceed to OrderTrackingActivity
            Intent intent = new Intent(PaymentActivity.this, OrderTrackingActivity.class);
            intent.putExtra("deliveryAddress", fullAddress);
            intent.putExtra("deliveryInstructions", deliveryInstr);
            intent.putExtra("phoneNumber", phoneNumber);
            intent.putExtra("orderSummary", orderSummary.getText().toString());
            intent.putExtra("orderTotal", tvOrderTotal.getText().toString().replace("Total: ", ""));
            Log.d(TAG, "Launching OrderTrackingActivity with extras.");
            startActivity(intent);
        });
    }
}