package com.example.kangarun;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kangarun.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "messages";
    private ActivityChatBinding binding;
    private User receiver;
    private List<Message> messageList;
    private ChatAdapter adapter;
    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null) {
            return;
        }
        if (value != null) {
            int count = messageList.size();
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    Message m = new Message();
                    m.senderId = documentChange.getDocument().getString("senderId");
                    m.receiverId = documentChange.getDocument().getString("receiverId");
                    m.messageContent = documentChange.getDocument().getString("message");
                    m.datetime = getDateTime(documentChange.getDocument().getDate("time"));
                    m.dateObj = documentChange.getDocument().getDate("time");
                    messageList.add(m);
                }
            }
            // Sort messages by date
            messageList.sort(Comparator.comparing(m -> m.dateObj));
            if (count == 0) {
                adapter.notifyDataSetChanged();
            } else {
                adapter.notifyItemRangeInserted(messageList.size(), messageList.size());
                binding.chatRecycleView.smoothScrollToPosition(messageList.size() - 1);
            }
            binding.chatRecycleView.setVisibility(View.VISIBLE);
        }
    };
    private FirebaseFirestore db;

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
        listenMessage();


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

    private void init() {
        messageList = new ArrayList<>();
        adapter = new ChatAdapter(
                receiver.getUserId(),
                messageList,
                getCurrentUserId()
        );
        binding.chatRecycleView.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();
    }

    public void sendMessage() {
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

    private void listenMessage() {
        db.collection("collection_chat")
                .whereEqualTo("senderId", getCurrentUserId())
                .whereEqualTo("receiverId", receiver.getUserId())
                .addSnapshotListener(eventListener);
        db.collection("collection_chat")
                .whereEqualTo("senderId", receiver.getUserId())
                .whereEqualTo("receiverId", getCurrentUserId())
                .addSnapshotListener(eventListener);
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
    private Bitmap getBitmapFromEncoded(String encoded) {
        byte[] bytes = Base64.getDecoder().decode(encoded);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private String getDateTime(Date date) {
        return new SimpleDateFormat("yyyy MM-dd - hh:mm a", Locale.getDefault()).format(date);
    }
}