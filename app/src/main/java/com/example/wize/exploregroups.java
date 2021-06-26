package com.example.wize;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class exploregroups extends AppCompatActivity {
String image,info,name,key;
ImageView back;
ShapeableImageView topicimage;
TextView topicname,Topicinfo;
    Long currentTime;
    FirebaseFirestore fStore;
Button join;
String currentuser;
    String Sname,Simage,Suname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exploregroups);
        back=findViewById(R.id.backfromexploregroups);
        topicimage=findViewById(R.id.imagegroup);
        topicname=findViewById(R.id.nameexploregroups);
        Topicinfo=findViewById(R.id.infogroup);
        join=findViewById(R.id.joinbtn);
         currentuser= FirebaseAuth.getInstance().getCurrentUser().getUid();
        Intent intent=getIntent();
        name=intent.getStringExtra("topicname");
        image=intent.getStringExtra("topicimage");
        key=intent.getStringExtra("topickey");
        info=intent.getStringExtra("topicinfo");
        topicname.setText(name);
        Log.d("datainfo",info);
        info= info.replace("\\n","\n");
        Log.d("datainfo",info);
        Topicinfo.setText(info);
        Uri postUri = Uri.parse(image);
        Picasso.get().load(postUri).into(topicimage);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentTime = System.currentTimeMillis();
                fStore= FirebaseFirestore.getInstance();
                Map<String,Object> data=new HashMap<>();
                data.put("Name",name);
                data.put("User_Image",image);
                data.put("key",key);
                data.put("Time",currentTime.toString());
                fStore.collection("Users").document(currentuser).collection("Groups_Joined").add(data);

                DocumentReference documentReference= fStore.collection("Users").document(currentuser);
                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                        Sname=value.getString("Full_Name");
                        Suname=value.getString("UserName");
                        Simage=value.getString("profileImage");
                        Map<String,Object> data=new HashMap<>();
                        data.put("Name",Sname);
                        data.put("User_Id",currentuser);
                        data.put("User_Image",Simage);
                        data.put("UserName",Suname);
                        data.put("Time",currentTime.toString());
                        fStore.collection("group").document(key).collection("Users").add(data);
                    }
                });

                Snackbar.make(view, "Congrats!!.. You joined "+name+" group.. You can now message from chat", Snackbar.LENGTH_LONG).show();

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(exploregroups.this,HomeActivity.class));
                finish();
            }
        });
    }
}