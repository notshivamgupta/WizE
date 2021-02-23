package com.example.wize;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class comment extends AppCompatActivity {
ImageView back,send;
RecyclerView recomment;
EditText comment;
String key;
String user;
commentAdapter adapter;
FirebaseFirestore fStore;
 String name;
    String cm=null;
    Long currentTime;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        back=findViewById(R.id.backfromcomment);
        recomment=findViewById(R.id.recyclercomment);
        send=findViewById(R.id.sendcomment);
        comment=findViewById(R.id.textforcomment);

        recomment.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recomment.setItemAnimator(null);
         user= FirebaseAuth.getInstance().getCurrentUser().getUid();
        Intent intent=getIntent();
        key=intent.getStringExtra("postid");

        fStore= FirebaseFirestore.getInstance();
        CollectionReference cRef=fStore.collection("Posts").document(key).collection("Comments");
        Query query = cRef.orderBy("time",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<commentModel> options = new FirestoreRecyclerOptions.Builder<commentModel>()
                .setQuery(query, commentModel.class)
                .build();
        adapter=new commentAdapter(options);
        recomment.setAdapter(adapter);
        comment.setText(null, EditText.BufferType.EDITABLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(comment.this,HomeActivity.class));
                finish();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
                            currentTime = System.currentTimeMillis();
                            name=value.getString("Full_Name");
                            Map<String, Object> cp = new HashMap<>();
                            cp.put("userCommented", user);
                            cp.put("Comment",cm);
                            cp.put("cName",name);
                            cp.put("time",currentTime);
                            FirebaseFirestore.getInstance().collection("Posts")
                                    .document(key)
                                    .collection("Comments")
                                    .add(cp);
                        }
                    });
                }

                FirebaseFirestore.getInstance().collection("Posts").document(key).collection("Comments").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            count = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                count++;
                            }
                        } else {
                            Log.d("Tagsd", "Error getting documents: ", task.getException());
                        }
                        Map<String,Object> add=new HashMap<>();
                        add.put("nComments",count-1);
                        FirebaseFirestore.getInstance().collection("Posts").document(key).update(add);
                    }
                });
                comment.setText(null);

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