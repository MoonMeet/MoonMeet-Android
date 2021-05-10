package org.mark.moonmeet;

import android.content.Context;
import android.os.Build;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import org.mark.moonmeet.transformer.ZoomOutSlideTransformer;
import org.mark.moonmeet.ui.BaseFragment;

import java.util.ArrayList;
import java.util.Random;

public class IntroActivity extends BaseFragment {
	
	private LinearLayout holder;
	private ViewPager viewpager1;
	private LinearLayout linear_dots_box;
	private DotsIndicator dots_indicator;
	
	
	@Override
	public View createView(Context context) {
		fragmentView = new FrameLayout(context);
		actionBar.setAddToContainer(false);
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.intro,(ViewGroup) fragmentView, false);
		((ViewGroup) fragmentView).addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		initialize(context);
		com.google.firebase.FirebaseApp.initializeApp(getThis());
		initializeLogic();
		return fragmentView;
	}
	
	private void initialize(Context context) {
		holder = findViewById(R.id.holder);
		viewpager1 = findViewById(R.id.viewpager1);
		linear_dots_box = findViewById(R.id.linear_dots_box);
		dots_indicator = findViewById(R.id.dots_indicator);
	}
	
	private void initializeLogic() {
		viewpager1.setAdapter(new MyFragmentAdapter(getApplicationContext(), getSupportFragmentManager(), 3));
		viewpager1.setPageTransformer(true, new ZoomOutSlideTransformer());
		dots_indicator.setViewPager(viewpager1);
	}

	public class MyFragmentAdapter extends FragmentStatePagerAdapter {
		Context context;
		int tabCount;

		public MyFragmentAdapter(Context context, FragmentManager fm, int tabCount) {
			super(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
			this.context = context;
			this.tabCount = tabCount;
		}
		
		@Override
		public int getCount(){
			return tabCount;
		}
		
		@Override
		public CharSequence getPageTitle(int _position) {
			
			return null;
		}
		
		@Override
		public Fragment getItem(int _position) {
			if (_position == 0) {
				return new IntroducingFragmentActivity();
			}
			if (_position == 1) {
				return new MessagingFragmentActivity();
			}
			if (_position == 2) {
				return new GetstartedFragmentActivity();
			}
			return null;
		}
		
	}

	@Override
	public boolean isSwipeBackEnabled(MotionEvent event) {
		return false;
	}

	@Override
	public boolean onBackPressed() {
		finishAffinity();
		return true;
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
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getThis().getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getThis().getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getThis().getResources().getDisplayMetrics().heightPixels;
	}
}
