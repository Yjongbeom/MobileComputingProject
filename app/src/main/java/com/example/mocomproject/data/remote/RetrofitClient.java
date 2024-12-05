package com.example.mocomproject.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RetrofitClient {
    private static Retrofit farmTechRetrofit;
    private static Retrofit orgnicFarmngRetrofit;
    private static final String FARM_TECH_BASE_URL = "http://api.nongsaro.go.kr/service/monthFarmTech/";
    private static final String ORGNIC_FARMNG_BASE_URL = "http://api.nongsaro.go.kr/service/orgnicFarmng/";

    public static Retrofit getFarmTechClient() {
        if (farmTechRetrofit == null) {
            farmTechRetrofit = new Retrofit.Builder()
                    .baseUrl(FARM_TECH_BASE_URL)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();
        }
        return farmTechRetrofit;
    }

    public static Retrofit getOrgnicFarmngClient() {
        if (orgnicFarmngRetrofit == null) {
            orgnicFarmngRetrofit = new Retrofit.Builder()
                    .baseUrl(ORGNIC_FARMNG_BASE_URL)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();
        }
        return orgnicFarmngRetrofit;
    }

}
