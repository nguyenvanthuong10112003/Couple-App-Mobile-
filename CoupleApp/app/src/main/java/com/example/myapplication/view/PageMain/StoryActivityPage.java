package com.example.myapplication.view.PageMain;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.view.BasePage.BasePageMainActivity;

public class StoryActivityPage extends BasePageMainActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        idBtnPage = R.id.idBtnPageStory;
        init();
    }
}