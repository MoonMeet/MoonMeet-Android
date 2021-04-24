package org.mark.moonmeet;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class PrivacySettingsActivity extends AppCompatActivity {
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String My_LastSeen = "";
	private String My_PhoneNumber = "";
	private String My_PhoneNumberStatus = "";
	private String My_FirebaseUID = "";
	private double time = 0;
	private double deference = 0;
	private HashMap<String, Object> Seen_Push = new HashMap<>();
	private HashMap<String, Object> PrivacyPush = new HashMap<>();
	
	private LinearLayout bar;
	private RelativeLayout relative_layout_holder;
	private ShapeableImageView back;
	private MaterialTextView privacy_topbar;
	private FitWindowsLinearLayout fitslinearlayout;
	private LinearLayoutCompat linearcompat;
	private LinearLayout divider_topbar;
	private LinearLayout part1_holder;
	private LinearLayout tip_linear;
	private LinearLayout part2_holder;
	private LinearLayout tip_linear2;
	private LinearLayout space;
	private TextView privacy_text;
	private LinearLayout phonenumber_holder;
	private LinearLayout phonenumber_divider;
	private LinearLayout name_holder;
	private LinearLayout last_seen_dividee;
	private TextView phonenumber;
	private LinearLayout space_between;
	private TextView status_phone;
	private TextView lastseen;
	private LinearLayout space_between2;
	private TextView status_seen;
	private TextView tip_text;
	private TextView textview5;
	private LinearLayout passcode_linear;
	private LinearLayout passcode_divider;
	private LinearLayout activesession_holder;
	private LinearLayout activesession_divider;
	private TextView passcode_text;
	private LinearLayout space_between3;
	private TextView nothing;
	private TextView active_session;
	private LinearLayout space_between4;
	private TextView nothing2;
	private TextView tip_text2;
	
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
	private Intent toContinue = new Intent();
	private Calendar c = Calendar.getInstance();
	private SharedPreferences passcode;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.privacy_settings);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		bar = (LinearLayout) findViewById(R.id.bar);
		relative_layout_holder = (RelativeLayout) findViewById(R.id.relative_layout_holder);
		back = (ShapeableImageView) findViewById(R.id.back);
		privacy_topbar = (MaterialTextView) findViewById(R.id.privacy_topbar);
		fitslinearlayout = (FitWindowsLinearLayout) findViewById(R.id.fitslinearlayout);
		linearcompat = (LinearLayoutCompat) findViewById(R.id.linearcompat);
		divider_topbar = (LinearLayout) findViewById(R.id.divider_topbar);
		part1_holder = (LinearLayout) findViewById(R.id.part1_holder);
		tip_linear = (LinearLayout) findViewById(R.id.tip_linear);
		part2_holder = (LinearLayout) findViewById(R.id.part2_holder);
		tip_linear2 = (LinearLayout) findViewById(R.id.tip_linear2);
		space = (LinearLayout) findViewById(R.id.space);
		privacy_text = (TextView) findViewById(R.id.privacy_text);
		phonenumber_holder = (LinearLayout) findViewById(R.id.phonenumber_holder);
		phonenumber_divider = (LinearLayout) findViewById(R.id.phonenumber_divider);
		name_holder = (LinearLayout) findViewById(R.id.name_holder);
		last_seen_dividee = (LinearLayout) findViewById(R.id.last_seen_dividee);
		phonenumber = (TextView) findViewById(R.id.phonenumber);
		space_between = (LinearLayout) findViewById(R.id.space_between);
		status_phone = (TextView) findViewById(R.id.status_phone);
		lastseen = (TextView) findViewById(R.id.lastseen);
		space_between2 = (LinearLayout) findViewById(R.id.space_between2);
		status_seen = (TextView) findViewById(R.id.status_seen);
		tip_text = (TextView) findViewById(R.id.tip_text);
		textview5 = (TextView) findViewById(R.id.textview5);
		passcode_linear = (LinearLayout) findViewById(R.id.passcode_linear);
		passcode_divider = (LinearLayout) findViewById(R.id.passcode_divider);
		activesession_holder = (LinearLayout) findViewById(R.id.activesession_holder);
		activesession_divider = (LinearLayout) findViewById(R.id.activesession_divider);
		passcode_text = (TextView) findViewById(R.id.passcode_text);
		space_between3 = (LinearLayout) findViewById(R.id.space_between3);
		nothing = (TextView) findViewById(R.id.nothing);
		active_session = (TextView) findViewById(R.id.active_session);
		space_between4 = (LinearLayout) findViewById(R.id.space_between4);
		nothing2 = (TextView) findViewById(R.id.nothing2);
		tip_text2 = (TextView) findViewById(R.id.tip_text2);
		Fauth = FirebaseAuth.getInstance();
		passcode = getSharedPreferences("passcode", Activity.MODE_PRIVATE);
		
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		status_phone.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_BottomSheet();
			}
		});
		
		status_seen.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_BottomSheet2();
			}
		});
		
		passcode_linear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				toContinue.setClass(getApplicationContext(), PasscodeSetupActivity.class);
				startActivity(toContinue);
			}
		});
		
		activesession_holder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				toContinue.setClass(getApplicationContext(), DevicesActivity.class);
				startActivity(toContinue);
			}
		});
		
		nothing.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				try {
					
try{
						
						passcode.edit().clear().apply();
						
					} catch(Exception e) {
						
						SketchwareUtil.showMessage(getApplicationContext(), (e.toString()));
						
					}
					SketchwareUtil.showMessage(getApplicationContext(), "Passcode cleared.");
				} catch(Exception e) {
					
SketchwareUtil.showMessage(getApplicationContext(), (e.toString()));
				}
			}
		});
		
		_users_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					if (_childValue.containsKey("uid") && (_childValue.containsKey("last_seen") && (_childValue.containsKey("phone_status") && _childValue.containsKey("phone")))) {
						My_LastSeen = _childValue.get("last_seen").toString();
						My_PhoneNumber = _childValue.get("phone").toString();
						My_PhoneNumberStatus = _childValue.get("phone_status").toString();
						My_FirebaseUID = _childValue.get("uid").toString();
						if (My_LastSeen.equals("private")) {
							status_seen.setText("Recently");
						}
						else {
							c = Calendar.getInstance();
							Seen_Push = new HashMap<>();
							Seen_Push.put("last_seen", String.valueOf((long)(c.getTimeInMillis())));
							users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(Seen_Push);
							Seen_Push.clear();
							_NewTime(Double.parseDouble(My_LastSeen), status_seen);
						}
						if (My_PhoneNumberStatus.equals("none")) {
							status_phone.setText(My_PhoneNumber);
						}
						else {
							status_phone.setText("Unknown");
						}
					}
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					if (_childValue.containsKey("uid") && (_childValue.containsKey("last_seen") && (_childValue.containsKey("phone_status") && _childValue.containsKey("phone")))) {
						My_LastSeen = _childValue.get("last_seen").toString();
						My_PhoneNumber = _childValue.get("phone").toString();
						My_PhoneNumberStatus = _childValue.get("phone_status").toString();
						My_FirebaseUID = _childValue.get("uid").toString();
						if (My_LastSeen.equals("private")) {
							status_seen.setText("Recently");
						}
						else {
							_NewTime(Double.parseDouble(My_LastSeen), status_seen);
						}
						if (My_PhoneNumberStatus.equals("none")) {
							status_phone.setText(My_PhoneNumber);
						}
						else {
							status_phone.setText("Unknown");
						}
					}
				}
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
	
	
	public void _NewTime (final double _position, final TextView _textview) {
		time = _position;
		c = Calendar.getInstance();
		deference = c.getTimeInMillis() - time;
		if (deference < 60000) {
			_textview.setText("Active now");
		}
		else {
			if (deference < (60 * 60000)) {
				_textview.setText("Active ".concat(String.valueOf((long)(deference / 60000)).concat(" Minutes ago")));
			}
			else {
				if (deference < (24 * (60 * 60000))) {
					_textview.setText("Active ".concat(String.valueOf((long)(deference / (60 * 60000))).concat(" Hours ago")));
				}
				else {
					c.setTimeInMillis((long)(time));
					_textview.setText("Active on ".concat(new SimpleDateFormat("EEEE, MMMM d").format(c.getTime())));
				}
			}
		}
	}
	
	
	public void _BottomSheet () {
		final com.google.android.material.bottomsheet.BottomSheetDialog dialog = new com.google.android.material.bottomsheet.BottomSheetDialog(this);
		View lay = getLayoutInflater().inflate(R.layout.image_options, null);
		dialog.setContentView(lay);
		dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
		LinearLayout l1 = (LinearLayout) lay.findViewById(R.id.take_linear);
		
		LinearLayout l2 = (LinearLayout) lay.findViewById(R.id.upload_linear);
		
		LinearLayout l3 = (LinearLayout) lay.findViewById(R.id.remove_linear);
		
		ImageView i1 = (ImageView) lay.findViewById(R.id.take_img);
		
		ImageView i2 = (ImageView) lay.findViewById(R.id.upload_img);
		
		ImageView i3 = (ImageView) lay.findViewById(R.id.remove_img);
		
		TextView head = (TextView) lay.findViewById(R.id.choose_txt);
		
		TextView t1 = (TextView) lay.findViewById(R.id.take_txt);
		
		TextView t2 = (TextView) lay.findViewById(R.id.upload_txt);
		
		TextView t3 = (TextView) lay.findViewById(R.id.remove_txt);
		l3.setVisibility(View.GONE);
		i1.setVisibility(View.GONE);
		i2.setVisibility(View.GONE);
		i1.setColorFilter(0xFF726E69, PorterDuff.Mode.MULTIPLY);
		i2.setColorFilter(0xFF726E69, PorterDuff.Mode.MULTIPLY);
		t1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/rmedium.ttf"), 0);
		t2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/rmedium.ttf"), 0);
		head.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/rmedium.ttf"), 0);
		head.setText("Choose a Security Option");
		head.setTextColor(0xFF193566);
		t1.setText("Show My Number");
		t2.setText("Hide My Number");
		l1.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View _view) {
									PrivacyPush = new HashMap<>();
				PrivacyPush.put("phone_status", "none");
				users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(PrivacyPush);
				PrivacyPush.clear();
				dialog.dismiss();
							}
					});
		l2.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View _view) {
									PrivacyPush = new HashMap<>();
				PrivacyPush.put("phone_status", "private");
				users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(PrivacyPush);
				PrivacyPush.clear();
				dialog.dismiss();
							}
					});
		dialog.show();
	}
	
	
	public void _BottomSheet2 () {
		final com.google.android.material.bottomsheet.BottomSheetDialog dialog = new com.google.android.material.bottomsheet.BottomSheetDialog(this);
		View lay = getLayoutInflater().inflate(R.layout.image_options, null);
		dialog.setContentView(lay);
		dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
		LinearLayout l1 = (LinearLayout) lay.findViewById(R.id.take_linear);
		
		LinearLayout l2 = (LinearLayout) lay.findViewById(R.id.upload_linear);
		
		LinearLayout l3 = (LinearLayout) lay.findViewById(R.id.remove_linear);
		
		ImageView i1 = (ImageView) lay.findViewById(R.id.take_img);
		
		ImageView i2 = (ImageView) lay.findViewById(R.id.upload_img);
		
		ImageView i3 = (ImageView) lay.findViewById(R.id.remove_img);
		
		TextView head = (TextView) lay.findViewById(R.id.choose_txt);
		
		TextView t1 = (TextView) lay.findViewById(R.id.take_txt);
		
		TextView t2 = (TextView) lay.findViewById(R.id.upload_txt);
		
		TextView t3 = (TextView) lay.findViewById(R.id.remove_txt);
		l3.setVisibility(View.GONE);
		i2.setVisibility(View.GONE);
		i1.setVisibility(View.GONE);
		i1.setColorFilter(0xFF726E69, PorterDuff.Mode.MULTIPLY);
		i2.setColorFilter(0xFF726E69, PorterDuff.Mode.MULTIPLY);
		t1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/rmedium.ttf"), 0);
		t2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/rmedium.ttf"), 0);
		head.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/rmedium.ttf"), 0);
		head.setText("Choose a Security option");
		head.setTextColor(0xFF193566);
		t1.setText("Show My Last Seen");
		t2.setText("Hide My Last Seen");
		l1.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View _view) {
									c = Calendar.getInstance();
				PrivacyPush = new HashMap<>();
				PrivacyPush.put("last_seen", String.valueOf((long)(c.getTimeInMillis())));
				users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(PrivacyPush);
				PrivacyPush.clear();
				dialog.dismiss();
							}
					});
		l2.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View _view) {
									PrivacyPush = new HashMap<>();
				PrivacyPush.put("last_seen", "private");
				users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(PrivacyPush);
				PrivacyPush.clear();
				dialog.dismiss();
							}
					});
		dialog.show();
	}
	
	
	public void _RippleEffects (final String _color, final View _view) {
		android.content.res.ColorStateList clr = new android.content.res.ColorStateList(new int[][]{new int[]{}},new int[]{Color.parseColor(_color)});
		android.graphics.drawable.RippleDrawable ripdr = new android.graphics.drawable.RippleDrawable(clr, null, null);
		_view.setBackground(ripdr);
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
