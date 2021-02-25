package com.example.wize;

import android.content.Intent;
import android.net.Uri;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class postAdapter extends FirestoreRecyclerAdapter<postModel,postAdapter.postHolder> {
    String user= FirebaseAuth.getInstance().getCurrentUser().getUid();
    String id;
    String nm;
    public postAdapter(@NonNull FirestoreRecyclerOptions<postModel> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull postHolder holder, int position, @NonNull postModel model) {

      holder.name.setText(model.Full_Name);
      holder.name.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if (model.userId.equals(user))
              {
                  Log.d("Tags","Own profile");
              }
              else {
                  Intent inten=new Intent(view.getContext(),ViewUsersProfile.class);
                  inten.putExtra("Uid",model.userId);
                  view.getContext().startActivity(inten);
              }
          }
      });
      if (model.nComments>0)
      {
          holder.com.setImageResource(R.drawable.ic_vector__7_);
      }
      else
      {
          holder.com.setImageResource(R.drawable.ic_vector__3_);
      }
     holder.text.setText(model.textPost);
     holder.like.setText(model.nLikes.toString());
     holder.comment.setText(model.nComments.toString());
       Uri postUri = Uri.parse(model.getImage());
       Picasso.get().load(postUri).into(holder.img);
        StorageReference storageReference= FirebaseStorage.getInstance().getReference();
        StorageReference profoleRef=storageReference.child("ProfileImg").child(model.userId);
        profoleRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.pimg);
            }
        });
       if (model.type.equals("Text Post"))
       {
           holder.text.setTrimLength(200);
           holder.img.setVisibility(View.GONE);
       }
       holder.com.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(view.getContext(),comment.class);
               intent.putExtra("postid",model.key);
               intent.putExtra("nm",model.Full_Name);
               view.getContext().startActivity(intent);
           }
       });
        String wordMonth = null;
        String postTime = Long.toString(Long.parseLong(model.timeStamp));

        String date = convertDate(postTime,"dd");
        String month = convertDate(postTime,"MM");

        if(month.equals("01")){
            wordMonth = "Jan";
        }
        if(month.equals("02")){
            wordMonth = "Feb";
        }
        if(month.equals("03")){
            wordMonth = "March";
        }
        if(month.equals("04")){
            wordMonth = "April";
        }
        if(month.equals("05")){
            wordMonth = "May";
        }
        if(month.equals("06")){
            wordMonth = "June";
        }
        if(month.equals("07")){
            wordMonth = "July";
        }
        if(month.equals("08")){
            wordMonth = "Aug";
        }
        if(month.equals("09")){
            wordMonth = "Sep";
        }
        if(month.equals("10")){
            wordMonth = "Oct";
        }
        if(month.equals("11")){
            wordMonth = "Nov";
        }
        if(month.equals("12")){
            wordMonth = "Dec";
        }

        String time = convertDate(postTime,"hh:mm:ss");
        String display = wordMonth +"  " + date + ",  " + time +"  "+convertDate(postTime,"a");
         holder.time.setText(display);

        final List<LikeModel> list = new ArrayList<>();
        FirebaseFirestore.getInstance().collection("Posts").document(model.key).collection("Likes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                list.clear();

                for(QueryDocumentSnapshot snap: value){
                    list.add(new LikeModel(snap.getString("likerId").trim()));
                    if(snap.getString("likerId").trim().equals(user)){
                        holder.lik.setImageResource(R.drawable.ic_vector__5_);
                    }
                }

                holder.lik.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                         holder.lik.setEnabled(false);
                        int flag = 0;
                        for(LikeModel likerId: list){
                            if(likerId.getLikerId().equals(user)){
                                FirebaseFirestore.getInstance().collection("Posts").document(model.key).collection("Likes")
                                        .document(user).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Map<String,Object> ad=new HashMap<>();
                                        ad.put("nLikes",model.nLikes-1);
                                        FirebaseFirestore.getInstance().collection("Posts").document(model.key).update(ad);
                                        holder.lik.setImageResource(R.drawable.ic_vector__6_);
                                        holder.lik.setEnabled(true);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                                flag = 1;
                            }
                        }
                        if(flag == 0){
                            Map<String, Object> pp = new HashMap<>();
                            pp.put("likerId", user);
                            FirebaseFirestore.getInstance().collection("Posts")
                                    .document(model.key)
                                    .collection("Likes")
                                    .document(user)
                                    .set(pp).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    holder.lik.setImageResource(R.drawable.ic_vector__5_);
                                    Map<String,Object> add=new HashMap<>();
                                    add.put("nLikes",model.nLikes+1);
                                    FirebaseFirestore.getInstance().collection("Posts").document(model.key).update(add);
                                    holder.lik.setEnabled(true);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                        }
                    }
                });
            }
        });
        holder.lik.setImageResource(R.drawable.ic_vector__6_);
    }

    @NonNull
    @Override
    public postHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.imagepostrecycler,
                parent, false);
        return new postHolder(v);
    }

    public class postHolder extends RecyclerView.ViewHolder {
        TextView name,like,comment,time;
        ShapeableImageView img;
        ReadMoreTextView text;
        ImageView lik,com;
        CircleImageView pimg;
        public postHolder(@NonNull View itemView) {
            super(itemView);
           name=itemView.findViewById(R.id.textView21);
            text=itemView.findViewById(R.id.textView23);
            like=itemView.findViewById(R.id.textView24);
            comment=itemView.findViewById(R.id.comment);
            time=itemView.findViewById(R.id.textView22);
            img=itemView.findViewById(R.id.imageViewaddpst);
            lik=itemView.findViewById(R.id.toggleButton2);
            pimg=itemView.findViewById(R.id.circleImageView3);
            com=itemView.findViewById(R.id.commentimg);
        }
    }
    public String convertDate(String dateInMilliseconds,String dateFormat) {
        return DateFormat.format(dateFormat, Long.parseLong(dateInMilliseconds)).toString();
    }

}
