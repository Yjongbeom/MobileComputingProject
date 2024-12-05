package com.example.mocomproject.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mocomproject.MyDB;
import com.example.mocomproject.R;

public class MentorRequestFragment extends Fragment {

    private EditText editTextQuestion;
    private Button buttonNext;
    private MyDB myDB;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mentor_request, container, false);

        editTextQuestion = root.findViewById(R.id.editTextQuestion);
        buttonNext = root.findViewById(R.id.buttonNext);

        myDB = new MyDB(requireContext());

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = editTextQuestion.getText().toString();
                if (!question.isEmpty()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("question", question);
                    Navigation.findNavController(v).navigate(R.id.action_navigation_request_to_select_mentor, bundle);
                }
            }
        });

        return root;
    }
}

