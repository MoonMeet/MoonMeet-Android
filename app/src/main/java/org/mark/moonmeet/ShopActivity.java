package org.mark.moonmeet;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;
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

import org.mark.moonmeet.utils.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class ShopActivity extends AppCompatActivity {
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private FloatingActionButton _fab;
	
	private ArrayList<HashMap<String, Object>> Shop_LM = new ArrayList<>();
	
	private LinearLayout topbar;
	private LinearLayout divider_topbar;
	private LinearLayout holder;
	private ImageView back;
	private TextView shop_text;
	private LinearLayout space_top;
	private LinearLayout noshopsyet;
	private RecyclerView RecyclerView;
	private MaterialTextView noshopsyet_full_txt;
	private MaterialTextView noshopsyet_mini_txt;
	
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
	private Intent toKa7la = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.shop);
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
		_fab = (FloatingActionButton) findViewById(R.id._fab);
		
		topbar = (LinearLayout) findViewById(R.id.topbar);
		divider_topbar = (LinearLayout) findViewById(R.id.divider_topbar);
		holder = (LinearLayout) findViewById(R.id.holder);
		back = (ImageView) findViewById(R.id.back);
		shop_text = (TextView) findViewById(R.id.shop_text);
		space_top = (LinearLayout) findViewById(R.id.space_top);
		noshopsyet = (LinearLayout) findViewById(R.id.noshopsyet);
		RecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
		noshopsyet_full_txt = (MaterialTextView) findViewById(R.id.noshopsyet_full_txt);
		noshopsyet_mini_txt = (MaterialTextView) findViewById(R.id.noshopsyet_mini_txt);
		Fauth = FirebaseAuth.getInstance();
		
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				toKa7la.setClass(getApplicationContext(), AddToSellActivity.class);
				startActivity(toKa7la);
			}
		});
		
		_shop_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				Shop_LM.add(_childValue);
				if (Shop_LM.size() < 1) {
					noshopsyet.setVisibility(View.VISIBLE);
				}
				else {
					if (Shop_LM.size() > 0) {
						noshopsyet.setVisibility(View.GONE);
						RecyclerView.setAdapter(new RecyclerViewAdapter(Shop_LM));
						RecyclerView.getAdapter().notifyDataSetChanged();
						Collections.reverse(Shop_LM);
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
		back.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF193566"),
			Color.parseColor("#FF193566")}));
		topbar.setElevation((int)2);
		// Configure RecycerViewGridLayout Adapter
		RecyclerView.setAdapter(new RecyclerViewAdapter(Shop_LM));
		GridLayoutManager mGridLayoutManager = new GridLayoutManager(ShopActivity.this,2);
		
		RecyclerView.setLayoutManager(mGridLayoutManager);
		
		int spanCount = 2; // 2 columns
		int spacing = 5; // 50px
		boolean includeEdge = true;
		RecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
		if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals("d2bZ9GGc15UCMdU7CvHQ6dtwRUI3") || FirebaseAuth.getInstance().getCurrentUser().getUid().equals("VHTcld2ZmjQmSLmit9zniFTsneA2")) {
			_fab.show();
			_fab.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#FF193566")));
		}
		else {
			_fab.hide();
		}
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
	
	public void _CardStyle (final View _view, final double _shadow, final double _radius, final String _color, final boolean _touch) {
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
		gd.setColor(Color.parseColor(_color));
		gd.setCornerRadius((int)_radius);
		_view.setBackground(gd);
		
		if (Build.VERSION.SDK_INT >= 21){
			_view.setElevation((int)_shadow);}
		if (_touch) {
			_view.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()){
						case MotionEvent.ACTION_DOWN:{
							ObjectAnimator scaleX = new ObjectAnimator();
							scaleX.setTarget(_view);
							scaleX.setPropertyName("scaleX");
							scaleX.setFloatValues(0.9f);
							scaleX.setDuration(100);
							scaleX.start();
							
							ObjectAnimator scaleY = new ObjectAnimator();
							scaleY.setTarget(_view);
							scaleY.setPropertyName("scaleY");
							scaleY.setFloatValues(0.9f);
							scaleY.setDuration(100);
							scaleY.start();
							break;
						}
						case MotionEvent.ACTION_UP:{
							
							ObjectAnimator scaleX = new ObjectAnimator();
							scaleX.setTarget(_view);
							scaleX.setPropertyName("scaleX");
							scaleX.setFloatValues((float)1);
							scaleX.setDuration(100);
							scaleX.start();
							
							ObjectAnimator scaleY = new ObjectAnimator();
							scaleY.setTarget(_view);
							scaleY.setPropertyName("scaleY");
							scaleY.setFloatValues((float)1);
							scaleY.setDuration(100);
							scaleY.start();
							
							break;
						}
					}
					return false;
				}
			});
		}
	}
	
	
	public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;
		public RecyclerViewAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _inflater.inflate(R.layout.shopc, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linearholder = (LinearLayout) _view.findViewById(R.id.linearholder);
			final LinearLayout linear_cargo = (LinearLayout) _view.findViewById(R.id.linear_cargo);
			final ImageView image_cargo = (ImageView) _view.findViewById(R.id.image_cargo);
			final TextView textview_cargo = (TextView) _view.findViewById(R.id.textview_cargo);
			final LinearLayout bottom_linear = (LinearLayout) _view.findViewById(R.id.bottom_linear);
			final TextView cargo_star = (TextView) _view.findViewById(R.id.cargo_star);
			final TextView money = (TextView) _view.findViewById(R.id.money);
			
			_CardStyle(linear_cargo, 3, 7, "#FFECF0F3", false);
			if (_data.get((int)_position).containsKey("uid")) {
				if (_data.get((int)_position).containsKey("name")) {
					textview_cargo.setText(Shop_LM.get((int)_position).get("name").toString());
				}
				if (_data.get((int)_position).containsKey("price")) {
					money.setText(Shop_LM.get((int)_position).get("price").toString());
				}
				if (_data.get((int)_position).containsKey("star")) {
					if (Shop_LM.get((int)_position).get("star").toString().equals("1")) {
						cargo_star.setText("⭐");
					}
					else {
						if (Shop_LM.get((int)_position).get("star").toString().equals("2")) {
							cargo_star.setText("⭐⭐");
						}
						else {
							if (Shop_LM.get((int)_position).get("star").toString().equals("3")) {
								cargo_star.setText("⭐⭐⭐");
							}
							else {
								if (Shop_LM.get((int)_position).get("star").toString().equals("4")) {
									cargo_star.setText("⭐⭐⭐⭐");
								}
								else {
									if (Shop_LM.get((int)_position).get("star").toString().equals("5")) {
										cargo_star.setText("⭐⭐⭐⭐⭐");
									}
									else {
										cargo_star.setText("⭐⭐⭐⭐⭐");
									}
								}
							}
						}
					}
				}
				if (_data.get((int)_position).containsKey("image")) {
					Glide.with(getApplicationContext()).load(Uri.parse(Shop_LM.get((int)_position).get("image").toString())).into(image_cargo);
				}
				linear_cargo.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						toKa7la.setClass(getApplicationContext(), ViewShopActivity.class);
						toKa7la.putExtra("name", Shop_LM.get((int)_position).get("name").toString());
						toKa7la.putExtra("price", Shop_LM.get((int)_position).get("price").toString());
						toKa7la.putExtra("star", Shop_LM.get((int)_position).get("star").toString());
						toKa7la.putExtra("image", Shop_LM.get((int)_position).get("image").toString());
						toKa7la.putExtra("specific", Shop_LM.get((int)_position).get("specific").toString());
						toKa7la.putExtra("url", Shop_LM.get((int)_position).get("url").toString());
						startActivity(toKa7la);
					}
				});
			}
			else {
				linearholder.setVisibility(View.GONE);
			}
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
			}
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
