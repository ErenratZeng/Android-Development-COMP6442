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
import com.example.kangarun.utils.Parser;
import com.example.kangarun.utils.Tokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        boolean invalid = false;
        List<User> users = new ArrayList<>();
        // Try tokenize
        Map<String, String> tokens = Tokenizer.tokenize(query);
        if (!query.contains("=")) {
            users = MainActivity.tree.searchPartial(query);
        } else {
            tokens = Tokenizer.tokenize(query);
            if (tokens == null) {
                invalid = true;
            } else {
                Map<String, String> parsed = Parser.parse(tokens);
                if (parsed == null) {
                    invalid = true;
                } else {
                    users = MainActivity.tree.searchToken(parsed);
                }
            }
        }


        if (!users.isEmpty()) {
            for (User user : users) {
                Log.d("treeRet", user.getUsername());
            }
            UserAdapter adapter = new UserAdapter(users, this);
            binding.userRecyclerView.setAdapter(adapter);
            binding.userRecyclerView.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "Search <"+query+"> success", Toast.LENGTH_SHORT).show();
        } else {
            // Handle case where no users are found or list is empty
            binding.userRecyclerView.setVisibility(View.GONE);
            if (invalid){
                Toast.makeText(getApplicationContext(), "Expression <" + query +
                        "> is invalid, please check grammar", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Search <"+query+"> no result", Toast.LENGTH_SHORT).show();
            }
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