package com.example.smartfarm;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MemoActivity extends AppCompatActivity {

    EditText memoEdit = null;
    TextFileManager textFileManager = new TextFileManager(this);

    String memoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        memoEdit = (EditText) findViewById(R.id.memo_edit);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            // 1. 파일에 저장된 메모 텍스트 파일 불러오기
            case R.id.load_btn:
                memoData = textFileManager.load();
                memoEdit.setText(memoData);

                Toast.makeText(this, "불러오기 완료", Toast.LENGTH_LONG).show();
                break;
            // 2. 에딧텍스트에 입력된 메모를 텍스트 파일로 저장하기
            case R.id.save_btn:
                memoData = memoEdit.getText().toString();
                textFileManager.save(memoData);
                memoEdit.setText("");

                Toast.makeText(this, "저장 완료", Toast.LENGTH_LONG).show();
                break;
            // 3. 저장된 메모 파일 삭제하기
            case R.id.delete_btn:
                textFileManager.delete();
                memoEdit.setText("");

                Toast.makeText(this, "삭제 완료", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
