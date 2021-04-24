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
import androidx.core.widget.NestedScrollView;

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

public class AddToSellActivity extends AppCompatActivity {
	public final int REQ_CD_FP = 101;
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private String path_name = "";
	private String path_file = "";
	private boolean isImagePicked = false;
	private boolean posting = false;
	private HashMap<String, Object> MapVar = new HashMap<>();
	private String picurl = "";
	private String pid = "";
	
	private LinearLayout topbar;
	private LinearLayout divider_top;
	private NestedScrollView NestedScroller;
	private ImageView back;
	private TextView topbar_text;
	private LinearLayout space_top;
	private ImageView add;
	private ImageView done;
	private LinearLayout holder;
	private EditText Name_EditText;
	private LinearLayout linear2;
	private EditText Price_EditText;
	private LinearLayout linear3;
	private EditText Specific_EditText;
	private LinearLayout linear4;
	private EditText Star_EditText;
	private LinearLayout linear5;
	private EditText URL_EditText;
	private LinearLayout linear6;
	private ImageView preview;
	
	private Intent fp = new Intent(Intent.ACTION_GET_CONTENT);
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
	private DatabaseReference shop = _firebase.getReference("shop");
	private ChildEventListener _shop_child_listener;
	private StorageReference ShopPics = _firebase_storage.getReference("shoppic");
	private OnCompleteListener<Uri> _ShopPics_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _ShopPics_download_success_listener;
	private OnSuccessListener _ShopPics_delete_success_listener;
	private OnProgressListener _ShopPics_upload_progress_listener;
	private OnProgressListener _ShopPics_download_progress_listener;
	private OnFailureListener _ShopPics_failure_listener;
	private Calendar c = Calendar.getInstance();
	private Intent toPickImage = new Intent();
	private SharedPreferences CatchedImagePath;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.add_to_sell);
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
		topbar = (LinearLayout) findViewById(R.id.topbar);
		divider_top = (LinearLayout) findViewById(R.id.divider_top);
		NestedScroller = (NestedScrollView) findViewById(R.id.NestedScroller);
		back = (ImageView) findViewById(R.id.back);
		topbar_text = (TextView) findViewById(R.id.topbar_text);
		space_top = (LinearLayout) findViewById(R.id.space_top);
		add = (ImageView) findViewById(R.id.add);
		done = (ImageView) findViewById(R.id.done);
		holder = (LinearLayout) findViewById(R.id.holder);
		Name_EditText = (EditText) findViewById(R.id.Name_EditText);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		Price_EditText = (EditText) findViewById(R.id.Price_EditText);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		Specific_EditText = (EditText) findViewById(R.id.Specific_EditText);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		Star_EditText = (EditText) findViewById(R.id.Star_EditText);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		URL_EditText = (EditText) findViewById(R.id.URL_EditText);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		preview = (ImageView) findViewById(R.id.preview);
		fp.setType("image/*");
		fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		Fauth = FirebaseAuth.getInstance();
		CatchedImagePath = getSharedPreferences("CatchedImagePath", Activity.MODE_PRIVATE);
		
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				toPickImage.setClass(getApplicationContext(), ImagePickerActivity.class);
				toPickImage.putExtra("multiple_images", "false");
				toPickImage.setAction(Intent.ACTION_VIEW);
				startActivity(toPickImage);
			}
		});
		
		done.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (Name_EditText.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Please fill all the blanks.");
				}
				else {
					if (Price_EditText.getText().toString().equals("")) {
						SketchwareUtil.showMessage(getApplicationContext(), "Please fill all the blanks.");
					}
					else {
						if (Specific_EditText.getText().toString().equals("")) {
							SketchwareUtil.showMessage(getApplicationContext(), "Please fill all the blanks.");
						}
						else {
							if (Star_EditText.getText().toString().equals("")) {
								SketchwareUtil.showMessage(getApplicationContext(), "Please fill all the blanks.");
							}
							else {
								if (URL_EditText.getText().toString().equals("")) {
									SketchwareUtil.showMessage(getApplicationContext(), "Please fill all the blanks.");
								}
								else {
									if (isImagePicked && !path_file.equals("")) {
										pid = shop.push().getKey();
										ShopPics.child(path_name).putFile(Uri.fromFile(new File(path_file))).addOnFailureListener(_ShopPics_failure_listener).addOnProgressListener(_ShopPics_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
											@Override
											public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
												return ShopPics.child(path_name).getDownloadUrl();
											}}).addOnCompleteListener(_ShopPics_upload_success_listener);
									}
									else {
										SketchwareUtil.showMessage(getApplicationContext(), "Please pick a image.");
									}
								}
							}
						}
					}
				}
			}
		});
		
		_shop_child_listener = new ChildEventListener() {
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
		shop.addChildEventListener(_shop_child_listener);
		
		_ShopPics_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_ShopPics_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_ShopPics_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				picurl = _downloadUrl;
				if (isImagePicked && (!path_file.equals("") && !picurl.equals(""))) {
					posting = true;
					MapVar = new HashMap<>();
					MapVar.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
					MapVar.put("pid", pid);
					MapVar.put("name", Name_EditText.getText().toString().trim());
					MapVar.put("price", Price_EditText.getText().toString().trim());
					MapVar.put("specific", Specific_EditText.getText().toString().trim());
					MapVar.put("star", Star_EditText.getText().toString().trim());
					MapVar.put("url", URL_EditText.getText().toString().trim());
					MapVar.put("image", picurl);
					MapVar.put("time", String.valueOf((long)(c.getTimeInMillis())));
					shop.child(MapVar.get("pid").toString()).updateChildren(MapVar);
					MapVar.clear();
				}
			}
		};
		
		_ShopPics_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				
			}
		};
		
		_ShopPics_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_ShopPics_failure_listener = new OnFailureListener() {
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
		topbar.setElevation((int)2);
		back.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF193566"),
			Color.parseColor("#FF193566")}));
		done.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF193566"),
			Color.parseColor("#FF193566")}));
		add.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF193566"),
			Color.parseColor("#FF193566")}));
		preview.setVisibility(View.INVISIBLE);
		androidx.appcompat.widget.TooltipCompat.setTooltipText(back,"Back");
		androidx.appcompat.widget.TooltipCompat.setTooltipText(add,"Add");
		androidx.appcompat.widget.TooltipCompat.setTooltipText(done,"Done");
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
				path_file = CatchedImagePath.getString("LatestImagePath", "");
				path_name = "Ka7laShop".concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(11111111111111111d), (int)(9999999999999999d)))));
				isImagePicked = true;
				preview.setVisibility(View.VISIBLE);
				preview.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(path_file, 1024, 1024));
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
			path_file = CatchedImagePath.getString("LatestImagePath", "");
			path_name = "Ka7laShop".concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(11111111111111111d), (int)(9999999999999999d)))));
			isImagePicked = true;
			preview.setVisibility(View.VISIBLE);
			preview.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(path_file, 1024, 1024));
			CatchedImagePath.edit().putString("LatestImagePath", "-").commit();
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
