package com.example.myapplication.view.PageMain;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.viewpager2.widget.ViewPager2;
import com.example.myapplication.R;
import com.example.myapplication.helper.DateHelper;
import com.example.myapplication.view.Component.CalendarTable;
import com.example.myapplication.view.Component.CustomSpinnerAdapter;
import com.example.myapplication.helper.SlideByMarginAnimation;
import com.example.myapplication.view.BasePage.BasePageMainActivity;
import com.example.myapplication.view.ViewPager.CalendarViewPagerAdapter;
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
    ImageButton btnGiamNam;
    ImageButton btnTangNam;
    LocalDate currentSelected;
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
        btnGiamNam = findViewById(R.id.idPageCalendarBtnDownYear);
        btnTangNam = findViewById(R.id.idPageCalendarBtnUpYear);
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

                            Animation slideUp = new SlideByMarginAnimation(viewSelectDate, 0, -viewSelectDate.getHeight(), 100);
                            slideUp.setInterpolator(new AccelerateDecelerateInterpolator());
                            viewSelectDate.startAnimation(slideUp);
                        }
                    }
                    else
                    //kéo xuống
                    if (startY < event.getY() && Math.abs(startY - event.getY()) > Math.abs(startX - event.getX())) {
                        if (scrollViewPager2.getScrollY() == 0 && isOpen) {
                            isOpen = false;

                            Animation slideDown = new SlideByMarginAnimation(viewSelectDate, -viewSelectDate.getHeight(), 0, 100);
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
        tableCal.getSelectedItem().observe(this, view -> {
            if (view != null)
                try {
                    int year = Integer.parseInt(inputYear.getText().toString());
                    int month = Integer.parseInt(adapter.getItem(choseMonth.getSelectedItemPosition()));
                    currentSelected = LocalDate.of(year, month, Integer.parseInt(((TextView) view).getText().toString()));
                    tabLayout.getTabAt(0).setText(DateHelper.toDateString(currentSelected));
                } catch (Exception e) {}
        });
    }
    private void upateCal() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                int year = Integer.parseInt(inputYear.getText().toString());
                int month = Integer.parseInt(adapter.getItem(choseMonth.getSelectedItemPosition()));
                if (currentSelected == null)
                    currentSelected = LocalDate.now();
                CalendarTable.createCalendar(getBaseContext(), tableCal, currentSelected.getDayOfMonth(), month, year);
                tabLayout.getTabAt(0).setText(DateHelper.toDateString(currentSelected));
            } catch (Exception e) {}
        }
    }
    private void onChangeTime() {
        try {
            int year = Integer.parseInt(inputYear.getText().toString());
            int day = -1;
            for (int i = 1; i < tableCal.getChildCount(); i++)
            {
                TableRow row = (TableRow) tableCal.getChildAt(i);
                for (int j = 0; j < row.getChildCount(); j++) {
                    TextView item = (TextView) row.getChildAt(j);
                }
            }
        } catch (Exception e) {}
    }
}