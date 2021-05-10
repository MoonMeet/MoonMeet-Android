package org.mark.moonmeet;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import org.mark.moonmeet.ui.BaseFragment;

import java.util.List;

public class PasscodeSetupActivity extends BaseFragment {

    private String addLockPattern = "";
    private String ConfirmPatternLockCode = "";
    private boolean isPassedAddingPattern = false;

    private LinearLayout bar;
    private LinearLayout divider;
    private LinearLayout holder;
    private ShapeableImageView back;
    private MaterialTextView privacy_topbar;
    private TextView textview1;
    private PatternLockView patternlockview;
    private PatternLockView patternlockview1;
    private LinearLayout linear1;
    private TextView cancel_text;
    private LinearLayout linear2;
    private TextView continue_text;

    private SharedPreferences passcode;

    @Override
    public View createView(Context context) {
        fragmentView = new FrameLayout(context);
        actionBar.setAddToContainer(false);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.passcode_setup, ((ViewGroup) fragmentView), false);
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
        back = (ShapeableImageView) findViewById(R.id.back);
        privacy_topbar = (MaterialTextView) findViewById(R.id.privacy_topbar);
        textview1 = (TextView) findViewById(R.id.textview1);
        patternlockview = (PatternLockView) findViewById(R.id.patternlockview);
        patternlockview1 = (PatternLockView) findViewById(R.id.patternlockview1);
        linear1 = (LinearLayout) findViewById(R.id.linear1);
        cancel_text = (TextView) findViewById(R.id.cancel_text);
        linear2 = (LinearLayout) findViewById(R.id.linear2);
        continue_text = (TextView) findViewById(R.id.continue_text);
        passcode = context.getSharedPreferences("passcode", Activity.MODE_PRIVATE);

        back.setOnClickListener(_view -> finishFragment());

        patternlockview.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> _pattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> _pattern) {
                addLockPattern = PatternLockUtils.patternToString(patternlockview, _pattern);
                patternlockview.setCorrectStateColor(0xFF64BB6A);
                textview1.setText("Pattern Recorded");
                cancel_text.setText("Clear");
                continue_text.setEnabled(true);
                isPassedAddingPattern = true;
            }

            @Override
            public void onCleared() {

            }
        });

        patternlockview1.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {
                textview1.setText("Draw pattern again to confirm");
            }

            @Override
            public void onProgress(List<PatternLockView.Dot> _pattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> _pattern) {
                ConfirmPatternLockCode = PatternLockUtils.patternToString(patternlockview1, _pattern);
                if (addLockPattern.equals(ConfirmPatternLockCode)) {
                    patternlockview1.setViewMode(PatternLockView.PatternViewMode.CORRECT);
                    isPassedAddingPattern = false;
                    continue_text.setEnabled(true);
                } else {
                    textview1.setText("Pattern didn't match");
                    patternlockview1.setViewMode(PatternLockView.PatternViewMode.WRONG);
                }
            }

            @Override
            public void onCleared() {

            }
        });

        cancel_text.setOnClickListener(_view -> {
            if (cancel_text.getText().toString().equals("Cancel")) {
                finishFragment();
            } else {
                cancel_text.setText("Cancel");
                textview1.setText("Draw an unlock pattern");
                patternlockview.clearPattern();
                patternlockview1.clearPattern();
            }
        });

        continue_text.setOnClickListener(_view -> {
            if (isPassedAddingPattern) {
                patternlockview.setVisibility(View.GONE);
                patternlockview1.setVisibility(View.VISIBLE);
                textview1.setText("Draw pattern again to confirm");
                cancel_text.setText("Cancel");
                patternlockview.clearPattern();
                patternlockview1.clearPattern();
                continue_text.setEnabled(true);
            } else {
                passcode.edit().putString("passcode", addLockPattern).commit();
                SketchwareUtil.showMessage(getApplicationContext(), "Passcode added.");
                finishFragment();
            }
        });
    }

    private void initializeLogic() {
        back.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor("#FF193566"),
                Color.parseColor("#FF193566")}));
        bar.setElevation((int) 2);
        continue_text.setEnabled(false);
        patternlockview.setVisibility(View.VISIBLE);
        patternlockview1.setVisibility(View.GONE);
        patternlockview.setNormalStateColor(0xFF828282);
        patternlockview.setCorrectStateColor(0xFF64BB6A);
        patternlockview.setWrongStateColor(0xFFFF1A23);
        patternlockview1.setNormalStateColor(0xFF828282);
        patternlockview1.setCorrectStateColor(0xFF64BB6A);
        patternlockview1.setWrongStateColor(0xFFFF1A23);
    }

    @Override
    public boolean onBackPressed() {
        finishFragment();
        return false;
    }
}