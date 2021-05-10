package org.mark.moonmeet.components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.mark.moonmeet.R;
import org.mark.moonmeet.utils.NotificationCenter;

public class SoftKeyboardPopup extends PopupWindow implements ViewTreeObserver.OnGlobalLayoutListener {
    private Context context;
    private ViewGroup rootView;
    private EditText editText;
    private View anchorView;
    private View triggerView;
    private View contentView;
    private View view;

    private Intent toCamera = new Intent();
    private Intent toPickImage = new Intent();

    private LinearLayout alllin;
    private LinearLayout item1lin;
    private LinearLayout item2lin;
    private LinearLayout item3lin;
    private LinearLayout item4lin;
    private LinearLayout item1bg;
    private LinearLayout item2bg;
    private LinearLayout item3bg;
    private LinearLayout item4bg;
    private ImageView item1img;
    private ImageView item2img;
    private ImageView item3img;
    private ImageView item4img;
    private TextView ttl1txt;
    private TextView ttl2txt;
    private TextView ttl3txt;
    private TextView ttl4txt;

    private int KEYBOARD_OFFSET = 150 * Math.round(Resources.getSystem().getDisplayMetrics().density);
    private int defaultBottomMargin = 6 * Math.round(Resources.getSystem().getDisplayMetrics().density);
    private int defaultHorizontalMargin;
    private boolean isKeyboardOpened = false;
    private boolean isShowAtTop = false;
    private boolean isDismissing = false;
    private boolean firstLoad = true;
    private int keyboardHeight = KEYBOARD_OFFSET;
    private int keyboardOf = 0;

    public SoftKeyboardPopup(Context context, ViewGroup rootView, EditText edittext, View anchorView, View triggerView) {
        this.context = context;
        this.rootView = rootView;
        this.editText = editText;
        this.anchorView = anchorView;
        this.triggerView = triggerView;

        initConfig();
        initKeyboardListener();
        initMenuView();
    }

    public void initConfig() {
        setOutsideTouchable(true);
        setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });
    }

    private void initKeyboardListener() {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {

        int screenHeight = getScreenHeight();
        Rect windowRect = new Rect();

        rootView.getWindowVisibleDisplayFrame(windowRect);
        int windowHeight = windowRect.bottom - windowRect.top;
        int statusBarHeight = getStatusBarHeight();

        int heightDifference = screenHeight - windowHeight - statusBarHeight;
        if (firstLoad) {
            firstLoad = false;
            keyboardOf = heightDifference;
        }

        if (heightDifference > KEYBOARD_OFFSET) {
            keyboardHeight = heightDifference;
            isKeyboardOpened = true;
        } else {
            isKeyboardOpened = false;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return;
            dismiss();
        }
        //SketchwareUtil.showMessage(context,heightDifference + ">" + KEYBOARD_OFFSET);
    }

    public int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public int getStatusBarHeight() {
        int height = 0;
        int resourceID = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceID > 0) {
            height = context.getResources().getDimensionPixelSize(resourceID);
        }
        return height;
    }

    private void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    private void showAtTop() {
        isShowAtTop = true;
        setFocusable(true);
        setSize(rootView.getWidth() - defaultHorizontalMargin, keyboardHeight);
        Rect windowRect = new Rect();
        rootView.getWindowVisibleDisplayFrame(windowRect);
        int y = windowRect.bottom - keyboardHeight - (rootView.getBottom() - anchorView.getTop()) - defaultBottomMargin;
        showAtLocation(rootView, Gravity.BOTTOM, 0, y);
    }

    private void showOverKeyboard() {
        isShowAtTop = false;
        setFocusable(false);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            hideKeyboard();
        }
        setSize(LayoutParams.MATCH_PARENT, keyboardHeight + -keyboardOf);
        showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
    }

    @SuppressLint("InflateParams")
    private void initMenuView() {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.bsc, rootView, false);

        view.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> revealView());
        setContentView(view);

        // Define LinearLayouts
        LinearLayout alllin = (LinearLayout) view.findViewById(R.id.linear1);

        _radius_4("#FFFFFF", "#00000000", 0, 0, 0, 0, 0, alllin);
        LinearLayout item1lin = (LinearLayout) view.findViewById(R.id.item1);

        _RippleEffects("#DADADA", item1lin);
        LinearLayout item2lin = (LinearLayout) view.findViewById(R.id.item2);

        _RippleEffects("#DADADA", item2lin);
        LinearLayout item3lin = (LinearLayout) view.findViewById(R.id.item3);

        _RippleEffects("#DADADA", item3lin);
        LinearLayout item4lin = (LinearLayout) view.findViewById(R.id.item4);

        _RippleEffects("#DADADA", item4lin);
        LinearLayout item1bg = (LinearLayout) view.findViewById(R.id.item1_lin);
        LinearLayout item2bg = (LinearLayout) view.findViewById(R.id.item2_lin);
        LinearLayout item3bg = (LinearLayout) view.findViewById(R.id.item3_lin);
        LinearLayout item4bg = (LinearLayout) view.findViewById(R.id.item4_lin);
        // Define ImageViews
        ImageView item1img = (ImageView) view.findViewById(R.id.item1_img);

        item1img.setImageResource(R.drawable.menu_camera);
        _ICC(item1img, "#193566", "#193566");
        ImageView item2img = (ImageView) view.findViewById(R.id.item2_img);

        item2img.setImageResource(R.drawable.profile_photos);
        _ICC(item2img, "#193566", "#193566");
        ImageView item3img = (ImageView) view.findViewById(R.id.item3_img);

        item3img.setImageResource(R.drawable.input_video);
        _ICC(item3img, "#193566", "#193566");
        ImageView item4img = (ImageView) view.findViewById(R.id.item4_img);

        item4img.setImageResource(R.drawable.input_attach);
        _ICC(item4img, "#193566", "#193566");
        // Define TextViews
        TextView ttl1txt = (TextView) view.findViewById(R.id.ttl1_txt);
        ttl1txt.setText("Pick The");
        ttl1txt.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/rmedium.ttf"), 0);
        TextView ttl2txt = (TextView) view.findViewById(R.id.ttl2_txt);
        ttl2txt.setText("Following Files");
        ttl2txt.setTextColor(0xFF193566);
        ttl2txt.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/rmedium.ttf"), 0);
        TextView item1txt = (TextView) view.findViewById(R.id.item1_txt);

        item1txt.setText("Take a photo");
        item1txt.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/rmedium.ttf"), 0);
        TextView item2txt = (TextView) view.findViewById(R.id.item2_txt);

        item2txt.setText("Import a photo");
        item2txt.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/rmedium.ttf"), 0);
        TextView item3txt = (TextView) view.findViewById(R.id.item3_txt);

        item3txt.setText("Import a video");
        item3txt.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/rmedium.ttf"), 0);
        TextView item4txt = (TextView) view.findViewById(R.id.item4_txt);

        item4txt.setText("Import a file");
        item4txt.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/rmedium.ttf"), 0);
        // Begging of SetData
        item3lin.setVisibility(View.GONE);
        item4lin.setVisibility(View.GONE);
        // OnClick
        item1lin.setOnClickListener(v -> NotificationCenter.getInstance().postNotificationName(NotificationCenter.getNeedPresentCamera, "ignore"));
        item2lin.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putString("multiple_images", "false");
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.getNeedPresentImagePicker, args);
        });
        item3lin.setOnClickListener(v -> {
            //startActivityForResult(video_picker, REQ_CD_VIDEO_PICKER);
            dismiss();
        });
        item4lin.setOnClickListener(v -> {
            //startActivityForResult(attach_picker, REQ_CD_ATTACH_PICKER);
            dismiss();
        });

        contentView = view;
    }

    public void show() {
        if (isKeyboardOpened) {
            showOverKeyboard();
            //SketchwareUtil.showMessage(context,String.valueOf(keyboardHeight));
        } else {
            showAtTop();
        }
    }

    @Override
    public void dismiss() {
        if (isDismissing || !isShowing()) return;
        isDismissing = true;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            super.dismiss();
            return;
        }
        int centerX = calculateCenterX();
        int centerY = calculateCenterY();
        float endRadius = calculateRadius(centerX);
        Animator anim = ViewAnimationUtils.createCircularReveal(contentView, centerX, centerY, endRadius * 2.2f, 0f);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                SoftKeyboardPopup.super.dismiss();
                isDismissing = false;
            }
        });
        anim.start();
    }

    private void revealView() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return;
        int centerX = calculateCenterX();
        int centerY = calculateCenterY();
        float endRadius = calculateRadius(centerX);
        //SketchwareUtil.showMessage(context,"end radius: " + endRadius);
        Animator animator = ViewAnimationUtils.createCircularReveal(contentView, centerX, centerY, 0f, endRadius * 2.2f);
        animator.start();
    }

    private int calculateCenterX() {
        int viewCenter = triggerView.getWidth() / 2;
        return triggerView.getLeft() + viewCenter;
    }

    private float calculateRadius(int centerX) {
        float x = contentView.getHeight();
        return (float) Math.sqrt((centerX * centerX + x * x));
    }

    private int calculateCenterY() {
        int centerY = 0;
        if (isShowAtTop) {
            centerY = view.getBottom();
        }
        return centerY;
    }

    private void _radius_4(final String _color1, final String _color2, final double _str, final double _n1, final double _n2, final double _n3, final double _n4, final View _view) {
        android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
        gd.setColor(Color.parseColor(_color1));
        gd.setStroke((int) _str, Color.parseColor(_color2));
        gd.setCornerRadii(new float[]{(int) _n1, (int) _n1, (int) _n2, (int) _n2, (int) _n3, (int) _n3, (int) _n4, (int) _n4});
        _view.setBackground(gd);
    }

    private void _RippleEffects(final String _color, final View _view) {
        android.content.res.ColorStateList clr = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(_color)});
        android.graphics.drawable.RippleDrawable ripdr = new android.graphics.drawable.RippleDrawable(clr, null, null);
        _view.setBackground(ripdr);
    }

    private void _ICC(final ImageView _img, final String _c1, final String _c2) {
        _img.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor(_c1), Color.parseColor(_c2)}));
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}
