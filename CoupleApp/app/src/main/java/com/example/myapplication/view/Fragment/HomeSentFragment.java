package com.example.myapplication.view.Fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.helper.Converter;
import com.example.myapplication.helper.DateHelper;
import com.example.myapplication.helper.HttpHelper;
import com.example.myapplication.model.User;
import com.example.myapplication.model.UserLogin;
import com.example.myapplication.parcelable.UserParcelable;
import com.example.myapplication.view.PageChild.HomeDetailUser;
import com.example.myapplication.repository.DateInvitationRepository;
import com.example.myapplication.viewmodel.HomeModels;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;

public class HomeSentFragment extends Fragment {
    private LinkedList<User> datas;
    private UserLogin currentUser;
    private HomeModels homeModels;
    private String nameSearch;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        nameSearch = "";
        View view = inflater.inflate(R.layout.fragment_home_sent, container, false);
        homeModels = new ViewModelProvider(requireActivity()).get(HomeModels.class);
        homeModels.getUserLogin().observe(getViewLifecycleOwner(), userLogin -> {
            this.currentUser = userLogin;
        });
        LinearLayout group = view.findViewById(R.id.idFragmentHomeSentContainer);
        homeModels.getLiveList().observe(getViewLifecycleOwner(), listData -> {
            datas = listData;
            setup(group);
        });
        homeModels.getNameSearch().observe(getViewLifecycleOwner(), nameSearch -> {
            this.nameSearch = nameSearch;
            setup(group);
        });
        return view;
    }
    private void setup(LinearLayout container) {
        if (datas == null || datas.size() == 0 || currentUser == null) {
            return;
        }
        container.removeAllViews();
        float scale = getResources().getDisplayMetrics().density;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int) Converter.dpToPx(scale, 0.5f)
        ));
        for (User user : datas) {
            if (!(nameSearch == null || nameSearch.isEmpty() || user.getFullName() == null ||
                    user.getFullName().isEmpty() ||
                    user.getFullName().startsWith(nameSearch)) || user.getInvite() == null ||
                    user.getInvite().getSenderId() != currentUser.getId())
                continue;
            View viewChild = LayoutInflater.from(getContext())
                .inflate(R.layout.fragment_home_component_added, container, false);
            ((TextView)viewChild.findViewById(R.id.idFragmentHomeIdTextName)).setText(
                user.getFullName() != null && !user.getFullName().isEmpty() ? user.getFullName() : "<Chưa đặt tên>");
            ShapeableImageView imageAvatar = viewChild.findViewById(R.id.idFragmentHomeIdImageAvatar);
            try {
                if (user.getUrlAvatar() != null && !user.getUrlAvatar().isEmpty()) {
                    Picasso.get().load(user.getUrlAvatar()).into(imageAvatar);
                } else {
                    imageAvatar.setBackgroundResource(R.drawable.account_svgrepo_com);
                }
            } catch (Exception e) {
                e.printStackTrace();
                imageAvatar.setBackgroundResource(R.drawable.account_svgrepo_com);
            } finally {
                imageAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), HomeDetailUser.class);
                        intent.putExtra("data", new UserParcelable(user));
                        startActivity(intent);
                    }
                });
            }
            ((ImageView)viewChild.findViewById(R.id.idFragmentHomeIdIconGender))
                    .setImageDrawable(ContextCompat.getDrawable(getContext(), user.getGender() ? R.drawable.ic_male : R.drawable.ic_female));
            TextView timeSend = viewChild.findViewById(R.id.idFragmentHomeTextTimeSend);
            if (timeSend != null && user.getInvite().getTimeSend() != null)
                timeSend.setText(DateHelper.demThoiGian(user.getInvite().getTimeSend()));
            container.addView(viewChild);
            View nganCach = new View(getContext());
            nganCach.setLayoutParams(layoutParams);
            nganCach.setBackground(new ColorDrawable(ContextCompat.getColor(getContext(), R.color.secondary)));
            container.addView(nganCach);
            TextView btnCancel = viewChild.findViewById(R.id.idFragmentHomeCancel);
            if (btnCancel != null)
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        homeModels.huyMoiHenHo(user.getInvite().getId());
                    }
                });
        }
    }
}