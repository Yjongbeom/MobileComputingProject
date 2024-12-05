package com.example.mocomproject.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mocomproject.R;
import com.example.mocomproject.databinding.FragmentChatBinding;

import java.util.List;
import java.util.Map;

public class ChatFragment extends Fragment {

    private FragmentChatBinding binding;
    private ChatViewModel chatViewModel;
    Button MentorBtn;
    TextView text1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        binding = FragmentChatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerView;
        text1 = binding.textNoConversations;
        MentorBtn = binding.MentorRequestBtn;
        ChatAdapter adapter = new ChatAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        chatViewModel.getChatList().observe(getViewLifecycleOwner(), new Observer<List<Map<String, String>>>() {
            @Override
            public void onChanged(List<Map<String, String>> chatList) {
                if(chatList.isEmpty())
                {
                    recyclerView.setVisibility(View.GONE);
                    text1.setVisibility(View.VISIBLE);
                }
                else
                {
                    recyclerView.setVisibility(View.VISIBLE);
                    text1.setVisibility(View.GONE);
                    adapter.setChatList(chatList);
                }
            }
        });

        adapter.setOnItemClickListener(new ChatAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String question) {
                Bundle bundle = new Bundle();
                bundle.putString("question", question);
                Navigation.findNavController(root).navigate(R.id.action_navigation_chat_to_navigation_chat_detail, bundle);
            }
        });

        MentorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_chat_to_mentor_request);
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
