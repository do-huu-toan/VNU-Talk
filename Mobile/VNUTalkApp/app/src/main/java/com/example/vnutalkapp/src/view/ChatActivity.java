package com.example.vnutalkapp.src.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vnutalkapp.R;
import com.example.vnutalkapp.src.apdater.ChatApdater;
import com.example.vnutalkapp.src.api.ApiService;
import com.example.vnutalkapp.src.model.Chat;
import com.example.vnutalkapp.src.model.MessageItem;
import com.example.vnutalkapp.src.model.MessageSend;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class ChatActivity extends AppCompatActivity {
    private Boolean call = false;
    private Socket mSocket;
    private EditText edtMessage;
    private Button btnSend;
    private RecyclerView rcvChat;
    private ChatApdater chatApdater;
    private List<Chat> mListChat;
    private Bundle bundle;
    private Button btnCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //
        setTitle(getIntent().getExtras().getString("receiverUsername"));
        // SocketIO Init:
        initSocketIO();
        mSocket.connect();
        mSocket.on("message", onNewMessage);
        mSocket.on("isCall", onNewCall);
        mSocket.on("answercall", onAnswerCall);
        //
        bundle = getIntent().getExtras();
        edtMessage = findViewById(R.id.edt_message);
        btnSend = findViewById(R.id.btn_send);
        btnCall = findViewById(R.id.btn_call);
        rcvChat = findViewById(R.id.rcv_chat);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvChat.setLayoutManager(linearLayoutManager);
        mListChat = getListData();
        chatApdater = new ChatApdater();
        chatApdater.setData(mListChat, bundle.getString("userId"));

        rcvChat.setAdapter(chatApdater);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendChat();
            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCallClick();
            }
        });
    }

    private void handleCallClick(){
        call = false;
        MessageSend messageSend = new MessageSend("", bundle.getString("receiverId"));
        Gson gson = new Gson();
        try {
            JSONObject obj = new JSONObject(gson.toJson(messageSend));
            mSocket.emit("call", obj);
        }
        catch (Exception e){

        }
    }
    private void callActivity(String status){
        String userId = bundle.getString("userId");
        String receiverId = bundle.getString("receiverId");
        Intent intent = new Intent(ChatActivity.this, VideoCallActivity.class);
        // T???o bundle:
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        bundle.putString("receiverId", receiverId);
        bundle.putString("caller", status);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private void sendChat() {
        String strMessage = edtMessage.getText().toString().trim();
        if(TextUtils.isEmpty(strMessage)){
            return;
        }
        mListChat.add(new Chat(strMessage,bundle.getString("userId"), bundle.getString("receiverId")));
        chatApdater.notifyDataSetChanged();
        rcvChat.scrollToPosition(mListChat.size() - 1);
        edtMessage.setText("");
        // Call API POST Message:
        ApiService.apiService.createMesssage(new Chat(strMessage,bundle.getString("userId"), bundle.getString("receiverId") )).enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> call, Response<Chat> response) {
                Log.i("Send Message", response.body().getMessage());
                Toast.makeText(ChatActivity.this, "G???i tin nh???n th??nh c??ng", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
                Toast.makeText(ChatActivity.this, "G???i tin nh???n l???i", Toast.LENGTH_SHORT).show();
            }
        });
        // SOCKET IO Message
        MessageSend messageSend = new MessageSend(strMessage, bundle.getString("receiverId"));
        Gson gson = new Gson();
        try{
            JSONObject obj = new JSONObject(gson.toJson(messageSend));
            mSocket.emit("sendMessage", obj);
        }
        catch (Exception e){}
    }
    private List<Chat> getListData(){
        List<Chat> list = new ArrayList<>();
        /*
        list.add(new Chat("Xin ch??o", "Trang", "To??n"));
        list.add(new Chat("OK, Xin ch??o l???i", "To??n", "Trang"));
        list.add(new Chat("Xin ch??o", "Trang", "To??n"));
        list.add(new Chat("Xin ch??o", "Trang", "To??n"));
        list.add(new Chat("OK, Xin ch??o l???i", "To??n", "Trang"));
        */
        // Call API t??m c??c ??o???n chat
        String seederId = getIntent().getExtras().getString("userId");
        String receiverId = getIntent().getExtras().getString("receiverId");
        Log.i("Call API", "Run here 1");
        try{
            ApiService.apiService.getListMessage(seederId, receiverId).enqueue(new Callback<List<Chat>>() {
                @Override
                public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
                    Log.i("Call API", String.valueOf(response.body()));
                    if(response.body() != null){
                        for(int i = 0; i < response.body().size(); i++)
                        {
                            Chat message = response.body().get(i);
                            list.add(new Chat(message.getMessage(),message.getSender() , message.getReceiver()));
                            mListChat = list;
                            chatApdater.notifyDataSetChanged();
                        }
                    }
                    else{
                        Log.i("Call API ERROR", "NULL");
                    }
                }

                @Override
                public void onFailure(Call<List<Chat>> call, Throwable t) {

                }
            });
        }
        catch (Exception e){
            Log.e("Error Call API Message", e.getMessage());
        }
        return  list;
    }
    private void initSocketIO(){
        try {
            Log.i("Bundle", String.valueOf(getIntent().getExtras()));
            mSocket = IO.socket("http://192.168.0.100:3000?userId=" + getIntent().getExtras().getString("userId"));
            }
            catch (Exception e){
                Log.e("Error SocketIO", e.getMessage());
            }

    }
    /* Listener socket on "messange" */
    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject)args[0];
                    String message = data.optString("message");
                    String seederId = data.optString("seederId");
                    if(seederId.equals(bundle.getString("receiverId"))) {
                        mListChat.add(new Chat(message, seederId, getIntent().getExtras().getString("userId")));
                        chatApdater.notifyDataSetChanged();
                        rcvChat.scrollToPosition(mListChat.size() - 1);
                    }
                }
            });
        }
    };

    private Emitter.Listener onNewCall = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject)args[0];
                    String seederId = data.optString("seederId");
                    Toast.makeText(ChatActivity.this, "C?? cu???c g???i ?????n", Toast.LENGTH_SHORT).show();
                    callActivity("0");
                }
            });
        }
    };

    private Emitter.Listener onAnswerCall = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject)args[0];
                    String seederId = data.optString("seederId");
                    Toast.makeText(ChatActivity.this, "Ch???p nh???n cu???c g???i", Toast.LENGTH_SHORT).show();
                    if(seederId.equals(bundle.getString("receiverId"))){
                        callActivity("1");
                    }
                }
            });
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        mSocket.disconnect();
        finish();
    }
}