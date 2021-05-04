package org.mark.moonmeet;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import org.mark.moonmeet.ui.BaseFragment;
import org.mark.moonmeet.utils.AndroidUtilities;

import java.io.File;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseFragment {

    public final int REQ_CD_CAM = 101;
    public final int REQ_CD_FP = 102;
    private Timer _timer = new Timer();
    private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
    private String ImagePath = "";
    private String AudioPath = "";
    private String MainPath = "";
    private String VideoPath = "";
    private String DocumentPath = "";
    private String changelog = "";
    private String version = "";
    private String update_link = "";
    private String app_version = "";
    private HashMap<String, Object> DynamicMap = new HashMap<>();
    private FrameLayout moon_first_holder;
    private LinearLayout moon_secondholder;
    private LinearLayout the_big_moonmeet_holder;
    private LinearLayout the_little_moon_holder;
    private ImageView moonmeet_logo;
    private TextView moonmeet_txt;
    private LinearLayout the_medium_moonmeet_holder;
    private LinearLayout the_small_moonmeet_holder;
    private ImageView tree_in_middle;
    private ImageView medium_tree_left;
    private ImageView small_tree_left;
    private LinearLayout space_in_low;
    private ImageView small_tree_right;
    private ImageView medium_tree_right;
    private Intent intent = new Intent();
    private TimerTask timer;
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
    private SharedPreferences vp;
    private Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    private File _file_cam;
    private Intent fp = new Intent(Intent.ACTION_GET_CONTENT);
    private TextToSpeech T;
    private SpeechRecognizer S;
    private SharedPreferences sp_paths;
    private TimerTask AnimationUtilsTimer;
    private TimerTask FadeAnimationTimer;
    private DatabaseReference update = _firebase.getReference("update");
    private ChildEventListener _update_child_listener;
    private SharedPreferences passcode;
    private Intent toWeb = new Intent();
    private SharedPreferences CatchedImagePath;
    private SharedPreferences sp_pc;

    @Override
    public View createView(Context context) {
        fragmentView = new FrameLayout(context);
        actionBar.setAddToContainer(false);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.splash, (ViewGroup) fragmentView, false);
        FirebaseApp.initializeApp(context);
        ((ViewGroup) fragmentView).addView(view);
        initialize(context);
        initializeLogic();
        return fragmentView;
    }

    private void initialize(Context context) {
        moon_first_holder = (FrameLayout) findViewById(R.id.moon_first_holder);
        moon_secondholder = (LinearLayout) findViewById(R.id.moon_secondholder);
        the_big_moonmeet_holder = (LinearLayout) findViewById(R.id.the_big_moonmeet_holder);
        the_little_moon_holder = (LinearLayout) findViewById(R.id.the_little_moon_holder);
        moonmeet_logo = (ImageView) findViewById(R.id.moonmeet_logo);
        moonmeet_txt = (TextView) findViewById(R.id.moonmeet_txt);
        the_medium_moonmeet_holder = (LinearLayout) findViewById(R.id.the_medium_moonmeet_holder);
        the_small_moonmeet_holder = (LinearLayout) findViewById(R.id.the_small_moonmeet_holder);
        tree_in_middle = (ImageView) findViewById(R.id.tree_in_middle);
        medium_tree_left = (ImageView) findViewById(R.id.medium_tree_left);
        small_tree_left = (ImageView) findViewById(R.id.small_tree_left);
        space_in_low = (LinearLayout) findViewById(R.id.space_in_low);
        small_tree_right = (ImageView) findViewById(R.id.small_tree_right);
        medium_tree_right = (ImageView) findViewById(R.id.medium_tree_right);
        Fauth = FirebaseAuth.getInstance();
        sp_pc = context.getSharedPreferences("sp_pc", Activity.MODE_PRIVATE);
        vp = context.getSharedPreferences("vp", Activity.MODE_PRIVATE);
        _file_cam = FileUtil.createNewPictureFile(getApplicationContext());
        Uri _uri_cam = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            _uri_cam = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", _file_cam);
        } else {
            _uri_cam = Uri.fromFile(_file_cam);
        }
        cam.putExtra(MediaStore.EXTRA_OUTPUT, _uri_cam);
        cam.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        fp.setType("*/*");
        fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        T = new TextToSpeech(getApplicationContext(), null);
        S = SpeechRecognizer.createSpeechRecognizer(getParentActivity());
        sp_paths = getParentActivity().getSharedPreferences("MoonMeetPaths", Activity.MODE_PRIVATE);
        passcode = getParentActivity().getSharedPreferences("passcode", Activity.MODE_PRIVATE);
        CatchedImagePath = getParentActivity().getSharedPreferences("CatchedImagePath", Activity.MODE_PRIVATE);

        _update_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childKey.equals("new_update")) {
                    if (_childValue.containsKey("version") && (_childValue.containsKey("link") && _childValue.containsKey("changelog"))) {
                        version = _childValue.get("version").toString();
                        update_link = _childValue.get("link").toString();
                        changelog = _childValue.get("changelog").toString();
                        if (version.equals(app_version)) {
                            if (!passcode.getString("passcode", "").equals("")) {
                                intent.setClass(getApplicationContext(), PasscodeLockActivity.class);
                                startActivity(intent);
                            } else {
                                timer = new TimerTask() {
                                    @Override
                                    public void run() {
                                        AndroidUtilities.runOnUIThread(() -> {
                                            if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
                                                Bundle orgs = new Bundle();
                                                orgs.putString("taked_photo", ".");
                                                presentFragment(new SetupActivity(orgs), false);
                                            } else if (vp.getString("ViewPager", "").equals("done")) {
                                                Bundle args = new Bundle();
                                                args.putString("Country", ".");
                                                args.putString("Code", ".");
                                                presentFragment(new OtpActivity(args), false);
                                            } else {
                                                presentFragment(new IntroActivity(), false);
                                            }
                                        });
                                    }
                                };
                                _timer.schedule(timer, (int) (2000));
                            }
                        } else if (!version.equals(app_version)) {
                            _NewUpdate();
                        }
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);

            }

            @Override
            public void onChildMoved(DataSnapshot _param1, String _param2) {

            }

            @Override
            public void onChildRemoved(DataSnapshot _param1) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);

            }

            @Override
            public void onCancelled(DatabaseError _param1) {
                final int _errorCode = _param1.getCode();
                final String _errorMessage = _param1.getMessage();

            }
        };
        update.addChildEventListener(_update_child_listener);

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
            final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";

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
        try {
            android.content.pm.PackageInfo pInfo = getParentActivity().getPackageManager().getPackageInfo(getParentActivity().getPackageName(), 0);
            app_version = pInfo.versionName;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        _anim();
        _MoonMeetPathSetup();
        moonmeet_txt.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(), "fonts/royal_404.ttf"), 1);
        CatchedImagePath.edit().putString("LatestImagePath", "-").apply();
    }

    @Override
    protected void onTransitionAnimationEnd(boolean isOpen, boolean backward) {
        if (isOpen && !backward) {
            Log.d(SplashActivity.class.getSimpleName(), "onStart event get called.");
        }
    }

    @Override
    public void onActivityResultFragment(int requestCode, int resultCode, Intent data) {
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }


    public void _MoonMeetPathSetup() {
        MainPath = FileUtil.getExternalStorageDir().concat("/MoonMeet/");
        ImagePath = FileUtil.getExternalStorageDir().concat("/MoonMeet/".concat("MoonMeet Images/"));
        AudioPath = FileUtil.getExternalStorageDir().concat("/MoonMeet/".concat("MoonMeet Audios/"));
        DocumentPath = FileUtil.getExternalStorageDir().concat("/MoonMeet/".concat("MoonMeet Documents/"));
        VideoPath = FileUtil.getExternalStorageDir().concat("/MoonMeet/".concat("MoonMeet Videos/"));
        sp_paths.edit().putString("MainPath", MainPath).apply();
        sp_paths.edit().putString("ImagePath", ImagePath).apply();
        sp_paths.edit().putString("AudioPath", AudioPath).apply();
        sp_paths.edit().putString("VideoPath", VideoPath).apply();
        sp_paths.edit().putString("DocumentPath", DocumentPath).apply();
        if (FileUtil.isExistFile(MainPath)) {
            if (FileUtil.isExistFile(ImagePath)) {

            } else {
                FileUtil.makeDir(ImagePath);
            }
            if (FileUtil.isExistFile(AudioPath)) {

            } else {
                FileUtil.makeDir(AudioPath);
            }
            if (FileUtil.isExistFile(VideoPath)) {

            } else {
                FileUtil.makeDir(VideoPath);
            }
            if (FileUtil.isExistFile(DocumentPath)) {

            } else {
                FileUtil.makeDir(DocumentPath);
            }
        } else {
            FileUtil.makeDir(MainPath);
        }
    }


    public void _FadeOut(final View _view, final double _duration) {
        _Animator(_view, "scaleX", 0, 200);
        _Animator(_view, "scaleY", 0, 200);
        FadeAnimationTimer = new TimerTask() {
            @Override
            public void run() {
                AndroidUtilities.runOnUIThread(() -> {
                    _Animator(_view, "scaleX", 1, 200);
                    _Animator(_view, "scaleY", 1, 200);
                });
            }
        };
        _timer.schedule(FadeAnimationTimer, (int) (_duration));
    }


    public void _Animator(final View _view, final String _propertyName, final double _value, final double _duration) {
        ObjectAnimator anim = new ObjectAnimator();
        anim.setTarget(_view);
        anim.setPropertyName(_propertyName);
        anim.setFloatValues((float) _value);
        anim.setDuration((long) _duration);
        anim.setInterpolator(new android.view.animation.AccelerateDecelerateInterpolator());
        anim.start();
    }


    public void _anim() {
        moonmeet_logo.setScaleX((float) (0));
        _Animator(moon_secondholder, "translationY", 300, 0);
        AnimationUtilsTimer = new TimerTask() {
            @Override
            public void run() {
                AndroidUtilities.runOnUIThread(() -> _Animator(moon_secondholder, "translationY", 0, 600));
            }
        };
        _timer.schedule(AnimationUtilsTimer, (int) (100));
        AnimationUtilsTimer = new TimerTask() {
            @Override
            public void run() {
                AndroidUtilities.runOnUIThread(() -> {
                    moonmeet_logo.setScaleX((float) (1));
                    moonmeet_logo.setScaleY((float) (1));
                    _FadeOut(moonmeet_logo, 100);
                });
            }
        };
        _timer.schedule(AnimationUtilsTimer, (int) (700));
    }


    public void _addCardView(final View _layoutView, final double _margins, final double _cornerRadius, final double _cardElevation, final double _cardMaxElevation, final boolean _preventCornerOverlap, final String _backgroundColor) {
        androidx.cardview.widget.CardView cv = new androidx.cardview.widget.CardView(getParentActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        int m = (int) _margins;
        lp.setMargins(m, m, m, m);
        cv.setLayoutParams(lp);
        int c = Color.parseColor(_backgroundColor);
        cv.setCardBackgroundColor(c);
        cv.setRadius((float) _cornerRadius);
        cv.setCardElevation((float) _cardElevation);
        cv.setMaxCardElevation((float) _cardMaxElevation);
        cv.setPreventCornerOverlap(_preventCornerOverlap);
        if (_layoutView.getParent() instanceof LinearLayout) {
            ViewGroup vg = ((ViewGroup) _layoutView.getParent());
            vg.removeView(_layoutView);
            vg.removeAllViews();
            vg.addView(cv);
            cv.addView(_layoutView);
        }
    }


    public void _NewUpdate() {
        final com.google.android.material.bottomsheet.BottomSheetDialog bottomSheetDialog = new com.google.android.material.bottomsheet.BottomSheetDialog(getParentActivity());
        View bottomSheetView;
        bottomSheetView = getParentActivity().getLayoutInflater().inflate(R.layout.update_bsc, null);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
        TextView t1 = (TextView) bottomSheetView.findViewById(R.id.t1);
        TextView t2 = (TextView) bottomSheetView.findViewById(R.id.t2);
        TextView b1 = (TextView) bottomSheetView.findViewById(R.id.b1);
        TextView b2 = (TextView) bottomSheetView.findViewById(R.id.b2);
        TextView t3 = (TextView) bottomSheetView.findViewById(R.id.t3);
        TextView t4 = (TextView) bottomSheetView.findViewById(R.id.t4);
        ImageView i1 = (ImageView) bottomSheetView.findViewById(R.id.i1);
        LinearLayout bg1 = (LinearLayout) bottomSheetView.findViewById(R.id.bg1);
        LinearLayout bg2 = (LinearLayout) bottomSheetView.findViewById(R.id.bg2);
        LinearLayout card = (LinearLayout) bottomSheetView.findViewById(R.id.card);
        t1.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(), "fonts/rmedium.ttf"), 0);
        t2.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(), "fonts/en_light.ttf"), 0);
        b1.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(), "fonts/rmedium.ttf"), 0);
        b2.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(), "fonts/rmedium.ttf"), 0);
        t3.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(), "fonts/rmedium.ttf"), 0);
        t4.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(), "fonts/en_light.ttf"), 0);
        _RoundAndBorder(bg1, "#FFFFFF", 0, "#000000", 15);
        _RoundAndBorder(bg2, "#FFFFFF", 0, "#000000", 15);
        _addCardView(card, 0, 15, 0, 0, true, "#FFFFFF");
        _rippleRoundStroke(b2, "#FFFFFF", "#EEEEEE", 15, 2.5d, "#EEEEEE");
        _rippleRoundStroke(b1, "#FF193566", "#40FFFFFF", 15, 0, "#000000");
        t2.setText(changelog);
        t4.setText("Version".concat(" ".concat(version)));
        b1.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            toWeb.setAction(Intent.ACTION_VIEW);
            toWeb.setData(Uri.parse(update_link));
            startActivity(toWeb);
            finishAffinity();
        });
        b2.setOnClickListener(v -> {
        });
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.show();
    }


    public void _RoundAndBorder(final View _view, final String _color1, final double _border, final String _color2, final double _round) {
        android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
        gd.setColor(Color.parseColor(_color1));
        gd.setCornerRadius((int) _round);
        gd.setStroke((int) _border, Color.parseColor(_color2));
        _view.setBackground(gd);
    }


    public void _rippleRoundStroke(final View _view, final String _focus, final String _pressed, final double _round, final double _stroke, final String _strokeclr) {
        android.graphics.drawable.GradientDrawable GG = new android.graphics.drawable.GradientDrawable();
        GG.setColor(Color.parseColor(_focus));
        GG.setCornerRadius((float) _round);
        GG.setStroke((int) _stroke,
                Color.parseColor("#" + _strokeclr.replace("#", "")));
        android.graphics.drawable.RippleDrawable RE = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(_pressed)}), GG, null);
        _view.setBackground(RE);
    }
}
