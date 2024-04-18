package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class ExerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        if (id == null || id.isEmpty()) {
            Toast.makeText(getApplicationContext(), "id is invalid", Toast.LENGTH_SHORT).show();
        }
    }
}