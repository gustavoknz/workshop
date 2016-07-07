package br.org.eldorado.workshop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent in = getIntent();
        String name = in.getStringExtra("name");

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(name);
    }
}
