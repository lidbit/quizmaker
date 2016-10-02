package com.adouglas.quizmaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class TestRunnerActivity extends Activity {

    TextView time;
    TextView currentQuestion;
    Test test;
    List<Question> questions;
    List<Choice> currentChoices;
    Intent testResultIntent;
    private ArrayAdapter<Choice> choicesAdapter;
    private int currentQuestionIndex = 0;
    private ListView lvChoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_runner);

        testResultIntent = new Intent(this, TestResultActivity.class);

        time = (TextView) findViewById(R.id.time_remaining);
        currentQuestion = (TextView) findViewById(R.id.question);
        Intent intent = getIntent();
        String testId = intent.getStringExtra("test_id");
        test = Test.findById(Test.class, Long.parseLong(testId));
        questions = Question.find(Question.class, "test = ?", test.getId().toString());

        lvChoices = (ListView) findViewById(R.id.lvChoices);

        currentQuestion.setText(questions.get(currentQuestionIndex).content);
        currentChoices = Choice.find(Choice.class, "question = ?", questions.get(currentQuestionIndex).getId().toString());

        choicesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, currentChoices);

        lvChoices.setAdapter(choicesAdapter);
        lvChoices.setClickable(true);

        lvChoices.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                Choice choice = (Choice) lvChoices.getItemAtPosition(position);
            }
        });
    }

    private void getNextQuestion() {
        currentQuestion.setText(questions.get(currentQuestionIndex).content);
        choicesAdapter.clear();
        currentChoices = Choice.find(Choice.class, "question = ?", questions.get(currentQuestionIndex).getId().toString());
        choicesAdapter.addAll(currentChoices);
        Log.d("", currentChoices.toString());
        choicesAdapter.notifyDataSetChanged();
        currentQuestionIndex++;
        if(currentQuestionIndex > questions.size() - 1)
        {
            currentQuestionIndex = questions.size() - 1;
        }
    }

    private void getPrevQuestion() {
        currentQuestionIndex--;
        if(currentQuestionIndex < 0)
        {
            currentQuestionIndex = 0;
        }
        currentQuestion.setText(questions.get(currentQuestionIndex).content);
        currentChoices = Choice.find(Choice.class, "question = ?", questions.get(currentQuestionIndex).getId().toString());
        choicesAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d("test runner", "started");
        new CountDownTimer(Integer.parseInt(test.timelimit) * 1000, 1000) {

            public void onTick(long millisUntilFinished) {

                time.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                time.setText("done!");
                testResultIntent.putExtra("test_id", test.getId().toString());
                startActivity(testResultIntent);
            }
        }.start();
    }

    public void onNext(View view)
    {
        getNextQuestion();
    }

    public void onPrev(View view)
    {
        getPrevQuestion();
    }
}
