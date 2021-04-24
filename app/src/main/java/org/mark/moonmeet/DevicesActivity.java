package org.mark.moonmeet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.FitWindowsLinearLayout;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class DevicesActivity extends AppCompatActivity {
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String My_UID = "";
	private String My_SessionTime = "";
	private String My_Session = "";
	private String My_Country = "";
	private String My_LastSeen = "";
	
	private LinearLayout bar;
	private RelativeLayout relative_layout_holder;
	private ShapeableImageView back;
	private MaterialTextView devices;
	private FitWindowsLinearLayout fitslinearlayout;
	private LinearLayoutCompat linearcompat;
	private RelativeLayout space_relative;
	private LinearLayoutCompat linearcompat_holder_device;
	private LinearLayout divider_bottom;
	private LinearLayoutCompat no_other_session_compat_linear;
	private LinearLayoutCompat linearcompat_semi_online;
	private MaterialTextView online_text;
	private MaterialTextView this_device;
	private MaterialTextView moonmeet_current_version;
	private MaterialTextView phone_status;
	private MaterialTextView country_text;
	private ShapeableImageView no_devices_icon;
	private MaterialTextView no_active_sessions;
	private MaterialTextView no_active_long_text;
	
	private FirebaseAuth Fauth;
	private OnCompleteListener<Void> Fauth_updateEmailListener;
	private OnCompleteListener<Void> Fauth_updatePasswordListener;
	private OnCompleteListener<Void> Fauth_emailVerificationSentListener;
	private OnCompleteListener<Void> Fauth_deleteUserListener;
	private OnCompleteListener<Void> Fauth_updateProfileListener;
	private OnCompleteListener<AuthResult> Fauth_phoneAuthListener;
	private OnCompleteListener<AuthResult> Fauth_googleSignInListener;
	private OnCompleteListener<AuthResult> _Fauth_create_user_listener;
	private OnCompleteListener<AuthResult> _Fauth_sign_in_listener;
	private OnCompleteListener<Void> _Fauth_reset_password_listener;
	private DatabaseReference users = _firebase.getReference("users");
	private ChildEventListener _users_child_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.devices);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		bar = (LinearLayout) findViewById(R.id.bar);
		relative_layout_holder = (RelativeLayout) findViewById(R.id.relative_layout_holder);
		back = (ShapeableImageView) findViewById(R.id.back);
		devices = (MaterialTextView) findViewById(R.id.devices);
		fitslinearlayout = (FitWindowsLinearLayout) findViewById(R.id.fitslinearlayout);
		linearcompat = (LinearLayoutCompat) findViewById(R.id.linearcompat);
		space_relative = (RelativeLayout) findViewById(R.id.space_relative);
		linearcompat_holder_device = (LinearLayoutCompat) findViewById(R.id.linearcompat_holder_device);
		divider_bottom = (LinearLayout) findViewById(R.id.divider_bottom);
		no_other_session_compat_linear = (LinearLayoutCompat) findViewById(R.id.no_other_session_compat_linear);
		linearcompat_semi_online = (LinearLayoutCompat) findViewById(R.id.linearcompat_semi_online);
		online_text = (MaterialTextView) findViewById(R.id.online_text);
		this_device = (MaterialTextView) findViewById(R.id.this_device);
		moonmeet_current_version = (MaterialTextView) findViewById(R.id.moonmeet_current_version);
		phone_status = (MaterialTextView) findViewById(R.id.phone_status);
		country_text = (MaterialTextView) findViewById(R.id.country_text);
		no_devices_icon = (ShapeableImageView) findViewById(R.id.no_devices_icon);
		no_active_sessions = (MaterialTextView) findViewById(R.id.no_active_sessions);
		no_active_long_text = (MaterialTextView) findViewById(R.id.no_active_long_text);
		Fauth = FirebaseAuth.getInstance();
		
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		_users_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					if (_childValue.containsKey("uid") && (_childValue.containsKey("session_time") && (_childValue.containsKey("session") && (_childValue.containsKey("country") && _childValue.containsKey("last_seen"))))) {
						My_UID = _childValue.get("uid").toString();
						My_SessionTime = _childValue.get("session_time").toString();
						My_Session = _childValue.get("session").toString();
						My_Country = _childValue.get("country").toString();
						My_LastSeen = _childValue.get("last_seen").toString();
						phone_status.setText(My_Session);
						country_text.setText(My_Country);
					}
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		users.addChildEventListener(_users_child_listener);
		
		Fauth_updateEmailListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		Fauth_updatePasswordListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		Fauth_emailVerificationSentListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		Fauth_deleteUserListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		Fauth_phoneAuthListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task){
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		Fauth_updateProfileListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		Fauth_googleSignInListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task){
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		_Fauth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_Fauth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_Fauth_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				
			}
		};
	}
	
	private void initializeLogic() {
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		bar.setElevation((int)2);
		back.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF193566"),
			Color.parseColor("#FF193566")}));
		no_devices_icon.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF193566"),
			Color.parseColor("#FF193566")}));
		androidx.appcompat.widget.TooltipCompat.setTooltipText(back,"Back");
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
	public void _UpdateStatus () {
	}
	private void updateStatusBar() {
				Window window = getWindow();
				window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
				window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
				window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
				window.setStatusBarColor(getResources().getColor(R.color.StatusBarColor));
				window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
				
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
