package org.mark.moonmeet;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import java.util.ArrayList;
import java.util.Random;

public class FaqInfoActivity extends AppCompatActivity {
	
	private String FrequentlyAskedQuestions = "";
	
	private LinearLayout topbar;
	private NestedScrollView scroller;
	private ImageView imageview1;
	private TextView topbar_txt;
	private LinearLayout framelayout;
	private TextView privacy_policy;
	private TextView privacy_policy_txt;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.faq_info);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		topbar = (LinearLayout) findViewById(R.id.topbar);
		scroller = (NestedScrollView) findViewById(R.id.scroller);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		topbar_txt = (TextView) findViewById(R.id.topbar_txt);
		framelayout = (LinearLayout) findViewById(R.id.framelayout);
		privacy_policy = (TextView) findViewById(R.id.privacy_policy);
		privacy_policy_txt = (TextView) findViewById(R.id.privacy_policy_txt);
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
	}
	
	private void initializeLogic() {
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		updateStatusBar();
		imageview1.setColorFilter(0xFF193566, PorterDuff.Mode.MULTIPLY);
		topbar.setElevation((int)2);
		topbar_txt.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/rmedium.ttf"), 0);
		android.widget.RelativeLayout rl = new android.widget.RelativeLayout(FaqInfoActivity.this);
		
		rl.setLayoutParams(new LinearLayout.LayoutParams(-1,-1));
		
		framelayout.addView(rl);
		FrequentlyAskedQuestions = "Q&A 1 : what is moonmeet ?\n\n-- Moon Meet is an instant messaging application that gives you a good acces to connect with friends, make relationships, share stories etc..\n\nQ&A 2 : who it is for ?\n\n-- Moon Meet is allowed for everyone who wanna chat and make new relationships and new friends.\n\nQ&A 3 : why moonmeet ?\n\n-- Moon Meet because it is a special platform.\n\nQ&A 4 : how i can get moonmeet ?\n\n-- You can get moonmeet by going to the website or the mobile application.\n\nQ&A 5 : how i can join moonmeet ?\n\n-- You can join moonmeet by creating an account and join the other people.\n\nQ&A 6 : can i delete a message ?\n\n— Yes you can delete your messages anytime.\n\nQ&A 7 : can i change my informations ?\n\n— Yes, you can change them anytime.\n\nQ&A 8 : can i delete my account ?\n\n-- Sure, you can delete your accountn anytime.\n\nQ&A 9 : who can see me online ?\n\n-- Anyone in the platform can see you online because Moon Meet is a fully public platform.\n\nQ&A 10 : are our passwords in safety ?\n\n-- of Course ! your passwords are fully hashed and no one can see it.\n\nQ&A 11 : who is behind Moon Meet ?\n\n-- Behind Moon Meet, there is Aziz Becha ( Co, Founder & Web Developer) And Rayen Mark ( CO, Founder & Mobile Developer) and we are from Tunisia.\n\nQ&A 12 : can i report a problem ?\n\n-- Sure ! reporting bugs / problems is the duty of everyone on our platform !\n\nQ&A 13 : can i help to improove this platform ?\n\n-- Yes, if you have some good ideas you can share it with us and be one of the contributors.\n\nQ&A 14 : who can see my profile ?\n\n-- Everyone on the platform can see your profile, but he cannot see your phon number for security purposes.\n\nQ&A 15 : which informations can Moon Meet gather about me ?\n\n-- Moon Meet collects public informations about you like firtname, lastname, birth date, username, bio, email (shortly, the informations that you enter while doing the registration.)";
		privacy_policy_txt.setText(FrequentlyAskedQuestions);
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
