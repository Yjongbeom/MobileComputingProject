package com.example.mocomproject.ui.settings;

import static android.os.Build.VERSION_CODES.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mocomproject.MyDB;
import com.example.mocomproject.databinding.FragmentSettingsBinding;
import com.example.mocomproject.ui.home.HomeViewModel;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;
    private MyDB myDB;
    private String currentUserId;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        myDB = new MyDB(getContext());

        currentUserId = myDB.getCurrentUserId();

        CheckBox checkBoxMentor = binding.check1;
        boolean isMentorEnabled = myDB.isMentorEnabled(currentUserId);
        checkBoxMentor.setChecked(isMentorEnabled);

        checkBoxMentor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                myDB.updateMentorStatus(currentUserId, isChecked);
                String message;
                if(isChecked)
                    message = "멘토 기능이 활성화되었습니다.";
                else
                    message = "멘토 기능이 비활성화되었습니다.";
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
