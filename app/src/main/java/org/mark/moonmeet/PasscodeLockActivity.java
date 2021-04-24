package org.mark.moonmeet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Vibrator;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PasscodeLockActivity extends AppCompatActivity {
	
	private String FingerHash = "";
	private String AlreadyFingerHash = "";
	private String KEYNAME = "";
	private  CancellationSignal cancellationSignal;
	private boolean mKeyguardSecure = false;
	private boolean mEnrolledFingerprint = false;
	private boolean mHardwareNotDetected = false;
	private boolean mFine = false;
	
	private LinearLayout bar;
	private LinearLayout divider;
	private LinearLayout holder;
	private MaterialTextView privacy_topbar;
	private ShapeableImageView back;
	private PatternLockView patternlockview;
	
	private SharedPreferences passcode;
	private Vibrator vib;
	private Intent toContinue = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.passcode_lock);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		bar = (LinearLayout) findViewById(R.id.bar);
		divider = (LinearLayout) findViewById(R.id.divider);
		holder = (LinearLayout) findViewById(R.id.holder);
		privacy_topbar = (MaterialTextView) findViewById(R.id.privacy_topbar);
		back = (ShapeableImageView) findViewById(R.id.back);
		patternlockview = (PatternLockView) findViewById(R.id.patternlockview);
		passcode = getSharedPreferences("passcode", Activity.MODE_PRIVATE);
		vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
		patternlockview.addPatternLockListener(new PatternLockViewListener() {
			@Override
			public void onStarted() {
				
			}
			
			@Override
			public void onProgress(List<PatternLockView.Dot> _pattern) {
				
			}
			
			@Override
			public void onComplete(List<PatternLockView.Dot> _pattern) {
				FingerHash = PatternLockUtils.patternToString(patternlockview, _pattern);
				AlreadyFingerHash = passcode.getString("passcode", "");
				if (FingerHash.equals(AlreadyFingerHash)) {
					toContinue.setClass(getApplicationContext(), LaunchActivity.class);
					startActivity(toContinue);
				}
				else {
					patternlockview.setViewMode(PatternLockView.PatternViewMode.WRONG);
					vib.vibrate((long)(25));
					patternlockview.clearPattern();
				}
			}
			
			@Override
			public void onCleared() {
				
			}
		});
	}
	
	private void initializeLogic() {
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		bar.setElevation((int)2);
		back.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor("#FF193566"),
			Color.parseColor("#FF193566")}));
		patternlockview.setNormalStateColor(0xFF828282);
		patternlockview.setCorrectStateColor(0xFF64BB6A);
		patternlockview.setWrongStateColor(0xFFFF1A23);
		_mShowFingerPrintDialog();
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
		finishAffinity();
	}
	public void _mShowFingerPrintDialog () {
		KEYNAME = "KEYNAME";
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					android.hardware.fingerprint.FingerprintManager fingerprintManager = (android.hardware.fingerprint.FingerprintManager) getSystemService(FINGERPRINT_SERVICE); 
					android.app.KeyguardManager keyguardManager = (android.app.KeyguardManager) getSystemService(KEYGUARD_SERVICE);
					if (!fingerprintManager.isHardwareDetected()) {
								//Fingerprint Scanner not detected in Device
								mHardwareNotDetected = false;



					}
					else {
								if (!keyguardManager.isKeyguardSecure()) {
											//Add Lock to your Phone in Settings
											mKeyguardSecure = false;
											SketchwareUtil.showMessage(getApplicationContext(), "Add Lock to your Phone in Settings to check if you have fingerprint sensor.");
								}
								else {
											if (!fingerprintManager.hasEnrolledFingerprints()) {
														//You should add atleast 1 Fingerprint to use this Feature
														mEnrolledFingerprint = false;
														SketchwareUtil.showMessage(getApplicationContext(), "You should add atleast 1 Fingerprint to use this feature.");
											}
											else {
														//Place your Finger on Scanner to Access the App
														mFine = true;
														SketchwareUtil.showMessage(getApplicationContext(), "Place your finger on Fingerprint Scanner to access the app.");
														generateKey();
														if (cipherInit()) {
																	android.hardware.fingerprint.FingerprintManager.CryptoObject cryptoObject = new android.hardware.fingerprint.FingerprintManager.CryptoObject(cipher);
																	FingerprintHandler fingerprintHandler = new FingerprintHandler(this); 
																	fingerprintHandler.startAuth(fingerprintManager, cryptoObject);
														}
											}
								}
					}
		}
	}
	@android.annotation.TargetApi(Build.VERSION_CODES.M) 
	private java.security.KeyStore keyStore;
	private void generateKey() { 
		try { 
					java.security.KeyStore keyStore = java.security.KeyStore.getInstance("AndroidKeyStore"); javax.crypto.KeyGenerator keyGenerator = javax.crypto.KeyGenerator.getInstance(android.security.keystore.KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore"); 
					keyStore.load(null); 
					
					keyGenerator.init(new android.security.keystore.KeyGenParameterSpec.Builder(KEYNAME, android.security.keystore.KeyProperties.PURPOSE_ENCRYPT | android.security.keystore.KeyProperties.PURPOSE_DECRYPT) .setBlockModes(android.security.keystore.KeyProperties.BLOCK_MODE_CBC) .setUserAuthenticationRequired(true) .setEncryptionPaddings( android.security.keystore.KeyProperties.ENCRYPTION_PADDING_PKCS7) .build()); keyGenerator.generateKey(); } 
		catch (java.security.KeyStoreException | java.io.IOException | java.security.cert.CertificateException | java.security.NoSuchAlgorithmException | java.security.InvalidAlgorithmParameterException | java.security.NoSuchProviderException e) { e.printStackTrace();
		} }
	@android.annotation.TargetApi(Build.VERSION_CODES.M) 
	private javax.crypto.Cipher cipher;
	public boolean cipherInit() { 
		try { 
					cipher = javax.crypto.Cipher.getInstance(android.security.keystore.KeyProperties.KEY_ALGORITHM_AES + "/" + android.security.keystore.KeyProperties.BLOCK_MODE_CBC + "/" + android.security.keystore.KeyProperties.ENCRYPTION_PADDING_PKCS7); } 
		catch (java.security.NoSuchAlgorithmException | javax.crypto.NoSuchPaddingException e) { throw new RuntimeException("Failed to get Cipher", e); } 
		try { 
					java.security.KeyStore keyStore = java.security.KeyStore.getInstance("AndroidKeyStore");
					keyStore.load(null); 
					
					javax.crypto.SecretKey key = (javax.crypto.SecretKey) keyStore.getKey(KEYNAME, null); cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key); 
					return true; } 
		catch (android.security.keystore.KeyPermanentlyInvalidatedException e) { 
					return false; } 
		catch (java.security.KeyStoreException | java.security.cert.CertificateException | java.security.UnrecoverableKeyException | java.io.IOException | java.security.NoSuchAlgorithmException | java.security.InvalidKeyException e) { throw new RuntimeException("Failed to init Cipher", e); }
	}
	@android.annotation.TargetApi(Build.VERSION_CODES.M)
	
	
	public class FingerprintHandler extends android.hardware.fingerprint.FingerprintManager.AuthenticationCallback { 
		
		private android.content.Context context; 
		public FingerprintHandler(Context context){ this.context = context; } 
		public void startAuth(android.hardware.fingerprint.FingerprintManager fingerprintManager, android.hardware.fingerprint.FingerprintManager.CryptoObject cryptoObject){ 
					cancellationSignal = new CancellationSignal(); 
					fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null); 
					final AlertDialog mFingerDialog = new AlertDialog.Builder(PasscodeLockActivity.this).create();
					LayoutInflater auth = getLayoutInflater();
					
					View convertView = (View) auth.inflate(R.layout.fingerprintc, null);
					mFingerDialog.setView(convertView);
					mFingerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
					LinearLayout main_dialog_holder = (LinearLayout) convertView.findViewById(R.id.main_dialog_holder);

					main_dialog_holder.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)10, 0xFFECF0F3));
					TextView cancel_sensor = (TextView) convertView.findViewById(R.id.cancel_sensor);
					_RippleEffects("#FFDADADA", cancel_sensor);
					cancel_sensor.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View _view) {
											cancellationSignal.cancel();
											mFingerDialog.dismiss();
								}
					});
					mFingerDialog.setCancelable(true);
					if (mFine) {
								mFingerDialog.show();
					}
		}
		@Override
		public void onAuthenticationFailed(){
					vib.vibrate((long)(35));
		}
		@Override 
		public void onAuthenticationError(int errorCode, CharSequence errString){
					vib.vibrate((long)(35));
		}
		@Override 
		public void onAuthenticationSucceeded(android.hardware.fingerprint.FingerprintManager.AuthenticationResult result){
					toContinue.setClass(getApplicationContext(), LaunchActivity.class);
					startActivity(toContinue);
					cancellationSignal.cancel();
		} 
		
		@Override
		public void onAuthenticationHelp(int helpCode, CharSequence helpString){
					SketchwareUtil.showMessage(getApplicationContext(), (helpString.toString()));
		} 
		
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
