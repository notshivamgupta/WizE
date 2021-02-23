package com.example.wize;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewUsersProfile extends AppCompatActivity {
String user;
CircleImageView userimg;
TextView name,username,posts,groups,friends;
TagsAdapter adapter;
RecyclerView rview;
ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users_profile);
        userimg=findViewById(R.id.circleImageView2user);
        name=findViewById(R.id.textView11user);
        username=findViewById(R.id.textView12user);
        groups=findViewById(R.id.textView13user);
        posts=findViewById(R.id.textView14user);
        friends=findViewById(R.id.textView15user);
        rview=findViewById(R.id.recyclerViewuser);
        back=findViewById(R.id.backfromprofileuser);
        Intent intent=getIntent();
        user=intent.getStringExtra("Uid");
        StorageReference storageReference= FirebaseStorage.getInstance().getReference();
        StorageReference profoleRef=storageReference.child("ProfileImg").child(user);
        profoleRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(userimg);
            }
        });
        DocumentReference documentReference= FirebaseFirestore.getInstance().collection("Users").document(user);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                String names=value.getString("Full_Name");
                String usernames=value.getString("UserName");
                long friend= value.getLong("Friends");
                long post= value.getLong("Posts");
                long group= value.getLong("Groups");
                name.setText(names);
                username.setText("@"+usernames);
                posts.setText(String.valueOf(post));
                groups.setText(String.valueOf(group));
                friends.setText(String.valueOf(friend));
            }
        });
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        List<Map<String, Object>> interests = (List<Map<String, Object>>) document.get("User_Interests");
                        rview.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.HORIZONTAL));
                        adapter=new TagsAdapter(interests);
                        rview.setAdapter(adapter);
                    }
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewUsersProfile.this,HomeActivity.class));
                finish();
            }
        });
    }
}