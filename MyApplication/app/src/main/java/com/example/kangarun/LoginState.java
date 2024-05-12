package com.example.kangarun;

public class LoginState {
    private static LoginState instance;
    private String id;

    private LoginState() {

    }

    public static LoginState getInstance() {
        if (instance == null) {
            instance = new LoginState();
        }
        return instance;
    }

    public String getUserId() {
        return id;
    }

    public void setUserId(String id) {
        this.id = id;
    }

}
