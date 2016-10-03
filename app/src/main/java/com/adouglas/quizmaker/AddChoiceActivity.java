package com.adouglas.quizmaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class AddChoiceActivity extends Activity {

    private Question question;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_choice);

        Intent intent = getIntent();
        String questionId = intent.getStringExtra("question_id");
        question = Question.findById(Question.class, Long.parseLong(questionId));
    }

    public void onCancel(View view)
    {
        Intent intent = new Intent(this, ChoicesActivity.class);
        intent.putExtra("question_id", question.getId().toString());
        startActivity(intent);
    }

    public void onSaveChoice(View view)
    {
        Choice choice = new Choice();
        choice.question = question;
        choice.choiceContent = ((TextView) findViewById(R.id.choiceContent)).getText().toString();
        choice.correct = ((CheckBox) findViewById(R.id.correct)).isChecked();
        choice.save();

        Intent intent = new Intent(this, ChoicesActivity.class);
        intent.putExtra("question_id", question.getId().toString());
        startActivity(intent);
    }
}
