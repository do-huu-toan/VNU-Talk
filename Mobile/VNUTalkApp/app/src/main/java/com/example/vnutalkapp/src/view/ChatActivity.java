package com.example.vnutalkapp.src.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.vnutalkapp.R;
import com.example.vnutalkapp.src.apdater.ChatApdater;
import com.example.vnutalkapp.src.api.ApiService;
import com.example.vnutalkapp.src.model.Chat;
import com.example.vnutalkapp.src.model.MessageItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    private EditText edtMessage;
    private Button btnSend;
    private RecyclerView rcvChat;
    private ChatApdater chatApdater;
    private List<Chat> mListChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        edtMessage = findViewById(R.id.edt_message);
        btnSend = findViewById(R.id.btn_send);
        rcvChat = findViewById(R.id.rcv_chat);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvChat.setLayoutManager(linearLayoutManager);
        mListChat = getListData();
        chatApdater = new ChatApdater();
        chatApdater.setData(mListChat, "Toàn");

        rcvChat.setAdapter(chatApdater);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendChat();
            }
        });
    }
    private void sendChat(){
        String strMessage = edtMessage.getText().toString().trim();
        if(TextUtils.isEmpty(strMessage)){
            return;
        }
        mListChat.add(new Chat(strMessage,"Toàn", "Trang"));
        chatApdater.notifyDataSetChanged();
        rcvChat.scrollToPosition(mListChat.size() - 1);

        edtMessage.setText("");
    }
    private List<Chat> getListData(){
        List<Chat> list = new ArrayList<>();
        /*
        list.add(new Chat("Xin chào", "Trang", "Toàn"));
        list.add(new Chat("OK, Xin chào lại", "Toàn", "Trang"));
        list.add(new Chat("Xin chào", "Trang", "Toàn"));
        list.add(new Chat("Xin chào", "Trang", "Toàn"));
        list.add(new Chat("OK, Xin chào lại", "Toàn", "Trang"));
        */
        // Call API tìm các đoạn chat
        String seederId = getIntent().getExtras().getString("userId");
        String receiverId = getIntent().getExtras().getString("receiverId");
        ApiService.apiService.getListMessage(seederId, receiverId).enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {

            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {

            }
        });
        return  list;
    }
}