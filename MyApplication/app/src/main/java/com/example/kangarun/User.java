package com.example.kangarun;

import static com.example.kangarun.activity.LoginActivity.currentUser;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// Make it serializable to pass the data through Intent
public class User implements Serializable, Comparable<User> {
    public static final String TAG = "User";
    protected String userId;
    protected String gender;
    protected String username;
    protected String password;
    private String email;
    private double weight;
    private double height;
    private List<String> friendsList;  // Storing friend IDs
    private List<String> blockList;  // Blocked users
    private List<String> activityHistory;  // Storing activity IDs for simplicity

    public User(String username, String password) {
        this.userId = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
        this.friendsList = new ArrayList<>();
        this.blockList = new ArrayList<>();
        this.activityHistory = new ArrayList<>();

    }

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

    public List<String> getActivityHistory() {
        return activityHistory;
    }

    public List<String> getBlockList() {
        return blockList;
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
        if (blockList.remove(id)) {
            saveBlockListToStorage();
        }


    }
    private void saveBlockListToStorage() {
        String uid = getCurrentUserId();
        if (uid != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("user").document(uid).update("blockList", blockList)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Block list updated successfully!"))
                    .addOnFailureListener(e -> Log.e(TAG, "Error updating block list", e));
        }
    }
    // Method to update user profile
    public void updateProfile(String newUsername, String newEmail) {
        setUsername(newUsername);
        setEmail(newEmail);
        uploadProfile();
    }

    public boolean compareGender(String g){
        if (gender == null){
            return g.equals("Other") || g.equals("All Genders");
        }
        if (g.equals("All Genders")){
            return true;
        }
        if (g.equals("Male") || g.equals("Female")) {
            return gender.equalsIgnoreCase(g);
        } else if (g.equals("Other")) {
            return !gender.equals("Male") && !gender.equals("Female");
        } else {
            throw new RuntimeException("invalid gender input");
        }
    }
    public void uploadProfile() {
//        String uid = currentUser.getUserId();
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

    //print user details
    public void printUser() {
        System.out.println("User ID: " + userId);
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
        System.out.println("Friends List: " + friendsList.toString());
        System.out.println("Activity History: " + activityHistory.toString());
    }


    @Override
    public int compareTo(User o) {
        return this.username.compareTo(o.username);
    }
}
