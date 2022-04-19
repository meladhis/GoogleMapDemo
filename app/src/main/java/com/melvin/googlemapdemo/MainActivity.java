package com.melvin.googlemapdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private GoogleMap google_Map;
    public Switch locationSwtch;
    private FusedLocationProviderClient fusedLocationClient;
    public Location myLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //we cast our fragment into the SupportMapFragment
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fgm_googlemaps_id);
        mapFragment.getMapAsync(this);
        locationSwtch = findViewById(R.id.switch_id);

        //Initilizing the fusedLocationClient variable
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);

        locationSwtch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //requesting permissions to access location
                requestPermission();
            }

        });

    }
    /* End of onCreate Method */

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        google_Map = googleMap;
        google_Map.getUiSettings().setMyLocationButtonEnabled(true);
        google_Map.setMyLocationEnabled(true);

    }


    //For getting user permission
    private void requestPermission() {

        if (locationSwtch.isChecked()) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
            {
                //We will show the location
                show_location();
            }
            else {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]
                        { Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1)
        {
            if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                //Showing the location

                show_location();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void show_location()
    {
        fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>()
        {
            @Override
            public void onComplete(@NonNull Task<Location> task)
            {
                if (task.isSuccessful())
                {
                    myLocation = task.getResult();
                    if (myLocation != null)
                    {
                        LatLng latilongitude = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                        google_Map.moveCamera(CameraUpdateFactory.newLatLngZoom(latilongitude, 30));

                    }

                }


            }
        });
    }


}