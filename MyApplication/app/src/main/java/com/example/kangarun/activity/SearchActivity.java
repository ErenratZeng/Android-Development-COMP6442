package com.example.kangarun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kangarun.R;
import com.example.kangarun.User;
import com.example.kangarun.UserListener;
import com.example.kangarun.adapter.UserAdapter;
import com.example.kangarun.databinding.ActivitySearchBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements UserListener {
    private ActivitySearchBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String query = getIntent().getStringExtra("query");
        binding.searchView.setQuery(query, false);
        searchUsers(query);


        // Search Again
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(), "Searching" + query, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.putExtra("query", query);
                finish();
                startActivity(intent);
                // Call the  api
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

    }


    private void searchUsers(String query) {
        List<User> users = new ArrayList<>();
        users = MainActivity.tree.searchPartial(query);

        if (!users.isEmpty()) {
            for (User user : users){
                Log.d("treeRet", user.getUsername());
            }
            UserAdapter adapter = new UserAdapter(users, this);
            binding.userRecyclerView.setAdapter(adapter);
            binding.userRecyclerView.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "Search success", Toast.LENGTH_SHORT).show();
        } else {
            // Handle case where no users are found or list is empty
            binding.userRecyclerView.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Search No result", Toast.LENGTH_SHORT).show();
        }
    }


    // Go to profile if click the user
    @Override
    public void onUserClicked(User user) {
        Intent intent = new Intent(getApplicationContext(), FriendProfileActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}