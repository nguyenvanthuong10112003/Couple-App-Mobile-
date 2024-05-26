package com.example.myapplication.view.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.view.AdapterRecycleView.CalendarTabRecycler;
import com.example.myapplication.viewmodel.CalendarModels;

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