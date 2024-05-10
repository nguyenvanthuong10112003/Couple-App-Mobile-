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
import com.example.myapplication.view.AdapterRecycleView.HomeTabRecycler;
import com.example.myapplication.viewmodel.HomeModels;

public class HomeMindFragment extends Fragment {
    private HomeModels homeModels;
    private HomeTabRecycler.MindAdapter mindAdapter;
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        homeModels = new ViewModelProvider(requireActivity()).get(HomeModels.class);
        recyclerView = view.findViewById(R.id.recycleList);
        mindAdapter = new HomeTabRecycler.MindAdapter(getContext(), homeModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(mindAdapter);
        return view;
    }
}