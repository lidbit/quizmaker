package com.adouglas.quizmaker;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.adouglas.quizmaker.model.Choice;
import com.adouglas.quizmaker.model.Question;
import com.adouglas.quizmaker.model.Test;
import org.json.*;
import com.google.gson.Gson;
import com.loopj.android.http.*;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import cz.msebera.android.httpclient.Header;

public class TestsActivity extends BaseActivity {

    private ArrayAdapter<Test> testsAdapter;
    private List<Test> tests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Tests");
        actionBar.setSubtitle("Long press to edit test");
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(QuizmakerRestClient.isTokenValid())
        {
            try {
                getTests();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            tests = Test.listAll(Test.class);
            testsAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, tests);
            testsAdapter.notifyDataSetChanged();
            initialiseListView(testsAdapter);
        }
    }

    private void initialiseListView(ArrayAdapter<Test> testsAdapter) {
        final ListView listView = (ListView) findViewById(R.id.lvTests);

        registerForContextMenu(listView);
        listView.setAdapter(testsAdapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long id)
            {
                Intent intent = new Intent(getApplicationContext(), TestIntroActivity.class);
                Test test = (Test) listView.getItemAtPosition(position);
                intent.putExtra("test_id", test.getId().toString());
                startActivity(intent);
            }
        });
    }

    public void getTests() throws JSONException
    {
        QuizmakerRestClient.get("tests", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String response = responseBody == null ? null : new String(
                            responseBody, getCharset());
                    Gson gson = new Gson();
                    Test[] arr = gson.fromJson(response, Test[].class);
                    tests = Arrays.asList(arr);
                    testsAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, tests);
                    testsAdapter.notifyDataSetChanged();
                    initialiseListView(testsAdapter);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

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
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo();
        switch (menuItem.getItemId())
        {
            case 1:
                editTest(info.id);
                return true;
            case 2:
                deleteTest(info.id);
                return true;
            default:
                return super.onContextItemSelected(menuItem);
        }
    }

    private void editTest(long id)
    {
        Test test = tests.get((int)id);
        Intent intent = new Intent(this, EditTestActivity.class);
        intent.putExtra("test_id", test.getId().toString());
        startActivity(intent);
    }

    private void deleteTest(long id)
    {
        Test test = tests.get((int)id);

        List<Question> questions = Question.find(Question.class, "test = ?", test.getId().toString());

        if(questions.size() > 0)
        {
            for(int i = 0; i < questions.size(); i++)
            {
                List<Choice> choices = Choice.find(Choice.class, "question = ?", questions.get(i).getId().toString());
                if(choices.size() > 0)
                {
                    for(int j = 0; j < choices.size(); j++)
                    {
                        choices.get(j).delete();
                    }
                }
                questions.get(i).delete();
            }
        }

        tests.remove((int)id);
        testsAdapter.notifyDataSetChanged();
        // remove from db
        test.delete();

        Toast.makeText(getApplicationContext(), "Deleted test " + String.valueOf(id), Toast.LENGTH_SHORT).show();
    }
}
