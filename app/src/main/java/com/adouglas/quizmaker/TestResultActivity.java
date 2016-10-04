package com.adouglas.quizmaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class TestResultActivity extends Activity {
    private Test test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);
        Intent intent = getIntent();
        String testId = intent.getStringExtra("test_id");
        test = Test.findById(Test.class, Integer.parseInt(testId));

    }

    public void onBackToTests(View view)
    {
        Intent intent = new Intent(this, TestsActivity.class);
        startActivity(intent);
    }

    public void onTryAgain(View view)
    {
        Intent intent = new Intent(this, TestIntroActivity.class);
        intent.putExtra("test_id", test.getId().toString());
        startActivity(intent);
    }
}
