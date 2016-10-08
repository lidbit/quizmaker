package com.adouglas.quizmaker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class QuestionsExpandableActivity extends Activity {

    Test test;
    List<Question> questions;
    Map<Question, List<Choice>> questionListMap;
    ExpandableListView expListView;
    private ExpandableListAdapter expListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_expandable);

        Intent intent = getIntent();
        String testId = intent.getStringExtra("test_id");
        test = Test.findById(Test.class, Long.parseLong(testId));

        questions = Question.find(Question.class, "test = ?", testId);

        questionListMap = new LinkedHashMap<>();

        for (Question q: questions) {
            questionListMap.put(q, q.getChoices());
        }

        expListView = (ExpandableListView) findViewById(R.id.question_list);
        expListAdapter = new ExpandableListAdapter(
                this, questions, questionListMap);

        expListView.setAdapter(expListAdapter);

        registerForContextMenu(expListView);
        expListView.setLongClickable(true);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                final Choice choice = (Choice) expListAdapter.getChild(
                        groupPosition, childPosition);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(QuestionsExpandableActivity.this);
                alertDialog.setTitle("Add Choice");
                alertDialog.setMessage("Enter Question Content");

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), choice.choiceContent, Toast.LENGTH_SHORT);
                        dialog.cancel();
                    }
                });

                alertDialog.setPositiveButton("Add Choice", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), choice.choiceContent, Toast.LENGTH_SHORT);
                        dialog.cancel();
                    }
                });

                alertDialog.show();

                return true;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo)
    {
        menu.add(Menu.NONE, 1, Menu.NONE, "Edit");
        menu.add(Menu.NONE, 2, Menu.NONE, "delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem menuItem)
    {
        ExpandableListView.ExpandableListContextMenuInfo info =
                (ExpandableListView.ExpandableListContextMenuInfo) menuItem.getMenuInfo();
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
        questions.remove((int)id);
        question.delete();
        expListAdapter.notifyDataSetChanged();
    }

    public void onTest(View view)
    {
        Intent intent = new Intent(this, EditTestActivity.class);
        intent.putExtra("test_id", test.getId().toString());
        startActivity(intent);
        finish();
    }

    public void onAddQuestion(View view)
    {
        Intent intent = new Intent(this, AddQuestionActivity.class);
        intent.putExtra("test_id", test.getId().toString());
        startActivity(intent);
    }
}
