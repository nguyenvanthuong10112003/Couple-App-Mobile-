package com.example.myapplication.view.PageMain;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import com.example.myapplication.R;
import com.example.myapplication.helper.DateHelper;
import com.example.myapplication.view.Component.CalendarTable;
import com.example.myapplication.view.Component.CustomSpinnerAdapter;
import com.example.myapplication.view.BasePage.BasePageMainActivity;
import com.example.myapplication.view.PageChild.CalendarAddScheduleActivityPage;
import com.example.myapplication.view.ViewPager.CalendarViewPagerAdapter;
import com.example.myapplication.viewmodel.CalendarModels;
import com.google.android.material.tabs.TabLayout;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalendarActivityPage extends BasePageMainActivity {
    Spinner choseMonth;
    EditText inputYear;
    CalendarTable tableCal;
    ArrayAdapter<String> adapter;
    TabLayout tabLayout;
    CalendarViewPagerAdapter viewPagerAdapter;
    ViewPager2 viewPager2;
    ViewGroup viewSelectDate;
    ViewGroup viewBottom;
    ImageButton btnGiamNam;
    ImageButton btnTangNam;
    LocalDate currentSelected;
    View btnAddSchedule;
    boolean isOpenCal = true;
    ImageButton btnToggle;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        idBtnPage = R.id.idBtnPageCalendar;
        init();
    }
    @Override
    protected void getData() {
        super.getData();
        inputYear = (EditText) findViewById(R.id.idInputYear);
        tableCal = (CalendarTable) findViewById(R.id.idTableCal);
        choseMonth = findViewById(R.id.idPageCalendarSpinnerChoseMonth);
        tabLayout = findViewById(R.id.idPageCalendarTabLayout);
        viewPagerAdapter = new CalendarViewPagerAdapter(this);
        viewPager2 = findViewById(R.id.idPageCalendarViewPager2);
        viewSelectDate = findViewById(R.id.idPageCalendarViewSelectDate);
        viewBottom = findViewById(R.id.idPageCalendarView);
        btnGiamNam = findViewById(R.id.idPageCalendarBtnDownYear);
        btnTangNam = findViewById(R.id.idPageCalendarBtnUpYear);
        baseModels = new ViewModelProvider(this).get(CalendarModels.class);
        btnAddSchedule = findViewById(R.id.idPageCalendarBtnAddSchedule);
        btnToggle = findViewById(R.id.idPageCalendarBtnToggle);
    }

    @Override
    protected boolean startFocus(ViewGroup view) {
        return true;
    }

    @Override
    protected boolean isBusy() {
        return super.isBusy() || ((CustomSpinnerAdapter)choseMonth.getAdapter()).isOpen();
    }
    @Override
    protected void setting() {
        super.setting();
        List<String> data = new ArrayList<String>(
            Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")
        );
        adapter = new CustomSpinnerAdapter(this, R.layout.spinner_item_selected, data);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        choseMonth.setAdapter(adapter);
        choseMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        tableCal.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tableCal.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
                    return;
                LocalDate today = LocalDate.now();
                choseMonth.setSelection(today.getMonthValue() - 1);
                inputYear.setText(String.valueOf(today.getYear()));
            }
        });
        inputYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                upateCal();
            }
        });
        btnGiamNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = inputYear.getText().toString();
                try {
                    int year = Integer.parseInt(text);
                    inputYear.setText(String.valueOf(year - 1));
                    upateCal();
                } catch (Exception e) {}
            }
        });
        btnTangNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = inputYear.getText().toString();
                try {
                    int year = Integer.parseInt(text);
                    inputYear.setText(String.valueOf(year + 1));
                    upateCal();
                } catch (Exception e) {}
            }
        });
        choseMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                upateCal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ((CalendarModels) baseModels).getLiveSelectedDate().observe(this, date -> {
            if (date != null)
                try {
                    currentSelected = date;
                    tabLayout.getTabAt(0).setText(DateHelper.toDateString(currentSelected));
                } catch (Exception e) {}
        });
        btnAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivityPage.this, CalendarAddScheduleActivityPage.class);
                activityLaucher.launch(intent);
            }
        });
        btnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpenCal) {
                    Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
                    btnToggle.setClickable(false);
                    slideUp.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            isOpenCal = false;
                            btnToggle.setClickable(true);
                            btnToggle.setImageResource(R.drawable.ic_down);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    viewSelectDate.startAnimation(slideUp);
                    Animation animation = new Animation() {
                        final int height = viewSelectDate.getHeight();
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {
                            ViewGroup.LayoutParams layoutParams = viewBottom.getLayoutParams();
                            ((RelativeLayout.LayoutParams) layoutParams).setMargins(0,(int)(height * (1-interpolatedTime)) - height, 0, 0);
                            viewBottom.requestLayout();
                        }
                    };
                    animation.setDuration(200);
                    viewBottom.startAnimation(animation);
                } else {
                    Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                    btnToggle.setClickable(false);
                    slideDown.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            isOpenCal = true;
                            btnToggle.setClickable(true);
                            btnToggle.setImageResource(R.drawable.ic_up);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    viewSelectDate.startAnimation(slideDown);
                    Animation animation = new Animation() {
                        final int height = viewSelectDate.getHeight();
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {
                            ViewGroup.LayoutParams layoutParams = viewBottom.getLayoutParams();
                            ((RelativeLayout.LayoutParams) layoutParams).setMargins(0, -(int)(height * (1-interpolatedTime)), 0, 0);
                            viewBottom.requestLayout();
                        }
                    };
                    animation.setDuration(200);
                    viewBottom.startAnimation(animation);
                }
            }
        });
        step = 1;
        startLoad();
    }

    @Override
    protected void resume(Intent dt) {
        try {
            if (dt != null)
                ((CalendarModels) baseModels).setNewSchedule(dt.getParcelableExtra("new-schedule"));
        } catch (Exception ignored) {}
    }

    @Override
    protected void startLoad() {
        switch (step) {
            case 1:
                ((CalendarModels) baseModels).initLiveList();
                break;
        }
    }

    @Override
    protected void whenSuccess() {
        switch (step)
        {
            case 1: {
                setupTabLayout();
                content.setVisibility(View.VISIBLE);
            } break;
        }
        step = -1;
    }

    private void setupTabLayout() {
        viewPager2.setAdapter(viewPagerAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
            super.onPageSelected(position);
            tabLayout.getTabAt(position).select();
            }
        });
        viewPager2.setOffscreenPageLimit(1);
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

    @Override
    protected void whenNoHaveCouple() {
        alert.show("Bạn chưa có cặp đôi, vui lòng ghép đôi để sử dụng chức năng này", new Runnable() {
            @Override
            public void run() {
                toPage(findViewById(R.id.idBtnPageHome));
            }
        });
    }

    private void upateCal() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                int year = Integer.parseInt(inputYear.getText().toString());
                int month = Integer.parseInt(adapter.getItem(choseMonth.getSelectedItemPosition()));
                if (currentSelected == null)
                    currentSelected = LocalDate.now();
                CalendarTable.createCalendar((CalendarModels) baseModels, getBaseContext(), tableCal, currentSelected.getDayOfMonth(), month, year);
                tabLayout.getTabAt(0).setText(DateHelper.toDateString(currentSelected));
            } catch (Exception e) {}
        }
    }
}