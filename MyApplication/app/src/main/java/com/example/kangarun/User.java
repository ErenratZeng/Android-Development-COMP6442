package com.example.kangarun;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// Make it serializable to pass the data through Intent
public class User implements Serializable {
    public static final String TAG = "User";
    protected String userId;
    protected String gender;
    protected String username;
    protected String password;
    transient FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String email;
    private double weight;
    private double height;
    private String profilePicture;
    private List<String> friendsList;  // Storing friend IDs
    private List<String> blockList;  // Blocked users
    private List<String> activityHistory;  // Storing activity IDs for simplicity

    public User(String username, String password) {
        this.userId = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
        this.profilePicture = ""; // TODO
        this.friendsList = new ArrayList<>();
        this.blockList = new ArrayList<>();
        this.activityHistory = new ArrayList<>();
    }

    public User() {

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

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
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

    public List<String> getActivityHistory() {
        return activityHistory;
    }

    // Methods to manage friends
    public boolean addFriend(String friendId) {
        if (!friendsList.contains(friendId)) {
            friendsList.add(friendId);
            return true;
        }
        return false;
    }

    public void removeFriend(String friendId) {
        friendsList.remove(friendId);
    }

    public boolean block(String id) {
        if (!blockList.contains(id)) {
            blockList.add(id);
            return true;
        }
        return false;
    }

    public void unBlock(String id) {
        blockList.remove(id);
    }

    // Method to update user profile
    public void updateProfile(String newUsername, String newEmail, String newProfilePicture) {
        setUsername(newUsername);
        setEmail(newEmail);
        setProfilePicture(newProfilePicture);
        uploadProfile();
    }

    public void uploadProfile() {
        String uid = getCurrentUserId();
        if (uid != null) {
            Map<String, Object> userProfile = new HashMap<>();
            userProfile.put("uid", getUserId());
            userProfile.put("username", getUsername());
            userProfile.put("gender", getGender());
            userProfile.put("email", getEmail());
            userProfile.put("height", getHeight());
            userProfile.put("weight", getWeight());
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

    //print user details
    public void printUser() {
        System.out.println("User ID: " + userId);
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
        System.out.println("Profile Picture URL: " + profilePicture);
        System.out.println("Friends List: " + friendsList.toString());
        System.out.println("Activity History: " + activityHistory.toString());
    }


}
