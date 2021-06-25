package com.example.wize;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class AddChatAdapter extends FirestoreRecyclerAdapter<AddChatModel,AddChatAdapter.AddChatHolder> {
    public AddChatAdapter(@NonNull FirestoreRecyclerOptions<AddChatModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final AddChatHolder holder, int position, @NonNull final AddChatModel model) {
        holder.name.setText(model.Full_Name);
        holder.uname.setText("@"+model.UserName);
        Uri profuri = Uri.parse(model.getProfileImage());
        Picasso.get().load(profuri).into(holder.img);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentuser= FirebaseAuth.getInstance().getCurrentUser().getUid();
                if (model.User_Id.equals(currentuser))
                {
                    Toast.makeText(view.getContext(), "Can not view own profile..", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent=new Intent(view.getContext(),sendChat.class);
                    intent.putExtra("receiver",model.User_Id);
                    intent.putExtra("receivername",model.Full_Name);
                    intent.putExtra("receiverimg",model.profileImage);
                    view.getContext().startActivity(intent);
                }

            }
        });
    }

    @NonNull
    @Override
    public AddChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerforaddchat,
                parent, false);
        return new AddChatHolder(v);
    }

    public class AddChatHolder extends RecyclerView.ViewHolder{

        TextView name,uname;
        RelativeLayout relativeLayout;
        ImageView img;
        public AddChatHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.usernameforadd);
            uname=itemView.findViewById(R.id.userStatus);
            relativeLayout=itemView.findViewById(R.id.recyclerforChats);
            img=itemView.findViewById(R.id.imageaddchat);
        }
    }
}
