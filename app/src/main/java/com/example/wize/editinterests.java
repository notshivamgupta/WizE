package com.example.wize;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class editinterests extends AppCompatActivity {
ImageView back;
  private   ToggleButton appdev,webdev,oop,mlai,grapdgn,digmar,phot,fin,cybsec,comskill,entre,gamdev,scinrec;
    List<String> intr;
    Button done;
    String userId;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editinterests);
        back=findViewById(R.id.backfromeditinterest);
        appdev=findViewById(R.id.appdevei);
        webdev=findViewById(R.id.webdevei);
        oop=findViewById(R.id.oopei);
        mlai=findViewById(R.id.ml_aiei);
        grapdgn=findViewById(R.id.Grpdgnei);
        digmar=findViewById(R.id.digmarei);
        phot=findViewById(R.id.photei);
        fin=findViewById(R.id.finei);
        cybsec=findViewById(R.id.cybsecei);
        comskill=findViewById(R.id.comskillsei);
        entre=findViewById(R.id.entrepreneurshipei);
        gamdev=findViewById(R.id.gamedevei);
        scinrec=findViewById(R.id.scinrecei);
        done=findViewById(R.id.donebtn);
        intr=new ArrayList<>();
        Intent intent=getIntent();
        String a=intent.getStringExtra("from");
        String nm=intent.getStringExtra("fullName");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (a.equals("home"))
                {
                    startActivity(new Intent(editinterests.this,HomeActivity.class));
                    finish();
                }
                else {
                    Intent intent=new Intent(editinterests.this,Editprofile.class);
                    intent.putExtra("fullName",nm);
                    startActivity(intent);
                    finish();
                }
            }
        });
        appdev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (appdev.isChecked())
                {

                    appdev.setBackgroundDrawable(getResources().getDrawable(R.drawable.togbtnback));

                    appdev.setTextColor(getResources().getColor(R.color.white));
                    intr.add("App development");
                }
                else
                {
                    appdev.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    appdev.setTextColor(getResources().getColor(R.color.black));
                    intr.remove("App development");
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
                    intr.add("Web development");
                }
                else
                {
                    webdev.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    webdev.setTextColor(getResources().getColor(R.color.black));
                    intr.remove("Web development");
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
                    intr.add("Object oriented programing");
                }
                else
                {
                    oop.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    oop.setTextColor(getResources().getColor(R.color.black));
                    intr.remove("Object oriented programing");
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
                    intr.add("ML/AI");
                }
                else
                {
                    mlai.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    mlai.setTextColor(getResources().getColor(R.color.black));
                    intr.remove("ML/AI");
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
                    intr.add("Graphic design");
                }
                else
                {
                    grapdgn.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    grapdgn.setTextColor(getResources().getColor(R.color.black));
                    intr.remove("Graphic design");
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
                    intr.add("Digital marketing");
                }
                else
                {
                    digmar.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    digmar.setTextColor(getResources().getColor(R.color.black));
                    intr.remove("Digital marketing");
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
                    intr.add("Photography");
                }
                else
                {
                    phot.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    phot.setTextColor(getResources().getColor(R.color.black));
                    intr.remove("Photography");
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
                    intr.add("Finance");
                }
                else
                {
                    fin.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    fin.setTextColor(getResources().getColor(R.color.black));
                    intr.remove("Finance");
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
                    intr.add("Cyber security");
                }
                else
                {
                    cybsec.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    cybsec.setTextColor(getResources().getColor(R.color.black));
                    intr.remove("Cyber security");
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
                    intr.add("Communication skills");
                }
                else
                {
                    comskill.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    comskill.setTextColor(getResources().getColor(R.color.black));
                    intr.remove("Communication skills");
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
                    intr.add("Entrepreneurship");
                }
                else
                {
                    entre.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    entre.setTextColor(getResources().getColor(R.color.black));
                    intr.remove("Entrepreneurship");
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
                    intr.add("3d and Game development");
                }
                else
                {
                    gamdev.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    gamdev.setTextColor(getResources().getColor(R.color.black));
                    intr.remove("3d and Game development");
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
                    intr.add("Science and research");
                }
                else
                {
                    scinrec.setBackgroundDrawable(getResources().getDrawable(R.drawable.correcttogbtn));
                    scinrec.setTextColor(getResources().getColor(R.color.black));
                    intr.remove("Science and research");
                }
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                userId = mAuth.getCurrentUser().getUid();
                DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document(userId);
                Map<String, Object> user = new HashMap<>();
                user.put("User_Interests", intr);
                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        String TAG = "Tag";
                        Log.d(TAG, "On Success: Interests added for" + userId);
                    }
                });
                if (a.equals("home"))
                {
                    startActivity(new Intent(editinterests.this,HomeActivity.class));
                    finish();
                }
                else {
                    Intent intent=new Intent(editinterests.this,Editprofile.class);
                    intent.putExtra("fullName",nm);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}