package com.example.wize;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class result extends AppCompatActivity {
TextView cor,tim;
    ArrayList pieEntries;
    PieDataSet pieDataSet;
    PieData pieData;
    PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        cor=findViewById(R.id.correct);
        tim=findViewById(R.id.timetaken);
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
        pieChart.setHoleRadius(75f);
        pieEntries.add(new PieEntry(a,""));
        pieEntries.add(new PieEntry(5-a,""));
        pieDataSet=new PieDataSet(pieEntries,"");
        pieData=new PieData(pieDataSet);
        pieData.setDrawValues(false);
       pieChart.setData(pieData);
        pieDataSet.setColors(new int[]{Color.parseColor("#07E092"),Color.parseColor("#E76F51")});
    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(result.this,HomeActivity.class));
        finish();
    }
}