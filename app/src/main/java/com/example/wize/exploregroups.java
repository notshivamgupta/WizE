package com.example.wize;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

public class exploregroups extends AppCompatActivity {
String image,info,name,key;
ImageView back;
ShapeableImageView topicimage;
TextView topicname,Topicinfo;
Button join;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exploregroups);
        back=findViewById(R.id.backfromexploregroups);
        topicimage=findViewById(R.id.imagegroup);
        topicname=findViewById(R.id.nameexploregroups);
        Topicinfo=findViewById(R.id.infogroup);
        join=findViewById(R.id.joinbtn);

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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(exploregroups.this,HomeActivity.class));
                finish();
            }
        });
    }
}