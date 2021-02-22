package com.example.wize;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class comment extends AppCompatActivity {
ImageView back,send;
RecyclerView recomment;
EditText comment;
String key;
String user;
Long ncomments;
commentAdapter adapter;
FirebaseFirestore fStore;
 String name;
    String cm=null;
    Long currentTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        back=findViewById(R.id.backfromcomment);
        recomment=findViewById(R.id.recyclercomment);
        send=findViewById(R.id.sendcomment);
        comment=findViewById(R.id.textforcomment);
        currentTime = System.currentTimeMillis();
        recomment.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recomment.setItemAnimator(null);
         user= FirebaseAuth.getInstance().getCurrentUser().getUid();
        Intent intent=getIntent();
        key=intent.getStringExtra("postid");
        ncomments=intent.getLongExtra("com",0);
        fStore= FirebaseFirestore.getInstance();
        CollectionReference cRef=fStore.collection("Posts").document(key).collection("Comments");
        Query query = cRef.orderBy("time",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<commentModel> options = new FirestoreRecyclerOptions.Builder<commentModel>()
                .setQuery(query, commentModel.class)
                .build();
        adapter=new commentAdapter(options);
        recomment.setAdapter(adapter);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(comment.this,HomeActivity.class));
                finish();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               cm=comment.getText().toString();
                if (cm.equals(""))
                {
                    Snackbar.make(view, "Comment is empty....", Snackbar.LENGTH_LONG).show();
                }
                else
                {
                    DocumentReference documentReference=fStore.collection("Users").document(user);
                    documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            name=value.getString("Full_Name");
                            Map<String, Object> cp = new HashMap<>();
                            cp.put("userCommented", user);
                            cp.put("Comment",cm);
                            cp.put("cName",name);
                            cp.put("time",currentTime.toString());
                            FirebaseFirestore.getInstance().collection("Posts")
                                    .document(key)
                                    .collection("Comments")
                                    .add(cp).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Map<String,Object> add=new HashMap<>();
                                    add.put("nComments",ncomments+1);
                                    FirebaseFirestore.getInstance().collection("Posts").document(key).update(add);
                                }
                            });
                        }
                    });
                    comment.setText(null);
                }
            }

        });
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