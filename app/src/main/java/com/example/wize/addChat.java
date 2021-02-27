package com.example.wize;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import co.ceryle.segmentedbutton.SegmentedButton;
import co.ceryle.segmentedbutton.SegmentedButtonGroup;

public class addChat extends AppCompatActivity {
SearchView search;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private AddChatAdapter adapter;
    SegmentedButton nm,unm;
    RecyclerView rview;
    SegmentedButtonGroup sb;
    ImageView cut;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chat);
        sb=findViewById(R.id.segmentedButtonGroup2);
        nm=findViewById(R.id.btnname);
        unm=findViewById(R.id.btnusername);
        search=findViewById(R.id.search);
        rview=findViewById(R.id.recycleraddchat);
        cut=findViewById(R.id.cutsearch);
        rview.setLayoutManager(new LinearLayoutManager(this));
        rview.setItemAnimator(null);
        searchfunc();
        sb.setOnClickedButtonListener(new SegmentedButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(int position) {
                if (position==0)
                {
                    searchfunc();
                }
                else
                {
                    search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String s) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String s) {
                            if (s.equals(""))
                            {
                                rview.setAdapter(null);
                            }
                            else {
                                Query query = db.collection("Users").orderBy("UserName").startAt(s).endAt(s+"\uf8ff");
                                FirestoreRecyclerOptions<AddChatModel> options = new FirestoreRecyclerOptions.Builder<AddChatModel>()
                                        .setQuery(query, AddChatModel.class)
                                        .build();
                                adapter = new AddChatAdapter(options);
                                adapter.startListening();
                                rview.setAdapter(adapter);
                            }

                            return true;
                        }
                    });

                }
            }
        });

        cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(addChat.this,HomeActivity.class));
                finish();
            }
        });

    }
    public void searchfunc()
    {
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.equals(""))
                {
                    rview.setAdapter(null);
                }
                else {
                    Query query = db.collection("Users").orderBy("Full_Name").startAt(s).endAt(s+"\uf8ff");
                    FirestoreRecyclerOptions<AddChatModel> options = new FirestoreRecyclerOptions.Builder<AddChatModel>()
                            .setQuery(query, AddChatModel.class)
                            .build();
                    adapter = new AddChatAdapter(options);
                    adapter.startListening();
                    rview.setAdapter(adapter);
                }

                return true;
            }
        });
    }

}