package com.example.wize;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class sendChat extends AppCompatActivity {
ImageView send;
EditText message;
String userrec,userimg,username;
    Long currentTime;
    CircleImageView uimg;
    TextView nameontop;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String userId;
    getMessageAdapter adapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_chat);
        send=findViewById(R.id.sendmessage);
        message=findViewById(R.id.sendchat);
        uimg=findViewById(R.id.uimgsendchat);
        nameontop=findViewById(R.id.namesendchat);
        recyclerView=findViewById(R.id.recyclersendchat);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        Intent intent=getIntent();

        userrec=intent.getStringExtra("receiver");
        username=intent.getStringExtra("receivername");
        userimg=intent.getStringExtra("receiverimg");
        Uri profuri = Uri.parse(userimg);
        Picasso.get().load(profuri).into(uimg);
        nameontop.setText(username);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Chats").orderBy("time").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("Send Message", "Listen failed.", error);
                    return;
                }
                else
                {
                    List<getMessageModel> messages = new ArrayList<>();

                    for (QueryDocumentSnapshot doc : value) {
                        if ((doc.getString("receiver").equals(userrec) && doc.getString("sender").equals(userId)) || (doc.getString("sender").equals(userrec) && doc.getString("receiver").equals(userId)))
                            messages.add(
                                    new getMessageModel(
                                            doc.getString("message"),
                                            doc.getString("receiver"),
                                            doc.getString("sender")
                                    )
                            );

                    }

                    adapter = new getMessageAdapter(messages,userId);
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mes=message.getText().toString();
                if (!mes.equals(""))
                {
                    currentTime = System.currentTimeMillis();

                    Map<String, Object> user = new HashMap<>();
                    user.put("message", mes);
                    user.put("receiver",userrec);
                    user.put("sender",userId);
                    user.put("time",currentTime);
                    String id = db.collection("Chats").document().getId();
                    db.collection("Chats").document(id).set(user);
                }
                else
                {
                    Snackbar.make(view, "Message is empty....", Snackbar.LENGTH_LONG).show();
                }
                message.setText("");

            }
        });

    }
}