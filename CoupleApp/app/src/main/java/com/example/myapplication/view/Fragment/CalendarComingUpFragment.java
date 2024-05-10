package com.example.myapplication.view.Fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.helper.Converter;
import com.example.myapplication.helper.DateHelper;
import com.example.myapplication.model.Schedule;
import com.example.myapplication.model.UserLogin;
import com.example.myapplication.view.AdapterRecycleView.CalendarTabRecycler;
import com.example.myapplication.view.BasePage.BasePage;
import com.example.myapplication.viewmodel.CalendarModels;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class CalendarComingUpFragment extends Fragment {
    private CalendarModels calendarModels;
    private RecyclerView recyclerView;
    private CalendarTabRecycler.AdapterComingUp calComingUpAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        calendarModels = new ViewModelProvider(requireActivity()).get(CalendarModels.class);
        recyclerView = view.findViewById(R.id.idPageCalFragmentRecyclerView);
        calComingUpAdapter = new CalendarTabRecycler.AdapterComingUp(getContext(), calendarModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(calComingUpAdapter);
        return view;
    }
}