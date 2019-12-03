package com.example.smartfarm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 메인 액티비티
 * 서비스를 이용하는 사용자들이 키우는 작물 데이터를 조회할 수 있다.
 */

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private UserListAdapter adapter;
    private ArrayList<User> userList;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        listView = (ListView) findViewById(R.id.list);
        userList = new ArrayList<User>();

        getUser("http://15.165.80.105/user_list.php");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getApplicationContext(), UserDataActivity.class);
                intent.putExtra("id", userList.get(position).userID);
                //Toast.makeText(MainActivity.this, userList.get(position).userID, Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
    }

    // 메뉴 버튼에 대한 메소드
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.first:
                intent = new Intent(getApplicationContext(), WebActivity.class);
                startActivity(intent);
                break;

            case R.id.second:
                intent = new Intent(getApplicationContext(), MemoActivity.class);
                startActivity(intent);
                break;
        }
        return true;
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
                            String userID, userName;

                            int count = 0;

                            while(count < jsonArray.length()) {
                                JSONObject object = jsonArray.getJSONObject(count);
                                userID = object.getString("User_ID");//여기서 ID가 대문자임을 유의
                                //userPassword = object.getString("userPassword");
                                userName = (String)object.getString("User_Nickname");
                                //userAge = object.getString("userAge");
                                User user = new User(userID, userName);
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