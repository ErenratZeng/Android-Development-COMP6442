package com.example.kangarun.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.kangarun.R;
import com.example.kangarun.activity.fragments.FriendsFragment;
import com.example.kangarun.activity.fragments.ProfileFragment;
import com.example.kangarun.activity.fragments.SearchFragment;
import com.example.kangarun.activity.fragments.MapsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavMenuActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);

        fragmentManager = getSupportFragmentManager();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int id = item.getItemId();

                if (id == R.id.navigation_search) {
                    selectedFragment = new SearchFragment();
                } else if (id == R.id.navigation_sports) {
                    selectedFragment = new MapsFragment();
                } else if (id == R.id.navigation_friends) {
                    selectedFragment = new FriendsFragment();
                } else if (id == R.id.navigation_profile) {
                    selectedFragment = new ProfileFragment();
                }

                if (selectedFragment != null) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                }
                return true;
            }
        });

        // 初始化时加载默认 Fragment
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, new SearchFragment())
                    .commit();
        }
    }
}
