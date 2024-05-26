package com.example.myapplication.view.PageChild;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.helper.DateHelper;
import com.example.myapplication.parcelable.ScheduleParcelable;
import com.example.myapplication.view.BasePage.BasePageAuthActivity;
import com.example.myapplication.view.Component.InputDateTime;
import com.example.myapplication.viewmodel.CalendarModels;

import java.time.LocalDateTime;

public class CalendarAddScheduleActivityPage extends BasePageAuthActivity {
    private View btnCancel;
    private View btnAccept;
    private EditText inputTitle;
    private EditText inputContent;
    private InputDateTime inputDateTime;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_addschedule);
        init();
    }

    @Override
    protected void getData() {
        super.getData();
        btnCancel = findViewById(R.id.idPageCalAddScheBtnCancel);
        btnAccept = findViewById(R.id.idPageCalAddScheBtnAdd);
        inputTitle = findViewById(R.id.idPageCalendarInputTitle);
        inputDateTime = findViewById(R.id.idPageCalendarInputTime);
        inputContent = findViewById(R.id.idPageCalendarInputContent);
        baseModels = new ViewModelProvider(this).get(CalendarModels.class);
    }

    @Override
    protected void setting() {
        super.setting();
        findViewById(R.id.header_logo).setVisibility(View.INVISIBLE);
        findViewById(R.id.header_backPage).setVisibility(View.VISIBLE);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSchedule();
            }
        });
        ((CalendarModels) baseModels).getLiveNewSchedule().observe(this, newSchedule -> {
            if (newSchedule != null)
            {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("new-schedule", new ScheduleParcelable(newSchedule));
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    private void addSchedule() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime time = inputDateTime.getValueTime();
            if (time == null) {
                alert.show("Thời gian không hợp lệ");
                return;
            }
            if (LocalDateTime.now().compareTo(time) > 0) {
                alert.show("Không thể thêm lịch trình ở quá khứ");
                return;
            }
            String title = inputTitle.getText().toString();
            if (title == null || title.isEmpty()) {
                alert.show("Tiêu để không được để trống");
                return;
            }
            String content = inputContent.getText().toString();
            ((CalendarModels) baseModels).add(title,
                    DateHelper.toDateTimeServe(time), content);
        }
    }
}
