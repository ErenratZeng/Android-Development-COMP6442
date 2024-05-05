package com.example.kangarun.adapter;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kangarun.R;
import com.example.kangarun.User;
import com.example.kangarun.UserListener;
import com.example.kangarun.activity.ChatActivity;
import com.example.kangarun.databinding.UserContainerBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private final List<User> users;
    private UserListener userListener;

    public UserAdapter(List<User> users, UserListener userListener) {
        this.users = users;
        this.userListener = userListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserContainerBinding b = UserContainerBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new UserViewHolder(b);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setUserData(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        UserContainerBinding binding;

        UserViewHolder(UserContainerBinding b) {
            super(b.getRoot());
            binding = b;
            binding.buttonMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // The adapter position can be used to get the user data from the list
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        User user = users.get(getAdapterPosition());
                        Intent intent = new Intent(v.getContext(), ChatActivity.class);
                        intent.putExtra("user", user);
                        v.getContext().startActivity(intent);
                    }
                }
            });

        }

        void setUserData(User user) {
            binding.textName.setText(user.getUsername());
            binding.textEmail.setText(user.getEmail());

            StorageReference fileRef = FirebaseStorage.getInstance().getReference()
                            .child("user/" + user.getUserId() + "/profile.jpg");
            // Attempt to get metadata to check if the file exists
            fileRef.getMetadata().addOnSuccessListener(storageMetadata -> {
                // Metadata exists, file should be there, attempt to load it
                fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    Picasso.get().load(uri).into(binding.imageProfile);
                }).addOnFailureListener(e -> {
                    // Handle error in getting download URL
                    Log.e("Storage", "Error getting download URL", e);
                    binding.imageProfile.setImageResource(R.drawable.profile_icon); // Set default or error image
                });
            }).addOnFailureListener(e -> {
                // File does not exist or other error in fetching metadata
                Log.d("Storage", "File does not exist, use default profile");
                binding.imageProfile.setImageResource(R.drawable.profile_icon); // Set default or error image
            });

            binding.getRoot().setOnClickListener(v -> userListener.onUserClicked(user));
        }
    }
}
