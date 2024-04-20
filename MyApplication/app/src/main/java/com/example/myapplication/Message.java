package com.example.myapplication;

public class Message {
    private String senderId;
    private String recipientId;
    private String messageContent;
    private long timestamp; // Create timestamp on send

    public Message(String senderId, String recipientId, String messageContent, long timestamp) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.messageContent = messageContent;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters and Setters
    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
