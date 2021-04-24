package org.mark.moonmeet;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PasscodeSetupActivity extends AppCompatActivity {
	
	private String addLockPattern = "";
	private String ConfirmPatternLockCode = "";
	private boolean isPassedAddingPattern = false;
	
	private LinearLayout bar;
	private LinearLayout divider;
	private LinearLayout holder;
	private ShapeableImageView back;
	private MaterialTextView privacy_topbar;
	private TextView textview1;
	private PatternLockView patternlockview;
	private PatternLockView patternlockview1;
	private LinearLayout linear1;
	private TextView cancel_text;
	private LinearLayout linear2;
	private TextView continue_text;
	
	private SharedPreferences passcode;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.passcode_setup);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		bar = (LinearLayout) findViewById(R.id.bar);
		divider = (LinearLayout) findViewById(R.id.divider);
		holder = (LinearLayout) findViewById(R.id.holder);
		back = (ShapeableImageView) findViewById(R.id.back);
		privacy_topbar = (MaterialTextView) findViewById(R.id.privacy_topbar);
		textview1 = (TextView) findViewById(R.id.textview1);
		patternlockview = (PatternLockView) findViewById(R.id.patternlockview);
		patternlockview1 = (PatternLockView) findViewById(R.id.patternlockview1);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		cancel_text = (TextView) findViewById(R.id.cancel_text);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		continue_text = (TextView) findViewById(R.id.continue_text);
		passcode = getSharedPreferences("passcode", Activity.MODE_PRIVATE);
		
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		patternlockview.addPatternLockListener(new PatternLockViewListener() {
			@Override
			public void onStarted() {
				
			}
			
			@Override
			public void onProgress(List<PatternLockView.Dot> _pattern) {
				
			}
			
			@Override
			public void onComplete(List<PatternLockView.Dot> _pattern) {
				addLockPattern = PatternLockUtils.patternToString(patternlockview, _pattern);
				patternlockview.setCorrectStateColor(0xFF64BB6A);
				textview1.setText("Pattern Recorded");
				cancel_text.setText("Clear");
				continue_text.setEnabled(true);
				isPassedAddingPattern = true;
			}
			
			@Override
			public void onCleared() {
				
			}
		});
		
		patternlockview1.addPatternLockListener(new PatternLockViewListener() {
			@Override
			public void onStarted() {
				textview1.setText("Draw pattern again to confirm");
			}
			
			@Override
			public void onProgress(List<PatternLockView.Dot> _pattern) {
				
			}
			
			@Override
			public void onComplete(List<PatternLockView.Dot> _pattern) {
				ConfirmPatternLockCode = PatternLockUtils.patternToString(patternlockview1, _pattern);
				if (addLockPattern.equals(ConfirmPatternLockCode)) {
					patternlockview1.setViewMode(PatternLockView.PatternViewMode.CORRECT);
					isPassedAddingPattern = false;
					continue_text.setEnabled(true);
				}
				else {
					textview1.setText("Pattern didn't match");
					patternlockview1.setViewMode(PatternLockView.PatternViewMode.WRONG);
				}
			}
			
			@Override
			public void onCleared() {
				
			}
		});
		
		cancel_text.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (cancel_text.getText().toString().equals("Cancel")) {
					finish();
				}
				else {
					cancel_text.setText("Cancel");
					textview1.setText("Draw an unlock pattern");
					patternlockview.clearPattern();
					patternlockview1.clearPattern();
				}
			}
		});
		
		continue_text.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (isPassedAddingPattern) {
					patternlockview.setVisibility(View.GONE);
					patternlockview1.setVisibility(View.VISIBLE);
					textview1.setText("Draw pattern again to confirm");
					cancel_text.setText("Cancel");
					patternlockview.clearPattern();
					patternlockview1.clearPattern();
					continue_text.setEnabled(true);
				}
				else {
					passcode.edit().putString("passcode", addLockPattern).commit();
					SketchwareUtil.showMessage(getApplicationContext(), "Passcode added.");
					finish();
				}
			}
		});
	}
	
	private void initializeLogic() {
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		back.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF193566"),
			Color.parseColor("#FF193566")}));
		bar.setElevation((int)2);
		continue_text.setEnabled(false);
		patternlockview.setVisibility(View.VISIBLE);
		patternlockview1.setVisibility(View.GONE);
		patternlockview.setNormalStateColor(0xFF828282);
		patternlockview.setCorrectStateColor(0xFF64BB6A);
		patternlockview.setWrongStateColor(0xFFFF1A23);
		patternlockview1.setNormalStateColor(0xFF828282);
		patternlockview1.setCorrectStateColor(0xFF64BB6A);
		patternlockview1.setWrongStateColor(0xFFFF1A23);
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
