package com.example.kangarun;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

public class ExerciseRecordDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_record_detail);

        TextView textViewDate = findViewById(R.id.textViewDate);
        TextView textViewDistance = findViewById(R.id.textViewDistance);
        TextView textViewDuration = findViewById(R.id.textViewDuration);
        TextView textViewCalories = findViewById(R.id.textViewCalories);
        ImageView imageViewMapSnapshot = findViewById(R.id.imageViewMapSnapshot);

        //
        String date = getIntent().getStringExtra("date");
        String distance = getIntent().getStringExtra("distance");
        String duration = getIntent().getStringExtra("duration");
        String calories = getIntent().getStringExtra("calories");
        String imagePath = getIntent().getStringExtra("imagePath");

        textViewDate.setText("Date " + date);
        textViewDistance.setText("Distance: "+distance+" km");
        textViewDuration.setText("Duration: "+duration);
        textViewCalories.setText("Calories: " + calories +" kcal");

        // Load images from Firebase Storage
        Picasso.get().load(imagePath).into(imageViewMapSnapshot);

    }
}