package com.example.kangarun.activity;

import static com.example.kangarun.activity.LoginActivity.currentUser;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kangarun.R;
import com.example.kangarun.User;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class UpdateProfileActivity extends AppCompatActivity {
    private EditText  editTextUserName, editTextGender, editTextWeight, editTextHeight;
    private TextView textemail;
    private Button uploadImageButton, uploadInfoButton;
    private ImageView profile_image_view;
    private StorageReference storageReference;
    private double weight, height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        String userId = currentUser.getUserId();


        editTextUserName = findViewById(R.id.username);
        editTextGender = findViewById(R.id.usergender);
        editTextWeight = findViewById(R.id.userweight);
        editTextHeight = findViewById(R.id.userheight);
        textemail = findViewById(R.id.useremail);

        profile_image_view = findViewById(R.id.profile_image_view);
        uploadImageButton = findViewById(R.id.uploadImageButton);
        uploadInfoButton = findViewById(R.id.uploadInfoButton);

        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("user/" + User.getCurrentUserId() + "/profile.jpg");

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = firebaseFirestore.collection("user").document(User.getCurrentUserId());
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                editTextUserName.setText(value.getString("username"));
                textemail.setText(value.getString("email"));
                editTextGender.setText(value.getString("gender"));
                editTextWeight.setText(String.valueOf(value.getDouble("weight")));
                editTextHeight.setText(String.valueOf(value.getDouble("height")));
            }
        });

        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profile_image_view);
            }
        });

        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(UpdateProfileActivity.this)
                        .crop(1f, 1f)                //Crop image to 1:1
                        .compress(240)            //Compress image file size
                        .maxResultSize(540, 540)    // Image max size
                        .start();
            }
        });

        uploadInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = editTextUserName.getText().toString();
                String gender = editTextGender.getText().toString();
                weight = Double.parseDouble(editTextWeight.getText().toString());
                height = Double.parseDouble(editTextHeight.getText().toString());
                String email = textemail.getText().toString();

                try {
                    weight = Double.parseDouble(editTextWeight.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(UpdateProfileActivity.this, "Invalid weight", Toast.LENGTH_SHORT).show();
                }

                try {
                    height = Double.parseDouble(editTextHeight.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(UpdateProfileActivity.this, "Invalid height", Toast.LENGTH_SHORT).show();
                }
                User user = new User();
                user.setUsername(userName);
                user.setEmail(email);
                user.setUserId(userId);
                user.setGender(gender);
                user.setWeight(weight);
                user.setHeight(height);
                user.uploadProfile();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        uploadPictureToFirebase(uri);
    }

    private void uploadPictureToFirebase(Uri pictureUri) {
        StorageReference fileRef = storageReference.child("user/" + currentUser.getUserId() + "/profile.jpg");
        fileRef.putFile(pictureUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(UpdateProfileActivity.this, "Picture uploaded", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(UpdateProfileActivity.this, "Picture uploaded failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}