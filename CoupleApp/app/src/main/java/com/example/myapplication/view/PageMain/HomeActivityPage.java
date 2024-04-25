package com.example.myapplication.view.PageMain;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.R;
import com.example.myapplication.define.DefineCoupleAttrRequest;
import com.example.myapplication.define.DefineUserAttrRequest;
import com.example.myapplication.helper.DateHelper;
import com.example.myapplication.helper.RealPathUtil;
import com.example.myapplication.model.Couple;
import com.example.myapplication.model.FarewellRequest;
import com.example.myapplication.model.ResponseAPI;
import com.example.myapplication.model.UserLogin;
import com.example.myapplication.parcelable.UserParcelable;
import com.example.myapplication.view.Authentication.LoginActivity;
import com.example.myapplication.view.BasePage.BasePageMainActivity;
import com.example.myapplication.view.PageChild.HomeDetailUser;
import com.example.myapplication.view.PageChild.HomeUpdateInfoPage;
import com.example.myapplication.view.ViewPager.HomeViewPagerAdapter;
import com.example.myapplication.viewmodel.HomeModels;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class HomeActivityPage extends BasePageMainActivity {
    private Toolbar toolBarMenu;
    private View hinhAnhTraiTim;
    TabLayout tabLayout;
    HomeViewPagerAdapter viewPagerAdapter;
    ViewPager2 viewPager2;
    private ShapeableImageView imageDoiPhuong;
    private TextView textTenDoiPhuong;
    private ImageView iconGioiTinhDoiPhuong;
    private TextView textNgaySinhDoiPhuong;
    private ShapeableImageView imageBanThan;
    private TextView textTenBanThan;
    private ImageView iconGioiTinhBanThan;
    private TextView textNgaySinhBanThan;
    private ImageView imageHinhNen;
    private TextView textNgayBatDau;
    private TextView textNgayYeuNhau;
    private ViewGroup listTime;
    private EditText inputSearch;
    private boolean loaded = false;
    private FarewellRequest farewellRequest;
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
        baseModels = new ViewModelProvider(this).get(HomeModels.class);
        imageDoiPhuong = findViewById(R.id.idPageHomeLayoutCoupleImageDoiPhuong);
        textTenDoiPhuong = findViewById(R.id.idPageHomeLayoutCoupleTenDoiPhuong);
        iconGioiTinhDoiPhuong = findViewById(R.id.idPageHomeLayoutCoupleGioiTinhDoiPhuong);
        textNgaySinhDoiPhuong = findViewById(R.id.idPageHomeLayoutCoupleNgaySinhDoiPhuong);
        imageBanThan = findViewById(R.id.idPageHomeLayoutCoupleImageBanThan);
        textTenBanThan = findViewById(R.id.idPageHomeLayoutCoupleTenBanThan);
        iconGioiTinhBanThan = findViewById(R.id.idPageHomeLayoutCoupleGioiTinhBanThan);
        textNgaySinhBanThan = findViewById(R.id.idPageHomeLayoutCoupleNgaySinhBanThan);
        imageHinhNen = findViewById(R.id.idPageHomeHinhNen);
        textNgayBatDau = findViewById(R.id.idPageHomeLayoutCoupleNgayBatDau);
        textNgayYeuNhau = findViewById(R.id.idPageHomeLayoutCoupleSoNgayYeuNhau);
        listTime = findViewById(R.id.idPageHomeListTime);
        inputSearch = findViewById(R.id.idPageHomeInputSearch);
    }
    @Override
    protected void setting() {
        super.setting();
        setSupportActionBar(toolBarMenu);
        Animation animationCoGiat = AnimationUtils.loadAnimation(this, R.anim.traitimcogiat);
        hinhAnhTraiTim.startAnimation(animationCoGiat);
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
        imageHinhNen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(HomeActivityPage.this)
                    .crop()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .start();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getData() != null) {
            MediaType type = MediaType.parse("multipart/form-data");
            String realPath = RealPathUtil.getRealPath(this, data.getData());
            File file = new File(realPath);
            RequestBody requestBodyBackground = RequestBody.create(file, type);
            MultipartBody.Part multipartBodyAvt  =
                    MultipartBody.Part.createFormData(DefineCoupleAttrRequest.imageBg, file.getName(), requestBodyBackground);
            ((HomeModels) baseModels).changeBg(multipartBodyAvt);
        }
    }

    @Override
    protected void onChangCurrentUser() {
        if (userLogin.getFullName() == null || userLogin.getFullName().isEmpty()) {
            toPageForResult(HomeUpdateInfoPage.class);
        }
        else if (!loaded) {
            step = 1;
            startLoad();
        }
    }

    @Override
    protected void resert() {
        super.resert();
        baseModels.getUserLogin();
        startLoad();
    }

    public void startLoad() {
        loaded = true;
        switch (step) {
            case 1: ((HomeModels)baseModels).getLiveCouple().observe(this, couple -> {
                this.couple = couple;
                if (couple == null)
                    setupTab();
                else
                    setupCouple();
            }); break;
            case 2: ((HomeModels)baseModels).initLiveList(); break;
            case 3:
                ((HomeModels) baseModels).getFarewellRequestLiveData()
                    .observe(this, farewellRequest -> {
                        this.farewellRequest = farewellRequest;
                    }); break;
            case 4:
                ((HomeModels) baseModels).phanHoiYeuCauChiaTay(false); break;
            case 5:
                ((HomeModels) baseModels).phanHoiYeuCauChiaTay(true); break;
            case 6:
                ((HomeModels) baseModels).yeuCauChiaTay(); break;
        }
    }

    private void alertChiaTay() {
        if (farewellRequest != null && farewellRequest.getSenderId() != userLogin.getId())
            alert.show("Đối phương yêu cầu chia tay, bạn có đồng ý chứ?",
                "Từ chối", "Đồng ý",
                new Runnable() {
                    @Override
                    public void run() {
                        step = 4;
                        startLoad();
                    }
                }, new Runnable() {
                    @Override
                    public void run() {
                        step = 5;
                        startLoad();
                    }
                });
    }

    @Override
    protected void whenServerError() {
        String message = "Có lỗi xảy ra, vui lòng thử lại sau";
        switch (step) {
            case 1:
            case 2:
            case 3:
                alert.show(message, "Tải lại", new Runnable() {
                    @Override
                    public void run() {
                        startLoad();
                    }
                });
                break;
            case 4: case 5: {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                alertChiaTay();
            } break;
            default:
                alert.show(message);
        }
    }
    @Override
    protected void whenSuccess() {
        switch (step) {
            case 3: {
                alertChiaTay();
            } break;
            case 5:
                ((HomeModels) baseModels).setCoupleLive(null); break;
            case 6:
                Toast.makeText(this, "Đã gửi yêu cầu chia tay đến đối phương, vui lòng chờ phản hồi",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_home, menu);
        return true;
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
        step = 2;
        startLoad();
    }
    private void setupCouple() {
        findViewById(R.id.idPageHomeLayoutCouple).setVisibility(View.VISIBLE);
        findViewById(R.id.idPageHomeLayoutGhepCap).setVisibility(View.INVISIBLE);
        try {
            if (couple.getPhotoUrl() != null && !couple.getPhotoUrl().isEmpty())
                Picasso.get().load(couple.getPhotoUrl()).into(imageHinhNen);
        } catch (Exception e) {
            imageHinhNen.setBackgroundResource(R.drawable.hinh_nen_tinh_yeu_taihinhanh_vn__7_);
        }
        try {
            if (couple.getEnemy().getUrlAvatar() != null && !couple.getEnemy().getUrlAvatar().isEmpty())
                Picasso.get().load(couple.getEnemy().getUrlAvatar()).into(imageDoiPhuong);
        } catch (Exception e) {
            imageDoiPhuong.setBackgroundResource(R.drawable.account_svgrepo_com);
        }
        try {
            if (couple.getMind().getUrlAvatar() != null && !couple.getMind().getUrlAvatar().isEmpty())
                Picasso.get().load(couple.getMind().getUrlAvatar()).into(imageBanThan);
        } catch (Exception e) {
            imageBanThan.setBackgroundResource(R.drawable.account_svgrepo_com);
        }
        imageDoiPhuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeDetailUser.class);
                intent.putExtra("data", new UserParcelable(couple.getEnemy()));
                startActivity(intent);
            }
        });
        imageBanThan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeDetailUser.class);
                intent.putExtra("data", new UserParcelable(couple.getMind()));
                startActivity(intent);
            }
        });
        textTenDoiPhuong.setText(couple.getEnemy().getFullName());
        textNgaySinhDoiPhuong.setText(DateHelper.toDateString(couple.getEnemy().getDob()));
        textTenBanThan.setText(couple.getMind().getFullName());
        textNgaySinhBanThan.setText(DateHelper.toDateString(couple.getMind().getDob()));
        iconGioiTinhDoiPhuong.setImageResource(couple.getEnemy().getGender() ? R.drawable.ic_male : R.drawable.ic_female);
        iconGioiTinhBanThan.setImageResource(couple.getMind().getGender() ? R.drawable.ic_male : R.drawable.ic_female);
        textNgayBatDau.setText(DateHelper.toDateString(couple.getTimeStart()));
        textNgayYeuNhau.setText(String.valueOf(DateHelper.demNgay(couple.getTimeStart())));
        findViewById(R.id.idPageHomeLayoutCouple).setVisibility(View.VISIBLE);
        LocalDateTime begin = DateHelper.toLocalDateTime(couple.getTimeStart());
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O)
                                return;
                            LocalDateTime now = LocalDateTime.now();
                            Duration duration = Duration.between(begin, now);

                            long years = duration.toDays() / 365;
                            long months = (duration.toDays() % 365) / 30;
                            long days = duration.toDays() % 365 % 30;
                            long hours = duration.toHours() % 24;
                            long minutes = duration.toMinutes() % 60;
                            long seconds = duration.getSeconds() % 60;

                            ((TextView)listTime.getChildAt(0)).setText(DateHelper.to2(years));
                            ((TextView)listTime.getChildAt(2)).setText(DateHelper.to2(months));
                            ((TextView)listTime.getChildAt(4)).setText(DateHelper.to2(days));
                            ((TextView)listTime.getChildAt(6)).setText(DateHelper.to2(hours));
                            ((TextView)listTime.getChildAt(8)).setText(DateHelper.to2(minutes));
                            ((TextView)listTime.getChildAt(10)).setText(DateHelper.to2(seconds));
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    };
                }
            }
        }).start();
        step = 3;
        startLoad();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.idTitleFinish) {
            alert.show("Bạn có chắc chắn muốn chia tay chứ?", "Hủy bỏ", "Xác nhận",
                null, new Runnable() {
                    @Override
                    public void run() {
                        step = 6;
                        startLoad();
                    }
                });
        }
        return super.onOptionsItemSelected(item);
    }
}