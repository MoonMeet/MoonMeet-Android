package org.mark.moonmeet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import org.mark.axemojiview.view.AXEmojiTextView;
import org.mark.moonmeet.ui.BaseFragment;
import org.mark.moonmeet.utils.AndroidUtilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class StoryActivity extends BaseFragment {

	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String uid = "";
	private double currentPosition = 0;
	private double Time = 0;
	private double difference = 0;
	private HashMap<String, Object> StoryView = new HashMap<>();
	private String current = "";
	private String Firstname = "";
	private String Lastname = "";
	private String avatar = "";
	private String username = "";
	private HashMap<String, Object> Report_Map = new HashMap<>();
	private double x1 = 0;
	private double x2 = 0;
	private double y1 = 0;
	private double y2 = 0;
	
	private ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();
	private ArrayList<String> viewLS = new ArrayList<>();
	
	private LinearLayout linear1;
	private ProgressBar progressbar1;
	private LinearLayout linear14;
	private LinearLayout midlin;
	private LinearLayout bottomlin;
	private ImageView imageview8;
	private LinearLayout linear16;
	private LinearLayout linear20;
	private LinearLayout linear19;
	private ImageView imageview11;
	private LinearLayout propics_lin;
	private LinearLayout linear15;
	private CircleImageView circleimageview1;
	private TextView textview8;
	private TextView textview9;
	private TextView textview13;
	private ImageView imageview9;
	private ImageView imageview10;
	private LinearLayout linear18;
	private AXEmojiTextView textview12;
	private ImageView imageview5;
	private LinearLayout linear13;
	private ImageView imageview6;
	private TextView textview10;
	
	private DatabaseReference stories = _firebase.getReference("stories");
	private ChildEventListener _stories_child_listener;
	private DatabaseReference users = _firebase.getReference("users");
	private ChildEventListener _users_child_listener;
	private Calendar calendar = Calendar.getInstance();
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
	private DatabaseReference StoryViews = _firebase.getReference("StoryViews");
	private ChildEventListener _StoryViews_child_listener;
	private SharedPreferences sp_seen;
	private Intent toViewers = new Intent();
	private SharedPreferences EnableViewers;
	private TimerTask Timer;
	private DatabaseReference reports = _firebase.getReference("reports");
	private ChildEventListener _reports_child_listener;
	private SharedPreferences MyStoryData;

	public StoryActivity(String storyuid) {
		this.uid = storyuid;
	}

	@Override
	public View createView(Context context) {
		fragmentView = new FrameLayout(context);
		actionBar.setAddToContainer(false);
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.story, ((ViewGroup) fragmentView), false);
		((ViewGroup) fragmentView).addView(view);
		initialize(context);
		com.google.firebase.FirebaseApp.initializeApp(getParentActivity());
		initializeLogic();
		return fragmentView;
	}
	
	private void initialize(Context context) {
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		progressbar1 = (ProgressBar) findViewById(R.id.progressbar1);
		linear14 = (LinearLayout) findViewById(R.id.linear14);
		midlin = (LinearLayout) findViewById(R.id.midlin);
		bottomlin = (LinearLayout) findViewById(R.id.bottomlin);
		imageview8 = (ImageView) findViewById(R.id.imageview8);
		linear16 = (LinearLayout) findViewById(R.id.linear16);
		linear20 = (LinearLayout) findViewById(R.id.linear20);
		linear19 = (LinearLayout) findViewById(R.id.linear19);
		imageview11 = (ImageView) findViewById(R.id.imageview11);
		propics_lin = (LinearLayout) findViewById(R.id.propics_lin);
		linear15 = (LinearLayout) findViewById(R.id.linear15);
		circleimageview1 = (CircleImageView) findViewById(R.id.circleimageview1);
		textview8 = (TextView) findViewById(R.id.textview8);
		textview9 = (TextView) findViewById(R.id.textview9);
		textview13 = (TextView) findViewById(R.id.textview13);
		imageview9 = (ImageView) findViewById(R.id.imageview9);
		imageview10 = (ImageView) findViewById(R.id.imageview10);
		linear18 = (LinearLayout) findViewById(R.id.linear18);
		textview12 = (AXEmojiTextView) findViewById(R.id.textview12);
		imageview5 = (ImageView) findViewById(R.id.imageview5);
		linear13 = (LinearLayout) findViewById(R.id.linear13);
		imageview6 = (ImageView) findViewById(R.id.imageview6);
		textview10 = (TextView) findViewById(R.id.textview10);
		Fauth = FirebaseAuth.getInstance();
		sp_seen = context.getSharedPreferences("sp_seen", Activity.MODE_PRIVATE);
		EnableViewers = context.getSharedPreferences("sp_ev", Activity.MODE_PRIVATE);
		MyStoryData = context.getSharedPreferences("MyStoryData", Activity.MODE_PRIVATE);
		
		imageview8.setOnClickListener(_view -> finishFragment());
		
		linear19.setOnClickListener(_view -> {
			Bundle bundle = new Bundle();
			bundle.putString("sid", current);
			presentFragment(new StoryViewActivity(bundle));
		});
		
		imageview11.setOnClickListener(view -> _BottomSheet());
		
		imageview5.setOnClickListener(view -> {
			if (currentPosition == 0) {

			}
			else {
				_getdata(currentPosition - 1);
			}
		});
		
		imageview6.setOnClickListener(_view -> {
			if (listmap.size() > (currentPosition + 1)) {
				_getdata(currentPosition + 1);
			}
		});
		
		_stories_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childValue.containsKey("uid") && (_childValue.containsKey("sid") && (_childValue.containsKey("time") && (_childValue.containsKey("text") || _childValue.containsKey("image"))))) {
					calendar = Calendar.getInstance();
					if ((calendar.getTimeInMillis() - Double.parseDouble(_childValue.get("time").toString())) > 86400000) {
						stories.child(_childValue.get("sid").toString()).removeValue();
						StoryViews.child(_childValue.get("sid").toString()).removeValue();
					}
					else {
						if (_childValue.get("uid").toString().equals(uid)) {
							listmap.add(_childValue);
							if (listmap.size() > 0) {
								_getdata(0);
							}
							else {
								finishFragment();
							}
							progressbar1.setVisibility(View.GONE);
						}
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
				listmap.clear();
				stories.addChildEventListener(_stories_child_listener);
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		stories.addChildEventListener(_stories_child_listener);
		
		_users_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(uid)) {
					if (_childValue.containsKey("firstname") && (_childValue.containsKey("lastname") && (_childValue.containsKey("avatar") && _childValue.containsKey("username")))) {
						textview8.setText(_childValue.get("firstname").toString().concat(" ".concat(_childValue.get("lastname").toString())));
						com.bumptech.glide.Glide.with(getApplicationContext())
						.load(_childValue.get("avatar").toString())
						.override(1024, 1024)
						.into(circleimageview1);
					}
				}
				if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					if (_childValue.containsKey("firstname") && (_childValue.containsKey("lastname") && (_childValue.containsKey("avatar") && _childValue.containsKey("username")))) {
						Firstname = _childValue.get("firstname").toString();
						Lastname = _childValue.get("lastname").toString();
						avatar = _childValue.get("avatar").toString();
						username = _childValue.get("username").toString();
						Timer = new TimerTask() {
							@Override
							public void run() {
								AndroidUtilities.runOnUIThread(() -> {
									linear19.setAlpha((float)(1));
									linear19.setEnabled(true);
								});
							}
						};
						_timer.schedule(Timer, (int)(600));
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
		
		_StoryViews_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (listmap.size() < 1) {
					finishFragment();
				}
				else {
					if (listmap.get((int)currentPosition).containsKey("sid")) {
						if (_childKey.equals(listmap.get((int)currentPosition).get("sid").toString())) {
							SketchwareUtil.getAllKeysFromMap(_childValue, viewLS);
							textview13.setText(String.valueOf((long)(viewLS.size())));
						}
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
				viewLS.clear();
				StoryViews.addChildEventListener(_StoryViews_child_listener);
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		StoryViews.addChildEventListener(_StoryViews_child_listener);
		
		_reports_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
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
		reports.addChildEventListener(_reports_child_listener);
		
		Fauth_updateEmailListener = _param1 -> {
			final boolean _success = _param1.isSuccessful();
			final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

		};
		
		Fauth_updatePasswordListener = _param1 -> {
			final boolean _success = _param1.isSuccessful();
			final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

		};
		
		Fauth_emailVerificationSentListener = _param1 -> {
			final boolean _success = _param1.isSuccessful();
			final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

		};
		
		Fauth_deleteUserListener = _param1 -> {
			final boolean _success = _param1.isSuccessful();
			final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

		};
		
		Fauth_phoneAuthListener = task -> {
			final boolean _success = task.isSuccessful();
			final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";

		};
		
		Fauth_updateProfileListener = _param1 -> {
			final boolean _success = _param1.isSuccessful();
			final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

		};
		
		Fauth_googleSignInListener = task -> {
			final boolean _success = task.isSuccessful();
			final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";

		};
		
		_Fauth_create_user_listener = _param1 -> {
			final boolean _success = _param1.isSuccessful();
			final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

		};
		
		_Fauth_sign_in_listener = _param1 -> {
			final boolean _success = _param1.isSuccessful();
			final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

		};
		
		_Fauth_reset_password_listener = _param1 -> {
			final boolean _success = _param1.isSuccessful();

		};
	}
	
	private void initializeLogic() {
		StoryViews.addChildEventListener(_StoryViews_child_listener);
		textview8.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(),"fonts/rmedium.ttf"), 0);
		textview9.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(),"fonts/rmedium.ttf"), 0);
		textview10.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(),"fonts/rmedium.ttf"), 0);
		textview12.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(),"fonts/rmedium.ttf"), 0);
		linear14.setElevation((int)2);
		bottomlin.setElevation((int)2);
		linear19.setAlpha((float)(0.3d));
		linear19.setEnabled(false);
		_Gestures();
		androidx.appcompat.widget.TooltipCompat.setTooltipText(imageview8,"Back");
		androidx.appcompat.widget.TooltipCompat.setTooltipText(imageview11,"Options");
		androidx.appcompat.widget.TooltipCompat.setTooltipText(imageview6,"Next");
		androidx.appcompat.widget.TooltipCompat.setTooltipText(imageview5,"Previous");
		imageview11.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF193566"),
			Color.parseColor("#FF193566")}));
		imageview9.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF193566"),
			Color.parseColor("#FF193566")}));
		imageview5.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF193566"),
			Color.parseColor("#FF193566")}));
		imageview6.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF193566"),
			Color.parseColor("#FF193566")}));
		imageview8.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF193566"),
			Color.parseColor("#FF193566")}));
	}
	
	public void _getdata (final double _position) {
		linear19.setVisibility(View.INVISIBLE);
		Timer = new TimerTask() {
			@Override
			public void run() {
				AndroidUtilities.runOnUIThread(() -> {
					if (uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
						linear19.setVisibility(View.VISIBLE);
					}
					else {
						linear19.setVisibility(View.GONE);
					}
				});
			}
		};
		_timer.schedule(Timer, (int)(500));
		sp_seen.edit().putString(listmap.get((int)_position).get("sid").toString(), "true").apply();
		if (!FirebaseAuth.getInstance().getCurrentUser().getUid().equals(listmap.get((int)_position).get("uid").toString())) {
			StoryView = new HashMap<>();
			StoryView.put("uid", MyStoryData.getString("uid", ""));
			StoryView.put("firstname", MyStoryData.getString("firstname", ""));
			StoryView.put("lastname", MyStoryData.getString("lastname", ""));
			StoryView.put("avatar", MyStoryData.getString("avatar", ""));
			StoryView.put("username", MyStoryData.getString("username", ""));
			StoryView.put("sid", listmap.get((int)_position).get("sid").toString());
			StoryViews.child(listmap.get((int)_position).get("sid").toString().concat("/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid()))).updateChildren(StoryView);
			StoryView.clear();
		}
		if (listmap.get((int)_position).containsKey("image")) {
			imageview10.setVisibility(View.VISIBLE);
			com.bumptech.glide.Glide.with(getApplicationContext())
			.load(listmap.get((int)_position).get("image").toString())
			.override(1024, 1024)
			.into(imageview10);
		}
		else {
			imageview10.setVisibility(View.INVISIBLE);
		}
		if (listmap.get((int)_position).containsKey("text")) {
			linear18.setVisibility(View.VISIBLE);
			textview12.setText(listmap.get((int)_position).get("text").toString());
		}
		else {
			linear18.setVisibility(View.GONE);
		}
		if (listmap.get((int)_position).containsKey("time")) {
			_Time(Double.parseDouble(listmap.get((int)_position).get("time").toString()), textview9);
		}
		textview10.setText(String.valueOf((long)(_position + 1)).concat("/".concat(String.valueOf((long)(listmap.size())))));
		if (_position == 0) {
			imageview5.setEnabled(false);
			imageview5.setAlpha((float)(0.3d));
		}
		else {
			imageview5.setEnabled(true);
			imageview5.setAlpha((float)(1));
		}
		if (_position == (listmap.size() - 1)) {
			imageview6.setEnabled(false);
			imageview6.setAlpha((float)(0.3d));
		}
		else {
			imageview6.setEnabled(true);
			imageview6.setAlpha((float)(1));
		}
		currentPosition = _position;
		current = listmap.get((int)_position).get("sid").toString();
		viewLS.clear();
		StoryViews.addChildEventListener(_StoryViews_child_listener);
	}
	
	
	public void _Time (final double _Position, final TextView _Textview) {
		Time = _Position;
		calendar = Calendar.getInstance();
		difference = calendar.getTimeInMillis() - Time;
		if (difference < 60000) {
			_Textview.setText("Just Now");
		}
		else {
			if (difference < (60 * 60000)) {
				_Textview.setText(String.valueOf((long)(difference / 60000)).concat(" Minutes Ago"));
			}
			else {
				if (difference < (24 * (60 * 60000))) {
					_Textview.setText(String.valueOf((long)(difference / (60 * 60000))).concat(" Hours Ago"));
				}
				else {
					
				}
			}
		}
	}
	
	
	public void _BottomSheet () {
		final
		com.google.android.material.bottomsheet.BottomSheetDialog bsh = new com.google.android.material.bottomsheet.BottomSheetDialog(getParentActivity());
		
		View bshlay = getParentActivity().getLayoutInflater().inflate(R.layout.bsc, null);
		
		bsh.setContentView(bshlay);
		
		bsh.show();
		// Define LinearLayouts
		LinearLayout alllin = (LinearLayout)bshlay.findViewById(R.id.linear1);
		
		bsh.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
		
		gd.setColor(Color.parseColor("#FFFFFF"));
		
		gd.setStroke((int)0, Color.parseColor("#FF193566"));
		
		gd.setCornerRadii(new float[]{(int)20,(int)20,(int)20,(int)20,(int)0,(int)0,(int)0,(int)0});
		
		alllin.setBackground(gd);
		LinearLayout item1lin = (LinearLayout)bshlay.findViewById(R.id.item1);
		
		_RippleEffects("#DADADA", item1lin);
		LinearLayout item2lin = (LinearLayout)bshlay.findViewById(R.id.item2);
		
		_RippleEffects("#DADADA", item2lin);
		LinearLayout item3lin = (LinearLayout)bshlay.findViewById(R.id.item3);
		
		_RippleEffects("#DADADA", item3lin);
		LinearLayout item1bg = (LinearLayout)bshlay.findViewById(R.id.item1_lin);
		LinearLayout item2bg = (LinearLayout)bshlay.findViewById(R.id.item2_lin);
		LinearLayout item3bg = (LinearLayout)bshlay.findViewById(R.id.item3_lin);
		LinearLayout item4bg = (LinearLayout)bshlay.findViewById(R.id.item4_lin);
		item4bg.setVisibility(View.GONE);
		// Define ImageViews
		ImageView item1img = (ImageView)bshlay.findViewById(R.id.item1_img);
		item1img.setImageResource(R.drawable.msg_copy);
		item1img.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF193566"),
			Color.parseColor("#FF193566")}));
		ImageView item2img = (ImageView)bshlay.findViewById(R.id.item2_img);
		item2img.setImageResource(R.drawable.chats_delete);
		item2img.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF193566"),
			Color.parseColor("#FF193566")}));
		ImageView item3img = (ImageView)bshlay.findViewById(R.id.item3_img);
		item3img.setImageResource(R.drawable.profile_info);
		item3img.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF193566"),
			Color.parseColor("#FF193566")}));
		// Define TextViews
		TextView ttl1txt = (TextView)bshlay.findViewById(R.id.ttl1_txt);
		ttl1txt.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(),"fonts/rmedium.ttf"), 0);
		ttl1txt.setText("Choose Option");
		TextView ttl2txt = (TextView)bshlay.findViewById(R.id.ttl2_txt);
		ttl2txt.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(),"fonts/rmedium.ttf"), 0);
		ttl2txt.setTextColor(0xFF193566);
		ttl2txt.setText(listmap.get((int)currentPosition).get("firstname").toString().concat(" ".concat(listmap.get((int)currentPosition).get("lastname").toString())));
		ttl2txt.setVisibility(View.GONE);
		TextView item1txt = (TextView)bshlay.findViewById(R.id.item1_txt);
		item1txt.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(),"fonts/rmedium.ttf"), 0);
		item1txt.setText("Copy Text");
		TextView item2txt = (TextView)bshlay.findViewById(R.id.item2_txt);
		item2txt.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(),"fonts/rmedium.ttf"), 0);
		item2txt.setText("Delete Story");
		TextView item3txt = (TextView)bshlay.findViewById(R.id.item3_txt);
		item3txt.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(),"fonts/rmedium.ttf"), 0);
		item3txt.setText("Report Story");
		// SetData
		if (uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
			item3bg.setVisibility(View.GONE);
		}
		else {
			item2bg.setVisibility(View.GONE);
		}
		if (listmap.get((int)currentPosition).containsKey("text")) {
			
		}
		else {
			item1bg.setVisibility(View.GONE);
		}
		// OnClick
		item1lin.setOnClickListener(_view -> {
				((ClipboardManager) getParentActivity().getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", listmap.get((int)currentPosition).get("text").toString()));
			AndroidUtilities.showToast("Copied to Clipboard.");
bsh.dismiss();
		});
		item2lin.setOnClickListener(_view -> {
				_DeleteDialog();
bsh.dismiss();
		});
		item3lin.setOnClickListener(_view -> {
				_ReportDialog();
bsh.dismiss();
		});
		bsh.show();
	}
	
	
	public void _DeleteDialog () {
		final AlertDialog DeleteRequest = new AlertDialog.Builder(getParentActivity()).create();
		LayoutInflater Del = getParentActivity().getLayoutInflater();
		View convertView = (View) Del.inflate(R.layout.delete, null);
		DeleteRequest.setView(convertView);
		DeleteRequest.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		TextView foreveryone = (TextView)
		convertView.findViewById(R.id.foreveryone);
		TextView cancel = (TextView)
		convertView.findViewById(R.id.cancel);
		TextView for_me = (TextView)
		convertView.findViewById(R.id.for_me);
		TextView delete_text = (TextView)
		convertView.findViewById(R.id.delete_text);
		TextView delete_description = (TextView)
		convertView.findViewById(R.id.delete_description);
		foreveryone.setVisibility(View.GONE);
		for_me.setText("Delete");
		delete_text.setText("Delete Story");
		delete_description.setText("Are you sure you want to delete your story ? This can be undone.");
		_RippleEffects("#DADADA", cancel);
		_RippleEffects("#DADADA", for_me);
		cancel.setOnClickListener(_view -> DeleteRequest.dismiss());
		for_me.setOnClickListener(_view -> {
				if (uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
	stories.child(listmap.get((int)currentPosition).get("sid").toString()).removeValue();
	StoryViews.child(listmap.get((int)currentPosition).get("sid").toString()).removeValue();
	DeleteRequest.dismiss();
}
		});
		DeleteRequest.setCancelable(true);
		DeleteRequest.show();
	}
	
	
	public void _ReportDialog () {
		final AlertDialog ReportRequest = new AlertDialog.Builder(getParentActivity()).create();
		LayoutInflater rep = getParentActivity().getLayoutInflater();
		
		View convertView = (View) rep.inflate(R.layout.delete, null);
		ReportRequest.setView(convertView);
		ReportRequest.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		TextView foreveryone = (TextView)
		convertView.findViewById(R.id.foreveryone);
		TextView cancel = (TextView)
		convertView.findViewById(R.id.cancel);
		TextView for_me = (TextView)
		convertView.findViewById(R.id.for_me);
		TextView delete_text = (TextView)
		convertView.findViewById(R.id.delete_text);
		TextView delete_description = (TextView)
		convertView.findViewById(R.id.delete_description);
		foreveryone.setVisibility(View.GONE);
		for_me.setText("Report");
		delete_text.setText("Report Story");
		delete_description.setText("Before reporting, Please make sure this story contains at least one of these below:\n\n+ Sexual Content\n+ Rude Behavior / Harassment\n+ Spam\n+ Or any other content that you think shouldn't be here.\n\nPlease note that there's only one admin at the moment that can review reports, so reviewing may take some time.");
		_RippleEffects("#DADADA", cancel);
		_RippleEffects("#DADADA", for_me);
		cancel.setOnClickListener(_view -> ReportRequest.dismiss());
		for_me.setOnClickListener(_view -> {
				Report_Map = new HashMap<>();
Report_Map.put("id", listmap.get((int)currentPosition).get("sid").toString());
Report_Map.put("reported_by", FirebaseAuth.getInstance().getCurrentUser().getUid());
Report_Map.put("uid", uid);
Report_Map.put("rid", reports.push().getKey());
if (listmap.get((int)currentPosition).containsKey("text")) {
	Report_Map.put("text", listmap.get((int)currentPosition).get("text").toString());
}
if (listmap.get((int)currentPosition).containsKey("image")) {
	Report_Map.put("image", listmap.get((int)currentPosition).get("image").toString());
}
reports.child("Stories/".concat(Report_Map.get("rid").toString())).updateChildren(Report_Map);
Report_Map.clear();
SketchwareUtil.showMessage(getApplicationContext(), "Reported.");
ReportRequest.dismiss();
		});
		ReportRequest.setCancelable(true);
		ReportRequest.show();
	}
	
	
	public void _RippleEffects (final String _color, final View _view) {
		android.content.res.ColorStateList clr = new android.content.res.ColorStateList(new int[][]{new int[]{}},new int[]{Color.parseColor(_color)});
		android.graphics.drawable.RippleDrawable ripdr = new android.graphics.drawable.RippleDrawable(clr, null, null);
		_view.setBackground(ripdr);
	}
	
	
	public void _Gestures () {
		midlin.setOnTouchListener((v, event) -> {switch (event.getAction()) {
											case MotionEvent.ACTION_DOWN:
											x1 = event.getX();
											y1 = event.getY();
											return true;
											case MotionEvent.ACTION_UP:
											x2 = event.getX();
											y2 = event.getY();
											if ((x1 - x2) < -250) {
														// Right

											}
											else {
														if ((x2 - x1) < -250) {
																	// Left
														}
														else {
																	if ((y1 - y2) < -250) {
																				// Down
																				if (currentPosition == 0) {

																				}
																				else {
																							_getdata(currentPosition - 1);
																				}
																	}
																	else {
																				if ((y2 - y1) < -250) {
																							// Up
																							if (listmap.size() > (currentPosition + 1)) {
																										_getdata(currentPosition + 1);
																							}
																				}
																				else {
																							// Touch
																				}
																	}
														}
											}
											return true;
								}
								return false;
					});
		
	}
}
