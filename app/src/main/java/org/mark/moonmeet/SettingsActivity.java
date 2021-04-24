package org.mark.moonmeet;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.widget.NestedScrollView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
	public final int REQ_CD_CAM = 101;
	public final int REQ_CD_FP = 102;
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private FloatingActionButton _fab;
	private String fontName = "";
	private String color = "";
	private double time = 0;
	private double deference = 0;
	private HashMap<String, Object> map = new HashMap<>();
	private String My_Username = "";
	private String My_Avatar = "";
	private String My_Firstname = "";
	private String My_Lastname = "";
	private String My_Bio = "";
	private String My_FirebaseUID = "";
	private String My_Lastseen = "";
	private String My_Phone = "";
	private String My_PhoneStatus = "";
	private boolean is_image_picked = false;
	private boolean is_camera_picked = false;
	private String camera_name = "";
	private String camera_path = "";
	private String picker_name = "";
	private String picker_path = "";
	private boolean NotificationCenterCompleted = false;
	private HashMap<String, Object> AvatarPush = new HashMap<>();
	
	private LinearLayout topbar;
	private NestedScrollView scroller;
	private ImageView back;
	private TextView settings_text;
	private LinearLayout space_top;
	private ImageView dots;
	private LinearLayout main;
	private LinearLayout moon_info_holder;
	private LinearLayout divider;
	private LinearLayout part1_holder;
	private LinearLayout space_part1_2;
	private LinearLayout part2_holder;
	private LinearLayout space_part2_3;
	private LinearLayout part3_holder;
	private LinearLayout space_bottom;
	private TextView version;
	private CircleImageView avatar;
	private LinearLayout name_state_holder;
	private TextView name_moon;
	private TextView state_moon;
	private TextView account;
	private LinearLayout number_holder;
	private LinearLayout divider2;
	private LinearLayout name_holder;
	private LinearLayout divider3;
	private LinearLayout bio_holder;
	private TextView number;
	private TextView number_info;
	private TextView name;
	private TextView name_info;
	private TextView bio;
	private TextView bio_info;
	private TextView settings;
	private LinearLayout privacy_holder;
	private LinearLayout divider4;
	private LinearLayout chats_holder;
	private LinearLayout divider5;
	private LinearLayout devices_holder;
	private ImageView privacy_icon;
	private TextView privacy_and_security;
	private ImageView chats_icon;
	private TextView chats_settings;
	private ImageView devices_icon;
	private TextView devices;
	private TextView help;
	private LinearLayout answered_holder;
	private LinearLayout divider6;
	private LinearLayout faq_holder;
	private LinearLayout divider7;
	private LinearLayout policy_holder;
	private ImageView ask_icon;
	private TextView answered_questions;
	private ImageView faq_icon;
	private TextView moonmeet_faq;
	private ImageView policy_icon;
	private TextView privacy_policy;
	
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
	private Calendar calendar = Calendar.getInstance();
	private Intent toContinue = new Intent();
	private SharedPreferences my_data;
	private SharedPreferences sp_pc;
	private SharedPreferences sp_mydt;
	private SharedPreferences passcode;
	private SharedPreferences sp_df;
	private SharedPreferences sp_sc;
	private SharedPreferences sp_seen;
	private SharedPreferences EnableViewers;
	private SharedPreferences sp_lm;
	private Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	private File _file_cam;
	private Intent fp = new Intent(Intent.ACTION_GET_CONTENT);
	private StorageReference propic = _firebase_storage.getReference("propics");
	private OnCompleteListener<Uri> _propic_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _propic_download_success_listener;
	private OnSuccessListener _propic_delete_success_listener;
	private OnProgressListener _propic_upload_progress_listener;
	private OnProgressListener _propic_download_progress_listener;
	private OnFailureListener _propic_failure_listener;
	private SharedPreferences MyStoryData;
	private Intent toPickImage = new Intent();
	private SharedPreferences CatchedImagePath;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.settings);
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
		_fab = (FloatingActionButton) findViewById(R.id._fab);
		
		topbar = (LinearLayout) findViewById(R.id.topbar);
		scroller = (NestedScrollView) findViewById(R.id.scroller);
		back = (ImageView) findViewById(R.id.back);
		settings_text = (TextView) findViewById(R.id.settings_text);
		space_top = (LinearLayout) findViewById(R.id.space_top);
		dots = (ImageView) findViewById(R.id.dots);
		main = (LinearLayout) findViewById(R.id.main);
		moon_info_holder = (LinearLayout) findViewById(R.id.moon_info_holder);
		divider = (LinearLayout) findViewById(R.id.divider);
		part1_holder = (LinearLayout) findViewById(R.id.part1_holder);
		space_part1_2 = (LinearLayout) findViewById(R.id.space_part1_2);
		part2_holder = (LinearLayout) findViewById(R.id.part2_holder);
		space_part2_3 = (LinearLayout) findViewById(R.id.space_part2_3);
		part3_holder = (LinearLayout) findViewById(R.id.part3_holder);
		space_bottom = (LinearLayout) findViewById(R.id.space_bottom);
		version = (TextView) findViewById(R.id.version);
		avatar = (CircleImageView) findViewById(R.id.avatar);
		name_state_holder = (LinearLayout) findViewById(R.id.name_state_holder);
		name_moon = (TextView) findViewById(R.id.name_moon);
		state_moon = (TextView) findViewById(R.id.state_moon);
		account = (TextView) findViewById(R.id.account);
		number_holder = (LinearLayout) findViewById(R.id.number_holder);
		divider2 = (LinearLayout) findViewById(R.id.divider2);
		name_holder = (LinearLayout) findViewById(R.id.name_holder);
		divider3 = (LinearLayout) findViewById(R.id.divider3);
		bio_holder = (LinearLayout) findViewById(R.id.bio_holder);
		number = (TextView) findViewById(R.id.number);
		number_info = (TextView) findViewById(R.id.number_info);
		name = (TextView) findViewById(R.id.name);
		name_info = (TextView) findViewById(R.id.name_info);
		bio = (TextView) findViewById(R.id.bio);
		bio_info = (TextView) findViewById(R.id.bio_info);
		settings = (TextView) findViewById(R.id.settings);
		privacy_holder = (LinearLayout) findViewById(R.id.privacy_holder);
		divider4 = (LinearLayout) findViewById(R.id.divider4);
		chats_holder = (LinearLayout) findViewById(R.id.chats_holder);
		divider5 = (LinearLayout) findViewById(R.id.divider5);
		devices_holder = (LinearLayout) findViewById(R.id.devices_holder);
		privacy_icon = (ImageView) findViewById(R.id.privacy_icon);
		privacy_and_security = (TextView) findViewById(R.id.privacy_and_security);
		chats_icon = (ImageView) findViewById(R.id.chats_icon);
		chats_settings = (TextView) findViewById(R.id.chats_settings);
		devices_icon = (ImageView) findViewById(R.id.devices_icon);
		devices = (TextView) findViewById(R.id.devices);
		help = (TextView) findViewById(R.id.help);
		answered_holder = (LinearLayout) findViewById(R.id.answered_holder);
		divider6 = (LinearLayout) findViewById(R.id.divider6);
		faq_holder = (LinearLayout) findViewById(R.id.faq_holder);
		divider7 = (LinearLayout) findViewById(R.id.divider7);
		policy_holder = (LinearLayout) findViewById(R.id.policy_holder);
		ask_icon = (ImageView) findViewById(R.id.ask_icon);
		answered_questions = (TextView) findViewById(R.id.answered_questions);
		faq_icon = (ImageView) findViewById(R.id.faq_icon);
		moonmeet_faq = (TextView) findViewById(R.id.moonmeet_faq);
		policy_icon = (ImageView) findViewById(R.id.policy_icon);
		privacy_policy = (TextView) findViewById(R.id.privacy_policy);
		Fauth = FirebaseAuth.getInstance();
		my_data = getSharedPreferences("my_data", Activity.MODE_PRIVATE);
		sp_pc = getSharedPreferences("sp_pc", Activity.MODE_PRIVATE);
		sp_mydt = getSharedPreferences("sp_mydt", Activity.MODE_PRIVATE);
		passcode = getSharedPreferences("passcode", Activity.MODE_PRIVATE);
		sp_df = getSharedPreferences("sp_df", Activity.MODE_PRIVATE);
		sp_sc = getSharedPreferences("sp_sc", Activity.MODE_PRIVATE);
		sp_seen = getSharedPreferences("sp_seen", Activity.MODE_PRIVATE);
		EnableViewers = getSharedPreferences("sp_ev", Activity.MODE_PRIVATE);
		sp_lm = getSharedPreferences("lastmessage", Activity.MODE_PRIVATE);
		_file_cam = FileUtil.createNewPictureFile(getApplicationContext());
		Uri _uri_cam = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			_uri_cam= FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", _file_cam);
		}
		else {
			_uri_cam = Uri.fromFile(_file_cam);
		}
		cam.putExtra(MediaStore.EXTRA_OUTPUT, _uri_cam);
		cam.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		fp.setType("image/*");
		fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		MyStoryData = getSharedPreferences("MyStoryData", Activity.MODE_PRIVATE);
		CatchedImagePath = getSharedPreferences("CatchedImagePath", Activity.MODE_PRIVATE);
		
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		dots.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_Popup();
			}
		});
		
		name_holder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				toContinue.setClass(getApplicationContext(), ChangeusernameActivity.class);
				startActivity(toContinue);
			}
		});
		
		bio_holder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				toContinue.setClass(getApplicationContext(), ChangebioActivity.class);
				startActivity(toContinue);
			}
		});
		
		privacy_holder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				toContinue.setClass(getApplicationContext(), PrivacySettingsActivity.class);
				startActivity(toContinue);
			}
		});
		
		chats_holder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				SketchwareUtil.showMessage(getApplicationContext(), "Soon.");
			}
		});
		
		devices_holder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				toContinue.setClass(getApplicationContext(), DevicesActivity.class);
				startActivity(toContinue);
			}
		});
		
		faq_holder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				toContinue.setClass(getApplicationContext(), FaqInfoActivity.class);
				startActivity(toContinue);
			}
		});
		
		policy_holder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				toContinue.setClass(getApplicationContext(), TermsandprivacyActivity.class);
				startActivity(toContinue);
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_BottomSheet();
			}
		});
		
		_users_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					if (_childValue.containsKey("uid") && (_childValue.containsKey("avatar") && (_childValue.containsKey("firstname") && (_childValue.containsKey("lastname") && (_childValue.containsKey("bio") && (_childValue.containsKey("phone") && _childValue.containsKey("username"))))))) {
						My_Username = _childValue.get("username").toString();
						My_Avatar = _childValue.get("avatar").toString();
						My_Firstname = _childValue.get("firstname").toString();
						My_Lastname = _childValue.get("lastname").toString();
						My_Bio = _childValue.get("bio").toString();
						My_FirebaseUID = _childValue.get("uid").toString();
						My_Lastseen = _childValue.get("last_seen").toString();
						My_Phone = _childValue.get("phone").toString();
						My_PhoneStatus = _childValue.get("phone_status").toString();
						com.bumptech.glide.Glide.with(getApplicationContext())
						.load(My_Avatar)
						.override(1024, 1024)
						.into(avatar);
						name_moon.setText(My_Firstname.concat(" ".concat(My_Lastname)));
						if (My_PhoneStatus.equals("none")) {
							number.setText(My_Phone);
						}
						else {
							number.setText("Unknown");
						}
						if (My_Bio.equals("")) {
							bio.setText("Bio");
							bio_info.setText("Add a few words about yourself");
						}
						else {
							bio.setText(My_Bio);
							bio_info.setText("Bio");
						}
						if (My_Lastseen.equals("private")) {
							state_moon.setText("Last Seen Recently");
						}
						else {
							calendar = Calendar.getInstance();
							map = new HashMap<>();
							map.put("last_seen", String.valueOf((long)(calendar.getTimeInMillis())));
							users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(map);
							map.clear();
							_NewTime(Double.parseDouble(_childValue.get("last_seen").toString()), state_moon);
						}
						if (_childValue.get("username").toString().equals("")) {
							name.setText("None");
							name_info.setText("Username");
						}
						else {
							name.setText(_childValue.get("username").toString());
							name_info.setText("Username");
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
					if (_childValue.containsKey("uid") && (_childValue.containsKey("avatar") && (_childValue.containsKey("firstname") && (_childValue.containsKey("lastname") && (_childValue.containsKey("bio") && (_childValue.containsKey("phone") && _childValue.containsKey("username"))))))) {
						My_Username = _childValue.get("username").toString();
						My_Avatar = _childValue.get("avatar").toString();
						My_Firstname = _childValue.get("firstname").toString();
						My_Lastname = _childValue.get("lastname").toString();
						My_Bio = _childValue.get("bio").toString();
						My_FirebaseUID = _childValue.get("uid").toString();
						My_Lastseen = _childValue.get("last_seen").toString();
						My_Phone = _childValue.get("phone").toString();
						My_PhoneStatus = _childValue.get("phone_status").toString();
						name_moon.setText(My_Firstname.concat(" ".concat(My_Lastname)));
						com.bumptech.glide.Glide.with(getApplicationContext())
						.load(My_Avatar)
						.override(1024, 1024)
						.into(avatar);
						if (My_PhoneStatus.equals("none")) {
							number.setText(My_Phone);
						}
						else {
							number.setText("Unknown");
						}
						if (My_Bio.equals("")) {
							bio.setText("Bio");
							bio_info.setText("Add a few words about yourself");
						}
						else {
							bio.setText(My_Bio);
							bio_info.setText("Bio");
						}
						if (My_Lastseen.equals("private")) {
							state_moon.setText("Last Seen Recently");
						}
						else {
							_NewTime(Double.parseDouble(My_Lastseen), state_moon);
						}
						if (_childValue.get("username").toString().equals("")) {
							name.setText("None");
							name_info.setText("Username");
						}
						else {
							name.setText(_childValue.get("username").toString());
							name_info.setText("Username");
						}
					}
				}
				if (NotificationCenterCompleted) {
					_Custom_Loading(false);
					NotificationCenterCompleted = false;
					// onBackPressed
					finishAfterTransition();
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
		
		_propic_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_propic_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_propic_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				NotificationCenterCompleted = true;
				if (NotificationCenterCompleted) {
					AvatarPush = new HashMap<>();
					AvatarPush.put("avatar", _downloadUrl);
					users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(AvatarPush);
					AvatarPush.clear();
				}
			}
		};
		
		_propic_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				
			}
		};
		
		_propic_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_propic_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				
			}
		};
		
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
		updateStatusBar();
		topbar.setElevation((int)2);
		fontName = "fonts/".concat("roboto_light".concat(".ttf"));
		overrideFonts(this,getWindow().getDecorView()); 
	} 
	private void overrideFonts(final android.content.Context context, final View v) {
		
		try {
				Typeface 
				typeace = Typeface.createFromAsset(getAssets(), fontName);;
				if ((v instanceof ViewGroup)) {
						ViewGroup vg = (ViewGroup) v;
						for (int i = 0;
						i < vg.getChildCount();
						i++) {
								View child = vg.getChildAt(i);
								overrideFonts(context, child);
						}
				}
				else {
						if ((v instanceof TextView)) {
								((TextView) v).setTypeface(typeace);
						}
						else {
								if ((v instanceof EditText )) {
										((EditText) v).setTypeface(typeace);
								}
								else {
										if ((v instanceof Button)) {
												((Button) v).setTypeface(typeace);
										}
								}
						}
				}
		}
		catch(Exception e)
		
		{
				SketchwareUtil.showMessage(getApplicationContext(), "Error Loading Font");
		};
		_fab.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#FF193566")));
		color = "#FF193566";
		back.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor(color),
			Color.parseColor(color)}));
		dots.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor(color),
			Color.parseColor(color)}));
		privacy_icon.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor(color),
			Color.parseColor(color)}));
		chats_icon.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor(color),
			Color.parseColor(color)}));
		devices_icon.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor(color),
			Color.parseColor(color)}));
		ask_icon.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor(color),
			Color.parseColor(color)}));
		faq_icon.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor(color),
			Color.parseColor(color)}));
		policy_icon.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor(color),
			Color.parseColor(color)}));
		_RippleEffects("#FFDADADA", back);
		_RippleEffects("#FFDADADA", dots);
		androidx.appcompat.widget.TooltipCompat.setTooltipText(back,"back");
		scroller.getViewTreeObserver()
		       .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
			            @Override
			            public void onScrollChanged() {
				                if (scroller.getChildAt(0).getBottom()
				                     <= (scroller.getHeight() + scroller.getScrollY())) {
					                    _fab.hide();
					                } else {
					                    _fab.show();
					                }
				            }
			        });
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		switch (_requestCode) {
			case REQ_CD_CAM:
			if (_resultCode == Activity.RESULT_OK) {
				 String _filePath = _file_cam.getAbsolutePath();
				
				is_camera_picked = true;
				camera_name = "MoonMeetCamera".concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(11111111111111d), (int)(99999999999999d)))));
				camera_path = _filePath;
				if (is_camera_picked) {
					_Custom_Loading(true);
					propic.child(camera_name).putFile(Uri.fromFile(new File(camera_path))).addOnFailureListener(_propic_failure_listener).addOnProgressListener(_propic_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
						@Override
						public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
							return propic.child(camera_name).getDownloadUrl();
						}}).addOnCompleteListener(_propic_upload_success_listener);
					is_camera_picked = false;
				}
			}
			else {
				
			}
			break;
			
			case REQ_CD_FP:
			if (_resultCode == Activity.RESULT_OK) {
				ArrayList<String> _filePath = new ArrayList<>();
				if (_data != null) {
					if (_data.getClipData() != null) {
						for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
							ClipData.Item _item = _data.getClipData().getItemAt(_index);
							_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
						}
					}
					else {
						_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
					}
				}
				is_image_picked = true;
				picker_path = _filePath.get((int)(0));
				picker_name = Uri.parse(picker_path).getLastPathSegment();
				if (is_image_picked) {
					_Custom_Loading(true);
					propic.child(picker_name).putFile(Uri.fromFile(new File(picker_path))).addOnFailureListener(_propic_failure_listener).addOnProgressListener(_propic_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
						@Override
						public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
							return propic.child(picker_name).getDownloadUrl();
						}}).addOnCompleteListener(_propic_upload_success_listener);
					is_image_picked = false;
				}
			}
			else {
				
			}
			break;
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
		if (!CatchedImagePath.getString("LatestImagePath", "").equals("-")) {
			is_camera_picked = true;
			camera_name = "MoonMeetUpdateProfile".concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(11111111111111d), (int)(99999999999999d)))));
			camera_path = CatchedImagePath.getString("LatestImagePath", "");
			if (is_camera_picked) {
				_Custom_Loading(true);
				propic.child(camera_name).putFile(Uri.fromFile(new File(camera_path))).addOnFailureListener(_propic_failure_listener).addOnProgressListener(_propic_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
					@Override
					public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
						return propic.child(camera_name).getDownloadUrl();
					}}).addOnCompleteListener(_propic_upload_success_listener);
				CatchedImagePath.edit().putString("LatestImagePath", "-").commit();
				is_camera_picked = false;
			}
		}
	}
	public void _NewTime (final double _position, final TextView _textview) {
		time = _position;
		calendar = Calendar.getInstance();
		deference = calendar.getTimeInMillis() - time;
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
					calendar.setTimeInMillis((long)(time));
					_textview.setText("Active on ".concat(new SimpleDateFormat("EEEE, MMMM d").format(calendar.getTime())));
				}
			}
		}
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
	
	
	public void _Popup () {
		View popupView = getLayoutInflater().inflate(R.layout.toolsup, null);
		final PopupWindow popup = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
		TextView tx1 = popupView.findViewById(R.id.tx1);
		
		TextView tx2 = popupView.findViewById(R.id.tx2);
		
		LinearLayout ly = popupView.findViewById(R.id.ly);
		
		LinearLayout lin1 = popupView.findViewById(R.id.lin1);
		
		LinearLayout lin2 = popupView.findViewById(R.id.lin2);
		
		ImageView img1 = popupView.findViewById(R.id.pen);
		
		ImageView img2 = popupView.findViewById(R.id.door);
		img1.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF616161"),
			Color.parseColor("#FF616161")}));
		img2.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF616161"),
			Color.parseColor("#FF616161")}));
		tx1.setTextColor(0xFF616161);
		tx2.setTextColor(0xFF616161);
		lin1.setOnClickListener(new OnClickListener() { public void onClick(View view) {
				toContinue.setClass(getApplicationContext(), EditNameActivity.class);
				startActivity(toContinue);
				popup.dismiss();
			} });
		
		lin2.setOnClickListener(new OnClickListener() { public void onClick(View view) {
				toContinue.setClass(getApplicationContext(), SignoutActivity.class);
				startActivity(toContinue);
				try{
					
					my_data.edit().clear().apply();
					
				} catch(Exception e) {
					
					SketchwareUtil.showMessage(getApplicationContext(), (e.toString()));
					
				}
				try{
					
					sp_pc.edit().clear().apply();
					
				} catch(Exception e) {
					
					SketchwareUtil.showMessage(getApplicationContext(), (e.toString()));
					
				}
				try{
					
					sp_mydt.edit().clear().apply();
					
				} catch(Exception e) {
					
					SketchwareUtil.showMessage(getApplicationContext(), (e.toString()));
					
				}
				try{
					
					passcode.edit().clear().apply();
					
				} catch(Exception e) {
					
					SketchwareUtil.showMessage(getApplicationContext(), (e.toString()));
					
				}
				try{
					
					sp_df.edit().clear().apply();
					
				} catch(Exception e) {
					
					SketchwareUtil.showMessage(getApplicationContext(), (e.toString()));
					
				}
				try{
					
					sp_sc.edit().clear().apply();
					
				} catch(Exception e) {
					
					SketchwareUtil.showMessage(getApplicationContext(), (e.toString()));
					
				}
				try{
					
					sp_seen.edit().clear().apply();
					
				} catch(Exception e) {
					
					SketchwareUtil.showMessage(getApplicationContext(), (e.toString()));
					
				}
				try{
					
					EnableViewers.edit().clear().apply();
					
				} catch(Exception e) {
					
					SketchwareUtil.showMessage(getApplicationContext(), (e.toString()));
					
				}
				try{
					
					sp_lm.edit().clear().apply();
					
				} catch(Exception e) {
					
					SketchwareUtil.showMessage(getApplicationContext(), (e.toString()));
					
				}
				try{
					
					MyStoryData.edit().clear().apply();
					
				} catch(Exception e) {
					
					SketchwareUtil.showMessage(getApplicationContext(), (e.toString()));
					
				}
				popup.dismiss();
			} });
		
		popup.setAnimationStyle(R.style.PopupAnimation);
		
		popup.showAsDropDown(dots, 0,0);
		
		popup.setBackgroundDrawable(new BitmapDrawable());
		android.graphics.drawable.GradientDrawable ln = new android.graphics.drawable.GradientDrawable ();
		ln.setColor (Color.parseColor("#FFECF0F3"));
		
		ln.setCornerRadius (20);
		
		ly.setBackground (ln);
		
		ly.setElevation(5);
	}
	
	
	public void _BottomSheet () {
		final com.google.android.material.bottomsheet.BottomSheetDialog dialog = new com.google.android.material.bottomsheet.BottomSheetDialog(SettingsActivity.this);
		View lay = getLayoutInflater().inflate(R.layout.image_options, null);
		dialog.setContentView(lay);
		dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
		LinearLayout l1 = (LinearLayout) lay.findViewById(R.id.take_linear);
		
		LinearLayout l2 = (LinearLayout) lay.findViewById(R.id.upload_linear);
		
		LinearLayout l3 = (LinearLayout) lay.findViewById(R.id.remove_linear);
		
		ImageView i1 = (ImageView) lay.findViewById(R.id.take_img);
		
		ImageView i2 = (ImageView) lay.findViewById(R.id.upload_img);
		
		ImageView i3 = (ImageView) lay.findViewById(R.id.remove_img);
		
		TextView t1 = (TextView) lay.findViewById(R.id.take_txt);
		
		TextView t2 = (TextView) lay.findViewById(R.id.upload_txt);
		
		TextView t3 = (TextView) lay.findViewById(R.id.remove_txt);
		l3.setVisibility(View.GONE);
		i1.setColorFilter(0xFF726E69, PorterDuff.Mode.MULTIPLY);
		i2.setColorFilter(0xFF726E69, PorterDuff.Mode.MULTIPLY);
		t1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/rmedium.ttf"), 0);
		t2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/rmedium.ttf"), 0);
		l1.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View _view) {
									toPickImage.setClass(getApplicationContext(), CameraActivity.class);
				toPickImage.setAction(Intent.ACTION_VIEW);
				startActivity(toPickImage);
				dialog.dismiss();
							}
					});
		l2.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View _view) {
									toPickImage.setClass(getApplicationContext(), ImagePickerActivity.class);
				toPickImage.putExtra("multiple_images", "false");
				toPickImage.setAction(Intent.ACTION_VIEW);
				startActivity(toPickImage);
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
	
	
	public void _Custom_Loading (final boolean _ifShow) {
		if (_ifShow) {
			if (coreprog == null){
				coreprog = new ProgressDialog(this);
				coreprog.setCancelable(false);
				coreprog.setCanceledOnTouchOutside(false);
				
				coreprog.requestWindowFeature(Window.FEATURE_NO_TITLE);  coreprog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
				
			}
			coreprog.setMessage(null);
			coreprog.show();
			View _view = getLayoutInflater().inflate(R.layout.waitc, null);
			LinearLayout linear_base = (LinearLayout) _view.findViewById(R.id.linear_base);
			ProgressBar pbar = (ProgressBar) _view.findViewById(R.id.prog);
			android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
			gd.setColor(Color.parseColor("#FFECF0F3"));
			gd.setCornerRadius(100);
			linear_base.setBackground(gd);
			coreprog.setContentView(_view);
		}
		else {
			if (coreprog != null){
				coreprog.dismiss();
			}
		}
	}
	private ProgressDialog coreprog;
	{
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
