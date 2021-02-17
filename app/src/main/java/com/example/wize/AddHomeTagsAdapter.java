package com.example.wize;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AddHomeTagsAdapter extends RecyclerView.Adapter<AddHomeTagsAdapter.AddhomeView> {
    List<String> tags;
    public AddHomeTagsAdapter(List<String>tags)
    {this.tags=tags;}
    @NonNull
    @Override
    public AddhomeView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layouthometags,parent,false);
        return new AddhomeView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddhomeView holder, int position) {
     holder.ab.setText(tags.get(position));
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public class AddhomeView extends RecyclerView.ViewHolder {
        TextView ab;
        public AddhomeView(@NonNull View itemView) {
            super(itemView);
            ab=itemView.findViewById(R.id.textfortagshome);
        }
    }
}
