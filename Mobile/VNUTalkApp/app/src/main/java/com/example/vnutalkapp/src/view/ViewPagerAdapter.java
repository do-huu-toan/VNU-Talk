package com.example.vnutalkapp.src.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.vnutalkapp.src.view.fragment.MessageFragment;
import com.example.vnutalkapp.src.view.fragment.PhoneBookFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new MessageFragment();
            case 1:
                return new PhoneBookFragment();
            default:
                return new MessageFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
