package com.example.kangarun.activity.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kangarun.R;
import com.example.kangarun.User;
import com.example.kangarun.UserListener;
import com.example.kangarun.activity.FriendProfileActivity;
import com.example.kangarun.adapter.UserAdapter;
import com.example.kangarun.databinding.FragmentSearchBinding;
import com.example.kangarun.activity.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements UserListener {
    private FragmentSearchBinding binding;
    private List<String> blockedUsers = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        String query = getActivity().getIntent().getStringExtra("query");
        binding.searchView.setQuery(query, false);
        loadBlockedUsers(() -> searchUsers(query));

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getActivity(), "Searching " + query, Toast.LENGTH_SHORT).show();
                searchUsers(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return binding.getRoot();
    }

    private void loadBlockedUsers(Runnable onComplete) {
        String currentUserId = User.getCurrentUserId();
        if (currentUserId != null) {
            FirebaseFirestore.getInstance().collection("user").document(currentUserId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            blockedUsers = (List<String>) documentSnapshot.get("blockedContents");
                        }
                        if (blockedUsers == null) {
                            blockedUsers = new ArrayList<>();
                        }
                        onComplete.run();
                    })
                    .addOnFailureListener(e -> Log.e("SearchFragment", "Error loading blocked users", e));
        }
    }

    private void searchUsers(String query) {
        if (query == null || query.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter a search term", Toast.LENGTH_SHORT).show();
            return;
        }
        List<User> users = MainActivity.tree.searchPartial(query);

        // Debugging: Print the blockedUsers list
        Log.d("BlockedUsers", "Blocked Users: " + blockedUsers);

        // Filter out blocked users
        List<User> filteredUsers = new ArrayList<>();
        for (User user : users) {
            if (!blockedUsers.contains(user.getUserId())) {
                filteredUsers.add(user);
            }
        }

        if (!filteredUsers.isEmpty()) {
            for (User user : filteredUsers) {
                Log.d("treeRet", user.getUsername());
            }
            UserAdapter adapter = new UserAdapter(filteredUsers, this);
            binding.userRecyclerView.setAdapter(adapter);
            binding.userRecyclerView.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "Search success", Toast.LENGTH_SHORT).show();
        } else {
            binding.userRecyclerView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "No results found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUserClicked(User user) {
        Intent intent = new Intent(getActivity(), FriendProfileActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}
