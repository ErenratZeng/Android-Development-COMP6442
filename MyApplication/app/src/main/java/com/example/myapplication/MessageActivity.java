package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class MessageActivity extends AppCompatActivity {

    private EditText editTextMessage;
    private ArrayAdapter<String> adapter;

    // Dummy users for the purpose of this example
    private User currentUser = new User("User1", "password1");
    private User otherUser = new User("User2", "password11");
    private String otherUserId = "User2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        ListView listViewMessages = findViewById(R.id.listViewMessages);
        editTextMessage = findViewById(R.id.editTextMessage);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listViewMessages.setAdapter(adapter);

        findViewById(R.id.buttonSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = editTextMessage.getText().toString();
                if (!messageText.isEmpty()) {
                    // Assume the current user is sending the message
                    currentUser.sendMessage(otherUserId, messageText);

                    // For display purposes, show the message in the ListView
                    adapter.add("Me: " + messageText);
                    adapter.notifyDataSetChanged();

                    // Clear the input field for the next message
                    editTextMessage.setText("");
                }
            }
        });
    }
}
