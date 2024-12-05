package com.example.mocomproject.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.mocomproject.R;
import com.example.mocomproject.databinding.FragmentInfoBinding;

import java.util.ArrayList;
import java.util.Calendar;

public class InfoFragment extends Fragment {

    private FragmentInfoBinding binding;
    TextView dateText, sayText;
    Button btnWeek, btnWeather;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InfoViewModel infoViewModel =
                new ViewModelProvider(this).get(InfoViewModel.class);

        binding = FragmentInfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dateText = binding.todayText;
        sayText = binding.wiseSaying;
        btnWeek = binding.weekInfo;
        btnWeather = binding.weather;



        infoViewModel.getDateText().observe(getViewLifecycleOwner(), dateText::setText);
        infoViewModel.getSayText().observe(getViewLifecycleOwner(), sayText::setText);

        btnWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_infoFragment_to_MonthInfo);
            }
        });

        btnWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_infoFragment_to_Weather);
            }
        });

        return root;
    }
}