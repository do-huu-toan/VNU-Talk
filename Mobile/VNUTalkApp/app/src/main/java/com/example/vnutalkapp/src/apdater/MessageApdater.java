package com.example.vnutalkapp.src.apdater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vnutalkapp.R;
import com.example.vnutalkapp.src.model.MessageItem;
import com.example.vnutalkapp.src.view.fragment.MessageFragment;

import java.util.List;

public class MessageApdater extends RecyclerView.Adapter<MessageApdater.MessageViewHolder>{

    private List<MessageItem> mListMessage;
    private Context mContext;
    public MessageApdater(Context context, List<MessageItem> mListMessage) {
        this.mListMessage = mListMessage;
        this.mContext = context;
    }

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
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, message.getUser(), Toast.LENGTH_SHORT).show();
            }
        });
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
        private RelativeLayout layoutItem;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.tv_item_user);
            message = itemView.findViewById(R.id.tv_item_user_message);
            layoutItem = itemView.findViewById(R.id.layout_item);
        }
    }
}
