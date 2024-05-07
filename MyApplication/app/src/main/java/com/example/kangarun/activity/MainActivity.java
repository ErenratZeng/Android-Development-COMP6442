package com.example.kangarun.activity;

import android.content.Intent;
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
import com.example.kangarun.utils.UserAVLTree;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static UserAVLTree tree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_search) {
                selectedFragment = new SearchFragment();
            } else if (itemId == R.id.navigation_sports) {
                selectedFragment = new SportsFragment();
            } else if (itemId == R.id.navigation_friends) {
                selectedFragment = new FriendsFragment();
            } else if (itemId == R.id.navigation_profile) {
                selectedFragment = new ProfileFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;
            }
            return false;
        });

        // 默认加载 ProfileFragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
        }
    }
}

