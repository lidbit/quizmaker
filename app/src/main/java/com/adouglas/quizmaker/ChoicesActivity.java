package com.adouglas.quizmaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChoicesActivity extends Activity {

    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choices);

        Intent intent = getIntent();
        String questionId = intent.getStringExtra("question_id");

        question = Question.findById(Question.class, Long.parseLong(questionId));
    }

    public void onQuestion(View view)
    {
        Intent intent = new Intent(this, AddQuestionActivity.class);
        intent.putExtra("question_id", question.getId().toString());
        startActivity(intent);
    }

    public void onAddChoice(View view)
    {
        Intent intent = new Intent(this, AddChoiceActivity.class);
        intent.putExtra("question_id", question.getId().toString());
        startActivity(intent);
    }
}
