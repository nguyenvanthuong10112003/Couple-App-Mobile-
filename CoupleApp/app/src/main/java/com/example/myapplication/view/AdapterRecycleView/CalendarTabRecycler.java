package com.example.myapplication.view.AdapterRecycleView;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.helper.DateHelper;
import com.example.myapplication.model.Schedule;
import com.example.myapplication.model.User;
import com.example.myapplication.model.UserLogin;
import com.example.myapplication.parcelable.UserParcelable;
import com.example.myapplication.view.BasePage.BasePage;
import com.example.myapplication.view.Component.Button;
import com.example.myapplication.view.PageChild.HomeDetailUser;
import com.example.myapplication.viewmodel.CalendarModels;
import com.example.myapplication.viewmodel.HomeModels;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CalendarTabRecycler {
    static final int VIEW_TYPE_MIND = 1;
    static final int VIEW_TYPE_ENEMY = 2;
    static private class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView textNote;
        TextView textTitle;
        TextView textDate;
        TextView textTime;
        TextView textContent;
        Button buttonAccept;
        Button buttonCancel;
        TextView buttonCanceled;
        TextView buttonAccepted;
        View btnDelete;
        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            textNote = itemView.findViewById(R.id.idPageCalFragmentTextNote);
            textTitle = itemView.findViewById(R.id.idPageCalFragmentTextTitle);
            textDate = itemView.findViewById(R.id.idPageCalFragmentTextDate);
            textTime = itemView.findViewById(R.id.idPageCalFragmentTextTime);
            textContent = itemView.findViewById(R.id.idPageCalFragmentTextContent);
            buttonAccept = itemView.findViewById(R.id.idPageCalendarFragmentButtonAccept);
            buttonCancel = itemView.findViewById(R.id.idPageCalendarFragmentButtonCancel);
            buttonAccepted = itemView.findViewById(R.id.idPageCalendarFragmentButtonAccepted);
            buttonCanceled = itemView.findViewById(R.id.idPageCalendarFragmentButtonCanceled);
            btnDelete = itemView.findViewById(R.id.idPageCalFragmentButtonDelete);
        }
    }
    static public class Adapter extends RecyclerView.Adapter<ScheduleViewHolder> {
        UserLogin currentUser;
        Context context;
        List<Schedule> list;
        CalendarModels calendarModels;
        public Adapter(Context context, CalendarModels calendarModels) {
            this.context = context;
            this.calendarModels = calendarModels;
            this.calendarModels.getUserLogin().observeForever(userLogin -> this.currentUser = userLogin);
            this.calendarModels.getLiveList().observeForever(list -> setData(list));
        }
        protected void setData(LinkedList<Schedule> list) {
            this.list = list;
            notifyDataSetChanged();
        }
        @Override
        public int getItemViewType(int position) {
            Schedule schedule = list.get(position);
            if (schedule == null)
                return -1;
            if (schedule.getSenderId() == currentUser.getId())
                return VIEW_TYPE_MIND;
            else
                return VIEW_TYPE_ENEMY;
        }

        @NonNull
        @Override
        public CalendarTabRecycler.ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = null;
            if (viewType == VIEW_TYPE_MIND)
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_calendar_component_mind, parent, false);
            else if (viewType == VIEW_TYPE_ENEMY)
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_calendar_component_enemy, parent, false);
            if (view == null) return null;
            return new CalendarTabRecycler.ScheduleViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CalendarTabRecycler.ScheduleViewHolder holder, int position) {
            if (holder == null)
                return;
            try {
                Schedule schedule = list.get(position);
                if (schedule.getSenderId() == currentUser.getId()) {
                    holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((BasePage) context).getAlert().show(
                                "Bạn có chắc chắn muốn xóa lịch trình này chứ?", "Hủy bỏ",
                                "Đồng ý", null,
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        calendarModels.delete(schedule.getId());
                                    }
                                });
                        }
                    });
                    if (schedule.getTimeFeedBack() == null) {
                        holder.textNote.setText("Đang chờ phản hồi");
                        holder.textNote.setTextColor(ContextCompat.getColor(context, R.color.primary));
                    } else if (schedule.isAccept()) {
                        holder.textNote.setText("Đối phương đồng ý");
                        holder.textNote.setTextColor(ContextCompat.getColor(context, R.color.success));
                    } else {
                        holder.textNote.setText("Đối phương từ chối");
                        holder.textNote.setTextColor(ContextCompat.getColor(context, R.color.secondary));
                    }
                } else {
                    holder.buttonAccept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            calendarModels.feedBack(schedule.getId(), false);
                        }
                    });
                    holder.buttonAccepted.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            calendarModels.feedBack(schedule.getId(), true);
                        }
                    });
                    if (schedule.getTimeFeedBack() != null) {
                        if (schedule.isAccept()) {
                            holder.buttonAccepted.setVisibility(View.VISIBLE);
                            holder.buttonAccept.setVisibility(View.INVISIBLE);
                        } else {
                            holder.buttonCanceled.setVisibility(View.VISIBLE);
                            holder.buttonCancel.setVisibility(View.INVISIBLE);
                        }
                    }
                    if (schedule.isDeleted()) {
                        holder.textNote.setText("Đối phương đã xóa");
                        holder.textNote.setTextColor(ContextCompat.getColor(context, R.color.danger));
                    } else {
                        holder.textNote.setText("Đối phương đã tạo");
                        holder.textNote.setTextColor(ContextCompat.getColor(context, R.color.blue));
                    }
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        LocalDateTime now = LocalDateTime.now();
                        if (DateHelper.toLocalDateTime(schedule.getTime()).compareTo(now) <= 0 || schedule.isDeleted()) {
                            holder.buttonAccept.setClickable(false);
                            holder.buttonCancel.setClickable(false);
                            holder.buttonAccept.setAlpha(0.6f);
                            holder.buttonCancel.setAlpha(0.6f);
                        }
                    }
                }
                holder.textTitle.setText(schedule.getTitle());
                holder.textContent.setText(
                        "Nội dung: " + (schedule.getContent().trim().isEmpty() ? "Không có" : schedule.getContent()));
                holder.textDate.setText(DateHelper.toDateString(schedule.getTime()));
                holder.textTime.setText(DateHelper.toTimeString(schedule.getTime()));
            } catch (Exception e) {}
        }

        @Override
        public int getItemCount() {
            if (list == null || list.size() == 0)
                return 0;
            return list.size();
        }
    }
    static public class AdapterToday extends Adapter {
        LocalDate currentDate;
        public AdapterToday(Context context, CalendarModels calendarModels) {
            super(context, calendarModels);
            calendarModels.getLiveSelectedDate().observeForever(day -> {currentDate = day; setData(calendarModels.getLiveList().getValue());});
        }

        @Override
        protected void setData(LinkedList<Schedule> list) {
            this.list = new ArrayList<>();
            try {
                list.stream().forEach(item -> {
                    if (DateHelper.toLocalDate(item.getTime()).equals(currentDate))
                        this.list.add(item);
                });
                notifyDataSetChanged();
            } catch (Exception e) {}
        }

        @Override
        public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
            super.onBindViewHolder(holder, position);
            ((View)holder.textDate.getParent()).setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0));
        }
    }
    static public class AdapterComingUp extends Adapter {

        public AdapterComingUp(Context context, CalendarModels calendarModels) {
            super(context, calendarModels);
        }

        @Override
        protected void setData(LinkedList<Schedule> list) {
            this.list = new ArrayList<>();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalDate now = LocalDate.now();
                try {
                    list.stream().forEach(item -> {
                        if (DateHelper.toLocalDate(item.getTime()).compareTo(now) > 0)
                            this.list.add(item);
                    });
                } catch (Exception e) {}
                notifyDataSetChanged();
            }
        }
    }
}
