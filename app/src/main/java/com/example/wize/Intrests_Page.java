package com.example.wize;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Intrests_Page extends AppCompatActivity {
ToggleButton appdev,webdev,oop,mlai,grapdgn,digmar,phot,fin,cybsec,comskill,entre,gamdev,scinrec;
Button mainbtn;
FirebaseAuth mAuth;
EditText username;
List<String> intrests;
    FirebaseFirestore db;
    boolean ok=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intrests__page);
        username=findViewById(R.id.editText);
        mainbtn=findViewById(R.id.intresttohome);
        appdev=findViewById(R.id.appdev);
        webdev=findViewById(R.id.webdev);
        oop=findViewById(R.id.oop);
        mlai=findViewById(R.id.ml_ai);
        grapdgn=findViewById(R.id.Grpdgn);
        digmar=findViewById(R.id.digmar);
        phot=findViewById(R.id.phot);
        fin=findViewById(R.id.fin);
        cybsec=findViewById(R.id.cybsec);
        comskill=findViewById(R.id.comskills);
        entre=findViewById(R.id.entrepreneurship);
        gamdev=findViewById(R.id.gamedev);
        scinrec=findViewById(R.id.scinrec);
        intrests=new ArrayList<>();
        db=FirebaseFirestore.getInstance();
        appdev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (appdev.isChecked())
                {

                    appdev.setBackgroundDrawable(getResources().getDrawable(R.drawable.togbtnback));

                    appdev.setTextColor(getResources().getColor(R.color.white));
                    intrests.add("App development");
                }
                else
                {
                    appdev.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    appdev.setTextColor(getResources().getColor(R.color.black));
                    intrests.remove("App development");
                }
            }
        });
        webdev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (webdev.isChecked())
                {
                    webdev.setBackgroundDrawable(getResources().getDrawable(R.drawable.togbtnback));

                    webdev.setTextColor(getResources().getColor(R.color.white));
                    intrests.add("Web development");
                }
                else
                {
                    webdev.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    webdev.setTextColor(getResources().getColor(R.color.black));
                    intrests.remove("Web development");
                }
            }
        });
        oop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (oop.isChecked())
                {
                    oop.setBackgroundDrawable(getResources().getDrawable(R.drawable.togbtnback));

                    oop.setTextColor(getResources().getColor(R.color.white));
                    intrests.add("Object oriented programing");
                }
                else
                {
                    oop.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    oop.setTextColor(getResources().getColor(R.color.black));
                    intrests.remove("Object oriented programing");
                }
            }
        });
        mlai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mlai.isChecked())
                {
                    mlai.setBackgroundDrawable(getResources().getDrawable(R.drawable.togbtnback));

                    mlai.setTextColor(getResources().getColor(R.color.white));
                    intrests.add("ML/AI");
                }
                else
                {
                    mlai.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    mlai.setTextColor(getResources().getColor(R.color.black));
                    intrests.remove("ML/AI");
                }
            }
        });
        grapdgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (grapdgn.isChecked())
                {
                    grapdgn.setBackgroundDrawable(getResources().getDrawable(R.drawable.togbtnback));

                    grapdgn.setTextColor(getResources().getColor(R.color.white));
                    intrests.add("Graphic design");
                }
                else
                {
                    grapdgn.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    grapdgn.setTextColor(getResources().getColor(R.color.black));
                    intrests.remove("Graphic design");
                }
            }
        });
        digmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (digmar.isChecked())
                {
                    digmar.setBackgroundDrawable(getResources().getDrawable(R.drawable.togbtnback));

                    digmar.setTextColor(getResources().getColor(R.color.white));
                    intrests.add("Digital marketing");
                }
                else
                {
                    digmar.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    digmar.setTextColor(getResources().getColor(R.color.black));
                    intrests.remove("Digital marketing");
                }
            }
        });
        phot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phot.isChecked())
                {
                    phot.setBackgroundDrawable(getResources().getDrawable(R.drawable.togbtnback));

                    phot.setTextColor(getResources().getColor(R.color.white));
                    intrests.add("Photography");
                }
                else
                {
                    phot.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    phot.setTextColor(getResources().getColor(R.color.black));
                    intrests.remove("Photography");
                }
            }
        });
        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fin.isChecked())
                {
                    fin.setBackgroundDrawable(getResources().getDrawable(R.drawable.togbtnback));

                    fin.setTextColor(getResources().getColor(R.color.white));
                    intrests.add("Finance");
                }
                else
                {
                    fin.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    fin.setTextColor(getResources().getColor(R.color.black));
                    intrests.remove("Finance");
                }
            }
        });
        cybsec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cybsec.isChecked())
                {
                    cybsec.setBackgroundDrawable(getResources().getDrawable(R.drawable.togbtnback));

                    cybsec.setTextColor(getResources().getColor(R.color.white));
                    intrests.add("Cyber security");
                }
                else
                {
                    cybsec.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    cybsec.setTextColor(getResources().getColor(R.color.black));
                    intrests.remove("Cyber security");
                }
            }
        });
        comskill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (comskill.isChecked())
                {
                    comskill.setBackgroundDrawable(getResources().getDrawable(R.drawable.togbtnback));

                    comskill.setTextColor(getResources().getColor(R.color.white));
                    intrests.add("Communication skills");
                }
                else
                {
                    comskill.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    comskill.setTextColor(getResources().getColor(R.color.black));
                    intrests.remove("Communication skills");
                }
            }
        });
        entre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (entre.isChecked())
                {
                    entre.setBackgroundDrawable(getResources().getDrawable(R.drawable.togbtnback));

                    entre.setTextColor(getResources().getColor(R.color.white));
                    intrests.add("Entrepreneurship");
                }
                else
                {
                    entre.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    entre.setTextColor(getResources().getColor(R.color.black));
                    intrests.remove("Entrepreneurship");
                }
            }
        });
        gamdev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gamdev.isChecked())
                {
                    gamdev.setBackgroundDrawable(getResources().getDrawable(R.drawable.togbtnback));

                    gamdev.setTextColor(getResources().getColor(R.color.white));
                    intrests.add("3d and Game development");
                }
                else
                {
                    gamdev.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    gamdev.setTextColor(getResources().getColor(R.color.black));
                    intrests.remove("3d and Game development");
                }
            }
        });
        scinrec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scinrec.isChecked())
                {
                    scinrec.setBackgroundDrawable(getResources().getDrawable(R.drawable.togbtnback));
                    scinrec.setTextColor(getResources().getColor(R.color.white));
                    intrests.add("Science and research");
                }
                else
                {
                    scinrec.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    scinrec.setTextColor(getResources().getColor(R.color.black));
                    intrests.remove("Science and research");
                }
            }
        });
        mainbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usernme=username.getText().toString().trim();
                if(usernme.isEmpty()){
                    username.setError("username cannot be empty");
                    return;
                }
                if(!usernme.equals(usernme.toLowerCase())){
                    username.setError("No Caps Allowed");
                    return;
                }
                if (usernme.contains(" ")) {
                    username.setError("No Spaces Allowed");
                    return;
                }
                db.collection("Users").whereEqualTo("UserName", usernme).addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.d("Check", "listen failed: " + error.getMessage());
                        }
                        List list = new ArrayList<>();
                        for(QueryDocumentSnapshot query : value){
                            list.add(query.getString("UserName"));
                        }
                        if(list.isEmpty()){
                       ok=true;
                        }
                        else{
                            username.setError("Username Taken");
                        }
                    }
                });
                if (ok==true) {
                    startActivity(new Intent(Intrests_Page.this, HomeActivity.class));
                    finish();
                    String userId;
                    mAuth = FirebaseAuth.getInstance();
                    userId = mAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = db.collection("Users").document(userId);
                    Map<String, Object> user = new HashMap<>();
                    user.put("User_Interests", intrests);
                    user.put("UserName", usernme);
                    documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            String TAG = "Tag";
                            Log.d(TAG, "On Success: Interests added for" + userId);
                        }
                    });
                }

            }
        });
    }
}