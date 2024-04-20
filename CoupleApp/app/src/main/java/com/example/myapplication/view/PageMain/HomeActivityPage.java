package com.example.myapplication.view.PageMain;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.R;
import com.example.myapplication.model.Couple;
import com.example.myapplication.model.Response;
import com.example.myapplication.model.User;
import com.example.myapplication.model.UserLogin;
import com.example.myapplication.service.api_service.ApiService;
import com.example.myapplication.service.api_service.CoupleApiService;
import com.example.myapplication.service.api_service.UserApiService;
import com.example.myapplication.view.BasePage.BasePageMainActivity;
import com.example.myapplication.view.PageChild.HomeUpdateInfoPage;
import com.example.myapplication.view.ViewPager.HomeViewPagerAdapter;
import com.example.myapplication.viewmodel.HomeModels;
import com.google.android.material.tabs.TabLayout;

import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Callback;

public class HomeActivityPage extends BasePageMainActivity {
    private Toolbar toolBarMenu;
    private View hinhAnhTraiTim;
    TabLayout tabLayout;
    HomeViewPagerAdapter viewPagerAdapter;
    ViewPager2 viewPager2;
    private HomeModels homeModel;
    private Couple couple;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        idBtnPage = R.id.idBtnPageHome;
        init();
    }
    @Override
    protected void getData() {
        super.getData();
        toolBarMenu = findViewById(R.id.idPageHomeToolBarMenu);
        hinhAnhTraiTim = findViewById(R.id.idPageHomeImageHinhTraiTim);
        tabLayout = findViewById(R.id.idPageHomeTabLayout);
        viewPager2 = findViewById(R.id.idPageHomeViewPager2);
        viewPagerAdapter = new HomeViewPagerAdapter(this);
        homeModel = new ViewModelProvider(this).get(HomeModels.class);
    }

    @Override
    protected void setting() {
        super.setting();
        homeModel.setUserLogin(userLogin);
        if (userLogin.getFullName() == null) {
            toPage(HomeUpdateInfoPage.class);
            return;
        }
        setSupportActionBar(toolBarMenu);
        Animation animationCoGiat = AnimationUtils.loadAnimation(this, R.anim.traitimcogiat);
        hinhAnhTraiTim.startAnimation(animationCoGiat);
        getCouple();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.idTitleFinish) {

        }
        return super.onOptionsItemSelected(item);
    }
    private void setupCouple() {

    }
    private void setupTab() {
        findViewById(R.id.idPageHomeLayoutCouple).setVisibility(View.INVISIBLE);
        findViewById(R.id.idPageHomeLayoutGhepCap).setVisibility(View.VISIBLE);
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
        getAllPersonWithDateInvitation();
    }
    private void getCouple() {
        if (token == null || token.isEmpty())
            return;
        CoupleApiService coupleApiService = ApiService
            .createApiServiceWithAuth(this, CoupleApiService.class, token);
        startLoading();
        coupleApiService.get()
            .enqueue(new Callback<Response<Couple>>() {
                @Override
                public void onResponse(Call<Response<Couple>> call, retrofit2.Response<Response<Couple>> response) {
                    stopLoading();
                    if (!response.isSuccessful())
                        alert.show("Get an error" + response.message());
                    else if (response.body().getStatus() == Response.NEED_LOGIN)
                        logout();
                    else if (response.body().getStatus() == Response.ERROR)
                        alert.show(response.body().getMessage());
                    else {
                        couple = response.body().getData();
                        if (couple == null)
                            setupTab();
                        else
                            setupCouple();
                    }
                }
                @Override
                public void onFailure(Call<Response<Couple>> call, Throwable throwable) {
                    stopLoading();
                    alert.show("Có lỗi xảy ra, vui lòng thử lại sau.", "Thử lại", new Runnable() {
                        @Override
                        public void run() {
                            getCouple();
                        }
                    });
                }
            });
    }
    private void getAllPersonWithDateInvitation() {
        if (token == null || token.isEmpty())
            return;
        UserApiService userApiService = ApiService
            .createApiServiceWithAuth(this, UserApiService.class, token);
        startLoading();
        userApiService.getAll().enqueue(
            new Callback<Response<LinkedList<User>>>() {
                @Override
                public void onResponse(Call<Response<LinkedList<User>>> call, retrofit2.Response<Response<LinkedList<User>>> response) {
                    stopLoading();
                    if (!response.isSuccessful())
                        alert.show("Get an error" + response.message());
                    else if (response.body().getStatus() == Response.NEED_LOGIN)
                        logout();
                    else if (response.body().getStatus() == Response.ERROR)
                        alert.show(response.body().getMessage());
                    else
                        homeModel.setList(response.body().getData());
                }
                @Override
                public void onFailure(Call<Response<LinkedList<User>>> call, Throwable throwable) {
                    stopLoading();
                    alert.show("Có lỗi xảy ra, vui lòng thử lại sau.", "Thử lại", new Runnable() {
                        @Override
                        public void run() {
                            getAllPersonWithDateInvitation();
                        }
                    });
                }
            }
        );
    }
}