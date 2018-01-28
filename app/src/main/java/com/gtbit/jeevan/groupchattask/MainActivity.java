package com.gtbit.jeevan.groupchattask;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etMessage;
    private Button sendButton;
    private RecyclerView chatList;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference messageDataBaseReference;
    private String username;
    private MyRecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMessage = findViewById(R.id.messageET);
        sendButton = findViewById(R.id.sendButton);
        chatList = findViewById(R.id.recyclerView);

        adapter = new MyRecyclerViewAdapter(new ArrayList<ChatMessage>(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        chatList.setAdapter(adapter);
        layoutManager.setStackFromEnd(true);
        chatList.setLayoutManager(layoutManager);

        if (FirebaseDatabase.getInstance() == null)
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        firebaseDatabase = FirebaseDatabase.getInstance();
        messageDataBaseReference = firebaseDatabase.getReference().child("messages");

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        username = pref.getString("username", "");

        etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty())
                    sendButton.setEnabled(false);
                else
                    sendButton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        messageDataBaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatMessage message = dataSnapshot.getValue(ChatMessage.class);
                adapter.add(message);
                chatList.scrollToPosition(adapter.getItemCount() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = etMessage.getText().toString().trim();
                ChatMessage chatMessage = new ChatMessage(username, message);
                messageDataBaseReference.push().setValue(chatMessage);
                etMessage.setText("");
            }
        });
    }

}
