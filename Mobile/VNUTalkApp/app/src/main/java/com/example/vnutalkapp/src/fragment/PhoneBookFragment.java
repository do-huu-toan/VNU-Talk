package com.example.vnutalkapp.src.fragment;

import android.os.Bundle;
import android.util.Log;
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
import com.example.vnutalkapp.src.apdater.PhoneBookApdater;
import com.example.vnutalkapp.src.api.ApiService;
import com.example.vnutalkapp.src.model.MessageItem;
import com.example.vnutalkapp.src.model.PhoneBookItem;
import com.example.vnutalkapp.src.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneBookFragment extends Fragment {
    private RecyclerView rcvPhoneBook;
    private PhoneBookApdater phoneBookApdater;
    private List<PhoneBookItem> listPhoneBook = new ArrayList<PhoneBookItem>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phone_book, container, false);
        rcvPhoneBook = view.findViewById(R.id.rcv_phonebook);
        phoneBookApdater = new PhoneBookApdater(inflater.getContext(), listPhoneBook, getArguments());
        getListData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(inflater.getContext());
        rcvPhoneBook.setLayoutManager(linearLayoutManager);
        rcvPhoneBook.setAdapter(phoneBookApdater);
        return view;
    }
    public void getListData() {
        List<PhoneBookItem> list = new ArrayList<>();
        ApiService.apiService.getAllUser().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.body() != null){
                    for (int i = 0; i < response.body().size(); i++) {
                        listPhoneBook.add(new PhoneBookItem(response.body().get(i).getId(),response.body().get(i).getFullname(),false));
                        phoneBookApdater.setData(listPhoneBook);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }
}
