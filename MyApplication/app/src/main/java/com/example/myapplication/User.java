package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    protected String userId;
    protected String username;
    protected String password;

    public User(String userId, String username, String password) {
        this.userId = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
    }

    public User() {

    }

    public void login() {
        // Login logic here
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


}
