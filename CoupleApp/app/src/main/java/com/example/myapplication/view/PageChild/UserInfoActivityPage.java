package com.example.myapplication.view.PageChild;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;

import com.example.myapplication.R;
import com.example.myapplication.component.ImageViewFull;
import com.example.myapplication.component.InputDate;
import com.example.myapplication.define.DefineSharedPreferencesUserAuthen;
import com.example.myapplication.define.DefineUserAttrRequest;
import com.example.myapplication.helper.DateHelper;
import com.example.myapplication.helper.RealPathUtil;
import com.example.myapplication.helper.StringHelper;
import com.example.myapplication.model.Response;
import com.example.myapplication.model.User;
import com.example.myapplication.service.api_service.ApiService;
import com.example.myapplication.service.api_service.UserApiService;
import com.example.myapplication.view.BasePage.BasePageAuthActivity;
import com.example.myapplication.view.Authentication.LoginActivity;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    private TextView textTimeCreate;
    private Uri uriAvatar;
    private UserApiService userApiService;
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
        textTimeCreate = findViewById(R.id.idPageInfoUserTabViewTextTimeCreate);
    }

    @Override
    protected void setting() {
        super.setting();
        //Xử lý sự kiện khi bàn phím bật lên
        settingInputText(content);
        userApiService = ApiService.createApiServiceWithAuth(this, UserApiService.class, token);
        findViewById(R.id.header_backPage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedWithResult();
            }
        });
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
        userApiService.mind().enqueue(new Callback<Response<User>>() {
            @Override
            public void onResponse(Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
                stopLoading();
                if (!response.isSuccessful())
                    alert.show("Get an error",
                        new Runnable() {@Override public void run() {onBackPressed();}});
                else if (response.body().getStatus() == -1)
                    logout();
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
                alert.show("Có lỗi xảy ra, vui lòng thử lại sau.", "Trở về", "Tải lại",
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
        if (currentUser.getUrlAvatar() != null && !currentUser.getUrlAvatar().isEmpty())
            try {Picasso.get().load(currentUser.getUrlAvatar()).into(viewAvatar);}
            catch (Exception e) {viewAvatar.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.account_svgrepo_com));}
        String[]split = currentUser.getFullName().split("");
        textAlias.setText(currentUser.getAlias() != null && !currentUser.getAlias().isEmpty() ?
                currentUser.getAlias() : split[split.length - 1]);
        textLifeStory.setText(currentUser.getLifeStory() != null ? currentUser.getLifeStory() : "Chưa viết tiểu sử");
        textTimeCreate.setText(DateHelper.toDateTimeString(currentUser.getTimeCreate()));
        uriAvatar = null;
    }
    private void setupContentEdit() {
        inputFullname.setText(currentUser.getFullName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            try {
                LocalDate dob = DateHelper.toLocalDate(currentUser.getDob());
                if (dob != null) {
                    inputDob.setDate(dob.getYear(), dob.getMonthValue(), dob.getDayOfMonth());
                }
            } catch (Exception e) {e.printStackTrace();}
        ((RadioButton)findViewById(currentUser.getGender() ?
                R.id.idPageInfoUserTabEditRadioGenderMale : R.id.idPageInfoUserTabEditRadioGenderFeMale))
                .setChecked(true);
        inputEmail.setText(currentUser.getEmail());
        if (currentUser.getUrlAvatar() != null && !currentUser.getUrlAvatar().isEmpty())
            try {Picasso.get().load(currentUser.getUrlAvatar()).into(inputAvatar);}
            catch (Exception e) {inputAvatar.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.account_svgrepo_com));}
        inputAlias.setText(currentUser.getAlias());
        inputLifeStory.setText(currentUser.getLifeStory());
    }
    public void toggleTabEdit(View v) {
        if (tabView.getVisibility() == View.VISIBLE) {
            try {
                Picasso.get().load(userLogin.getAvatar()).into(inputAvatar);
            } catch (Exception e) {inputAvatar.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.account_svgrepo_com));}
            setupContentEdit();
            inputFullname.requestFocus();
            inputFullname.setSelection(inputFullname.getText().length());
            tabView.setVisibility(View.INVISIBLE);
            tabEdit.setVisibility(View.VISIBLE);
        } else {
            setupContentView();
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
        if (data.getData() != null) {
            uriAvatar = data.getData();
            inputAvatar.setImageURI(uriAvatar);
        }
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
    public void sendEdit(View v) {
        LocalDate dob = null;
        try {
            dob = inputDob.getValue();
        } catch (Exception e) {e.printStackTrace();}
        finally {
            if (dob == null) {
                alert.show("Ngày không hợp lệ");
                return;
            }
        }
        if (radioGender.getCheckedRadioButtonId() == -1)
        {
            alert.show("Giới tính không được để trống");
            return;
        }
        String fullName = inputFullname.getText().toString();
        String alias = inputAlias.getText().toString();
        String email = inputEmail.getText().toString();
        String lifeStory = inputLifeStory.getText().toString();
        boolean gender = R.id.idPageInfoUserTabEditRadioGenderMale == radioGender.getCheckedRadioButtonId();
        if (fullName.length() == 0) {
            alert.show("Họ và tên phải có ít nhất 1 ký tự");
            return;
        }
        if (!StringHelper.isValidEmailAddress(email)) {
            alert.show("Email không đúng định dạng");
            return;
        }
        MediaType type = MediaType.parse("multipart/form-data");
        MultipartBody.Part multipartBodyAvt = null;
        if (uriAvatar != null) {
            String realPath = RealPathUtil.getRealPath(this, uriAvatar);
            File file = new File(realPath);
            RequestBody requestBodyAvatar = RequestBody.create(file, type);
            multipartBodyAvt =
                    MultipartBody.Part.createFormData(DefineUserAttrRequest.imageAvatar, file.getName(), requestBodyAvatar);
        }
        startLoading();
        userApiService.edit(multipartBodyAvt,
                        RequestBody.create(fullName, type),
                        RequestBody.create(DateHelper.toDateServe(dob), type),
                        RequestBody.create(alias, type),
                        RequestBody.create(email, type),
                        RequestBody.create(gender ? "true" : "false", type),
                        RequestBody.create(lifeStory, type))
            .enqueue(new Callback<Response<User>>() {
                @Override
                public void onResponse(Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
                    stopLoading();
                    if (!response.isSuccessful())
                        alert.show("Get an error");
                    else if (response.body().getStatus() == Response.NEED_LOGIN)
                        logout();
                    else if (response.body().getStatus() == Response.ERROR)
                        alert.show(response.body().getMessage());
                    else if (response.body().getStatus() == Response.SUCCESS) {
                        currentUser = response.body().getData();
                        HashMap<String, String> map = new HashMap<>();
                        map.put(DefineSharedPreferencesUserAuthen.URL_AVATAR, currentUser.getUrlAvatar());
                        map.put(DefineSharedPreferencesUserAuthen.ALIAS, currentUser.getAlias());
                        map.put(DefineSharedPreferencesUserAuthen.FULL_NAME, currentUser.getFullName());
                        map.put(DefineSharedPreferencesUserAuthen.EMAIL, currentUser.getEmail());
                        dataLocalManager.saveDatas(
                            DefineSharedPreferencesUserAuthen.PATH, map
                        );
                        userLogin.setAlias(currentUser.getAlias());
                        userLogin.setAvatar(currentUser.getUrlAvatar());
                        userLogin.setFullName(currentUser.getFullName());
                        alert.show("Lưu thành công", new Runnable() {
                            @Override
                            public void run() {
                                toggleTabEdit(null);
                            }
                        });
                    }
                }
                @Override
                public void onFailure(Call<Response<User>> call, Throwable throwable) {
                    stopLoading();
                    alert.show("Có lỗi xảy ra, vui lòng thử lại sau.", "Trở về", "Thử lại",
                            null, new Runnable() {@Override public void run() {sendEdit(null);}
                            });
                }
            });
    }
}
