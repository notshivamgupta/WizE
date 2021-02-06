package com.example.wize;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

public class introduction extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private SliderAdapter sliderAdapter;
    private TextView[] mDots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        mSlideViewPager=(ViewPager)findViewById(R.id.slideViewPager);
                mDotLayout=(LinearLayout)findViewById(R.id.dotsLayout);
                sliderAdapter=new SliderAdapter(this);

                mSlideViewPager.setAdapter(sliderAdapter);
                addDotsIndicator();

    }
    public void addDotsIndicator(){
        mDots=new TextView[3];
        for(int i=0;i<mDots.length;i++){

            mDots[i]=new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(Color.parseColor("#000000"));

            mDotLayout.addView(mDots[i]);

        }
    }

}