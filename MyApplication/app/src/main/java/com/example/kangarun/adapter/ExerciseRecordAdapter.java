package com.example.kangarun.adapter;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kangarun.activity.ExerciseRecordDetailActivity;
import com.example.kangarun.R;
import com.example.kangarun.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class ExerciseRecordAdapter extends BaseAdapter<ExerciseRecordAdapter.RecordViewHolder> {

    private List<DocumentSnapshot> recordsList;

    public ExerciseRecordAdapter(List<DocumentSnapshot> recordsList) {
        this.recordsList = recordsList;
    }

    @Override
    protected RecordViewHolder createView(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false);
        return new RecordViewHolder(view);
    }

    @Override
    protected void bindView(RecordViewHolder holder, int position) {
        DocumentSnapshot document = recordsList.get(position);
        String date = document.getString("date");
        double distance = document.getDouble("distance");
        String duration = document.getString("duration");
        double calories = document.getDouble("calories");
        Log.d("Adapter", position + " " + date + " " + distance + " " + duration + " " + calories);

        String path = "exerciseRecord/" + User.getCurrentUserId() + date + "/mapSnapshot.png";
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = storageRef.child(path);

        holder.textViewDate.setText(date);
        holder.textViewDistance.setText("Distance: " + distance + " km");
        holder.textViewDuration.setText("Duration: " + duration);
        holder.textViewCalories.setText("Calories: " + calories + " kcal");

        holder.itemView.setOnClickListener(v -> {
            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // 创建 Intent 并开始新的 Activity
                    Intent intent = new Intent(holder.itemView.getContext(), ExerciseRecordDetailActivity.class);
                    intent.putExtra("date", date);
                    intent.putExtra("distance", String.valueOf(distance));
                    intent.putExtra("duration", duration);
                    intent.putExtra("calories", String.valueOf(calories));
                    intent.putExtra("imagePath", uri.toString()); // 传递图片的下载 URL
                    holder.itemView.getContext().startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("FirebaseStorage", "Error getting the image URL", e);
                    // 在这里处理错误，比如通知用户图片加载失败
                    Toast.makeText(holder.itemView.getContext(), "Failed to load image", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    protected int getDataCount() {
        return recordsList.size();
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.record_item; // 提供布局 ID
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

