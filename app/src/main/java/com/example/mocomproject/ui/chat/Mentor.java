package com.example.mocomproject.ui.chat;

public class Mentor {
    private String mentorId;
    private String mentorPhone;

    public Mentor(String mentorId, String mentorPhone) {
        this.mentorId = mentorId;
        this.mentorPhone = mentorPhone;
    }

    public String getMentorId() {
        return mentorId;
    }

    public String getMentorPhone() {
        return mentorPhone;
    }

    public void setMentorId(String mentorId) {
        this.mentorId = mentorId;
    }

    public void setMentorPhone(String mentorPhone) {
        this.mentorPhone = mentorPhone;
    }
}

