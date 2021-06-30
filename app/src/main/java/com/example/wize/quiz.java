package com.example.wize;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class quiz extends AppCompatActivity {
String subject;
TextView ques, op1, op2, op3, op4, timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ques=findViewById(R.id.question);
        op1=findViewById(R.id.option1);
        op2=findViewById(R.id.option2);
        op3=findViewById(R.id.option3);
        op4=findViewById(R.id.option4);
        timer=findViewById(R.id.time);
        Intent intent=getIntent();
        subject=intent.getStringExtra("topicname");
        ques.setText(subject);

    }
}