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

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Home_Fragment extends Fragment {
    Button addpost;
    FirebaseAuth mAuth;
    RecyclerView post;
    postAdapter adapter;
     Button appdev,webdev,oop,mlai,grapdgn,digmar,phot,fin,cybsec,comskill,entre,gamdev,scinrec;
    Query query;
    TextView edinter,all;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public Home_Fragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home_, container, false);
        addpost=view.findViewById(R.id.addpost);
        post=view.findViewById(R.id.recyclerpost);
        all=view.findViewById(R.id.textView20);
        appdev=view.findViewById(R.id.ap1);
        webdev=view.findViewById(R.id.wb1);
        oop=view.findViewById(R.id.oop1);
        mlai=view.findViewById(R.id.mlai1);
        grapdgn=view.findViewById(R.id.gd1);
        digmar=view.findViewById(R.id.dm1);
        phot=view.findViewById(R.id.p1);
        fin=view.findViewById(R.id.f1);
        cybsec=view.findViewById(R.id.cs1);
        comskill=view.findViewById(R.id.coms1);
        entre=view.findViewById(R.id.e1);
        gamdev=view.findViewById(R.id.gamed1);
        scinrec=view.findViewById(R.id.sr1);

        mAuth =  FirebaseAuth.getInstance();
        post.setLayoutManager(new LinearLayoutManager(getContext()));
        post.setItemAnimator(null);
        edinter=view.findViewById(R.id.goeditinterfromhome);
        edinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),editinterests.class);
                intent.putExtra("from","home");
                startActivity(intent);
            }
        });
        CollectionReference cRef=db.collection("Posts");
        query = cRef.orderBy("timeStamp",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<postModel> options = new FirestoreRecyclerOptions.Builder<postModel>()
                .setQuery(query, postModel.class)
                .build();
        adapter=new postAdapter(options);
        post.setAdapter(adapter);
        addpost.setOnClickListener(new View.OnClickListener()
        {@Override
        public void onClick(View view) {
            startActivity(new Intent(getActivity(),Addposts.class));
        }
        });
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query = cRef.orderBy("timeStamp",Query.Direction.DESCENDING);
                FirestoreRecyclerOptions<postModel> option = new FirestoreRecyclerOptions.Builder<postModel>()
                        .setQuery(query, postModel.class)
                        .build();
                adapter.updateOptions(option);
                post.setAdapter(adapter);
                changecolour();
                all.setBackgroundResource(R.drawable.hometagselected);
            }
        });
        appdev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changecolour();
                appdev.setBackgroundResource(R.drawable.hometagselected);
                setposts("App development");
            }
        });
        webdev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changecolour();
                webdev.setBackgroundResource(R.drawable.hometagselected);

                setposts("Web development");
            }
        });
        oop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changecolour();
                oop.setBackgroundResource(R.drawable.hometagselected);
                setposts("Object oriented programing");
            }
        });
        mlai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changecolour();
                mlai.setBackgroundResource(R.drawable.hometagselected);
                setposts("Ml/AI");
            }
        });
        grapdgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changecolour();
                grapdgn.setBackgroundResource(R.drawable.hometagselected);
                setposts("Graphic design");
            }
        });
        digmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changecolour();
                digmar.setBackgroundResource(R.drawable.hometagselected);
                setposts("Digital marketing");
            }
        });
        phot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changecolour();
                phot.setBackgroundResource(R.drawable.hometagselected);
                setposts("Photography");
            }
        });
        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changecolour();
                fin.setBackgroundResource(R.drawable.hometagselected);
                setposts("Finance");
            }
        });
        cybsec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changecolour();
                cybsec.setBackgroundResource(R.drawable.hometagselected);
                setposts("Cyber security");
            }
        });
        comskill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changecolour();
                comskill.setBackgroundResource(R.drawable.hometagselected);
                setposts("Communication skills");
            }
        });
        entre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changecolour();
                entre.setBackgroundResource(R.drawable.hometagselected);
                setposts("Entrepreneurship");
            }
        });
        gamdev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changecolour();
                gamdev.setBackgroundResource(R.drawable.hometagselected);
                setposts("3d and Game development");
            }
        });
        scinrec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changecolour();
                scinrec.setBackgroundResource(R.drawable.hometagselected);
                setposts("Science and research");
            }
        });
        return view;
    }

    public void changecolour()
    {
        appdev.setBackgroundResource(R.drawable.backhometagsrec);
        webdev.setBackgroundResource(R.drawable.backhometagsrec);
        oop.setBackgroundResource(R.drawable.backhometagsrec);
        mlai.setBackgroundResource(R.drawable.backhometagsrec);
        grapdgn.setBackgroundResource(R.drawable.backhometagsrec);
        digmar.setBackgroundResource(R.drawable.backhometagsrec);
        phot.setBackgroundResource(R.drawable.backhometagsrec);
        fin.setBackgroundResource(R.drawable.backhometagsrec);
        cybsec.setBackgroundResource(R.drawable.backhometagsrec);
        comskill.setBackgroundResource(R.drawable.backhometagsrec);
        entre.setBackgroundResource(R.drawable.backhometagsrec);
        gamdev.setBackgroundResource(R.drawable.backhometagsrec);
        scinrec.setBackgroundResource(R.drawable.backhometagsrec);
        all.setBackgroundResource(R.drawable.backhometagsrec);
    }
    public void setposts(String a)
    {
        CollectionReference cRef=db.collection("Posts");
        query = cRef.orderBy("timeStamp",Query.Direction.DESCENDING).whereEqualTo("tags",a);
        FirestoreRecyclerOptions<postModel> option = new FirestoreRecyclerOptions.Builder<postModel>()
                .setQuery(query, postModel.class)
                .build();
        adapter.updateOptions(option);
        post.setAdapter(adapter);
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