package com.example.vnutalkapp.src.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vnutalkapp.R;
import com.example.vnutalkapp.src.model.MessageSend;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.UUID;

import io.socket.client.IO;
import io.socket.client.Socket;

public class VideoCallActivity extends AppCompatActivity {
    boolean isPeerConnected = false;
    WebView mWebView;
    Button btnCallVideo;
    private Socket mSocket;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call);
        bundle = getIntent().getExtras();
        mWebView = findViewById(R.id.wv_videoCall);
        btnCallVideo = findViewById(R.id.btn_callVideo);

        initSocketIO();
        mSocket.connect();
        setupWebView();


        btnCallVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageSend messageSend = new MessageSend("", bundle.getString("receiverId"));
                Gson gson = new Gson();
                try{
                    Toast.makeText(VideoCallActivity.this, "Trả lời", Toast.LENGTH_SHORT).show();
                    JSONObject obj = new JSONObject(gson.toJson(messageSend));
                    mSocket.emit("answercall", obj);
                }
                catch (Exception e){}
                //handleBtnCallVideoClick(bundle.getString("receiverId"));
            }
        });


    }

    private void initSocketIO(){
        try {
            Log.i("Bundle", String.valueOf(getIntent().getExtras()));
            mSocket = IO.socket("http://192.168.0.100:3000?userId=" + bundle.getString("userId"));
        }
        catch (Exception e){
            Log.e("Error SocketIO", e.getMessage());
        }

    }

    private void handleBtnCallVideoClick(String receiverId) {
        callJavascriptFunction("javascript:startCall(\"" + receiverId + "\")");
    }

    private void loadVideoCall() {
        String filePath = "file:android_asset/call.html";
        mWebView.loadUrl(filePath);
        mWebView.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String url) {
                initializePeer();
                if(bundle.getString("caller").equals("1")){
                    Toast.makeText(VideoCallActivity.this, "Bắt đầu", Toast.LENGTH_SHORT).show();
                    handleBtnCallVideoClick(bundle.getString("receiverId"));
                }
                /*
                else if(bundle.getString("caller").equals("0")){
                    MessageSend messageSend = new MessageSend("", bundle.getString("receiverId"));
                    Gson gson = new Gson();
                    try{
                        Toast.makeText(VideoCallActivity.this, "Trả lời", Toast.LENGTH_SHORT).show();
                        JSONObject obj = new JSONObject(gson.toJson(messageSend));
                        mSocket.emit("answercall", obj);
                    }
                    catch (Exception e){}
                }

                 */
            }
        });
    }



    public void setupWebView() {
        mWebView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onPermissionRequest(PermissionRequest request) {
                request.grant(request.getResources());
            }
        });
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        mWebView.addJavascriptInterface(new myJavascipt(VideoCallActivity.this), "Android");

        loadVideoCall();
    }

    public void callJavascriptFunction(String fuctionString){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            mWebView.evaluateJavascript(fuctionString, null);
        } else {
            mWebView.loadUrl(fuctionString);
        }
    }
    private void initializePeer() {
        callJavascriptFunction("javascript:init(\""+ bundle.getString("userId")+"\")");
    }
    private String getUniqueID() {
        return UUID.randomUUID().toString();
    }

}

class myJavascipt {
    VideoCallActivity a;
    public myJavascipt(VideoCallActivity videoCallActivity) {
        a = videoCallActivity;
    }

    @JavascriptInterface
    public void onPeerConnected(VideoCallActivity a) {
        a.isPeerConnected = true;
    }
}

