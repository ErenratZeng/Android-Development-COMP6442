package com.example.kangarun.activity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kangarun.R;
import com.example.kangarun.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


public class FriendProfileActivity extends AppCompatActivity {
    TextView useremail, username, usergender, userweight, userheight;
    Button blockUserButton;
    ImageView profile_image_view;
    StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;

    public FriendProfileActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
        User user = (User) getIntent().getSerializableExtra("user");
        username = findViewById(R.id.username);
        useremail = findViewById(R.id.useremail);
        usergender = findViewById(R.id.usergender);
        userweight = findViewById(R.id.userweight);
        userheight = findViewById(R.id.userheight);
        profile_image_view = findViewById(R.id.profile_image_view);
        blockUserButton = findViewById(R.id.blockUserButton);
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("user/" + user.getUserId() + "/profile.jpg");
        if (user != null){
            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(profile_image_view);
                }
            });

            firebaseFirestore = FirebaseFirestore.getInstance();
            DocumentReference documentReference = firebaseFirestore.collection("user").document(user.getUserId());
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    username.setText(value.getString("username"));
                    useremail.setText(value.getString("email"));
                    usergender.setText(value.getString("gender"));
                    userweight.setText(String.valueOf(value.getDouble("weight")));
                    userheight.setText(String.valueOf(value.getDouble("height")));
                    //TODO Add label in each text
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "User has no profile", Toast.LENGTH_LONG).show();
        }

    }
}