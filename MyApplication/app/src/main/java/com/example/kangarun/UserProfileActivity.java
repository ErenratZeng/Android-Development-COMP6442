package com.example.kangarun;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.kangarun.databinding.ActivityUserProfileBinding;
import com.example.kangarun.utils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.ktx.Firebase;

public class UserProfileActivity extends AppCompatActivity {
    private ActivityUserProfileBinding binding;
    private User receiver;
    ImageView profilePic;
    EditText usernameInput;
    User currentUser;

    public UserProfileActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        profilePic = binding.profileImageView;
        usernameInput = binding.username;

        getUserData();
    }


//    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
//        View view = inflater.inflate(R.layout.activity_user_profile, container, false);
//        profilePic = view.findViewById(R.id.profile_image_view);
//        usernameInput = view.findViewById(R.id.username);
//        getUserData();
//        return view;
//    }

    void getUserData(){
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(task ->  {
//            currentUser = task.getResult().toObject(User.class);
//            usernameInput.setText(currentUser.getUsername());
            if (task.isSuccessful() && task.getResult() != null) {
                currentUser = task.getResult().toObject(User.class);
                if (currentUser != null) {
                    Log.d("UserProfile", "Username: " + currentUser.getUsername());
                    usernameInput.setText(currentUser.getUsername());
                } else {
                    Log.d("UserProfile", "No user data found.");
                    Toast.makeText(UserProfileActivity.this, "No user data found.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.d("UserProfile", "Failed to fetch user data: " + task.getException());
                Toast.makeText(UserProfileActivity.this, "Failed to fetch user data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}