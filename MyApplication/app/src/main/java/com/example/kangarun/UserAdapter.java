package com.example.kangarun;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kangarun.databinding.UserContainerBinding;

import java.util.Base64;
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
        if (users.get(position) == null) {
            throw new RuntimeException("dwadwa");
        }
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
        }

        void setUserData(User user) {
            binding.textName.setText(user.getUsername());
            binding.textEmail.setText(user.getEmail());
            //binding.imageProfile.setImageBitmap(getBitmapFromEncoded(user.getProfilePicture()));TODO
            binding.getRoot().setOnClickListener(v -> userListener.onUserClicked(user));
        }
    }

    private Bitmap getBitmapFromEncoded(String encoded) {
        byte[] bytes = Base64.getDecoder().decode(encoded);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
