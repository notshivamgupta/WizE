package com.example.wize;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

public class exploreAdapter extends FirestoreRecyclerAdapter<exploreModel,exploreAdapter.exploreHolder> {

    public exploreAdapter(@NonNull FirestoreRecyclerOptions<exploreModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull exploreHolder holder, int position, @NonNull exploreModel model) {
       holder.text.setText(model.name);
        Uri postUri = Uri.parse(model.image);
        Picasso.get().load(postUri).into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),exploregroups.class);
                intent.putExtra("topicname",model.name);
                intent.putExtra("topickey",model.key);
                intent.putExtra("topicimage",model.image);
                intent.putExtra("topicinfo",model.info);
                intent.putExtra("topicusers",model.NoUSers);
                view.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public exploreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.explorerecycler,
                parent, false);
        return new exploreHolder(v);
    }

    public class exploreHolder extends RecyclerView.ViewHolder {
        ShapeableImageView image;
        TextView text;
        public exploreHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.exploreimage);
            text=itemView.findViewById(R.id.exploretext);
        }
    }
}
