package com.example.vnutalkapp.src.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.vnutalkapp.R;
import com.example.vnutalkapp.src.apdater.MessageApdater;
import com.example.vnutalkapp.src.apdater.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private TextView tv_userName;
    private ViewPager2 mViewPager;
    private Bundle bundle;
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
        bundle = getIntent().getExtras();
        tv_userName = findViewById(R.id.tv_user_name);
        tv_userName.setText(bundle.getString("fullName"));
        // View Pager:
        mViewPager = findViewById(R.id.view_pager);
        setUpViewPager();

    }

    private void setUpViewPager(){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), bundle);
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

