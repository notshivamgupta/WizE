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

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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

public class groupchat extends AppCompatActivity {
String userrec,username,userimg;
TextView nameontop;
ShapeableImageView uimg;
ImageView send;
EditText message;
    FirebaseFirestore db;
    String userId;
Long currentTime;
GroupSchowChatAdapter adapter;
RecyclerView recyclerView;
String Simage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupchat);
        Intent intent=getIntent();
        nameontop=findViewById(R.id.groupnamechat);
        db = FirebaseFirestore.getInstance();
        recyclerView=findViewById(R.id.recyclergroupchat);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        uimg=findViewById(R.id.uimgsendchat);
        message=findViewById(R.id.textforgroupmessage);
        send=findViewById(R.id.sendgroupchat);
        userrec=intent.getStringExtra("receivergroup");
        username=intent.getStringExtra("receivernamegroup");
        userimg=intent.getStringExtra("receiverimggroup");
        Uri profuri = Uri.parse(userimg);
        Picasso.get().load(profuri).into(uimg);
        nameontop.setText(username);
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid();


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("group").document(userrec).collection("chat").orderBy("time").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("Send Message", "Listen failed.", error);
                    return;
                }
                else
                {
                    List<Groupsmessagesmodel> messages = new ArrayList<>();

                    for (QueryDocumentSnapshot doc : value) {
                            messages.add(
                                    new Groupsmessagesmodel(
                                            doc.getString("message"),
                                            doc.getString("receiver"),
                                            doc.getString("sender"),
                                            doc.getString("Image")
                                    )
                            );

                    }

                    adapter = new GroupSchowChatAdapter(messages,userId);
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
                    DocumentReference documentReference= FirebaseFirestore.getInstance().collection("Users").document(userId);
                    documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            Simage=value.getString("profileImage");
                            currentTime = System.currentTimeMillis();

                            Map<String, Object> user = new HashMap<>();
                            user.put("message", mes);
                            user.put("receiver",userrec);
                            user.put("sender",userId);
                            user.put("time",currentTime);
                            user.put("Image",Simage);
                            db.collection("group").document(userrec).collection("chat").add(user);
                        }
                    });


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