package com.june.writingprompter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        boolean promptUsed = getIntent().getExtras().getBoolean("promptUsed");

        TextView editTextTextMultiLine = findViewById(R.id.editTextTextMultiLine);
        TextView result = findViewById(R.id.tv_result);
        editTextTextMultiLine.setText(intent.getStringExtra("text"));
        String timeSpent = intent.getStringExtra("timeSpent");
        String msg;
        if (promptUsed) {
            msg = "제시어가 사용되었어요";
        }
        else {
            msg = "제시어가 사용되지 않았어요";
        }

        result.setText("소요시간: "+ timeSpent +", "+ msg);
    }
}