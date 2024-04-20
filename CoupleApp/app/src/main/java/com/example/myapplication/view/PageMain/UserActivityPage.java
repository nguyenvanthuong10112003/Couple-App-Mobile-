package com.example.myapplication.view.PageMain;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.example.myapplication.model.UserLogin;
import com.example.myapplication.view.BasePage.BasePageMainActivity;
import com.example.myapplication.view.PageChild.UserInfoActivityPage;
import com.example.myapplication.view.PageChild.UserSecurityActivityPage;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

public class UserActivityPage extends BasePageMainActivity {
    private ColorDrawable colorTouchDown;
    private ColorDrawable colorTouchUp;
    private ShapeableImageView avatarUser;
    private TextView nameUser;
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
        avatarUser = findViewById(R.id.idImgAvatarTabUser);
        nameUser = findViewById(R.id.idTextNameUser);
    }
    private void toLayout(int id) {
        if (id == R.id.ibBtnToLogout)
            logout();
        else if (id == R.id.ibBtnToPageInfoUser)
            toPageForResult(UserInfoActivityPage.class);
        else if (id == R.id.ibBtnToPageSecurity)
            toPage(UserSecurityActivityPage.class);
    }
    @Override
    protected void resert() {
        checkLogin();
        setupUser();
    }

    private void setupUser() {
        if (userLogin.getAlias() != null && !userLogin.getAlias().isEmpty())
            nameUser.setText(userLogin.getAlias());
        else if (userLogin.getFullName() != null && !userLogin.getFullName().isEmpty()) {
            String[] nameSplits = userLogin.getFullName().split(" ");
            nameUser.setText(nameSplits[nameSplits.length - 1]);
        }
        else
            nameUser.setText("<Chưa đặt tên>");
        if (userLogin.getAvatar() != null && !userLogin.getAvatar().isEmpty())
            try {
                Picasso.get().load(userLogin.getAvatar()).into(avatarUser);
            } catch (Exception e) {avatarUser.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.account_svgrepo_com));}
    }
    @Override
    protected void setting() {
        super.setting();
        setupUser();
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
}