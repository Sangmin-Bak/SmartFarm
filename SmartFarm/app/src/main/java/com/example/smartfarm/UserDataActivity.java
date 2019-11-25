package com.example.smartfarm;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserDataActivity extends AppCompatActivity {

    HttpConnection httpConn = HttpConnection.getInstance();

    String getID;
    TextView getIDText, CropText, CO2Text, WaterText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdata);

        Intent intent = getIntent();

        getIDText = (TextView) findViewById(R.id.getID);
        CropText = (TextView) findViewById(R.id.dataText1);
        CO2Text = (TextView) findViewById(R.id.dataText2);
        WaterText = (TextView) findViewById(R.id.dataText3);

        getID = intent.getStringExtra("id");    // 클릭한 사용자의 아이디
        getIDText.setText(getID + "님의 작물");

        sendData(getID);
    }

    // 아이디와 비번 전송
    private void sendData(final String User_ID) {
        new Thread() {
            public void run() {
                httpConn.dataRequest(User_ID, callback);
            }
        }.start();
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
                            CropText.setText(jsonObject.getString("UserCrop_Name"));
                            CO2Text.setText(jsonObject.getString("CarbonDioxide_Data"));
                            WaterText.setText(jsonObject.getString("WaterSensor_Data"));
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
