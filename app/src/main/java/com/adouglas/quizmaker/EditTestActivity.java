package com.adouglas.quizmaker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EditTestActivity extends BaseActivity {

    Test test;
    List<Question> questions;
    Map<Question, List<Choice>> questionListMap;
    ExpandableListView expListView;
    private ExpandableListAdapter expListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_test);

        Intent intent = getIntent();
        String testId = intent.getStringExtra("test_id");
        if(testId != null && !testId.isEmpty())
        {
            test = Test.findById(Test.class, Integer.valueOf(testId));
            ((EditText) findViewById(R.id.txtTestName)).setText(test.name);
            ((EditText) findViewById(R.id.txtTestDescription)).setText(test.description);
            ((EditText) findViewById(R.id.txtTestTimelimit)).setText(test.timelimit);

            questions = Question.find(Question.class, "test = ?", testId);
        }
        else
        {
            // We can always delete a test and questions
            // lets not bother with save/cancel buttons
            test = new Test();
            test.name = "test name";
            test.description = "test description";
            test.timelimit = "60";
            test.save();
            questions = new ArrayList<>();
            Question q = new Question();
            q.content = "sample question";
            q.test = test;
            q.save();
            questions.add(q);
            questionListMap = new LinkedHashMap<>();
        }

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
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditTestActivity.this);
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

        final EditText testName = (EditText)findViewById(R.id.txtTestName);
        testName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    String testNameValue = testName.getText().toString();

                    if(testNameValue != null && test.name != null
                            && !testNameValue.isEmpty()
                            && !test.name.equals(testNameValue))
                    {
                        test.name = testNameValue;
                        test.save();
                        Toast.makeText(getApplicationContext(), "Test updated", Toast.LENGTH_SHORT);
                    }
                }
            }
        });
        final EditText testDesc = (EditText) findViewById(R.id.txtTestDescription);
        testDesc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    String testDescValue = testDesc.getText().toString();
                    if(testDescValue != null && test.description != null
                            && !testDescValue.isEmpty()
                            && !test.description.equals(testDescValue))
                    {
                        test.description = testDesc.getText().toString();
                        test.save();
                        Toast.makeText(getApplicationContext(), "Test updated", Toast.LENGTH_SHORT);
                    }
                }
            }
        });
        final EditText testTime = (EditText) findViewById(R.id.txtTestTimelimit);
        testTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    String testTimeValue = testTime.getText().toString();
                    if(testTimeValue != null && test.timelimit != null
                        && !testTimeValue.isEmpty()
                        && !test.timelimit.equals(testTimeValue))
                    {
                        test.timelimit = testTime.getText().toString();
                        test.save();
                        Toast.makeText(getApplicationContext(), "Test updated", Toast.LENGTH_SHORT);
                    }
                }
            }
        });
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        test.name = ((EditText) findViewById(R.id.txtTestName)).getText().toString();
        test.description = ((EditText) findViewById(R.id.txtTestDescription)).getText().toString();
        test.timelimit = ((EditText) findViewById(R.id.txtTestTimelimit)).getText().toString();
        test.save();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        test.name = ((EditText) findViewById(R.id.txtTestName)).getText().toString();
        test.description = ((EditText) findViewById(R.id.txtTestDescription)).getText().toString();
        test.timelimit = ((EditText) findViewById(R.id.txtTestTimelimit)).getText().toString();
        test.save();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(EditTestActivity.this);
        builder.setTitle("Edit Question");
        builder.setCancelable(true);
        LayoutInflater inflater = getLayoutInflater();

        final View editQuestionView = inflater.inflate(R.layout.add_choice, null);
        final Question q = questions.get((int)id);

        EditText editText = (EditText) editQuestionView.findViewById(R.id.choiceContent);
        editText.setText(q.content);

        builder.setView(editQuestionView);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final EditText textEdit = (EditText) editQuestionView.findViewById(R.id.choiceContent);
                q.content = textEdit.getText().toString();
                q.save();

                expListAdapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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

    public void addQuestion(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditTestActivity.this);
        builder.setTitle("Add Question");
        builder.setCancelable(true);
        LayoutInflater inflater = getLayoutInflater();

        final View addQuestionView = inflater.inflate(R.layout.add_choice, null);
        final Question q = new Question();
        q.test = test;

        builder.setView(addQuestionView);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final EditText textEdit = (EditText) addQuestionView.findViewById(R.id.choiceContent);
                q.content = textEdit.getText().toString();
                q.save();

                questions.add(q);

                questionListMap.put(q, q.getChoices());
                expListAdapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void onSave(View view)
    {
        String testName = ((EditText) findViewById(R.id.txtTestName)).getText().toString();
        String testDescription = ((EditText) findViewById(R.id.txtTestDescription)).getText().toString();
        String testTimeLimit = ((EditText) findViewById(R.id.txtTestTimelimit)).getText().toString();

        test.name = testName;
        test.description = testDescription;
        test.timelimit = testTimeLimit;

        test.save();

        clearFields();
        
        Intent intent = new Intent(this, QuestionsExpandableActivity.class);
        intent.putExtra("test_id", test.getId().toString());
        startActivity(intent);
        finish();
    }

    private void clearFields()
    {
        ((EditText) findViewById(R.id.txtTestName)).setText("");
        ((EditText) findViewById(R.id.txtTestDescription)).setText("");
        ((EditText) findViewById(R.id.txtTestTimelimit)).setText("");
    }

    public void onCancel(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
