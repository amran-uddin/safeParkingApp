package com.example.safeparkingapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void sendToMap(View view) {
        Intent moveTo = new Intent(this, MapsActivity.class);

        startActivity(moveTo);
    }

    public void sendToSafety(View view) {
        goToUrl ( "https://atpins.com/pdfs/online/driver/DriverSafetyTipParkingLots.pdf");
    }

    public void sendToCrimeMap(View view)
    {
        goToUrl ( "https://www.neighborhoodscout.com/mi/detroit/crime");
    }

    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    public void sendToTop(View view) {

    }
}
