package com.example.mocomproject.ui.chat;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mocomproject.Login.LoginView;
import com.example.mocomproject.MyDB;
import com.example.mocomproject.R;

import java.util.List;

public class MentorAdapter extends RecyclerView.Adapter<MentorAdapter.MentorViewHolder> {

    private List<Mentor> mentors;
    private String message;
    private MyDB myDB;
    private Context mContext;

    public MentorAdapter(Context context, List<Mentor> mentors, String message) {
        this.mentors = mentors;
        this.message = message;
        this.mContext = context;
        this.myDB = new MyDB(context);
    }

    @NonNull
    @Override
    public MentorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new MentorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MentorViewHolder holder, int position) {
        Mentor mentor = mentors.get(position);
        holder.mentorId.setText(mentor.getMentorId());

        holder.bind(mentor);
    }

    @Override
    public int getItemCount() {
        return mentors.size();
    }

    class MentorViewHolder extends RecyclerView.ViewHolder {

        private TextView mentorId;
        private Button requestButton;

        public MentorViewHolder(@NonNull View itemView) {
            super(itemView);
            mentorId = itemView.findViewById(R.id.text_view);
            requestButton = itemView.findViewById(R.id.requestBtn);
        }
        public void bind(Mentor mentor) {
            requestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMentorSelected(mentor, itemView.getContext());
                }
            });
        }
    }

    private void onMentorSelected(Mentor mentor, Context context) {
        String phoneNumber = mentor.getMentorPhone();
        SmsManager sms = SmsManager.getDefault();

        myDB = new MyDB(context);

        myDB.insertQuestion(message, mentor.getMentorId());

        Toast.makeText(context, message + "내용의 문자를 " + phoneNumber + "에 전송 완료하였습니다.", Toast.LENGTH_SHORT).show();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }
}

