package com.example.smartfarm;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<User> userList;

    public UserListAdapter(Context context, ArrayList<User> userList){
        this.context = context;
        this.userList = userList;
    }

    //출력할 총갯수를 설정하는 메소드
    @Override
    public int getCount() {
        return userList.size();
    }

    //특정한 유저를 반환하는 메소드
    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    //아이템별 아이디를 반환하는 메소드
    @Override
    public long getItemId(int i) {
        return i;
    }

    //가장 중요한 부분
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.userlist, null);

        //뷰에 다음 컴포넌트들을 연결시켜줌
        //TextView userID = (TextView)v.findViewById(R.id.userID);
        //TextView userPassword = (TextView)v.findViewById(R.id.userPassword);
        TextView userName = (TextView)v.findViewById(R.id.Name);
        //TextView userAge = (TextView)v.findViewById(R.id.userAge);

        //userID.setText(userList.get(i).getUserID());
        //userPassword.setText(userList.get(i).getUserPassword());
        userName.setText(userList.get(i).getUserName());
        //userAge.setText(userList.get(i).getUserAge());

        //이렇게하면 findViewWithTag를 쓸 수 있음 없어도 되는 문장임
        v.setTag(userList.get(i).getUserID());

        //만든뷰를 반환함
        return v;
    }
}