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

        //TODO: Create add/edit question ui
        Question question1 = new Question();
        Question question2 = new Question();
        Question question3 = new Question();
        Question question4 = new Question();

        test.save();

        question1.content = "1 + 1";
        question1.test = test;

        Choice q1Choice1 = new Choice();
        Choice q1Choice2 = new Choice();
        Choice q1Choice3 = new Choice();
        Choice q1Choice4 = new Choice();

        q1Choice1.question = question1;
        q1Choice1.choiceContent = "3";
        q1Choice1.correct = false;

        q1Choice2.question = question1;
        q1Choice2.choiceContent = "10";
        q1Choice2.correct = false;

        q1Choice3.question = question1;
        q1Choice3.choiceContent = "2";
        q1Choice3.correct = true;

        q1Choice4.question = question1;
        q1Choice4.choiceContent = "22";
        q1Choice4.correct = false;


        question2.content = "2 + 1";
        question2.test = test;

        Choice q2Choice1 = new Choice();
        Choice q2Choice2 = new Choice();
        Choice q2Choice3 = new Choice();
        Choice q2Choice4 = new Choice();

        q2Choice1.question = question2;
        q2Choice1.choiceContent = "3";
        q2Choice1.correct = true;

        q2Choice2.question = question2;
        q2Choice2.choiceContent = "10";
        q2Choice2.correct = false;

        q2Choice3.question = question2;
        q2Choice3.choiceContent = "2";
        q2Choice3.correct = false;

        q2Choice4.question = question2;
        q2Choice4.choiceContent = "22";
        q2Choice4.correct = false;


        question3.content = "2 + 5";
        question3.test = test;

        Choice q3Choice1 = new Choice();
        Choice q3Choice2 = new Choice();
        Choice q3Choice3 = new Choice();
        Choice q3Choice4 = new Choice();

        q3Choice1.question = question3;
        q3Choice1.choiceContent = "7";
        q3Choice1.correct = true;

        q3Choice2.question = question3;
        q3Choice2.choiceContent = "10";
        q3Choice2.correct = false;

        q3Choice3.question = question3;
        q3Choice3.choiceContent = "2";
        q3Choice3.correct = false;

        q3Choice4.question = question3;
        q3Choice4.choiceContent = "9";
        q3Choice4.correct = false;

        question4.content = "4 + 4";
        question4.test = test;

        Choice q4Choice1 = new Choice();
        Choice q4Choice2 = new Choice();
        Choice q4Choice3 = new Choice();
        Choice q4Choice4 = new Choice();

        q4Choice1.question = question4;
        q4Choice1.choiceContent = "8";
        q4Choice1.correct = true;

        q4Choice2.question = question4;
        q4Choice2.choiceContent = "10";
        q4Choice2.correct = false;

        q4Choice3.question = question4;
        q4Choice3.choiceContent = "2";
        q4Choice3.correct = false;

        q4Choice4.question = question4;
        q4Choice4.choiceContent = "9";
        q4Choice4.correct = false;


        question1.save();

        q1Choice1.save();
        q1Choice2.save();
        q1Choice3.save();
        q1Choice4.save();


        question2.save();

        q2Choice1.save();
        q2Choice2.save();
        q2Choice3.save();
        q2Choice4.save();


        question3.save();

        q3Choice1.save();
        q3Choice2.save();
        q3Choice3.save();
        q3Choice4.save();


        question4.save();

        q4Choice1.save();
        q4Choice2.save();
        q4Choice3.save();
        q4Choice4.save();


        clearFields();
        Intent intent = new Intent(this, TestsActivity.class);
        startActivity(intent);
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
