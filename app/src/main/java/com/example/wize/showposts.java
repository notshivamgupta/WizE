package com.example.wize;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
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

public class showposts extends AppCompatActivity {

    FirebaseFirestore fStore;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView name, like, comment, timez;
    EditText sendcomment;
    ShapeableImageView img;
    CircleImageView dp;
    ReadMoreTextView text;
    commentAdapter adapter;
    ImageView back;
    RecyclerView recomment;
    ImageView lik, com;
    int commentcheck;
    int likee;
    int c;
    CircleImageView pimg;
    String userId;
    String namecm;
    String cm=null;
    Long currentTime;
    int count;
    String key;
    ImageView send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showposts);

        name = findViewById(R.id.textView21);
        text = findViewById(R.id.textView23);
        like = findViewById(R.id.textView24);
        comment = findViewById(R.id.commentposts);
        timez = findViewById(R.id.timetext);
        img = findViewById(R.id.imageViewaddpst);
        lik = findViewById(R.id.toggleButton2);
        dp=findViewById(R.id.circleImageView3s);
        pimg = findViewById(R.id.circleImageView3);
        com = findViewById(R.id.commentimg);
        recomment=findViewById(R.id.cmrecycler2);
        send=findViewById(R.id.sendcommentprofile);
        sendcomment=findViewById(R.id.textforcomment);
        back=findViewById(R.id.backfrompostview);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(showposts.this,HomeActivity.class));
                finish();
            }
        });

        recomment.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recomment.setItemAnimator(null);

        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        String Full_Name = intent.getStringExtra("Full_Name");
        String image = intent.getStringExtra("image");
        String nComments = intent.getStringExtra("nComments");
        String Likes = intent.getStringExtra("nLikes");
        likee= Integer.parseInt(Likes);
        String textPost = intent.getStringExtra("textPost");
        String timeStamp = intent.getStringExtra("timeStamp");
        String type = intent.getStringExtra("type");
        String poster=intent.getStringExtra("userId");

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();


        name.setText(Full_Name);
        text.setText(textPost);
        like.setText(Likes);
        Uri postUri = Uri.parse(image);
        Picasso.get().load(postUri).into(img);

        StorageReference storageReference= FirebaseStorage.getInstance().getReference();
        StorageReference profoleRef=storageReference.child("ProfileImg").child(poster);
        profoleRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(dp);
            }
        });

        FirebaseFirestore.getInstance().collection("Posts").document(key).collection("Comments").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    c = 0;
                    for (DocumentSnapshot document : task.getResult()) {
                        c++;
                    }
                } else {
                    Log.d("Tagsd", "Error getting documents: ", task.getException());
                }
                comment.setText("Comment ("+Integer.toString(c-1)+")");

            }
        });
        if (c-1>0)
        {
         com.setImageResource(R.drawable.ic_vector__7_);
        }
        else
        {
         com.setImageResource(R.drawable.ic_vector__3_);
        }


        String wordMonth = null;
        String postTime = Long.toString(Long.parseLong(timeStamp));

        String date = convertDate(postTime, "dd");
        String month = convertDate(postTime, "MM");

        if (month.equals("01")) {
            wordMonth = "Jan";
        }
        if (month.equals("02")) {
            wordMonth = "Feb";
        }
        if (month.equals("03")) {
            wordMonth = "March";
        }
        if (month.equals("04")) {
            wordMonth = "April";
        }
        if (month.equals("05")) {
            wordMonth = "May";
        }
        if (month.equals("06")) {
            wordMonth = "June";
        }
        if (month.equals("07")) {
            wordMonth = "July";
        }
        if (month.equals("08")) {
            wordMonth = "Aug";
        }
        if (month.equals("09")) {
            wordMonth = "Sep";
        }
        if (month.equals("10")) {
            wordMonth = "Oct";
        }
        if (month.equals("11")) {
            wordMonth = "Nov";
        }
        if (month.equals("12")) {
            wordMonth = "Dec";
        }

        String time = convertDate(postTime, "hh:mm:ss");
        String display = wordMonth + "  " + date + ",  " + time + "  " + convertDate(postTime, "a");
        timez.setText(display);

        final List<LikeModel> list = new ArrayList<>();

        FirebaseFirestore.getInstance().collection("Posts").document(key).collection("Likes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                list.clear();

                for(QueryDocumentSnapshot snap: value){
                    list.add(new LikeModel(snap.getString("likerId").trim()));
                    if(snap.getString("likerId").trim().equals(userId)){
                        lik.setImageResource(R.drawable.ic_vector__5_);
                    }
                }

                lik.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        lik.setEnabled(false);
                        int flag = 0;
                        for(LikeModel likerId: list){
                            if(likerId.getLikerId().equals(userId)){
                                FirebaseFirestore.getInstance().collection("Posts").document(key).collection("Likes")
                                        .document(userId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Map<String,Object> ad=new HashMap<>();
                                        ad.put("nLikes",--likee);
                                        Log.d("togo",""+likee);
                                        like.setText(Integer.toString(likee));
                                        FirebaseFirestore.getInstance().collection("Posts").document(key).update(ad);
                                        lik.setImageResource(R.drawable.ic_vector__6_);
                                        lik.setEnabled(true);
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
                            pp.put("likerId", userId);
                            FirebaseFirestore.getInstance().collection("Posts")
                                    .document(key)
                                    .collection("Likes")
                                    .document(userId)
                                    .set(pp).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    lik.setImageResource(R.drawable.ic_vector__5_);
                                    Map<String,Object> add=new HashMap<>();
                                    add.put("nLikes",++likee);
                                    Log.d("togo",""+likee);
                                    like.setText(Integer.toString(likee));
                                    FirebaseFirestore.getInstance().collection("Posts").document(key).update(add);
                                    lik.setEnabled(true);
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
        lik.setImageResource(R.drawable.ic_vector__6_);

        fStore= FirebaseFirestore.getInstance();
        CollectionReference cRef=fStore.collection("Posts").document(key).collection("Comments");
        Query query = cRef.orderBy("time",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<commentModel> options = new FirestoreRecyclerOptions.Builder<commentModel>()
                .setQuery(query, commentModel.class)
                .build();
        adapter=new commentAdapter(options);
        recomment.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                cm=sendcomment.getText().toString();
                if (cm.equals(""))
                {
                    Snackbar.make(view, "Comment is empty....", Snackbar.LENGTH_LONG).show();
                }
                else
                {
                    DocumentReference documentReference=fStore.collection("Users").document(userId);
                    documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            currentTime = System.currentTimeMillis();
                            namecm=value.getString("Full_Name");
                            Map<String, Object> cp = new HashMap<>();
                            cp.put("userCommented", userId);
                            cp.put("Comment",cm);
                            cp.put("cName",namecm);
                            cp.put("time",currentTime);
                            FirebaseFirestore.getInstance().collection("Posts")
                                    .document(key)
                                    .collection("Comments")
                                    .add(cp);
                        }
                    });
                }

                FirebaseFirestore.getInstance().collection("Posts").document(key).collection("Comments").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            count = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                count++;
                            }
                        } else {
                            Log.d("Tagsd", "Error getting documents: ", task.getException());
                        }
                        Map<String,Object> add=new HashMap<>();
                        add.put("nComments",count-1);
                        FirebaseFirestore.getInstance().collection("Posts").document(key).update(add);
                        FirebaseFirestore.getInstance().collection("Posts").document(key).collection("Comments").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    c = 0;
                                    for (DocumentSnapshot document : task.getResult()) {
                                        c++;
                                    }
                                } else {
                                    Log.d("Tagsd", "Error getting documents: ", task.getException());
                                }
                                comment.setText("Comment ("+Integer.toString(c-1)+")");

                            }
                        });
                    }
                });
                sendcomment.setText(null);

            }

        });



    }

    public String convertDate(String dateInMilliseconds, String dateFormat) {
        return DateFormat.format(dateFormat, Long.parseLong(dateInMilliseconds)).toString();
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}