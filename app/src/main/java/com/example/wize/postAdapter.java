package com.example.wize;

import android.net.Uri;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

public class postAdapter extends FirestoreRecyclerAdapter<postModel,postAdapter.postHolder> {

    public postAdapter(@NonNull FirestoreRecyclerOptions<postModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull postHolder holder, int position, @NonNull postModel model) {
     holder.name.setText(model.Full_Name);
     holder.text.setText(model.textPost);
     holder.like.setText(model.nLikes.toString());
     holder.comment.setText(model.nComments.toString());
       Uri postUri = Uri.parse(model.getImage());
       Picasso.get().load(postUri).into(holder.img);
       if (model.type.equals("Text Post"))
       {
           holder.img.setVisibility(View.GONE);
       }
        String wordMonth = null;
        String postTime = Long.toString(Long.parseLong(model.timeStamp));

        String date = convertDate(postTime,"dd");
        String month = convertDate(postTime,"MM");

        if(month.equals("01")){
            wordMonth = "Jan";
        }
        if(month.equals("02")){
            wordMonth = "Feb";
        }
        if(month.equals("03")){
            wordMonth = "March";
        }
        if(month.equals("04")){
            wordMonth = "April";
        }
        if(month.equals("05")){
            wordMonth = "May";
        }
        if(month.equals("06")){
            wordMonth = "June";
        }
        if(month.equals("07")){
            wordMonth = "July";
        }
        if(month.equals("08")){
            wordMonth = "Aug";
        }
        if(month.equals("09")){
            wordMonth = "Sep";
        }
        if(month.equals("10")){
            wordMonth = "Oct";
        }
        if(month.equals("11")){
            wordMonth = "Nov";
        }
        if(month.equals("12")){
            wordMonth = "Dec";
        }

        String time = convertDate(postTime,"hh:mm:ss");
        String display = wordMonth +"  " + date + ",  " + time +"  "+convertDate(postTime,"a");
         holder.time.setText(display);
         holder.lik.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if (holder.lik.isChecked())
                 {
                     holder.lik.setButtonDrawable(R.drawable.ic_vector__5_);
                 }
                 else
                 {
                     holder.lik.setButtonDrawable(R.drawable.ic_vector__6_);

                 }
             }
         });
    }

    @NonNull
    @Override
    public postHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.imagepostrecycler,
                parent, false);
        return new postHolder(v);
    }

    public class postHolder extends RecyclerView.ViewHolder {
        TextView name,like,comment,time;
        ShapeableImageView img;
        ReadMoreTextView text;
        ToggleButton lik;
        public postHolder(@NonNull View itemView) {
            super(itemView);
           name=itemView.findViewById(R.id.textView21);
            text=itemView.findViewById(R.id.textView23);
            like=itemView.findViewById(R.id.textView24);
            comment=itemView.findViewById(R.id.comment);
            time=itemView.findViewById(R.id.textView22);
            img=itemView.findViewById(R.id.imageViewaddpst);
            lik=itemView.findViewById(R.id.toggleButton2);

        }
    }
    public String convertDate(String dateInMilliseconds,String dateFormat) {
        return DateFormat.format(dateFormat, Long.parseLong(dateInMilliseconds)).toString();
    }
}
