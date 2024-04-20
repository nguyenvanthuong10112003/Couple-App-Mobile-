package com.example.myapplication.view.PageMain;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.view.BasePage.BasePageMainActivity;

public class MessageActivityPage extends BasePageMainActivity {
    View boxInput;
    EditText inputMessage;
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
    }
}