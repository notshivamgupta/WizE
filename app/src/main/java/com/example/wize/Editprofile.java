package com.example.wize;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Editprofile extends AppCompatActivity {
ImageView back;
Button editinter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        back=findViewById(R.id.backfromeditprofile);
        editinter=findViewById(R.id.editinterestbtn);
        editinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Editprofile.this,editinterests.class));
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Editprofile.this,Profile.class));
                finish();
            }
        });
    }
}