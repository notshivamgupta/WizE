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
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class AddChatAdapter extends FirestoreRecyclerAdapter<AddChatModel,AddChatAdapter.AddChatHolder> {
    public AddChatAdapter(@NonNull FirestoreRecyclerOptions<AddChatModel> options) {
        super(options);
    }
    Long currentTime;
    FirebaseFirestore fStore;
    String ContactsId;
    String userId;
    String Sname,Simage,Suname;
    @Override
    protected void onBindViewHolder(@NonNull final AddChatHolder holder, int position, @NonNull final AddChatModel model) {
        holder.name.setText(model.Full_Name);
        Uri profuri = Uri.parse(model.getProfileImage());
        Picasso.get().load(profuri).into(holder.img);
        userId=FirebaseAuth.getInstance().getCurrentUser().getUid();
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
                    currentTime = System.currentTimeMillis();
                    fStore= FirebaseFirestore.getInstance();
                    Map<String,Object> data=new HashMap<>();
                    data.put("Name",model.Full_Name);
                    data.put("User_Id",model.User_Id);
                    data.put("User_Image",model.profileImage);
                    data.put("UserName",model.UserName);
                    data.put("Time",currentTime.toString());
                    fStore.collection("Users").document(currentuser).collection("Contacts").add(data);

                    DocumentReference documentReference= fStore.collection("Users").document(userId);
                    documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                            Sname=value.getString("Full_Name");
                            Suname=value.getString("UserName");
                            Simage=value.getString("profileImage");
                            Map<String,Object> data=new HashMap<>();
                            data.put("Name",Sname);
                            data.put("User_Id",userId);
                            data.put("User_Image",Simage);
                            data.put("UserName",Suname);
                            data.put("Time",currentTime.toString());
                            fStore.collection("Users").document(model.User_Id).collection("Contacts").add(data);
                        }
                    });

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

        TextView name;
        RelativeLayout relativeLayout;
        ImageView img;
        public AddChatHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.usernameforadd);
            relativeLayout=itemView.findViewById(R.id.recyclerforChats);
            img=itemView.findViewById(R.id.imageaddchat);
        }
    }
}
