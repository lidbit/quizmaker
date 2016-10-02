package com.adouglas.quizmaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TestRunnerActivity extends Activity {

    TextView time;
    Test test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_runner);
        time = (TextView) findViewById(R.id.time_remaining);
        Intent intent = getIntent();
        String testId = intent.getStringExtra("test_id");
        test = Test.findById(Test.class, Long.parseLong(testId));
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d("test runner", "started");
        new CountDownTimer(Integer.parseInt(test.timelimit) * 10000, 1000) {

            public void onTick(long millisUntilFinished) {

                time.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                time.setText("done!");
            }
        }.start();
    }
}
