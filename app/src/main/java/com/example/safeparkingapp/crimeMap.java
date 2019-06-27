package com.example.safeparkingapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.opencsv.CSVReader;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class crimeMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private boolean mLocationPermissons = true;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("Location: ", location.toString());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (Build.VERSION.SDK_INT < 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        } else {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED)
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                mLocationPermissons = true;
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        // LatLng sydney = new LatLng(-34, 151);

        ArrayList<LatLng> list = new ArrayList<LatLng>();

        AssetManager assetManager = getAssets();
        try {
            BufferedReader fileReader = new BufferedReader(
                    new InputStreamReader(getAssets().open("crime data.csv"))
            );
            CSVReader csvReader = new CSVReader(fileReader);

            String[] mline;

            List<String[]> fileData = csvReader.readAll();

            for (String[] row: fileData.subList( 1, fileData.size()))
            {
                try {
                    double dataLon = Double.parseDouble(row[19]);
                    double dataLat = Double.parseDouble(row[20]);
                    list.add(new LatLng(dataLat, dataLon));
                }
                catch (NumberFormatException e)
                {
                    Log.d("Error", e.getMessage());
                }
            }

            /*while ((mline = csvReader.readNext()) != null) {
                for (String cell : mline) {
                    Log.d("THEDATAINTHEFILE", cell);
                }
            }*/
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("ERROR", e.getMessage());
            list.add(new LatLng(42.363073,  -83.070714));
            list.add(new LatLng(42.363425,  -83.073681));
            list.add(new LatLng(42.362364,  -83.06524));
            list.add(new LatLng(42.360359,  -83.06095));
            list.add(new LatLng(42.361332,  -83.070212));
            list.add(new LatLng(42.361104,  -83.070466));
            list.add(new LatLng(42.361023,  -83.068413));
            list.add(new LatLng(42.359946,  -83.066395));
            list.add(new LatLng(42.360334,  -83.072452));
            list.add(new LatLng(42.357478,  -83.065935));
        }

        int[] colors = {
                Color.rgb(102, 245, 0), // green
                Color.rgb(255, 0, 0)    // red
        };

        float[] startPoints = {
                0.1f, 1f
        };

        Gradient gradient = new Gradient(colors, startPoints);

        HeatmapTileProvider provider = new HeatmapTileProvider.Builder()
                .data(list)
                .gradient(gradient)
                .build();

        provider.setRadius(175);
        provider.setOpacity(50.0);
        TileOverlay mOverlay;
        mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(provider));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try
        {
            if (mLocationPermissons)
            {
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful())
                        {
                            Location currentLocation = (Location) task.getResult();
                            LatLng current = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 18));
                            Log.d("Worked", "It went");
                        }
                        else
                        {
                            Toast.makeText(crimeMap.this, "Unable to get current location", Toast.LENGTH_SHORT).show();
                            Log.d("ERROR", "Task Failed");
                        }
                    }
                });
            }
            else
            {
                Log.d("ERROR", "Permissions is false");
            }
        }
        catch (SecurityException e)
        {
            Log.d("ERROR", "Task Failed");
        }
    }

    public void send(View view) {
        Intent menuPage = new Intent(this, menu.class);

        startActivity(menuPage);
    }
}
