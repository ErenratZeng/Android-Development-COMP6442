package com.example.kangarun;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

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
