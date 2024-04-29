package com.example.kangarun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kangarun.databinding.ActivityFriendListBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class FriendListActivity extends AppCompatActivity implements UserListener{

    private ActivityFriendListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFriendListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getUsers();
        setListeners();
    }

    private void setListeners(){
        binding.imageBack.setOnClickListener(v -> onBackPressed());
    }
    private void getUsers() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user").get().addOnCompleteListener(t ->{
            String currentUid = getCurrentUserId();
           if (t.isSuccessful() && t.getResult() != null){
               List<User> users = new ArrayList<>();
               for (QueryDocumentSnapshot queryDocumentSnapshot : t.getResult()) {
                   assert currentUid != null;
                   if (true) { //currentUid.equals(queryDocumentSnapshot.getId())
                       User user = new User();
                       user.setUsername(queryDocumentSnapshot.getString("username"));
                       user.setEmail(queryDocumentSnapshot.getString("email"));
                        // user.setProfilePicture(queryDocumentSnapshot.getString(""));TODO
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

    private String getCurrentUserId() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            return currentUser.getUid();
        }
        return null;
    }

    @Override
    public void onUserClicked(User user){
        Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}

