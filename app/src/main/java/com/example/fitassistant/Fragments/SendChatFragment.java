package com.example.fitassistant.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitassistant.Models.MessageModel;
import com.example.fitassistant.Providers.AuthProvider;
import com.example.fitassistant.Providers.ChatProvider;
import com.example.fitassistant.R;

public class SendChatFragment extends Fragment {
    private EditText toUserEmail;
    private EditText message;
    private Button sendMessage;
    private MessageModel newMessage;
    private AuthProvider authProvider;
    private ChatProvider chatProvider;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newMessage = new MessageModel();
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
        getActivity().setTitle(getString(R.string.new_message));
        toUserEmail = view.findViewById(R.id.touser_email);
        message = view.findViewById(R.id.message_et);
        sendMessage = view.findViewById(R.id.send_message);
        //Set message fields
        long tsLong = System.currentTimeMillis()/1000;
        newMessage.setMessageId(Long.toString(tsLong));
        newMessage.setFromUser(authProvider.getUserEmail());
        newMessage.setToUser(toUserEmail.getText().toString());
        newMessage.setMessage(message.getText().toString());
        //Set action on button click
        sendMessage.setOnClickListener(
                v -> {
                    chatProvider.sendMessage(newMessage);
                }
        );
    }
}
