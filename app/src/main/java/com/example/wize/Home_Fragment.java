package com.example.wize;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.common.collect.Lists;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class Home_Fragment extends Fragment {
    Button addpost;
    List<String> list = Lists.newArrayList("App development", "Web development", "Object oriented programing","Ml/AI","Graphic design","Digital marketing","Photography","Finance","Cyber security","Communication skills","Entrepreneurship","3d and Game development","Science and research");
    RecyclerView rv;
    FirebaseAuth mAuth;
    AddHomeTagsAdapter addHomeTagsAdapter;
    RecyclerView post;
    postAdapter adapter;
    TextView edinter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public Home_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home_, container, false);
        addpost=view.findViewById(R.id.addpost);
        rv=view.findViewById(R.id.recyclerView2);
        post=view.findViewById(R.id.recyclerpost);
        mAuth =  FirebaseAuth.getInstance();
       edinter=view.findViewById(R.id.goeditinterfromhome);
       edinter.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(getActivity(),editinterests.class);
               intent.putExtra("from","home");
               startActivity(intent);
           }
       });
        addpost.setOnClickListener(new View.OnClickListener()
        {@Override
        public void onClick(View view) {
            startActivity(new Intent(getActivity(),Addposts.class));
        }
        });
        rv.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL));
        addHomeTagsAdapter=new AddHomeTagsAdapter(list);
        rv.setAdapter(addHomeTagsAdapter);
        CollectionReference cRef=db.collection("Posts");
        Query query = cRef.orderBy("timeStamp",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<postModel> options = new FirestoreRecyclerOptions.Builder<postModel>()
                .setQuery(query, postModel.class)
                .build();
        post.setLayoutManager(new LinearLayoutManager(getContext()));
        post.setItemAnimator(null);
         adapter=new postAdapter(options);
          post.setAdapter(adapter);

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