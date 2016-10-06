package com.adouglas.quizmaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class TestRunnerActivity extends Activity {

    TextView time;
    TextView currentQuestion;
    TextView currentQuestionNumber;
    Test test;
    List<Question> questions;
    List<Choice> currentChoices;
    List<QuestionResult> questionResults;
    private ArrayAdapter<Choice> choicesAdapter;
    private int currentQuestionIndex = 0;
    Choice currentChoice;
    private int correctAnswers = 0;
    private ListView lvChoices;
    private AdapterView.OnItemClickListener clickListener;
    private TestResult testResult;
    private long testResultId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_runner);

        Intent intent = getIntent();
        String testId = intent.getStringExtra("test_id");
        test = Test.findById(Test.class, Long.parseLong(testId));

        questions = Question.find(Question.class, "test = ?", test.getId().toString());

        testResult = new TestResult();
        testResult.testName = test.name;
        testResult.testDescription = test.description;
        testResult.dateTaken = new Date();
        testResult.totalQuestions = questions.size();
        testResult.testId = test.getId().intValue();
        testResult.correctAnswers = correctAnswers;
        testResultId = testResult.save();

        questionResults = new ArrayList<>();

        time = (TextView) findViewById(R.id.time_remaining);
        currentQuestion = (TextView) findViewById(R.id.question);
        currentQuestionNumber = (TextView) findViewById(R.id.current_question);
        lvChoices = (ListView) findViewById(R.id.lvChoices);

        currentQuestionNumber.setText(String.valueOf(currentQuestionIndex+1));

        //TODO: Fix index out of bounds exception, when there are no questions
        currentQuestion.setText(questions.get(currentQuestionIndex).content);
        currentChoices = Choice.find(Choice.class, "question = ?", questions.get(currentQuestionIndex).getId().toString());

        choicesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, currentChoices);
        lvChoices.setAdapter(choicesAdapter);
        lvChoices.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // TODO: Issue here with not able to select a choice once it has been selected previously
        // Currently only able to select an answer once the first time.
        clickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentChoice = (Choice) lvChoices.getItemAtPosition(position);
                boolean questionAnswered = false;
                QuestionResult questionResult;
                if(currentChoice.correct)
                {
                    correctAnswers++;
                    testResult.correctAnswers = correctAnswers;
                }

                for(int i = 0; i < questionResults.size(); i++)
                {
                    if(Objects.equals(questionResults.get(i).questionContent, questions.get(currentQuestionIndex).content))
                    {
                        questionAnswered = true;
                        break;
                    }
                }

                if(!questionAnswered)
                {
                    questionResult = new QuestionResult();
                    questionResult.questionContent = questions.get(currentQuestionIndex).content;
                    questionResult.correct = currentChoice.correct;
                    questionResult.userChoice = currentChoice.choiceContent;
                    questionResult.correctChoice = questions.get(currentQuestionIndex).getCorrectChoice().choiceContent;
                    questionResult.testResult = TestResult.findById(TestResult.class, testResultId);
                    questionResult.save();

                    questionResults.add(questionResult);
                }
                else
                {
                    questionResult = questionResults.get(currentQuestionIndex);
                    questionResult.questionContent = questions.get(currentQuestionIndex).content;
                    questionResult.correct = currentChoice.correct;
                    questionResult.userChoice = currentChoice.choiceContent;
                    questionResult.correctChoice = questions.get(currentQuestionIndex).getCorrectChoice().choiceContent;
                    questionResult.save();
                }
            }
        };

        lvChoices.setOnItemClickListener(clickListener);
    }

    private void getNextQuestion() {
        currentQuestionIndex++;
        if(currentQuestionIndex > questions.size() - 1)
        {
            Button btnNext = (Button) findViewById(R.id.btnNext);
            btnNext.setEnabled(false);
            currentQuestionIndex = questions.size() - 1;
        }
        else
        {
            Button btnPrev = (Button) findViewById(R.id.btnPrev);
            btnPrev.setEnabled(true);
            currentQuestion.setText(questions.get(currentQuestionIndex).content);
            currentQuestionNumber.setText(String.valueOf(currentQuestionIndex+1));
            choicesAdapter.clear();
            currentChoices = Choice.find(Choice.class, "question = ?", questions.get(currentQuestionIndex).getId().toString());
            choicesAdapter.addAll(currentChoices);
            Log.d("", currentChoices.toString());
            choicesAdapter.notifyDataSetChanged();
        }
    }

    private void getPrevQuestion() {
        Button btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setEnabled(true);
        currentQuestionIndex--;
        if(currentQuestionIndex < 0)
        {
            Button btnPrev = (Button) findViewById(R.id.btnPrev);
            btnPrev.setEnabled(false);
            currentQuestionIndex = 0;
        }
        currentQuestionNumber.setText(String.valueOf(currentQuestionIndex+1));
        currentQuestion.setText(questions.get(currentQuestionIndex).content);
        currentChoices = Choice.find(Choice.class, "question = ?", questions.get(currentQuestionIndex).getId().toString());
        choicesAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d("test runner", "started");
        final Intent testResultIntent = new Intent(this, TestResultActivity.class);

        // For some reason I have to make a copy of
        // a variable to make it accessible in the next activity
        String trId = String.valueOf(testResultId);

        testResultIntent.putExtra("test_result_id", trId);

        new CountDownTimer(Integer.parseInt(test.timelimit) * 1000, 1000) {

            public void onTick(long millisUntilFinished) {

                time.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                time.setText("done!");
                startActivity(testResultIntent);
                finish();
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
