package com.example.mocomproject.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mocomproject.data.model.FarmTechDetailItem;
import com.example.mocomproject.data.model.OrgnicFarmngDtlItem;
import com.example.mocomproject.data.model.OrgnicFarmngDtlResponse;
import com.example.mocomproject.data.model.OrgnicFarmngItem;
import com.example.mocomproject.data.model.OrgnicFarmngResponse;
import com.example.mocomproject.data.remote.ApiService;
import com.example.mocomproject.data.remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrganicViewModel extends ViewModel {
    private final ApiService orgnicFarmngApiService;
    private final MutableLiveData<String> mText1;
    private final MutableLiveData<String> mContent1;
    private final MutableLiveData<String> mDate1;
    private final MutableLiveData<String> mText2;
    private final MutableLiveData<String> mContent2;
    private final MutableLiveData<String> mDate2;
    private final MutableLiveData<String> mText3;
    private final MutableLiveData<String> mContent3;
    private final MutableLiveData<String> mDate3;

    public OrganicViewModel() {
        mText1 = new MutableLiveData<>();
        mContent1 = new MutableLiveData<>();
        mDate1 = new MutableLiveData<>();
        mText2 = new MutableLiveData<>();
        mContent2 = new MutableLiveData<>();
        mDate2 = new MutableLiveData<>();
        mText3 = new MutableLiveData<>();
        mContent3 = new MutableLiveData<>();
        mDate3 = new MutableLiveData<>();

        orgnicFarmngApiService = RetrofitClient.getOrgnicFarmngClient().create(ApiService.class);

        fetchOrgnicFarmngData();
    }

    private void fetchOrgnicFarmngData() {
        String apiKey = "202406198D0PCNVXCYJIPVU3TYF3W";

        orgnicFarmngApiService.getOrgnicFarmngLst(apiKey).enqueue(new Callback<OrgnicFarmngResponse>() {
            @Override
            public void onResponse(Call<OrgnicFarmngResponse> call, Response<OrgnicFarmngResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    OrgnicFarmngResponse orgnicFarmngResponse = response.body();
                    List<OrgnicFarmngItem> orgnicFarmngItems = orgnicFarmngResponse.getBody().getItems().getItemList();
                    if (orgnicFarmngItems != null && orgnicFarmngItems.size() >= 3) {
                        mText1.setValue(orgnicFarmngItems.get(0).getTitle());
                        mDate1.setValue(orgnicFarmngItems.get(0).getDate());
                        orgnicFarmngDetailContent(apiKey, orgnicFarmngItems.get(0).getCntntsNo(), mContent1);

                        mText2.setValue(orgnicFarmngItems.get(1).getTitle());
                        mDate2.setValue(orgnicFarmngItems.get(1).getDate());
                        orgnicFarmngDetailContent(apiKey, orgnicFarmngItems.get(1).getCntntsNo(), mContent2);

                        mText3.setValue(orgnicFarmngItems.get(2).getTitle());
                        mDate3.setValue(orgnicFarmngItems.get(2).getDate());
                        orgnicFarmngDetailContent(apiKey, orgnicFarmngItems.get(2).getCntntsNo(), mContent3);
                    }
                } else {
                    try {
                        System.out.println("Error: " + response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrgnicFarmngResponse> call, Throwable t) {
                System.out.println("Failure: " + t.getMessage());
            }
        });
    }

    private void orgnicFarmngDetailContent(String apiKey, String cntntsNo, MutableLiveData<String> contentLiveData) {
        orgnicFarmngApiService.getOrgnicFarmngDetail(apiKey, cntntsNo).enqueue(new Callback<OrgnicFarmngDtlResponse>() {
            @Override
            public void onResponse(Call<OrgnicFarmngDtlResponse> call, Response<OrgnicFarmngDtlResponse> response) {
                System.out.println(response.body());
                if (response.isSuccessful() && response.body() != null) {
                    OrgnicFarmngDtlResponse orgnicFarmngDtlResponse = response.body();
                    OrgnicFarmngDtlItem orgnicFarmngDtlItem = orgnicFarmngDtlResponse.getBody().getItem();
                    if (orgnicFarmngDtlItem != null) {
                        contentLiveData.setValue(orgnicFarmngDtlItem.geteContent());
                    }
                } else {
                    try {
                        System.out.println("Error: " + response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrgnicFarmngDtlResponse> call, Throwable t) {
                System.out.println("Failure: " + t.getMessage());
            }
        });
    }

    public LiveData<String> getText1() {
        return mText1;
    }

    public LiveData<String> getContent1() {
        return mContent1;
    }

    public LiveData<String> getDate1() {
        return mDate1;
    }

    public LiveData<String> getText2() {
        return mText2;
    }

    public LiveData<String> getContent2() {
        return mContent2;
    }

    public LiveData<String> getDate2() {
        return mDate2;
    }

    public LiveData<String> getText3() {
        return mText3;
    }

    public LiveData<String> getContent3() {
        return mContent3;
    }

    public LiveData<String> getDate3() {
        return mDate3;
    }
}
