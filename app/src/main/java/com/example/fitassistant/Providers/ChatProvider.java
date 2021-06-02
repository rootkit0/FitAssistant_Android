package com.example.fitassistant.Providers;

import com.example.fitassistant.Models.MessageModel;
import com.example.fitassistant.Other.Constants;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChatProvider {
    private FirebaseDatabase database;

    public ChatProvider() {
        database = FirebaseDatabase.getInstance(Constants.databaseUrl);
    }

    public DatabaseReference messagesReference() {
        return database.getReference(Constants.messagesPath);
    }

    public List<MessageModel> getMessagesByUserEmail(DataSnapshot snapshot, String userEmail) {
        ArrayList<MessageModel> messages = new ArrayList<>();
        if (snapshot.exists()) {
            for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                MessageModel message = messageSnapshot.getValue(MessageModel.class);
                if (message.getToUser().equals(userEmail) || message.getFromUser().equals(userEmail)) {
                    messages.add(message);
                }
            }
        }
        return messages;
    }
}
