package com.example.wize;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

public class commentAdapter extends FirestoreRecyclerAdapter<commentModel,commentAdapter.commentHolder> {
    String userid= FirebaseAuth.getInstance().getCurrentUser().getUid();

    public commentAdapter(@NonNull FirestoreRecyclerOptions<commentModel> options) {
        super(options);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onBindViewHolder(@NonNull commentAdapter.commentHolder holder, int position, @NonNull commentModel model) {
        holder.cent.setText(model.Comment);

        holder.name.setText(model.cName);
        if (model.cName==userid)
        {
            holder.cent.setBackgroundColor(R.color.colorPrimary);
        }
        else
        {
            holder.cent.setBackgroundColor(R.color.colorPrimaryDark);
        }
    }

    @NonNull
    @Override
    public commentAdapter.commentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.commentrecycler,
                parent, false);
        return new commentHolder(v);
    }

    public class commentHolder extends RecyclerView.ViewHolder {
        TextView cent,name;
        public commentHolder(@NonNull View itemView) {
            super(itemView);
            cent=itemView.findViewById(R.id.commentpagecom);
            name=itemView.findViewById(R.id.nameincomment);

        }
    }
}
