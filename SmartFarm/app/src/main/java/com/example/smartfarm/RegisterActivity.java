package com.example.smartfarm;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 회원가입 액티비티
 * 원하는 아이디, 비밀번호와 자신의 이름, 나이를 입력
 * 입력한 정보는 DB에 저장된다.
 */

public class RegisterActivity extends AppCompatActivity {

    HttpConnection httpConn = HttpConnection.getInstance();

    String userID, password, name;
    String age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText idText = (EditText) findViewById(R.id.userID);
        final EditText pwText = (EditText) findViewById(R.id.password);
        final EditText nameText = (EditText) findViewById(R.id.name);
        final EditText ageText = (EditText) findViewById(R.id.age);

        Button registerBt = (Button) findViewById(R.id.register);
        registerBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userID = idText.getText().toString();
                password = pwText.getText().toString();
                name = nameText.getText().toString();
                age = ageText.getText().toString();

                sendData(userID, password, name, age);
                Toast.makeText(RegisterActivity.this, "환영합니다. " + name + "님!" , Toast.LENGTH_SHORT).show();
                finish(); // 회원가입 정보 입력 후 버튼을 누르면 로그인 화면으로 돌아감
            }
        });
    }

    // 자동 분사 여부와 선택한 향기를 전송
    private void sendData(final String userID, final String password, final String name, final String age) {
        new Thread() {
            public void run() {
                httpConn.registerUser(userID, password, name, age, callback);
            }
        }.start();
    }

    private final Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            //Log.d(TAG, "콜백오류:"+e.getMessage());
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body = response.body().string();
            //Log.d(TAG, "서버에서 응답한 Body:"+body);
        }
    };
}
