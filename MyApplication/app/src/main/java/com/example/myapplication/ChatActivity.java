package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myapplication.databinding.ActivityChatBinding;

public class ChatActivity extends AppCompatActivity {
    private ActivityChatBinding binding;
    private User receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        binding.imageBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        receiver = getIntent().getSerializableExtra("user", com.example.myapplication.User.class);
        assert receiver != null;
        binding.textName.setText(receiver.getUsername());

    }

    public void setListeners() {

    }
}