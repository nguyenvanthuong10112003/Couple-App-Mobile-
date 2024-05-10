package com.example.myapplication.view.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.helper.Converter;
import com.example.myapplication.helper.DateHelper;
import com.example.myapplication.model.Schedule;
import com.example.myapplication.model.User;
import com.example.myapplication.model.UserLogin;
import com.example.myapplication.parcelable.UserParcelable;
import com.example.myapplication.view.AdapterRecycleView.CalendarTabRecycler;
import com.example.myapplication.view.BasePage.BasePage;
import com.example.myapplication.view.PageChild.HomeDetailUser;
import com.example.myapplication.viewmodel.CalendarModels;
import com.example.myapplication.viewmodel.HomeModels;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class CalendarTodayFragment extends Fragment {
    private CalendarModels calendarModels;
    private RecyclerView recyclerView;
    private CalendarTabRecycler.AdapterToday calTodayAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        calendarModels = new ViewModelProvider(requireActivity()).get(CalendarModels.class);
        recyclerView = view.findViewById(R.id.idPageCalFragmentRecyclerView);
        calTodayAdapter = new CalendarTabRecycler.AdapterToday(getContext(), calendarModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(calTodayAdapter);
        return view;
    }
}