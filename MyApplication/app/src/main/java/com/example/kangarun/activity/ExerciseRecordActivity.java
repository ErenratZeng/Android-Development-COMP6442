package com.example.kangarun.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kangarun.LoginState;
import com.example.kangarun.R;
import com.example.kangarun.User;
import com.example.kangarun.adapter.ExerciseRecordAdapter;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Bingnan Zhao u6508459,Qiutong Zeng u7724723,Heng Sun u7611510
 */
public class ExerciseRecordActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private boolean dateDescending;
    private boolean distanceDescending;
    private boolean durationDescending;

    private List<DocumentSnapshot> list;
    private List<DocumentSnapshot> dateDeslist;
    private List<DocumentSnapshot> dateAsclist;
    private List<DocumentSnapshot> distanceDeslist;
    private List<DocumentSnapshot> distanceAsclist;
    private List<DocumentSnapshot> durationDeslist;
    private List<DocumentSnapshot> durationAsclist;

    private ExerciseRecordAdapter adapter;
    private Button sortByDateButton;
    private Button sortByDistanceButton;
    private Button sortByDurationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list = new ArrayList<>();

        dateDescending = true;
        distanceDescending = false;
        durationDescending = false;

        setContentView(R.layout.activity_exercise_record);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.exercise_record_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize RecyclerView and adapter
        RecyclerView recyclerView = findViewById(R.id.exerciseRecordView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExerciseRecordAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        CollectionReference records = db.collection("exerciseRecord");
        LoginState currentUser = LoginState.getInstance();
        String uid = currentUser.getUserId();
        //String uid = User.getCurrentUserId();
        Log.d("ExerciseRecord", "uid:" + uid);
        if (uid != null) {
            Query userRecords = records.whereEqualTo("uid", uid);
            userRecords.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(document);
                    }
                    Log.d("Firestore", "Total documents fetched: " + list.size());
                    dateAsclist = new ArrayList<>(list);
                    distanceAsclist = new ArrayList<>(list);
                    durationAsclist = new ArrayList<>(list);
                    Collections.sort(dateAsclist, new Comparator<DocumentSnapshot>() {
                        @Override
                        public int compare(DocumentSnapshot doc1, DocumentSnapshot doc2) {
                            String date1 = doc1.contains("date") ? doc1.getString("date") : "0";
                            String date2 = doc2.contains("date") ? doc2.getString("date") : "0";
                            return date1.compareTo(date2);
                        }
                    });
                    dateDeslist =  new ArrayList<>(dateAsclist);
                    Collections.reverse(dateDeslist);
                    Collections.sort(distanceAsclist, new Comparator<DocumentSnapshot>() {
                        @Override
                        public int compare(DocumentSnapshot doc1, DocumentSnapshot doc2) {
                            double distance1 = doc1.contains("distance") ? doc1.getDouble("distance") : 0;
                            double distance2 = doc2.contains("distance") ? doc2.getDouble("distance") : 0;
                            return Double.compare(distance1, distance2);
                        }
                    });
                    distanceDeslist = new ArrayList<>(distanceAsclist);
                    Collections.reverse(distanceDeslist);
                    Collections.sort(durationAsclist, new Comparator<DocumentSnapshot>() {
                        @Override
                        public int compare(DocumentSnapshot doc1, DocumentSnapshot doc2) {
                            String duration1 = doc1.contains("duration") ? doc1.getString("duration") : "0";
                            String duration2 = doc2.contains("duration") ? doc2.getString("duration") : "0";
                            return duration1.compareTo(duration2);
                        }
                    });
                    durationDeslist = new ArrayList<>(durationAsclist);
                    Collections.reverse(durationDeslist);
                    adapter.updateData(dateDeslist);
                } else {
                    Log.d("Firestore", "Error getting documents: ", task.getException());
                }
            });

        }
        sortByDateButton = findViewById(R.id.SortByDateButton);
        sortByDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Sort By Date", Toast.LENGTH_SHORT).show();
                if(!dateAsclist.isEmpty() && !dateDeslist.isEmpty())
                    adapter.updateData(dateDescending? dateAsclist : dateDeslist);
                dateDescending = !dateDescending;
                toggleSortDirection(dateDescending, sortByDateButton);
            }
        });
        sortByDistanceButton = findViewById(R.id.SortByDistanceButton);
        sortByDistanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Sort By Distance", Toast.LENGTH_SHORT).show();
                if(!distanceAsclist.isEmpty() && !distanceDeslist.isEmpty())
                    adapter.updateData(distanceDescending? distanceAsclist : distanceDeslist);
                distanceDescending = !distanceDescending;
                toggleSortDirection(distanceDescending, sortByDistanceButton);
            }
        });
        sortByDurationButton = findViewById(R.id.SortByDurationButton);
        sortByDurationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Sort By Duration", Toast.LENGTH_SHORT).show();
                if(!durationAsclist.isEmpty() && !durationDeslist.isEmpty())
                    adapter.updateData(durationDescending? durationAsclist : durationDeslist);
                durationDescending = !durationDescending;
                toggleSortDirection(durationDescending, sortByDurationButton);
            }
        });

        EdgeToEdge.enable(this);
    }
    private void toggleSortDirection(boolean descending, Button button) {
        sortByDateButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        sortByDistanceButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        sortByDurationButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        if (descending) {
            button.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_descending, 0);
        } else {
            button.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_ascending, 0);
        }
    }
}