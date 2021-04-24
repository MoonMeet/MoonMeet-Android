package org.mark.moonmeet;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import org.mark.axemojiview.view.AXEmojiEditText;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class NewstoryActivity extends AppCompatActivity {
	public final int REQ_CD_FP = 101;
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private String Type = "";
	private boolean ImagePicked = false;
	private String image_path = "";
	private String image_name = "";
	private String text = "";
	private HashMap<String, Object> map = new HashMap<>();
	private String image_url = "";
	private boolean sharing = false;
	private String firstname = "";
	private String lastname = "";
	private String avatar = "";
	
	private LinearLayout linear1;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private ImageView imageview4;
	private TextView textview4;
	private TextView textview5;
	private ProgressBar progressbar1;
	private LinearLayout picktype_lin;
	private LinearLayout text_lin;
	private LinearLayout image_lin;
	private LinearLayout image_picklin;
	private LinearLayout text_picklin;
	private TextView textview1;
	private TextView textview2;
	private AXEmojiEditText edittext1;
	private ImageView imageview2;
	private AXEmojiEditText edittext2;
	
	private TimerTask t;
	private ObjectAnimator oa = new ObjectAnimator();
	private Intent fp = new Intent(Intent.ACTION_GET_CONTENT);
	private Calendar c = Calendar.getInstance();
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
	private DatabaseReference stories = _firebase.getReference("stories");
	private ChildEventListener _stories_child_listener;
	private StorageReference stories_fs = _firebase_storage.getReference("pics/stories");
	private OnCompleteListener<Uri> _stories_fs_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _stories_fs_download_success_listener;
	private OnSuccessListener _stories_fs_delete_success_listener;
	private OnProgressListener _stories_fs_upload_progress_listener;
	private OnProgressListener _stories_fs_download_progress_listener;
	private OnFailureListener _stories_fs_failure_listener;
	private DatabaseReference users = _firebase.getReference("users");
	private ChildEventListener _users_child_listener;
	private Intent toPickImage = new Intent();
	private SharedPreferences CatchedImagePath;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.newstory);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
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
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		textview4 = (TextView) findViewById(R.id.textview4);
		textview5 = (TextView) findViewById(R.id.textview5);
		progressbar1 = (ProgressBar) findViewById(R.id.progressbar1);
		picktype_lin = (LinearLayout) findViewById(R.id.picktype_lin);
		text_lin = (LinearLayout) findViewById(R.id.text_lin);
		image_lin = (LinearLayout) findViewById(R.id.image_lin);
		image_picklin = (LinearLayout) findViewById(R.id.image_picklin);
		text_picklin = (LinearLayout) findViewById(R.id.text_picklin);
		textview1 = (TextView) findViewById(R.id.textview1);
		textview2 = (TextView) findViewById(R.id.textview2);
		edittext1 = (AXEmojiEditText) findViewById(R.id.edittext1);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		edittext2 = (AXEmojiEditText) findViewById(R.id.edittext2);
		fp.setType("image/*");
		fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		Fauth = FirebaseAuth.getInstance();
		CatchedImagePath = getSharedPreferences("CatchedImagePath", Activity.MODE_PRIVATE);
		
		imageview4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		textview5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (ImagePicked) {
					if (image_path.equals("")) {
						
					}
					else {
						if (edittext2.getText().toString().trim().length() > 250) {
							((EditText)edittext2).setError("Character limit exceeded (limit is 250 characters)");
						}
						else {
							sharing = true;
							textview5.setEnabled(false);
							textview5.setAlpha((float)(0.5d));
							progressbar1.setVisibility(View.VISIBLE);
							text = edittext2.getText().toString().trim();
							stories_fs.child(image_name).putFile(Uri.fromFile(new File(image_path))).addOnFailureListener(_stories_fs_failure_listener).addOnProgressListener(_stories_fs_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
								@Override
								public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
									return stories_fs.child(image_name).getDownloadUrl();
								}}).addOnCompleteListener(_stories_fs_upload_success_listener);
						}
					}
				}
				else {
					if (edittext1.getText().toString().equals("")) {
						((EditText)edittext1).setError("If this is a text story, then it has to contain some text!");
					}
					else {
						if (edittext1.getText().toString().trim().length() > 250) {
							((EditText)edittext1).setError("Character limit exceeded (limit is 250 characters)");
						}
						else {
							sharing = true;
							textview5.setEnabled(false);
							textview5.setAlpha((float)(0.5d));
							progressbar1.setVisibility(View.VISIBLE);
							text = edittext1.getText().toString().trim();
							_share();
						}
					}
				}
			}
		});
		
		image_picklin.setOnClickListener(_view -> {
			Type = "Text";
			oa.setTarget(linear1);
			oa.setPropertyName("alpha");
			oa.setFloatValues((float)(0));
			oa.setDuration((int)(200));
			oa.start();
			t = new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(() -> {
						toPickImage.setClass(getApplicationContext(), ImagePickerActivity.class);
						toPickImage.putExtra("multiple_images", "false");
						toPickImage.setAction(Intent.ACTION_VIEW);
						startActivity(toPickImage);
						picktype_lin.setVisibility(View.GONE);
						text_lin.setVisibility(View.GONE);
						image_lin.setVisibility(View.VISIBLE);
						oa.setTarget(linear1);
						oa.setPropertyName("alpha");
						oa.setFloatValues((float)(1));
						oa.setDuration((int)(200));
						oa.start();
					});
				}
			};
			_timer.schedule(t, (int)(200));
			ImagePicked = true;
		});
		
		text_picklin.setOnClickListener(_view -> {
			Type = "Text";
			oa.setTarget(linear1);
			oa.setPropertyName("alpha");
			oa.setFloatValues((float)(0));
			oa.setDuration((int)(200));
			oa.start();
			t = new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(() -> {
						picktype_lin.setVisibility(View.GONE);
						text_lin.setVisibility(View.VISIBLE);
						image_lin.setVisibility(View.GONE);
						oa.setTarget(linear1);
						oa.setPropertyName("alpha");
						oa.setFloatValues((float)(1));
						oa.setDuration((int)(200));
						oa.start();
						textview5.setVisibility(View.VISIBLE);
					});
				}
			};
			_timer.schedule(t, (int)(210));
		});
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivityForResult(fp, REQ_CD_FP);
			}
		});
		
		_stories_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (sharing) {
					finish();
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
		stories.addChildEventListener(_stories_child_listener);
		
		_stories_fs_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_stories_fs_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_stories_fs_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				image_url = _downloadUrl;
				_share();
			}
		};
		
		_stories_fs_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				
			}
		};
		
		_stories_fs_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_stories_fs_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				
			}
		};
		
		_users_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					if (_childValue.containsKey("firstname") && _childValue.containsKey("lastname")) {
						firstname = _childValue.get("firstname").toString();
						lastname = _childValue.get("lastname").toString();
					}
					if (_childValue.containsKey("avatar")) {
						avatar = _childValue.get("avatar").toString();
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
		updateStatusBar();
		ImagePicked = false;
		linear3.setElevation((int)2);
		imageview4.setColorFilter(0xFF193566, PorterDuff.Mode.MULTIPLY);
		_gd(image_picklin, "#FFFFFF", "#E0E0E0", 20);
		_gd(text_picklin, "#FFFFFF", "#E0E0E0", 20);
		_gd(textview5, "#193566", "#00000000", 20);
		textview4.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/rmedium.ttf"), 0);
		textview5.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/rmedium.ttf"), 0);
		textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/rmedium.ttf"), 0);
		textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/rmedium.ttf"), 0);
		edittext1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/rmedium.ttf"), 0);
		edittext2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/rmedium.ttf"), 0);
		textview5.setVisibility(View.INVISIBLE);
		progressbar1.setVisibility(View.GONE);
		androidx.appcompat.widget.TooltipCompat.setTooltipText(imageview4,"Back");
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		switch (_requestCode) {
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
				image_path = _filePath.get((int)(0));
				image_name = Uri.parse(image_path).getLastPathSegment();
				imageview2.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(image_path, 1024, 1024));
				textview5.setVisibility(View.VISIBLE);
				ImagePicked = true;
			}
			else {
				if (image_path.equals("")) {
					picktype_lin.setVisibility(View.VISIBLE);
					image_lin.setVisibility(View.GONE);
				}
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
		if (CatchedImagePath.getString("LatestImagePath", "").equals("-") && ImagePicked) {
			if (image_path.equals("")) {
				picktype_lin.setVisibility(View.VISIBLE);
				image_lin.setVisibility(View.GONE);
				ImagePicked = false;
			}
		}
		else {
			if (!CatchedImagePath.getString("LatestImagePath", "").equals("-") && ImagePicked) {
				image_path = CatchedImagePath.getString("LatestImagePath", "");
				image_name = "MoonMeetStory".concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(11111111111111d), (int)(99999999999999d)))));
				imageview2.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(image_path, 1024, 1024));
				textview5.setVisibility(View.VISIBLE);
				ImagePicked = true;
				CatchedImagePath.edit().putString("LatestImagePath", "-").commit();
			}
		}
	}
	public void _gd (final View _view, final String _c, final String _sc, final double _r) {
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
		
		gd.setColor(Color.parseColor(_c));
		gd.setCornerRadius((float)_r);
		gd.setStroke(2, Color.parseColor(_sc));
		
		_view.setBackground(gd);
	}
	
	
	public void _share () {
		c = Calendar.getInstance();
		map = new HashMap<>();
		map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
		map.put("sid", stories.push().getKey());
		map.put("firstname", firstname);
		map.put("lastname", lastname);
		map.put("avatar", avatar);
		map.put("time", String.valueOf((long)(c.getTimeInMillis())));
		if (!image_url.equals("")) {
			map.put("image", image_url);
		}
		if (!text.equals("")) {
			map.put("text", text);
		}
		stories.child(map.get("sid").toString()).updateChildren(map);
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
