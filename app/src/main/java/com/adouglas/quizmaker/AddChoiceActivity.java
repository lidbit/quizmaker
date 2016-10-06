package com.adouglas.quizmaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class AddChoiceActivity extends Activity {

    private Question question;
    private Choice choice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_choice);

        Intent intent = getIntent();
        String questionId = intent.getStringExtra("question_id");
        String choiceId = intent.getStringExtra("choice_id");
        TextView textViewQuestionContent = (TextView) findViewById(R.id.question_content);
        if(choiceId != null && !choiceId.isEmpty())
        {
            choice = Choice.findById(Choice.class, Long.parseLong(choiceId));
            Question q = Question.findById(Question.class, choice.question.getId());
            textViewQuestionContent.setText(q.content);
            ((TextView) findViewById(R.id.choiceContent)).setText(choice.choiceContent);
            ((CheckBox) findViewById(R.id.correct)).setChecked(choice.correct);
            this.setTitle("Edit Choice");
        }
        else
        {
            question = Question.findById(Question.class, Long.parseLong(questionId));
            textViewQuestionContent.setText(question.content);
            this.setTitle("Add Choice");
        }
    }

    public void onCancel(View view)
    {
        Intent intent = new Intent(this, ChoicesActivity.class);
        if(choice != null)
        {
            intent.putExtra("question_id", choice.question.getId().toString());
        }
        else
        {
            intent.putExtra("question_id", question.getId().toString());
        }
        startActivity(intent);
    }

    public void onSaveChoice(View view)
    {
        Intent intent = new Intent(this, ChoicesActivity.class);

        if(choice != null)
        {
            choice.choiceContent = ((TextView) findViewById(R.id.choiceContent)).getText().toString();
            choice.correct = ((CheckBox) findViewById(R.id.correct)).isChecked();
            choice.save();
            intent.putExtra("question_id", choice.question.getId().toString());
        }
        else
        {
            Choice choice = new Choice();
            choice.question = question;
            choice.choiceContent = ((TextView) findViewById(R.id.choiceContent)).getText().toString();
            choice.correct = ((CheckBox) findViewById(R.id.correct)).isChecked();
            choice.save();
            intent.putExtra("question_id", choice.question.getId().toString());
        }

        startActivity(intent);
    }
}
