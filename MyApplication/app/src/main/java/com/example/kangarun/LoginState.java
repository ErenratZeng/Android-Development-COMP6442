package com.example.kangarun;


import java.util.ArrayList;
import java.util.List;

public class LoginState {
    private static LoginState instance;
    private String id;
    private List<String> blockList;

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
    }

    // Getter for block list
    public List<String> getBlockList() {
        return blockList;
    }
}

