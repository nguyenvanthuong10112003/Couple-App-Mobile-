package com.example.myapplication.view.PageMain;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.helper.DateHelper;
import com.example.myapplication.model.ListMessage;
import com.example.myapplication.model.Message;
import com.example.myapplication.view.BasePage.BasePageMainActivity;
import com.example.myapplication.viewmodel.MessageModels;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.util.LinkedList;

public class MessageActivityPage extends BasePageMainActivity {
    View boxInput;
    EditText inputMessage;
    TextView btnSend;
    LinearLayout container;
    ScrollView scrollView;
    ShapeableImageView imageAvatarEnemy;
    TextView textNameEnemy;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        idBtnPage = R.id.idBtnPageMessage;
        init();
    }

    @Override
    protected void getData() {
        super.getData();
        boxInput = findViewById(R.id.idPageMessageBoxInput);
        inputMessage = findViewById(R.id.idPageMessageInputMessage);
        baseModels = new ViewModelProvider(this).get(MessageModels.class);
        btnSend = findViewById(R.id.idPageMessageBtnSendMessage);
        container = findViewById(R.id.idPageMessageListMessage);
        scrollView = (ScrollView) container.getParent();
        imageAvatarEnemy = findViewById(R.id.idPageMessageImageAvatar);
        textNameEnemy = findViewById(R.id.idPageMessageTextName);
    }

    @Override
    protected boolean startFocus(ViewGroup view) {
        return true;
    }

    @Override
    protected void setting() {
        super.setting();
        inputMessage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                inputMessage.getWindowVisibleDisplayFrame(r);
                int screenHeight = inputMessage.getRootView().getHeight();
                int keypadHeight = screenHeight - r.height();
                if (keypadHeight > 0)
                    boxInput.setPadding(0, 0, 0, keypadHeight - footer.getHeight());
                else
                    boxInput.setPadding(0,0,0,0);
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
        content.setVisibility(View.INVISIBLE);
        inputMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (inputMessage.getText().toString().length() == 0)
                {
                    btnSend.setAlpha(0.4f);
                } else {
                    btnSend.setAlpha(1);
                }
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputMessage.getText().toString().length() == 0)
                    return;
                step = 2;
                startLoad();
            }
        });
        step = 1;
        startLoad();
    }

    @Override
    protected void startLoad() {
        switch (step) {
            case 1:
                ((MessageModels) baseModels).getLiveListMessage()
                    .observe(this, list -> {
                        if (list != null)
                            setupMessages(list);
                    });
                break;
            case 2:
                ((MessageModels) baseModels).sendMessage(inputMessage.getText().toString());
                break;
        }
    }

    @Override
    protected void whenNoHaveCouple() {
        alert.show("Bạn chưa có cặp đôi, vui lòng ghép đôi", new Runnable() {
            @Override
            public void run() {
                toPage(findViewById(R.id.idBtnPageHome));
            }
        });
    }

    @Override
    protected void whenServerError() {
        String message = "Có lỗi xảy ra, vui lòng thử lại sau";
        switch (step) {
            case 1:
                alert.show(message, "Trở về", "Tải lại",
                    new Runnable() {
                        @Override
                        public void run() {
                            onBackPressed();
                        }
                    },
                    new Runnable() {
                        @Override
                        public void run() {
                            startLoad();
                        }
                    });
        }
    }

    @Override
    protected void whenSuccess() {
        switch (step) {
            case 2:
                inputMessage.setText("");
        }
    }

    private void setupMessages(ListMessage listMessages) {
        content.setVisibility(View.VISIBLE);
        textNameEnemy.setText(listMessages.getCouple().getEnemy().getFullName());
        String url = listMessages.getCouple().getEnemy().getUrlAvatar();
        if (!(url == null || url.isEmpty()))
            try {
                Picasso.get().load(url).into(imageAvatarEnemy);
            } catch (Exception e) {
                imageAvatarEnemy.setBackgroundResource(R.drawable.account_svgrepo_com);
            }
        if (listMessages.getMessages() == null)
            return;
        if (listMessages.getMessages().size() > 0)
            container.removeAllViews();
        for (Message message : listMessages.getMessages()) {
            View viewChild;
            if (message.getSenderId() == userLogin.getId())
                viewChild = LayoutInflater.from(this)
                    .inflate(R.layout.message_mind, container, false);
            else
                viewChild = LayoutInflater.from(this)
                    .inflate(R.layout.message_enemy, container, false);
            TextView content = viewChild.findViewById(R.id.idPageMessageItemContent);
            content.setText(message.getContent());
            TextView timeRead = viewChild.findViewById(R.id.idPageMessageItemTimeRead);
            if (timeRead != null)
                timeRead.setText(message.getTimeRead() == null ? "Chưa đọc" : "Đã xem " +
                    DateHelper.demThoiGian(message.getTimeRead()));
            TextView timeSend = viewChild.findViewById(R.id.idPageMessageItemTimeSend);
            if (timeSend != null)
                timeSend.setText(DateHelper.demThoiGian(message.getTimeSend()));
            ShapeableImageView imageAvatarEnemy = viewChild.findViewById(R.id.idPageMessageItemImageAvatar);
            if (imageAvatarEnemy != null) {
                if (!(url == null || url.isEmpty()))
                    try {
                        Picasso.get().load(url).into(imageAvatarEnemy);
                    } catch (Exception e) {
                        imageAvatarEnemy.setBackgroundResource(R.drawable.account_svgrepo_com);
                    }
            }
            container.addView(viewChild);
        }
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }
}