package org.mark.moonmeet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.mark.axemojiview.view.AXEmojiTextView;
import org.mark.moonmeet.utils.GridSpacingItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity {
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private double width = 0;
	private double time = 0;
	private String UserChats_str = "";
	private HashMap<String, Object> storiesMap = new HashMap<>();
	private double limit = 0;
	private String Chatroom = "";
	private String Chatcopy = "";
	private String RandomChoiceID = "";
	private double RandomUserChoice = 0;
	private boolean isActivityFirstRun = false;
	private String RecentMedia_str = "";
	
	private ArrayList<HashMap<String, Object>> all_users = new ArrayList<>();
	private ArrayList<String> UserChats_LS = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> UserChats_LM = new ArrayList<>();
	private ArrayList<String> UserMedia_LS = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> UserMedia_LM = new ArrayList<>();
	private ArrayList<String> UserVoice_LS = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> UserVoice_LM = new ArrayList<>();
	
	private LinearLayout all_tabs_holder;
	private NestedScrollView scroll;
	private LinearLayout topbar;
	private LinearLayout tbl_alltabs;
	private LinearLayout divider;
	private ImageView imageview1;
	private TextView textview1;
	private LinearLayout tbl_alltabs2;
	private LinearLayout tabsholder;
	private LinearLayout tab1_btn;
	private LinearLayout tab2_btn;
	private LinearLayout tab3_btn;
	private TextView tab1_btn_txt;
	private TextView tab2_btn_txt;
	private TextView tab3_btn_txt;
	private LinearLayout tab1;
	private LinearLayout tab2;
	private LinearLayout tab3;
	private LinearLayout line;
	private LinearLayout main_linear;
	private LinearLayout chats_no;
	private RecyclerView chats_rv;
	private LinearLayout media_no;
	private RecyclerView media_rv;
	private LinearLayout voice_no;
	private RecyclerView voice_rv;
	private TextView chats_no1;
	private TextView chats_no2;
	private TextView media_no1;
	private TextView media_no2;
	private TextView voice_no1;
	private TextView voice_no2;
	
	private TimerTask pclick;
	private DatabaseReference users = _firebase.getReference("users");
	private ChildEventListener _users_child_listener;
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
	private DatabaseReference Chat1 = _firebase.getReference("Chatroom");
	private ChildEventListener _Chat1_child_listener;
	private DatabaseReference Chat2 = _firebase.getReference("Chatcopy");
	private ChildEventListener _Chat2_child_listener;
	private DatabaseReference UserChats = _firebase.getReference("UserChats");
	private ChildEventListener _UserChats_child_listener;
	private Calendar c = Calendar.getInstance();
	private Calendar Cal = Calendar.getInstance();
	private Intent toGoChat = new Intent();
	private Intent toViewStory = new Intent();
	private SharedPreferences sp_seen;
	private DatabaseReference stories = _firebase.getReference("stories");
	private ChildEventListener _stories_child_listener;
	private DatabaseReference StoryViews = _firebase.getReference("StoryViews");
	private ChildEventListener _StoryViews_child_listener;
	private Calendar calendar = Calendar.getInstance();
	private Intent toContinue = new Intent();
	private DatabaseReference RecentMedia = _firebase.getReference("RecentMedia");
	private ChildEventListener _RecentMedia_child_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.search);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		all_tabs_holder = (LinearLayout) findViewById(R.id.all_tabs_holder);
		scroll = (NestedScrollView) findViewById(R.id.scroll);
		topbar = (LinearLayout) findViewById(R.id.topbar);
		tbl_alltabs = (LinearLayout) findViewById(R.id.tbl_alltabs);
		divider = (LinearLayout) findViewById(R.id.divider);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		textview1 = (TextView) findViewById(R.id.textview1);
		tbl_alltabs2 = (LinearLayout) findViewById(R.id.tbl_alltabs2);
		tabsholder = (LinearLayout) findViewById(R.id.tabsholder);
		tab1_btn = (LinearLayout) findViewById(R.id.tab1_btn);
		tab2_btn = (LinearLayout) findViewById(R.id.tab2_btn);
		tab3_btn = (LinearLayout) findViewById(R.id.tab3_btn);
		tab1_btn_txt = (TextView) findViewById(R.id.tab1_btn_txt);
		tab2_btn_txt = (TextView) findViewById(R.id.tab2_btn_txt);
		tab3_btn_txt = (TextView) findViewById(R.id.tab3_btn_txt);
		tab1 = (LinearLayout) findViewById(R.id.tab1);
		tab2 = (LinearLayout) findViewById(R.id.tab2);
		tab3 = (LinearLayout) findViewById(R.id.tab3);
		line = (LinearLayout) findViewById(R.id.line);
		main_linear = (LinearLayout) findViewById(R.id.main_linear);
		chats_no = (LinearLayout) findViewById(R.id.chats_no);
		chats_rv = (RecyclerView) findViewById(R.id.chats_rv);
		media_no = (LinearLayout) findViewById(R.id.media_no);
		media_rv = (RecyclerView) findViewById(R.id.media_rv);
		voice_no = (LinearLayout) findViewById(R.id.voice_no);
		voice_rv = (RecyclerView) findViewById(R.id.voice_rv);
		chats_no1 = (TextView) findViewById(R.id.chats_no1);
		chats_no2 = (TextView) findViewById(R.id.chats_no2);
		media_no1 = (TextView) findViewById(R.id.media_no1);
		media_no2 = (TextView) findViewById(R.id.media_no2);
		voice_no1 = (TextView) findViewById(R.id.voice_no1);
		voice_no2 = (TextView) findViewById(R.id.voice_no2);
		Fauth = FirebaseAuth.getInstance();
		sp_seen = getSharedPreferences("sp_seen", Activity.MODE_PRIVATE);
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		tab1_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				scroll.post(new Runnable() { 
					        public void run() { 
						             scroll.fullScroll(scroll.FOCUS_UP);
						        } 
				});
				_autoScroll(150, tabsholder);
				tab1.setVisibility(View.GONE);
				tab2.setVisibility(View.GONE);
				int width = tab1_btn_txt.getMeasuredWidth();
				_setViewSize(line, width, 7);
				tab1_btn_txt.setTextColor(0xFF193566);
				tab2_btn_txt.setTextColor(0xFF455A64);
				tab3_btn_txt.setTextColor(0xFF455A64);
				media_rv.setVisibility(View.GONE);
				voice_rv.setVisibility(View.GONE);
				media_no.setVisibility(View.GONE);
				voice_no.setVisibility(View.GONE);
				if ((UserChats_LS.size() < 1) && (UserChats_LM.size() < 1)) {
					chats_rv.setVisibility(View.GONE);
					chats_no.setVisibility(View.VISIBLE);
				}
				else {
					chats_no.setVisibility(View.GONE);
					chats_rv.setVisibility(View.VISIBLE);
				}
			}
		});
		
		tab2_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				scroll.post(new Runnable() { 
					        public void run() { 
						             scroll.fullScroll(scroll.FOCUS_UP);
						        } 
				});
				_autoScroll(150, tabsholder);
				tab1.setVisibility(View.VISIBLE);
				tab2.setVisibility(View.GONE);
				int width = tab2_btn_txt.getMeasuredWidth();
				_setViewSize(line, width, 7);
				tab1_btn_txt.setTextColor(0xFF455A64);
				tab2_btn_txt.setTextColor(0xFF193566);
				tab3_btn_txt.setTextColor(0xFF455A64);
				chats_rv.setVisibility(View.GONE);
				voice_rv.setVisibility(View.GONE);
				chats_no.setVisibility(View.GONE);
				voice_no.setVisibility(View.GONE);
				if ((UserMedia_LM.size() < 1) && (UserMedia_LS.size() < 1)) {
					media_no.setVisibility(View.VISIBLE);
					media_rv.setVisibility(View.GONE);
				}
				else {
					media_no.setVisibility(View.GONE);
					media_rv.setVisibility(View.VISIBLE);
				}
			}
		});
		
		tab3_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				scroll.post(new Runnable() { 
					        public void run() { 
						             scroll.fullScroll(scroll.FOCUS_UP);
						        } 
				});
				_autoScroll(150, tabsholder);
				tab1.setVisibility(View.VISIBLE);
				tab2.setVisibility(View.VISIBLE);
				int width = tab3_btn_txt.getMeasuredWidth();
				_setViewSize(line, width, 7);
				tab1_btn_txt.setTextColor(0xFF455A64);
				tab2_btn_txt.setTextColor(0xFF455A64);
				tab3_btn_txt.setTextColor(0xFF193566);
				chats_rv.setVisibility(View.GONE);
				media_rv.setVisibility(View.GONE);
				chats_no.setVisibility(View.GONE);
				media_no.setVisibility(View.GONE);
				if ((UserVoice_LS.size() < 1) && (UserVoice_LM.size() < 1)) {
					voice_no.setVisibility(View.VISIBLE);
					voice_rv.setVisibility(View.GONE);
				}
				else {
					voice_rv.setVisibility(View.VISIBLE);
					voice_no.setVisibility(View.GONE);
				}
			}
		});
		
		_users_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childValue.containsKey("firstname") && (_childValue.containsKey("lastname") && _childValue.containsKey("avatar"))) {
					all_users.add(_childValue);
					if (isActivityFirstRun) {
						RandomUserChoice = SketchwareUtil.getRandom((int)(1), (int)(all_users.size()));
						RandomChoiceID = all_users.get((int)RandomUserChoice - 1).get("uid").toString();
						isActivityFirstRun = false;
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
		
		_Chat1_child_listener = new ChildEventListener() {
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
		Chat1.addChildEventListener(_Chat1_child_listener);
		
		_Chat2_child_listener = new ChildEventListener() {
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
		Chat2.addChildEventListener(_Chat2_child_listener);
		
		_UserChats_child_listener = new ChildEventListener() {
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
		UserChats.addChildEventListener(_UserChats_child_listener);
		
		_stories_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childValue.containsKey("uid") && (_childValue.containsKey("sid") && (_childValue.containsKey("time") && (_childValue.containsKey("avatar") && (_childValue.containsKey("text") || _childValue.containsKey("image")))))) {
					calendar = Calendar.getInstance();
					if ((calendar.getTimeInMillis() - Double.parseDouble(_childValue.get("time").toString())) > 86400000) {
						stories.child(_childValue.get("sid").toString()).removeValue();
						StoryViews.child(_childValue.get("sid").toString()).removeValue();
					}
					else {
						storiesMap.put(_childValue.get("uid").toString(), _childValue.get("sid").toString());
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
		stories.addChildEventListener(_stories_child_listener);
		
		_StoryViews_child_listener = new ChildEventListener() {
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
		StoryViews.addChildEventListener(_StoryViews_child_listener);
		
		_RecentMedia_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childValue.containsKey("image")) {
					UserMedia_LM.add(_childValue);
					if (UserMedia_LM.size() > 0) {
						media_rv.setAdapter(new Media_rvAdapter(UserMedia_LM));
					}
					if (isActivityFirstRun) {
						SketchwareUtil.showMessage(getApplicationContext(), "ok");
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
		RecentMedia.addChildEventListener(_RecentMedia_child_listener);
		
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
		isActivityFirstRun = true;
		limit = 10;
		UserChats.removeEventListener(_UserChats_child_listener);
		UserChats_str = "UserChats/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid());
		UserChats = _firebase.getReference(UserChats_str);
		UserChats.addChildEventListener(_UserChats_child_listener);
		Chat1.removeEventListener(_Chat1_child_listener);
		Chat2.removeEventListener(_Chat2_child_listener);
		Chatroom = "chat/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("/".concat(RandomChoiceID)));
		Chatcopy = "chat/".concat(RandomChoiceID.concat("/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid())));
		Chat1 =
		_firebase.getReference(Chatroom);
		Chat2 =
		_firebase.getReference(Chatcopy);
		Chat1.addChildEventListener(_Chat1_child_listener);
		Chat2.addChildEventListener(_Chat2_child_listener);
		// Configure RecycerViewGridLayout Adapter
		GridLayoutManager mGridLayoutManager = new GridLayoutManager(SearchActivity.this,3);
		
		media_rv.setLayoutManager(mGridLayoutManager);
		
		int spanCount = 3; // 3 columns
		int spacing = 5; // 25px
		boolean includeEdge = true;
		media_rv.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
		RecentMedia.removeEventListener(_RecentMedia_child_listener);
		RecentMedia_str = "RecentMedia/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid());
		RecentMedia =
		_firebase.getReference(RecentMedia_str);
		RecentMedia.addChildEventListener(_RecentMedia_child_listener);
		query = UserChats.limitToLast((int)limit);
		query.addValueEventListener(valueEventListener1);
		_Tabs();
		pclick = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						tab1_btn.performClick();
					}
				});
			}
		};
		_timer.schedule(pclick, (int)(100));
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		updateStatusBar();
		LinearLayoutManager cLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
		chats_rv.setLayoutManager(cLinearLayoutManager);
		chats_rv.setItemViewCacheSize(60);
		chats_rv.setDrawingCacheEnabled(true);
		chats_rv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
		chats_rv.setItemAnimator(new DefaultItemAnimator());
		imageview1.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF193566"),
			Color.parseColor("#FF193566")}));
		all_tabs_holder.setElevation((int)2);
		androidx.appcompat.widget.TooltipCompat.setTooltipText(imageview1,"Back");
		
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _setViewSize (final View _view1, final double _width, final double _height) {
		_view1.setLayoutParams(new LinearLayout.LayoutParams((int)_width, (int)_height));
	}
	
	
	public void _radius_4 (final String _color1, final String _color2, final double _str, final double _n1, final double _n2, final double _n3, final double _n4, final View _view) {
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
		
		gd.setColor(Color.parseColor(_color1));
		
		gd.setStroke((int)_str, Color.parseColor(_color2));
		
		gd.setCornerRadii(new float[]{(int)_n1,(int)_n1,(int)_n2,(int)_n2,(int)_n3,(int)_n3,(int)_n4,(int)_n4});
		
		_view.setBackground(gd);
	}
	
	
	public void _autoScroll (final double _duration, final View _view) {
		LinearLayout viewgroup =(LinearLayout) _view;
		
		android.transition.AutoTransition autoTransition = new android.transition.AutoTransition(); autoTransition.setDuration((long)_duration); android.transition.TransitionManager.beginDelayedTransition(viewgroup, autoTransition);
	}
	
	
	public void _SetTextSize (final TextView _TextIDE, final double _SizeNUM) {
		_TextIDE.setTextSize((float)_SizeNUM);
	}
	
	
	public void _Tabs () {
		_radius_4("#FF193566", "#00000000", 0, 50, 50, 0, 0, line);
		_setViewSize(tab3, SketchwareUtil.getDisplayWidthPixels(getApplicationContext()) / 3, 7);
		_setViewSize(tab2, SketchwareUtil.getDisplayWidthPixels(getApplicationContext()) / 3, 7);
		_setViewSize(tab1, SketchwareUtil.getDisplayWidthPixels(getApplicationContext()) / 3, 7);
		_SetTextSize(tab3_btn_txt, 13);
		_SetTextSize(tab2_btn_txt, 13);
		_SetTextSize(tab1_btn_txt, 13);
		tab1.setVisibility(View.GONE);
		tab2.setVisibility(View.GONE);
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
	
	
	public void _Time (final double _position, final TextView _textview) {
		c = Calendar.getInstance();
		time = c.getTimeInMillis() - _position;
		if (((time / 1000) / 3600) < 24) {
			c.setTimeInMillis((long)(_position));
			_textview.setText(new SimpleDateFormat("hh:mm a").format(c.getTime()));
		}
		else {
			c.setTimeInMillis((long)(_position));
			_textview.setText(new SimpleDateFormat("MMM d 'at' hh:mm a").format(c.getTime()));
		}
	}
	
	
	public void _round (final View _view, final double _num, final String _color) {
		try {
			android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
			gd.setColor(Color.parseColor(_color));
			
			
			float f = (float) _num;
			
			
			gd.setCornerRadius(f);
			_view.setBackground(gd);
			
		} catch(Exception e){ Log.e("Error: ", e.toString()); String error_code = e.toString();
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
							builder.setTitle("Error")
								.setMessage(error_code)
								.setCancelable(false)
								.setNegativeButton("ОК", new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int id) {
												
										}
								});
							AlertDialog alert = builder.create();
							alert.show();
		}
	}
	
	
	public void _RippleEffects (final String _color, final View _view) {
		android.content.res.ColorStateList clr = new android.content.res.ColorStateList(new int[][]{new int[]{}},new int[]{Color.parseColor(_color)});
		android.graphics.drawable.RippleDrawable ripdr = new android.graphics.drawable.RippleDrawable(clr, null, null);
		_view.setBackground(ripdr);
	}
	
	
	public void _FirebaseQuery () {
	}
	com.google.firebase.database.Query query;
	ValueEventListener valueEventListener1 = new ValueEventListener() {
		@Override
		public void onDataChange(DataSnapshot _param1) {
			try {
				all_users.clear();
				UserChats_LS.clear();
				UserChats_LM.clear();
				GenericTypeIndicator < HashMap< String, Object>> _ind = new GenericTypeIndicator<HashMap< String, Object>>() {};
				for (DataSnapshot _data : _param1.getChildren()) {
					HashMap <String, Object> _map= _data.getValue(_ind);
					if (_map.containsKey("uid")) {
						if (UserChats_LS.contains(_map.get("uid").toString())) {
							
						}
						else {
							if (_map.containsKey("last_message_time")) {
								UserChats_LM.add(_map);
								UserChats_LS.add(_data.getKey());
								Collections.reverse(UserChats_LM);
								SketchwareUtil.sortListMap(UserChats_LM, "last_message_time", false, false);
							}
							chats_rv.setAdapter(new Chats_rvAdapter(UserChats_LM));
							chats_rv.getAdapter().notifyDataSetChanged();
							chats_no.setVisibility(View.GONE);
						}
					}
				}
			} catch (Exception e) {
				
				showMessage(e.toString()); }
		}
		@Override
		public void onCancelled(DatabaseError databaseError) {} 
	};
	{
	}
	
	
	public void _libsben_animation (final View _view, final String _libsben, final Intent _libsben_intent) {
		_view.setTransitionName(_libsben);
		
		android.app.ActivityOptions optionsCompat = android.app.ActivityOptions.makeSceneTransitionAnimation(SearchActivity.this, _view, _libsben);
		        startActivity(_libsben_intent, optionsCompat.toBundle());
	}
	
	
	public class Chats_rvAdapter extends RecyclerView.Adapter<Chats_rvAdapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;
		public Chats_rvAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _inflater.inflate(R.layout.chatsc, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear9 = (LinearLayout) _view.findViewById(R.id.linear9);
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final LinearLayout nothing = (LinearLayout) _view.findViewById(R.id.nothing);
			final LinearLayout storylin = (LinearLayout) _view.findViewById(R.id.storylin);
			final LinearLayout linear2 = (LinearLayout) _view.findViewById(R.id.linear2);
			final LinearLayout propic_bg = (LinearLayout) _view.findViewById(R.id.propic_bg);
			final de.hdodenhof.circleimageview.CircleImageView circleimageview2 = (de.hdodenhof.circleimageview.CircleImageView) _view.findViewById(R.id.circleimageview2);
			final LinearLayout linear8 = (LinearLayout) _view.findViewById(R.id.linear8);
			final LinearLayout linear5 = (LinearLayout) _view.findViewById(R.id.linear5);
			final LinearLayout linear4 = (LinearLayout) _view.findViewById(R.id.linear4);
			final TextView name = (TextView) _view.findViewById(R.id.name);
			final TextView time = (TextView) _view.findViewById(R.id.time);
			final LinearLayout linear10 = (LinearLayout) _view.findViewById(R.id.linear10);
			final LinearLayout unread_lin = (LinearLayout) _view.findViewById(R.id.unread_lin);
			final AXEmojiTextView content = (AXEmojiTextView) _view.findViewById(R.id.content);
			final ImageView imageview2 = (ImageView) _view.findViewById(R.id.imageview2);
			final TextView textview1 = (TextView) _view.findViewById(R.id.textview1);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			_round(propic_bg, 360, "#193566");
			_round(unread_lin, 360, "#193566");
			name.setTextColor(0xFF607D8B);
			name.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/roboto_regular.ttf"), 0);
			time.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/roboto_light.ttf"), 0);
			content.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/roboto_regular.ttf"), 0);
			textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/roboto_light.ttf"), 0);
			unread_lin.setVisibility(View.INVISIBLE);
			imageview2.setVisibility(View.GONE);
			time.setVisibility(View.INVISIBLE);
			if (_data.get((int)_position).containsKey("uid") && (_data.get((int)_position).containsKey("firstname") && (_data.get((int)_position).containsKey("lastname") && (_data.get((int)_position).containsKey("avatar") && _data.get((int)_position).containsKey("last_message_uid"))))) {
				if (!_data.get((int)_position).get("uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					imageview2.setColorFilter(0xFF193566, PorterDuff.Mode.MULTIPLY);
					_radius_4("#64bb6a", "#ffffff", 6, 40, 40, 40, 40, linear8);
					if (_data.get((int)_position).get("firstname").toString().equals("") && _data.get((int)_position).get("lastname").toString().equals("")) {
						linear9.setVisibility(View.VISIBLE);
					}
					else {
						name.setText(_data.get((int)_position).get("firstname").toString().concat(" ".concat(_data.get((int)_position).get("lastname").toString())));
					}
					if (_data.get((int)_position).get("avatar").toString().equals("")) {
						linear9.setVisibility(View.VISIBLE);
					}
					else {
						com.bumptech.glide.Glide.with(getApplicationContext())
						.load(_data.get((int)_position).get("avatar").toString())
						.override(50, 50)
						.into(circleimageview2);
					}
					linear8.setVisibility(View.INVISIBLE);
					if (!_data.get((int)_position).get("lastseen").toString().equals("")) {
						Cal = Calendar.getInstance();
						if ((Cal.getTimeInMillis() - Double.parseDouble(_data.get((int)_position).get("lastseen").toString())) < 30000) {
							linear8.setVisibility(View.VISIBLE);
						}
					}
					if (_data.get((int)_position).containsKey("last_message_time")) {
						_Time(Double.parseDouble(_data.get((int)_position).get("last_message_time").toString()), time);
						time.setVisibility(View.VISIBLE);
					}
					else {
						
					}
					if (_data.get((int)_position).containsKey("last_message_uid")) {
						if (_data.get((int)_position).containsKey("last_message_deleted")) {
							if (_data.get((int)_position).get("last_message_deleted").toString().equals("true")) {
								imageview2.setVisibility(View.GONE);
								content.setText("Message Deleted.");
								if (!_data.get((int)_position).get("firstname").toString().equals("")) {
									content.setText(_data.get((int)_position).get("firstname").toString().concat(" Deleted a message."));
								}
							}
						}
						else {
							if (_data.get((int)_position).get("last_message_uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
								content.setText("You : ");
							}
							else {
								content.setText("");
								if (_data.get((int)_position).containsKey("last_message_status")) {
									if (_data.get((int)_position).get("last_message_status").toString().equals("Sent")) {
										content.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/roboto_light.ttf"), 1);
									}
									else {
										content.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/roboto_light.ttf"), 0);
									}
								}
							}
						}
						if (_data.get((int)_position).containsKey("last_message_type")) {
							if (_data.get((int)_position).get("last_message_type").toString().equals("image")) {
								if (_data.get((int)_position).get("last_message_uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
									content.setText("You sent an image.");
									content.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/roboto_regular.ttf"), 2);
								}
								else {
									content.setText(_data.get((int)_position).get("firstname").toString().concat(" Sent a photo."));
									if (_data.get((int)_position).containsKey("last_message_status")) {
										if (_data.get((int)_position).get("last_message_status").toString().equals("Sent")) {
											content.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/roboto_light.ttf"), 3);
										}
										else {
											content.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/roboto_light.ttf"), 2);
										}
									}
								}
							}
							else {
								if (_data.get((int)_position).get("last_message_type").toString().equals("love")) {
									imageview2.setVisibility(View.VISIBLE);
								}
								else {
									if (_data.get((int)_position).get("last_message_type").toString().equals("text")) {
										if (_data.get((int)_position).containsKey("last_message_text")) {
											content.setText(content.getText().toString().concat(_data.get((int)_position).get("last_message_text").toString()));
										}
										else {
											content.setText("Start chatting with ".concat(_data.get((int)_position).get("firstname").toString()));
										}
									}
									else {
										content.setText("Start chatting with ".concat(_data.get((int)_position).get("firstname").toString()));
									}
								}
								if (_data.get((int)_position).containsKey("UnreadMessagesNum")) {
									if (_data.get((int)_position).get("UnreadMessagesNum").toString().equals("")) {
										unread_lin.setVisibility(View.INVISIBLE);
									}
									else {
										if (_data.get((int)_position).get("UnreadMessagesNum").toString().equals("0")) {
											unread_lin.setVisibility(View.INVISIBLE);
										}
										else {
											textview1.setText(_data.get((int)_position).get("UnreadMessagesNum").toString());
											unread_lin.setVisibility(View.VISIBLE);
										}
									}
								}
								else {
									unread_lin.setVisibility(View.INVISIBLE);
								}
							}
						}
					}
					else {
						linear9.setVisibility(View.GONE);
					}
					if (storiesMap.containsKey(_data.get((int)_position).get("uid").toString())) {
						if (sp_seen.getString(storiesMap.get(_data.get((int)_position).get("uid").toString()).toString(), "").equals("")) {
							_radius_4("#00000000", "#FF193566", 8, 100, 100, 100, 100, storylin);
						}
						else {
							_radius_4("#00000000", "#FFDADADA", 8, 100, 100, 100, 100, storylin);
						}
						storylin.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View _view) {
								toViewStory.setClass(getApplicationContext(), StoryActivity.class);
								toViewStory.setClass(getApplicationContext(), StoryActivity.class);
								toViewStory.putExtra("uid", _data.get((int)_position).get("uid").toString());
								startActivity(toViewStory);
							}
						});
					}
					else {
						_radius_4("#00000000", "#00000000", 8, 100, 100, 100, 100, storylin);
					}
					linear1.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View _view) {
							toGoChat.setClass(getApplicationContext(), ChatActivity.class);
							toGoChat.putExtra("uid", _data.get((int)_position).get("uid").toString());
							toGoChat.putExtra("type", "private");
							startActivity(toGoChat);
						}
					});
				}
				else {
					linear9.setVisibility(View.GONE);
				}
			}
			else {
				linear9.setVisibility(View.GONE);
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
	
	public class Media_rvAdapter extends RecyclerView.Adapter<Media_rvAdapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;
		public Media_rvAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _inflater.inflate(R.layout.sharedphotoc, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			
			if (_data.get((int)_position).containsKey("image")) {
				Glide.with(getApplicationContext())
				.load(_data.get((int)_position).get("image").toString())
				.listener(new RequestListener<Drawable>() {
							@Override public boolean onLoadFailed(@androidx.annotation.Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
										linear1.setVisibility(View.GONE);
										return false;
							}
							
							@Override public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
										
										return false;
							}})
				.into(imageview1);
				
				imageview1.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						toContinue.setClass(getApplicationContext(), PhotoviewerActivity.class);
						toContinue.putExtra("uid", _data.get((int)_position).get("uid").toString());
						toContinue.putExtra("image_link", _data.get((int)_position).get("image").toString());
						toContinue.putExtra("image_time", _data.get((int)_position).get("time").toString());
						toContinue.putExtra("firstname", _data.get((int)_position).get("firstname").toString());
						toContinue.putExtra("lastname", _data.get((int)_position).get("lastname").toString());
						_libsben_animation(imageview1, "image", toContinue);
					}
				});
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
