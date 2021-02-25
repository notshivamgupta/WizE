package com.example.wize;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends FirestoreRecyclerAdapter<postModel,ImageAdapter.myadapter> {

    public ImageAdapter(FirestoreRecyclerOptions<postModel> image) {
        super(image);
    }

    @Override
    protected void onBindViewHolder(@NonNull myadapter holder, int position, @NonNull postModel model) {
        Uri postUri = Uri.parse(model.image);
        Picasso.get().load(postUri).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),showposts.class);
                intent.putExtra("key",model.key);
                intent.putExtra("Full_Name",model.Full_Name);
                intent.putExtra("image",model.image);
                intent.putExtra("nComments",model.nComments.toString());
                intent.putExtra("nLikes",model.nLikes.toString());
                intent.putExtra("textPost",model.textPost);
                intent.putExtra("timeStamp",model.timeStamp);
                intent.putExtra("type",model.type);
                intent.putExtra("userId",model.userId);
                Log.d("tago",""+model.key);
                v.getContext().startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public myadapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.profileposts,parent,false);
        return new myadapter(view);
    }


    public class myadapter extends RecyclerView.ViewHolder{
        ImageView imageView;
        public myadapter(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.images);
        }
    }

}