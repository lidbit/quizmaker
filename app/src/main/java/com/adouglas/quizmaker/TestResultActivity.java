package com.adouglas.quizmaker;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.adouglas.quizmaker.model.QuestionResult;
import com.adouglas.quizmaker.model.Test;
import com.adouglas.quizmaker.model.TestResult;
import java.util.List;

public class TestResultActivity extends BaseActivity {
    private TestResult testResult;
    ArrayAdapter<QuestionResult> questionResultArrayAdapter;
    List<QuestionResult> questionResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);
        Intent intent = getIntent();
        String testResultId = intent.getStringExtra("test_result_id");
        testResult = TestResult.findById(TestResult.class, Long.parseLong(testResultId));
        TextView testScore = (TextView) findViewById(R.id.test_score);
        Resources res = getResources();

        float percent = ((float)testResult.correctAnswers / testResult.totalQuestions) * 100;

        String percentString = res.getString(R.string.test_score, percent, "%");

        testScore.setText(percentString);
        questionResults = QuestionResult.find(QuestionResult.class, "test_result = ?", testResult.getId().toString());
        questionResultArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, questionResults);
        ListView lvQuestionResults = (ListView) findViewById(R.id.lvQuestionResults);
        lvQuestionResults.setAdapter(new QuestionResultsAdapter(this, questionResults));
    }

    public void onBackToTests(View view)
    {
        Intent intent = new Intent(this, TestsActivity.class);
        startActivity(intent);
        finish();
    }

    public void onTryAgain(View view)
    {
        Intent intent = new Intent(this, TestIntroActivity.class);
        Test test = Test.findById(Test.class, testResult.testId);
        intent.putExtra("test_id", test.getId().toString());
        startActivity(intent);
        finish();
    }
}
