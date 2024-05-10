package com.example.kangarun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kangarun.R;
import com.example.kangarun.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword, editTextUserName, editTextGender, editTextWeight, editTextHeight;
    private Button buttonRegister;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private double weight;
    private double height;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextGender = findViewById(R.id.editTextGender);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextHeight = findViewById(R.id.editTextHeight);
        buttonRegister = findViewById(R.id.buttonRegister);

        firebaseAuth = FirebaseAuth.getInstance();
        User currentuser = User.getInstance();

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String userName = editTextUserName.getText().toString();
                String gender = editTextGender.getText().toString();
                weight = 0;
                height = 0;

                try {
                    weight = Double.parseDouble(editTextWeight.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(RegisterActivity.this, "Invalid weight", Toast.LENGTH_SHORT).show();
                }

                try {
                    height = Double.parseDouble(editTextHeight.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(RegisterActivity.this, "Invalid height", Toast.LENGTH_SHORT).show();
                }

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password must be longer than 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                            currentuser.setUsername(userName);
                            currentuser.setEmail(email);
                            currentuser.setUserId(currentuser.getCurrentUserId());
                            currentuser.setGender(gender);
                            currentuser.setWeight(weight);
                            currentuser.setHeight(height);
                            currentuser.uploadProfile();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(RegisterActivity.this, "Account Created Failed " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            //TODO Fixed bug for message
                        }
                    }
                });
            }
        });
    }
}
