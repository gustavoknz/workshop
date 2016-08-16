package com.example.rafaelbelleza.camerademo;


import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class CameraCustomFragment extends Fragment {
    private Camera mCamera;
    private CameraPreview mPreview;
    private FrameLayout preview;
    private Context mContext;

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File pictureFile = CameraCommon.getOutputMediaFile(CameraCommon.MEDIA_TYPE_IMAGE);
            if (pictureFile == null) {
                Log.d("CameraDemo", "Error creating media file, check storage permissions");
                return;
            }

            Intent scanFileIntent = new Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(pictureFile));
            mContext.sendBroadcast(scanFileIntent);

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                Toast.makeText(mContext, "File saved to: " + pictureFile.getPath(),
                        Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                Log.d("CameraDemo", "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d("CameraDemo", "Error accessing file: " + e.getMessage());
            }

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

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releaseCamera();
    }
}
