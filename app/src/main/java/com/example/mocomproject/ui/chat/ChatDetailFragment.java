package com.example.mocomproject.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mocomproject.databinding.FragmentChatDetailBinding;

public class ChatDetailFragment extends Fragment {

    private FragmentChatDetailBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChatDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (getArguments() != null) {
            String question = getArguments().getString("question");

            TextView chatDetailTextView = binding.chatDetailTextView;
            chatDetailTextView.setText(question);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
