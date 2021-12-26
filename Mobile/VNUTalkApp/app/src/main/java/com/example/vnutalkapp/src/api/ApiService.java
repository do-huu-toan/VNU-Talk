package com.example.vnutalkapp.src.api;

import com.example.vnutalkapp.src.model.Chat;
import com.example.vnutalkapp.src.model.MessageApi;
import com.example.vnutalkapp.src.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/api/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("user")
    Call<List<User>> getAllUser();
    @POST("account/login")
    Call<User> login(@Body User user);
    @GET("message?")
    Call<List<Chat>> getListMessage(@Query("seederId") String seederId, @Query("seederId") String receiverId);
}
