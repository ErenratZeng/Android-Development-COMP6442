package com.example.kangarun;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class LoginState {
    private static LoginState instance;
    private String id;
    private List<String> blockList;

    // Private constructor to enforce singleton pattern
    private LoginState() {
        blockList = new ArrayList<>();
    }

    // Singleton instance accessor
    public static LoginState getInstance() {
        if (instance == null) {
            instance = new LoginState();
        }
        return instance;
    }

    // Getter for user ID
    public String getUserId() {
        return id;
    }

    // Setter for user ID
    public void setUserId(String id) {
        this.id = id;
        // Fetch block list from Firestore when user ID is set
        FirebaseFirestore.getInstance().collection("user")
                .document(id)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        User user = documentSnapshot.toObject(User.class);
                        if (user != null) {
                            blockList = user.getBlockList();
                        }
                    }
                });
    }

    // Getter for block list
    public List<String> getBlockList() {
        return blockList;
    }

    // Method to block a user
    public void blockUser(String userId) {
        if (!blockList.contains(userId)) {
            blockList.add(userId);
            saveBlockListToStorage(); // Save changes to Firestore
        }
    }

    // Method to unblock a user
    public void unblockUser(String userId) {
        if (blockList.remove(userId)) {
            saveBlockListToStorage(); // Save changes to Firestore
        }
    }

    // Method to save block list to Firestore
    private void saveBlockListToStorage() {
        String uid = getUserId();
        if (uid != null) {
            FirebaseFirestore.getInstance().collection("user")
                    .document(uid)
                    .update("blockList", blockList)
                    .addOnSuccessListener(aVoid -> {
                        // Block list updated successfully
                        Log.d("LoginState", "Block list updated successfully!");
                    })
                    .addOnFailureListener(e -> {
                        // Handle the error
                        Log.e("LoginState", "Error updating block list", e);
                    });
        }
    }

    // Method to upload user profile to Firestore
    public void uploadProfile() {
        FirebaseFirestore.getInstance().collection("user")
                .document(id)
                .update("blockList", blockList)
                .addOnSuccessListener(aVoid -> {
                    // Profile updated successfully
                    Log.d("LoginState", "Profile updated successfully!");
                })
                .addOnFailureListener(e -> {
                    // Handle the error
                    Log.e("LoginState", "Error updating profile", e);
                });
    }

}

