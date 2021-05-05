package org.mark.moonmeet;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import org.mark.moonmeet.components.LayoutHelper;
import org.mark.moonmeet.ui.BaseFragment;
import org.mark.moonmeet.utils.AndroidUtilities;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseFragment {

    private final Timer timerUtil = new Timer();
    private final FirebaseDatabase firebase = FirebaseDatabase.getInstance();
    private final DatabaseReference update = firebase.getReference("update");
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
    private SharedPreferences vp;
    private SharedPreferences sp_paths;
    private TimerTask AnimationUtilsTimer;
    private TimerTask FadeAnimationTimer;
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
        ((ViewGroup) fragmentView).addView(view, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        initialize(context);
        initializeLogic();
        return fragmentView;
    }

    private void initialize(Context context) {
        moon_first_holder = findViewById(R.id.moon_first_holder);
        moon_secondholder = findViewById(R.id.moon_secondholder);
        the_big_moonmeet_holder = findViewById(R.id.the_big_moonmeet_holder);
        the_little_moon_holder = findViewById(R.id.the_little_moon_holder);
        moonmeet_logo = findViewById(R.id.moonmeet_logo);
        moonmeet_txt = findViewById(R.id.moonmeet_txt);
        the_medium_moonmeet_holder = findViewById(R.id.the_medium_moonmeet_holder);
        the_small_moonmeet_holder = findViewById(R.id.the_small_moonmeet_holder);
        tree_in_middle = findViewById(R.id.tree_in_middle);
        medium_tree_left = findViewById(R.id.medium_tree_left);
        small_tree_left = findViewById(R.id.small_tree_left);
        space_in_low = findViewById(R.id.space_in_low);
        small_tree_right = findViewById(R.id.small_tree_right);
        medium_tree_right = findViewById(R.id.medium_tree_right);
        Fauth = FirebaseAuth.getInstance();
        sp_pc = context.getSharedPreferences("sp_pc", Activity.MODE_PRIVATE);
        vp = context.getSharedPreferences("vp", Activity.MODE_PRIVATE);
        sp_paths = getParentActivity().getSharedPreferences("MoonMeetPaths", Activity.MODE_PRIVATE);
        passcode = getParentActivity().getSharedPreferences("passcode", Activity.MODE_PRIVATE);
        CatchedImagePath = getParentActivity().getSharedPreferences("CatchedImagePath", Activity.MODE_PRIVATE);

        ChildEventListener _update_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot param1, String param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String childKey = param1.getKey();
                final HashMap<String, Object> childValue = param1.getValue(_ind);
                if (childKey.equals("new_update")) {
                    if (childValue.containsKey("version") && (childValue.containsKey("link") && childValue.containsKey("changelog"))) {
                        version = childValue.get("version").toString();
                        update_link = childValue.get("link").toString();
                        changelog = childValue.get("changelog").toString();
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
                                                presentFragment(new SetupActivity(orgs));
                                            } else if (vp.getString("ViewPager", "").equals("done")) {
                                                Bundle args = new Bundle();
                                                args.putString("Country", ".");
                                                args.putString("Code", ".");
                                                presentFragment(new OtpActivity(args));
                                            } else {
                                                presentFragment(new IntroActivity());
                                            }
                                        });
                                    }
                                };
                                timerUtil.schedule(timer, (int) (2000));
                            }
                        } else if (!version.equals(app_version)) {
                            NewUpdate();
                        }
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }

        };
        update.addChildEventListener(_update_child_listener);
    }

    private void initializeLogic() {
        try {
            android.content.pm.PackageInfo pInfo = getParentActivity().getPackageManager().getPackageInfo(getParentActivity().getPackageName(), 0);
            app_version = pInfo.versionName;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        anim();
        MoonMeetPathSetup();
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


    public void MoonMeetPathSetup() {
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


    public void FadeOut(final View view, final double duration) {
        Animator(view, "scaleX", 0, 200);
        Animator(view, "scaleY", 0, 200);
        FadeAnimationTimer = new TimerTask() {
            @Override
            public void run() {
                AndroidUtilities.runOnUIThread(() -> {
                    Animator(view, "scaleX", 1, 200);
                    Animator(view, "scaleY", 1, 200);
                });
            }
        };
        timerUtil.schedule(FadeAnimationTimer, (int) (duration));
    }


    public void Animator(final View view, final String propertyName, final double value, final double duration) {
        ObjectAnimator anim = new ObjectAnimator();
        anim.setTarget(view);
        anim.setPropertyName(propertyName);
        anim.setFloatValues((float) value);
        anim.setDuration((long) duration);
        anim.setInterpolator(new android.view.animation.AccelerateDecelerateInterpolator());
        anim.start();
    }


    public void anim() {
        moonmeet_logo.setScaleX((float) (0));
        Animator(moon_secondholder, "translationY", 300, 0);
        AnimationUtilsTimer = new TimerTask() {
            @Override
            public void run() {
                AndroidUtilities.runOnUIThread(() -> Animator(moon_secondholder, "translationY", 0, 600));
            }
        };
        timerUtil.schedule(AnimationUtilsTimer, (int) (100));
        AnimationUtilsTimer = new TimerTask() {
            @Override
            public void run() {
                AndroidUtilities.runOnUIThread(() -> {
                    moonmeet_logo.setScaleX((float) (1));
                    moonmeet_logo.setScaleY((float) (1));
                    FadeOut(moonmeet_logo, 100);
                });
            }
        };
        timerUtil.schedule(AnimationUtilsTimer, (int) (700));
    }


    public void addCardView(final View layoutView, final double margins, final double cornerRadius, final double cardElevation, final double cardMaxElevation, final boolean preventCornerOverlap, final String backgroundColor) {
        androidx.cardview.widget.CardView cv = new androidx.cardview.widget.CardView(getParentActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        int m = (int) margins;
        lp.setMargins(m, m, m, m);
        cv.setLayoutParams(lp);
        int c = Color.parseColor(backgroundColor);
        cv.setCardBackgroundColor(c);
        cv.setRadius((float) cornerRadius);
        cv.setCardElevation((float) cardElevation);
        cv.setMaxCardElevation((float) cardMaxElevation);
        cv.setPreventCornerOverlap(preventCornerOverlap);
        if (layoutView.getParent() instanceof LinearLayout) {
            ViewGroup vg = ((ViewGroup) layoutView.getParent());
            vg.removeView(layoutView);
            vg.removeAllViews();
            vg.addView(cv);
            cv.addView(layoutView);
        }
    }

    public void NewUpdate() {
        final com.google.android.material.bottomsheet.BottomSheetDialog bottomSheetDialog = new com.google.android.material.bottomsheet.BottomSheetDialog(getParentActivity());
        View bottomSheetView;
        bottomSheetView = getParentActivity().getLayoutInflater().inflate(R.layout.update_bsc, null);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
        TextView t1 = bottomSheetView.findViewById(R.id.t1);
        TextView t2 = bottomSheetView.findViewById(R.id.t2);
        TextView b1 = bottomSheetView.findViewById(R.id.b1);
        TextView b2 = bottomSheetView.findViewById(R.id.b2);
        TextView t3 = bottomSheetView.findViewById(R.id.t3);
        TextView t4 = bottomSheetView.findViewById(R.id.t4);
        ImageView i1 = bottomSheetView.findViewById(R.id.i1);
        LinearLayout bg1 = bottomSheetView.findViewById(R.id.bg1);
        LinearLayout bg2 = bottomSheetView.findViewById(R.id.bg2);
        LinearLayout card = bottomSheetView.findViewById(R.id.card);
        t1.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(), "fonts/rmedium.ttf"), 0);
        t2.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(), "fonts/en_light.ttf"), 0);
        b1.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(), "fonts/rmedium.ttf"), 0);
        b2.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(), "fonts/rmedium.ttf"), 0);
        t3.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(), "fonts/rmedium.ttf"), 0);
        t4.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(), "fonts/en_light.ttf"), 0);
        RoundAndBorder(bg1, "#FFFFFF", 0, "#000000", 15);
        RoundAndBorder(bg2, "#FFFFFF", 0, "#000000", 15);
        addCardView(card, 0, 15, 0, 0, true, "#FFFFFF");
        rippleRoundStroke(b2, "#FFFFFF", "#EEEEEE", 15, 2.5d, "#EEEEEE");
        rippleRoundStroke(b1, "#FF193566", "#40FFFFFF", 15, 0, "#000000");
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


    public void RoundAndBorder(final View view, final String color1, final double border, final String color2, final double round) {
        android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
        gd.setColor(Color.parseColor(color1));
        gd.setCornerRadius((int) round);
        gd.setStroke((int) border, Color.parseColor(color2));
        view.setBackground(gd);
    }


    public void rippleRoundStroke(final View view, final String focus, final String presssed, final double round, final double stroke, final String strokeclr) {
        android.graphics.drawable.GradientDrawable GG = new android.graphics.drawable.GradientDrawable();
        GG.setColor(Color.parseColor(focus));
        GG.setCornerRadius((float) round);
        GG.setStroke((int) stroke,
                Color.parseColor("#" + strokeclr.replace("#", "")));
        android.graphics.drawable.RippleDrawable RE = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(presssed)}), GG, null);
        view.setBackground(RE);
    }
}
