package com.example.kangarun;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class welcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome); // make sure the layout name is correct
        Log.d("ActivityStatus", "welcomeActivity started successfully");
        Button letsGoButton = findViewById(R.id.btnLetsGo);
        if (letsGoButton != null) {
            letsGoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(welcomeActivity.this, loginActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}


