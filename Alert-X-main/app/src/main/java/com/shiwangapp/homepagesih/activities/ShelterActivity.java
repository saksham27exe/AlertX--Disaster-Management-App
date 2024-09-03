package com.shiwangapp.homepagesih.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shiwangapp.homepagesih.Adapters.UserAdapter;
import com.shiwangapp.homepagesih.Adapters.UserModel;
import com.shiwangapp.homepagesih.R;
import com.shiwangapp.homepagesih.location.LatLong;

import java.util.ArrayList;
import java.util.List;

public class ShelterActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap myMap;
    private Spinner spinnerTextSize, spinnerTextSize1;
    private static final int PERMISSION_CODE = 103;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter); // Corrected layout file name

        spinnerTextSize = findViewById(R.id.spinner1);
        spinnerTextSize1 = findViewById(R.id.spinner2);

        String[] textSizes = getResources().getStringArray(R.array.type_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, textSizes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTextSize.setAdapter(adapter);

        String[] textSizes2 = getResources().getStringArray(R.array.resources_array);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, textSizes2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTextSize1.setAdapter(adapter2);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setSelectedItemId(R.id.bottom_shelter);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.bottom_home) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.bottom_shelter) {
                startActivity(new Intent(getApplicationContext(), ShelterActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.bottom_tips) {
                startActivity(new Intent(getApplicationContext(), TipsActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.bottom_profile) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.bottom_placeholder) {
                //////


                //////
                return true;
            }
            return false;

        });



    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;

        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_CODE);
        } else {
            client.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    LatLng currentLoc = new LatLng(location.getLatitude(), location.getLongitude());
                    myMap.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                            .position(currentLoc)
                            .title("You are here")
                            .draggable(true));
                    myMap.moveCamera(CameraUpdateFactory.newLatLng(currentLoc));
                    myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 15));
                } else {
                    Toast.makeText(ShelterActivity.this, "Location not available", Toast.LENGTH_SHORT).show();
                }
            });
        }

        ArrayList<LatLng> ll = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("contactForm").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ll.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        LatLong i = snapshot1.getValue(LatLong.class);
                        if (i != null) {
                            String s = i.getName();
                            LatLng t = new LatLng(Double.parseDouble(i.getLatitude()), Double.parseDouble(i.getLongitude()));
                            myMap.addMarker(new MarkerOptions().position(t).title(s));
                            Log.i("NewMarker", "onDataChange: " + t + " " + s);
                            ll.add(t);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ShelterActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                Log.e("FirebaseError", "onCancelled: " + error.getMessage());
            }
        });

        // Sample markers
        LatLng sharda = new LatLng(28.4731, 77.4829);
        LatLng niet = new LatLng(28.4629, 77.4907);
        LatLng fortis = new LatLng(28.6187, 77.3726);
        LatLng kingInst = new LatLng(13.012442, 80.217615);
        LatLng svcet = new LatLng(13.2718, 79.1187);
        LatLng rvsHosp = new LatLng(13.2718, 79.11986);

        myMap.addMarker(new MarkerOptions().position(sharda).title("Sharda Hospital"));
        myMap.addMarker(new MarkerOptions().position(niet).title("NIET"));
        myMap.addMarker(new MarkerOptions().position(fortis).title("Fortis Hospital"));
        myMap.addMarker(new MarkerOptions().position(kingInst).title("King Institute of Preventive Medicine & Research"));
        myMap.addMarker(new MarkerOptions().position(svcet).title("Sri Venkateswara College of Engineering Technology, Chittoor, Andhra Pradesh, India"));
        myMap.addMarker(new MarkerOptions().position(rvsHosp).title("RVS Hospitals, Chittoor, Andhra Pradesh, India"));

        myMap.setOnMarkerClickListener(marker -> {
            Toast.makeText(ShelterActivity.this, marker.getTitle(), Toast.LENGTH_SHORT).show();
            return false;
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, try to get location again
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    onMapReady(myMap);
                }
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
