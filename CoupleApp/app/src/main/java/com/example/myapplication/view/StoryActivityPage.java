package com.example.myapplication.view;

import android.os.Bundle;

import com.example.myapplication.R;

public class StoryActivityPage extends BasePageMainActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_story);
        idBtnPage = R.id.idBtnPageStory;
        super.onCreate(savedInstanceState);
    }
}