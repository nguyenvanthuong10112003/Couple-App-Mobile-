package com.example.myapplication.view.PageMain;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import com.example.myapplication.R;
import com.example.myapplication.define.DefineCoupleAttrRequest;
import com.example.myapplication.helper.DateHelper;
import com.example.myapplication.helper.RealPathUtil;
import com.example.myapplication.model.Couple;
import com.example.myapplication.model.FarewellRequest;
import com.example.myapplication.parcelable.UserParcelable;
import com.example.myapplication.view.BasePage.BasePageMainActivity;
import com.example.myapplication.view.PageChild.HomeDetailUserActivityPage;
import com.example.myapplication.view.PageChild.HomeFindLoveActivityPage;
import com.example.myapplication.view.PageChild.HomeUpdateInfoActivityPage;
import com.example.myapplication.viewmodel.HomeModels;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.imageview.ShapeableImageView;
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
    }
    @Override
    protected void setting() {
        super.setting();
        setSupportActionBar(toolBarMenu);
        Animation animationCoGiat = AnimationUtils.loadAnimation(this, R.anim.traitimcogiat);
        hinhAnhTraiTim.startAnimation(animationCoGiat);
        imageHinhNen.setOnClickListener(v -> ImagePicker.with(HomeActivityPage.this)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .start());
        ((HomeModels)baseModels).getLiveCouple().observe(this, couple -> {
            this.couple = couple;
            if (couple == null) {
                Intent intent = new Intent(HomeActivityPage.this, HomeFindLoveActivityPage.class);
                activityLaucher.launch(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                return;
            }
            setupCouple();
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getData() != null) {
            try {
                MediaType type = MediaType.parse("multipart/form-data");
                String realPath = RealPathUtil.getRealPath(this, data.getData());
                File file = new File(realPath);
                RequestBody requestBodyBackground = RequestBody.create(file, type);
                MultipartBody.Part multipartBodyAvt  =
                    MultipartBody.Part.createFormData(DefineCoupleAttrRequest.imageBg, file.getName(), requestBodyBackground);
                ((HomeModels) baseModels).changeBg(multipartBodyAvt);
            } catch (Exception ignored) {}
        }
    }

    @Override
    protected void onChangCurrentUser() {
        if (userLogin.getFullName() == null || userLogin.getFullName().isEmpty())
            toPageForResult(HomeUpdateInfoActivityPage.class);
        else {
            step = 1;
            startLoad();
        }
    }

    @Override
    protected void resume(Intent data) {
        baseModels.getUserLogin();
        try {
            if (data != null)
                ((HomeModels) baseModels).setCoupleLive(data.getParcelableExtra("new-couple"));
        } catch (Exception e) {}
    }

    public void startLoad() {
        switch (step) {
            case 1: ((HomeModels) baseModels).initCouple(); break;
            case 2:
                ((HomeModels) baseModels).getFarewellRequestLiveData()
                    .observe(this, farewellRequest -> {
                        this.farewellRequest = farewellRequest;
                    }); break;
            case 3:
                ((HomeModels) baseModels).phanHoiYeuCauChiaTay(false); break;
            case 4:
                ((HomeModels) baseModels).phanHoiYeuCauChiaTay(true); break;
            case 5:
                ((HomeModels) baseModels).yeuCauChiaTay(); break;
        }
    }

    private void alertChiaTay() {
        if (farewellRequest != null && farewellRequest.getSenderId() != userLogin.getId())
            alert.show("Đối phương yêu cầu chia tay, bạn có đồng ý chứ?",
                "Từ chối", "Đồng ý",
                    () -> {
                        step = 3;
                        startLoad();
                    }, () -> {
                        step = 4;
                        startLoad();
                    });
    }

    @Override
    protected void whenServerError() {
        String message = "Có lỗi xảy ra, vui lòng thử lại sau";
        switch (step) {
            case 1:
            case 2:
                alert.show(message, "Tải lại", this::startLoad);
                break;
            case 3: case 4: {
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
            case 2: {
                alertChiaTay();
            } break;
            case 4:
                ((HomeModels) baseModels).setCoupleLive(null); break;
            case 5:
                Toast.makeText(this, "Đã gửi yêu cầu chia tay đến đối phương, vui lòng chờ phản hồi",
                        Toast.LENGTH_SHORT).show();
                break;
        }
        step = -1;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_home, menu);
        return true;
    }
    private void setupCouple() {
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
        imageDoiPhuong.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HomeDetailUserActivityPage.class);
            intent.putExtra("data", new UserParcelable(couple.getEnemy()));
            startActivity(intent);
        });
        imageBanThan.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HomeDetailUserActivityPage.class);
            intent.putExtra("data", new UserParcelable(couple.getMind()));
            startActivity(intent);
        });
        textTenDoiPhuong.setText(couple.getEnemy().getFullName());
        textNgaySinhDoiPhuong.setText(DateHelper.toDateString(couple.getEnemy().getDob()));
        textTenBanThan.setText(couple.getMind().getFullName());
        textNgaySinhBanThan.setText(DateHelper.toDateString(couple.getMind().getDob()));
        iconGioiTinhDoiPhuong.setImageResource(couple.getEnemy().getGender() ? R.drawable.ic_male : R.drawable.ic_female);
        iconGioiTinhBanThan.setImageResource(couple.getMind().getGender() ? R.drawable.ic_male : R.drawable.ic_female);
        textNgayBatDau.setText(DateHelper.toDateString(couple.getTimeStart()));
        textNgayYeuNhau.setText(String.valueOf(DateHelper.demNgay(couple.getTimeStart())));
        LocalDateTime begin = DateHelper.toLocalDateTime(couple.getTimeStart());
        new Thread(() -> {
            while (couple != null) {
                runOnUiThread(() -> {
                    if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) return;
                    LocalDateTime now = LocalDateTime.now();
                    Duration duration = Duration.between(begin, now);

                    long years = duration.toDays() / 365;
                    long months = (duration.toDays() % 365) / 30;
                    long days = duration.toDays() % 365 % 30;
                    long hours = duration.toHours() % 24;
                    long minutes = duration.toMinutes() % 60;
                    long seconds = duration.getSeconds() % 60;

                    ((TextView) listTime.getChildAt(0)).setText(DateHelper.to2(years));
                    ((TextView) listTime.getChildAt(2)).setText(DateHelper.to2(months));
                    ((TextView) listTime.getChildAt(4)).setText(DateHelper.to2(days));
                    ((TextView) listTime.getChildAt(6)).setText(DateHelper.to2(hours));
                    ((TextView) listTime.getChildAt(8)).setText(DateHelper.to2(minutes));
                    ((TextView) listTime.getChildAt(10)).setText(DateHelper.to2(seconds));

                    long day = Integer.parseInt(textNgayYeuNhau.getText().toString());
                    long nextDay = DateHelper.demNgay(couple.getTimeStart());
                    if (day < nextDay)
                        textNgayYeuNhau.setText(String.valueOf(nextDay));
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        step = 2;
        startLoad();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.idTitleFinish) {
            alert.show("Bạn có chắc chắn muốn gửi yêu cầu chia tay chứ?", "Hủy bỏ", "Xác nhận",
                null, () -> {
                    step = 5;
                    startLoad();
                });
        }
        return super.onOptionsItemSelected(item);
    }
}