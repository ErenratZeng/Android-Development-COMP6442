package com.example.kangarun;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kangarun.databinding.ActivityUserProfileBinding;

public class UserProfileActivity extends AppCompatActivity {
    private ActivityUserProfileBinding binding;
    private User receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        receiver = getIntent().getSerializableExtra("user", com.example.kangarun.User.class);
        assert receiver != null;

        binding.textViewFullName.setText(receiver.getUsername());
    }
}