package com.example.kangarun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.kangarun.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Intent intent = null;

                if (id == R.id.navigation_search) {
                    intent = new Intent(MainActivity.this, SearchActivity.class);
                } else if (id == R.id.navigation_sports) {
                    intent = new Intent(MainActivity.this, ExerciseRecordActivity.class); // Replace with your actual activity class
                } else if (id == R.id.navigation_friends) {
                    intent = new Intent(MainActivity.this, FriendListActivity.class); // Replace with your actual activity class
                } else if (id == R.id.navigation_profile) {
                    intent = new Intent(MainActivity.this, UserProfileActivity.class); // Replace with your actual activity class
                }

                if (intent != null) {
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        // Ensure the first fragment is loaded by default (if not redirected to Search)
        if (savedInstanceState == null) {
            startActivity(new Intent(MainActivity.this, UserProfileActivity.class)); // Default activity
        }
    }
}


