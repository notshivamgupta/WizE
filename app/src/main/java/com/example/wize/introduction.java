package com.example.wize;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class introduction extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private SliderAdapter sliderAdapter;
    private TextView[] mDots;
Button back,next;
private int mcurrentpage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);
        sliderAdapter = new SliderAdapter(this);
        back=findViewById(R.id.goback);
        next=findViewById(R.id.gonext);
        mSlideViewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewlisner);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mcurrentpage<2)
                {
                    mSlideViewPager.setCurrentItem(mcurrentpage+1);
                }
                else
                {
                    startActivity(new Intent(introduction.this,SignIn.class));
                    finish();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSlideViewPager.setCurrentItem(mcurrentpage-1);
            }
        });
    }

    public void addDotsIndicator(int position) {
        mDots = new TextView[3];
        mDotLayout.removeAllViews();
        for (int i = 0; i < mDots.length; i++) {

            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(Color.parseColor("#000000"));

            mDotLayout.addView(mDots[i]);

        }
        if (mDots.length>0)
        {
            mDots[position].setTextColor(getResources().getColor(R.color.dark_blue));
        }
    }

    ViewPager.OnPageChangeListener viewlisner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
                addDotsIndicator(position);
                mcurrentpage=position;
                if(position==0)
                {
                    next.setEnabled(true);
                    back.setEnabled(false);
                    back.setVisibility(View.INVISIBLE);
                    next.setText("Next");
                    back.setText("");
                }
                else if(position==mDots.length-1){
                    next.setEnabled(true);
                    back.setEnabled(true);
                    back.setVisibility(View.VISIBLE);
                    next.setText("Finish");
                    back.setText("Back");
            }
                else {

                    next.setEnabled(true);
                    back.setEnabled(true);
                    back.setVisibility(View.VISIBLE);
                    next.setText("Next");
                    back.setText("Back");
                }
        }


        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}