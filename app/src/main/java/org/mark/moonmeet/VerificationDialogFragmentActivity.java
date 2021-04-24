package org.mark.moonmeet;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;


public class VerificationDialogFragmentActivity extends DialogFragment {
	
	private LinearLayout bg;
	private CardView cardview;
	private LinearLayout card;
	private LinearLayout main;
	private LinearLayout top_linear;
	private LinearLayout middle_linear;
	private LinearLayout last_linear;
	private TextView moonmeet;
	private TextView procces_txt;
	private LinearLayout space;
	private TextView stop_txt;
	private LinearLayout space_between;
	private TextView continue_txt;
	
	private SharedPreferences sp_df;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.verification_dialog_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		com.google.firebase.FirebaseApp.initializeApp(getContext());
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		bg = (LinearLayout) _view.findViewById(R.id.bg);
		cardview = (CardView) _view.findViewById(R.id.cardview);
		card = (LinearLayout) _view.findViewById(R.id.card);
		main = (LinearLayout) _view.findViewById(R.id.main);
		top_linear = (LinearLayout) _view.findViewById(R.id.top_linear);
		middle_linear = (LinearLayout) _view.findViewById(R.id.middle_linear);
		last_linear = (LinearLayout) _view.findViewById(R.id.last_linear);
		moonmeet = (TextView) _view.findViewById(R.id.moonmeet);
		procces_txt = (TextView) _view.findViewById(R.id.procces_txt);
		space = (LinearLayout) _view.findViewById(R.id.space);
		stop_txt = (TextView) _view.findViewById(R.id.stop_txt);
		space_between = (LinearLayout) _view.findViewById(R.id.space_between);
		continue_txt = (TextView) _view.findViewById(R.id.continue_txt);
		sp_df = getContext().getSharedPreferences("sp_df", Activity.MODE_PRIVATE);
		
		stop_txt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				sp_df.edit().putString("cancelled", "yes").commit();
				dismiss();
			}
		});
		
		continue_txt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				dismiss();
			}
		});
	}
	
	private void initializeLogic() {
	}
	
	@Override
	public void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Dialog dialog = getDialog();
		  if (dialog != null) { 
			  int width = ViewGroup.LayoutParams.MATCH_PARENT;
			  int height = ViewGroup.LayoutParams.MATCH_PARENT; 
			  dialog.getWindow().setLayout(width, height);
			dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); 
			   }
	}}
