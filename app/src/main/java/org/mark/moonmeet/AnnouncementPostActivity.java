package org.mark.moonmeet;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;


public class AnnouncementPostActivity extends AppCompatActivity {
	public final int REQ_CD_FP = 101;
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private boolean posting = false;
	private String My_FirebaseUID = "";
	private String My_FirstName = "";
	private String My_LastName = "";
	private String My_Avatar = "";
	private String picpath = "";
	private String picname = "";
	private boolean isImagePicked = false;
	private HashMap<String, Object> MapVar = new HashMap<>();
	private String pid = "";
	private String picurl = "";
	
	private LinearLayout TopBar;
	private LinearLayout Divider;
	private LinearLayout mHolder;
	private ImageView Back;
	private TextView NewPost_Text;
	private LinearLayout space_top;
	private ImageView AddPhotoImage;
	private ImageView Done;
	private LinearLayout mSecondHolder;
	private ImageView Picker_Preview;
	private LinearLayout mLinearInside;
	private EditText EditText;
	
	private StorageReference Announcement_Photos = _firebase_storage.getReference("Announcement_Photos");
	private OnCompleteListener<Uri> _Announcement_Photos_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _Announcement_Photos_download_success_listener;
	private OnSuccessListener _Announcement_Photos_delete_success_listener;
	private OnProgressListener _Announcement_Photos_upload_progress_listener;
	private OnProgressListener _Announcement_Photos_download_progress_listener;
	private OnFailureListener _Announcement_Photos_failure_listener;
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
	private DatabaseReference announcement = _firebase.getReference("announcement");
	private ChildEventListener _announcement_child_listener;
	private Intent fp = new Intent(Intent.ACTION_GET_CONTENT);
	private Calendar c = Calendar.getInstance();
	private SharedPreferences CatchedImagePath;
	private Intent toPickImage = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.announcement_post);
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
		TopBar = (LinearLayout) findViewById(R.id.TopBar);
		Divider = (LinearLayout) findViewById(R.id.Divider);
		mHolder = (LinearLayout) findViewById(R.id.mHolder);
		Back = (ImageView) findViewById(R.id.Back);
		NewPost_Text = (TextView) findViewById(R.id.NewPost_Text);
		space_top = (LinearLayout) findViewById(R.id.space_top);
		AddPhotoImage = (ImageView) findViewById(R.id.AddPhotoImage);
		Done = (ImageView) findViewById(R.id.Done);
		mSecondHolder = (LinearLayout) findViewById(R.id.mSecondHolder);
		Picker_Preview = (ImageView) findViewById(R.id.Picker_Preview);
		mLinearInside = (LinearLayout) findViewById(R.id.mLinearInside);
		EditText = (EditText) findViewById(R.id.EditText);
		Fauth = FirebaseAuth.getInstance();
		fp.setType("image/*");
		fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		CatchedImagePath = getSharedPreferences("CatchedImagePath", Activity.MODE_PRIVATE);
		
		Back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		AddPhotoImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				toPickImage.setClass(getApplicationContext(), ImagePickerActivity.class);
				toPickImage.putExtra("multiple_images", "false");
				toPickImage.setAction(Intent.ACTION_VIEW);
				startActivity(toPickImage);
			}
		});
		
		Done.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				pid = announcement.push().getKey();
				if (!picpath.equals("")) {
					Announcement_Photos.child(picname).putFile(Uri.fromFile(new File(picpath))).addOnFailureListener(_Announcement_Photos_failure_listener).addOnProgressListener(_Announcement_Photos_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
						@Override
						public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
							return Announcement_Photos.child(picname).getDownloadUrl();
						}}).addOnCompleteListener(_Announcement_Photos_upload_success_listener);
				}
				else {
					if (picpath.equals("")) {
						MapVar = new HashMap<>();
						MapVar.put("uid", My_FirebaseUID);
						MapVar.put("pid", pid);
						MapVar.put("firstname", My_FirstName);
						MapVar.put("lastname", My_LastName);
						MapVar.put("avatar", My_Avatar);
						if (!EditText.getText().toString().trim().equals("")) {
							MapVar.put("text", EditText.getText().toString().trim());
						}
						MapVar.put("time", String.valueOf((long)(c.getTimeInMillis())));
						MapVar.put("image_time", String.valueOf((long)(c.getTimeInMillis())));
						announcement.child(MapVar.get("pid").toString()).updateChildren(MapVar);
						MapVar.clear();
						posting = true;
					}
				}
			}
		});
		
		_Announcement_Photos_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_Announcement_Photos_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_Announcement_Photos_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				picurl = _downloadUrl;
				_PostAnnouncement();
				posting = true;
			}
		};
		
		_Announcement_Photos_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				
			}
		};
		
		_Announcement_Photos_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_Announcement_Photos_failure_listener = new OnFailureListener() {
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
					if (_childValue.containsKey("uid") && (_childValue.containsKey("firstname") && (_childValue.containsKey("lastname") && (_childValue.containsKey("avatar") && _childValue.containsKey("phone"))))) {
						My_FirebaseUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
						My_FirstName = _childValue.get("firstname").toString();
						My_LastName = _childValue.get("lastname").toString();
						My_Avatar = _childValue.get("avatar").toString();
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
		
		_announcement_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (posting) {
					posting = false;
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
		announcement.addChildEventListener(_announcement_child_listener);
		
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
		isImagePicked = false;
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		TopBar.setElevation((int)2);
		Back.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF193566"),
			Color.parseColor("#FF193566")}));
		AddPhotoImage.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF193566"),
			Color.parseColor("#FF193566")}));
		Done.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF193566"),
			Color.parseColor("#FF193566")}));
		androidx.appcompat.widget.TooltipCompat.setTooltipText(Back,"Done");
		androidx.appcompat.widget.TooltipCompat.setTooltipText(AddPhotoImage,"Add Photo");
		androidx.appcompat.widget.TooltipCompat.setTooltipText(Done,"Done");
		Picker_Preview.setVisibility(View.INVISIBLE);
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
				picpath = CatchedImagePath.getString("LatestImagePath", "");
				picname = "Announcement".concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(11111111111111111111d), (int)(99999999999999999999d)))));
				Picker_Preview.setVisibility(View.VISIBLE);
				Picker_Preview.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(picpath, 1024, 1024));
				isImagePicked = true;
				CatchedImagePath.edit().putString("LatestImagePath", "-").commit();
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
			picpath = CatchedImagePath.getString("LatestImagePath", "");
			picname = "Announcement".concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(11111111111111111111d), (int)(99999999999999999999d)))));
			Picker_Preview.setVisibility(View.VISIBLE);
			Picker_Preview.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(picpath, 1024, 1024));
			isImagePicked = true;
			CatchedImagePath.edit().putString("LatestImagePath", "-").commit();
		}
	}
	public void _PostAnnouncement () {
		if (isImagePicked && (!picpath.equals("") && !picurl.equals(""))) {
			MapVar = new HashMap<>();
			MapVar.put("uid", My_FirebaseUID);
			MapVar.put("pid", pid);
			MapVar.put("firstname", My_FirstName);
			MapVar.put("lastname", My_LastName);
			MapVar.put("avatar", My_Avatar);
			if (!EditText.getText().toString().trim().equals("")) {
				MapVar.put("text", EditText.getText().toString().trim());
			}
			MapVar.put("image", picurl);
			MapVar.put("time", String.valueOf((long)(c.getTimeInMillis())));
			MapVar.put("image_time", String.valueOf((long)(c.getTimeInMillis())));
			announcement.child(MapVar.get("pid").toString()).updateChildren(MapVar);
			MapVar.clear();
		}
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
