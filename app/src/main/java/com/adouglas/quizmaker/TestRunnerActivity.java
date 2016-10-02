package com.adouglas.quizmaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

public class TestRunnerActivity extends Activity {

    TextView time;
    Test test;
    Intent testResultIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_runner);
        testResultIntent = new Intent(this, TestResultActivity.class);
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
        new CountDownTimer(Integer.parseInt(test.timelimit) * 1000, 1000) {

            public void onTick(long millisUntilFinished) {

                time.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                time.setText("done!");
                testResultIntent.putExtra("test_id", test.getId().toString());
                startActivity(testResultIntent);
            }
        }.start();
    }
}
