package com.example.mocomproject.Login;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mocomproject.MainActivity;
import com.example.mocomproject.MyDB;
import com.example.mocomproject.R;
import com.example.mocomproject.ui.home.HomeFragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginView extends FragmentActivity {
    Button loginBtn;
    EditText IdEdt, PwEdt;
    SQLiteDatabase sqlDB;
    MyDB myHelper;
    ImageView img;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginview);

        Fragment fragment = new HomeFragment();
        loginBtn = (Button) findViewById(R.id.loginBtn);
        IdEdt = (EditText) findViewById(R.id.idEdt);
        PwEdt = (EditText) findViewById(R.id.pwEdt);
        img = (ImageView) findViewById(R.id.imageView);
        img.setImageResource(R.drawable.home);

        myHelper = new MyDB(LoginView.this);

        PwEdt.setInputType(android.text.InputType.TYPE_CLASS_TEXT |
                android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = IdEdt.getText().toString();
                String inputPassword = PwEdt.getText().toString();
                String hashedPassword = hashPassword(inputPassword);

                if (hashedPassword != null) {
                    sqlDB = myHelper.getReadableDatabase();
                    Cursor cursor = sqlDB.rawQuery("SELECT * FROM groupTBL WHERE userId = ?", new String[]{userId});

                    if (cursor.moveToFirst()) {
                        String storedHashedPassword = cursor.getString(cursor.getColumnIndex("password"));
                        if (hashedPassword.equals(storedHashedPassword)) {

                            // 로그인 성공
                            Toast.makeText(LoginView.this, "Login successful", Toast.LENGTH_SHORT).show();
                            myHelper.setCurrentUserId(userId);
                            myHelper.recordAttendance();

                            // 로그인 성공 후 다음 이동
                            Intent intent = new Intent(LoginView.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // 패스워드 불일치
                            Toast.makeText(LoginView.this, "Invalid password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // 사용자 아이디 없음
                        Toast.makeText(LoginView.this, "User not found", Toast.LENGTH_SHORT).show();
                    }
                    cursor.close();
                    sqlDB.close();
                } else {
                    // 해싱 실패 처리
                    Log.e("LoginActivity", "Password hashing failed.");
                }
            }
        });
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
