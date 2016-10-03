package com.adouglas.quizmaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
        String questionContent = ((TextView) findViewById(R.id.QuestionContent)).getText().toString();

        Question question = new Question();
        question.content = questionContent;
        question.test = test;
        question.save();

        Intent intent = new Intent(this, ChoicesActivity.class);
        intent.putExtra("question_id", question.getId().toString());
        startActivity(intent);
    }

    public void onCancel(View view)
    {
        Intent intent = new Intent(this, QuestionsActivity.class);
        intent.putExtra("test_id", test.getId().toString());
        startActivity(intent);
    }
}
