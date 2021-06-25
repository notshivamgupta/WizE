package com.example.wize;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    private TextInputEditText Rt1,Rt2,Rt3;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    String userId;
    Button sgnvrf;
    ImageView cutvrf;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    ImageView ig;
    public static final String TAG = "Tag";
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        Rt1 = findViewById(R.id.rti1);
        Rt2 = findViewById(R.id.rti2);
        Rt3 = findViewById(R.id.rti3);
        ig=findViewById(R.id.imageView3);
        button = findViewById(R.id.btn1);
        ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this,SignIn.class));
            }
        });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String Email = Rt2.getText().toString().trim();
                    final String Pass = Rt3.getText().toString().trim();
                    final String Name = Rt1.getText().toString();
                    if (TextUtils.isEmpty(Name)) {

                        Rt1.setError("Name is Required");
                        return;
                    }
                    if (TextUtils.isEmpty(Email)) {

                        Rt2.setError("Email is Required");
                        return;
                    }
                    if (TextUtils.isEmpty(Pass)) {
                        Rt3.setError("Password is Required");
                        return;
                    }
                    if (Pass.length() < 7) {
                        Rt3.setError("Password must contain atleast 8 characters");
                        return;
                    }
                    mAuth.createUserWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser users = mAuth.getCurrentUser();
                                users.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {

                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        builder = new AlertDialog.Builder(SignUp.this);
                                        final View view = getLayoutInflater().inflate(R.layout.verificationmail,null);
                                        builder.setView(view);
                                        dialog = builder.create();
                                        dialog.show();
                                        cutvrf=(ImageView) dialog.findViewById(R.id.cutverification);
                                        sgnvrf=(Button) dialog.findViewById(R.id.signfromverif);
                                        cutvrf.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialog.dismiss();
                                            }
                                        });
                                        sgnvrf.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                startActivity(new Intent(SignUp.this,SignIn.class));
                                                finish();
                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("Tag", "onFailure:  Email not sent" + e.getMessage());
                                    }
                                });
                                userId = mAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = db.collection("Users").document(userId);
                                Map<String, Object> user = new HashMap<>();
                                user.put("Full_Name", Name);
                                user.put("Email_Id", Email);
                                user.put("User_Id", userId);
                                user.put("Posts",0);
                                user.put("Friends",0);
                                user.put("Groups",0);
                                user.put("profileImage","https://firebasestorage.googleapis.com/v0/b/wize-675b2.appspot.com/o/ProfileImg%2Fdefuserimg.png?alt=media&token=96541f8e-0ace-4497-9249-516b4877795b");
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "On Success: User Profile Created for" + userId);
                                    }
                                });
                            } else {
                                Snackbar.make(view, "Error!" + task.getException().getMessage(), Snackbar.LENGTH_LONG).show();
                            }

                        }
                    });
                }
            });

    }
}