package com.example.vnutalkapp.src.apdater;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.vnutalkapp.src.fragment.MessageFragment;
import com.example.vnutalkapp.src.fragment.PhoneBookFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public Bundle bundle;
    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, Bundle bundleParent) {
        super(fragmentManager, lifecycle);
        bundle = bundleParent;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new MessageFragment();
            case 1:
                PhoneBookFragment phoneBookFragment = new PhoneBookFragment();
                phoneBookFragment.setArguments(bundle);
                return phoneBookFragment;
            default:
                return new MessageFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
