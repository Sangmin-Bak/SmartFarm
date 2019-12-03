package com.example.smartfarm;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MemoActivity extends AppCompatActivity {

    EditText memoEdit = null;
    TextFileManager textFileManager = new TextFileManager(this);

    String memoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        checkFunction();

        memoEdit = (EditText) findViewById(R.id.memo_edit);
    }

    public void onClick(View v) throws FileNotFoundException {
        switch (v.getId()) {
            // 1. 파일에 저장된 메모 텍스트 파일 불러오기
            case R.id.load_btn:
                //memoData = textFileManager.load();
                //memoEdit.setText(memoData);
                try{
                    String path = getExternalPath();
                    BufferedReader br = new BufferedReader(new FileReader(path+"/Memo.txt"));
                    String readStr = "";
                    String str = null;
                    while(((str = br.readLine()) != null)) {
                        readStr += str + "\n";
                    }
                    memoEdit.setText(memoData);
                    br.close();
                    Toast.makeText(this, path, Toast.LENGTH_LONG).show();
                    //  return readStr;

                }catch (FileNotFoundException e){
                    e.printStackTrace();
                    Toast.makeText(this, "File not Found", Toast.LENGTH_SHORT).show();
                }catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            // 2. 에딧텍스트에 입력된 메모를 텍스트 파일로 저장하기
            case R.id.save_btn:
                memoData = memoEdit.getText().toString();
                //textFileManager.save(memoData);
                //memoEdit.setText("");

                try{
                    String path = getExternalPath();
                    String filename = "Memo.txt";

                    BufferedWriter bw = new BufferedWriter(new FileWriter(path + filename, false));
                    bw.write(memoData);
                    bw.close();
                    Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            // 3. 저장된 메모 파일 삭제하기
            case R.id.delete_btn:
                textFileManager.delete();
                memoEdit.setText("");

                Toast.makeText(this, "삭제 완료", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private String getExternalPath() {
        String sdPath ="";
        String ext = Environment.getExternalStorageState();
        if(ext.equals(Environment.MEDIA_MOUNTED)){
            sdPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        }else{
            sdPath  = getFilesDir() +"";

        }
        return sdPath;
    }

    public void checkFunction(){
        int permissioninfo = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissioninfo == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"SDCard 쓰기 권한 있음",Toast.LENGTH_SHORT).show();
        }else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);

            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        String str = null;
        if(requestCode == 100){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                str = "SD Card 쓰기권한 승인";
            else str = "SD Card 쓰기권한 거부";
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        }
    }

}
