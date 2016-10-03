package com.adouglas.quizmaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ChoicesActivity extends Activity {

    private Question question;
    List<Choice> choices;
    private ArrayAdapter<Choice> choicesArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choices);

        Intent intent = getIntent();
        String questionId = intent.getStringExtra("question_id");

        question = Question.findById(Question.class, Long.parseLong(questionId));
        choices = Choice.find(Choice.class, "question = ?", question.getId().toString());

        choicesArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, choices);
        choicesArrayAdapter.notifyDataSetChanged();

        ListView lvChoices = (ListView) findViewById(R.id.lvChoices);
        lvChoices.setAdapter(choicesArrayAdapter);
        lvChoices.setClickable(true);
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
