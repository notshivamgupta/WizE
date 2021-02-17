package com.example.wize;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.common.collect.Lists;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Home_Fragment extends Fragment {
    Button addpost;
    List<String> list = Lists.newArrayList("App development", "Web development", "Object oriented programing","Ml/AI","Graphic design","Digital marketing","Photography","Finance","Cyber security","Communication skills","Entrepreneurship","3d and Game development","Science and research");
    RecyclerView rv;
    AddHomeTagsAdapter addHomeTagsAdapter;
    public Home_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_home_, container, false);
       addpost=view.findViewById(R.id.addpost);
       rv=view.findViewById(R.id.recyclerView2);
       addpost.setOnClickListener(new View.OnClickListener()
       {@Override
       public void onClick(View view) {
           startActivity(new Intent(getActivity(),Addposts.class));
       }
       });
        rv.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL));
        addHomeTagsAdapter=new AddHomeTagsAdapter(list);
        rv.setAdapter(addHomeTagsAdapter);
        return view;
    }
}