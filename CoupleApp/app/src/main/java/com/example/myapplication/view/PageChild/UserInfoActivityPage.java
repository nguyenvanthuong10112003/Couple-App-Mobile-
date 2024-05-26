package com.example.myapplication.view.PageChild;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.parcelable.UserParcelable;
import com.example.myapplication.view.Component.Button;
import com.example.myapplication.view.Component.ImageViewFull;
import com.example.myapplication.view.Component.InputDate;
import com.example.myapplication.define.DefineSharedPreferencesUserAuthen;
import com.example.myapplication.define.DefineUserAttrRequest;
import com.example.myapplication.helper.DateHelper;
import com.example.myapplication.helper.RealPathUtil;
import com.example.myapplication.helper.StringHelper;
import com.example.myapplication.model.User;
import com.example.myapplication.view.BasePage.BasePageAuthActivity;
import com.example.myapplication.viewmodel.UserModels;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UserInfoActivityPage extends BasePageAuthActivity {
    private ShapeableImageView viewAvatar;
    private TextView textFullname;
    private TextView textAlias;
    private TextView textEmail;
    private TextView textDob;
    private TextView textLifeStory;
    private TextView textGender;
    private ImageViewFull layoutZoom;
    private TextView textTimeCreate;
    private User currentUser;
    private Button btnEdit;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infouser);
        init();
    }
    @Override
    protected void getData() {
        super.getData();
        textFullname = findViewById(R.id.idPageInfoUserTabViewTextFullname);
        textAlias = findViewById(R.id.idPageInfoUserTabViewTextAlias);
        textDob = findViewById(R.id.idPageInfoUserTabViewTextDob);
        textLifeStory = findViewById(R.id.idPageInfoUserTabViewTextLifeStory);
        textGender = findViewById(R.id.idPageInfoUserTabViewTextGender);
        textEmail = findViewById(R.id.idPageInfoUserTabViewTextEmail);
        viewAvatar = findViewById(R.id.idPageInfoUserTabViewImageAvatar);
        textTimeCreate = findViewById(R.id.idPageInfoUserTabViewTextTimeCreate);
        btnEdit = findViewById(R.id.idPageInfoUserTabViewBtnEdit);
        baseModels = new ViewModelProvider(this).get(UserModels.class);
    }
    @Override
    protected void setting() {
        super.setting();
        findViewById(R.id.header_backPage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedWithResult();
            }
        });
        ((UserModels) baseModels).getUser()
            .observe(this, user -> {
                if (user == null)
                    logout();
                else {
                    currentUser = user;
                    setupContentView();
                }
            });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivityPage.this, UserUpdateActivityPage.class);
                intent.putExtra("info-mind", new UserParcelable(currentUser));
                activityLaucher.launch(intent);
            }
        });
        step = 1;
        startLoad();
    }

    @Override
    protected void resume(Intent data) {
        try {
            currentUser = data.getParcelableExtra("updated");
            setupContentView();
        } catch (Exception e) {}
    }

    @Override
    protected void startLoad() {
        switch (step) {
            case 1: ((UserModels) baseModels).initUser(); break;
        }
    }
    @Override
    protected void whenServerError() {
        String message = "Có lỗi xảy ra, vui lòng thử lại sau";
        switch (step) {
            case 1: alert.show(message, "Tải lại", "Trở về",
                new Runnable() {
                    @Override
                    public void run() {
                        startLoad();
                    }},
                new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                    }
                });
            break;
            default: alert.show(message);
        }
    }

    private void setupContentView() {
        textFullname.setText(currentUser.getFullName());
        textDob.setText(DateHelper.toDateString(currentUser.getDob()));
        textGender.setText(currentUser.getGender() ? "Nam" : "Nữ");
        textEmail.setText(currentUser.getEmail());
        if (currentUser.getUrlAvatar() != null && !currentUser.getUrlAvatar().isEmpty())
            try {Picasso.get().load(currentUser.getUrlAvatar()).into(viewAvatar);}
            catch (Exception e) {viewAvatar.setBackgroundResource(R.drawable.account_svgrepo_com);}
        String[]split = currentUser.getFullName().split("");
        textAlias.setText(currentUser.getAlias() != null && !currentUser.getAlias().isEmpty() ?
                currentUser.getAlias() : split[split.length - 1]);
        textLifeStory.setText(currentUser.getLifeStory() != null ? currentUser.getLifeStory() : "Chưa viết tiểu sử");
        textTimeCreate.setText(DateHelper.toDateTimeString(currentUser.getTimeCreate()));
    }
    public void viewImageBig(View v) {
        preventDefaultEvent(body, false);
        if (layoutZoom != null)
        {
            layoutZoom.changeImage(currentUser.getUrlAvatar());
            layoutZoom.open();
            return;
        }
        layoutZoom = ImageViewFull.create(this, currentUser.getUrlAvatar(),
            new Runnable() {@Override public void run() {preventDefaultEvent(content, false);}},
            new Runnable() {@Override public void run() {preventDefaultEvent(content, true);}});
        addContentView(layoutZoom, new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        ));
    }
}
