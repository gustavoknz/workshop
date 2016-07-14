package br.org.eldorado.workshop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ActivitiesFirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_first);

        // Get text from SharedPreferences
        SharedPreferences sp = getSharedPreferences(Constants.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        String defaultName = "";
        String name = sp.getString(Constants.SHARED_PREFERENCES_NAME_KEY, defaultName);

        EditText eText = (EditText) findViewById(R.id.editText);
        eText.setText(name);
    }

    public void btnClicked(View v) {
        EditText eText = (EditText) findViewById(R.id.editText);
        String nameTyped = eText.getText().toString();

        Intent in = new Intent(this, ActivitiesSecondActivity.class);
        in.putExtra("name", nameTyped);

        startActivity(in);
    }
}
