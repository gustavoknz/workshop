package com.example.rafaelbelleza.camerademo;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class CameraCustomFragment extends Fragment {
    private Camera mCamera;
    private CameraPreview mPreview;
    private FrameLayout preview;
    private Context mContext;

    public TextView textProgrres;
    private TextView batteryPercent;
    private EditText ed1;
    private Button btn1;

    int counter = 0;
    int maxCounter;

    private void getBatteryPercentage() {
        BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                context.unregisterReceiver(this);
                int currentLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

                int level = -1;
                if (currentLevel >= 0 && scale > 0) {
                    level = (currentLevel * 100) / scale;
                }
                batteryPercent.setText("Battery Level Remaining: " + level + "%");
            }
        };
        IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        mContext.registerReceiver(batteryLevelReceiver, batteryLevelFilter);
    }


    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File pictureFile = CameraCommon.getOutputMediaFile(CameraCommon.MEDIA_TYPE_IMAGE,counter );
            if (pictureFile == null) {
                Log.d("CameraDemo", "Error creating media file, check storage permissions");
                return;
            }

            Intent scanFileIntent = new Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(pictureFile));
            mContext.sendBroadcast(scanFileIntent);

/*            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();

                Toast.makeText(mContext, "File saved to: " + pictureFile.getPath(),
                        Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                Log.d("CameraDemo", "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d("CameraDemo", "Error accessing file: " + e.getMessage());
            }*/

            mCamera.startPreview();
        }
    };

    public CameraCustomFragment() {
        // Required empty public constructor
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            Log.e("CameraDemo", e.toString());
        }
        return c; // returns null if camera is unavailable
    }

    private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_camera_custom, container, false);

        // Create an instance of Camera
        mCamera = getCameraInstance();
        mCamera.setDisplayOrientation(90);

        mContext = container.getContext();
        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(mContext, mCamera);
        preview = (FrameLayout) rootView.findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        preview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get an image from the camera
                        mCamera.takePicture(null, null, mPicture);
                    }
                }
        );

        batteryPercent = (TextView) rootView.findViewById(R.id.batteryLevel);
        textProgrres = (TextView) rootView.findViewById(R.id.textProgrres);
        ed1 = (EditText) rootView.findViewById(R.id.ed1);
        btn1 = (Button) rootView.findViewById(R.id.btn1);
        btn1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get an image from the camera
                        maxCounter = Integer.parseInt(ed1.getText().toString());
                        counter = 0;
                      //  mCamera.takePicture(null, null, mPicture);
                        new process().execute();


                    }
                }
        );

        getBatteryPercentage();

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releaseCamera();
    }




    //------------------------------------------------------------------------------------------
    // Background Async Task to Load all product by making HTTP Request
    //------------------------------------------------------------------------------------------
    class process extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //
        }
        /*** getting All products from url  **/
        protected String doInBackground(String... args) {

           // mCamera.takePicture(null, null, mPicture);

            return null;
        }
        /*** After completing background task Dismiss the progress dialog  ***/
        protected void onPostExecute(String result) {

            textProgrres.setText(counter+" of "+maxCounter);
            getBatteryPercentage();
            counter++;
            mCamera.takePicture(null, null, mPicture);

            if(counter <= maxCounter){
                new process().execute();
            }

        }

    }
}
