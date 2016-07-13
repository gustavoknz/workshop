package br.org.eldorado.workshop;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ExampleService extends Service {
    private static final String TAG = "GalleryService";
    private static final int MAX_EXECUTIONS = 20;
    private int count = 0;
    private Timer timer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Starting service...");
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.d(TAG, String.format("Running service at %s (%d)", new Date(), ++count));
                if (count >= MAX_EXECUTIONS) {
                    Log.d(TAG, "Calling stop...");
                    stopSelf();
                }
            }
        }, 0, 3000);

        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        Log.d(TAG, "Service started");

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        Log.d(TAG, "Service Destroyed");
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }
}
