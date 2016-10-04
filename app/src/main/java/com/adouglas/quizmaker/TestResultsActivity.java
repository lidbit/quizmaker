package com.adouglas.quizmaker;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class TestResultsActivity extends Activity {
    ArrayAdapter<TestResult> testResultArrayAdapter;
    List<TestResult> testResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_results);

        ListView listView = (ListView) findViewById(R.id.lvTestResults);
        testResults = TestResult.listAll(TestResult.class);
        testResultArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, testResults);
        listView.setAdapter(testResultArrayAdapter);
        listView.setClickable(true);
    }
}
