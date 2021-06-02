package com.example.fitassistant.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitassistant.Models.MessageModel;
import com.example.fitassistant.Providers.AuthProvider;
import com.example.fitassistant.Providers.ChatProvider;
import com.example.fitassistant.R;

import java.util.Objects;

public class SendChatFragment extends Fragment {
    private EditText userEmail;
    private EditText message;
    private AuthProvider authProvider;
    private ChatProvider chatProvider;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authProvider = new AuthProvider();
        chatProvider = new ChatProvider();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sendchat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Objects.requireNonNull(getActivity()).setTitle(getString(R.string.new_message));
        userEmail = view.findViewById(R.id.touser_email);
        message = view.findViewById(R.id.message_et);
        Button sendMessage = view.findViewById(R.id.send_message);
        //Set action on button click
        sendMessage.setOnClickListener(
                v -> {
                    //Timestamp as messageId
                    long tsLong = System.currentTimeMillis();
                    //Set message model
                    MessageModel newMessage = new MessageModel(String.valueOf(tsLong), authProvider.getUserEmail(), authProvider.getUserId(), userEmail.getText().toString(), message.getText().toString());
                    chatProvider.messagesReference().child(newMessage.getMessageId()).setValue(newMessage);
                    Toast.makeText(getContext(), R.string.message_sent, Toast.LENGTH_SHORT).show();
                });
    }
}
