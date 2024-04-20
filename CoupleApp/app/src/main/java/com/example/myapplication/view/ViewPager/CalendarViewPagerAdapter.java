package com.example.myapplication.view.ViewPager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.myapplication.view.Fragment.CalendarTodayFragment;
import com.example.myapplication.view.Fragment.CalendarAllFragment;
import com.example.myapplication.view.Fragment.CalendarComingUpFragment;

public class CalendarViewPagerAdapter extends FragmentStateAdapter {
    public CalendarViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public CalendarViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public CalendarViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new CalendarTodayFragment();
            case 1:
                return new CalendarComingUpFragment();
            case 2:
                return new CalendarAllFragment();
        }
        return new CalendarTodayFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}