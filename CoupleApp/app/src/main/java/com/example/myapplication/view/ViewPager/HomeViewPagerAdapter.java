package com.example.myapplication.view.ViewPager;

import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.myapplication.model.User;
import com.example.myapplication.model.UserLogin;
import com.example.myapplication.parcelable.UserLoginParcelable;
import com.example.myapplication.parcelable.UserParcelable;
import com.example.myapplication.view.Fragment.HomeAllFragment;
import com.example.myapplication.view.Fragment.HomeMindFragment;
import com.example.myapplication.view.Fragment.HomeSentFragment;

import java.util.ArrayList;
import java.util.LinkedList;
public class HomeViewPagerAdapter extends FragmentStateAdapter {
    public HomeViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    public HomeViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public HomeViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new HomeSentFragment();
            case 2:
                return new HomeMindFragment();
        }
        return new HomeAllFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}