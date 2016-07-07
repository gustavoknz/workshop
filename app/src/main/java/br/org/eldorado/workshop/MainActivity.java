package br.org.eldorado.workshop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnClicked(View v) {
        EditText eText = (EditText) findViewById(R.id.editText);
        String nameTyped = eText.getText().toString();

        Intent in = new Intent(this, SecondActivity.class);
        in.putExtra("name", nameTyped);

        startActivity(in);
    }
}
