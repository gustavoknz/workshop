package br.org.eldorado.workshop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private static final String[] EXAMPLES = new String[]{"Activities", "Shared Preferences", "Service"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_example, EXAMPLES);

        ListView listView = (ListView) findViewById(R.id.listExamples);
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);

        final MainActivity holder = this;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, show a toast with the TextView text
                Log.d(TAG, String.format("Item position %d tapped", position));
                //Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        startActivity(new Intent(holder, ActivitiesFirstActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(holder, ActivitiesFirstActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(holder, ServiceActivity.class));
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), R.string.example_default_text, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        listView.setBackgroundColor(getResources().getColor(android.R.color.background_light));
    }
}
