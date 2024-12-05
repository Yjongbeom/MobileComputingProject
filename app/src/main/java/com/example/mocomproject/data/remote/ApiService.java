package com.example.mocomproject.data.remote;

import com.example.mocomproject.data.model.FarmTechDetailResponse;
import com.example.mocomproject.data.model.FarmTechGuideResponse;
import com.example.mocomproject.data.model.FarmTechResponse;
import com.example.mocomproject.data.model.OrgnicFarmngDtlResponse;
import com.example.mocomproject.data.model.OrgnicFarmngResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    // FarmTech API
    @GET("monthFarmTechLst")
    Call<FarmTechResponse> getFarmTechList(
            @Query("apiKey") String apiKey,
            @Query("srchStr") String searchKeyword,
            @Query("sEraInfo") String eraInfo,
            @Query("pageNo") int pageNo,
            @Query("numOfRows") int numOfRows
    );

    @GET("monthFarmTechDtlGuideLst")
    Call<FarmTechGuideResponse> getFarmGuideList(
            @Query("apiKey") String apiKey,
            @Query("srchCurationNo") String srchCurationNo
    );

    @GET("monthFarmTechDtl")
    Call<FarmTechDetailResponse> getFarmTechDtl(
            @Query("apiKey") String apiKey,
            @Query("srchCurationNo") String srchCurationNo,
            @Query("srchCntntsSnn") String srchCntntsSnn,
            @Query("appendDomainAt") String appendDomainAt
    );

    // OrgnicFarmng API
    @GET("orgnicFarmngDtl")
    Call<OrgnicFarmngDtlResponse> getOrgnicFarmngDetail(
            @Query("apiKey") String apiKey,
            @Query("cntntsNo") String cntntsNo
    );

    @GET("orgnicFarmngLst")
    Call<OrgnicFarmngResponse> getOrgnicFarmngLst(
            @Query("apiKey") String apiKey
    );
}
