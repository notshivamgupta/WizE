package com.example.wize;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class result extends AppCompatActivity {
TextView cor,tim,changetext,welldone;
    ArrayList pieEntries;
    PieDataSet pieDataSet;
    PieData pieData;
    PieChart pieChart;
    ImageView emoji;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        cor=findViewById(R.id.correct);
        tim=findViewById(R.id.timetaken);
        welldone=findViewById(R.id.welldone);
        changetext=findViewById(R.id.changingtext);
        emoji=findViewById(R.id.emoji);
        pieChart=findViewById(R.id.piechart);
        int a,time;
        Intent intent=getIntent();
        a=intent.getIntExtra("Correct",0);
        time= intent.getIntExtra("time",0);
        cor.setText(Integer.toString(a)+" / 5");
        tim.setText("Time taken:-  "+Integer.toString(time/60)+" : "+Integer.toString(time%60));
        pieEntries=new ArrayList<>();
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setDrawSliceText(false);
        pieChart.setHoleRadius(80f);
        pieEntries.add(new PieEntry(a,""));
        pieEntries.add(new PieEntry(5-a,""));
        pieDataSet=new PieDataSet(pieEntries,"");
        pieData=new PieData(pieDataSet);
        pieData.setDrawValues(false);
        pieChart.setData(pieData);
        pieDataSet.setColors(new int[]{Color.parseColor("#07E092"),Color.parseColor("#E76F51")});
        if (a<4)
        {
            changetext.setText("Oops! Looks like you don't have sufficient knowledge about this topic. We would recommend you to look for other topics in your interest.");
            welldone.setText("Oh Ho!");
            emoji.setImageResource(R.drawable.ic_sad);
        }
        else
        {
            changetext.setText("Looks like you have the perfect amount of knowledge to join this group and discuss it with other!");
            welldone.setText("Well Done!");
            emoji.setImageResource(R.drawable.ic_smile);
        }
    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(result.this,HomeActivity.class));
        finish();
    }
}