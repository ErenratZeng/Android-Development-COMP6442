// MainActivity.java
package com.example.kangarun.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.kangarun.R;
import com.example.kangarun.activity.fragments.FriendsFragment;
import com.example.kangarun.activity.fragments.ProfileFragment;
import com.example.kangarun.activity.fragments.SearchFragment;
import com.example.kangarun.activity.fragments.MapsFragment;
import com.example.kangarun.utils.FirebaseUtil;
import com.example.kangarun.utils.UserAVLTree;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    public static UserAVLTree tree; // 声明为静态的 UserAVLTree 类型

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化 tree 并从 Firebase 加载用户数据
        tree = new UserAVLTree();
        FirebaseUtil.loadUsersIntoAVL(tree);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_search) {
                selectedFragment = new SearchFragment();
            } else if (itemId == R.id.navigation_sports) {
                selectedFragment = new MapsFragment();
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

