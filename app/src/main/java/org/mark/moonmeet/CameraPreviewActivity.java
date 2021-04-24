package org.mark.moonmeet;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Random;


public class CameraPreviewActivity extends AppCompatActivity {
	
	private String VerifiedImageCaptured_STR = "";
	
	private LinearLayout LinearBaseHolder;
	private LinearLayout topbar;
	private LinearLayout divider;
	private LinearLayout LinearSecondaryHolder;
	private LinearLayout choice_linear;
	private ImageView back;
	private TextView announcements_text;
	private LinearLayout space_top;
	private ImageView imagepreview_img;
	private ImageView cancel;
	private LinearLayout space_bottom;
	private ImageView done;
	
	private Intent toGetIntent = new Intent();
	private SharedPreferences CatchedImagePath;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.camera_preview);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
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
		LinearBaseHolder = (LinearLayout) findViewById(R.id.LinearBaseHolder);
		topbar = (LinearLayout) findViewById(R.id.topbar);
		divider = (LinearLayout) findViewById(R.id.divider);
		LinearSecondaryHolder = (LinearLayout) findViewById(R.id.LinearSecondaryHolder);
		choice_linear = (LinearLayout) findViewById(R.id.choice_linear);
		back = (ImageView) findViewById(R.id.back);
		announcements_text = (TextView) findViewById(R.id.announcements_text);
		space_top = (LinearLayout) findViewById(R.id.space_top);
		imagepreview_img = (ImageView) findViewById(R.id.imagepreview_img);
		cancel = (ImageView) findViewById(R.id.cancel);
		space_bottom = (LinearLayout) findViewById(R.id.space_bottom);
		done = (ImageView) findViewById(R.id.done);
		CatchedImagePath = getSharedPreferences("CatchedImagePath", Activity.MODE_PRIVATE);
		
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				CatchedImagePath.edit().putString("VerifiedImageCaptured", "-").commit();
				CatchedImagePath.edit().putString("LatestImagePath", "-").commit();
				CatchedImagePath.edit().putString("BackFromPreview", "true").commit();
				finish();
			}
		});
		
		done.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				CatchedImagePath.edit().putString("LatestImagePath", VerifiedImageCaptured_STR).commit();
				CatchedImagePath.edit().putString("VerifiedImageCaptured", "-").commit();
				CatchedImagePath.edit().putString("BackFromPreview", "true").commit();
				finish();
			}
		});
	}
	
	private void initializeLogic() {
		VerifiedImageCaptured_STR = CatchedImagePath.getString("VerifiedImageCaptured", "");
		toGetIntent = getIntent();
		if (toGetIntent.hasExtra("CapturedImage")) {
			imagepreview_img.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(getIntent().getStringExtra("CapturedImage"), 1024, 1024));
		}
		else {
			finish();
		}
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		announcements_text.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_light.ttf"), 1);
		cancel.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF193566"),
			Color.parseColor("#FF193566")}));
		done.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF193566"),
			Color.parseColor("#FF193566")}));
		back.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF193566"),
			Color.parseColor("#FF193566")}));
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
		CatchedImagePath.edit().putString("VerifiedImageCaptured", "-").commit();
		CatchedImagePath.edit().putString("LatestImagePath", "-").commit();
		CatchedImagePath.edit().putString("BackFromPreview", "true").commit();
		finish();
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
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
