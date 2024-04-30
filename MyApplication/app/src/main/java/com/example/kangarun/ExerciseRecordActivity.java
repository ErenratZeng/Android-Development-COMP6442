package com.example.kangarun;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ExerciseRecordActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_exercise_record);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 初始化RecyclerView和适配器
        RecyclerView recyclerView = findViewById(R.id.exerciseRecordView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ExerciseRecordAdapter adapter = new ExerciseRecordAdapter(new ArrayList<>()); // 先设置一个空的Adapter
        recyclerView.setAdapter(adapter);

        CollectionReference records = db.collection("exerciseRecord");
        String uid = User.getCurrentUserId();
        Log.d("ExerciseRecord", "uid:" + uid);
        if (uid != null) {
            Query userRecords = records.whereEqualTo("uid", uid);
            userRecords.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(document);
                    }
                    Log.d("Firestore", "Total documents fetched: " + list.size());
                    adapter.updateData(list);
                } else {
                    Log.d("Firestore", "Error getting documents: ", task.getException());
                }
            });
        }

        EdgeToEdge.enable(this);
    }
}