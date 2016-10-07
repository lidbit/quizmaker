package com.adouglas.quizmaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AddQuestionActivity extends Activity {

    private Test test;
    private Question question;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        Intent intent = getIntent();
        String testId = intent.getStringExtra("test_id");
        String questionId = intent.getStringExtra("question_id");

        if(questionId != null && !questionId.isEmpty())
        {
            question = Question.findById(Question.class, Long.parseLong(questionId));
            ((TextView) findViewById(R.id.QuestionContent)).setText(question.content);
        }
        else
        {
            test = Test.findById(Test.class, Long.parseLong(testId));
        }
    }

    public void onSaveQuestion(View view)
    {
        String questionContent = ((TextView) findViewById(R.id.QuestionContent)).getText().toString();
        Intent intent = new Intent(this, ChoicesActivity.class);

        if(question != null)
        {
            question.content = questionContent;
            question.save();
            intent.putExtra("question_id", question.getId().toString());
        }
        else
        {
            Question newQuestion = new Question();
            newQuestion.content = questionContent;
            newQuestion.test = test;
            newQuestion.save();
            intent.putExtra("question_id", newQuestion.getId().toString());
        }
        startActivity(intent);
    }

    public void onCancel(View view)
    {
        Intent intent = new Intent(this, QuestionsExpandableActivity.class);
        if(question != null)
        {
            intent.putExtra("test_id", question.test.getId().toString());
        }
        else
        {
            intent.putExtra("test_id", test.getId().toString());
        }
        startActivity(intent);
    }
}
