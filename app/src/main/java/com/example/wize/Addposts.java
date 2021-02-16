package com.example.wize;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.yalantis.ucrop.UCrop;

import java.io.File;

public class Addposts extends AppCompatActivity {
TextView name;
String nm,userId;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    TextView addphoto;
    Uri uri;
    ImageView imageSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addposts);
         name=findViewById(R.id.usernameinaddpost);
         addphoto=findViewById(R.id.linearlayout1);
        imageSelected = findViewById(R.id.imageViewaddpst);
        firebaseAuth= FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();
        userId=firebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference= fStore.collection("Users").document(userId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                nm=value.getString("Full_Name");
                name.setText(nm);
            }
        });
        addphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery,1000);
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
            imageSelected.setImageURI(uri);
            imageSelected.setVisibility(View.VISIBLE);
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