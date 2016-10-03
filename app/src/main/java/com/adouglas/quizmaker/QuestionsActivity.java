package com.adouglas.quizmaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;

public class QuestionsActivity extends Activity {

    Test test;
    List<Question> questions;
    private ArrayAdapter<Question> questionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        ListView lvQuestions = (ListView) findViewById(R.id.lvQuestions);
        registerForContextMenu(lvQuestions);

        Intent intent = getIntent();
        String testId = intent.getStringExtra("test_id");
        test = Test.findById(Test.class, Long.parseLong(testId));
        questions = Question.find(Question.class, "test = ?", test.getId().toString());
        questionsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, questions);
        questionsAdapter.notifyDataSetChanged();
        lvQuestions.setAdapter(questionsAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo)
    {
        menu.add(Menu.NONE, 1, Menu.NONE, "Edit");
        menu.add(Menu.NONE, 2, Menu.NONE, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem menuItem)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo();
        switch (menuItem.getItemId())
        {
            case 1:
                editQuestion(info.id);
                return true;
            case 2:
                deleteQuestion(info.id);
                return true;
            default:
                return super.onContextItemSelected(menuItem);
        }
    }

    public void editQuestion(long id)
    {
        Question question = questions.get((int)id);
        Intent intent = new Intent(this, AddQuestionActivity.class);
        intent.putExtra("question_id", question.getId().toString());
        startActivity(intent);
    }

    public void deleteQuestion(long id)
    {
        Question question = questions.get((int)id);
        List<Choice> choices = Choice.find(Choice.class, "question = ?", question.getId().toString());
        if(choices != null && choices.size() > 0)
        {
            for(int i = 0; i < choices.size(); i++)
            {
                choices.get(i).delete();
            }
        }
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
