package com.example.wize;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Addposts extends AppCompatActivity {
    String userId;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    ImageView addphoto;
    Uri uri;
    private ToggleButton appdev,webdev,oop,mlai,grapdgn,digmar,phot,fin,cybsec,comskill,entre,gamdev,scinrec;
    EditText txtpost;
    ImageView imageSelected;
    Button addpost;
    List<String> Tags;
    String Type="Text Post";
    StorageReference sr;
    ImageView back;
    String ur="";
    String id;
    Long currentTime;
    String fullName;
    Long npst;
    StorageReference proRef;
    String profilePic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addposts);
        addphoto=findViewById(R.id.imageViewaddpst);
        imageSelected = findViewById(R.id.imageViewaddpst);
        txtpost=findViewById(R.id.textforpost);
        back=findViewById(R.id.backfromaddpost);
        addpost=findViewById(R.id.post);

        appdev=findViewById(R.id.appdevaddpost);
        webdev=findViewById(R.id.webdevaddpost);
        oop=findViewById(R.id.oopaddpost);
        mlai=findViewById(R.id.ml_aiaddpost);
        grapdgn=findViewById(R.id.Grpdgnaddpost);
        digmar=findViewById(R.id.digmaraddpost);
        phot=findViewById(R.id.photaddpost);
        fin=findViewById(R.id.finaddpost);
        cybsec=findViewById(R.id.cybsecaddpost);
        comskill=findViewById(R.id.comskillsaddpost);
        entre=findViewById(R.id.entrepreneurshipaddpost);
        gamdev=findViewById(R.id.gamedevaddpost);
        scinrec=findViewById(R.id.scinrecaddpost);
        Tags=new ArrayList<>();
        currentTime = System.currentTimeMillis();
        firebaseAuth= FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();
        userId=firebaseAuth.getCurrentUser().getUid();
        sr= FirebaseStorage.getInstance().getReference();
        DocumentReference documentReference= fStore.collection("Users").document(userId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                fullName=value.getString("Full_Name");
                npst=value.getLong("Posts");
            }
        });
        addphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery,1000);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Addposts.this,HomeActivity.class));
                finish();
            }
        });


        appdev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (appdev.isChecked() && (Tags.size()<2))
                {

                    appdev.setBackgroundDrawable(getResources().getDrawable(R.drawable.togbtnback));

                    appdev.setTextColor(getResources().getColor(R.color.white));
                    Tags.add("App development");
                }
                else
                {
                    appdev.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    appdev.setTextColor(getResources().getColor(R.color.black));
                    Tags.remove("App development");
                }
            }
        });
        webdev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (webdev.isChecked()&& (Tags.size()<2))
                {
                    webdev.setBackgroundDrawable(getResources().getDrawable(R.drawable.togbtnback));

                    webdev.setTextColor(getResources().getColor(R.color.white));
                    Tags.add("Web development");
                }
                else
                {
                    webdev.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    webdev.setTextColor(getResources().getColor(R.color.black));
                    Tags.remove("Web development");
                }
            }
        });
        oop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (oop.isChecked()&& (Tags.size()<2))
                {
                    oop.setBackgroundDrawable(getResources().getDrawable(R.drawable.togbtnback));

                    oop.setTextColor(getResources().getColor(R.color.white));
                    Tags.add("Object oriented programing");
                }
                else
                {
                    oop.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    oop.setTextColor(getResources().getColor(R.color.black));
                    Tags.remove("Object oriented programing");
                }
            }
        });
        mlai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mlai.isChecked()&& (Tags.size()<2))
                {
                    mlai.setBackgroundDrawable(getResources().getDrawable(R.drawable.togbtnback));

                    mlai.setTextColor(getResources().getColor(R.color.white));
                    Tags.add("Ml/AI");
                }
                else
                {
                    mlai.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    mlai.setTextColor(getResources().getColor(R.color.black));
                    Tags.remove("Ml/AI");
                }
            }
        });
        grapdgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (grapdgn.isChecked()&& (Tags.size()<2))
                {
                    grapdgn.setBackgroundDrawable(getResources().getDrawable(R.drawable.togbtnback));

                    grapdgn.setTextColor(getResources().getColor(R.color.white));
                    Tags.add("Graphic design");
                }
                else
                {
                    grapdgn.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    grapdgn.setTextColor(getResources().getColor(R.color.black));
                    Tags.remove("Graphic design");
                }
            }
        });
        digmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (digmar.isChecked()&& (Tags.size()<2))
                {
                    digmar.setBackgroundDrawable(getResources().getDrawable(R.drawable.togbtnback));

                    digmar.setTextColor(getResources().getColor(R.color.white));
                    Tags.add("Digital marketing");
                }
                else
                {
                    digmar.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    digmar.setTextColor(getResources().getColor(R.color.black));
                    Tags.remove("Digital marketing");
                }
            }
        });
        phot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phot.isChecked()&& (Tags.size()<2))
                {
                    phot.setBackgroundDrawable(getResources().getDrawable(R.drawable.togbtnback));

                    phot.setTextColor(getResources().getColor(R.color.white));
                    Tags.add("Photography");
                }
                else
                {
                    phot.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    phot.setTextColor(getResources().getColor(R.color.black));
                    Tags.remove("Photography");
                }
            }
        });
        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fin.isChecked()&& (Tags.size()<2))
                {
                    fin.setBackgroundDrawable(getResources().getDrawable(R.drawable.togbtnback));

                    fin.setTextColor(getResources().getColor(R.color.white));
                    Tags.add("Finance");
                }
                else
                {
                    fin.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    fin.setTextColor(getResources().getColor(R.color.black));
                    Tags.remove("Finance");
                }
            }
        });
        cybsec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cybsec.isChecked()&& (Tags.size()<2))
                {
                    cybsec.setBackgroundDrawable(getResources().getDrawable(R.drawable.togbtnback));

                    cybsec.setTextColor(getResources().getColor(R.color.white));
                    Tags.add("Cyber security");
                }
                else
                {
                    cybsec.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    cybsec.setTextColor(getResources().getColor(R.color.black));
                    Tags.remove("Cyber security");
                }
            }
        });
        comskill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (comskill.isChecked()&& (Tags.size()<2))
                {
                    comskill.setBackgroundDrawable(getResources().getDrawable(R.drawable.togbtnback));

                    comskill.setTextColor(getResources().getColor(R.color.white));
                    Tags.add("Communication skills");
                }
                else
                {
                    comskill.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    comskill.setTextColor(getResources().getColor(R.color.black));
                    Tags.remove("Communication skills");
                }
            }
        });
        entre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (entre.isChecked()&& (Tags.size()<2))
                {
                    entre.setBackgroundDrawable(getResources().getDrawable(R.drawable.togbtnback));

                    entre.setTextColor(getResources().getColor(R.color.white));
                    Tags.add("Entrepreneurship");
                }
                else
                {
                    entre.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    entre.setTextColor(getResources().getColor(R.color.black));
                    Tags.remove("Entrepreneurship");
                }
            }
        });
        gamdev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gamdev.isChecked()&& (Tags.size()<2))
                {
                    gamdev.setBackgroundDrawable(getResources().getDrawable(R.drawable.togbtnback));

                    gamdev.setTextColor(getResources().getColor(R.color.white));
                    Tags.add("3d and Game development");
                }
                else
                {
                    gamdev.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    gamdev.setTextColor(getResources().getColor(R.color.black));
                    Tags.remove("3d and Game development");
                }
            }
        });
        scinrec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scinrec.isChecked()&& (Tags.size()<2))
                {
                    scinrec.setBackgroundDrawable(getResources().getDrawable(R.drawable.togbtnback));
                    scinrec.setTextColor(getResources().getColor(R.color.white));
                    Tags.add("Science and research");
                }
                else
                {
                    scinrec.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    scinrec.setTextColor(getResources().getColor(R.color.black));
                    Tags.remove("Science and research");
                }
            }
        });

        addpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = txtpost.getText().toString();
                if (txt.isEmpty()) {
                    txtpost.setError("Enter Some Text!");
                }
                else
                if (Type.equals("Image Post") && txt!=null) {

                    Map<String,Object>data=new HashMap<>();
                    data.put("key","");
                    data.put("timeStamp",currentTime.toString());
                    data.put("textPost",txt);
                    data.put("image","");
                    data.put("type",Type);
                    data.put("nComments",0);
                    data.put("nLikes",0);
                    data.put("tags",Tags);
                    data.put("userId",userId);
                    data.put("Full_Name",fullName);
                    fStore.collection("Posts").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Snackbar.make(view, "Posting Please wait!....", Snackbar.LENGTH_LONG).show();

                        }
                    }).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            id = task.getResult().getId();
                            Map<String, Object> pp = new HashMap<>();
                            pp.put("likerId", "");
                            fStore.collection("Posts").document(id).collection("Likes").add(pp).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    Log.d("XXX", "Likes collection created");
                                }
                            });
                            Map<String, Object> p = new HashMap<>();
                            p.put("Comment", "");
                            p.put("userCommented","");
                            fStore.collection("Posts").document(id).collection("Comments").add(p).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    Log.d("XXX", "Comments collection created");
                                }
                            });
                            final StorageReference fileref = sr.child("Posts")
                                    .child(userId + currentTime.toString());
                            StorageTask uploadTask = fileref.putFile(uri);
                            uploadTask.continueWithTask(new Continuation() {
                                @Override
                                public Object then(@NonNull Task task) throws Exception {
                                    if (!task.isSuccessful()) {
                                        throw task.getException();

                                    }
                                    return fileref.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    Snackbar.make(view, "Post uploaded!...", Snackbar.LENGTH_LONG).show();

                                    final Uri downloadUri = task.getResult();
                                    String postUri = downloadUri.toString();
                                    Map<String, Object> profile = new HashMap<>();
                                    profile.put("image", postUri);
                                    profile.put("key",id);
                                    fStore.collection("Posts").document(id)
                                            .update(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            Map<String, Object> pro = new HashMap<>();
                                            pro.put("Post ID", id);
                                            fStore.collection("Users")
                                                    .document(userId)
                                                    .collection("No Of Posts")
                                                    .add(pro)
                                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                                            if (task.isSuccessful())
                                                            {

                                                                Map<String,Object> noPosts=new HashMap<>();
                                                                        noPosts.put("Posts",npst+1);
                                                                        fStore.collection("Users")
                                                                                .document(userId).update(noPosts).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                            }
                                                                        });
                                                                startActivity(new Intent(Addposts.this,HomeActivity.class));
                                                            }

                                                        }
                                                    });
                                        }
                                    });
                                }
                            });


                        }
                    });
                }

                else if (Type.equals("Text Post")&& txt!=null )
                {
                    Snackbar.make(view, "Post uploaded!...", Snackbar.LENGTH_LONG).show();
                    String id=fStore.collection("Posts").document().getId();
                    Map<String,Object>data=new HashMap<>();
                    data.put("key",id);
                    data.put("timeStamp",currentTime.toString());
                    data.put("textPost",txt);
                    data.put("image","");
                    data.put("type",Type);
                    data.put("nComments",0);
                    data.put("nLikes",0);
                    data.put("tags",Tags);
                    data.put("userId",userId);
                    data.put("Full_Name",fullName);
                    fStore.collection("Posts").document(id).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                startActivity(new Intent(Addposts.this,HomeActivity.class));
                                finish();
                            }
                            else
                            {
                                Toast.makeText(Addposts.this, "Error!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                    Map<String, Object> pp = new HashMap<>();
                    pp.put("likerId", "");
                    fStore.collection("Posts").document(id).collection("Likes").add(pp).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Log.d("XXX", "Likes collection created");
                        }
                    });
                    Map<String, Object> p = new HashMap<>();
                    p.put("Comment", "");
                    p.put("userCommented","");
                    fStore.collection("Posts").document(id).collection("Comments").add(p).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Log.d("XXX", "Comments collection created");
                        }
                    });
                    Map<String, Object> pro = new HashMap<>();
                    pro.put("Post ID", id);
                    fStore.collection("Users")
                            .document(userId)
                            .collection("No Of Posts")
                            .add(pro)
                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if (task.isSuccessful())
                                    {
                                        Map<String,Object> noPosts=new HashMap<>();
                                        noPosts.put("Posts",npst+1);
                                        fStore.collection("Users")
                                                .document(userId).update(noPosts).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                            }
                                        });
                                        startActivity(new Intent(Addposts.this,HomeActivity.class));
                                    }

                                }
                            });
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000 && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            if(imageUri!= null) {
                UCrop uCrop =  UCrop.of(imageUri, Uri.fromFile(new File(getCacheDir(), System.currentTimeMillis() + ".jpg" )));
                uCrop.withAspectRatio(1, 1);
                uCrop.withMaxResultSize(1000, 1000);
                uCrop.start(Addposts.this);
                uCrop.withOptions(getCropOptions());
            }
        }
        else if(requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK){
            uri = UCrop.getOutput(data);
            addphoto.setImageURI(uri);
            Type="Image Post";
        }
    }

    public UCrop.Options getCropOptions() {
        UCrop.Options options = new UCrop.Options();
        options.setHideBottomControls(false);
        options.setCompressionQuality(100);
        options.setMaxBitmapSize(10000);
        return options;
    }

}