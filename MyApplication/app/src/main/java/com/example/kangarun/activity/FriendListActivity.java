package com.example.kangarun.activity;

import static com.example.kangarun.activity.LoginActivity.currentUser;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.kangarun.User;
import com.example.kangarun.UserListener;
import com.example.kangarun.adapter.UserAdapter;
import com.example.kangarun.databinding.ActivityFriendListBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class FriendListActivity extends AppCompatActivity implements UserListener {

    private ActivityFriendListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFriendListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (currentUser != null && currentUser.getUserId() != null) {
            getUsers();
            setListeners();
        } else {
            binding.textErrorMessage.setText("User ID not set");
            binding.textErrorMessage.setVisibility(View.VISIBLE);
        }
    }

    private void setListeners() {
        binding.imageBack.setOnClickListener(v -> onBackPressed());
    }

    private void getUsers() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user").get().addOnCompleteListener(t -> {
            if (t.isSuccessful() && t.getResult() != null) {
                List<User> users = new ArrayList<>();
                List<String> blockList = currentUser.getBlockList(); // 获取黑名单列表

                String currentUid = currentUser.getUserId();
                for (QueryDocumentSnapshot queryDocumentSnapshot : t.getResult()) {
                    List<String> friends = (List<String>) queryDocumentSnapshot.get("friendList");
                    if (friends != null && !blockList.contains(queryDocumentSnapshot.getId()) &&
                            friends.contains(currentUid) && !currentUid.equals(queryDocumentSnapshot.getId())) {
                        User user = new User();
                        user.setUsername(queryDocumentSnapshot.getString("username"));
                        user.setEmail(queryDocumentSnapshot.getString("email"));
                        user.setUserId(queryDocumentSnapshot.getString("uid"));
                        users.add(user);
                    }
                }

                if (!users.isEmpty()) {
                    UserAdapter adapter = new UserAdapter(users, this);
                    binding.userRecyclerView.setAdapter(adapter);
                    binding.userRecyclerView.setVisibility(View.VISIBLE);
                    binding.textErrorMessage.setVisibility(View.GONE);
                } else {
                    binding.userRecyclerView.setVisibility(View.GONE);
                    binding.textErrorMessage.setText("No friends found");
                    binding.textErrorMessage.setVisibility(View.VISIBLE);
                }
            } else {
                binding.textErrorMessage.setText("Failed to load friends");
                binding.textErrorMessage.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onUserClicked(User user) {
        Intent intent = new Intent(getApplicationContext(), FriendProfileActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}
