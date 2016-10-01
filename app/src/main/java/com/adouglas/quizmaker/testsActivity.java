package com.adouglas.quizmaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class TestsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

        List<Test> tests = Test.listAll(Test.class);
        final ListView listView = (ListView) findViewById(R.id.lvTests);

        ArrayAdapter<Test> testsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tests);
        listView.setAdapter(testsAdapter);
        listView.setClickable(true);
        final Intent intent = new Intent(this, TestIntroActivity.class);
        listView.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                Test test = (Test) listView.getItemAtPosition(position);
                intent.putExtra("test_id", test.getId().toString());
                startActivity(intent);
            }
        });
    }
}
