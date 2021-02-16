package com.example.wize;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.Myview> {
    private List<Map<String, Object>> interests;
    public TagsAdapter(List<Map<String, Object>> interests)
    {
      this.interests=interests;
    }
    @NonNull
    @Override
    public Myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerfortags,parent,false);
        return new Myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myview holder, int position) {
            holder.a.setText((CharSequence) interests.get(position));
    }

    @Override
    public int getItemCount() {
        return interests.size();
    }
    public class Myview extends RecyclerView.ViewHolder {
        TextView a;
        public Myview(@NonNull View itemView) {
            super(itemView);
            a=itemView.findViewById(R.id.textfortagrec);
        }
    }
}
