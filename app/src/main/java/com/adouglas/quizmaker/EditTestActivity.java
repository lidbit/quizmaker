package com.adouglas.quizmaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_test);
    }

    public void onSave(View view)
    {
        String testName = ((EditText) findViewById(R.id.txtTestName)).getText().toString();
        String testDescription = ((EditText) findViewById(R.id.txtTestDescription)).getText().toString();
        String testTimeLimit = ((EditText) findViewById(R.id.txtTestTimelimit)).getText().toString();

        Test test = new Test();
        test.name = testName;
        test.description = testDescription;
        test.timelimit = testTimeLimit;
        test.save();

        Toast.makeText(getApplicationContext(), "Test Created", Toast.LENGTH_SHORT);
        clearFields();
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
    }
}
