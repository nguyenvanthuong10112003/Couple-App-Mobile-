package com.example.myapplication.view.BasePage;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.example.myapplication.model.Couple;
import com.example.myapplication.view.PageMain.CalendarActivityPage;
import com.example.myapplication.view.PageMain.HomeActivityPage;
import com.example.myapplication.view.PageMain.MessageActivityPage;
import com.example.myapplication.view.PageMain.StoryActivityPage;
import com.example.myapplication.view.PageMain.UserActivityPage;

public class BasePageMainActivity extends BasePageAuthActivity {
    protected int idBtnPage = R.id.idBtnPageHome;
    protected View btnPage;
    protected View screen;
    protected Couple couple;
    private static final long DOUBLE_BACK_PRESS_DELAY = 2000;
    private long lastBackPressTime = 0;
    @Override
    protected void getData() {
        super.getData();
        btnPage = findViewById(idBtnPage);
        screen = findViewById(R.id.idScreen);
    }
    @Override
    protected void setting() {
        super.setting();
        btnPage.setBackground(new ColorDrawable(ContextCompat.getColor(this, R.color.primary_active)));
        findViewById(R.id.header_backPage).setVisibility(View.INVISIBLE);
        findViewById(R.id.header_logo).setVisibility(View.VISIBLE);
    }
    public void toPage(View view) {
        if (isBusy())
            return;
        Intent intent = null;
        View parent = (View) view.getParent();
        if (couple == null && !(
                view.getId() == R.id.idBtnPageHome || parent.getId() == R.id.idBtnPageHome ||
                view.getId() ==  R.id.idBtnPageUser || parent.getId() == R.id.idBtnPageUser)) {
            alert.show("Bạn cần ghép cặp đôi mới có thể sử dụng chức năng này");
            return;
        }
        if ((view.getId() == R.id.idBtnPageHome ||
                parent.getId() == R.id.idBtnPageHome) &&
                !this.getClass().equals(HomeActivityPage.class))
            intent = new Intent(this, HomeActivityPage.class);
        else if ((view.getId() == R.id.idBtnPageCalendar ||
                parent.getId() == R.id.idBtnPageCalendar) &&
                !this.getClass().equals(CalendarActivityPage.class))
            intent = new Intent(this, CalendarActivityPage.class);
        else if ((view.getId() == R.id.idBtnPageMessage ||
                parent.getId() == R.id.idBtnPageMessage) &&
                !this.getClass().equals(MessageActivityPage.class))
            intent = new Intent(this, MessageActivityPage.class);
        else if ((view.getId() == R.id.idBtnPageStory ||
                parent.getId() == R.id.idBtnPageStory) &&
                !this.getClass().equals(StoryActivityPage.class))
            intent = new Intent(this, StoryActivityPage.class);
        else if ((view.getId() == R.id.idBtnPageUser ||
                parent.getId() == R.id.idBtnPageUser) &&
                !this.getClass().equals(UserActivityPage.class))
            intent = new Intent(this, UserActivityPage.class);
        if (intent != null) {
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }
    }
    @Override
    public void onBackPressed() {
        if (alert.isOpenAlert())
            return;
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBackPressTime <= DOUBLE_BACK_PRESS_DELAY) {
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            finishAffinity();
        } else {
            lastBackPressTime = currentTime;
            Toast.makeText(this, "Nhấn thêm một lần nữa để thoát", Toast.LENGTH_SHORT).show();
        }
    }
}
