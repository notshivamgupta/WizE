package com.example.wize;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class Explore extends Fragment {
    RecyclerView alltopics;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    exploreAdapter adapter;
    public Explore() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_explore, container, false);
        alltopics=view.findViewById(R.id.allexploretopics);
        alltopics.setLayoutManager(new GridLayoutManager(getContext(),2));
        CollectionReference cRef=db.collection("group");
        Query query = cRef.orderBy("name");
        FirestoreRecyclerOptions<exploreModel> options = new FirestoreRecyclerOptions.Builder<exploreModel>()
                .setQuery(query, exploreModel.class)
                .build();
        adapter=new exploreAdapter(options);
        alltopics.setAdapter(adapter);

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
       // adapter.stopListening();
    }
}