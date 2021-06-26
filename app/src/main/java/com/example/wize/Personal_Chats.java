package com.example.wize;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Personal_Chats extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    Personalchat_Adapter adapter;
    RecyclerView chat;
    String UserId;
    public Personal_Chats() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
          View view=inflater.inflate(R.layout.fragment_personal__chats, container, false);
          chat=view.findViewById(R.id.recyclerforPersonalchats);
        chat.setLayoutManager(new LinearLayoutManager(getContext()));
        chat.setItemAnimator(null);
          UserId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        CollectionReference cRef=db.collection("Users").document(UserId).collection("Contacts");
        Query query = cRef.orderBy("Time", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<PersonalChat_Moel> options = new FirestoreRecyclerOptions.Builder<PersonalChat_Moel>()
                .setQuery(query, PersonalChat_Moel.class)
                .build();
        adapter=new Personalchat_Adapter(options);
        chat.setAdapter(adapter);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}