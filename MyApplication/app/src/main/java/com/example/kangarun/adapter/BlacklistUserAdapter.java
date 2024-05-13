package com.example.kangarun.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kangarun.R;
import com.example.kangarun.User;
import com.example.kangarun.UserListener;
import com.example.kangarun.databinding.ItemContainerBlacklistUserBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BlacklistUserAdapter extends RecyclerView.Adapter<BlacklistUserAdapter.UserViewHolder> {

    private final List<User> users;
    private final UserListener userListener;

    public BlacklistUserAdapter(List<User> users, UserListener userListener) {
        this.users = users;
        this.userListener = userListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerBlacklistUserBinding binding = ItemContainerBlacklistUserBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.setUserData(user);

        holder.binding.buttonUnblock.setOnClickListener(v -> {
            if (userListener != null) {
                userListener.onUserUnblocked(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        ItemContainerBlacklistUserBinding binding;

        UserViewHolder(ItemContainerBlacklistUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setUserData(User user) {
            binding.textName.setText(user.getUsername());
            binding.textEmail.setText(user.getEmail());
            binding.imageProfile.setImageResource(R.drawable.profile_icon); // Set default image
            binding.getRoot().setOnClickListener(v -> userListener.onUserClicked(user));
        }
    }
}
