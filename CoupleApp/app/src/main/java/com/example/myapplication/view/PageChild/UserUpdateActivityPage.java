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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import com.example.myapplication.R;
import com.example.myapplication.define.DefineSharedPreferencesUserAuthen;
import com.example.myapplication.define.DefineUserAttrRequest;
import com.example.myapplication.helper.DateHelper;
import com.example.myapplication.helper.RealPathUtil;
import com.example.myapplication.helper.StringHelper;
import com.example.myapplication.model.User;
import com.example.myapplication.parcelable.UserParcelable;
import com.example.myapplication.view.BasePage.BasePageAuthActivity;
import com.example.myapplication.view.Component.Button;
import com.example.myapplication.view.Component.InputDate;
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

public class UserUpdateActivityPage extends BasePageAuthActivity {
    private EditText inputFullname;
    private EditText inputAlias;
    private EditText inputEmail;
    private InputDate inputDob;
    private EditText inputLifeStory;
    private RadioGroup radioGender;
    private ShapeableImageView inputAvatar;
    private UserParcelable currentUser;
    private Uri uriAvatar;
    private Button btnCanel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_updateuser);
        init();
    }

    @Override
    protected void getData() {
        super.getData();
        inputFullname = findViewById(R.id.idPageInfoUserTabEditInputFullname);
        inputAlias = findViewById(R.id.idPageInfoUserTabEditInputAlias);
        inputLifeStory = findViewById(R.id.idPageInfoUserTabEditInputLifeStory);
        radioGender = findViewById(R.id.idPageInfoUserTabEditRadioGender);
        inputEmail = findViewById(R.id.idPageInfoUserTabEditInputEmail);
        inputAvatar = findViewById(R.id.idPageInfoUserTabEditImageAvatar);
        inputDob = findViewById(R.id.idPageInfoUserTabEditInputDob);
        btnCanel = findViewById(R.id.idPageInfoUserTabEditBtnCancel);
        baseModels = new ViewModelProvider(this).get(UserModels.class);
        Intent intent = getIntent();
        currentUser = intent.getParcelableExtra("info-mind");
    }

    @Override
    protected void setting() {
        super.setting();
        if (currentUser == null) {
            onBackPressed();
            return;
        }
        btnCanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        settingInputText(content);
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
            try {
                Picasso.get().load(currentUser.getUrlAvatar()).into(inputAvatar);}
            catch (Exception e) {inputAvatar.setBackgroundResource(R.drawable.account_svgrepo_com);}
        inputAlias.setText(currentUser.getAlias());
        inputLifeStory.setText(currentUser.getLifeStory());
        inputFullname.requestFocus();
        inputFullname.setSelection(inputFullname.getText().length());
    }

    private void settingInputText(View view) {
        if (view instanceof ViewGroup)
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
                        content.setPadding(0, 0, 0, keypadHeight);
                    else
                        content.setPadding(0,0,0,0);
                }
            });
        }
    }

    @Override
    protected void whenSuccess() {
        super.whenSuccess();
        try {
            User user = ((UserModels) baseModels).getUser().getValue();
            if (user == null) return;
            HashMap<String, String> map = new HashMap<>();
            map.put(DefineSharedPreferencesUserAuthen.URL_AVATAR, user.getUrlAvatar());
            map.put(DefineSharedPreferencesUserAuthen.ALIAS, user.getAlias());
            map.put(DefineSharedPreferencesUserAuthen.FULL_NAME, user.getFullName());
            map.put(DefineSharedPreferencesUserAuthen.EMAIL, user.getEmail());
            dataLocalManager.saveDatas(
                    DefineSharedPreferencesUserAuthen.PATH, map
            );
            Intent resultIntent = new Intent();
            resultIntent.putExtra("updated", new UserParcelable(user));
            setResult(RESULT_OK, resultIntent);
            finish();
        } catch (Exception e) {}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getData() != null) {
            uriAvatar = data.getData();
            inputAvatar.setImageURI(uriAvatar);
        }
    }

    public void openImagePicker(View view) {
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .start();
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
        ((UserModels) baseModels).edit(multipartBodyAvt,
            RequestBody.create(fullName, type),
            RequestBody.create(DateHelper.toDateServe(dob), type),
            RequestBody.create(alias, type),
            RequestBody.create(email, type),
            RequestBody.create(gender ? "true" : "false", type),
            RequestBody.create(lifeStory, type));
    }
}
