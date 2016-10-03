package com.adouglas.quizmaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AddQuestionActivity extends Activity {

    private Test test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        Intent intent = getIntent();
        String testId = intent.getStringExtra("test_id");
        test = Test.findById(Test.class, Long.parseLong(testId));
    }

    public void onAddQuestion(View view)
    {
        Question question1 = new Question();
        Question question2 = new Question();
        Question question3 = new Question();
        Question question4 = new Question();

        question1.content = "1 + 1";
        question1.test = test;

        question2.content = "2 + 1";
        question2.test = test;

        question3.content = "2 + 5";
        question3.test = test;

        question4.content = "4 + 4";
        question4.test = test;

        question1.save();
        question2.save();
        question3.save();
        question4.save();
    }

    public void onCancel(View view)
    {
        Intent intent = new Intent(this, QuestionsActivity.class);
        intent.putExtra("test_id", test.getId().toString());
        startActivity(intent);
    }
}
