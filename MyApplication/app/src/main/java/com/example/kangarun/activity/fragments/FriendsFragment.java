package com.example.kangarun.activity.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.kangarun.R;
import com.example.kangarun.User;
import com.example.kangarun.adapter.UserAdapter;
import com.example.kangarun.databinding.FragmentFriendsBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class FriendsFragment extends Fragment {
    private FragmentFriendsBinding binding;
    private FirebaseFirestore firebaseFirestore;
    private List<User> friendsList;
    private UserAdapter userAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFriendsBinding.inflate(inflater, container, false);

        firebaseFirestore = FirebaseFirestore.getInstance();
        friendsList = new ArrayList<>();
        userAdapter = new UserAdapter(friendsList, user -> {
            // Handle user click here
        });

        binding.recyclerViewFriends.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewFriends.setAdapter(userAdapter);

        loadFriends();

        return binding.getRoot();
    }

    private void loadFriends() {
        String currentUserId = User.getCurrentUserId();
        Log.d("FriendsFragment", "Loading friends for user: " + currentUserId);
        if (currentUserId != null) {
            firebaseFirestore.collection("user")
                    .document(currentUserId)
                    .collection("friends")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User friend = document.toObject(User.class);
                                friendsList.add(friend);
                                Log.d("FriendsFragment", "Friend loaded: " + friend.getUsername());
                            }
                            userAdapter.notifyDataSetChanged();
                        } else {
                            Log.d("FriendsFragment", "Error loading friends: ", task.getException());
                        }
                    });
        } else {
            Log.d("FriendsFragment", "Current user ID is null.");
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
