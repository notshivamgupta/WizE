package com.example.wize;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;


public class Chat extends Fragment {
TextView txt;
    TabLayout tabLayout;
    ViewPager viewPager;
    public Chat() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_chat, container, false);

        tabLayout=view.findViewById(R.id.Tab_layout);
        viewPager=view.findViewById(R.id.View_pager);
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.setFragments(new Personal_Chats(),"Personal Chats");
        viewPagerAdapter.setFragments(new Group_chats(),"Group Chats");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

       txt=view.findViewById(R.id.gotoaddchat);
       txt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(getActivity(),addChat.class));
           }
       });
        return view;
    }
}