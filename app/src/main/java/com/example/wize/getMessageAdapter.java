package com.example.wize;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class getMessageAdapter extends RecyclerView.Adapter<getMessageAdapter.GetMessageHolder> {

    private static final int SENT = 0;
    private static final int RECEIVED = 1;

    private String userId;
    private List<getMessageModel> messages;

    public getMessageAdapter(List<getMessageModel> messages, String userId) {

        this.messages = messages;
        this.userId = userId;
    }

    @NonNull
    @Override
    public GetMessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == SENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.rightchatrecycler, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.leftchatrecycler, parent, false);
        }
        return new GetMessageHolder(view);
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
    public void onBindViewHolder(@NonNull GetMessageHolder holder, int position) {

        holder.bind(messages.get(position));

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class GetMessageHolder extends RecyclerView.ViewHolder{
        TextView message;
        public GetMessageHolder(@NonNull View itemView) {
            super(itemView);
            message=itemView.findViewById(R.id.leftchat);
        }
        public void bind(getMessageModel chat) {
            message.setText(chat.getMessage());
        }

    }

}
