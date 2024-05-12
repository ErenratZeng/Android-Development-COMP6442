package com.example.kangarun.activity;

import static com.example.kangarun.activity.LoginActivity.currentUser;
import static com.example.kangarun.utils.FirebaseUtil.loadUsersIntoAVL;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kangarun.R;
import com.example.kangarun.User;
import com.example.kangarun.utils.UserAVLTree;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;

public class MainActivity extends AppCompatActivity {
    public static UserAVLTree tree;
    ImageView profileButton;
    CircleMenu circleMenu;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                currentUser.setUserId("");
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        storageReference = FirebaseStorage.getInstance().getReference();
        profileButton = findViewById(R.id.main_profile_image_view);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "User Profile", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                startActivity(intent);
            }
        });

        tree = new UserAVLTree();
        loadUsersIntoAVL(tree);
        circleMenu = (CircleMenu) findViewById(R.id.circle_menu);

        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.mipmap.icon_menu, R.mipmap.icon_cancel)
                .addSubMenu(Color.parseColor("#30A400"), R.mipmap.icon_cancel)
                .addSubMenu(Color.parseColor("#FF4B32"), R.mipmap.icon_cancel)
                .addSubMenu(Color.parseColor("#258CFF"), R.mipmap.icon_cancel)
                .addSubMenu(Color.parseColor("#FF6A00"), R.mipmap.icon_cancel)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {

                    @Override
                    public void onMenuSelected(int index) {
                        switch (index) {
                            case 1:
                                Intent intent = new Intent(getApplicationContext(), FriendListActivity.class);
                                startActivity(intent);
                                break;
                            case 0:
                                Intent intent1 = new Intent(getApplicationContext(), SearchActivity.class);
                                startActivity(intent1);
                                break;
                            case 3:
                                Intent intent2 = new Intent(getApplicationContext(), ExerciseRecordActivity.class);
                                startActivity(intent2);
                                break;
                            case 2:
                                Intent intent3 = new Intent(getApplicationContext(), MapsActivity.class);
                                startActivity(intent3);
                                break;
                        }
                    }

                }).setOnMenuStatusChangeListener(new OnMenuStatusChangeListener() {

                    @Override
                    public void onMenuOpened() {}

                    @Override
                    public void onMenuClosed() {}

                });


    }



    @Override
    public void onBackPressed() {
//        Toast.makeText(this, "You cannot return to last page", Toast.LENGTH_SHORT).show();
        //Ban return button
    }

    @Override
    protected void onResume() {
        super.onResume();
        setProfileImage();
    }

    private void setProfileImage() {
        StorageReference profileRef = storageReference.child("user/" + currentUser.getUserId() + "/profile.jpg");

//        StorageReference profileRef = storageReference.child("user/" + User.getCurrentUserId() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileButton);
            }
        });
    }
}