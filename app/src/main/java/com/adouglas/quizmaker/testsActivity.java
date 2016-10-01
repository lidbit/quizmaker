package com.adouglas.quizmaker;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TestsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

        Test test = new Test();
        test.name = "Arithemtic";
        test.description = "Basic math";
        test.running = false;
        test.userId = 1;
        test.timelimit = "60";
        test.save();
    }
}
