package com.example.vnutalkapp.src.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vnutalkapp.R;
import com.example.vnutalkapp.src.api.ApiService;
import com.example.vnutalkapp.src.model.MessageApi;
import com.example.vnutalkapp.src.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);

        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginHandle();
            }
        });
    }

    private void loginHandle() {

        // Call API Login:
        try {
            String username = edtUsername.toString();
            String password = edtPassword.toString();

            if(!(username.matches("") && password.matches(""))){
                ApiService.apiService.login(new User(edtUsername.getText().toString(), edtPassword.getText().toString())).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User result = response.body();
                        if(result != null){
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            // Tạo bundle:
                            Bundle bundle = new Bundle();
                            bundle.putString("userId", result.getId());
                            bundle.putString("fullName", result.getFullname());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu không chính xác !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Xảy ra lỗi trong quá trình xử lý !", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        catch (Exception e)
        {
            Toast.makeText(LoginActivity.this, "Call Api Error", Toast.LENGTH_SHORT).show();
        }

        /*
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

         */
    }
}