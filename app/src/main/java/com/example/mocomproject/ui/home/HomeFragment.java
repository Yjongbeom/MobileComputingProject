package com.example.mocomproject.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.mocomproject.Login.LoginMain;
import com.example.mocomproject.Login.RegisterView;
import com.example.mocomproject.MyDB;
import com.example.mocomproject.R;
import com.example.mocomproject.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private MyDB myDB;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        myDB = new MyDB(requireContext());

        Button todayInfoBtn = binding.todayInfo;
        ImageView img = binding.imageView;

        todayInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_infoFragment);
            }
        });

        int attendanceDays = myDB.getAttendanceDays();
        updateAttendanceImage(img, attendanceDays);

        return root;
    }

    private void updateAttendanceImage(ImageView imageView, int attendanceDays) {
        if (attendanceDays < 1) {
            imageView.setImageResource(R.drawable.start);
        } else if (attendanceDays < 2) {
            imageView.setImageResource(R.drawable.start2);
        } else if (attendanceDays < 3) {
            imageView.setImageResource(R.drawable.start3);
        } else if (attendanceDays < 4) {
            imageView.setImageResource(R.drawable.start4);
        } else {
            imageView.setImageResource(R.drawable.start5);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}