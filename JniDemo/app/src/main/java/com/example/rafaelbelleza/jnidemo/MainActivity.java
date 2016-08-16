package com.example.rafaelbelleza.jnidemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView) findViewById(R.id.jni_msgView)).setText(stringFromJNI());
        byte[] byteArray = new byte[] {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
        };
        ((TextView) findViewById(R.id.textView)).setText(Arrays.toString(byteArray));
        byteArrayInit(byteArray);
        ((TextView) findViewById(R.id.textView2)).setText(Arrays.toString(byteArray));
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public native void byteArrayInit(byte[] byteArray);
}
