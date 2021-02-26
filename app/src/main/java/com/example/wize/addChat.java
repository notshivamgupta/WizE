package com.example.wize;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class addChat extends AppCompatActivity {
SearchView search;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private AddChatAdapter adapter;
    RecyclerView rview;
    ImageView cut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chat);
        search=findViewById(R.id.search);
        rview=findViewById(R.id.recycleraddchat);
        cut=findViewById(R.id.cutsearch);
        rview.setLayoutManager(new LinearLayoutManager(this));
        rview.setItemAnimator(null);
        cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(addChat.this,HomeActivity.class));
                finish();
            }
        });
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