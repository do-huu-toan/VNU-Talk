package com.example.vnutalkapp.src.apdater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vnutalkapp.R;
import com.example.vnutalkapp.src.model.Chat;

import java.util.List;

public class ChatApdater extends RecyclerView.Adapter<ChatApdater.MessageViewHolder> {
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private String username;
    private List<Chat> mListChat;

    public void setData(List<Chat> list, String username){
        this.mListChat = list;
        this.username = username;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right, parent, false);
            return new MessageViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_left, parent, false);
            return new MessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Chat chat = mListChat.get(position);
        if(chat == null){
            return;
        }
        holder.tvMessage.setText(chat.getMessage());
    }

    @Override
    public int getItemViewType(int position) {
        if(mListChat.get(position).getSender().equals(username)){
            return MSG_TYPE_RIGHT;
        }
        else return MSG_TYPE_LEFT;
    }

    @Override
    public int getItemCount() {
        if(mListChat != null){
            return mListChat.size();
        }
        return 0;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{
        private TextView tvMessage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMessage = itemView.findViewById(R.id.tv_message);
        }
    }
}
