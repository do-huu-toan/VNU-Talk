package com.example.vnutalkapp.src.apdater;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vnutalkapp.R;
import com.example.vnutalkapp.src.model.PhoneBookItem;
import com.example.vnutalkapp.src.view.ChatActivity;

import java.util.List;

public class PhoneBookApdater extends RecyclerView.Adapter<PhoneBookApdater.PhoneBookViewHolder>{

    private List<PhoneBookItem> listUser;
    private Context mContext;
    private Bundle mBundle;
    public PhoneBookApdater(Context mContext, List<PhoneBookItem> listUser, Bundle bundle) {
        this.listUser = listUser;
        this.mContext = mContext;
        this.mBundle = bundle;
    }
    public void setData(List<PhoneBookItem> data){
        listUser = data;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public PhoneBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_phonebook, parent, false);
        return new PhoneBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneBookViewHolder holder, int position) {
        PhoneBookItem item = listUser.get(position);
        holder.tv_userFullName.setText(item.getFullName());
        holder.img_userOnline.setVisibility(item.isOnline() ? View.VISIBLE : View.INVISIBLE);
        // Sự kiện sau khi ấn vào 1 liên hệ
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,item.getUserId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, ChatActivity.class);
                // Truyền Id vào bundle
                Bundle bundle = mBundle;
                bundle.putString("receiverId",item.getUserId());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(listUser != null){
            return listUser.size();
        }
        return 0;
    }

    public class PhoneBookViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_userFullName;
        private ImageView img_userOnline;
        private RelativeLayout layoutItem;
        public PhoneBookViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_userFullName = itemView.findViewById(R.id.tv_user_fullname);
            img_userOnline = itemView.findViewById(R.id.user_online);
            layoutItem = itemView.findViewById(R.id.layout_item);
        }
    }
}
