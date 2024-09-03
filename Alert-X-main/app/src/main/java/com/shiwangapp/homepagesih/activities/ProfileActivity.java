package com.shiwangapp.homepagesih.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shiwangapp.homepagesih.R;

public class ProfileActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //getSupportActionBar().setTitle("Profile");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setSelectedItemId(R.id.bottom_profile);

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
                return true;
            } else if (itemId == R.id.bottom_placeholder) {
                startActivity(new Intent(getApplicationContext(), SOSActivity.class));
                finish();
                return true;
            }
            return false;
        });

        Button btn1 = (Button) findViewById(R.id.get_current_location);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)  {
                Toast.makeText(getBaseContext(), "Your Location is" , Toast.LENGTH_SHORT ).show();
                Toast.makeText(getBaseContext(), "",Toast.LENGTH_LONG);
                Toast.makeText(getBaseContext(), "FG85+CH Greater Noida, Uttar Pradesh",Toast.LENGTH_LONG).show();









            }
        });

        Button btn2 = (Button) findViewById(R.id.smstoken);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, sendingsms.class);
                startActivity(intent);
            }
        });
    }
}