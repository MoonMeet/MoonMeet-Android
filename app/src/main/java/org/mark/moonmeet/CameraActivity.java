package org.mark.moonmeet;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Rational;
import android.util.Size;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageAnalysisConfig;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LifecycleOwner;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class CameraActivity extends AppCompatActivity {
	public final int REQ_CD_CAMERA = 101;
	public final int REQ_CD_FP = 102;
	
	private TextureView view_finder;
	private ImageView capture_button;
	
	private Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	private File _file_camera;
	private Intent fp = new Intent(Intent.ACTION_GET_CONTENT);
	private SharedPreferences CatchedImagePath;
	private Intent toGetImage = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.camera);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		}
		else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		view_finder = (TextureView) findViewById(R.id.view_finder);
		capture_button = (ImageView) findViewById(R.id.capture_button);
		_file_camera = FileUtil.createNewPictureFile(getApplicationContext());
		Uri _uri_camera = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			_uri_camera= FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", _file_camera);
		}
		else {
			_uri_camera = Uri.fromFile(_file_camera);
		}
		camera.putExtra(MediaStore.EXTRA_OUTPUT, _uri_camera);
		camera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		fp.setType("*/*");
		fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		CatchedImagePath = getSharedPreferences("CatchedImagePath", Activity.MODE_PRIVATE);
	}
	
	private void initializeLogic() {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		startCamera();
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	
	@Override
	public void onBackPressed() {
		finish();
	}
	
	@Override
	public void onStart() {
		super.onStart();
		if (CatchedImagePath.getString("LatestImagePath", "").equals("-") && (!CatchedImagePath.getString("VerifiedImageCaptured", "").equals("-") && CatchedImagePath.getString("BackFromPreview", "").equals("true"))) {
			CatchedImagePath.edit().putString("BackFromPreview", "false").commit();
			finish();
		}
		else {
			if (!CatchedImagePath.getString("LatestImagePath", "").equals("-") && (CatchedImagePath.getString("VerifiedImageCaptured", "").equals("-") && CatchedImagePath.getString("BackFromPreview", "").equals("true"))) {
				CatchedImagePath.edit().putString("BackFromPreview", "false").commit();
				finish();
			}
			else {
				if (CatchedImagePath.getString("LatestImagePath", "").equals("-") && (CatchedImagePath.getString("VerifiedImageCaptured", "").equals("-") && CatchedImagePath.getString("BackFromPreview", "").equals("true"))) {
					CatchedImagePath.edit().putString("BackFromPreview", "false").commit();
					finish();
				}
				else {
					
				}
			}
		}
	}
	public void _InitializeCameraX () {
	}
	    private void startCamera() {
		        //make sure there isn't another camera instance running before starting
		        CameraX.unbindAll();
		
		        /* start preview */
		        int aspRatioW = view_finder.getWidth(); //get width of screen
		        int aspRatioH = view_finder.getHeight(); //get height
		        Rational asp = new Rational (aspRatioW, aspRatioH); //aspect ratio
		        Size screen = new Size(aspRatioW, aspRatioH); //size of the screen
		
		        //config obj for preview/viewfinder thingy.
		        PreviewConfig pConfig = new PreviewConfig.Builder().setTargetAspectRatio(asp).setTargetResolution(screen).build();
		        Preview preview = new Preview(pConfig); //lets build it
		
		        preview.setOnPreviewOutputUpdateListener(
		                new Preview.OnPreviewOutputUpdateListener() {
				                    //to update the surface texture we have to destroy it first, then re-add it
				                    @Override
				                    public void onUpdated(Preview.PreviewOutput output){
						                        ViewGroup parent = (ViewGroup) view_finder.getParent();
						                        parent.removeView(view_finder);
						                        parent.addView(view_finder, 0);
						
						                        view_finder.setSurfaceTexture(output.getSurfaceTexture());
						                        updateTransform();
						                    }
				                });
		
		        /* image capture */
		
		        //config obj, selected capture mode
		        ImageCaptureConfig imgCapConfig = new ImageCaptureConfig.Builder().setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
		                .setTargetRotation(getWindowManager().getDefaultDisplay().getRotation()).build();
		        final ImageCapture imgCap = new ImageCapture(imgCapConfig);
		
		        findViewById(R.id.capture_button).setOnClickListener(new View.OnClickListener() {
				            @Override
				            public void onClick(View v) {
						                File file = new File(FileUtil.getExternalStorageDir().concat("/MoonMeet") + "/" + System.currentTimeMillis() + ".jpg");
						                imgCap.takePicture(file, new ImageCapture.OnImageSavedListener() {
								                    @Override
								                    public void onImageSaved(@NonNull File file) {
										                        String imgpath = file.getAbsolutePath();
						capture_button.setEnabled(false);
						CatchedImagePath.edit().putString("VerifiedImageCaptured", imgpath).commit();
						toGetImage.setClass(getApplicationContext(), CameraPreviewActivity.class);
						toGetImage.putExtra("CapturedImage", imgpath);
						toGetImage.setAction(Intent.ACTION_VIEW);
						startActivity(toGetImage);
						capture_button.setEnabled(true);
										                    }
								
								                    @Override
								                    public void onError(@NonNull ImageCapture.UseCaseError useCaseError, @NonNull String message, @Nullable Throwable cause) {
										                        String msg = "Photo capture failed: " + message;
										                        Toast.makeText(getBaseContext(), msg,Toast.LENGTH_LONG).show();
										                        if(cause != null){
												                            cause.printStackTrace();
												                        }
										                    }
								                });
						            }
				        });
		
		        /* image analyser */
		
		        ImageAnalysisConfig imgAConfig = new ImageAnalysisConfig.Builder().setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE).build();
		        ImageAnalysis analysis = new ImageAnalysis(imgAConfig);
		
		        analysis.setAnalyzer(
		            new ImageAnalysis.Analyzer(){
				                @Override
				                public void analyze(ImageProxy image, int rotationDegrees){
						                    //y'all can add code to analyse stuff here idek go wild.
						                }
				            });
		
		        //bind to lifecycle:
		        CameraX.bindToLifecycle((LifecycleOwner)this, analysis, imgCap, preview);
		    }
	
	    private void updateTransform(){
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
		        int rotation = (int)view_finder.getRotation(); //cast to int bc switches don't like floats
		
		        switch(rotation){ //correct output to account for display rotation
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
		
		        mx.postRotate((float)rotationDgr, centreX, centreY);
		        view_finder.setTransform(mx); //apply transformations to textureview
		    }
	private void doNothing() {
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}
