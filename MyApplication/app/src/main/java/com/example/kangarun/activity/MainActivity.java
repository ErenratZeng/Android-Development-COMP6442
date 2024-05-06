package com.example.kangarun.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.kangarun.R;
import com.example.kangarun.activity.fragments.FriendsFragment;
import com.example.kangarun.activity.fragments.ProfileFragment;
import com.example.kangarun.activity.fragments.SearchFragment;
import com.example.kangarun.activity.fragments.SportsFragment;
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
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.navigation_search:
                        selectedFragment = new SearchFragment();
                        break;
                    case R.id.navigation_profile:
                        selectedFragment = new ProfileFragment();
                        break;
                    case R.id.navigation_friends:
                        selectedFragment = new FriendsFragment();
                        break;
                    case R.id.navigation_sports:
                        selectedFragment = new SportsFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;
            }
        });

        // Setting initial fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchFragment()).commit();
    }
}
