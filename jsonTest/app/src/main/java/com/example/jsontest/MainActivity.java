package com.example.jsontest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView location_text, temp_text, weather_text;

    private ListView listView;
    private StateListAdapter adapter;
    private List<State> stateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //location_text = (TextView) findViewById(R.id.location);
        //temp_text = (TextView) findViewById(R.id.temp);
        //weather_text = (TextView) findViewById(R.id.weather);

        // 리스트뷰 초기화
        listView = (ListView) findViewById(R.id.listview);
        stateList = new ArrayList<State>();

        adapter = new StateListAdapter(getApplicationContext(), stateList);
        listView.setAdapter(adapter);

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(new Request.Builder().url("http://192.168.55.187/jsonTest02.php").build()).enqueue(new Callback() {
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

                            String location, temp, weather;

                            location = jsonObject.getString("지역");
                            temp = jsonObject.getString("기온");
                            weather = jsonObject.getString("날씨");

                            State state = new State(location, temp, weather);
                            stateList.add(state);

                           // location_text.setText(jsonObject.getString("지역"));
                           // temp_text.setText(jsonObject.getString("기온"));
                           // weather_text.setText(jsonObject.getString("날씨"));

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

}
