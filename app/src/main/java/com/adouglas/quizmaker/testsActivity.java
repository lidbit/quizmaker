package com.adouglas.quizmaker;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class TestsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

        List<Test> tests = Test.listAll(Test.class);
        ListView listView = (ListView) findViewById(R.id.lvTests);

        ArrayAdapter<Test> testsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tests);
        listView.setAdapter(testsAdapter);
    }
}
