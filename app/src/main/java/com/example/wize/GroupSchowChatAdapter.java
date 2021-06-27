package com.example.wize;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupSchowChatAdapter extends RecyclerView.Adapter<GroupSchowChatAdapter.GroupMessageHolder> {
    private static final int SENT = 0;
    private static final int RECEIVED = 1;

    private String userId;
    private List<Groupsmessagesmodel> messages;
    public GroupSchowChatAdapter(List<Groupsmessagesmodel> messages, String userId) {
        this.messages = messages;
        this.userId = userId;
    }


    @NonNull
    @Override
    public GroupMessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == SENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.rightgroupchatrecycler, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.leftgroupchatrecycler, parent, false);
        }
        return new GroupSchowChatAdapter.GroupMessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupMessageHolder holder, int position) {
        holder.bind(messages.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getSender().contentEquals(userId)) {
            return SENT;
        } else {
            return RECEIVED;
        }
    }
    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class GroupMessageHolder extends RecyclerView.ViewHolder {
        TextView mes,name;
        CircleImageView image;
        public GroupMessageHolder(@NonNull View itemView) {
            super(itemView);
            mes=itemView.findViewById(R.id.leftgroupchatforchatting);
            image=itemView.findViewById(R.id.groupchatdpgroup);
            name=itemView.findViewById(R.id.namegroupchat);
        }
        public void bind(Groupsmessagesmodel chat) {
            mes.setText(chat.getMessage());
            name.setText(chat.getSenderName());
            Uri profuri = Uri.parse(chat.getImage());
            Picasso.get().load(profuri).into(image);
        }
    }

}
