package com.shiwangapp.homepagesih.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shiwangapp.homepagesih.R;

public class SOSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);
        //getSupportActionBar().setTitle("SOS");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setSelectedItemId(R.id.bottom_placeholder);

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

//        Button btnSOS = findViewById(R.id.fab);
//        btnSOS.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.plcoding.bluetoothchat");
//                if (launchIntent != null) {
//                    Log.d("LaunchIntent", "Launch intent created successfully.");
//                    startActivity(launchIntent);
//                } else {
//                    Log.e("LaunchIntent", "Failed to create launch intent.");
//                    Toast.makeText(getApplicationContext(), "App not installed", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

}