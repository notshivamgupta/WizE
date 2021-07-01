package com.example.wize;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class result extends AppCompatActivity {
TextView cor,tim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        cor=findViewById(R.id.correct);
        tim=findViewById(R.id.timetaken);
        int a,time;
        Intent intent=getIntent();
        a=intent.getIntExtra("Correct",0);
        time= intent.getIntExtra("time",0);
        cor.setText(Integer.toString(a));
        tim.setText("Time taken-  "+Integer.toString(time/60)+" : "+Integer.toString(time%60));
    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(result.this,HomeActivity.class));
        finish();
    }
}