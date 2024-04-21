package com.example.myapplication;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Message {
    private String senderId;
    private String recipientId;
    private String messageContent;
    private String datetime; // Create timestamp on send

    public Message(String senderId, String recipientId, String messageContent, long timestamp) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.messageContent = messageContent;
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.datetime = myDateObj.format(myFormatObj);
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

    public String getDatetime() {
        return datetime;
    }


}
