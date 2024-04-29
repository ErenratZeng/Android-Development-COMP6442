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
        DocumentSnapshot document = recordsList.get(position);
        try {
            double distance = document.getDouble("distance"); // 尝试获取距离
            holder.textView.setText(String.valueOf(distance));
        } catch (Exception e) {
            Log.e("Adapter", "Error reading document", e);
            holder.textView.setText("Error!"); // 提供错误信息或备选内容
        }
    }

    @Override
    public int getItemCount() {
        return recordsList.size();
    }

    static class RecordViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}

