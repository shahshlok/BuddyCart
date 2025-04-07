package com.example.buddycart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    // Header
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

        // ============= HEADER =============
        ivBack = findViewById(R.id.ivBack);
        tvTitle = findViewById(R.id.tvTitle);
        ivBack.setOnClickListener(v -> finish());  // Back button

        // ============= DELIVERY FIELDS =============
        address1 = findViewById(R.id.address1);
        address2 = findViewById(R.id.address2);
        etDeliveryInstructions = findViewById(R.id.etDeliveryInstructions);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);

        // ============= PAYMENT METHOD HEADERS & DETAILS =============
        tvPaymentCreditCard = findViewById(R.id.tvPaymentCreditCard);
        llCreditCardDetails = findViewById(R.id.llCreditCardDetails);
        tvPaymentApplePay = findViewById(R.id.tvPaymentApplePay);
        llApplePayDetails = findViewById(R.id.llApplePayDetails);
        tvPaymentPayPal = findViewById(R.id.tvPaymentPayPal);
        llPayPalDetails = findViewById(R.id.llPayPalDetails);

        // Credit Card fields
        etCcNumber = findViewById(R.id.etCcNumber);
        etCcExpiry = findViewById(R.id.etCcExpiry);
        etCcCvv = findViewById(R.id.etCcCvv);

        // Toggle Credit Card details
        tvPaymentCreditCard.setOnClickListener(v -> {
            llCreditCardDetails.setVisibility(
                    llCreditCardDetails.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        });

        // Toggle Apple Pay details
        tvPaymentApplePay.setOnClickListener(v -> {
            llApplePayDetails.setVisibility(
                    llApplePayDetails.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        });

        // Toggle PayPal details
        tvPaymentPayPal.setOnClickListener(v -> {
            llPayPalDetails.setVisibility(
                    llPayPalDetails.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        });

        // ============= SUMMARY & CHECKOUT =============
        orderSummary = findViewById(R.id.orderSummary);
        checkoutButton = findViewById(R.id.checkoutButton);

        // Retrieve the cart summary passed from ShoppingCart (if available)
        String summary = getIntent().getStringExtra("orderSummary");
        if (summary != null && !summary.isEmpty()) {
            orderSummary.setText(summary);
        }

        // Process payment only if Credit Card details are provided
        checkoutButton.setOnClickListener(v -> {
            // Check if Credit Card section is expanded
            if (llCreditCardDetails.getVisibility() == View.VISIBLE) {
                // Validate credit card fields
                String ccNumber = etCcNumber.getText().toString().trim();
                String ccExpiry = etCcExpiry.getText().toString().trim();
                String ccCvv = etCcCvv.getText().toString().trim();

                if (ccNumber.isEmpty() || ccExpiry.isEmpty() || ccCvv.isEmpty()) {
                    Toast.makeText(this, "Please fill in your credit card details", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Capture additional delivery info
                String addressLine1 = address1.getText().toString().trim();
                String addressLine2 = address2.getText().toString().trim();
                String deliveryInstr = etDeliveryInstructions.getText().toString().trim();
                String phoneNumber = etPhoneNumber.getText().toString().trim();

                // Process payment (for now, simply display a Toast with some info)
                Toast.makeText(this, "Payment Processed!\n" +
                        "Phone: " + phoneNumber + "\n" +
                        "Instructions: " + deliveryInstr, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Please select Credit Card (for now) and fill details", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
