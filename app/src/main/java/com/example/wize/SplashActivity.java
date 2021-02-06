package com.example.wize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
FirebaseAuth mAuth;

TextView b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        b=findViewById(R.id.splashtext);
        mAuth= FirebaseAuth.getInstance();
        FirebaseUser users=mAuth.getCurrentUser();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if ((mAuth.getCurrentUser() != null) && users.isEmailVerified()) {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                }
                else
                {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }

            }
        },4000);
    }
}