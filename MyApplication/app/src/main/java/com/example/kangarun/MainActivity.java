package com.example.kangarun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startExerciseButton = findViewById(R.id.startExerciseButton);
        startExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Start Exercise", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

        Button exerciseRecordButton = findViewById(R.id.exerciseRecordButton);
        exerciseRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Exercise Record", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ExerciseRecordActivity.class);
                startActivity(intent);
            }
        });

        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), loginActivity.class));
                finish();
            }
        });

        ImageView profileButton = findViewById(R.id.imageView);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "User Profile", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                intent.putExtra("user", new User("Alice", "password123"));
                startActivity(intent);
            }
        });

        Button dmButton = findViewById(R.id.dmButton);
        dmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Message", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                // TODO: change the argument to User and remove the dummy
                intent.putExtra("user", new User("Alice", "password123"));
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You cannot return to last page", Toast.LENGTH_SHORT).show();
    }
}