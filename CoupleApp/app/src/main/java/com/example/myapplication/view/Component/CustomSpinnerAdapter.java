package com.example.myapplication.view.Component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private List<String> mData;
    private boolean isOn = false;
    public CustomSpinnerAdapter(Context context, int resource, List<String> data) {
        super(context, resource, data);
        mContext = context;
        mData = data;
    }
    public boolean isOpen() {
        return isOn;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.spinner_item_selected, parent, false);
        TextView tvSelectd = convertView.findViewById(R.id.idSpinnerSelected);
        String item = this.getItem(position);
        if (item != null && !item.isEmpty())
            tvSelectd.setText(item);
        isOn = false;
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.spinner_dropdown_item, parent, false);
        TextView tvList = convertView.findViewById(R.id.idSpinnerItem);
        String item = this.getItem(position);
        if (item != null && !item.isEmpty())
            tvList.setText(item);
        if (!isOn)
            isOn = true;
        return convertView;
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        return convertView;
    }
}
