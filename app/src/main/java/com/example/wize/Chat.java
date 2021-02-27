package com.example.wize;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class Chat extends Fragment {
TextView txt;
RecyclerView showchat;
String user;
CollectionReference db;
    List<String> userlist;
    String lastmessage;
    public Chat() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_chat, container, false);
       txt=view.findViewById(R.id.gotoaddchat);
       showchat=view.findViewById(R.id.showallchat);
       userlist=new ArrayList<>();
       user= FirebaseAuth.getInstance().getCurrentUser().getUid();
       db= FirebaseFirestore.getInstance().collection("Chats");
        db.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("Send Message", "Listen failed.", error);
                    return;
                }
                else
                {
                    List<String> messages = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : value) {
                        if (doc.getString("sender").equals(user))
                        {
                            int flag =0;
                            for(String s:messages)
                            {
                                if (s.equals(doc.getString("receiver")))
                                {
                                    flag=1;
                                    break;
                                }
                            }
                            if(flag==0)
                            messages.add(doc.getString("receiver"));
                        }
                        if (doc.getString("receiver").equals(user))
                        {
                            int flag=0;
                            for(String s:messages)
                            {
                                if (s.equals(doc.getString("sender"))){
                                    flag=1;
                                    break;
                                }
                            }
                            if(flag==0)
                            messages.add(doc.getString("sender"));
                        }
                    }
                }
            }
        });
        
       txt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(getActivity(),addChat.class));
           }
       });
        return view;
    }
}