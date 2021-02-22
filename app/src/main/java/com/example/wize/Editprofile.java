package com.example.wize;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Editprofile extends AppCompatActivity {
ImageView back,addprofimg,profimg;
Button editinter;
RecyclerView rc;
Uri uri;
TagsAdapter adapter;
StorageReference str;
EditText name;
Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        back=findViewById(R.id.backfromeditprofile);
        editinter=findViewById(R.id.editinterestbtn);
        profimg=findViewById(R.id.profileimage);
        addprofimg=findViewById(R.id.editprofileimage);
        rc=findViewById(R.id.recyclerView3);
        name=findViewById(R.id.editfullname);
        update=findViewById(R.id.updateprofbtn);
        Intent intent=getIntent();
        String nm=intent.getStringExtra("fullName");
        name.setText(nm);
        String userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference storageReference= FirebaseStorage.getInstance().getReference();
        StorageReference profoleRef=storageReference.child("ProfileImg").child(userId);
        profoleRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profimg);
            }
        });
        editinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a=new Intent(Editprofile.this,editinterests.class);
                a.putExtra("from","editprof");
                startActivity(a);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Editprofile.this,HomeActivity.class));
                finish();
            }
        });
        addprofimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery,1000);
            }
        });

        DocumentReference documentReference= FirebaseFirestore.getInstance().collection("Users").document(userId);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        List<Map<String, Object>> interests = (List<Map<String, Object>>) document.get("User_Interests");
                        rc.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.HORIZONTAL));
                        adapter=new TagsAdapter(interests);
                        rc.setAdapter(adapter);
                    }
                }
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nmtxt=name.getText().toString();
                if (uri!=null)
                {
                    Map<String, Object> namefb = new HashMap<>();
                    namefb.put("Full_Name", nmtxt);
                    FirebaseFirestore.getInstance().collection("Users").document(userId).update(namefb);
                    final StorageReference fileref = storageReference.child("ProfileImg")
                            .child(userId);
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
                            Snackbar.make(view, "Updating....", Snackbar.LENGTH_LONG).show();
                            final Uri downloadUri = task.getResult();
                            String pro = downloadUri.toString();
                            Map<String, Object> profileimg = new HashMap<>();
                            profileimg.put("profileImage", pro);
                            FirebaseFirestore.getInstance().collection("Users").document(userId).update(profileimg).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                       startActivity(new Intent(Editprofile.this,HomeActivity.class));
                                       finish();
                                }
                            });
                }
                    });
                 }
                 else
                {
                    Snackbar.make(view, "Updating....", Snackbar.LENGTH_LONG).show();
                    Map<String, Object> namefb = new HashMap<>();
                    namefb.put("Full_Name", nmtxt);
                    FirebaseFirestore.getInstance().collection("Users").document(userId).update(namefb);
                    startActivity(new Intent(Editprofile.this,HomeActivity.class));
                    finish();

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
                uCrop.start(Editprofile.this);
                uCrop.withOptions(getCropOptions());
            }
        }
        else if(requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK){
            uri = UCrop.getOutput(data);
            profimg.setImageURI(uri);
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