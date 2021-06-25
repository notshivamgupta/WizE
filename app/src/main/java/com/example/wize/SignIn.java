package com.example.wize;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class SignIn extends AppCompatActivity {
    private static final String TAG = "SignIn";
    private TextView forgotpassword;
    private FirebaseAuth mAuth;
    Button sgn;
    Button sign;
    ImageView ab;
    private android.app.AlertDialog.Builder builder;
    private android.app.AlertDialog dialog;
    TextView sgnup;
    EditText restmail;
    FirebaseFirestore fStore;
    Button restbtn;
    ImageView cutrest;
    private GoogleSignInClient googleSignInClient;
    private int RC_SIGN_IN=1;
    private TextInputEditText st1,st2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        forgotpassword=findViewById(R.id.forgotpassword);
        mAuth = FirebaseAuth.getInstance();
        sgn=findViewById(R.id.btnsgn);
        st1=findViewById(R.id.sti1);
        fStore=FirebaseFirestore.getInstance();
        st2=findViewById(R.id.sti2);
        ab=findViewById(R.id.appclose);
        sgnup=findViewById(R.id.butntogosignup);
        sign=findViewById(R.id.signInButton);
        ab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
        sgnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignIn.this,SignUp.class));
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient= GoogleSignIn.getClient(this,gso);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signin();
            }
        });


        sgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Email=st1.getText().toString().trim();
                final String Pass=st2.getText().toString().trim();
                if(TextUtils.isEmpty(Email)) {

                    st1.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(Pass))
                {
                    st2.setError("Password is Required");
                    return;
                }
                if (Pass.length()<7)
                {
                    st2.setError("Password must contain atleast 8 characters");
                    return;
                }
                mAuth.signInWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            FirebaseUser users=mAuth.getCurrentUser();
                            if (users.isEmailVerified()){
                                Toast.makeText(SignIn.this, "Login Sucessful!", Toast.LENGTH_SHORT).show();

                                String userId=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document(userId);
                                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                if (document.get("User_Interests") != null && document.get("UserName") != null ) {
                                                    startActivity(new Intent(SignIn.this,HomeActivity.class));
                                                    finish();
                                                }
                                                else {
                                                    startActivity(new Intent(SignIn.this, Intrests_Page.class));
                                                    finish();
                                                }
                                            }

                                        }
                                    }
                                });
                            }
                            else {
                                Snackbar.make(view, "Email not Verified!", Snackbar.LENGTH_LONG).show();
                            }
                        }
                        else {
                            Snackbar.make(view, "Login Failed!", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });


            }
        });


        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder = new android.app.AlertDialog.Builder(SignIn.this);
                final View vie = getLayoutInflater().inflate(R.layout.forgotpassword,null);
                builder.setView(vie);
                dialog = builder.create();
                dialog.show();
                cutrest=(ImageView) dialog.findViewById(R.id.cutresetmail);
                restbtn=(Button) dialog.findViewById(R.id.resetpass);
                restmail=(EditText) dialog.findViewById(R.id.editText);
                cutrest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                restbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mail=restmail.getText().toString();
                        if(TextUtils.isEmpty(mail)) {
                            restmail.setError("Email is Required");
                            return;
                        }
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Snackbar.make(view, "Password Reset Link Sent To Your Email.", Snackbar.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignIn.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }
                        },4000);
                    }
                });
            }
        });
    }


    private void signin() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSigninResult(task);
        }
    }
    private void handleSigninResult( Task<GoogleSignInAccount> task) {

        try {
            GoogleSignInAccount account =task.getResult(ApiException.class);
          //  Toast.makeText(this, "Sign In Sucessfull", Toast.LENGTH_SHORT).show();
            firebaseAuthWithGoogle(account.getIdToken());
        } catch (ApiException e) {
            Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show();
            firebaseAuthWithGoogle(null);
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            updateUI(user);
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }

                    }
                });
    }
    private void updateUI(FirebaseUser firebaseUser)
    {
        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (account!=null)
        {
            String Name=account.getDisplayName();
            String Email=account.getEmail();
            String userId=FirebaseAuth.getInstance().getCurrentUser().getUid();
            DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document(userId);
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            if ((document.get("Full_Name") != null && document.get("User_Interests") != null && document.get("UserName") != null)) {
                                startActivity(new Intent(SignIn.this,HomeActivity.class));
                                finish();
                            }
                        }
                        else {
                            Map<String, Object> user = new HashMap<>();
                            user.put("Full_Name", Name);
                            user.put("Email_Id", Email);
                            user.put("User_Id", userId);
                            user.put("Posts",0);
                            user.put("Friends",0);
                            user.put("Groups",0);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    DocumentReference documentReference= FirebaseFirestore.getInstance().collection("Users").document(userId);
                                    documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                            String img=value.getString("profileImage");
                                            if (img==null)
                                            {
                                                Map<String, Object> user = new HashMap<>();
                                                user.put("profileImage","https://firebasestorage.googleapis.com/v0/b/wize-675b2.appspot.com/o/ProfileImg%2Fdefuserimg.png?alt=media&token=96541f8e-0ace-4497-9249-516b4877795b");
                                                documentReference.update(user);
                                            }
                                        }
                                    });

                                    Log.d(TAG, "On Success: User Profile Created for" + userId);
                                }
                            });
                            startActivity(new Intent(SignIn.this, Intrests_Page.class));
                            finish();
                        }
                    }
                }
            });
        }
    }
}