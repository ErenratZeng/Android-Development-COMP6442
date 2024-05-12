package com.example.kangarun.activity;

import static com.example.kangarun.activity.LoginActivity.currentUser;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kangarun.R;
import com.example.kangarun.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {
    TextView useremail, username, usergender, userweight, userheight;
    Button uploadImageButton, updateInfoButton, blacklistButton;
    ImageView profile_image_view;
    StorageReference storageReference;
    FirebaseFirestore firebaseFirestore;
    String profileId, currentId;

    public UserProfileActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        username = findViewById(R.id.username);
        useremail = findViewById(R.id.useremail);
        usergender = findViewById(R.id.usergender);
        userweight = findViewById(R.id.userweight);
        userheight = findViewById(R.id.userheight);
        profile_image_view = findViewById(R.id.profile_image_view);
        uploadImageButton = findViewById(R.id.uploadImageButton);
        updateInfoButton = findViewById(R.id.uploadInfoButton);
        blacklistButton = findViewById(R.id.blacklistButton);

        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();

        currentId = currentUser != null ? currentUser.getUserId() : null;

//        User user = (User) getIntent().getSerializableExtra("user");
        Log.i("User in userprofile",currentId);
        if (currentId!=null)

        { profileId=currentId;loadUserProfile(currentId);}
//
//        if (user != null) {
//            profileId = user.getUserId();
//            loadUserProfile(profileId);
//        }else {
//            Toast.makeText(this, "Failed to get user details", Toast.LENGTH_SHORT).show();
//            Log.e("UserProfileActivity", "User object is null in intent");
//        }

        uploadImageButton.setOnClickListener(v -> ImagePicker.with(UserProfileActivity.this)
                .crop(1f, 1f) // Crop image to 1:1
                .compress(240) // Compress image file size
                .maxResultSize(540, 540) // Image max size
                .start());

        updateInfoButton.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, UpdateProfileActivity.class);
            startActivity(intent);
        });

        blacklistButton.setOnClickListener(v -> {
            if (blacklistButton.getText().equals("Blacklist")) {
                if (profileId != null && currentId != null) {
                    blacklistUser();
                    Intent intent = new Intent(getApplicationContext(), BlacklistActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(UserProfileActivity.this, "User IDs are not set", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadUserProfile(String userId) {
        StorageReference profileRef = storageReference.child("user/" + userId + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(profile_image_view))
                .addOnFailureListener(e -> Log.e("FirebaseStorage", "Error fetching profile image", e));

        DocumentReference documentReference = firebaseFirestore.collection("user").document(userId);
        documentReference.addSnapshotListener(this, (value, error) -> {
            if (value != null && value.exists()) {
                username.setText("Username: " + value.getString("username"));
                useremail.setText("Email: " + value.getString("email"));
                usergender.setText("Gender: " + value.getString("gender"));
                userweight.setText("Weight: " + value.getDouble("weight") + "kg");
                userheight.setText("Height: " + value.getDouble("height") + "cm");

//                List<String> blockList = (List<String>) value.get("blockList");
//                if (blockList != null && blockList.contains(currentId)) {
//                    blacklistButton.setText("Unblacklist");
//                } else {
//                    blacklistButton.setText("Blacklist");
//                }
            }
        });
    }

    private void blacklistUser() {
        DocumentReference currentDocRef = firebaseFirestore.collection("user").document(currentId);
        DocumentReference profileDocRef = firebaseFirestore.collection("user").document(profileId);

        currentDocRef.update("blockList", FieldValue.arrayUnion(profileId))
                .addOnSuccessListener(aVoid -> {
                    Log.d("BlacklistUser", "User blacklisted successfully!");
                    Toast.makeText(UserProfileActivity.this, "User blacklisted", Toast.LENGTH_SHORT).show();
//                    blacklistButton.setText("Unblacklist");
                    removeFromFriendList();
                })
                .addOnFailureListener(e -> Log.e("BlacklistUser", "Error blacklisting user", e));
    }

    private void unblacklistUser() {
        DocumentReference currentDocRef = firebaseFirestore.collection("user").document(currentId);
        currentDocRef.update("blockList", FieldValue.arrayRemove(profileId))
                .addOnSuccessListener(aVoid -> {
                    Log.d("UnblacklistUser", "User unblacklisted successfully!");
                    Toast.makeText(UserProfileActivity.this, "User unblacklisted", Toast.LENGTH_SHORT).show();
                    blacklistButton.setText("Blacklist");
                })
                .addOnFailureListener(e -> Log.e("UnblacklistUser", "Error unblacklisting user", e));
    }

    private void removeFromFriendList() {
        DocumentReference profileDocRef = firebaseFirestore.collection("user").document(profileId);
        DocumentReference currentDocRef = firebaseFirestore.collection("user").document(currentId);

        profileDocRef.update("friendList", FieldValue.arrayRemove(currentId))
                .addOnSuccessListener(aVoid -> Log.d("RemoveFriend", "Removed from friend's friend list"))
                .addOnFailureListener(e -> Log.e("RemoveFriend", "Error removing from friend's friend list", e));

        currentDocRef.update("friendList", FieldValue.arrayRemove(profileId))
                .addOnSuccessListener(aVoid -> Log.d("RemoveFriend", "Removed from current user's friend list"))
                .addOnFailureListener(e -> Log.e("RemoveFriend", "Error removing from current user's friend list", e));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getData() != null) {
            Uri uri = data.getData();
            uploadPictureToFirebase(uri);
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadPictureToFirebase(Uri pictureUri) {
        if (pictureUri != null && currentId != null) {
            StorageReference fileRef = storageReference.child("user/" + currentId + "/profile.jpg");
            fileRef.putFile(pictureUri).addOnSuccessListener(taskSnapshot -> {
                Toast.makeText(UserProfileActivity.this, "Picture uploaded", Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(profile_image_view));
            }).addOnFailureListener(e -> Toast.makeText(UserProfileActivity.this, "Picture upload failed", Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "Invalid picture or user ID", Toast.LENGTH_SHORT).show();
        }
    }
}
