package com.example.smartfarm;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdata);

        final TextView dataText1, dataText2, dataText3;
        dataText1 = (TextView) findViewById(R.id.dataText1);
        dataText2 = (TextView) findViewById(R.id.dataText2);
        dataText3 = (TextView) findViewById(R.id.dataText3);

        controlWeb();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(new Request.Builder().url("http://15.165.26.49/jsonTest02.php").build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            dataText1.setText(jsonObject.getString("지역"));
                            dataText2.setText(jsonObject.getString("기온"));
                            dataText3.setText(jsonObject.getString("날씨"));
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void controlWeb() {
        WebView webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();    // mobile web setting
        webSettings.setJavaScriptEnabled(true);     // 자바스크립트 허용
        webSettings.setLoadWithOverviewMode(true);      // 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정

        webView.loadUrl("http://192.168.55.185:8000/serial");
    }
}
