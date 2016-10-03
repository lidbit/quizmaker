package com.adouglas.quizmaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class QuestionsActivity extends Activity {

    private Test test;
    private List<Question> questions;
    private ArrayAdapter<Question> questionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        ListView lvQuestions = (ListView) findViewById(R.id.lvQuestions);

        Intent intent = getIntent();
        String testId = intent.getStringExtra("test_id");
        test = Test.findById(Test.class, Long.parseLong(testId));
        questions = Question.find(Question.class, "test = ?", test.getId().toString());
        questionsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, questions);
        questionsAdapter.notifyDataSetChanged();
        lvQuestions.setAdapter(questionsAdapter);
    }

    public void onTest(View view)
    {
        Intent intent = new Intent(this, EditTestActivity.class);
        intent.putExtra("test_id", test.getId().toString());
        startActivity(intent);
    }

    public void onAddQuestion(View view)
    {
        Intent intent = new Intent(this, AddQuestionActivity.class);
        intent.putExtra("test_id", test.getId().toString());
        startActivity(intent);
    }
}
