package com.example.wize;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

public class GroupChat_Adapter extends FirestoreRecyclerAdapter<GroupChat_Model,GroupChat_Adapter.GroupChat_Holder> {

    public GroupChat_Adapter(@NonNull FirestoreRecyclerOptions<GroupChat_Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull GroupChat_Holder holder, int position, @NonNull GroupChat_Model model) {
        holder.name.setText(model.Name);
        Uri profuri = Uri.parse(model.getUser_Image());
        Picasso.get().load(profuri).into(holder.img);
        holder.group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),groupchat.class);
                intent.putExtra("receivergroup",model.key);
                intent.putExtra("receivernamegroup",model.Name);
                intent.putExtra("receiverimggroup",model.User_Image);

                view.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public GroupChat_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerforchatshow,
                parent, false);
        return new GroupChat_Adapter.GroupChat_Holder(v);
    }

    public class GroupChat_Holder extends RecyclerView.ViewHolder {
        ConstraintLayout group;
        ShapeableImageView img;
        TextView name;
        public GroupChat_Holder(@NonNull View itemView) {
            super(itemView);
            group=itemView.findViewById(R.id.recyclerforgroupstartChats);
            img=itemView.findViewById(R.id.imagegroupinchat);
            name=itemView.findViewById(R.id.usernameforingroupchat);
        }
    }
}
