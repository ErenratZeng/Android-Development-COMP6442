package com.example.kangarun;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kangarun.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private ActivityChatBinding binding;
    private User receiver;
    private List<Message> messageList;
    private ChatAdapter adapter;
    private FirebaseFirestore db;

    private static final String TAG = "messages";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        binding.imageBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        binding.layoutSend.setOnClickListener(v -> sendMessage());
        receiver = getIntent().getSerializableExtra("user", com.example.kangarun.User.class);
        assert receiver != null;
        binding.textName.setText(receiver.getUsername());
        init();


        ImageView profileButton = findViewById(R.id.imageInfo);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "User Profile", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                intent.putExtra("user", receiver);
                startActivity(intent);
            }
        });

    }

    private void init(){
        messageList = new ArrayList<>();
        adapter = new ChatAdapter(
                getBitmapFromEncoded(receiver.getProfilePicture()),
                messageList,
                getCurrentUserId()
        );
        binding.chatRecycleView.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();
    }

    public void sendMessage(){
        HashMap<String, Object> message = new HashMap<>();
        message.put("senderId", getCurrentUserId());
        message.put("receiverId", receiver.getUserId());
        message.put("message", binding.inputMessage.getText().toString());
        message.put("time", new Date());
        db.collection("collection_chat")
                .add(message)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Message written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
        binding.inputMessage.setText(null);
    }

    private String getCurrentUserId() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            return currentUser.getUid();
        }
        return null;
    }
    // Decode encoded image string
    private Bitmap getBitmapFromEncoded (String encoded){
        byte[] bytes = Base64.getDecoder().decode(encoded);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}