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

public class Personalchat_Adapter extends FirestoreRecyclerAdapter<PersonalChat_Moel,Personalchat_Adapter.PersonalchatHolder> {
    public Personalchat_Adapter(@NonNull FirestoreRecyclerOptions<PersonalChat_Moel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PersonalchatHolder holder, int position, @NonNull PersonalChat_Moel model) {

        holder.name.setText(model.Name);
        Uri profuri = Uri.parse(model.getUser_Image());
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
                    intent.putExtra("receivername",model.Name);
                    intent.putExtra("receiverimg",model.User_Image);
                    view.getContext().startActivity(intent);
                }

            }
        });
    }

    @NonNull
    @Override
    public PersonalchatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerforaddchat,
                parent, false);
        return new Personalchat_Adapter.PersonalchatHolder(v);
    }

    public class PersonalchatHolder extends RecyclerView.ViewHolder{
        TextView name;
        RelativeLayout relativeLayout;
        ImageView img;
        public PersonalchatHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.usernameforadd);
            relativeLayout=itemView.findViewById(R.id.recyclerforChats);
            img=itemView.findViewById(R.id.imageaddchat);
        }
    }
}
