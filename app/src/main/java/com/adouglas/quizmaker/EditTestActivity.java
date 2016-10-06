package com.adouglas.quizmaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditTestActivity extends Activity {

    Test test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_test);

        Intent intent = getIntent();
        String testId = intent.getStringExtra("test_id");
        if(testId != null && !testId.isEmpty())
        {
            test = Test.findById(Test.class, Integer.valueOf(testId));
            ((EditText) findViewById(R.id.txtTestName)).setText(test.name);
            ((EditText) findViewById(R.id.txtTestDescription)).setText(test.description);
            ((EditText) findViewById(R.id.txtTestTimelimit)).setText(test.timelimit);
        }
        else
        {
            test = new Test();
        }
    }

    public void onSave(View view)
    {
        String testName = ((EditText) findViewById(R.id.txtTestName)).getText().toString();
        String testDescription = ((EditText) findViewById(R.id.txtTestDescription)).getText().toString();
        String testTimeLimit = ((EditText) findViewById(R.id.txtTestTimelimit)).getText().toString();

        test.name = testName;
        test.description = testDescription;
        test.timelimit = testTimeLimit;

        test.save();

        clearFields();

        Intent intent = new Intent(this, QuestionsActivity.class);
        intent.putExtra("test_id", test.getId().toString());
        startActivity(intent);
        finish();
    }

    private void clearFields()
    {
        ((EditText) findViewById(R.id.txtTestName)).setText("");
        ((EditText) findViewById(R.id.txtTestDescription)).setText("");
        ((EditText) findViewById(R.id.txtTestTimelimit)).setText("");
    }

    public void onCancel(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
