package com.adouglas.quizmaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class TestIntroActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_intro);

        Intent intent = getIntent();
        String test_id = intent.getStringExtra("test_id");
        Integer id = Integer.parseInt(test_id);

        Test test = Test.findById(Test.class, id);

        TextView textView = (TextView) findViewById(R.id.test_name);
        textView.setText(test.name);

        Toast.makeText(getApplicationContext(), test_id, Toast.LENGTH_SHORT).show();
    }
}
