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
        registerForContextMenu(lvChoices);
        lvChoices.setAdapter(choicesArrayAdapter);
        lvChoices.setClickable(true);
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
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo();
        switch (menuItem.getItemId())
        {
            case 1:
                editChoice(info.id);
                return true;
            case 2:
                deleteChoice(info.id);
                return true;
            default:
                return super.onContextItemSelected(menuItem);
        }
    }

    public void editChoice(long id)
    {
        Choice choice = choices.get((int)id);
        Intent intent = new Intent(this, AddChoiceActivity.class);
        intent.putExtra("choice_id", choice.getId().toString());
        startActivity(intent);
    }

    public void deleteChoice(long id)
    {
        Choice choice = choices.get((int)id);
        choices.remove(id);
        choice.delete();
        choicesArrayAdapter.notifyDataSetChanged();
    }

    public void onQuestion(View view)
    {
        Intent intent = new Intent(this, QuestionsActivity.class);
        intent.putExtra("test_id", question.test.getId().toString());
        startActivity(intent);
        finish();
    }

    public void onAddChoice(View view)
    {
        Intent intent = new Intent(this, AddChoiceActivity.class);
        intent.putExtra("question_id", question.getId().toString());
        startActivity(intent);
    }
}
