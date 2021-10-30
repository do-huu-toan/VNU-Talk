package com.example.vnutalkapp.src.apdater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vnutalkapp.R;
import com.example.vnutalkapp.src.model.MessageItem;

import java.util.List;

public class MessageApdater extends RecyclerView.Adapter<MessageApdater.MessageViewHolder>{

    private List<MessageItem> mListMessage;

    public void setData(List<MessageItem> data){
        mListMessage = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        MessageItem message = mListMessage.get(position);
        if(message == null)
            return;
        holder.user.setText(message.getUser());
        holder.message.setText(message.getMessage());
    }

    @Override
    public int getItemCount() {
        if(mListMessage != null){
            return mListMessage.size();
        }
        return 0;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{
        private TextView user;
        private TextView message;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.tv_item_user);
            message = itemView.findViewById(R.id.tv_item_user_message);
        }
    }
}
