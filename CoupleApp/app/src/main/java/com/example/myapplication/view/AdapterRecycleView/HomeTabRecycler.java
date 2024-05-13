package com.example.myapplication.view.AdapterRecycleView;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.helper.DateHelper;
import com.example.myapplication.model.DateInvitation;
import com.example.myapplication.model.User;
import com.example.myapplication.model.UserLogin;
import com.example.myapplication.parcelable.UserParcelable;
import com.example.myapplication.view.PageChild.HomeDetailUser;
import com.example.myapplication.viewmodel.HomeModels;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class HomeTabRecycler {
    static final int VIEW_TYPE_ADD = 1;
    static final int VIEW_TYPE_ADDED = 2;
    static final int VIEW_TYPE_MIND = 3;
    static private class DIViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView imageAvt;
        TextView nameUser;
        TextView timeSend;
        ImageView iconGender;
        TextView btnAdd;
        TextView btnCancel;
        TextView btnTuChoi;
        TextView btnChapNhan;
        public DIViewHolder(@NonNull View itemView) {
            super(itemView);
            imageAvt = itemView.findViewById(R.id.idFragmentHomeIdImageAvatar);
            nameUser = itemView.findViewById(R.id.idFragmentHomeIdTextName);
            iconGender = itemView.findViewById(R.id.idFragmentHomeIdIconGender);
            timeSend = itemView.findViewById(R.id.idFragmentHomeTextTimeSend);
            btnAdd = itemView.findViewById(R.id.idFragmentHomeAdd);
            btnCancel = itemView.findViewById(R.id.idFragmentHomeCancel);
            btnTuChoi = itemView.findViewById(R.id.idFragmentHomeTuChoi);
            btnChapNhan = itemView.findViewById(R.id.idFragmentHomeAccept);
        }
    }
    public static class Adapter extends RecyclerView.Adapter<DIViewHolder> {
        UserLogin currentUser;
        Context context;
        List<User> list;
        HomeModels homeModels;
        String nameSearch;
        public Adapter(Context context, HomeModels homeModels) {
            this.context = context;
            this.homeModels = homeModels;
            homeModels.getUserLogin().observeForever(userLogin -> {
                currentUser = userLogin;
            });
            homeModels.getLiveList().observeForever(listData -> {
                this.setData(listData);
            });
            homeModels.getNameSearch().observeForever(nameSearch -> {
                this.nameSearch = nameSearch;
                try {
                    list = homeModels.getLiveList().getValue().stream().filter(item -> item.getFullName().startsWith(nameSearch)).collect(Collectors.toList());
                } catch (Exception e) {}
                notifyDataSetChanged();
            });
        }
        protected void setData(LinkedList<User> list) {
            if (nameSearch == null || nameSearch.isEmpty())
                this.list = list;
            else
                this.list = list.stream().filter(item -> item.getFullName().startsWith(nameSearch)).collect(Collectors.toList());
            notifyDataSetChanged();
        }
        @Override
        public int getItemViewType(int position) {
            User user = list.get(position);
            if (user.getInvite() == null)
                return VIEW_TYPE_ADD;
            else if (user.getInvite().getSenderId() == currentUser.getId())
                return VIEW_TYPE_ADDED;
            else
                return VIEW_TYPE_MIND;
        }

        @NonNull
        @Override
        public DIViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = null;
            if (viewType == VIEW_TYPE_ADD)
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_component_add, parent, false);
            else if (viewType == VIEW_TYPE_ADDED)
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_component_added, parent, false);
            else if (viewType == VIEW_TYPE_MIND)
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_component_feedback, parent, false);
            if (view == null) return null;
            return new DIViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DIViewHolder holder, int position) {
            if (holder == null)
                return;
            try {
                User user = list.get(position);
                holder.nameUser.setText(user.getFullName().trim().isEmpty() ? "<Chưa đặt tên>" : user.getFullName());
                holder.iconGender.setImageResource(user.getGender() ? R.drawable.ic_male : R.drawable.ic_female);
                if (holder.timeSend != null)
                    holder.timeSend.setText(DateHelper.demThoiGian(user.getInvite().getTimeSend()));
                try {
                    if (user.getUrlAvatar() != null && !user.getUrlAvatar().isEmpty()) {
                        Picasso.get().load(user.getUrlAvatar()).into(holder.imageAvt);
                    } else {
                        holder.imageAvt.setBackgroundResource(R.drawable.account_svgrepo_com);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    holder.imageAvt.setBackgroundResource(R.drawable.account_svgrepo_com);
                } finally {
                    holder.imageAvt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, HomeDetailUser.class);
                            intent.putExtra("data", new UserParcelable(user));
                            startActivity(context, intent, Bundle.EMPTY);
                        }
                    });
                }
                if (holder.btnAdd != null)
                    holder.btnAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            homeModels.moiHenHo(user.getId());
                        }
                    });
                if (holder.btnCancel != null)
                    holder.btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            homeModels.huyMoiHenHo(user.getInvite().getId());
                        }
                    });
                if (holder.btnTuChoi != null)
                    holder.btnTuChoi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            homeModels.phanHoiLoiMoiHenHo(user.getInvite().getId(), false);
                        }
                    });
                if (holder.btnChapNhan != null)
                    holder.btnChapNhan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            homeModels.phanHoiLoiMoiHenHo(user.getInvite().getId(), true);
                        }
                    });
            } catch (Exception e) {}
        }

        @Override
        public int getItemCount() {
            if (list == null || list.size() == 0)
                return 0;
            return list.size();
        }
    }
    public static class MindAdapter extends Adapter {

        public MindAdapter(Context context, HomeModels homeModels) {
            super(context, homeModels);
        }

        @Override
        public void setData(LinkedList<User> list) {
            try {
                this.list = new ArrayList<>();
               list.stream().forEach(item -> {
                   if(item.getInvite() != null && item.getInvite().getSenderId() != currentUser.getId() &&
                       (nameSearch == null || item.getFullName().startsWith(nameSearch)))
                       this.list.add(item);
               });
            } catch (Exception e) {}
            notifyDataSetChanged();
        }
    }
    public static class AddedAdapter extends Adapter {

        public AddedAdapter(Context context, HomeModels homeModels) {
            super(context, homeModels);
        }

        @Override
        protected void setData(LinkedList<User> list) {
            try {
                this.list = new ArrayList<>();
                list.stream().forEach(item -> {
                    if(item.getInvite() != null && item.getInvite().getSenderId() == currentUser.getId() &&
                        (nameSearch == null || item.getFullName().startsWith(nameSearch)))
                        this.list.add(item);
                });
            } catch (Exception e) {}
            notifyDataSetChanged();
        }
    }
}
