package br.org.eldorado.workshop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent in = getIntent();
        String name = in.getStringExtra("name");
        String message = getResources().getString(R.string.message, name);

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);

        // Set name to Shared Preferences
        SharedPreferences.Editor sp = getSharedPreferences(Constants.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE).edit();
        sp.putString(Constants.SHARED_PREFERENCES_NAME_KEY, name);
        sp.apply();
    }
}
