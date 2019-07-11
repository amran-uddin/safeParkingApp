package com.example.safeparkingapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class menu extends AppCompatActivity {
    private static final String TAG = "menu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Log.d(TAG, "onCreate: Started");

        //First Image
        ImageView firstImage = (ImageView) findViewById(R.id.firstImage);
        int imageResources = getResources().getIdentifier("@drawable/parking", null, this.getPackageName());
        firstImage.setImageResource(imageResources);


       //Second Image
        ImageView image2 = (ImageView) findViewById(R.id.imageView2);
        int imageResources2 = getResources().getIdentifier("@drawable/app", null, this.getPackageName());
        firstImage.setImageResource(imageResources);





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
        Intent moveTo = new Intent(this, crimeMap.class);

        startActivity(moveTo);
    }

    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    public void sendToTop(View view) {

    }
}
