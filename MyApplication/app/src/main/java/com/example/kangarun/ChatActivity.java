package com.example.kangarun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kangarun.databinding.ActivityChatBinding;

public class ChatActivity extends AppCompatActivity {
    private ActivityChatBinding binding;
    private User receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        binding.imageBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        receiver = getIntent().getSerializableExtra("user", com.example.kangarun.User.class);
        assert receiver != null;
        binding.textName.setText(receiver.getUsername());


        ImageView profileButton = findViewById(R.id.imageInfo);
        profileButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"User Profile",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                intent.putExtra("user", receiver);
                startActivity(intent);
            }
        });

    }



    public void setListeners() {

    }
}