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

import com.example.vnutalkapp.R;

import java.util.UUID;

public class VideoCallActivity extends AppCompatActivity {
    boolean isPeerConnected = false;
    WebView mWebView;
    Button btnCallVideo;
    EditText edtUrlVideoCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call);

        mWebView = findViewById(R.id.wv_videoCall);
        btnCallVideo = findViewById(R.id.btn_callVideo);
        edtUrlVideoCall = findViewById(R.id.edt_urlCallVideo);

        setupWebView();
        btnCallVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBtnCallVideoClick();
            }
        });
    }

    private void handleBtnCallVideoClick() {
        callJavascriptFunction("javascript:startCall(\"" + edtUrlVideoCall.getText().toString() + "\")");
    }

    private void loadVideoCall() {
        String filePath = "file:android_asset/call.html";
        mWebView.loadUrl(filePath);
        mWebView.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String url) {
                initializePeer();
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
        String uniqueId = getUniqueID();
        callJavascriptFunction("javascript:init(\""+uniqueId+"\")");
        edtUrlVideoCall.setText(uniqueId);
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

