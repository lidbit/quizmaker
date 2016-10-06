package com.adouglas.quizmaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

        final ListView listView = (ListView) findViewById(R.id.lvTestResults);
        testResults = TestResult.listAll(TestResult.class);
        testResultArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, testResults);
        listView.setAdapter(testResultArrayAdapter);
        listView.setClickable(true);
        final Intent intent = new Intent(this, TestResultActivity.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TestResult testResult = (TestResult) listView.getItemAtPosition(position);
                intent.putExtra("test_result_id", testResult.getId().toString());
                startActivity(intent);
            }
        });
    }
}