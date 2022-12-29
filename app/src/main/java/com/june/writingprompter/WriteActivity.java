package com.june.writingprompter;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public class WriteActivity extends AppCompatActivity {

    Date startTime;
    int count = 1;
    int end_count = 0;
    Thread t;
    TextView tv_endTime;
    TextView editTextTextMultiLineEx;
    TextView editTextTextMultiLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        Intent intent = getIntent();

        TextView tv_cat = findViewById(R.id.tv_cat);
        TextView tv_time = findViewById(R.id.tv_time);
        TextView tv_startTime = findViewById(R.id.tv_startTime);

        tv_endTime = findViewById(R.id.tv_endTime);
        editTextTextMultiLineEx = findViewById(R.id.editTextTextMultiLineEx);
        editTextTextMultiLine = findViewById(R.id.editTextTextMultiLine);

        String category = intent.getStringExtra("cat");

        tv_cat.setText(category);
        tv_time.setText(intent.getStringExtra("time"));

        // 현재 시간을 기록
        startTime = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        tv_startTime.setText(formatter.format(startTime));

        Log.d("tv_time: ", tv_time.getText().toString());

        switch(tv_time.getText().toString()) {
            case "1 분" :
                end_count = 60;
                break;
            case "3 분" :
                end_count = 180;
                break;
            case "5 분" :
                end_count = 300;
                break;
            case "10 분" :
                end_count = 600;
                break;
            case "20 분" :
                end_count = 1200;
                break;
            default :
                end_count = 0;
                break;
        }

        // 핸들러로 전달할 runnable 객체. 수신 스레드 실행.
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                tv_endTime.setText(count ++ + " s");
                if(end_count <= count) {
                    goResult();
                }
            }
        } ;

        // 새로운 스레드 실행 코드. 1초 단위로 현재 시각 표시 요청.
        class NewRunnable implements Runnable {
            @Override
            public void run() {
                while (end_count > count) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace() ;
                    } catch (Exception e) {
                        e.printStackTrace() ;
                    }

                    // 메인 스레드에 runnable 전달.
                    runOnUiThread(runnable) ;
                }
            }
        }

        NewRunnable nr = new NewRunnable() ;
        t = new Thread(nr) ;
        t.start() ;
        String[] exString = new String[3];

        //선택한 카테고리에 맞는 제시어 리스트 선택
        //왜인지 모르는 이유로 switch로는 구현 실패
        if (category.equals("판타지")) {
            exString = Prompts.fantasy;
        }
        if (category.equals("자연")) {
            exString = Prompts.nature;
        }
        if (category.equals("SF")) {
            exString = Prompts.sf;
        }
        if (category.equals("공포")) {
            exString = Prompts.horror;
        }
        if (category.equals("기타")) {
            exString = Prompts.etc;
        }

        //랜덤한 제시어 선택해 표시
        editTextTextMultiLineEx.setText(exString[new Random().nextInt(exString.length)]);

    }


    public void endWriting(View view) {
        goResult();
    }


    private void goResult() {
        t.interrupt();

        String userText = editTextTextMultiLine.getText().toString();
        String promptText = editTextTextMultiLineEx.getText().toString();
        boolean promptUsed;

        //작성한 글에 제시어가 있으면 true, 없으면 false
        promptUsed = userText.contains(promptText);

        //결과 화면에 파라미터 전달
        Intent resultIntent = new Intent(getApplicationContext(), ResultActivity.class);
        resultIntent.putExtra("text", userText);
        resultIntent.putExtra("promptUsed", promptUsed);
        resultIntent.putExtra("timeSpent", tv_endTime.getText().toString());
        startActivity(resultIntent);
    }


}