package org.mark.moonmeet;

import static android.content.Context.FINGERPRINT_SERVICE;
import static android.content.Context.KEYGUARD_SERVICE;
import static android.os.Build.VERSION_CODES.M;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import org.mark.moonmeet.ui.BaseFragment;
import org.mark.moonmeet.utils.AndroidUtilities;

import java.util.List;

public class PasscodeLockActivity extends BaseFragment {

    private String FingerHash = "";
    private String AlreadyFingerHash = "";
    private String KEYNAME = "";
    private CancellationSignal cancellationSignal;
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
    private java.security.KeyStore keyStore;
    @TargetApi(M)
    private javax.crypto.Cipher cipher;

    @Override
    public View createView(Context context) {
        fragmentView = new FrameLayout(context);
        actionBar.setAddToContainer(false);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.passcode_lock, ((ViewGroup) fragmentView), false);
        ((ViewGroup) fragmentView).addView(view);
        initialize(context);
        com.google.firebase.FirebaseApp.initializeApp(context);
        initializeLogic();
        return fragmentView;
    }

    private void initialize(Context context) {
        bar = (LinearLayout) findViewById(R.id.bar);
        divider = (LinearLayout) findViewById(R.id.divider);
        holder = (LinearLayout) findViewById(R.id.holder);
        privacy_topbar = (MaterialTextView) findViewById(R.id.privacy_topbar);
        back = (ShapeableImageView) findViewById(R.id.back);
        patternlockview = (PatternLockView) findViewById(R.id.patternlockview);
        passcode = context.getSharedPreferences("passcode", Activity.MODE_PRIVATE);
        vib = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

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
                    presentFragment(new LaunchActivity());
                } else {
                    patternlockview.setViewMode(PatternLockView.PatternViewMode.WRONG);
                    vib.vibrate((long) (25));
                    patternlockview.clearPattern();
                }
            }

            @Override
            public void onCleared() {

            }
        });
    }

    private void initializeLogic() {
        bar.setElevation((int) 2);
        back.setImageTintList(new ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor("#FF193566"),
                Color.parseColor("#FF193566")}));
        patternlockview.setNormalStateColor(0xFF828282);
        patternlockview.setCorrectStateColor(0xFF64BB6A);
        patternlockview.setWrongStateColor(0xFFFF1A23);
        if (Build.VERSION.SDK_INT >= M) {
            _mShowFingerPrintDialog();
        }
    }

    @Override
    public boolean onBackPressed() {
        finishAffinity();
        return false;
    }

    @RequiresApi(M)
    public void _mShowFingerPrintDialog() {
        KEYNAME = "KEYNAME";
        if (Build.VERSION.SDK_INT >= M) {
            android.hardware.fingerprint.FingerprintManager fingerprintManager = (android.hardware.fingerprint.FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            android.app.KeyguardManager keyguardManager = (android.app.KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            if (!fingerprintManager.isHardwareDetected()) {
                //Fingerprint Scanner not detected in Device
                mHardwareNotDetected = false;
                return;

            } else {
                if (!keyguardManager.isKeyguardSecure()) {
                    //Add Lock to your Phone in Settings
                    mKeyguardSecure = false;
                    SketchwareUtil.showMessage(getApplicationContext(), "Add Lock to your Phone in Settings to check if you have fingerprint sensor.");
                } else {
                    if (!fingerprintManager.hasEnrolledFingerprints()) {
                        //You should add at least 1 Fingerprint to use this Feature
                        mEnrolledFingerprint = false;
                        SketchwareUtil.showMessage(getApplicationContext(), "You should add atleast 1 Fingerprint to use this feature.");
                    } else {
                        //Place your Finger on Scanner to Access the App
                        mFine = true;
                        SketchwareUtil.showMessage(getApplicationContext(), "Place your finger on Fingerprint Scanner to access the app.");
                        generateKey();
                        if (cipherInit()) {
                            android.hardware.fingerprint.FingerprintManager.CryptoObject cryptoObject = new android.hardware.fingerprint.FingerprintManager.CryptoObject(cipher);
                            FingerprintHandler fingerprintHandler = new FingerprintHandler(getParentActivity());
                            fingerprintHandler.startAuth(fingerprintManager, cryptoObject);
                        }
                    }
                }
            }
        }
    }

    @TargetApi(M)
    private void generateKey() {
        try {
            java.security.KeyStore keyStore = java.security.KeyStore.getInstance("AndroidKeyStore");
            javax.crypto.KeyGenerator keyGenerator = javax.crypto.KeyGenerator.getInstance(android.security.keystore.KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            keyStore.load(null);

            keyGenerator.init(new android.security.keystore.KeyGenParameterSpec.Builder(KEYNAME, android.security.keystore.KeyProperties.PURPOSE_ENCRYPT | android.security.keystore.KeyProperties.PURPOSE_DECRYPT).setBlockModes(android.security.keystore.KeyProperties.BLOCK_MODE_CBC).setUserAuthenticationRequired(true).setEncryptionPaddings(android.security.keystore.KeyProperties.ENCRYPTION_PADDING_PKCS7).build());
            keyGenerator.generateKey();
        } catch (java.security.KeyStoreException | java.io.IOException | java.security.cert.CertificateException | java.security.NoSuchAlgorithmException | java.security.InvalidAlgorithmParameterException | java.security.NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(M)
    public boolean cipherInit() {
        try {
            cipher = javax.crypto.Cipher.getInstance(android.security.keystore.KeyProperties.KEY_ALGORITHM_AES + "/" + android.security.keystore.KeyProperties.BLOCK_MODE_CBC + "/" + android.security.keystore.KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (java.security.NoSuchAlgorithmException | javax.crypto.NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }
        try {
            java.security.KeyStore keyStore = java.security.KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);

            javax.crypto.SecretKey key = (javax.crypto.SecretKey) keyStore.getKey(KEYNAME, null);
            cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (android.security.keystore.KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (java.security.KeyStoreException | java.security.cert.CertificateException | java.security.UnrecoverableKeyException | java.io.IOException | java.security.NoSuchAlgorithmException | java.security.InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    public void _RippleEffects(final String _color, final View _view) {
        android.content.res.ColorStateList clr = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(_color)});
        android.graphics.drawable.RippleDrawable ripdr = new android.graphics.drawable.RippleDrawable(clr, null, null);
        _view.setBackground(ripdr);
    }

    @android.annotation.TargetApi(M)


    public class FingerprintHandler extends android.hardware.fingerprint.FingerprintManager.AuthenticationCallback {

        private android.content.Context context;

        public FingerprintHandler(Context context) {
            this.context = context;
        }

        public void startAuth(android.hardware.fingerprint.FingerprintManager fingerprintManager, android.hardware.fingerprint.FingerprintManager.CryptoObject cryptoObject) {
            cancellationSignal = new CancellationSignal();
            fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
            final AlertDialog mFingerDialog = new AlertDialog.Builder(getParentActivity()).create();
            LayoutInflater auth = getParentActivity().getLayoutInflater();

            View convertView = (View) auth.inflate(R.layout.fingerprintc, null);
            mFingerDialog.setView(convertView);
            mFingerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LinearLayout main_dialog_holder = (LinearLayout) convertView.findViewById(R.id.main_dialog_holder);

            main_dialog_holder.setBackground(new GradientDrawable() {
                public GradientDrawable getIns(int a, int b) {
                    this.setCornerRadius(a);
                    this.setColor(b);
                    return this;
                }
            }.getIns((int) 10, 0xFFFFFF));
            TextView cancel_sensor = (TextView) convertView.findViewById(R.id.cancel_sensor);
            _RippleEffects("#FFDADADA", cancel_sensor);
            cancel_sensor.setOnClickListener(_view -> {
                cancellationSignal.cancel();
                mFingerDialog.dismiss();
            });
            mFingerDialog.setCancelable(true);
            if (mFine) {
                mFingerDialog.show();
            }
        }

        @Override
        public void onAuthenticationFailed() {
            vib.vibrate((long) (35));
        }

        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            vib.vibrate((long) (35));
        }

        @Override
        public void onAuthenticationSucceeded(android.hardware.fingerprint.FingerprintManager.AuthenticationResult result) {
            presentFragment(new LaunchActivity());
            cancellationSignal.cancel();
        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
            AndroidUtilities.showToast((helpString.toString()));
        }

    }
}