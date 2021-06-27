package com.example.wize;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowTopicUSersAdapter extends FirestoreRecyclerAdapter<ShowTopicUSersModel,ShowTopicUSersAdapter.TopicusersHolder>{
    public ShowTopicUSersAdapter(@NonNull FirestoreRecyclerOptions<ShowTopicUSersModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TopicusersHolder holder, int position, @NonNull ShowTopicUSersModel model) {
        holder.name.setText(model.Name);
        Uri postUri = Uri.parse(model.User_Image);
        Picasso.get().load(postUri).into(holder.img);
        holder.r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),ViewUsersProfile.class);
                intent.putExtra("Uid",model.User_Id);
                view.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public TopicusersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerforusersingroup,
                parent, false);
        return new ShowTopicUSersAdapter.TopicusersHolder(v);
    }

    public class TopicusersHolder extends RecyclerView.ViewHolder {
        TextView name;
        CircleImageView img;
        RelativeLayout r;
        public TopicusersHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.nameusersgroup);
            img=itemView.findViewById(R.id.imagegroupusers);
            r=itemView.findViewById(R.id.recyclerfortopicsnext);
        }
    }
}
