package com.example.wize;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupSchowChatAdapter extends RecyclerView.Adapter<GroupSchowChatAdapter.GroupMessageHolder> {
    private static final int SENT = 0;
    private static final int RECEIVED = 1;

    private String userId;
    private List<Groupsmessagesmodel> messages;
    public GroupSchowChatAdapter(List<Groupsmessagesmodel> messages, String userId) {
    }


    @NonNull
    @Override
    public GroupMessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupMessageHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class GroupMessageHolder extends RecyclerView.ViewHolder {
        TextView message;
        CircleImageView image;
        public GroupMessageHolder(@NonNull View itemView) {
            super(itemView);
            message=itemView.findViewById(R.id.leftgroupchat);
            image=itemView.findViewById(R.id.groupchatdp);
        }
        public void bind(GroupMessageHolder chat) {
           /* message.setText(chat.getMessage());
            Uri profuri = Uri.parse();
            Picasso.get().load(profuri).into(holder.img);*/
        }
    }

}
