package com.example.rafaelbelleza.camerademo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;

    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                fileUri = savedInstanceState.getParcelable("fileUri");
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            CameraIntentFragment cameraIntentFragment = new CameraIntentFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            cameraIntentFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, cameraIntentFragment).commit();
        }

        cameraCustomClicked();

    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("fileUri", fileUri);
    }


    public void cameraCustomClicked() {
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        if (currentFragment instanceof CameraCustomFragment) {
            return;
        }

        // Create fragment and give it an argument specifying the article it should show
        CameraCustomFragment cameraCustomFragment = new CameraCustomFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, cameraCustomFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = null;
                if (data != null) {

                    if (data.getData() == null) {
                        bitmap = (Bitmap) data.getExtras().get("data");
                    } else {
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else {
                    bitmap = BitmapFactory.decodeFile(fileUri.getPath());
                }

                if (bitmap != null) {
                    ImageView imageView = (ImageView) findViewById(R.id.imageView);
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
}
