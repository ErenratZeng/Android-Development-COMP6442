package com.example.myapplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

// Make it serializable to pass the data through Intent
public class User implements Serializable {
    protected String userId;
    protected String username;
    protected String password;
    private String email;
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

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public boolean block (String id) {
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

    // Method to send the message
    public void sendMessage(String recipientId, String content) {
        // Create a file name based on the user IDs involved in the conversation
        String conversationFileName = "chat_history_" + this.userId + "_" + recipientId + ".txt";

        // Ensure the directory for the chat history exists
        File directory = new File("ChatHistories");
        if (!directory.exists()) {
            directory.mkdir();
        }

        // Create a File object
        File conversationFile = new File(directory, conversationFileName);

        // Append the message to the file with BufferedWriter
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(conversationFile, true))) {
            // Construct the message with a timestamp
            String timestamp = new Date().toString(); // Simple string representation of the current Date and Time
            String messageLine = timestamp + " From " + this.userId + " to " + recipientId + ": " + content + "\n";

            // Write the message to the file
            writer.write(messageLine);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
