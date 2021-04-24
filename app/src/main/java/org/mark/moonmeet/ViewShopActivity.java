package org.mark.moonmeet;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Random;

public class ViewShopActivity extends AppCompatActivity {
	
	private String url = "";
	private String name = "";
	private String price = "";
	private String image = "";
	private String star = "";
	private String specific = "";
	private String OldWebisteLink = "";
	
	private LinearLayout topbar;
	private LinearLayout divider_top;
	private LinearLayout background;
	private LinearLayout nav;
	private ImageView back;
	private LinearLayout space_top;
	private NestedScrollView NestedScroller;
	private LinearLayout SemiHolder;
	private LinearLayout tobuyimage_holder;
	private LinearLayout Base_Purshase_Linear;
	private LinearLayout specific_holder;
	private LinearLayout delivery_holder;
	private LinearLayout semi_divider_holder;
	private LinearLayout condition_holder;
	private LinearLayout shop_infos_holder;
	private LinearLayout the_all_recommendation_holder;
	private ImageView tobuyimage;
	private TextView tobuymoney;
	private TextView textview_cargo;
	private LinearLayout star_holder;
	private TextView cargo_star;
	private TextView specific_base_text;
	private TextView specific_description;
	private ImageView specific_more_info_bsh;
	private TextView delivery_dummy_txt;
	private LinearLayout delivery_semi_holder;
	private ImageView open_delivery_bsh;
	private LinearLayout delivery_first_text_holder;
	private LinearLayout delivery_second_text_holder;
	private TextView delivery_txt_base_desceiption;
	private TextView delivery_under_txt;
	private TextView tax_delivery_txt;
	private LinearLayout divider;
	private TextView condition_dummy_text;
	private LinearLayout condition_all_things_holder;
	private ImageView open_condition_bsh;
	private LinearLayout condition_1_checked_holder;
	private LinearLayout condition_2_checked_holder;
	private TextView condition_before_last_condition;
	private TextView condition_last_description;
	private ImageView checkbox_image_1;
	private TextView right_the_checkbox_image_1;
	private ImageView checkbox_image_2;
	private TextView right_the_checkbox_image_2;
	private LinearLayout shop_helper;
	private LinearLayout rating_holder;
	private LinearLayout ka7la_divider;
	private TextView gotoshop_textview;
	private LinearLayout shop_image_holder;
	private TextView shop_helper_name;
	private ImageView shop_image;
	private LinearLayout shop_rating_holder;
	private LinearLayout space_between_rating1;
	private LinearLayout delivery_on_time_holder;
	private LinearLayout space_beetwen_rating_2;
	private LinearLayout chat_response_holder;
	private TextView shop_rating_percentage;
	private TextView shop_rating_txt;
	private TextView delivery_on_time_percentage;
	private TextView delivery_on_time_txt;
	private TextView chat_response_rate_percentage;
	private TextView chat_response_rate_txt;
	private LinearLayout base_recommendation_linear_text_holder;
	private LinearLayout recommendation_linear;
	private LinearLayout recommendation_text_holder;
	private TextView textview_recommend;
	private LinearLayout recommend;
	private LinearLayout recommend_page;
	private LinearLayout recommendation_1_2_holder;
	private LinearLayout recommendation_3_4_holder;
	private LinearLayout recommendation_1_holder_cd;
	private LinearLayout recommendation_2_holder_cd;
	private LinearLayout recommendation_1_image_holder;
	private TextView recommendation_1_money;
	private ImageView recommendation_1_image;
	private LinearLayout recommendation_2_image_holder;
	private TextView recommendation_2_money;
	private ImageView recommendation_2_image;
	private LinearLayout recommendation_3_holder_cd;
	private LinearLayout recommendation_4_holder_cd;
	private LinearLayout recommendation_3_image_holder;
	private TextView recommendation_3_money;
	private ImageView recommendation_3_image;
	private LinearLayout recommendation_4_image_holder;
	private TextView recommendation_4_money;
	private ImageView recommendation_4_image;
	private LinearLayout nav_mini_shop_holder;
	private LinearLayout nav_go_to_website_holder;
	private LinearLayout nav_mini_chat_holder;
	private ImageView nav_mini_shop_image;
	private TextView nav_mini_shop_txt;
	private TextView nav_go_to_website_txt;
	private ImageView nav_mini_chat_image;
	private TextView nav_mini_chat_text;
	
	private Intent toKa7la = new Intent();
	private Intent toObject = new Intent();
	private Intent toWeb = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.view_shop);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		topbar = (LinearLayout) findViewById(R.id.topbar);
		divider_top = (LinearLayout) findViewById(R.id.divider_top);
		background = (LinearLayout) findViewById(R.id.background);
		nav = (LinearLayout) findViewById(R.id.nav);
		back = (ImageView) findViewById(R.id.back);
		space_top = (LinearLayout) findViewById(R.id.space_top);
		NestedScroller = (NestedScrollView) findViewById(R.id.NestedScroller);
		SemiHolder = (LinearLayout) findViewById(R.id.SemiHolder);
		tobuyimage_holder = (LinearLayout) findViewById(R.id.tobuyimage_holder);
		Base_Purshase_Linear = (LinearLayout) findViewById(R.id.Base_Purshase_Linear);
		specific_holder = (LinearLayout) findViewById(R.id.specific_holder);
		delivery_holder = (LinearLayout) findViewById(R.id.delivery_holder);
		semi_divider_holder = (LinearLayout) findViewById(R.id.semi_divider_holder);
		condition_holder = (LinearLayout) findViewById(R.id.condition_holder);
		shop_infos_holder = (LinearLayout) findViewById(R.id.shop_infos_holder);
		the_all_recommendation_holder = (LinearLayout) findViewById(R.id.the_all_recommendation_holder);
		tobuyimage = (ImageView) findViewById(R.id.tobuyimage);
		tobuymoney = (TextView) findViewById(R.id.tobuymoney);
		textview_cargo = (TextView) findViewById(R.id.textview_cargo);
		star_holder = (LinearLayout) findViewById(R.id.star_holder);
		cargo_star = (TextView) findViewById(R.id.cargo_star);
		specific_base_text = (TextView) findViewById(R.id.specific_base_text);
		specific_description = (TextView) findViewById(R.id.specific_description);
		specific_more_info_bsh = (ImageView) findViewById(R.id.specific_more_info_bsh);
		delivery_dummy_txt = (TextView) findViewById(R.id.delivery_dummy_txt);
		delivery_semi_holder = (LinearLayout) findViewById(R.id.delivery_semi_holder);
		open_delivery_bsh = (ImageView) findViewById(R.id.open_delivery_bsh);
		delivery_first_text_holder = (LinearLayout) findViewById(R.id.delivery_first_text_holder);
		delivery_second_text_holder = (LinearLayout) findViewById(R.id.delivery_second_text_holder);
		delivery_txt_base_desceiption = (TextView) findViewById(R.id.delivery_txt_base_desceiption);
		delivery_under_txt = (TextView) findViewById(R.id.delivery_under_txt);
		tax_delivery_txt = (TextView) findViewById(R.id.tax_delivery_txt);
		divider = (LinearLayout) findViewById(R.id.divider);
		condition_dummy_text = (TextView) findViewById(R.id.condition_dummy_text);
		condition_all_things_holder = (LinearLayout) findViewById(R.id.condition_all_things_holder);
		open_condition_bsh = (ImageView) findViewById(R.id.open_condition_bsh);
		condition_1_checked_holder = (LinearLayout) findViewById(R.id.condition_1_checked_holder);
		condition_2_checked_holder = (LinearLayout) findViewById(R.id.condition_2_checked_holder);
		condition_before_last_condition = (TextView) findViewById(R.id.condition_before_last_condition);
		condition_last_description = (TextView) findViewById(R.id.condition_last_description);
		checkbox_image_1 = (ImageView) findViewById(R.id.checkbox_image_1);
		right_the_checkbox_image_1 = (TextView) findViewById(R.id.right_the_checkbox_image_1);
		checkbox_image_2 = (ImageView) findViewById(R.id.checkbox_image_2);
		right_the_checkbox_image_2 = (TextView) findViewById(R.id.right_the_checkbox_image_2);
		shop_helper = (LinearLayout) findViewById(R.id.shop_helper);
		rating_holder = (LinearLayout) findViewById(R.id.rating_holder);
		ka7la_divider = (LinearLayout) findViewById(R.id.ka7la_divider);
		gotoshop_textview = (TextView) findViewById(R.id.gotoshop_textview);
		shop_image_holder = (LinearLayout) findViewById(R.id.shop_image_holder);
		shop_helper_name = (TextView) findViewById(R.id.shop_helper_name);
		shop_image = (ImageView) findViewById(R.id.shop_image);
		shop_rating_holder = (LinearLayout) findViewById(R.id.shop_rating_holder);
		space_between_rating1 = (LinearLayout) findViewById(R.id.space_between_rating1);
		delivery_on_time_holder = (LinearLayout) findViewById(R.id.delivery_on_time_holder);
		space_beetwen_rating_2 = (LinearLayout) findViewById(R.id.space_beetwen_rating_2);
		chat_response_holder = (LinearLayout) findViewById(R.id.chat_response_holder);
		shop_rating_percentage = (TextView) findViewById(R.id.shop_rating_percentage);
		shop_rating_txt = (TextView) findViewById(R.id.shop_rating_txt);
		delivery_on_time_percentage = (TextView) findViewById(R.id.delivery_on_time_percentage);
		delivery_on_time_txt = (TextView) findViewById(R.id.delivery_on_time_txt);
		chat_response_rate_percentage = (TextView) findViewById(R.id.chat_response_rate_percentage);
		chat_response_rate_txt = (TextView) findViewById(R.id.chat_response_rate_txt);
		base_recommendation_linear_text_holder = (LinearLayout) findViewById(R.id.base_recommendation_linear_text_holder);
		recommendation_linear = (LinearLayout) findViewById(R.id.recommendation_linear);
		recommendation_text_holder = (LinearLayout) findViewById(R.id.recommendation_text_holder);
		textview_recommend = (TextView) findViewById(R.id.textview_recommend);
		recommend = (LinearLayout) findViewById(R.id.recommend);
		recommend_page = (LinearLayout) findViewById(R.id.recommend_page);
		recommendation_1_2_holder = (LinearLayout) findViewById(R.id.recommendation_1_2_holder);
		recommendation_3_4_holder = (LinearLayout) findViewById(R.id.recommendation_3_4_holder);
		recommendation_1_holder_cd = (LinearLayout) findViewById(R.id.recommendation_1_holder_cd);
		recommendation_2_holder_cd = (LinearLayout) findViewById(R.id.recommendation_2_holder_cd);
		recommendation_1_image_holder = (LinearLayout) findViewById(R.id.recommendation_1_image_holder);
		recommendation_1_money = (TextView) findViewById(R.id.recommendation_1_money);
		recommendation_1_image = (ImageView) findViewById(R.id.recommendation_1_image);
		recommendation_2_image_holder = (LinearLayout) findViewById(R.id.recommendation_2_image_holder);
		recommendation_2_money = (TextView) findViewById(R.id.recommendation_2_money);
		recommendation_2_image = (ImageView) findViewById(R.id.recommendation_2_image);
		recommendation_3_holder_cd = (LinearLayout) findViewById(R.id.recommendation_3_holder_cd);
		recommendation_4_holder_cd = (LinearLayout) findViewById(R.id.recommendation_4_holder_cd);
		recommendation_3_image_holder = (LinearLayout) findViewById(R.id.recommendation_3_image_holder);
		recommendation_3_money = (TextView) findViewById(R.id.recommendation_3_money);
		recommendation_3_image = (ImageView) findViewById(R.id.recommendation_3_image);
		recommendation_4_image_holder = (LinearLayout) findViewById(R.id.recommendation_4_image_holder);
		recommendation_4_money = (TextView) findViewById(R.id.recommendation_4_money);
		recommendation_4_image = (ImageView) findViewById(R.id.recommendation_4_image);
		nav_mini_shop_holder = (LinearLayout) findViewById(R.id.nav_mini_shop_holder);
		nav_go_to_website_holder = (LinearLayout) findViewById(R.id.nav_go_to_website_holder);
		nav_mini_chat_holder = (LinearLayout) findViewById(R.id.nav_mini_chat_holder);
		nav_mini_shop_image = (ImageView) findViewById(R.id.nav_mini_shop_image);
		nav_mini_shop_txt = (TextView) findViewById(R.id.nav_mini_shop_txt);
		nav_go_to_website_txt = (TextView) findViewById(R.id.nav_go_to_website_txt);
		nav_mini_chat_image = (ImageView) findViewById(R.id.nav_mini_chat_image);
		nav_mini_chat_text = (TextView) findViewById(R.id.nav_mini_chat_text);
		
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		gotoshop_textview.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				toWeb.setAction(Intent.ACTION_VIEW);
				toWeb.setData(Uri.parse("https://www.facebook.com/ka7lashop/"));
				startActivity(toWeb);
			}
		});
		
		nav_mini_shop_holder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				toWeb.setAction(Intent.ACTION_VIEW);
				toWeb.setData(Uri.parse("https://www.facebook.com/ka7lashop/"));
				startActivity(toWeb);
			}
		});
		
		nav_go_to_website_holder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (!url.equals("")) {
					if (url.contains("https://") && url.contains(".com/")) {
						toWeb.setAction(Intent.ACTION_VIEW);
						toWeb.setData(Uri.parse(url));
						startActivity(toWeb);
					}
					else {
						if (!url.contains("https://") && url.contains(".com/")) {
							OldWebisteLink = url;
							url = "https://".concat(OldWebisteLink);
							toWeb.setAction(Intent.ACTION_VIEW);
							toWeb.setData(Uri.parse(url));
							startActivity(toWeb);
						}
						else {
							SketchwareUtil.showMessage(getApplicationContext(), "Item doesn't exist on our server. contact seller for more informations.");
						}
					}
				}
			}
		});
		
		nav_mini_chat_holder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				toObject.setClass(getApplicationContext(), ChatActivity.class);
				toObject.putExtra("uid", "VHTcld2ZmjQmSLmit9zniFTsneA2");
				startActivity(toObject);
			}
		});
		
		nav_mini_shop_image.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				toWeb.setAction(Intent.ACTION_VIEW);
				toWeb.setData(Uri.parse("https://www.facebook.com/ka7lashop/"));
				startActivity(toWeb);
			}
		});
		
		nav_mini_chat_image.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				toObject.setClass(getApplicationContext(), ChatActivity.class);
				toObject.putExtra("uid", "VHTcld2ZmjQmSLmit9zniFTsneA2");
				startActivity(toObject);
			}
		});
	}
	
	private void initializeLogic() {
		toKa7la = getIntent();
		if (toKa7la.hasExtra("name")) {
			name = getIntent().getStringExtra("name");
			textview_cargo.setText(name);
		}
		if (toKa7la.hasExtra("price")) {
			price = getIntent().getStringExtra("price");
			tobuymoney.setText(price);
		}
		if (toKa7la.hasExtra("image")) {
			image = getIntent().getStringExtra("image");
			Glide.with(getApplicationContext()).load(Uri.parse(image)).into(tobuyimage);
		}
		if (toKa7la.hasExtra("url")) {
			url = getIntent().getStringExtra("url");
		}
		if (toKa7la.hasExtra("star")) {
			star = getIntent().getStringExtra("star");
			if (star.equals("1")) {
				cargo_star.setText("⭐");
			}
			else {
				if (star.equals("2")) {
					cargo_star.setText("⭐⭐");
				}
				else {
					if (star.equals("3")) {
						cargo_star.setText("⭐⭐⭐");
					}
					else {
						if (star.equals("4")) {
							cargo_star.setText("⭐⭐⭐⭐");
						}
						else {
							if (star.equals("5")) {
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
		if (toKa7la.hasExtra("specific")) {
			specific = getIntent().getStringExtra("specific");
			specific_description.setText(specific);
		}
		_Design();
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
	
	
	public void _Design () {
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		nav.setElevation((int)10);
		topbar.setElevation((int)2);
		_CardStyle(recommendation_1_holder_cd, 3, 7, "#FFECF0F3", false);
		_CardStyle(recommendation_2_holder_cd, 3, 7, "#FFECF0F3", false);
		_CardStyle(recommendation_3_holder_cd, 3, 7, "#FFECF0F3", false);
		_CardStyle(recommendation_4_holder_cd, 3, 7, "#FFECF0F3", false);
		_CardStyle(shop_image_holder, 2, 50, "#FFECF0F3", false);
		_CardStyle(nav_go_to_website_holder, 1, 50, "#FF193566", false);
		back.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF193566"),
			Color.parseColor("#FF193566")}));
		specific_more_info_bsh.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF193566"),
			Color.parseColor("#FF193566")}));
		open_delivery_bsh.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF193566"),
			Color.parseColor("#FF193566")}));
		nav_mini_shop_image.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF193566"),
			Color.parseColor("#FF193566")}));
		nav_mini_chat_image.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF193566"),
			Color.parseColor("#FF193566")}));
		open_condition_bsh.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF193566"),
			Color.parseColor("#FF193566")}));
		androidx.appcompat.widget.TooltipCompat.setTooltipText(back,"Back");
		androidx.appcompat.widget.TooltipCompat.setTooltipText(nav_mini_shop_image,"Go-To Ka7la HomePage");
		androidx.appcompat.widget.TooltipCompat.setTooltipText(nav_mini_chat_image,"Contact Seller");
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
