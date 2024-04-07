package com.example.myapplication.view;

import android.content.Intent;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.define.DefineSharedPreferencesUserAuthen;
import com.example.myapplication.helper.DataLocalManager;
import com.example.myapplication.helper.HttpHelper;
import com.example.myapplication.model.UserLogin;

public class BasePageMainActivity extends BasePageAuthActivity {
    protected int idBtnPage = R.id.idBtnPageHome;
    protected View btnPage;
    protected View screen;
    @Override
    protected void getData() {
        super.getData();
        btnPage = findViewById(idBtnPage);
        screen = findViewById(R.id.idScreen);
    }
    @Override
    protected void setting() {
        btnPage.setBackground(getResources().getDrawable(R.color.primary_active));
        super.setting();
    }
    public void toPage(View view) {
        if (isLoading || alert.isOpenAlert())
            return;
        Intent intent = null;
        View parent = (View) view.getParent();
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
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        finishAffinity();
    }
}
