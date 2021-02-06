package com.example.wize;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context=context;
    }

    public int[] slideimage={

            R.drawable.onboard1,
            R.drawable.onboard2,
            R.drawable.onboard3
    };
    public String[] slide_headings={

            "Meet the like minded people",
            "Get indulge into nothing but pure knowledge",
            "See the better version of yourself"
    };
    public String[] slide_descs={
            "Wireframe is still important for ideation.It will help you to quickly test idea.",
            "Wireframe is still important for ideation.",
            "Wireframe is still important for ideation."

    };



    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o;
    }

    @Override
    public Object instantiateItem(ViewGroup container,int position){

        layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slidelayout,container,false);

        ImageView slideImageView=(ImageView)view.findViewById(R.id.sliderimage);
        TextView slide_head=(TextView)view.findViewById(R.id.scn);
        TextView slide_desc=(TextView)view.findViewById(R.id.scd);

        slideImageView.setImageResource(slideimage[position]);
        slide_head.setText(slide_headings[position]);
        slide_desc.setText(slide_descs[position]);

        container.addView(view);

        return view;

    }
    @Override
    public void destroyItem(ViewGroup container,int position,Object object){

        container.removeView((RelativeLayout)object);
    }
}
