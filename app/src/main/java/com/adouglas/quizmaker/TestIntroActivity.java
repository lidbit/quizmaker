package com.adouglas.quizmaker;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.adouglas.quizmaker.model.Test;

public class TestIntroActivity extends BaseActivity {

    private Test test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_intro);

        Intent intent = getIntent();
        String test_id = intent.getStringExtra("test_id");
        Integer id = Integer.parseInt(test_id);

        test = Test.findById(Test.class, id);
        int questions = test.getQuestions().size();

        TextView textViewName = (TextView) findViewById(R.id.test_name);
        TextView textViewDesc = (TextView) findViewById(R.id.test_description);
        TextView textViewTimeLimit = (TextView) findViewById(R.id.test_timelimit);
        TextView textViewQuestionCount = (TextView) findViewById(R.id.question_count);

        Resources res = getResources();

        textViewName.setText(res.getString(R.string.test_name, test.name));
        textViewDesc.setText(res.getString(R.string.test_description, test.description));
        textViewTimeLimit.setText(res.getString(R.string.time_limit, test.timelimit));
        textViewQuestionCount.setText(String.valueOf(res.getString(R.string.test_question_count, questions)));
    }

    public void onStartTest(View view)
    {
        Intent intent = new Intent(this, TestRunnerActivity.class);
        intent.putExtra("test_id", test.getId().toString());
        startActivity(intent);
        finish();
    }
}
