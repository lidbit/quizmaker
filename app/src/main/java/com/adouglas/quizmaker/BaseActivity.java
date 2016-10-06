package com.adouglas.quizmaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;;

public class BaseActivity extends Activity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menuHome)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else if(item.getItemId() == R.id.menuTests)
        {
            Intent intent = new Intent(this, TestsActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
