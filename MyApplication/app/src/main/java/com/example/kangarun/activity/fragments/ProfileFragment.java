package com.example.kangarun.activity.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kangarun.User;
import com.example.kangarun.databinding.FragmentProfileBinding;
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

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();

        loadProfileImage();
        loadUserInfo();

        binding.uploadImageButton.setOnClickListener(v -> {
            ImagePicker.with(ProfileFragment.this)
                    .crop()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .start();
        });

        return binding.getRoot();
    }

    private void loadProfileImage() {
        StorageReference profileRef = storageReference.child("user/" + User.getCurrentUserId() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(binding.profileImageView));
    }

    private void loadUserInfo() {
        DocumentReference documentReference = firebaseFirestore.collection("user").document(User.getCurrentUserId());
        documentReference.addSnapshotListener((value, error) -> {
            if (value != null) {
                binding.username.setText(value.getString("username"));
                binding.useremail.setText(value.getString("email"));
                binding.usergender.setText(value.getString("gender"));
                binding.userweight.setText(String.valueOf(value.getDouble("weight")));
                binding.userheight.setText(String.valueOf(value.getDouble("height")));
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getData() != null) {
            Uri uri = data.getData();
            uploadPictureToFirebase(uri);
        }
    }

    private void uploadPictureToFirebase(Uri pictureUri) {
        StorageReference fileRef = storageReference.child("user/" + User.getCurrentUserId() + "/profile.jpg");
        fileRef.putFile(pictureUri).addOnSuccessListener(taskSnapshot -> {
            Toast.makeText(getContext(), "Picture uploaded", Toast.LENGTH_SHORT).show();
            fileRef.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(binding.profileImageView));
        }).addOnFailureListener(e -> Toast.makeText(getContext(), "Picture upload failed", Toast.LENGTH_SHORT).show());
    }
}
