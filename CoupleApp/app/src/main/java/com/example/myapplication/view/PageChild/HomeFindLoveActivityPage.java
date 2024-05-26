package com.example.myapplication.view.PageChild;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import com.example.myapplication.R;
import com.example.myapplication.parcelable.CoupleParcelable;
import com.example.myapplication.view.BasePage.BasePageMainActivity;
import com.example.myapplication.view.ViewPager.HomeViewPagerAdapter;
import com.example.myapplication.viewmodel.HomeModels;
import com.google.android.material.tabs.TabLayout;

public class HomeFindLoveActivityPage extends BasePageMainActivity {
    private TabLayout tabLayout;
    ViewPager2 viewPager2;
    HomeViewPagerAdapter viewPagerAdapter;
    EditText inputSearch;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_findlove);
        init();
    }

    @Override
    protected void getData() {
        super.getData();
        tabLayout = findViewById(R.id.idPageHomeTabLayout);
        viewPager2 = findViewById(R.id.idPageHomeViewPager2);
        viewPagerAdapter = new HomeViewPagerAdapter(this);
        baseModels = new ViewModelProvider(this).get(HomeModels.class);
        inputSearch = findViewById(R.id.idPageHomeInputSearch);
    }

    @Override
    protected void setting() {
        super.setting();
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ((HomeModels) baseModels).setNameSearch(inputSearch.getText().toString());
            }
        });
        viewPager2.setAdapter(viewPagerAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
            super.onPageSelected(position);
            tabLayout.getTabAt(position).select();
            }
        });
        ((HomeModels) baseModels).getLiveCouple().observe(this, couple -> {
            if (couple != null)
            {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("new-couple", new CoupleParcelable(couple));
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
        step = 1;
        startLoad();
    }

    @Override
    protected void startLoad() {
        switch (step) {
            case 1: ((HomeModels)baseModels).initLiveList(); break;
        }
    }

    @Override
    protected void whenSuccess() {
        switch (step) {
        }
        step = -1;
    }

    @Override
    protected void whenServerError() {
        String message = "Có lỗi xảy ra, vui lòng thử lại sau";
        switch (step) {
            case 1:
                alert.show(message, "Tải lại", this::startLoad); break;
        }
    }
}
