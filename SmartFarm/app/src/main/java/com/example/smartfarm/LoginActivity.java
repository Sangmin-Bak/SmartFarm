package com.example.smartfarm;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 사용자 로그인 액티비티
 * 앱 실행시 가장 먼저 실행된다.
 */

public class LoginActivity extends AppCompatActivity {

    HttpConnection httpConn = HttpConnection.getInstance();

    String userID, password;

    ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 앱 실행시 네트워크에 연결되어있는지 확인
        checkInternetState();

        final EditText idText = (EditText) findViewById(R.id.loginID);
        final EditText pwText = (EditText) findViewById(R.id.loginPW);

        Button loginBt = (Button) findViewById(R.id.login);
        loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userID = idText.getText().toString();
                password = pwText.getText().toString();
                sendData(userID, password);

            }
        });

        TextView registerText = (TextView) findViewById(R.id.registerText);
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    // 아이디와 비번 전송
    private void sendData(final String userID, final String password) {
        new Thread() {
            public void run() {
                httpConn.loginRequest(userID, password, callback);
            }
        }.start();
    }

    private void checkInternetState() {
        final Intent intent = new Intent();
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if(!(connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected())) {
            new AlertDialog.Builder(this)
                    .setTitle("네트워크 연결 오류")
                    .setMessage("네트워크가 연결 상태 확인 후 다시 시도해 주십시요.")
                    .setCancelable(false)
                    .setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                        }
                    }).show();
        }
    }

    // 뒤로가기를 누르면 앱 종료 여부를 물어봄
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("앱 종료")
                .setMessage("앱을 종료하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("아니요", null)
                .show();
    }

    // 로그인 요청 후 응답을 받는다.
    private final Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            //Log.d(TAG, "콜백오류:"+e.getMessage());
        }

        @Override
        public void onResponse(Call call, final Response response) throws IOException {

            new Handler(getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 응답 메세지를 json형태로 받아온다.
                        JSONObject jsonObject = new JSONObject(response.body().string());

                        // 로그인이 수락되면 메인액티비티로 이동한다.
                        String msg = jsonObject.getString("success");
                        if(msg == "true") {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            });
        }
    };
}
