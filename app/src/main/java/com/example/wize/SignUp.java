package com.example.wize;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
    Dialog dialog;
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

                                      /*  dialog=new Dialog(SignUp.this);
                                        dialog.*/

                                        Toast.makeText(SignUp.this, "Verification Email has been sent", Toast.LENGTH_SHORT).show();

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
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "On Success: User Profile Created for" + userId);
                                    }
                                });
                                Intent intent = new Intent(SignUp.this, SignIn.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(SignUp.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            });

    }
}