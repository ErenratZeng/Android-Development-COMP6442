package com.example.myapplication;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class loginActivity extends AppCompatActivity {

    private EditText editTextUserId;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Find the Views in the layout
        editTextUserId = findViewById(R.id.editTextUserId);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonCreateAccount = findViewById(R.id.buttonCreateAccount);

        // Apply insets for system windows
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        // Handle the login button click
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input from EditTexts
                String userId = editTextUserId.getText().toString();
                String password = editTextPassword.getText().toString();

                // Here you would check the credentials against your user database or authentication server
                // For now, we just check if the fields are not empty as an example
                if (!userId.isEmpty() && !password.isEmpty()) {
                    // If not empty, show a success message
                    Toast.makeText(loginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    // TODO: Proceed to the next screen or main application
                } else {
                    // If empty, show an error message
                    Toast.makeText(loginActivity.this, "Please enter user ID and password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Handle the create account button click
        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Here you would navigate to the registration screen
                // For now, we just show a message
                Toast.makeText(loginActivity.this, "Navigate to registration", Toast.LENGTH_SHORT).show();
                // TODO: Implement actual navigation to the registration activity
            }
        });
    }
}
