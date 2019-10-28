package com.example.jsontest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class StateListAdapter extends BaseAdapter {

    private Context context;
    private List<State> stateList;

    public StateListAdapter(Context context, List<State> stateList){
        this.context = context;
        this.stateList = stateList;
    }

    // 출력할 총 갯수를 설정하는 메소드
    @Override
    public int getCount() {
        return stateList.size();
    }

    // 특정한 아이템을 반환하는 메소드
    @Override
    public Object getItem(int i) {
        return stateList.get(i);
    }

    // 아이템별 아이디를 반환하는 메소드
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.state, null);

        //뷰에 다음 컴포넌트들을 연결시켜줌
        TextView location_text = (TextView)v.findViewById(R.id.location);
        TextView temp_text = (TextView)v.findViewById(R.id.temp);
        TextView weather_text = (TextView)v.findViewById(R.id.weather);

        location_text.setText(stateList.get(i).getLocation());
        temp_text.setText(stateList.get(i).getTemp());
        weather_text.setText(stateList.get(i).getWeather());

        //이렇게하면 findViewWithTag를 쓸 수 있음 없어도 되는 문장임
        v.setTag(stateList.get(i).getLocation());

        //만든뷰를 반환함
        return v;

    }
}
