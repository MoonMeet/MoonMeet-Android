package org.mark.moonmeet;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.TooltipCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.mark.moonmeet.ui.BaseFragment;
import org.mark.moonmeet.utils.AndroidUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class OtpActivity extends BaseFragment {

	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();

	private String verification_id = "";
	private double n = 0;
	private boolean Verification = false;
	private String phone = "";
	private String username = "";
	private HashMap<String, Object> Map = new HashMap<>();
	private HashMap<String, Object> ChatMap = new HashMap<>();
	private String uid = "";
	private boolean isNewUser = false;

	private ArrayList<String> dialing_code = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> countries = new ArrayList<>();
	private ArrayList<String> taked_usernames = new ArrayList<>();

	private LinearLayout topbar;
	private LinearLayout main;
	private TextView yourphone;
	private LinearLayout space_bar;
	private ImageView done;
	private LinearLayout part1;
	private LinearLayout part2;
	private LinearLayout space_top;
	private LinearLayout country_holder;
	private LinearLayout space_bottom;
	private LinearLayout holder;
	private TextView information;
	private TextView country_text;
	private LinearLayout divider_country;
	private TextView plus;
	private LinearLayout dial_holder;
	private LinearLayout number_holder;
	private EditText dial_code;
	private LinearLayout divider_dialcode;
	private EditText number_edittext;
	private LinearLayout divider_number;
	private ImageView sms;
	private TextView enter_code_txt;
	private TextView notice;
	private TextView user_phone;
	private LinearLayout otp_holder;
	private TextView didnt_get;
	private LinearLayout first;
	private LinearLayout second;
	private LinearLayout third;
	private LinearLayout forth;
	private LinearLayout fiver;
	private LinearLayout sixer;
	private EditText first_edittext;
	private LinearLayout first_divider;
	private EditText second_edittext;
	private LinearLayout second_divider;
	private EditText third_edittext;
	private LinearLayout third_divider;
	private EditText forth_edittext;
	private LinearLayout forth_divider;
	private EditText fiver_edittext;
	private LinearLayout fiver_divider;
	private EditText sixer_edittext;
	private LinearLayout sixer_divider;

	private PhoneAuthProvider.OnVerificationStateChangedCallbacks Pauth;
	private PhoneAuthProvider.ForceResendingToken Pauth_resendToken;
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
	private Intent toContinue = new Intent();
	private SharedPreferences sp_df;
	private TimerTask timer;
	private ObjectAnimator anim = new ObjectAnimator();
	private ObjectAnimator toolbar_txt = new ObjectAnimator();
	private ObjectAnimator extra = new ObjectAnimator();
	private ObjectAnimator extra2 = new ObjectAnimator();
	private RequestNetwork dial_calling;
	private RequestNetwork.RequestListener _dial_calling_request_listener;
	private DatabaseReference taked_username = _firebase.getReference("takedusername");
	private ChildEventListener _taked_username_child_listener;
	private DatabaseReference users = _firebase.getReference("users");
	private ChildEventListener _users_child_listener;
	private SharedPreferences sp_mydt;
	private DatabaseReference userchats = _firebase.getReference("userchats");
	private ChildEventListener _userchats_child_listener;
	private FirebaseAuthSettings firebaseAuthSettings;
	private String phoneNumber;
	private boolean forceGetData = true;

	public OtpActivity(Bundle args) {
		super(args);
	}

	@Override
	public View createView(@NonNull Context context) {
		fragmentView = new FrameLayout(context);
		actionBar.setAddToContainer(false);
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.otp, (ViewGroup) fragmentView, false);
		((ViewGroup) fragmentView).addView(view);
		initialize(context);
		FirebaseApp.initializeApp(context);
		initializeLogic();
		return fragmentView;
	}

	private void initialize(@NonNull Context context) {
		topbar = findViewById(R.id.topbar);
		main = findViewById(R.id.main);
		yourphone = findViewById(R.id.yourphone);
		space_bar = findViewById(R.id.space_bar);
		done = findViewById(R.id.done);
		part1 = findViewById(R.id.part1);
		part2 = findViewById(R.id.part2);
		space_top = findViewById(R.id.space_top);
		country_holder = findViewById(R.id.country_holder);
		space_bottom = findViewById(R.id.space_bottom);
		holder = findViewById(R.id.holder);
		information = findViewById(R.id.information);
		country_text = findViewById(R.id.country_text);
		divider_country = findViewById(R.id.divider_country);
		plus = findViewById(R.id.plus);
		dial_holder = findViewById(R.id.dial_holder);
		number_holder = findViewById(R.id.number_holder);
		dial_code = findViewById(R.id.dial_code);
		divider_dialcode = findViewById(R.id.divider_dialcode);
		number_edittext = findViewById(R.id.number_edittext);
		divider_number = findViewById(R.id.divider_number);
		sms = findViewById(R.id.sms);
		enter_code_txt = findViewById(R.id.enter_code_txt);
		notice = findViewById(R.id.notice);
		user_phone = findViewById(R.id.user_phone);
		otp_holder = findViewById(R.id.otp_holder);
		didnt_get = findViewById(R.id.didnt_get);
		first = findViewById(R.id.first);
		second = findViewById(R.id.second);
		third = findViewById(R.id.third);
		forth = findViewById(R.id.forth);
		fiver = findViewById(R.id.fiver);
		sixer = findViewById(R.id.sixer);
		first_edittext = findViewById(R.id.first_edittext);
		first_divider = findViewById(R.id.first_divider);
		second_edittext = findViewById(R.id.second_edittext);
		second_divider = findViewById(R.id.second_divider);
		third_edittext = findViewById(R.id.third_edittext);
		third_divider = findViewById(R.id.third_divider);
		forth_edittext = findViewById(R.id.forth_edittext);
		forth_divider = findViewById(R.id.forth_divider);
		fiver_edittext = findViewById(R.id.fiver_edittext);
		fiver_divider = findViewById(R.id.fiver_divider);
		sixer_edittext = findViewById(R.id.sixer_edittext);
		sixer_divider = findViewById(R.id.sixer_divider);
		Fauth = FirebaseAuth.getInstance();
		firebaseAuthSettings = Fauth.getFirebaseAuthSettings();
		sp_df = getParentActivity().getSharedPreferences("sp_df", Activity.MODE_PRIVATE);
		dial_calling = new RequestNetwork(getParentActivity());
		sp_mydt = getParentActivity().getSharedPreferences("sp_mydt", Activity.MODE_PRIVATE);

		done.setOnClickListener(_view -> {
			phoneNumber = plus.getText().toString().concat(dial_code.getText().toString().concat(number_edittext.getText().toString()));
			PhoneAuthOptions options = PhoneAuthOptions.newBuilder(Fauth)
					.setPhoneNumber(phoneNumber)
					.setTimeout(60L, TimeUnit.SECONDS)
					.setActivity(getParentActivity())
					.setCallbacks(Pauth)
					.build();
			PhoneAuthProvider.verifyPhoneNumber(options);
		});

		country_text.setOnClickListener(_view -> {
			divider_country.setBackgroundColor(0xFF193566);
			divider_number.setBackgroundColor(0xFFDADADA);
			divider_dialcode.setBackgroundColor(0xFFDADADA);
			presentFragment(new CountrycodeActivity());
		});

		dial_code.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (plus.getText().toString().concat(_charSeq).equals("")) {
					country_text.setText("Invalid Country Code");
				} else {
					if (dialing_code.contains(plus.getText().toString().concat(_charSeq))) {
						country_text.setText(countries.get((int) dialing_code.indexOf(plus.getText().toString().concat(_charSeq))).get("name").toString());
						number_edittext.requestFocus();
						divider_number.setBackgroundColor(0xFF193566);
						divider_country.setBackgroundColor(0xFFDADADA);
						divider_dialcode.setBackgroundColor(0xFFDADADA);
					} else {
						country_text.setText("Invalid Country Code");
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {

			}

			@Override
			public void afterTextChanged(Editable _param1) {

			}
		});

		number_edittext.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (_charSeq.length() > 6) {
					done.setVisibility(View.VISIBLE);
				} else {
					done.setVisibility(View.INVISIBLE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {

			}

			@Override
			public void afterTextChanged(Editable _param1) {

			}
		});

		didnt_get.setOnClickListener(_view -> {
			AndroidUtilities.showToast("Resending Verification Code...");
			String phoneNumber = plus.getText().toString().concat(dial_code.getText().toString().concat(number_edittext.getText().toString()));
			PhoneAuthOptions options = PhoneAuthOptions.newBuilder(Fauth)
					.setPhoneNumber(phoneNumber)
					.setTimeout(60L, TimeUnit.SECONDS)
					.setActivity(getParentActivity())
					.setCallbacks(Pauth)
					.setForceResendingToken(Pauth_resendToken)
					.build();
			PhoneAuthProvider.verifyPhoneNumber(options);
		});

		first_edittext.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (_charSeq.length() > 0) {
					second_edittext.requestFocus();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {

			}

			@Override
			public void afterTextChanged(Editable _param1) {

			}
		});

		second_edittext.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (_charSeq.length() > 0) {
					third_edittext.requestFocus();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {

			}

			@Override
			public void afterTextChanged(Editable _param1) {

			}
		});

		third_edittext.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (_charSeq.length() > 0) {
					forth_edittext.requestFocus();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {

			}

			@Override
			public void afterTextChanged(Editable _param1) {

			}
		});

		forth_edittext.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (_charSeq.length() > 0) {
					fiver_edittext.requestFocus();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {

			}

			@Override
			public void afterTextChanged(Editable _param1) {

			}
		});

		fiver_edittext.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (_charSeq.length() > 0) {
					sixer_edittext.requestFocus();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {

			}

			@Override
			public void afterTextChanged(Editable _param1) {

			}
		});

		sixer_edittext.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (_charSeq.length() > 0) {
					FirebaseAuth.getInstance().signInWithCredential(PhoneAuthProvider.getCredential(verification_id, first_edittext.getText().toString().concat(second_edittext.getText().toString().concat(third_edittext.getText().toString().concat(forth_edittext.getText().toString().concat(fiver_edittext.getText().toString().concat(sixer_edittext.getText().toString()))))))).addOnCompleteListener(Fauth_phoneAuthListener);
					SketchwareUtil.hideKeyboard(getApplicationContext());
				}
			}

			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {

			}

			@Override
			public void afterTextChanged(Editable _param1) {

			}
		});

		Pauth = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
			@Override
			public void onVerificationCompleted(@NonNull PhoneAuthCredential _credential) {

			}

			@Override
			public void onVerificationFailed(@NonNull FirebaseException e) {
				String _exception = e.getMessage();
				MaterialAlertDialogBuilder alertDialog = new MaterialAlertDialogBuilder(getParentActivity());
				alertDialog.setTitle("Verification Failed");
				alertDialog.setMessage(_exception.toString());
				alertDialog.setPositiveButton("RETRY", (dialog, which) -> {

				});
				alertDialog.create().show();
			}

			@Override
			public void onCodeSent(@NonNull String _verificationId, @NonNull PhoneAuthProvider.ForceResendingToken _token) {
				SketchwareUtil.hideKeyboard(getApplicationContext());
				anim.setTarget(part1);
				anim.setPropertyName("alpha");
				anim.setFloatValues((float) (0));
				anim.setDuration((int) (400));
				anim.start();
				toolbar_txt.setTarget(yourphone);
				toolbar_txt.setPropertyName("alpha");
				toolbar_txt.setFloatValues((float) (0));
				toolbar_txt.setDuration((int) (400));
				toolbar_txt.start();
				timer = new TimerTask() {
					@Override
					public void run() {
						AndroidUtilities.runOnUIThread(() -> {
							part1.setVisibility(View.GONE);
							done.setVisibility(View.INVISIBLE);
							part2.setAlpha((float) (0));
							part2.setVisibility(View.VISIBLE);
							yourphone.setText("Phone verification");
							extra.setTarget(part2);
							extra.setPropertyName("alpha");
							extra.setFloatValues((float) (1));
							extra.setDuration((int) (400));
							extra.start();
							extra2.setTarget(yourphone);
							extra2.setPropertyName("alpha");
							extra2.setFloatValues((float) (1));
							extra2.setDuration((int) (400));
							extra2.start();
						});
					}
				};
				_timer.schedule(timer, (int) (200));
				verification_id = _verificationId;
				Verification = true;
				user_phone.setText(phoneNumber);
			}
		};

		_dial_calling_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				dial_code.setText(_param2.substring((int) (1), (int) (_param2.length())));
			}

			@Override
			public void onErrorResponse(String _param1, String _param2) {

			}
		};

		_taked_username_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(@NonNull DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
				};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				SketchwareUtil.getAllKeysFromMap(_childValue, taked_usernames);
			}

			@Override
			public void onChildChanged(@NonNull DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
				};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);

			}

			@Override
			public void onChildMoved(@NonNull DataSnapshot _param1, String _param2) {

			}

			@Override
			public void onChildRemoved(@NonNull DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
				};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);

			}

			@Override
			public void onCancelled(@NonNull DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();

			}
		};
		taked_username.addChildEventListener(_taked_username_child_listener);

		_users_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(@NonNull DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
				};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				taked_usernames.add(_childKey);
			}

			@Override
			public void onChildChanged(@NonNull DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
				};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);

			}

			@Override
			public void onChildMoved(@NonNull DataSnapshot _param1, String _param2) {

			}

			@Override
			public void onChildRemoved(@NonNull DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
				};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);

			}

			@Override
			public void onCancelled(@NonNull DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();

			}
		};
		users.addChildEventListener(_users_child_listener);

		_userchats_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(@NonNull DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
				};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);

			}

			@Override
			public void onChildChanged(@NonNull DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
				};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);

			}

			@Override
			public void onChildMoved(@NonNull DataSnapshot _param1, String _param2) {

			}

			@Override
			public void onChildRemoved(@NonNull DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
				};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);

			}

			@Override
			public void onCancelled(@NonNull DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();

			}
		};
		userchats.addChildEventListener(_userchats_child_listener);

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
			String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
			if (_success) {
				FirebaseUser user = Fauth.getCurrentUser();
				boolean isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();
				if (isNewUser) {
					while (true) {
						username = FirebaseAuth.getInstance().getCurrentUser().getUid().substring((int) (0), (int) (4)).concat(String.valueOf((long) (SketchwareUtil.getRandom((int) (1000), (int) (9999)))));
						if (!taked_usernames.contains(username)) {
							Map = new HashMap<>();
							Map.put("username", username);
							Map.put("country", country_text.getText().toString());
							Map.put("phone_status", "none");
							Map.put("phone", phoneNumber);
							Log.d(OtpActivity.class.getSimpleName(), "phone is :" + phone.toString());
							Map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
							users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(Map);
							Map.put("username", username);
							Map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
							taked_username.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(Map);
							Map.clear();
							sp_mydt.edit().putString("uid", FirebaseAuth.getInstance().getCurrentUser().getUid()).apply();
							sp_mydt.edit().putString("phone", phoneNumber).apply();
							sp_mydt.edit().putString("username", username).apply();
							sp_mydt.edit().putString("country", country_text.getText().toString()).apply();
							Bundle bundle = new Bundle();
							bundle.putString("taked_photo", ".");
							presentFragment(new SetupActivity(bundle));
							break;
						}
					}
				} else {
					presentFragment(new LaunchActivity());
				}
			} else {
				MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(getParentActivity());
				dialogBuilder.setTitle("Verification Failed");
				dialogBuilder.setMessage(_errorMessage.toString());
				dialogBuilder.setPositiveButton("RETRY", (dialog12, which) -> {

				});
				dialogBuilder.create().show();
			}
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
		if (getArguments().containsKey("Country") && getArguments().containsKey("Code")) {
			if (getArguments().getString("Country").equals(".") && getArguments().getString("Code").equals(".")) {
				dial_calling.startRequestNetwork(RequestNetworkController.GET, "https://ipapi.co/country_calling_code", "dial", _dial_calling_request_listener);
			} else {
				country_text.setText(getArguments().getString("Country"));
				dial_code.setText(getArguments().getString("Code"));
			}
		}
		FirebaseAuth.getInstance().getFirebaseAuthSettings().forceRecaptchaFlowForTesting(true);
		SketchwareUtil.hideKeyboard(getApplicationContext());
		part2.setVisibility(View.GONE);
		done.setVisibility(View.INVISIBLE);
		done.setColorFilter(0xFF193566, PorterDuff.Mode.MULTIPLY);
		sms.setColorFilter(0xFF193566, PorterDuff.Mode.MULTIPLY);
		topbar.setElevation((int) 2);
		Verification = false;
		countries = new Gson().fromJson("[\n{\n\"name\": \"Afghanistan\",\n\"dial_code\": \"+93\",\n\"code\": \"AF\"\n},\n{\n\"name\": \"Aland Islands\",\n\"dial_code\": \"+358\",\n\"code\": \"AX\"\n},\n{\n\"name\": \"Albania\",\n\"dial_code\": \"+355\",\n\"code\": \"AL\"\n},\n{\n\"name\": \"Algeria\",\n\"dial_code\": \"+213\",\n\"code\": \"DZ\"\n},\n{\n\"name\": \"AmericanSamoa\",\n\"dial_code\": \"+1684\",\n\"code\": \"AS\"\n},\n{\n\"name\": \"Andorra\",\n\"dial_code\": \"+376\",\n\"code\": \"AD\"\n},\n{\n\"name\": \"Angola\",\n\"dial_code\": \"+244\",\n\"code\": \"AO\"\n},\n{\n\"name\": \"Anguilla\",\n\"dial_code\": \"+1264\",\n\"code\": \"AI\"\n},\n{\n\"name\": \"Antarctica\",\n\"dial_code\": \"+672\",\n\"code\": \"AQ\"\n},\n{\n\"name\": \"Antigua and Barbuda\",\n\"dial_code\": \"+1268\",\n\"code\": \"AG\"\n},\n{\n\"name\": \"Argentina\",\n\"dial_code\": \"+54\",\n\"code\": \"AR\"\n},\n{\n\"name\": \"Armenia\",\n\"dial_code\": \"+374\",\n\"code\": \"AM\"\n},\n{\n\"name\": \"Aruba\",\n\"dial_code\": \"+297\",\n\"code\": \"AW\"\n},\n{\n\"name\": \"Australia\",\n\"dial_code\": \"+61\",\n\"code\": \"AU\"\n},\n{\n\"name\": \"Austria\",\n\"dial_code\": \"+43\",\n\"code\": \"AT\"\n},\n{\n\"name\": \"Azerbaijan\",\n\"dial_code\": \"+994\",\n\"code\": \"AZ\"\n},\n{\n\"name\": \"Bahamas\",\n\"dial_code\": \"+1242\",\n\"code\": \"BS\"\n},\n{\n\"name\": \"Bahrain\",\n\"dial_code\": \"+973\",\n\"code\": \"BH\"\n},\n{\n\"name\": \"Bangladesh\",\n\"dial_code\": \"+880\",\n\"code\": \"BD\"\n},\n{\n\"name\": \"Barbados\",\n\"dial_code\": \"+1246\",\n\"code\": \"BB\"\n},\n{\n\"name\": \"Belarus\",\n\"dial_code\": \"+375\",\n\"code\": \"BY\"\n},\n{\n\"name\": \"Belgium\",\n\"dial_code\": \"+32\",\n\"code\": \"BE\"\n},\n{\n\"name\": \"Belize\",\n\"dial_code\": \"+501\",\n\"code\": \"BZ\"\n},\n{\n\"name\": \"Benin\",\n\"dial_code\": \"+229\",\n\"code\": \"BJ\"\n},\n{\n\"name\": \"Bermuda\",\n\"dial_code\": \"+1441\",\n\"code\": \"BM\"\n},\n{\n\"name\": \"Bhutan\",\n\"dial_code\": \"+975\",\n\"code\": \"BT\"\n},\n{\n\"name\": \"Bolivia, Plurinational State of\",\n\"dial_code\": \"+591\",\n\"code\": \"BO\"\n},\n{\n\"name\": \"Bosnia and Herzegovina\",\n\"dial_code\": \"+387\",\n\"code\": \"BA\"\n},\n{\n\"name\": \"Botswana\",\n\"dial_code\": \"+267\",\n\"code\": \"BW\"\n},\n{\n\"name\": \"Brazil\",\n\"dial_code\": \"+55\",\n\"code\": \"BR\"\n},\n{\n\"name\": \"British Indian Ocean Territory\",\n\"dial_code\": \"+246\",\n\"code\": \"IO\"\n},\n{\n\"name\": \"Brunei Darussalam\",\n\"dial_code\": \"+673\",\n\"code\": \"BN\"\n},\n{\n\"name\": \"Bulgaria\",\n\"dial_code\": \"+359\",\n\"code\": \"BG\"\n},\n{\n\"name\": \"Burkina Faso\",\n\"dial_code\": \"+226\",\n\"code\": \"BF\"\n},\n{\n\"name\": \"Burundi\",\n\"dial_code\": \"+257\",\n\"code\": \"BI\"\n},\n{\n\"name\": \"Cambodia\",\n\"dial_code\": \"+855\",\n\"code\": \"KH\"\n},\n{\n\"name\": \"Cameroon\",\n\"dial_code\": \"+237\",\n\"code\": \"CM\"\n},\n{\n\"name\": \"Canada\",\n\"dial_code\": \"+1\",\n\"code\": \"CA\"\n},\n{\n\"name\": \"Cape Verde\",\n\"dial_code\": \"+238\",\n\"code\": \"CV\"\n},\n{\n\"name\": \"Cayman Islands\",\n\"dial_code\": \"+ 345\",\n\"code\": \"KY\"\n},\n{\n\"name\": \"Central African Republic\",\n\"dial_code\": \"+236\",\n\"code\": \"CF\"\n},\n{\n\"name\": \"Chad\",\n\"dial_code\": \"+235\",\n\"code\": \"TD\"\n},\n{\n\"name\": \"Chile\",\n\"dial_code\": \"+56\",\n\"code\": \"CL\"\n},\n{\n\"name\": \"China\",\n\"dial_code\": \"+86\",\n\"code\": \"CN\"\n},\n{\n\"name\": \"Christmas Island\",\n\"dial_code\": \"+61\",\n\"code\": \"CX\"\n},\n{\n\"name\": \"Cocos (Keeling) Islands\",\n\"dial_code\": \"+61\",\n\"code\": \"CC\"\n},\n{\n\"name\": \"Colombia\",\n\"dial_code\": \"+57\",\n\"code\": \"CO\"\n},\n{\n\"name\": \"Comoros\",\n\"dial_code\": \"+269\",\n\"code\": \"KM\"\n},\n{\n\"name\": \"Congo\",\n\"dial_code\": \"+242\",\n\"code\": \"CG\"\n},\n{\n\"name\": \"Congo, The Democratic Republic of the Congo\",\n\"dial_code\": \"+243\",\n\"code\": \"CD\"\n},\n{\n\"name\": \"Cook Islands\",\n\"dial_code\": \"+682\",\n\"code\": \"CK\"\n},\n{\n\"name\": \"Costa Rica\",\n\"dial_code\": \"+506\",\n\"code\": \"CR\"\n},\n{\n\"name\": \"Cote d'Ivoire\",\n\"dial_code\": \"+225\",\n\"code\": \"CI\"\n},\n{\n\"name\": \"Croatia\",\n\"dial_code\": \"+385\",\n\"code\": \"HR\"\n},\n{\n\"name\": \"Cuba\",\n\"dial_code\": \"+53\",\n\"code\": \"CU\"\n},\n{\n\"name\": \"Cyprus\",\n\"dial_code\": \"+357\",\n\"code\": \"CY\"\n},\n{\n\"name\": \"Czech Republic\",\n\"dial_code\": \"+420\",\n\"code\": \"CZ\"\n},\n{\n\"name\": \"Denmark\",\n\"dial_code\": \"+45\",\n\"code\": \"DK\"\n},\n{\n\"name\": \"Djibouti\",\n\"dial_code\": \"+253\",\n\"code\": \"DJ\"\n},\n{\n\"name\": \"Dominica\",\n\"dial_code\": \"+1767\",\n\"code\": \"DM\"\n},\n{\n\"name\": \"Dominican Republic\",\n\"dial_code\": \"+1849\",\n\"code\": \"DO\"\n},\n{\n\"name\": \"Ecuador\",\n\"dial_code\": \"+593\",\n\"code\": \"EC\"\n},\n{\n\"name\": \"Egypt\",\n\"dial_code\": \"+20\",\n\"code\": \"EG\"\n},\n{\n\"name\": \"El Salvador\",\n\"dial_code\": \"+503\",\n\"code\": \"SV\"\n},\n{\n\"name\": \"Equatorial Guinea\",\n\"dial_code\": \"+240\",\n\"code\": \"GQ\"\n},\n{\n\"name\": \"Eritrea\",\n\"dial_code\": \"+291\",\n\"code\": \"ER\"\n},\n{\n\"name\": \"Estonia\",\n\"dial_code\": \"+372\",\n\"code\": \"EE\"\n},\n{\n\"name\": \"Ethiopia\",\n\"dial_code\": \"+251\",\n\"code\": \"ET\"\n},\n{\n\"name\": \"Falkland Islands (Malvinas)\",\n\"dial_code\": \"+500\",\n\"code\": \"FK\"\n},\n{\n\"name\": \"Faroe Islands\",\n\"dial_code\": \"+298\",\n\"code\": \"FO\"\n},\n{\n\"name\": \"Fiji\",\n\"dial_code\": \"+679\",\n\"code\": \"FJ\"\n},\n{\n\"name\": \"Finland\",\n\"dial_code\": \"+358\",\n\"code\": \"FI\"\n},\n{\n\"name\": \"France\",\n\"dial_code\": \"+33\",\n\"code\": \"FR\"\n},\n{\n\"name\": \"French Guiana\",\n\"dial_code\": \"+594\",\n\"code\": \"GF\"\n},\n{\n\"name\": \"French Polynesia\",\n\"dial_code\": \"+689\",\n\"code\": \"PF\"\n},\n{\n\"name\": \"Gabon\",\n\"dial_code\": \"+241\",\n\"code\": \"GA\"\n},\n{\n\"name\": \"Gambia\",\n\"dial_code\": \"+220\",\n\"code\": \"GM\"\n},\n{\n\"name\": \"Georgia\",\n\"dial_code\": \"+995\",\n\"code\": \"GE\"\n},\n{\n\"name\": \"Germany\",\n\"dial_code\": \"+49\",\n\"code\": \"DE\"\n},\n{\n\"name\": \"Ghana\",\n\"dial_code\": \"+233\",\n\"code\": \"GH\"\n},\n{\n\"name\": \"Gibraltar\",\n\"dial_code\": \"+350\",\n\"code\": \"GI\"\n},\n{\n\"name\": \"Greece\",\n\"dial_code\": \"+30\",\n\"code\": \"GR\"\n},\n{\n\"name\": \"Greenland\",\n\"dial_code\": \"+299\",\n\"code\": \"GL\"\n},\n{\n\"name\": \"Grenada\",\n\"dial_code\": \"+1473\",\n\"code\": \"GD\"\n},\n{\n\"name\": \"Guadeloupe\",\n\"dial_code\": \"+590\",\n\"code\": \"GP\"\n},\n{\n\"name\": \"Guam\",\n\"dial_code\": \"+1671\",\n\"code\": \"GU\"\n},\n{\n\"name\": \"Guatemala\",\n\"dial_code\": \"+502\",\n\"code\": \"GT\"\n},\n{\n\"name\": \"Guernsey\",\n\"dial_code\": \"+44\",\n\"code\": \"GG\"\n},\n{\n\"name\": \"Guinea\",\n\"dial_code\": \"+224\",\n\"code\": \"GN\"\n},\n{\n\"name\": \"Guinea-Bissau\",\n\"dial_code\": \"+245\",\n\"code\": \"GW\"\n},\n{\n\"name\": \"Guyana\",\n\"dial_code\": \"+595\",\n\"code\": \"GY\"\n},\n{\n\"name\": \"Haiti\",\n\"dial_code\": \"+509\",\n\"code\": \"HT\"\n},\n{\n\"name\": \"Holy See (Vatican City State)\",\n\"dial_code\": \"+379\",\n\"code\": \"VA\"\n},\n{\n\"name\": \"Honduras\",\n\"dial_code\": \"+504\",\n\"code\": \"HN\"\n},\n{\n\"name\": \"Hong Kong\",\n\"dial_code\": \"+852\",\n\"code\": \"HK\"\n},\n{\n\"name\": \"Hungary\",\n\"dial_code\": \"+36\",\n\"code\": \"HU\"\n},\n{\n\"name\": \"Iceland\",\n\"dial_code\": \"+354\",\n\"code\": \"IS\"\n},\n{\n\"name\": \"India\",\n\"dial_code\": \"+91\",\n\"code\": \"IN\"\n},\n{\n\"name\": \"Indonesia\",\n\"dial_code\": \"+62\",\n\"code\": \"ID\"\n},\n{\n\"name\": \"Iran, Islamic Republic of Persian Gulf\",\n\"dial_code\": \"+98\",\n\"code\": \"IR\"\n},\n{\n\"name\": \"Iraq\",\n\"dial_code\": \"+964\",\n\"code\": \"IQ\"\n},\n{\n\"name\": \"Ireland\",\n\"dial_code\": \"+353\",\n\"code\": \"IE\"\n},\n{\n\"name\": \"Isle of Man\",\n\"dial_code\": \"+44\",\n\"code\": \"IM\"\n},\n{\n\"name\": \"Israel\",\n\"dial_code\": \"+972\",\n\"code\": \"IL\"\n},\n{\n\"name\": \"Italy\",\n\"dial_code\": \"+39\",\n\"code\": \"IT\"\n},\n{\n\"name\": \"Jamaica\",\n\"dial_code\": \"+1876\",\n\"code\": \"JM\"\n},\n{\n\"name\": \"Japan\",\n\"dial_code\": \"+81\",\n\"code\": \"JP\"\n},\n{\n\"name\": \"Jersey\",\n\"dial_code\": \"+44\",\n\"code\": \"JE\"\n},\n{\n\"name\": \"Jordan\",\n\"dial_code\": \"+962\",\n\"code\": \"JO\"\n},\n{\n\"name\": \"Kazakhstan\",\n\"dial_code\": \"+77\",\n\"code\": \"KZ\"\n},\n{\n\"name\": \"Kenya\",\n\"dial_code\": \"+254\",\n\"code\": \"KE\"\n},\n{\n\"name\": \"Kiribati\",\n\"dial_code\": \"+686\",\n\"code\": \"KI\"\n},\n{\n\"name\": \"Korea, Democratic People's Republic of Korea\",\n\"dial_code\": \"+850\",\n\"code\": \"KP\"\n},\n{\n\"name\": \"Korea, Republic of South Korea\",\n\"dial_code\": \"+82\",\n\"code\": \"KR\"\n},\n{\n\"name\": \"Kuwait\",\n\"dial_code\": \"+965\",\n\"code\": \"KW\"\n},\n{\n\"name\": \"Kyrgyzstan\",\n\"dial_code\": \"+996\",\n\"code\": \"KG\"\n},\n{\n\"name\": \"Laos\",\n\"dial_code\": \"+856\",\n\"code\": \"LA\"\n},\n{\n\"name\": \"Latvia\",\n\"dial_code\": \"+371\",\n\"code\": \"LV\"\n},\n{\n\"name\": \"Lebanon\",\n\"dial_code\": \"+961\",\n\"code\": \"LB\"\n},\n{\n\"name\": \"Lesotho\",\n\"dial_code\": \"+266\",\n\"code\": \"LS\"\n},\n{\n\"name\": \"Liberia\",\n\"dial_code\": \"+231\",\n\"code\": \"LR\"\n},\n{\n\"name\": \"Libyan Arab Jamahiriya\",\n\"dial_code\": \"+218\",\n\"code\": \"LY\"\n},\n{\n\"name\": \"Liechtenstein\",\n\"dial_code\": \"+423\",\n\"code\": \"LI\"\n},\n{\n\"name\": \"Lithuania\",\n\"dial_code\": \"+370\",\n\"code\": \"LT\"\n},\n{\n\"name\": \"Luxembourg\",\n\"dial_code\": \"+352\",\n\"code\": \"LU\"\n},\n{\n\"name\": \"Macao\",\n\"dial_code\": \"+853\",\n\"code\": \"MO\"\n},\n{\n\"name\": \"Macedonia\",\n\"dial_code\": \"+389\",\n\"code\": \"MK\"\n},\n{\n\"name\": \"Madagascar\",\n\"dial_code\": \"+261\",\n\"code\": \"MG\"\n},\n{\n\"name\": \"Malawi\",\n\"dial_code\": \"+265\",\n\"code\": \"MW\"\n},\n{\n\"name\": \"Malaysia\",\n\"dial_code\": \"+60\",\n\"code\": \"MY\"\n},\n{\n\"name\": \"Maldives\",\n\"dial_code\": \"+960\",\n\"code\": \"MV\"\n},\n{\n\"name\": \"Mali\",\n\"dial_code\": \"+223\",\n\"code\": \"ML\"\n},\n{\n\"name\": \"Malta\",\n\"dial_code\": \"+356\",\n\"code\": \"MT\"\n},\n{\n\"name\": \"Marshall Islands\",\n\"dial_code\": \"+692\",\n\"code\": \"MH\"\n},\n{\n\"name\": \"Martinique\",\n\"dial_code\": \"+596\",\n\"code\": \"MQ\"\n},\n{\n\"name\": \"Mauritania\",\n\"dial_code\": \"+222\",\n\"code\": \"MR\"\n},\n{\n\"name\": \"Mauritius\",\n\"dial_code\": \"+230\",\n\"code\": \"MU\"\n},\n{\n\"name\": \"Mayotte\",\n\"dial_code\": \"+262\",\n\"code\": \"YT\"\n},\n{\n\"name\": \"Mexico\",\n\"dial_code\": \"+52\",\n\"code\": \"MX\"\n},\n{\n\"name\": \"Micronesia, Federated States of Micronesia\",\n\"dial_code\": \"+691\",\n\"code\": \"FM\"\n},\n{\n\"name\": \"Moldova\",\n\"dial_code\": \"+373\",\n\"code\": \"MD\"\n},\n{\n\"name\": \"Monaco\",\n\"dial_code\": \"+377\",\n\"code\": \"MC\"\n},\n{\n\"name\": \"Mongolia\",\n\"dial_code\": \"+976\",\n\"code\": \"MN\"\n},\n{\n\"name\": \"Montenegro\",\n\"dial_code\": \"+382\",\n\"code\": \"ME\"\n},\n{\n\"name\": \"Montserrat\",\n\"dial_code\": \"+1664\",\n\"code\": \"MS\"\n},\n{\n\"name\": \"Morocco\",\n\"dial_code\": \"+212\",\n\"code\": \"MA\"\n},\n{\n\"name\": \"Mozambique\",\n\"dial_code\": \"+258\",\n\"code\": \"MZ\"\n},\n{\n\"name\": \"Myanmar\",\n\"dial_code\": \"+95\",\n\"code\": \"MM\"\n},\n{\n\"name\": \"Namibia\",\n\"dial_code\": \"+264\",\n\"code\": \"NA\"\n},\n{\n\"name\": \"Nauru\",\n\"dial_code\": \"+674\",\n\"code\": \"NR\"\n},\n{\n\"name\": \"Nepal\",\n\"dial_code\": \"+977\",\n\"code\": \"NP\"\n},\n{\n\"name\": \"Netherlands\",\n\"dial_code\": \"+31\",\n\"code\": \"NL\"\n},\n{\n\"name\": \"Netherlands Antilles\",\n\"dial_code\": \"+599\",\n\"code\": \"AN\"\n},\n{\n\"name\": \"New Caledonia\",\n\"dial_code\": \"+687\",\n\"code\": \"NC\"\n},\n{\n\"name\": \"New Zealand\",\n\"dial_code\": \"+64\",\n\"code\": \"NZ\"\n},\n{\n\"name\": \"Nicaragua\",\n\"dial_code\": \"+505\",\n\"code\": \"NI\"\n},\n{\n\"name\": \"Niger\",\n\"dial_code\": \"+227\",\n\"code\": \"NE\"\n},\n{\n\"name\": \"Nigeria\",\n\"dial_code\": \"+234\",\n\"code\": \"NG\"\n},\n{\n\"name\": \"Niue\",\n\"dial_code\": \"+683\",\n\"code\": \"NU\"\n},\n{\n\"name\": \"Norfolk Island\",\n\"dial_code\": \"+672\",\n\"code\": \"NF\"\n},\n{\n\"name\": \"Northern Mariana Islands\",\n\"dial_code\": \"+1670\",\n\"code\": \"MP\"\n},\n{\n\"name\": \"Norway\",\n\"dial_code\": \"+47\",\n\"code\": \"NO\"\n},\n{\n\"name\": \"Oman\",\n\"dial_code\": \"+968\",\n\"code\": \"OM\"\n},\n{\n\"name\": \"Pakistan\",\n\"dial_code\": \"+92\",\n\"code\": \"PK\"\n},\n{\n\"name\": \"Palau\",\n\"dial_code\": \"+680\",\n\"code\": \"PW\"\n},\n{\n\"name\": \"Palestinian Territory, Occupied\",\n\"dial_code\": \"+970\",\n\"code\": \"PS\"\n},\n{\n\"name\": \"Panama\",\n\"dial_code\": \"+507\",\n\"code\": \"PA\"\n},\n{\n\"name\": \"Papua New Guinea\",\n\"dial_code\": \"+675\",\n\"code\": \"PG\"\n},\n{\n\"name\": \"Paraguay\",\n\"dial_code\": \"+595\",\n\"code\": \"PY\"\n},\n{\n\"name\": \"Peru\",\n\"dial_code\": \"+51\",\n\"code\": \"PE\"\n},\n{\n\"name\": \"Philippines\",\n\"dial_code\": \"+63\",\n\"code\": \"PH\"\n},\n{\n\"name\": \"Pitcairn\",\n\"dial_code\": \"+872\",\n\"code\": \"PN\"\n},\n{\n\"name\": \"Poland\",\n\"dial_code\": \"+48\",\n\"code\": \"PL\"\n},\n{\n\"name\": \"Portugal\",\n\"dial_code\": \"+351\",\n\"code\": \"PT\"\n},\n{\n\"name\": \"Puerto Rico\",\n\"dial_code\": \"+1939\",\n\"code\": \"PR\"\n},\n{\n\"name\": \"Qatar\",\n\"dial_code\": \"+974\",\n\"code\": \"QA\"\n},\n{\n\"name\": \"Romania\",\n\"dial_code\": \"+40\",\n\"code\": \"RO\"\n},\n{\n\"name\": \"Russia\",\n\"dial_code\": \"+7\",\n\"code\": \"RU\"\n},\n{\n\"name\": \"Rwanda\",\n\"dial_code\": \"+250\",\n\"code\": \"RW\"\n},\n{\n\"name\": \"Reunion\",\n\"dial_code\": \"+262\",\n\"code\": \"RE\"\n},\n{\n\"name\": \"Saint Barthelemy\",\n\"dial_code\": \"+590\",\n\"code\": \"BL\"\n},\n{\n\"name\": \"Saint Helena, Ascension and Tristan Da Cunha\",\n\"dial_code\": \"+290\",\n\"code\": \"SH\"\n},\n{\n\"name\": \"Saint Kitts and Nevis\",\n\"dial_code\": \"+1869\",\n\"code\": \"KN\"\n},\n{\n\"name\": \"Saint Lucia\",\n\"dial_code\": \"+1758\",\n\"code\": \"LC\"\n},\n{\n\"name\": \"Saint Martin\",\n\"dial_code\": \"+590\",\n\"code\": \"MF\"\n},\n{\n\"name\": \"Saint Pierre and Miquelon\",\n\"dial_code\": \"+508\",\n\"code\": \"PM\"\n},\n{\n\"name\": \"Saint Vincent and the Grenadines\",\n\"dial_code\": \"+1784\",\n\"code\": \"VC\"\n},\n{\n\"name\": \"Samoa\",\n\"dial_code\": \"+685\",\n\"code\": \"WS\"\n},\n{\n\"name\": \"San Marino\",\n\"dial_code\": \"+378\",\n\"code\": \"SM\"\n},\n{\n\"name\": \"Sao Tome and Principe\",\n\"dial_code\": \"+239\",\n\"code\": \"ST\"\n},\n{\n\"name\": \"Saudi Arabia\",\n\"dial_code\": \"+966\",\n\"code\": \"SA\"\n},\n{\n\"name\": \"Senegal\",\n\"dial_code\": \"+221\",\n\"code\": \"SN\"\n},\n{\n\"name\": \"Serbia\",\n\"dial_code\": \"+381\",\n\"code\": \"RS\"\n},\n{\n\"name\": \"Seychelles\",\n\"dial_code\": \"+248\",\n\"code\": \"SC\"\n},\n{\n\"name\": \"Sierra Leone\",\n\"dial_code\": \"+232\",\n\"code\": \"SL\"\n},\n{\n\"name\": \"Singapore\",\n\"dial_code\": \"+65\",\n\"code\": \"SG\"\n},\n{\n\"name\": \"Slovakia\",\n\"dial_code\": \"+421\",\n\"code\": \"SK\"\n},\n{\n\"name\": \"Slovenia\",\n\"dial_code\": \"+386\",\n\"code\": \"SI\"\n},\n{\n\"name\": \"Solomon Islands\",\n\"dial_code\": \"+677\",\n\"code\": \"SB\"\n},\n{\n\"name\": \"Somalia\",\n\"dial_code\": \"+252\",\n\"code\": \"SO\"\n},\n{\n\"name\": \"South Africa\",\n\"dial_code\": \"+27\",\n\"code\": \"ZA\"\n},\n{\n\"name\": \"South Sudan\",\n\"dial_code\": \"+211\",\n\"code\": \"SS\"\n},\n{\n\"name\": \"South Georgia and the South Sandwich Islands\",\n\"dial_code\": \"+500\",\n\"code\": \"GS\"\n},\n{\n\"name\": \"Spain\",\n\"dial_code\": \"+34\",\n\"code\": \"ES\"\n},\n{\n\"name\": \"Sri Lanka\",\n\"dial_code\": \"+94\",\n\"code\": \"LK\"\n},\n{\n\"name\": \"Sudan\",\n\"dial_code\": \"+249\",\n\"code\": \"SD\"\n},\n{\n\"name\": \"Suriname\",\n\"dial_code\": \"+597\",\n\"code\": \"SR\"\n},\n{\n\"name\": \"Svalbard and Jan Mayen\",\n\"dial_code\": \"+47\",\n\"code\": \"SJ\"\n},\n{\n\"name\": \"Swaziland\",\n\"dial_code\": \"+268\",\n\"code\": \"SZ\"\n},\n{\n\"name\": \"Sweden\",\n\"dial_code\": \"+46\",\n\"code\": \"SE\"\n},\n{\n\"name\": \"Switzerland\",\n\"dial_code\": \"+41\",\n\"code\": \"CH\"\n},\n{\n\"name\": \"Syrian Arab Republic\",\n\"dial_code\": \"+963\",\n\"code\": \"SY\"\n},\n{\n\"name\": \"Taiwan\",\n\"dial_code\": \"+886\",\n\"code\": \"TW\"\n},\n{\n\"name\": \"Tajikistan\",\n\"dial_code\": \"+992\",\n\"code\": \"TJ\"\n},\n{\n\"name\": \"Tanzania, United Republic of Tanzania\",\n\"dial_code\": \"+255\",\n\"code\": \"TZ\"\n},\n{\n\"name\": \"Thailand\",\n\"dial_code\": \"+66\",\n\"code\": \"TH\"\n},\n{\n\"name\": \"Timor-Leste\",\n\"dial_code\": \"+670\",\n\"code\": \"TL\"\n},\n{\n\"name\": \"Togo\",\n\"dial_code\": \"+228\",\n\"code\": \"TG\"\n},\n{\n\"name\": \"Tokelau\",\n\"dial_code\": \"+690\",\n\"code\": \"TK\"\n},\n{\n\"name\": \"Tonga\",\n\"dial_code\": \"+676\",\n\"code\": \"TO\"\n},\n{\n\"name\": \"Trinidad and Tobago\",\n\"dial_code\": \"+1868\",\n\"code\": \"TT\"\n},\n{\n\"name\": \"Tunisia\",\n\"dial_code\": \"+216\",\n\"code\": \"TN\"\n},\n{\n\"name\": \"Turkey\",\n\"dial_code\": \"+90\",\n\"code\": \"TR\"\n},\n{\n\"name\": \"Turkmenistan\",\n\"dial_code\": \"+993\",\n\"code\": \"TM\"\n},\n{\n\"name\": \"Turks and Caicos Islands\",\n\"dial_code\": \"+1649\",\n\"code\": \"TC\"\n},\n{\n\"name\": \"Tuvalu\",\n\"dial_code\": \"+688\",\n\"code\": \"TV\"\n},\n{\n\"name\": \"Uganda\",\n\"dial_code\": \"+256\",\n\"code\": \"UG\"\n},\n{\n\"name\": \"Ukraine\",\n\"dial_code\": \"+380\",\n\"code\": \"UA\"\n},\n{\n\"name\": \"United Arab Emirates\",\n\"dial_code\": \"+971\",\n\"code\": \"AE\"\n},\n{\n\"name\": \"United Kingdom\",\n\"dial_code\": \"+44\",\n\"code\": \"GB\"\n},\n{\n\"name\": \"United States\",\n\"dial_code\": \"+1\",\n\"code\": \"US\"\n},\n{\n\"name\": \"Uruguay\",\n\"dial_code\": \"+598\",\n\"code\": \"UY\"\n},\n{\n\"name\": \"Uzbekistan\",\n\"dial_code\": \"+998\",\n\"code\": \"UZ\"\n},\n{\n\"name\": \"Vanuatu\",\n\"dial_code\": \"+678\",\n\"code\": \"VU\"\n},\n{\n\"name\": \"Venezuela, Bolivarian Republic of Venezuela\",\n\"dial_code\": \"+58\",\n\"code\": \"VE\"\n},\n{\n\"name\": \"Vietnam\",\n\"dial_code\": \"+84\",\n\"code\": \"VN\"\n},\n{\n\"name\": \"Virgin Islands, British\",\n\"dial_code\": \"+1284\",\n\"code\": \"VG\"\n},\n{\n\"name\": \"Virgin Islands, U.S.\",\n\"dial_code\": \"+1340\",\n\"code\": \"VI\"\n},\n{\n\"name\": \"Wallis and Futuna\",\n\"dial_code\": \"+681\",\n\"code\": \"WF\"\n},\n{\n\"name\": \"Yemen\",\n\"dial_code\": \"+967\",\n\"code\": \"YE\"\n},\n{\n\"name\": \"Zambia\",\n\"dial_code\": \"+260\",\n\"code\": \"ZM\"\n},\n{\n\"name\": \"Zimbabwe\",\n\"dial_code\": \"+263\",\n\"code\": \"ZW\"\n}\n]", new TypeToken<ArrayList<HashMap<String, Object>>>() {
		}.getType());
		n = 0;
		for (int _repeat20 = 0; _repeat20 < (int) (countries.size()); _repeat20++) {
			dialing_code.add(countries.get((int) n).get("dial_code").toString());
			n++;
		}
		dial_code.setOnFocusChangeListener((v, hasFocus) -> {
			if (hasFocus) {

				divider_country.setBackgroundColor(0xFFDADADA);
				divider_dialcode.setBackgroundColor(0xFF193566);
				divider_number.setBackgroundColor(0xFFDADADA);
				first_divider.setBackgroundColor(0xFFDADADA);
				second_divider.setBackgroundColor(0xFFDADADA);
				third_divider.setBackgroundColor(0xFFDADADA);
				forth_divider.setBackgroundColor(0xFFDADADA);
				fiver_divider.setBackgroundColor(0xFFDADADA);
				sixer_divider.setBackgroundColor(0xFFDADADA);
			} else {
				number_edittext.setOnFocusChangeListener((v1, hasFocus1) -> {
					if (hasFocus1) {

						divider_country.setBackgroundColor(0xFFDADADA);
						divider_dialcode.setBackgroundColor(0xFFDADADA);
						divider_number.setBackgroundColor(0xFF193566);
						first_divider.setBackgroundColor(0xFFDADADA);
						second_divider.setBackgroundColor(0xFFDADADA);
						third_divider.setBackgroundColor(0xFFDADADA);
						forth_divider.setBackgroundColor(0xFFDADADA);
						fiver_divider.setBackgroundColor(0xFFDADADA);
						sixer_divider.setBackgroundColor(0xFFDADADA);
					} else {
						first_edittext.setOnFocusChangeListener((v11, hasFocus11) -> {
							if (hasFocus11) {

								divider_country.setBackgroundColor(0xFFDADADA);
								divider_dialcode.setBackgroundColor(0xFFDADADA);
								divider_number.setBackgroundColor(0xFFDADADA);
								first_divider.setBackgroundColor(0xFF193566);
								second_divider.setBackgroundColor(0xFFDADADA);
								third_divider.setBackgroundColor(0xFFDADADA);
								forth_divider.setBackgroundColor(0xFFDADADA);
								fiver_divider.setBackgroundColor(0xFFDADADA);
								sixer_divider.setBackgroundColor(0xFFDADADA);
							} else {
								second_edittext.setOnFocusChangeListener((v111, hasFocus111) -> {
									if (hasFocus111) {

										divider_country.setBackgroundColor(0xFFDADADA);
										divider_dialcode.setBackgroundColor(0xFFDADADA);
										divider_number.setBackgroundColor(0xFFDADADA);
										first_divider.setBackgroundColor(0xFFDADADA);
										second_divider.setBackgroundColor(0xFF193566);
										third_divider.setBackgroundColor(0xFFDADADA);
										forth_divider.setBackgroundColor(0xFFDADADA);
										fiver_divider.setBackgroundColor(0xFFDADADA);
										sixer_divider.setBackgroundColor(0xFFDADADA);
									} else {
										third_edittext.setOnFocusChangeListener((v1111, hasFocus1111) -> {
											if (hasFocus1111) {

												divider_country.setBackgroundColor(0xFFDADADA);
												divider_dialcode.setBackgroundColor(0xFFDADADA);
												divider_number.setBackgroundColor(0xFFDADADA);
												first_divider.setBackgroundColor(0xFFDADADA);
												second_divider.setBackgroundColor(0xFFDADADA);
												third_divider.setBackgroundColor(0xFF193566);
												forth_divider.setBackgroundColor(0xFFDADADA);
												fiver_divider.setBackgroundColor(0xFFDADADA);
												sixer_divider.setBackgroundColor(0xFFDADADA);
											} else {
												forth_edittext.setOnFocusChangeListener((v11111, hasFocus11111) -> {
													if (hasFocus11111) {

														divider_country.setBackgroundColor(0xFFDADADA);
														divider_dialcode.setBackgroundColor(0xFFDADADA);
														divider_number.setBackgroundColor(0xFFDADADA);
														first_divider.setBackgroundColor(0xFFDADADA);
														second_divider.setBackgroundColor(0xFFDADADA);
														third_divider.setBackgroundColor(0xFFDADADA);
														forth_divider.setBackgroundColor(0xFF193566);
														fiver_divider.setBackgroundColor(0xFFDADADA);
														sixer_divider.setBackgroundColor(0xFFDADADA);
													} else {
														fiver_edittext.setOnFocusChangeListener((v111111, hasFocus111111) -> {
															if (hasFocus111111) {

																divider_country.setBackgroundColor(0xFFDADADA);
																divider_dialcode.setBackgroundColor(0xFFDADADA);
																divider_number.setBackgroundColor(0xFFDADADA);
																first_divider.setBackgroundColor(0xFFDADADA);
																second_divider.setBackgroundColor(0xFFDADADA);
																third_divider.setBackgroundColor(0xFFDADADA);
																forth_divider.setBackgroundColor(0xFFDADADA);
																fiver_divider.setBackgroundColor(0xFF193566);
																sixer_divider.setBackgroundColor(0xFFDADADA);
															} else {
																sixer_edittext.setOnFocusChangeListener((v1111111, hasFocus1111111) -> {
																	if (hasFocus1111111) {

																		divider_country.setBackgroundColor(0xFFDADADA);
																		divider_dialcode.setBackgroundColor(0xFFDADADA);
																		divider_number.setBackgroundColor(0xFFDADADA);
																		first_divider.setBackgroundColor(0xFFDADADA);
																		second_divider.setBackgroundColor(0xFFDADADA);
																		third_divider.setBackgroundColor(0xFFDADADA);
																		forth_divider.setBackgroundColor(0xFFDADADA);
																		fiver_divider.setBackgroundColor(0xFFDADADA);
																		sixer_divider.setBackgroundColor(0xFF193566);
																	} else {
																		divider_country.setBackgroundColor(0xFFDADADA);
																		divider_dialcode.setBackgroundColor(0xFFDADADA);
																		divider_number.setBackgroundColor(0xFFDADADA);
																		first_divider.setBackgroundColor(0xFFDADADA);
																		second_divider.setBackgroundColor(0xFFDADADA);
																		third_divider.setBackgroundColor(0xFFDADADA);
																		forth_divider.setBackgroundColor(0xFFDADADA);
																		fiver_divider.setBackgroundColor(0xFFDADADA);
																		sixer_divider.setBackgroundColor(0xFFDADADA);
																	}
																});
															}
														});
													}
												});
											}
										});
									}
								});
							}
						});
					}
				});
			}
		});
		TooltipCompat.setTooltipText(done, "Done");
	}

	@Override
	protected void onTransitionAnimationEnd(boolean isOpen, boolean backward) {
		if (isOpen && !backward) {
			if (forceGetData) {
				if (getArguments().containsKey("Country") && getArguments().containsKey("Code")) {
					if (getArguments().getString("Country").equals(".") && getArguments().getString("Code").equals(".")) {
						dial_calling.startRequestNetwork(RequestNetworkController.GET, "https://ipapi.co/country_calling_code", "dial", _dial_calling_request_listener);
					} else {
						country_text.setText(getArguments().getString("Country"));
						dial_code.setText(getArguments().getString("Code"));
					}
				}
				forceGetData = false;
			}
		}
	}

	@Override
	public boolean onBackPressed() {
		if (Verification) {
			MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getParentActivity());
			builder.setTitle("Moon Meet");
			builder.setMessage("Do you want to stop the verification process");
			builder.setPositiveButton("Continue", (dialog, which) -> {

			});
			builder.setNegativeButton("Stop", (dialog, which) -> {
				if (Verification) {
					Verification = false;
					dial_code.setText("");
					number_edittext.setText("");
					user_phone.setText("");
					first_edittext.setText("");
					second_edittext.setText("");
					third_edittext.setText("");
					forth_edittext.setText("");
					fiver_edittext.setText("");
					sixer_edittext.setText("");
					first_edittext.setText("");
					anim.setTarget(part2);
					anim.setPropertyName("alpha");
					anim.setFloatValues((float) (0));
					anim.setDuration((int) (400));
					anim.start();
					toolbar_txt.setTarget(yourphone);
					toolbar_txt.setPropertyName("alpha");
					toolbar_txt.setFloatValues((float) (0));
					toolbar_txt.setDuration((int) (400));
					toolbar_txt.start();
					timer = new TimerTask() {
						@Override
						public void run() {
							AndroidUtilities.runOnUIThread(() -> {
								dial_calling.startRequestNetwork(RequestNetworkController.GET, "https://ipapi.co/country_calling_code", "dial_code", _dial_calling_request_listener);
								part2.setVisibility(View.GONE);
								done.setVisibility(View.VISIBLE);
								part1.setAlpha((float) (0));
								part1.setVisibility(View.VISIBLE);
								yourphone.setText("Your phone");
								extra.setTarget(part1);
								extra.setPropertyName("alpha");
								extra.setFloatValues((float) (1));
								extra.setDuration((int) (400));
								extra.start();
								extra2.setTarget(yourphone);
								extra2.setPropertyName("alpha");
								extra2.setFloatValues((float) (1));
								extra2.setDuration((int) (400));
								extra2.start();
							});
						}
					};
					_timer.schedule(timer, (int) (200));
				} else {
					finishAffinity();
				}
			});
			builder.create().show();
		} else {
			finishAffinity();
		}
		return false;
	}

	@Override
	public boolean onFragmentCreate() {
		super.onFragmentCreate();
		return true;
	}

	@Override
	public boolean isSwipeBackEnabled(MotionEvent event) {
		return false;
	}
}