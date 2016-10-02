package com.adouglas.quizmaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TestIntroActivity extends Activity {

    private Test test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_intro);

        Intent intent = getIntent();
        String test_id = intent.getStringExtra("test_id");
        Integer id = Integer.parseInt(test_id);

        test = Test.findById(Test.class, id);

        TextView textView = (TextView) findViewById(R.id.test_name);
        textView.setText(test.name);
    }

    public void onStartTest(View view)
    {
        Intent intent = new Intent(this, TestRunnerActivity.class);
        intent.putExtra("test_id", test.getId().toString());
        startActivity(intent);
    }
}
