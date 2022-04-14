package com.melvin.googlemapdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    public Switch locationSwtch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //we cast our fragment into the SupportMapFragment
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fgm_googlemaps_id);
        mapFragment.getMapAsync(this);
        locationSwtch = findViewById(R.id.switch_id);

        locationSwtch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //requesting permissions to access location
                requestPermission();
            }
        });

    }
    /* End of onCreate Method */


    //For getting user permission
    private void requestPermission() {

        if (locationSwtch.isChecked()) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
            {
                //We will show the location
            }
            else {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]
                        { Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1){
            if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                //We will resume from there
            }
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }
}