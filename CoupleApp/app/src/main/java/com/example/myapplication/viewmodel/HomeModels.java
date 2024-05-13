package com.example.myapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.define.DefineSharedPreferencesUserAuthen;
import com.example.myapplication.helper.DataLocalManager;
import com.example.myapplication.helper.HttpHelper;
import com.example.myapplication.model.Couple;
import com.example.myapplication.model.FarewellRequest;
import com.example.myapplication.model.User;
import com.example.myapplication.model.UserLogin;
import com.example.myapplication.repository.CoupleRepository;
import com.example.myapplication.repository.DateInvitationRepository;
import com.example.myapplication.repository.FarewellRequestRepository;
import com.example.myapplication.view.BasePage.BasePage;

import java.util.LinkedList;

import okhttp3.MultipartBody;

public class HomeModels extends BaseModels {
    private DateInvitationRepository dateInvitationRepository;
    private CoupleRepository coupleRepository;
    private FarewellRequestRepository farewellRequestRepository;
    private final MutableLiveData<LinkedList<User>> listUser = new MutableLiveData<>();
    private final MutableLiveData<String> nameSearch = new MutableLiveData<>();
    private final MutableLiveData<Couple> couple = new MutableLiveData<>();
    private final MutableLiveData<FarewellRequest> farewellRequestMutableLiveData = new MutableLiveData<>();
    public void initLiveList() {
        if (listUser.getValue() == null || listUser.getValue().size() == 0)
            dateInvitationRepository.getListUser().observeForever(list -> {
                if (list != null)
                    listUser.setValue(list);
            });
    }
    public void setCoupleLive(Couple couple) {this.couple.setValue(couple);}
    public HomeModels(@NonNull Application application) {
        super(application);
    }
    public MutableLiveData<String> getNameSearch() {
        return nameSearch;
    }
    public void setNameSearch(String nameSearch) {
        this.nameSearch.setValue(nameSearch);
    }
    public MutableLiveData<LinkedList<User>> getLiveList() {
        if (userLogin.getValue() == null)
            return null;
        return listUser;
    }
    public MutableLiveData<Couple> getLiveCouple() {
        if (userLogin.getValue() == null)
            return null;
        if (couple.getValue() == null)
            coupleRepository.getCoupleMutableLiveData().observeForever(this.couple::setValue);
        return couple;
    }
    public MutableLiveData<FarewellRequest> getFarewellRequestLiveData() {return farewellRequestRepository.getFarewellRequestMutableLiveData();}
    @Override
    public MutableLiveData<UserLogin> getUserLogin() {
        super.getUserLogin();
        if (userLogin.getValue() != null) {
            String token = HttpHelper.createToken(userLogin.getValue().getToken());
            dateInvitationRepository =
                new DateInvitationRepository(application, token);
            coupleRepository =
                new CoupleRepository(application, token);
            farewellRequestRepository =
                new FarewellRequestRepository(application, token);
            dateInvitationRepository.getDateInvitationAdd().observeForever(invite -> {
                if (invite == null || listUser.getValue() == null) return;
                LinkedList<User> list = listUser.getValue();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getId() == invite.getReceiverId()) {
                        list.get(i).setInvite(invite);
                        break;
                    }
                }
                listUser.setValue(list);
            });
            dateInvitationRepository.getDateInvitationDelete().observeForever(id -> {
                if (id == null || listUser.getValue() == null) return;
                LinkedList<User> list = listUser.getValue();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getInvite() != null && list.get(i).getInvite().getId() == id) {
                        list.get(i).setInvite(null);
                        break;
                    }
                }
                listUser.setValue(list);
            });
            dateInvitationRepository.getCouple().observeForever(couple::setValue);
        }
        return userLogin;
    }
    public void moiHenHo(int id) {dateInvitationRepository.guiLoiMoiHenHo(id);}
    public void huyMoiHenHo(int id) {dateInvitationRepository.huyLoiMoiHenHo(id);}
    public void phanHoiLoiMoiHenHo(int id, boolean isAccept) {dateInvitationRepository.phanHoiLoiMoi(id, isAccept);}
    public void changeBg(MultipartBody.Part image) {coupleRepository.changeBg(image);}
    public void yeuCauChiaTay() {farewellRequestRepository.create();}
    public void phanHoiYeuCauChiaTay(boolean isAccept) {farewellRequestRepository.feedBack(isAccept);}
}
