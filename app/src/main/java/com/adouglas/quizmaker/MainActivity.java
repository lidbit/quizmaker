package com.adouglas.quizmaker;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.adouglas.quizmaker.model.Choice;
import com.adouglas.quizmaker.model.Question;
import com.adouglas.quizmaker.model.QuestionResult;
import com.adouglas.quizmaker.model.Test;
import com.adouglas.quizmaker.model.TestResult;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.orm.SugarDb;

import java.io.File;

public class MainActivity extends BaseActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        //
        // Instant Run must be disabled for Sugar Orm to work!!!
        // TODO: Disable Instant run
        // In Android Studio got to
        // File -> Settings -> Build, Execution, Deployment -> Instant Run
        // Uncheck Enable Instant Run... option

        // During First run this is needed for force table creation

        try
        {
            Test.findById(Test.class, (long) 1);
            Question.findById(Question.class, (long) 1);
            Choice.findById(Choice.class, (long) 1);
            QuestionResult.findById(QuestionResult.class, (long) 1);
            TestResult.findById(TestResult.class, (long) 1);
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Please disable instant run in Android Studio", Toast.LENGTH_LONG).show();
            SugarDb sugarDb = new SugarDb(getApplicationContext());
            new File(sugarDb.getDB().getPath()).delete();
            finish();

//            Test t = new Test();
//            t.name = "test";
//            t.save();
//            Question q = new Question();
//            q.content = "1 + 1";
//            q.test = t;
//            q.save();
//            Choice c = new Choice();
//            c.choiceContent = "2";
//            c.question = q;
//            c.save();
//            TestResult tr = new TestResult();
//            tr.testName = "test";
//            tr.save();
//            QuestionResult qr = new QuestionResult();
//            qr.userChoice = "2";
//            qr.testResult = tr;
//            qr.save();
        }
    }

    public void OnTakeTest(View view) {
        Intent intent = new Intent(this, TestsActivity.class);
        startActivity(intent);
        finish();
    }

    public void onMakeTest(View view)
    {
        Intent intent = new Intent(this, EditTestActivity.class);
        startActivity(intent);
        finish();
    }

    public void onTestResults(View view)
    {
        Intent intent = new Intent(this, TestResultsActivity.class);
        startActivity(intent);
        finish();
    }
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
