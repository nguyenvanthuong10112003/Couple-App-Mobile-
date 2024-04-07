package com.example.myapplication.view;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.example.myapplication.helper.ImageHelper;
import com.example.myapplication.model.Response;
import com.example.myapplication.model.User;
import com.example.myapplication.service.api_service.ApiService;
import com.example.myapplication.service.api_service.UserApiService;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.time.Month;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;

public class UserActivityPage extends BasePageMainActivity {
    private ColorDrawable colorTouchDown;
    private ColorDrawable colorTouchUp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        idBtnPage = R.id.idBtnPageUser;
        init();
    }
    @Override
    protected void getData() {
        super.getData();
    }
    private void toLayout(int id) {
        if (id == R.id.ibBtnToLogout)
            logout();
        else if (id == R.id.ibBtnToPageInfoUser)
            toPage(UserInfoActivityPage.class);
    }
    @Override
    protected void setting() {
        super.setting();
        TextView nameUser = findViewById(R.id.idTextNameUser);
        if (!userLogin.getAlias().isEmpty())
            nameUser.setText(userLogin.getAlias());
        else if (!userLogin.getFullName().isEmpty()) {
            String[] nameSplits = userLogin.getFullName().split(" ");
            nameUser.setText(nameSplits[nameSplits.length - 1]);
        }
        else
            nameUser.setText("<Chưa đặt tên>");
        ShapeableImageView avatarUser = findViewById(R.id.idImgAvatarTabUser);
        if (!userLogin.getAvatar().isEmpty())
            try {
                Picasso.get().load(userLogin.getAvatar()).into(avatarUser);
            } catch (Exception e) {e.printStackTrace();}
        colorTouchDown = new ColorDrawable(ContextCompat.getColor(getBaseContext(), R.color.white_active));
        colorTouchUp = new ColorDrawable(ContextCompat.getColor(getBaseContext(), R.color.white));
        TableLayout table = (TableLayout) content;
        for (int i = 0; i < table.getChildCount(); i++) {
            View view = table.getChildAt(i);
            if (view instanceof TableRow)
                view.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN)
                            view.setBackground(colorTouchDown);
                        else if (event.getAction() == MotionEvent.ACTION_UP) {
                            view.setBackground(colorTouchUp);
                            toLayout(view.getId());
                        }
                        return true;
                    }
                });
        }
    }

    public void pressRow(View view) {
        view.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.secondary_active));
    }
}