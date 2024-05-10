package com.example.myapplication.view.PageMain;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.helper.DateHelper;
import com.example.myapplication.model.Message;
import com.example.myapplication.view.BasePage.BasePageMainActivity;
import com.example.myapplication.view.AdapterRecycleView.MessageAdapter;
import com.example.myapplication.viewmodel.MessageModels;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.time.LocalDateTime;

public class MessageActivityPage extends BasePageMainActivity {
    View boxInput;
    EditText inputMessage;
    TextView btnSend;
    ShapeableImageView imageAvatarEnemy;
    TextView textNameEnemy;
    RecyclerView recyclerView;
    MessageAdapter messageAdapter;
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
        recyclerView = findViewById(R.id.idPageMessageRecycler);
        imageAvatarEnemy = findViewById(R.id.idPageMessageImageAvatar);
        textNameEnemy = findViewById(R.id.idPageMessageTextName);
        messageAdapter = new MessageAdapter(this);
    }

    @Override
    protected void onChangCurrentUser() {
        super.onChangCurrentUser();
        messageAdapter.setCurrentUser(userLogin);
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
        messageAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                recyclerView.scrollToPosition(0);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.scrollToPosition(0);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(messageAdapter);

        step = 1;
        startLoad();
    }

    @Override
    protected void startLoad() {
        switch (step) {
            case 1: {
                ((MessageModels) baseModels).getLiveListMessage()
                    .observe(this, list -> {
                        if (list != null && list.getMessages() != null && list.getMessages().size() > 0) {
                            messageAdapter.setData(list);
                            findViewById(R.id.idPageMessageTextNoAny).setVisibility(View.INVISIBLE);
                        } else
                            findViewById(R.id.idPageMessageTextNoAny).setVisibility(View.VISIBLE);
                        if (content.getVisibility() == View.INVISIBLE)
                            content.setVisibility(View.VISIBLE);
                        if (list != null && list.getCouple() != null && list.getCouple().getEnemy() != null) {
                            textNameEnemy.setText(list.getCouple().getEnemy().getFullName());
                            String url = list.getCouple().getEnemy().getUrlAvatar();
                            if (url != null && !url.isEmpty()) {
                                Picasso.get().load(url).into(imageAvatarEnemy);
                            }
                        }
                    });
                break;
            }
            case 2:
                ((MessageModels) baseModels).sendMessage(inputMessage.getText().toString());
                break;
        }
    }

    @Override
    protected void whenNoHaveCouple() {
        alert.show("Bạn chưa có cặp đôi, vui lòng ghép đôi để sử dụng chức năng này", new Runnable() {
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
            case 1: {
                alert.show(message, "Trở về", "Tải lại",
                    new Runnable() {
                        @Override
                        public void run() {
                            toPage(findViewById(R.id.idBtnPageHome));
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
    }

    @Override
    protected void whenSuccess() {
        switch (step) {
            case 1:
                break;
            case 2: {
                Message message = new Message();
                message.setSenderId(userLogin.getId());
                message.setContent(inputMessage.getText().toString());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    message.setTimeSend(DateHelper.toTime(LocalDateTime.now()));
                }
                messageAdapter.addData(message);
                inputMessage.setText("");
            }
        }
        step = -1;
    }
}