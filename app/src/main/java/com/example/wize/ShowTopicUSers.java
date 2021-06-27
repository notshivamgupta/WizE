package com.example.wize;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ShowTopicUSers extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    ShowTopicUSersAdapter adapter;
    RecyclerView users;
    String UserId;
    String name,key;
    TextView nameotopic;
    ImageView nousers;
    TextView usertxt,txt2;
    Long Nousers;
String a="0";
String b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_topic_u_sers);
        users=findViewById(R.id.recyclernousersgroup);
        nameotopic=findViewById(R.id.nameexploregroupsmembers);
        nousers=findViewById(R.id.imageViewgroup);
        usertxt=findViewById(R.id.textView34);
        txt2=findViewById(R.id.hello);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(false);
        users.setItemAnimator(null);
        users.setLayoutManager(linearLayoutManager);
        Intent intent=getIntent();
        name=intent.getStringExtra("nameoftopic");
        key=intent.getStringExtra("keyoftopic");
        Nousers=intent.getLongExtra("NumberOfUsers",0);
        b=Nousers.toString();
        nameotopic.setText(name);
        if (Nousers==0)
        {
            nousers.setVisibility(View.VISIBLE);
            usertxt.setVisibility(View.VISIBLE);
            txt2.setVisibility(View.VISIBLE);
        }
        else
        {


            UserId= FirebaseAuth.getInstance().getCurrentUser().getUid();
            CollectionReference cRef=db.collection("group").document(key).collection("Users");
            Query query = cRef.orderBy("Time", Query.Direction.DESCENDING);
            FirestoreRecyclerOptions<ShowTopicUSersModel> options = new FirestoreRecyclerOptions.Builder<ShowTopicUSersModel>()
                    .setQuery(query, ShowTopicUSersModel.class)
                    .build();
            adapter=new ShowTopicUSersAdapter(options);
            users.setAdapter(adapter);
            adapter.startListening();
            nousers.setVisibility(View.INVISIBLE);
            usertxt.setVisibility(View.INVISIBLE);
            txt2.setVisibility(View.INVISIBLE);

        }


    }
    @Override
    public void onStart() {
        super.onStart();
    }

}
