package com.example.fitassistant.Models;

public class MessageModel {
    String messageId;
    String fromUser;
    String toUser;
    String message;

    public  MessageModel() { }

    public MessageModel(String messageId, String fromUser, String toUser, String message) {
        this.messageId = messageId;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getFromUser() {
        return fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public String getMessage() {
        return message;
    }
}
