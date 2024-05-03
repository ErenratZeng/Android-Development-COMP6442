package com.example.kangarun.activity;

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
        getUsers();
        setListeners();
    }

    private void setListeners() {
        binding.imageBack.setOnClickListener(v -> onBackPressed());
    }

    private void getUsers() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user").get().addOnCompleteListener(t -> {
            String currentUid = User.getCurrentUserId();
            if (t.isSuccessful() && t.getResult() != null) {
                List<User> users = new ArrayList<>();
                for (QueryDocumentSnapshot queryDocumentSnapshot : t.getResult()) {
                    assert currentUid != null;
                    if (!currentUid.equals(queryDocumentSnapshot.getId())) { //TODO: filter friends
                        User user = new User();
                        user.setUsername(queryDocumentSnapshot.getString("username"));
                        user.setEmail(queryDocumentSnapshot.getString("email"));
                        user.setUserId(queryDocumentSnapshot.getString("uid"));
                        // user.setProfilePicture(queryDocumentSnapshot.getString(""));TODO
                        // Add more instances if need
                        users.add(user);
                    }
                }
                if (!users.isEmpty()) {
                    UserAdapter adapter = new UserAdapter(users, this);
                    binding.userRecyclerView.setAdapter(adapter);
                    binding.userRecyclerView.setVisibility(View.VISIBLE);
                }

            }
        });
    }



    @Override
    public void onUserClicked(User user) {
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}

