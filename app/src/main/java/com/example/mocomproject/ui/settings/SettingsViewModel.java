package com.example.mocomproject.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {
    private final MutableLiveData<String> mText1;
    private final MutableLiveData<String> mText2;
    private final MutableLiveData<String> mText3;
    private final MutableLiveData<String> mText4;
    public SettingsViewModel() {
        mText1 = new MutableLiveData<>();
        mText2 = new MutableLiveData<>();
        mText3 = new MutableLiveData<>();
        mText4 = new MutableLiveData<>();

        mText1.setValue("ex1");
        mText1.setValue("ex2");
        mText1.setValue("ex3");
        mText1.setValue("ex4");

    }

    public LiveData<String> getText1() {
        return mText1;
    }
    public LiveData<String> getText2() {
        return mText2;
    }
    public LiveData<String> getText3() {
        return mText3;
    }
    public LiveData<String> getText4() {
        return mText4;
    }
}
