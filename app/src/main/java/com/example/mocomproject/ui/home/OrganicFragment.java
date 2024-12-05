package com.example.mocomproject.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.mocomproject.R;
import com.example.mocomproject.databinding.FragmentWeatherBinding;

public class OrganicFragment extends Fragment {

    private FragmentWeatherBinding binding;
    Button btn1, btn2, btn3;
    TextView dateText1, dateText2, dateText3;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        OrganicViewModel organicViewModel =
                new ViewModelProvider(this).get(OrganicViewModel.class);

        binding = FragmentWeatherBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btn1 = binding.btn1;
        btn2 = binding.btn2;
        btn3 = binding.btn3;

        dateText1 = binding.text1;
        dateText2 = binding.text2;
        dateText3 = binding.text3;

        organicViewModel.getText1().observe(getViewLifecycleOwner(), btn1::setText);
        organicViewModel.getText2().observe(getViewLifecycleOwner(), btn2::setText);
        organicViewModel.getText3().observe(getViewLifecycleOwner(), btn3::setText);

        organicViewModel.getDate1().observe(getViewLifecycleOwner(), dateText1::setText);
        organicViewModel.getDate2().observe(getViewLifecycleOwner(), dateText2::setText);
        organicViewModel.getDate3().observe(getViewLifecycleOwner(), dateText3::setText);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("detailTitle", organicViewModel.getText1().getValue());
                bundle.putString("detailText", organicViewModel.getContent1().getValue());
                Navigation.findNavController(root).navigate(R.id.action_weather_to_detail, bundle);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("detailTitle", organicViewModel.getText2().getValue());
                bundle.putString("detailText", organicViewModel.getContent2().getValue());
                Navigation.findNavController(root).navigate(R.id.action_weather_to_detail, bundle);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("detailTitle", organicViewModel.getText3().getValue());
                bundle.putString("detailText", organicViewModel.getContent3().getValue());
                Navigation.findNavController(root).navigate(R.id.action_weather_to_detail, bundle);
            }
        });

        return root;
    }
}
