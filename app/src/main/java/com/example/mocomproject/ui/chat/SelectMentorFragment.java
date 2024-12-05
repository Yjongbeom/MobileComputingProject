package com.example.mocomproject.ui.chat;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mocomproject.MyDB;
import com.example.mocomproject.R;

import java.util.List;

public class SelectMentorFragment extends Fragment {

    private RecyclerView recyclerViewMentors;
    private MyDB myDB;
    private ChatViewModel chatViewModel;
    private String message;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_select_mentor, container, false);

        recyclerViewMentors = root.findViewById(R.id.recyclerViewMentors);
        recyclerViewMentors.setLayoutManager(new LinearLayoutManager(getContext()));

        myDB = new MyDB(requireContext());
        myDB.insertMentor("Hello", "010-1111-1111");
        myDB.updateMentorStatus("Hello", true);
        myDB.insertMentor("Hell", "010-2222-2222");
        myDB.updateMentorStatus("Hell", true);
        myDB.insertMentor("Hi", "010-3333-3333");
        myDB.updateMentorStatus("Hi", false);



        List<Mentor> mentors = myDB.getAllMentors();

        if(getArguments() != null) {
            message = getArguments().getString("question");
        }

        MentorAdapter mentorAdapter = new MentorAdapter(requireContext(),mentors, message);
        recyclerViewMentors.setAdapter(mentorAdapter);

        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        return root;
    }

}
