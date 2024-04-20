package com.example.myapplication.view.PageChild;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.component.ImageViewFull;
import com.example.myapplication.helper.DateHelper;
import com.example.myapplication.model.User;
import com.example.myapplication.parcelable.UserParcelable;
import com.example.myapplication.view.BasePage.BasePageAuthActivity;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

public class HomeDetailUser extends BasePageAuthActivity {
    private ShapeableImageView viewAvatar;
    private TextView textFullname;
    private TextView textAlias;
    private TextView textEmail;
    private TextView textDob;
    private TextView textLifeStory;
    private TextView textGender;
    private ImageViewFull layoutZoom;
    private TextView textTimeCreate;
    private UserParcelable user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_detail_user);
        init();
    }

    @Override
    protected void getData() {
        super.getData();
        textFullname = findViewById(R.id.idPageHomeDetailUserTextFullname);
        textAlias = findViewById(R.id.idPageHomeDetailUserTextAlias);
        textDob = findViewById(R.id.idPageHomeDetailUserTextDob);
        textLifeStory = findViewById(R.id.idPageHomeDetailUserTextLifeStory);
        textGender = findViewById(R.id.idPageHomeDetailUserTextGender);
        textEmail = findViewById(R.id.idPageHomeDetailUserTextEmail);
        viewAvatar = findViewById(R.id.idPageHomeDetailUserImageAvatar);
        textTimeCreate = findViewById(R.id.idPageHomeDetailUserTextTimeCreate);
        Intent intent = getIntent();
        user = intent.getParcelableExtra("data");
    }

    @Override
    protected void setting() {
        super.setting();
        setupContentView();
    }

    private void setupContentView() {
        textFullname.setText(user.getFullName() == null || user.getFullName().isEmpty() ? "<Chưa đặt tên>" : user.getFullName());
        textDob.setText(DateHelper.toDateString(user.getDob()));
        textGender.setText(user.getGender() ? "Nam" : "Nữ");
        textEmail.setText(user.getEmail());
        if (user.getUrlAvatar() != null && !user.getUrlAvatar().isEmpty())
            try {
                Picasso.get().load(user.getUrlAvatar()).into(viewAvatar);}
            catch (Exception e) {viewAvatar.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.account_svgrepo_com));}
        String[]split = user.getFullName() != null ? user.getFullName().split("") : new String[]{"<Chưa đặt tên>"};
        textAlias.setText(user.getAlias() != null && !user.getAlias().isEmpty() ?
                user.getAlias() : split[split.length - 1]);
        textLifeStory.setText(user.getLifeStory() != null ? user.getLifeStory() : "Chưa viết tiểu sử");
        textTimeCreate.setText(DateHelper.toDateTimeString(user.getTimeCreate()));
    }

    public void viewImageBig(View v) {
        preventDefaultEvent(body, false);
        if (layoutZoom != null)
        {
            layoutZoom.changeImage(user.getUrlAvatar());
            layoutZoom.open();
            return;
        }
        layoutZoom = ImageViewFull.create(this, user.getUrlAvatar(),
                new Runnable() {@Override public void run() {preventDefaultEvent(content, false);}},
                new Runnable() {@Override public void run() {preventDefaultEvent(content, true);}});
        addContentView(layoutZoom, new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        ));
    }
}