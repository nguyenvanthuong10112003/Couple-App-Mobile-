package com.example.myapplication.view;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import com.example.myapplication.R;
import com.example.myapplication.component.CalendarTable;
import com.example.myapplication.model.User;

import java.time.LocalDate;
public class CalendarActivityPage extends BasePageMainActivity {
    Spinner choseMonth;
    EditText inputYear;
    CalendarTable tableCal;
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
        choseMonth = (Spinner) findViewById(R.id.spinnerChoseMonth);
        tableCal = (CalendarTable) findViewById(R.id.idTableCal);
    }
    @Override
    protected void setting() {
        super.setting();
        String[] months = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, months);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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
                LocalDate firstOfMonth = LocalDate.of(today.getYear(), today.getMonthValue(), 1);

                CalendarTable.createCalendar(getBaseContext(), tableCal, firstOfMonth.getDayOfWeek(),
                        today.getDayOfMonth(),
                        today.getMonthValue(), today.getYear());
            }
        });
    }
}