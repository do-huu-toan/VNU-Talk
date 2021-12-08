package com.example.vnutalkapp.src.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.vnutalkapp.R;
import com.example.vnutalkapp.src.apdater.MessageApdater;
import com.example.vnutalkapp.src.model.MessageItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcvMessage;
    private MessageApdater msgApdater;
    private ViewPager2 mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Bottom navigation:
        BottomNavigationView navigationView = findViewById(R.id.bottom_nav);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return handleBottomNavSelected(item);
            }
        });
        // View Pager:
        mViewPager = findViewById(R.id.view_pager);
        setUpViewPager();

    }
    
    private void setUpViewPager(){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        mViewPager.setAdapter(viewPagerAdapter);
    }
    private boolean handleBottomNavSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_messenger:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.action_book:
                mViewPager.setCurrentItem(1);
                break;
        }
        return true;
    }
}

