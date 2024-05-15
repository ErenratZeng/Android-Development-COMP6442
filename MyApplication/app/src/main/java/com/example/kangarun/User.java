package com.example.kangarun;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Runyao Wang u6812566, Qiutong Zeng u7724723
 */

public class User implements Serializable, Comparable<User> {
    public static final String TAG = "User";
    protected String userId;
    protected String gender;
    protected String username;
    private String email;
    private double weight;
    private double height;
    private List<String> friendsList;  // Storing friend IDs
    private List<String> blockList;  // Blocked users

    public User() {

    }

    // Constructor for Search Test only
    public User(String username, String email, String gender) {
        this.username = username;
        this.email = email;
        this.gender = gender;
    }


    public static String getCurrentUserId() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            return currentUser.getUid();
        }
        return null;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public List<String> getFriendsList() {
        return friendsList;
    }

    public List<String> getBlockList() {
        return blockList;
    }


    public void block(String id, OnSuccessListener<Void> onSuccessListener) {
        if (!blockList.contains(id)) {
            blockList.add(id);
            // Add current user to the block list of the user being blocked
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("user").document(id).update("blockList", FieldValue.arrayUnion(getCurrentUserId()))
                    .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "Block list updated successfully!");
                        onSuccessListener.onSuccess(aVoid);
                    })
                    .addOnFailureListener(e -> Log.e(TAG, "Error updating block list", e));
        }
    }

    // Compare user's gender with given type. The give type can only be Male, Female or Other
    public boolean compareGender(String g) {
        // null gender if the user doesn't provide gender. It should be classified as other
        if (gender == null) {
            return g.equals("Other") || g.equals("All Genders");
        }
        // Always true
        if (g.equals("All Genders")) {
            return true;
        }
        if (g.equals("Male") || g.equals("Female")) {
            return gender.equalsIgnoreCase(g);
        } else if (g.equals("Other")) {
            // If given type is other, return true if user is not male or female.
            return !gender.equals("Male") && !gender.equals("Female");
        } else {
            throw new RuntimeException("invalid gender input");
        }
    }

    public void uploadNewuserProfile() {
        String uid = getCurrentUserId();
        if (uid != null) {
            Map<String, Object> userProfile = new HashMap<>();
            userProfile.put("uid", getUserId());
            userProfile.put("username", getUsername());
            userProfile.put("gender", getGender());
            userProfile.put("email", getEmail());
            userProfile.put("height", getHeight());
            userProfile.put("weight", getWeight());
            userProfile.put("friendList", getFriendsList());
            userProfile.put("blockList", new ArrayList<>());
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("user").document(uid).set(userProfile).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "DocumentSnapshot successfully written!");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "Error writing document", e);
                }
            });
        }
    }

    @Override
    public int compareTo(User o) {
        return this.username.compareTo(o.username);
    }
}
