package com.example.kangarun.activity;

import static com.example.kangarun.activity.LoginActivity.currentUser;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.kangarun.User;
import com.example.kangarun.UserListener;
import com.example.kangarun.adapter.UserAdapter;
import com.example.kangarun.databinding.ActivityBlacklistBinding;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class BlacklistActivity extends AppCompatActivity implements UserListener {

    private ActivityBlacklistBinding binding;
    private List<User> blockedUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBlacklistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (currentUser != null && currentUser.getUserId() != null) {
            getBlockedUsers();
            setListeners();
        } else {
            binding.textErrorMessage.setText("User ID not set");
            binding.textErrorMessage.setVisibility(View.VISIBLE);
        }
    }

    private void setListeners() {
        binding.imageBack.setOnClickListener(v -> onBackPressed());
    }

    private void getBlockedUsers() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user").document(currentUser.getUserId()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                List<String> blockList = (List<String>) task.getResult().get("blockList");
                if (blockList != null && !blockList.isEmpty()) {
                    fetchBlockedUsersDetails(blockList);
                } else {
                    updateUI();
                }
            } else {
                binding.textErrorMessage.setText("Failed to load blocked users");
                binding.textErrorMessage.setVisibility(View.VISIBLE);
            }
        });
    }

    private void fetchBlockedUsersDetails(List<String> blockList) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        for (String userId : blockList) {
            db.collection("user").document(userId).get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    User user = task.getResult().toObject(User.class);
                    if (user != null) {
                        blockedUsers.add(user);
                    }
                }
                updateUI(); // Update UI after each fetch attempt
            });
        }
    }

    private void updateUI() {
        if (!blockedUsers.isEmpty()) {
            UserAdapter adapter = new UserAdapter(blockedUsers, this);
            binding.userRecyclerView.setAdapter(adapter);
            binding.userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            binding.userRecyclerView.setVisibility(View.VISIBLE);
            binding.textErrorMessage.setVisibility(View.GONE);
        } else {
            binding.userRecyclerView.setVisibility(View.GONE);
            binding.textErrorMessage.setText("No blocked users");
            binding.textErrorMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onUserClicked(User user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user").document(currentUser.getUserId())
                .update("blockList", FieldValue.arrayRemove(user.getUserId()))
                .addOnSuccessListener(aVoid -> {
                    blockedUsers.remove(user);
                    updateUI();
                    Toast.makeText(this, "User unblocked", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to unblock user", Toast.LENGTH_SHORT).show());
    }
}
