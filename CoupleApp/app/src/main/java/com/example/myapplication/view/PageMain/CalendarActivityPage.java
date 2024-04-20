package com.example.myapplication.view.PageMain;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.R;
import com.example.myapplication.component.CalendarTable;
import com.example.myapplication.component.CustomSpinnerAdapter;
import com.example.myapplication.helper.SlideByMarginAnimation;
import com.example.myapplication.view.BasePage.BasePageMainActivity;
import com.example.myapplication.view.ViewPager.CalendarViewPagerAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
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
    ScrollView scrollViewPager2;
    ViewGroup viewSelectDate;
    ViewGroup viewBottom;
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
        scrollViewPager2 = findViewById(R.id.idPageCalendarScrollViewPager2);
        viewSelectDate = findViewById(R.id.idPageCalendarViewSelectDate);
        viewBottom = findViewById(R.id.idPageCalendarView);
    }

    @Override
    protected boolean startFocus(ViewGroup view) {
        return true;
    }

    @Override
    protected boolean isBusy() {
        return super.isBusy() || ((CustomSpinnerAdapter)choseMonth.getAdapter()).isOpen();
    }
    @SuppressLint("ClickableViewAccessibility")
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
        scrollViewPager2.setOnTouchListener(new View.OnTouchListener() {
            private float startY;
            private float startX;
            private boolean isRuning = false;
            private int heightSelectDate;
            private boolean isOpen = false;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    this.startY = event.getY();
                    this.startX = event.getX();
                }
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    //kéo lên
                    if (event.getY() < startY && Math.abs(startY - event.getY()) > Math.abs(startX - event.getX())) {
                        if (scrollViewPager2.getScrollY() == 0 && !isOpen) {
                            isOpen = true;
                            heightSelectDate = viewSelectDate.getHeight();

                            Animation slideUp = new SlideByMarginAnimation(viewSelectDate, 0, -viewSelectDate.getHeight(), 200);
                            slideUp.setInterpolator(new AccelerateDecelerateInterpolator());
                            viewSelectDate.startAnimation(slideUp);
                        }
                    }
                    else
                    //kéo xuống
                    if (startY < event.getY() && Math.abs(startY - event.getY()) > Math.abs(startX - event.getX())) {
                        if (scrollViewPager2.getScrollY() == 0 && isOpen) {
                            isOpen = false;

                            Animation slideDown = new SlideByMarginAnimation(viewSelectDate, -viewSelectDate.getHeight(), 0, 200);
                            slideDown.setInterpolator(new AccelerateDecelerateInterpolator());
                            viewSelectDate.startAnimation(slideDown);
                        }
                    }
                return false;
            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
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
                LocalDate firstOfMonth = LocalDate.of(today.getYear(), today.getMonthValue(), 1);
                CalendarTable.createCalendar(getBaseContext(), tableCal, firstOfMonth.getDayOfWeek(),
                        today.getDayOfMonth(),
                        today.getMonthValue(), today.getYear());
            }
        });
    }
}