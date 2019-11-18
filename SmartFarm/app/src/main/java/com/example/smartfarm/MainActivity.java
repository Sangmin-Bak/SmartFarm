package com.example.smartfarm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 메인 액티비티
 * 사용자가 키우는 작물의 상태를 확인하고 햇빛과 물의 양을 조절하는 기능을 한다.
 */

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private UserListAdapter adapter;
    private ArrayList<User> userList;

    Intent intent = getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);
        userList = new ArrayList<User>();

        getUser("http://123.214.83.99/user_list.php");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), UserDataActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getUser(final String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(new Request.Builder().url(url).build()).enqueue(new Callback() {
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
                            JSONArray jsonArray = jsonObject.getJSONArray("response");
                            //final String[] name = new String[jsonArray.length()];
                            //String userID, userPassword, userName, userAge;
                            String userName;

                            int count = 0;

                            while(count < jsonArray.length()) {
                                JSONObject object = jsonArray.getJSONObject(count);
                                //userID = object.getString("userID");//여기서 ID가 대문자임을 유의
                                //userPassword = object.getString("userPassword");
                                userName = (String)object.getString("userName");
                                //userAge = object.getString("userAge");
                                User user = new User(userName);
                                //User user = new User(userID, userPassword, userName, userAge);
                                userList.add(user);
                                count++;
                            }
                            adapter = new UserListAdapter(getApplicationContext(), userList);
                            listView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                });
            }
        });
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

}