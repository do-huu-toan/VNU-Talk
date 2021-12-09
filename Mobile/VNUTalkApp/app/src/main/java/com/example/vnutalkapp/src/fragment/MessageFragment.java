package com.example.vnutalkapp.src.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vnutalkapp.R;
import com.example.vnutalkapp.src.apdater.MessageApdater;
import com.example.vnutalkapp.src.model.MessageItem;

import java.util.ArrayList;
import java.util.List;


public class MessageFragment extends Fragment {
    private RecyclerView rcvMessage;
    private MessageApdater msgApdater;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        // RecycleView
        rcvMessage = view.findViewById(R.id.rcv_listMessage);
        msgApdater = new MessageApdater(inflater.getContext(), getListData());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(inflater.getContext());
        rcvMessage.setLayoutManager(linearLayoutManager);
        rcvMessage.setAdapter(msgApdater);

        return view;
    }

    private List<MessageItem> getListData(){
        List<MessageItem> list = new ArrayList<>();
        list.add(new MessageItem("Đỗ Hữu Toàn", "OK, Xin chào lại"));
        list.add(new MessageItem("Test 1", "Hello"));
        list.add(new MessageItem("Test 2", "Tin nhắn nháp"));
        list.add(new MessageItem("Test 3", "Tin nhắn test"));
        list.add(new MessageItem("Test 4", "Gửi thử"));
        list.add(new MessageItem("Test 4", "Gửi thử"));
        list.add(new MessageItem("Test 4", "Gửi thử"));
        list.add(new MessageItem("Test 4", "Gửi thử"));
        list.add(new MessageItem("Test 4", "Gửi thử"));
        list.add(new MessageItem("Test 4", "Gửi thử"));
        return  list;
    }
}
