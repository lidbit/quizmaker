package com.adouglas.quizmaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class TestIntroActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_intro);

        Intent intent = getIntent();
        String test_id = intent.getStringExtra("test_id");
        Toast.makeText(getApplicationContext(), test_id, Toast.LENGTH_SHORT).show();
    }
}
