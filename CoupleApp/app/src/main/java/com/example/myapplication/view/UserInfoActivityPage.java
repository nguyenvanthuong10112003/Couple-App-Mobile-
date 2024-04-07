package com.example.myapplication.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.example.myapplication.component.ImageViewFull;
import com.example.myapplication.component.Input;
import com.example.myapplication.component.InputDate;
import com.example.myapplication.helper.DateHelper;
import com.example.myapplication.model.Response;
import com.example.myapplication.model.User;
import com.example.myapplication.service.api_service.ApiService;
import com.example.myapplication.service.api_service.UserApiService;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.imageview.ShapeableImageView;
import com.ortiz.touchview.TouchImageView;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;

public class UserInfoActivityPage extends BasePageAuthActivity {
    private User currentUser;
    private View tabView;
    private View tabEdit;
    private EditText inputFullname;
    private EditText inputAlias;
    private EditText inputEmail;
    private InputDate inputDob;
    private EditText inputLifeStory;
    private RadioGroup radioGender;
    private ShapeableImageView inputAvatar;
    private ShapeableImageView viewAvatar;
    private TextView textFullname;
    private TextView textAlias;
    private TextView textEmail;
    private TextView textDob;
    private TextView textLifeStory;
    private TextView textGender;
    private ImageViewFull layoutZoom;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infouser);
        init();
    }

    @Override
    protected void getData() {
        super.getData();
        tabEdit = findViewById(R.id.idPageInfoUserTabEdit);
        tabView = findViewById(R.id.idPageInfoUserTabView);

        inputFullname = findViewById(R.id.idPageInfoUserTabEditInputFullname);
        inputAlias = findViewById(R.id.idPageInfoUserTabEditInputAlias);
        inputLifeStory = findViewById(R.id.idPageInfoUserTabEditInputLifeStory);
        radioGender = findViewById(R.id.idPageInfoUserTabEditRadioGender);
        inputEmail = findViewById(R.id.idPageInfoUserTabEditInputEmail);
        inputAvatar = findViewById(R.id.idPageInfoUserTabEditImageAvatar);
        inputDob = findViewById(R.id.idPageInfoUserTabEditInputDob);

        textFullname = findViewById(R.id.idPageInfoUserTabViewTextFullname);
        textAlias = findViewById(R.id.idPageInfoUserTabViewTextAlias);
        textDob = findViewById(R.id.idPageInfoUserTabViewTextDob);
        textLifeStory = findViewById(R.id.idPageInfoUserTabViewTextLifeStory);
        textGender = findViewById(R.id.idPageInfoUserTabViewTextGender);
        textEmail = findViewById(R.id.idPageInfoUserTabViewTextEmail);
        viewAvatar = findViewById(R.id.idPageInfoUserTabViewImageAvatar);
    }

    @Override
    protected void setting() {
        super.setting();
        findViewById(R.id.header_logo).setVisibility(View.INVISIBLE);
        findViewById(R.id.header_backPage).setVisibility(View.VISIBLE);
        //Xử lý sự kiện khi bàn phím bật lên
        settingInputText(content);
        loadUser();
    }
    private void settingInputText(View view) {
        if (view instanceof  ViewGroup)
            for (int i = 0; i < ((ViewGroup)view).getChildCount(); i++)
                settingInputText(((ViewGroup)view).getChildAt(i));
        else if (view instanceof EditText) {
            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Rect r = new Rect();
                    view.getWindowVisibleDisplayFrame(r);
                    int screenHeight = view.getRootView().getHeight();
                    int keypadHeight = screenHeight - r.height();
                    if (keypadHeight > 0)
                        tabEdit.setPadding(0, 0, 0, keypadHeight);
                    else
                        tabEdit.setPadding(0,0,0,0);
                }
            });
        }
    }
    private void loadUser() {
        if (currentUser != null)
            return;
        content.setVisibility(View.INVISIBLE);
        startLoading();
        UserApiService userApiService = ApiService.createApiServiceWithAuth(UserApiService.class, token);
        userApiService.mind().enqueue(new Callback<Response<User>>() {
            @Override
            public void onResponse(Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
                stopLoading();
                if (!response.isSuccessful())
                    alert.show("Get an error",
                        new Runnable() {@Override public void run() {onBackPressed();}});
                else if (response.body().getStatus() == -1)
                    toPage(LoginActivity.class);
                else if (response.body().getStatus() == 1) {
                    currentUser = response.body().getData();
                    setupContentView();
                    content.setVisibility(View.VISIBLE);
                }
                else
                    alert.show(response.body().getMessage(),
                        new Runnable() {@Override public void run() {onBackPressed();}});
            }
            @Override
            public void onFailure(Call<Response<User>> call, Throwable throwable) {
                stopLoading();
                alert.show(throwable.toString(), "Trở về", "Tải lại",
                        new Runnable() {@Override public void run() {onBackPressed();}},
                        new Runnable() {@Override public void run() {loadUser();}});
            }
        });
    }
    private void setupContentView() {
        textFullname.setText(currentUser.getFullName());
        textDob.setText(DateHelper.toDateString(currentUser.getDob()));
        textGender.setText(currentUser.getGender() ? "Nam" : "Nữ");
        textEmail.setText(currentUser.getEmail());
        View grid = findViewById(R.id.idPageInfoUserTabViewGrid);
        textEmail.setWidth(grid.getWidth() - grid.getPaddingLeft() * 2
                - ((ViewGroup.MarginLayoutParams) grid.getLayoutParams()).leftMargin * 2 -
                ((GridLayout)grid).getChildAt(0).getWidth());
        if (currentUser.getUrlAvatar() != null && !currentUser.getUrlAvatar().isEmpty())
            try {Picasso.get().load(currentUser.getUrlAvatar()).into(viewAvatar);}
            catch (Exception e) {e.printStackTrace();}
        String[]split = currentUser.getFullName().split("");
        textAlias.setText(currentUser.getAlias() != null && !currentUser.getAlias().isEmpty() ?
                currentUser.getAlias() : split[split.length - 1]);
        textLifeStory.setText(currentUser.getLifeStory() != null ? currentUser.getLifeStory() : "Chưa viết tiểu sử");
    }
    private void setupContentEdit() {
        inputFullname.setText(currentUser.getFullName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                LocalDate dob = DateHelper.toLocalDate(currentUser.getDob());
                if (dob != null) {
                    inputDob.setDate(dob.getYear(), dob.getMonthValue(), dob.getDayOfMonth());
                }
            } catch (Exception e) {e.printStackTrace();}

        inputEmail.setText(currentUser.getEmail());
        if (currentUser.getUrlAvatar() != null && !currentUser.getUrlAvatar().isEmpty())
            try {Picasso.get().load(currentUser.getUrlAvatar()).into(inputAvatar);}
            catch (Exception e) {e.printStackTrace();}
        inputAlias.setText(currentUser.getAlias());
        inputLifeStory.setText(currentUser.getLifeStory());
    }
    public void toggleTabEdit(View v) {
        if (tabView.getVisibility() == View.VISIBLE) {
            tabView.setVisibility(View.INVISIBLE);
            tabEdit.setVisibility(View.VISIBLE);
            try {
                Picasso.get().load(userLogin.getAvatar()).into(inputAvatar);
            } catch (Exception e) {e.printStackTrace();}
            setupContentEdit();
            inputFullname.requestFocus();
            inputFullname.setSelection(inputFullname.getText().length());
        } else {
            tabView.setVisibility(View.VISIBLE);
            tabEdit.setVisibility(View.INVISIBLE);
        }
    }
    public void openImagePicker(View view) {
        ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getData() != null)
            inputAvatar.setImageURI(data.getData());
    }
    public void viewImageBig(View view) {
        preventDefaultEvent(body, false);
        if (layoutZoom != null)
        {
            layoutZoom.changeImage(currentUser.getUrlAvatar());
            layoutZoom.open();
            return;
        }
        layoutZoom = ImageViewFull.create(getBaseContext(), currentUser.getUrlAvatar(),
            new Runnable() {@Override public void run() {preventDefaultEvent(content, false);}},
            new Runnable() {@Override public void run() {preventDefaultEvent(content, true);}});
        addContentView(layoutZoom, new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        ));
    }
}
