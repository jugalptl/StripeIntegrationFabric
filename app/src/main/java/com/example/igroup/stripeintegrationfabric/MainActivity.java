package com.example.igroup.stripeintegrationfabric;

import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this);
        setContentView(R.layout.activity_main);
    }

    public void submitCard(View view) {
        // TODO: replace with your own test key
        final String publishableApiKey = BuildConfig.DEBUG ?
                "pk_test_6pRNASCoBOKtIshFeQd4XMUh" :
                getString(R.string.com_stripe_publishable_key);

        TextView cardNumberField = (TextView) findViewById(R.id.cardNumber);
        TextView monthField = (TextView) findViewById(R.id.month);
        TextView yearField = (TextView) findViewById(R.id.year);
        TextView cvcField = (TextView) findViewById(R.id.cvc);

        Card card = new Card(cardNumberField.getText().toString(),
                Integer.valueOf(monthField.getText().toString()),
                Integer.valueOf(yearField.getText().toString()),
                cvcField.getText().toString());

        Stripe stripe = new Stripe();
        stripe.createToken(card, publishableApiKey, new TokenCallback() {
                       public void onError(Exception error) {
                Log.d("Stripe", error.getLocalizedMessage());
            }

            @Override
            public void onSuccess(Token token) {
                Toast.makeText(
                        getApplicationContext(),
                        "Token created: " + token.getId(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }


}
