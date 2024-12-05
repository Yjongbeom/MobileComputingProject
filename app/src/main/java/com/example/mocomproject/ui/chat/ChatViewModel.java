package com.example.mocomproject.ui.chat;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mocomproject.MyDB;

import java.util.List;
import java.util.Map;

public class ChatViewModel extends AndroidViewModel {
    private MyDB db;
    private MutableLiveData<List<Map<String, String>>> chatList;

    public ChatViewModel(Application application) {
        super(application);
        db = new MyDB(application);
        chatList = new MutableLiveData<>();
        loadChats();
    }

    private void loadChats() {
        List<Map<String, String>> chats = db.getAllChats();
        chatList.setValue(chats);
    }

    public LiveData<List<Map<String, String>>> getChatList() {
        return chatList;
    }

    public void addChat(String mentorId, String question) {
        db.insertChat(mentorId, question);
        loadChats();
    }
}
