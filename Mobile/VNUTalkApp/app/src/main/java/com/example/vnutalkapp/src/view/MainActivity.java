package com.example.vnutalkapp.src.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.vnutalkapp.R;
import com.example.vnutalkapp.src.apdater.MessageApdater;
import com.example.vnutalkapp.src.model.MessageItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcvMessage;
    private MessageApdater msgApdater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcvMessage = findViewById(R.id.rcv_listMessage);
        msgApdater = new MessageApdater();
        msgApdater.setData(getListData());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvMessage.setLayoutManager(linearLayoutManager);
        rcvMessage.setAdapter(msgApdater);

        Log.e("DHT", String.valueOf(getListData().size()));
    }

    private List<MessageItem> getListData(){
        List<MessageItem> list = new ArrayList<>();
        list.add(new MessageItem("Đỗ Hữu Toàn", "Hello"));
        list.add(new MessageItem("Test 1", "Hello"));
        list.add(new MessageItem("Test 2", "Tin nhắn nháp"));
        list.add(new MessageItem("Test 3", "Tin nhắn test"));
        list.add(new MessageItem("Test 4", "Gửi thử"));
        return  list;
    }
}

