package com.example.mocomproject.Login;

import android.content.ContentValues;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.example.mocomproject.MyDB;
import com.example.mocomproject.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterView extends FragmentActivity {
    Button registerBtn;
    EditText IdEdt, PwEdt, phoneEdt;
    ImageView img;
    SQLiteDatabase sqlDB;
    MyDB myHelper;
    String phoneNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reigsterview);

        registerBtn = (Button) findViewById(R.id.registerBtn);
        IdEdt = (EditText) findViewById(R.id.idEdt);
        PwEdt = (EditText) findViewById(R.id.pwEdt);
        img = (ImageView) findViewById(R.id.imageView);
        img.setImageResource(R.drawable.home);
        phoneEdt = (EditText) findViewById(R.id.phoneEdt);
        myHelper = new MyDB(RegisterView.this);

        PwEdt.setInputType(android.text.InputType.TYPE_CLASS_TEXT |
                android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = IdEdt.getText().toString();
                String password = PwEdt.getText().toString();
                phoneNumber = phoneEdt.getText().toString();

                // 암호화된 비밀번호 얻기
                String hashedPassword = hashPassword(password);

                if (hashedPassword != null) {
                    // 이미 존재하는 userId인지 체크
                    if (isUserIdExists(userId)) {
                        Toast.makeText(RegisterView.this, "이미 존재하는 사용자입니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        // 회원 등록
                        sqlDB = myHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("userId", userId);
                        values.put("password", hashedPassword);
                        values.put("phoneNumber", phoneNumber);
                        sqlDB.insert("groupTBL", null, values);
                        sqlDB.close();

                        Toast.makeText(RegisterView.this, "회원가입 성공", Toast.LENGTH_SHORT).show();

                        // 회원가입 성공 후 로그인 화면으로 이동
                        Intent intent = new Intent(RegisterView.this, LoginMain.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(RegisterView.this, "비밀번호 암호화 실패", Toast.LENGTH_SHORT).show();
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

    private boolean isUserIdExists(String userId) {
        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM groupTBL WHERE userId = ?", new String[]{userId});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}
