package com.example.kangarun.activity;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.kangarun.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.kangarun.activity.fragments.SearchFragment;
import com.example.kangarun.activity.fragments.ProfileFragment;
import com.example.kangarun.activity.fragments.FriendsFragment;
import com.example.kangarun.activity.fragments.SportsFragment;
public class BottomNavMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.inflateMenu(R.menu.bottom_nav_menu);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.navigation_search: // 替换为实际的 ID 和 Fragment
                        selectedFragment = new SearchFragment();
                        break;
                    case R.id.navigation_sports: // 替换为实际的 ID 和 Fragment
                        selectedFragment = new SportsFragment();
                        break;
                    case R.id.navigation_friends:
                        selectedFragment = new FriendsFragment();
                        break;
                    case R.id.navigation_profile:
                        selectedFragment = new ProfileFragment();
                        break;
                    // 添加其他 case
                }
                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                }
                return true;
            }
        });

        // 初始化时加载默认 Fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FriendsFragment()).commit();
    }
}
