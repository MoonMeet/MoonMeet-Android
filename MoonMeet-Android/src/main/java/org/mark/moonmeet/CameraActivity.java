package org.mark.moonmeet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Matrix;
import android.util.Rational;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageAnalysisConfig;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;

import org.mark.moonmeet.ui.BaseFragment;
import org.mark.moonmeet.utils.AndroidUtilities;
import org.mark.moonmeet.utils.CustomLifeCycle;

import java.io.File;

public class CameraActivity extends BaseFragment {

    private TextureView view_finder;
    private ImageView capture_button;

    private CustomLifeCycle customLifecycle;

    private SharedPreferences CatchedImagePath;
    private Intent toGetImage = new Intent();

    @Override
    public View createView(Context context) {
        fragmentView = new FrameLayout(getParentActivity());
        actionBar.setAddToContainer(false);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.camera, (ViewGroup) fragmentView, false);
        ((ViewGroup) fragmentView).addView(view);
        customLifecycle = new CustomLifeCycle();
        customLifecycle.doOnCreate();
        initialize(context);
        fragmentView.setOnTouchListener((v, event) -> true);
        return fragmentView;
    }

    private void initialize(Context context) {
        view_finder = findViewById(R.id.view_finder);
        capture_button = findViewById(R.id.capture_button);
        CatchedImagePath = getParentActivity().getSharedPreferences("CatchedImagePath", Activity.MODE_PRIVATE);
        startCamera();
    }

    @Override
    public void onFragmentDestroy() {
        customLifecycle.doOnDestroy();
    }

    @Override
    public boolean onBackPressed() {
        customLifecycle.doOnDestroy();
        return true;
    }

    @Override
    public void onResume() {
        customLifecycle.doOnResume();
    }

    @Override
    protected void onTransitionAnimationEnd(boolean isOpen, boolean backward) {
        if (!CatchedImagePath.getString("LatestImagePath", "").equals("-")) {
            finishFragment();
        }
        customLifecycle.doOnStart();
    }

    public void startCamera() {
        //make sure there isn't another camera instance running before starting
        CameraX.unbindAll();

        /* start preview */
        int aspRatioW = view_finder.getWidth(); //get width of screen
        int aspRatioH = view_finder.getHeight(); //get height
        Rational asp = new Rational(aspRatioW, aspRatioH); //aspect ratio
        Size screen = new Size(aspRatioW, aspRatioH); //size of the screen

        //config obj for preview/viewfinder thingy.
        PreviewConfig pConfig = new PreviewConfig.Builder().setTargetAspectRatio(asp).setTargetResolution(screen).build();
        Preview preview = new Preview(pConfig); //lets build it

        //to update the surface texture we have to destroy it first, then re-add it
        preview.setOnPreviewOutputUpdateListener(
                output -> {
                    ViewGroup parent = (ViewGroup) view_finder.getParent();
                    parent.removeView(view_finder);
                    parent.addView(view_finder, 0);

                    view_finder.setSurfaceTexture(output.getSurfaceTexture());
                    updateTransform();
                });

        /* image capture */

        //config obj, selected capture mode
        ImageCaptureConfig imgCapConfig = new ImageCaptureConfig.Builder().setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                .setTargetRotation(getParentActivity().getWindowManager().getDefaultDisplay().getRotation()).build();
        final ImageCapture imgCap = new ImageCapture(imgCapConfig);

        findViewById(R.id.capture_button).setOnClickListener(v -> {
            File file = new File(FileUtil.getExternalStorageDir().concat("/MoonMeet") + "/" + System.currentTimeMillis() + ".jpg");
            imgCap.takePicture(file, new ImageCapture.OnImageSavedListener() {
                @Override
                public void onImageSaved(@NonNull File file) {
                    String imgpath = file.getAbsolutePath();
                    capture_button.setEnabled(false);
                    CatchedImagePath.edit().putString("LatestImagePath", imgpath).apply();
                    finishFragment();
                    capture_button.setEnabled(true);
                }

                @Override
                public void onError(@NonNull ImageCapture.UseCaseError useCaseError, @NonNull String message, @Nullable Throwable cause) {
                    String msg = "Photo capture failed: " + message;
                    AndroidUtilities.showToast(msg);
                    if (cause != null) {
                        cause.printStackTrace();
                    }
                }
            });
        });

        /* image analyser */

        ImageAnalysisConfig imgAConfig = new ImageAnalysisConfig.Builder().setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE).build();
        ImageAnalysis analysis = new ImageAnalysis(imgAConfig);

        analysis.setAnalyzer(
                (image, rotationDegrees) -> {
                    //y'all can add code to analyse stuff here idk go wild.
                });

        //bind to lifecycle:
        CameraX.bindToLifecycle(customLifecycle, analysis, imgCap, preview);

        // CameraX.bindToLifecycle((LifecycleOwner) this, analysis, imgCap, preview);
    }

    public void updateTransform() {
        /*
         * compensates the changes in orientation for the viewfinder, bc the rest of the layout stays in portrait mode.
         * methinks :thonk:
         * imgCap does this already, this class can be commented out or be used to optimise the preview
         */
        Matrix mx = new Matrix();
        float w = view_finder.getMeasuredWidth();
        float h = view_finder.getMeasuredHeight();

        float centreX = w / 2f; //calc centre of the viewfinder
        float centreY = h / 2f;

        int rotationDgr;
        int rotation = (int) view_finder.getRotation(); //cast to int bc switches don't like floats

        switch (rotation) { //correct output to account for display rotation
            case Surface.ROTATION_0:
                rotationDgr = 0;
                break;
            case Surface.ROTATION_90:
                rotationDgr = 90;
                break;
            case Surface.ROTATION_180:
                rotationDgr = 180;
                break;
            case Surface.ROTATION_270:
                rotationDgr = 270;
                break;
            default:
                return;
        }

        mx.postRotate((float) rotationDgr, centreX, centreY);
        view_finder.setTransform(mx); //apply transformations to TextTureView
    }

}
