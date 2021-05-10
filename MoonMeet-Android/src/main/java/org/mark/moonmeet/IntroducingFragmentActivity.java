package org.mark.moonmeet;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


public class IntroducingFragmentActivity extends Fragment {
	
	private FrameLayout framelayout;
	private LinearLayout background;
	private CardView cardview1;
	private LinearLayout li_hezhom_lkol;
	private LinearLayout card_back;
	private LinearLayout back_img;
	private LinearLayout back_color;
	private LinearLayout base;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private LinearLayout linear7;
	private LinearLayout linear8;
	private TextView textview2;
	private TextView textview3;
	private ImageView wizard1;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.introducing_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		com.google.firebase.FirebaseApp.initializeApp(getContext());
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		framelayout = (FrameLayout) _view.findViewById(R.id.framelayout);
		background = (LinearLayout) _view.findViewById(R.id.background);
		cardview1 = (CardView) _view.findViewById(R.id.cardview1);
		li_hezhom_lkol = (LinearLayout) _view.findViewById(R.id.li_hezhom_lkol);
		card_back = (LinearLayout) _view.findViewById(R.id.card_back);
		back_img = (LinearLayout) _view.findViewById(R.id.back_img);
		back_color = (LinearLayout) _view.findViewById(R.id.back_color);
		base = (LinearLayout) _view.findViewById(R.id.base);
		linear4 = (LinearLayout) _view.findViewById(R.id.linear4);
		linear5 = (LinearLayout) _view.findViewById(R.id.linear5);
		linear6 = (LinearLayout) _view.findViewById(R.id.linear6);
		linear7 = (LinearLayout) _view.findViewById(R.id.linear7);
		linear8 = (LinearLayout) _view.findViewById(R.id.linear8);
		textview2 = (TextView) _view.findViewById(R.id.textview2);
		textview3 = (TextView) _view.findViewById(R.id.textview3);
		wizard1 = (ImageView) _view.findViewById(R.id.wizard1);
	}
	
	private void initializeLogic() {
		if (getContext().getAssets() != null) {
			textview2.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/royal_404.ttf"), 1);
			textview3.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/royal_404.ttf"), 0);
		}
	}
	
	@Override
	public void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
	}
}
