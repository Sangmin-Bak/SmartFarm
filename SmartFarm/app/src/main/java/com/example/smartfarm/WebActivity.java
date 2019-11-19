package com.example.smartfarm;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        onControlWeb();
    }

    private void onControlWeb() {
        WebView controlWeb = (WebView) findViewById(R.id.webView);
        controlWeb.setWebViewClient(new WebViewClient()); // 이구문이 빠지면 web view 가아닌 설치된 다른 웹브라우저에서 새창으로 열린다.
        WebSettings webSettings = controlWeb.getSettings();    // mobile web setting
        webSettings.setJavaScriptEnabled(true);     // 자바스크립트 허용
        webSettings.setLoadWithOverviewMode(true);      // 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정

        controlWeb.loadUrl("http://192.168.55.185:5000/");
    }
}
