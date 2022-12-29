package com.june.writingprompter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startWriting(View view) {
        // 현재 화면의 선택사항 확인
        RadioGroup rg_cat= (RadioGroup) findViewById(R.id.rg_cat);
        RadioGroup rg_time= (RadioGroup) findViewById(R.id.rg_time);

        //버튼이 선택되지 않았다면 각각에 맞는 메시지 출력
        if ((RadioButton)findViewById(rg_cat.getCheckedRadioButtonId()) == null) {
            Toast.makeText(getApplicationContext(), "카테고리가 선택되지 않았어요.", Toast.LENGTH_LONG).show();
            return;
        }
        if ((RadioButton)findViewById(rg_time.getCheckedRadioButtonId()) == null) {
            Toast.makeText(getApplicationContext(), "작성시간이 선택되지 않았어요.", Toast.LENGTH_LONG).show();
            return;
        }

        final String rg_cat_value = ((RadioButton)findViewById(rg_cat.getCheckedRadioButtonId())).getText().toString();
        final String rg_time_value = ((RadioButton)findViewById(rg_time.getCheckedRadioButtonId())).getText().toString();

        // 편집화면에 파라미터 전달
        Intent writeIntent = new Intent(getApplicationContext(), WriteActivity.class);
        writeIntent.putExtra("cat", rg_cat_value);
        writeIntent.putExtra("time", rg_time_value);
        startActivity(writeIntent);
    }
}