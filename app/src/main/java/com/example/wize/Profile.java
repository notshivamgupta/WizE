package com.example.wize;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class Profile extends Fragment {

    Button logout,edit;
    ImageView back;
    ToggleButton menu;
    private GoogleSignInClient googleSignInClient;


    public Profile() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile, container, false);
        menu=view.findViewById(R.id.imageView6);
        back=view.findViewById(R.id.backfromprofile);
        logout=view.findViewById(R.id.buttonLo);
        edit=view.findViewById(R.id.buttonedit);
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient= GoogleSignIn.getClient(getActivity(),gso);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (menu.isChecked()) {
                    logout.setVisibility(view.VISIBLE);
                    edit.setVisibility(view.VISIBLE);
                }
                else
                {
                    logout.setVisibility(view.INVISIBLE);
                    edit.setVisibility(view.INVISIBLE);
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),HomeActivity.class));
                getActivity().finish();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertBuilder=new AlertDialog.Builder(getActivity());
                alertBuilder.create();
                alertBuilder.setMessage("Are You Sure You Want To Log Out?");
                alertBuilder.setCancelable(false);
                alertBuilder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        googleSignInClient.signOut();
                        startActivity(new Intent(getActivity(),SignIn.class));
                        getActivity().finish();
                    }
                });
                alertBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "The Operation Cancelled", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                });
                alertBuilder.show();
            }
        });

        return view;
    }
}