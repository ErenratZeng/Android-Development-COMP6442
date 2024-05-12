package com.example.kangarun.activity;

import static com.example.kangarun.activity.LoginActivity.currentUser;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
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
import java.util.stream.Collectors;

public class SearchActivity extends AppCompatActivity implements UserListener {
    private ActivitySearchBinding binding;
    private List<User> userList = new ArrayList<>();
    private Button sortName;
    private Button sortEmail;
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sortName = findViewById(R.id.sortUsername);
        sortName.setOnClickListener(v -> {
            CharSequence currentDescription = sortName.getText();

            if (currentDescription.equals(getString(R.string.nameAscending))) {
                // Currently ascending, sort descending next
                userList.sort((u1, u2) -> u2.getUsername().compareToIgnoreCase(u1.getUsername()));
                sortName.setText(R.string.nameDescending); // Update icon to descending
            } else {
                // Currently descending, sort ascending next
                userList.sort((u1, u2) -> u1.getUsername().compareToIgnoreCase(u2.getUsername()));
                sortName.setText(R.string.nameAscending);
            }
            createUserView(userList, query, false);
        });

        sortEmail = findViewById(R.id.sortEmail);
        sortEmail.setOnClickListener(v -> {
            CharSequence currentDescription = sortEmail.getText();

            if (currentDescription.equals(getString(R.string.emailAscending))) {
                // Currently ascending, sort descending next
                userList.sort((u1, u2) -> u2.getEmail().compareToIgnoreCase(u1.getEmail()));
                sortEmail.setText(R.string.emailDescending); // Update icon to descending
            } else {
                // Currently descending, sort ascending next
                userList.sort((u1, u2) -> u1.getEmail().compareToIgnoreCase(u2.getEmail()));
                sortEmail.setText(R.string.emailAscending);
            }
            createUserView(userList, query, false);
        });

        setupGenderFilter();

        // Search
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Show all users, for test only
                if (query.equals("all")) {
                    query = "";
                }
                searchUsers(query);
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
        List<String> blockedUsers = currentUser != null ? currentUser.getBlockList() : new ArrayList<>(); // Initialize blockedUsers

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

        // Filter users based on the block list
        userList = users.stream()
                .filter(user -> !blockedUsers.contains(user.getUserId())) // Exclude blocked users
                .collect(Collectors.toList());

        createUserView(userList, query, invalid);
    }

    private void createUserView(List<User> users, String query, boolean invalid) {
        if (!users.isEmpty()) {
            for (User user : users) {
                Log.d("treeRet", user.getUsername());
            }
            UserAdapter adapter = new UserAdapter(users, this);
            binding.userRecyclerView.setAdapter(adapter);
            binding.userRecyclerView.setVisibility(View.VISIBLE);
            binding.textErrorMessage.setVisibility(View.GONE);
            if (query != null) {
                Toast.makeText(getApplicationContext(), "Search <" + query + "> success", Toast.LENGTH_SHORT).show();
            }
        } else {
            binding.userRecyclerView.setVisibility(View.GONE);
            binding.textErrorMessage.setVisibility(View.VISIBLE);
            if (invalid) {
                Toast.makeText(getApplicationContext(), "Expression <" + query +
                        "> is invalid, please check grammar", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Search <" + query + "> no result", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setupGenderFilter() {
        Spinner spinner = findViewById(R.id.genderFilter);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.gender_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedGender = parent.getItemAtPosition(position).toString();
                Log.d("Selected gender", selectedGender);
                createUserView(userList.stream().filter(u -> u.compareGender(selectedGender)).collect(Collectors.toList()), null, false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle case where no gender filter is selected
            }
        });
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
