package com.example.kangarun;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class ExerciseRecordAdapter extends RecyclerView.Adapter<ExerciseRecordAdapter.RecordViewHolder> {

    private List<DocumentSnapshot> recordsList;

    public ExerciseRecordAdapter(List<DocumentSnapshot> recordsList) {
        this.recordsList = recordsList;
    }

    public void updateData(List<DocumentSnapshot> newData) {
        Log.d("Adapter", "Updating data with " + newData.size() + " records");
        recordsList.clear();
        recordsList.addAll(newData);
        notifyDataSetChanged();  // 通知数据已更改
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item, parent, false);
        return new RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecordViewHolder holder, int position) {
        DocumentSnapshot document = recordsList.get(recordsList.size() - 1 - position);
        String date = document.getString("date");
        double distance = document.getDouble("distance");
        String duration = document.getString("duration");
        double calories = document.getDouble("calories");
        Log.d("Adapter", position + " " + date + distance + duration + calories);

        holder.textViewDate.setText("Date: " + date);
        holder.textViewDistance.setText("Distance: " + distance + " km");
        holder.textViewDuration.setText("Duration: " + duration);
        holder.textViewCalories.setText("Calories: " + calories + " kcal");
    }

    @Override
    public int getItemCount() {
        Log.d("Adapter", "size = " + recordsList.size());
        return recordsList.size();
    }

    static class RecordViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDistance;
        TextView textViewDuration;
        TextView textViewCalories;
        TextView textViewDate;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDistance = itemView.findViewById(R.id.textViewDistance);
            textViewDuration = itemView.findViewById(R.id.textViewDuration);
            textViewCalories = itemView.findViewById(R.id.textViewCalories);
            textViewDate = itemView.findViewById(R.id.textViewDate);
        }
    }
}

