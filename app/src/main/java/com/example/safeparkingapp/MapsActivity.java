package com.example.safeparkingapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

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
        LatLng DetroitUC = new LatLng(42.361696, -83.069545);
        mMap.addMarker(new MarkerOptions().position(DetroitUC).title("Marker for the Urban Center").snippet("TEST WHAT DOES THIS DO"));


        //------------------Parking Locations-----------

        LatLng park1 = new LatLng(42.363073,  -83.070714);
        mMap.addMarker(new MarkerOptions().position(park1).title("park1").snippet("TEST WHAT DOES THIS DO"));

        LatLng park2 = new LatLng(42.363425,  -83.073681);
        mMap.addMarker(new MarkerOptions().position(park2).title("park2").snippet("TEST WHAT DOES THIS DO"));

        LatLng park4 = new LatLng(42.362364,  -83.06524);
        mMap.addMarker(new MarkerOptions().position(park4).title("park4").snippet("TEST WHAT DOES THIS DO"));

        LatLng park5 = new LatLng(42.360359,  -83.060951);
        mMap.addMarker(new MarkerOptions().position(park5).title("park5").snippet("TEST WHAT DOES THIS DO"));

        LatLng park6 = new LatLng(42.361332,  -83.070212);
        mMap.addMarker(new MarkerOptions().position(park6).title("park6").snippet("TEST WHAT DOES THIS DO"));

        LatLng park7 = new LatLng(42.361104,  -83.070466);
        mMap.addMarker(new MarkerOptions().position(park7).title("park7").snippet("TEST WHAT DOES THIS DO"));

        LatLng park8 = new LatLng(42.361023,  -83.068413);
        mMap.addMarker(new MarkerOptions().position(park8).title("park8").snippet("TEST WHAT DOES THIS DO"));

        LatLng park9 = new LatLng(42.359946,  -83.066395);
        mMap.addMarker(new MarkerOptions().position(park9).title("park9").snippet("TEST WHAT DOES THIS DO"));

        LatLng park10 = new LatLng(42.360334,  -83.072452);
        mMap.addMarker(new MarkerOptions().position(park10).title("park10").snippet("TEST WHAT DOES THIS DO"));

        LatLng park11 = new LatLng(42.357478,  -83.065935);
        mMap.addMarker(new MarkerOptions().position(park11).title("park11").snippet("TEST WHAT DOES THIS DO"));

        LatLng park12 = new LatLng(42.357131,  -83.063729);
        mMap.addMarker(new MarkerOptions().position(park12).title("park12").snippet("TEST WHAT DOES THIS DO"));

        LatLng park13 = new LatLng(42.358007,  -83.062366);
        mMap.addMarker(new MarkerOptions().position(park13).title("park13").snippet("TEST WHAT DOES THIS DO"));

        LatLng park14 = new LatLng(42.35615,  -83.072283);
        mMap.addMarker(new MarkerOptions().position(park14).title("park14").snippet("TEST WHAT DOES THIS DO"));

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
                            Toast.makeText(MapsActivity.this, "Unable to get current location", Toast.LENGTH_SHORT).show();
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
