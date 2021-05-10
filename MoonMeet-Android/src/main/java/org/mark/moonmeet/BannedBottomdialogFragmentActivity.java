package org.mark.moonmeet;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BannedBottomdialogFragmentActivity extends BottomSheetDialogFragment {
	
	private String BanHelper_STR = "";
	
	private NestedScrollView NestedScrollView;
	private LinearLayout LinearLayoutHolder;
	private TextView YouGotBannedText;
	private TextView TextView_Reason;
	private LinearLayout Divider;
	private TextView TextView_BanHelper;
	
	private SharedPreferences ban_reason;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.banned_bottomdialog_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		com.google.firebase.FirebaseApp.initializeApp(getContext());
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		NestedScrollView = (NestedScrollView) _view.findViewById(R.id.NestedScrollView);
		LinearLayoutHolder = (LinearLayout) _view.findViewById(R.id.LinearLayoutHolder);
		YouGotBannedText = (TextView) _view.findViewById(R.id.YouGotBannedText);
		TextView_Reason = (TextView) _view.findViewById(R.id.TextView_Reason);
		Divider = (LinearLayout) _view.findViewById(R.id.Divider);
		TextView_BanHelper = (TextView) _view.findViewById(R.id.TextView_BanHelper);
		ban_reason = getContext().getSharedPreferences("ban_reason", Activity.MODE_PRIVATE);
	}
	
	private void initializeLogic() {
		BanHelper_STR = "Sorry but you are banned cause of violating Moon Meet's Standards.\nPlease remember that we ban only people who doesn't respect our Rules or Terms & Conditions.\nlike sending or posting bad words or photos in the stories or in conversations so that will result to a permanent ban.\nIf you think that doesn't applying to you or something goes wrong, contact us to rayensbai2@gmail.com";
		TextView_BanHelper.setText(BanHelper_STR);
		if (ban_reason.getString("reason", "").equals("")) {
			TextView_Reason.setText("No reason provided by the admin.");
		}
		else {
			TextView_Reason.setText(ban_reason.getString("reason", ""));
		}
	}
	
	@Override
	public void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		switch (_requestCode) {
			
			default:
			break;
		}
	}
}
