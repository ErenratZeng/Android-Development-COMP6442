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
import com.google.firebase.firestore.WriteBatch;

import java.util.Collections;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {
    TextView useremail, username, usergender, userweight, userheight;
    Button  updateInfoButton, blacklistButton;
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
        updateInfoButton = findViewById(R.id.uploadInfoButton);
        blacklistButton = findViewById(R.id.blacklistButton);

        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();

        currentId = currentUser != null ? currentUser.getUserId() : null;
        StorageReference profileRef = storageReference.child("user/" + User.getCurrentUserId() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profile_image_view);
            }
        });

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = firebaseFirestore.collection("user").document(User.getCurrentUserId());
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                username.setText("Username: " + value.getString("username"));
                useremail.setText("Email: " + value.getString("email"));
                usergender.setText("Gender: " + value.getString("gender"));
                userweight.setText("Weight: " + String.valueOf(value.getDouble("weight")) + "kg");
                userheight.setText("Height: " + String.valueOf(value.getDouble("height")) + "cm");
            }
        });

        Log.i("User in userprofile",currentId);
        if (currentId!=null)

        { profileId=currentId;
            loadUserProfile(currentId);}

        updateInfoButton.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, UpdateProfileActivity.class);
            startActivity(intent);
        });

        blacklistButton.setOnClickListener(v -> {
            if (blacklistButton.getText().equals("Blacklist")) {
                if (profileId != null && currentId != null) {
                    blacklistUser();
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


            }
        });
    }

    private void blacklistUser() {
        DocumentReference currentDocRef = firebaseFirestore.collection("user").document(currentId);
        DocumentReference profileDocRef = firebaseFirestore.collection("user").document(profileId);
Log.i("block ","here");
        currentDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                profileDocRef.get().addOnCompleteListener(task2 -> {
                    if (task2.isSuccessful() && task2.getResult().exists()) {
                        // Both documents exist, proceed with batch update
                        WriteBatch batch = firebaseFirestore.batch();
                        batch.update(currentDocRef, "blockList", FieldValue.arrayUnion(profileId));
                        batch.update(currentDocRef, "friendList", FieldValue.arrayRemove(profileId));
                        batch.update(profileDocRef, "friendList", FieldValue.arrayRemove(currentId));

                        batch.commit()
                                .addOnSuccessListener(aVoid -> {
                                    Log.d("BlacklistUser", "User blacklisted successfully!");
                                    Toast.makeText(UserProfileActivity.this, "User blacklisted", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), BlacklistActivity.class);
                                    startActivity(intent);
                                    finish(); // Close the current activity
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("BlacklistUser", "Error blacklisting user", e);
                                    Toast.makeText(UserProfileActivity.this, "Failed to blacklist user: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        // Profile document does not exist
                        Log.e("BlacklistUser", "Profile document does not exist");
                        Toast.makeText(UserProfileActivity.this, "Profile user does not exist", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                // Current user document does not exist
                Log.e("BlacklistUser", "Current user document does not exist");
                Toast.makeText(UserProfileActivity.this, "Current user does not exist", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            Log.e("BlacklistUser", "Error fetching current user document", e);
            Toast.makeText(UserProfileActivity.this, "Error fetching current user: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
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
        Uri uri = data.getData();
        uploadPictureToFirebase(uri);
    }

    private void uploadPictureToFirebase(Uri pictureUri) {
        StorageReference fileRef = storageReference.child("user/" + currentUser.getUserId() + "/profile.jpg");
//        StorageReference fileRef = storageReference.child("user/" + User.getCurrentUserId() + "/profile.jpg");
        fileRef.putFile(pictureUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(UserProfileActivity.this, "Picture uploaded", Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profile_image_view);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserProfileActivity.this, "Picture uploaded failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}