package com.example.wize;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

import java.util.HashMap;
import java.util.Map;

public class SignIn extends AppCompatActivity {
    private static final String TAG = "SignIn";
    private TextView forgotpassword;
    private FirebaseAuth mAuth;
    Button sgn;
    GoogleSignInButton sign;
    private GoogleSignInClient googleSignInClient;
    //SignInButton sign;
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
        st2=findViewById(R.id.sti2);
        sign=findViewById(R.id.signInButton);

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
                                Intent intent = new Intent(SignIn.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {

                                Toast.makeText(SignIn.this, "Email not Verified", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(SignIn.this, "Login Failed!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText resetMail = new EditText(view.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext(),R.style.CustomDialogTheme);
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter Your Email To Receive The Password Reset Link.");
                passwordResetDialog.setView(resetMail);
                passwordResetDialog.setCancelable(true).setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail = resetMail.getText().toString();
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(SignIn.this, "Password Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignIn.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                passwordResetDialog.create().show();

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
            Toast.makeText(this, "Sign In Sucessfull", Toast.LENGTH_SHORT).show();
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

            Toast.makeText(this, "Welcome"+Name, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SignIn.this,HomeActivity.class));
        }
    }
}