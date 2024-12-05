package com.example.mocomproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mocomproject.ui.chat.Mentor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

public class MyDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "userDB";
    private static final int DATABASE_VERSION = 4;

    private static final String TABLE_USER = "groupTBL";
    private static final String TABLE_CHAT = "chatTBL";
    private static final String TABLE_MENTOR = "mentorTBL";
    private static final String TABLE_ATTENDANCE = "attendanceTBL";
    private static final String TABLE_QUESTION = "questionTBL";
    private static final String TABLE_ANSWER = "answerTBL";

    private static String id;

    public MyDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableIfNotExists(db, TABLE_USER, "CREATE TABLE groupTBL (userId TEXT PRIMARY KEY, password TEXT, phoneNumber TEXT, mentorEnabled INTEGER DEFAULT 0);");
        createTableIfNotExists(db, TABLE_CHAT, "CREATE TABLE chatTBL (chatId INTEGER PRIMARY KEY AUTOINCREMENT, chatContent TEXT);");
        createTableIfNotExists(db, TABLE_QUESTION, "CREATE TABLE questionTBL (questionId INTEGER PRIMARY KEY AUTOINCREMENT, questionContent TEXT, mentorId TEXT);");
        createTableIfNotExists(db, TABLE_ANSWER, "CREATE TABLE answerTBL (answerId INTEGER PRIMARY KEY AUTOINCREMENT, answerContent TEXT, questionId INTEGER);");
        createTableIfNotExists(db, TABLE_MENTOR, "CREATE TABLE mentorTBL (mentorId TEXT PRIMARY KEY, mentorPhone TEXT, mentorEnabled INTEGER DEFAULT 0);");
        createTableIfNotExists(db, TABLE_ATTENDANCE, "CREATE TABLE attendanceTBL (attendanceId INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT);");
    }

    private void createTableIfNotExists(SQLiteDatabase db, String tableName, String createTableQuery) {
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + tableName + "';", null);
        try {
            if (cursor.getCount() == 0) {
                db.execSQL(createTableQuery);
            }
        } finally {
            cursor.close();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENTOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDANCE);
        onCreate(db);
    }

    public String getCurrentUserId() {
        return id;
    }

    public void setCurrentUserId(String id) {
        MyDB.id = id;
    }

    public void insertChat(String mentorId, String question) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("chatContent", question);  // 질문 내용을 chatTBL에 저장
        values.put("mentorId", mentorId);
        db.insert(TABLE_CHAT, null, values);

        ContentValues questionValues = new ContentValues();
        questionValues.put("questionContent", question);
        questionValues.put("mentorId", mentorId);  // mentorId를 questionTBL에 저장
        db.insert(TABLE_QUESTION, null, questionValues);

        db.close();
    }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    public void recordAttendance() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", getCurrentDate());
        db.insert(TABLE_ATTENDANCE, null, values);
        db.close();
    }

    public int getAttendanceDays() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_ATTENDANCE, null);
        int attendanceDays = 0;
        if (cursor.moveToFirst()) {
            attendanceDays = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return attendanceDays;
    }

    public void insertQuestion(String question, String mentorId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String existingQuestion = getExistingQuestion(db, mentorId);

        String newQuestionContent;
        if (existingQuestion.isEmpty()) {
            newQuestionContent = "Q: " + question;
        } else {
            newQuestionContent = existingQuestion + "\n" + question;
        }

        ContentValues values = new ContentValues();
        values.put("questionContent", newQuestionContent);
        values.put("mentorId", mentorId);

        if (existingQuestion.isEmpty()) {
            db.insert(TABLE_QUESTION, null, values);
        } else {
            db.update(TABLE_QUESTION, values, "mentorId = ?", new String[]{mentorId});
        }

        ContentValues chatValues = new ContentValues();
        chatValues.put("chatContent", question);
        db.insert(TABLE_CHAT, null, chatValues);

        db.close();
    }

    private String getExistingQuestion(SQLiteDatabase db, String mentorId) {
        String question = "";
        Cursor cursor = db.rawQuery("SELECT questionContent FROM " + TABLE_QUESTION + " WHERE mentorId = ?", new String[]{mentorId});
        if (cursor.moveToFirst()) {
            question = cursor.getString(0);
        }
        cursor.close();
        return question;
    }

    public List<Mentor> getAllMentors() {
        List<Mentor> mentorList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT mentorId, mentorPhone FROM " + TABLE_MENTOR + " WHERE mentorEnabled = 1", null);

        if (cursor.moveToFirst()) {
            do {
                String mentorId = cursor.getString(0);
                String mentorPhone = cursor.getString(1);
                Mentor mentor = new Mentor(mentorId, mentorPhone);
                mentorList.add(mentor);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return mentorList;
    }

    public List<Map<String, String>> getAllChats() {
        List<Map<String, String>> chatList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT questionContent, mentorId FROM " + TABLE_QUESTION, null);

        if (cursor.moveToFirst()) {
            do {
                Map<String, String> chat = new HashMap<>();
                chat.put("question", cursor.getString(0));
                chat.put("mentorId", cursor.getString(1));
                chatList.add(chat);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return chatList;
    }

    public void updateMentorStatus(String mentorId, boolean isEnabled) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mentorEnabled", isEnabled ? 1 : 0);
        db.update(TABLE_MENTOR, values, "mentorId = ?", new String[]{mentorId});
        db.close();
    }

    public void insertMentor(String mentorId, String mentorPhone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mentorId", mentorId);
        values.put("mentorPhone", mentorPhone);
        values.put("mentorEnabled", 0);
        db.insert(TABLE_MENTOR, null, values);
        db.close();
    }

    public boolean isMentorEnabled(String mentorId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT mentorEnabled FROM " + TABLE_MENTOR + " WHERE mentorId = ?", new String[]{mentorId});
        boolean isEnabled = false;
        if (cursor.moveToFirst()) {
            isEnabled = cursor.getInt(0) == 1;
        }
        cursor.close();
        db.close();
        return isEnabled;
    }
}
