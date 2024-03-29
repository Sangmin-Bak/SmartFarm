package com.example.smartfarm;

import java.io.File;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
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

    /** DB에 회원가입 정보 저장. **/
    public void registerUser(String User_ID, String User_Passwd, String User_Nickname, String AGE, Callback callback) {
        RequestBody body = new FormBody.Builder()
                .add("User_ID", User_ID)
                .add("User_Passwd", User_Passwd)
                .add("User_Nickname", User_Nickname)
                .add("AGE", AGE)
                .build();
        Request request = new Request.Builder()
                .url("http://15.165.80.105/android_register.php")
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /** 로그인 요청. **/
    // DB에 저장된 id와 비번으로 로그인할 경우 로그인 수락
    public void loginRequest(String User_ID, String User_Passwd, Callback callback) {
        RequestBody body = new FormBody.Builder()
                .add("User_ID", User_ID)
                .add("User_Passwd", User_Passwd)
                .build();
        Request request = new Request.Builder()
                .url("http://15.165.80.105/android_login.php")
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /** 클릭한 사용자의 작물 정보를 조회. **/
    // DB에 저장된 id와 비번으로 로그인할 경우 로그인 수락
    public void dataRequest(String User_ID, Callback callback) {
        RequestBody body = new FormBody.Builder()
                .add("User_ID", User_ID)
                .build();
        Request request = new Request.Builder()
                .url("http://15.165.80.105/user_data.php")
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void sendFile(File file) {
        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("files", file.getName(), RequestBody.create(MultipartBody.FORM, file))
                .build();

        Request request = new Request.Builder()
                .url("http://15.165.80.105/getFiles.php")
                .post(body)
                .build();
    }
}

