package br.org.eldorado.workshop;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class GridActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        GridView gridView = (GridView) findViewById(R.id.gridExample);
        gridView.setAdapter(new ImageAdapter(this));
    }
}
