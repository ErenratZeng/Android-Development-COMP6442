package com.example.kangarun;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kangarun.databinding.ReceivedMessageBinding;
import com.example.kangarun.databinding.SentMessageBinding;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVED = 2;
    private final Bitmap receiverImg;
    private final List<Message> messageList;
    private final String senderId;

    public ChatAdapter(Bitmap receiverImg, List<Message> messageList, String senderId) {
        this.receiverImg = receiverImg;
        this.messageList = messageList;
        this.senderId = senderId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENT) {
            return new SentMessageViewHolder(
                    SentMessageBinding.inflate(
                            LayoutInflater.from(parent.getContext()), parent, false)
            );
        } else {
            return new ReceivedMessageViewHolder(
                    ReceivedMessageBinding.inflate(
                            LayoutInflater.from(parent.getContext()), parent, false)
            );
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_SENT) {
            ((SentMessageViewHolder) holder).setMessage(messageList.get(position));
        } else {
            ((ReceivedMessageViewHolder) holder).setMessage(messageList.get(position), receiverImg);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (messageList.get(position).senderId.equals(senderId)) {
            return VIEW_TYPE_SENT;
        } else {
            return VIEW_TYPE_RECEIVED;
        }
    }

    static class SentMessageViewHolder extends RecyclerView.ViewHolder {
        private final SentMessageBinding binding;

        SentMessageViewHolder(SentMessageBinding sentMessageBinding) {
            super(sentMessageBinding.getRoot());
            binding = sentMessageBinding;
        }

        void setMessage(Message m) {
            binding.textMessage.setText(m.messageContent);
            binding.textTime.setText(m.datetime);
        }
    }

    static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
        private final ReceivedMessageBinding binding;

        ReceivedMessageViewHolder(ReceivedMessageBinding receivedMessageBinding) {
            super(receivedMessageBinding.getRoot());
            binding = receivedMessageBinding;
        }

        void setMessage(Message m, Bitmap img) {
            binding.textMessage.setText(m.messageContent);
            binding.textTime.setText(m.datetime);
            binding.imageProfile.setImageBitmap(img);
        }
    }
}
