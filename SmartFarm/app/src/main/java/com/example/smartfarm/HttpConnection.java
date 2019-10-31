package com.example.smartfarm;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * DB로 데이터를 전송
 * 웹서버를 통해 php파일로 데이터 전송 후 php에서 DB로 데이터를 저장한다.
 */

public class HttpConnection {

    private OkHttpClient client;
    private static HttpConnection instance = new HttpConnection();
    public static HttpConnection getInstance() {
        return instance;
    }

    private HttpConnection() { this.client = new OkHttpClient(); }

    /** DB에 회원가입 정보 저장. */
    public void registerUser(String userID, String password, String name, String age, Callback callback) {
        RequestBody body = new FormBody.Builder()
                .add("userID", userID)
                .add("password", password)
                .add("name", name)
                .add("age", age)
                .build();
        Request request = new Request.Builder()
                .url("http://123.214.83.99/android_register.php")
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /** 로그인 요청. */
    // DB에 저장된 id와 비번으로 로그인할 경우 로그인 수락
    public void loginRequest(String userID, String password, Callback callback) {
        RequestBody body = new FormBody.Builder()
                .add("userID", userID)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url("http://123.214.83.99/android_login.php")
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
}

