package com.example.mocomproject.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mocomproject.data.model.FarmTechDetailItem;
import com.example.mocomproject.data.model.FarmTechDetailResponse;
import com.example.mocomproject.data.model.FarmTechGuideItem;
import com.example.mocomproject.data.model.FarmTechGuideResponse;
import com.example.mocomproject.data.model.FarmTechItem;
import com.example.mocomproject.data.model.FarmTechResponse;
import com.example.mocomproject.data.remote.ApiService;
import com.example.mocomproject.data.remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MonthInfoViewModel extends ViewModel {

    private final MutableLiveData<String> mText1;
    private final MutableLiveData<String> mContent1;
    private final MutableLiveData<String> mDate1;
    private final MutableLiveData<String> mText2;
    private final MutableLiveData<String> mContent2;
    private final MutableLiveData<String> mDate2;
    private final MutableLiveData<String> mText3;
    private final MutableLiveData<String> mContent3;
    private final MutableLiveData<String> mDate3;

    private final ApiService farmTechApiService;

    public MonthInfoViewModel() {
        mText1 = new MutableLiveData<>();
        mContent1 = new MutableLiveData<>();
        mDate1 = new MutableLiveData<>();
        mText2 = new MutableLiveData<>();
        mContent2 = new MutableLiveData<>();
        mDate2 = new MutableLiveData<>();
        mText3 = new MutableLiveData<>();
        mContent3 = new MutableLiveData<>();
        mDate3 = new MutableLiveData<>();

        farmTechApiService = RetrofitClient.getFarmTechClient().create(ApiService.class);

        fetchFarmTechData();
    }


    private void fetchFarmTechData() {
        String apiKey = "2024061870WH3TVCDEYAFVARDTOFQ";
        String searchKeyword = ""; // 검색어
        String eraInfo = ""; // 기간검색값
        int pageNo = 1; // 페이지 번호
        int numOfRows = 3; // 한 페이지에 가져올 건수

        farmTechApiService.getFarmTechList(apiKey, searchKeyword, eraInfo, pageNo, numOfRows)
                .enqueue(new Callback<FarmTechResponse>() {
                    @Override
                    public void onResponse(Call<FarmTechResponse> call, Response<FarmTechResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            FarmTechResponse farmTechResponse = response.body();
                            List<FarmTechItem> farmTechItems = farmTechResponse.getBody().getItems().getItemList();
                            if (farmTechItems != null && farmTechItems.size() >= 3) {
                                mText1.setValue(farmTechItems.get(0).getTitle());
                                mDate1.setValue(farmTechItems.get(0).getServiceDate());
                                fetchGuideList(apiKey, farmTechItems.get(0).getCurationNo(), mContent1);

                                mText2.setValue(farmTechItems.get(1).getTitle());
                                mDate2.setValue(farmTechItems.get(1).getServiceDate());
                                fetchGuideList(apiKey, farmTechItems.get(1).getCurationNo(), mContent2);

                                mText3.setValue(farmTechItems.get(2).getTitle());
                                mDate3.setValue(farmTechItems.get(2).getServiceDate());
                                fetchGuideList(apiKey, farmTechItems.get(2).getCurationNo(), mContent3);
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
                    public void onFailure(Call<FarmTechResponse> call, Throwable t) {
                        System.out.println("Failure: " + t.getMessage());
                    }
                });
    }


    private void fetchGuideList(String apiKey, String srchCurationNo, MutableLiveData<String> contentLiveData) {
        farmTechApiService.getFarmGuideList(apiKey, srchCurationNo)
                .enqueue(new Callback<FarmTechGuideResponse>() {
                    @Override
                    public void onResponse(Call<FarmTechGuideResponse> call, Response<FarmTechGuideResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            FarmTechGuideResponse guideResponse = response.body();
                            List<FarmTechGuideItem> guideItems = guideResponse.getBody().getItems().getItemList();
                            if (guideItems != null && !guideItems.isEmpty()) {
                                String srchCntntsSnn = guideItems.get(0).getCntntsSnn();
                                fetchDetailContent(apiKey, srchCurationNo, srchCntntsSnn, contentLiveData);
                            } else {
                                System.out.println("Error: guide itemList is null or empty");
                                contentLiveData.setValue("No guide content available");
                            }
                        } else {
                            try {
                                System.out.println("Error: " + response.errorBody().string());
                                contentLiveData.setValue("Error: " + response.errorBody().string());
                            } catch (Exception e) {
                                e.printStackTrace();
                                contentLiveData.setValue("Error parsing error body");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<FarmTechGuideResponse> call, Throwable t) {
                        System.out.println("Failure: " + t.getMessage());
                        contentLiveData.setValue("Failure: " + t.getMessage());
                    }
                });
    }


    private void fetchDetailContent(String apiKey, String srchCurationNo, String srchCntntsSnn, MutableLiveData<String> contentLiveData) {
        farmTechApiService.getFarmTechDtl(apiKey, srchCurationNo, srchCntntsSnn, "Y")
                .enqueue(new Callback<FarmTechDetailResponse>() {
                    @Override
                    public void onResponse(Call<FarmTechDetailResponse> call, Response<FarmTechDetailResponse> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().getBody() != null && response.body().getBody().getItem() != null) {
                            FarmTechDetailResponse detailResponse = response.body();
                            FarmTechDetailItem farmTechDetailItem = detailResponse.getBody().getItem();
                            if (farmTechDetailItem != null) {
                                contentLiveData.setValue(farmTechDetailItem.getCntntsInfo());
                            } else {
                                contentLiveData.setValue("No detail content available");
                            }
                        } else {
                            try {
                                if (response.errorBody() != null) {
                                    System.out.println("Error: " + response.errorBody().string());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<FarmTechDetailResponse> call, Throwable t) {
                        System.out.println("Failure: " + t.getMessage());
                        contentLiveData.setValue("Failure: " + t.getMessage());
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
