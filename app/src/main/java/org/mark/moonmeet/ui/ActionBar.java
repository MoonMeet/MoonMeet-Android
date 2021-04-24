package org.mark.moonmeet.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.mark.moonmeet.MoonMeetApplication;
import org.mark.moonmeet.components.EllipsizeSpanAnimator;
import org.mark.moonmeet.components.LayoutHelper;
import org.mark.moonmeet.utils.AndroidUtilities;

import java.util.ArrayList;

public class ActionBar extends FrameLayout {

    public static class ActionBarMenuOnItemClick {
        public void onItemClick(int id) {

        }

        public boolean canOpenMenu() {
            return true;
        }
    }

    private TextView backButtonTextView;
    private FrameLayout backContainer;
    private ImageView backButtonImageView;
    private SimpleTextView[] titleTextView = new SimpleTextView[2];
    private SimpleTextView subtitleTextView;
    private View actionModeTop;
    private ActionBarMenu menu;
    private ActionBarMenu actionMode;
	
	 private boolean supportsHolidayImage;
	 
    private boolean occupyStatusBar = Build.VERSION.SDK_INT >= 21;
    private boolean actionModeVisible;
    private boolean addToContainer = true;
	private boolean ignoreLayoutRequest;
    private boolean interceptTouches = true;
    private int extraHeight;
    private AnimatorSet actionModeAnimation;
    private View actionModeExtraView;
    private View actionModeTranslationView;
    private View actionModeShowingView;
    private View[] actionModeHidingViews;
	
	private int titleRightMargin;

    private boolean allowOverlayTitle;
    private CharSequence lastOverlayTitle;
    private Object[] overlayTitleToSet = new Object[3];
    private Runnable lastRunnable;
    private boolean titleOverlayShown;
    private Runnable titleActionRunnable;
    private boolean castShadows = true;

    private CharSequence lastTitle;
   

    protected boolean isSearchFieldVisible;
    protected int itemsBackgroundColor;
    protected int itemsActionModeBackgroundColor;
    protected int itemsColor;
    protected int itemsActionModeColor;
    private boolean isBackOverlayVisible;
    protected BaseFragment parentFragment;
    public ActionBarMenuOnItemClick actionBarMenuOnItemClick;
    private Point screenSize;
	
	private int titleColorToSet = 0;
    private boolean overlayTitleAnimation;
    private boolean titleAnimationRunning;
    private boolean fromBottom;
	EllipsizeSpanAnimator ellipsizeSpanAnimator = new EllipsizeSpanAnimator(this);

    public ActionBar(Context context) {
        super(context);
    }


    private void createBackButtonImage() {
        if (backButtonImageView != null) {
            return;
        }
        backButtonImageView = new ImageView(getContext());
        backButtonImageView.setScaleType(ImageView.ScaleType.CENTER);
        backButtonImageView.setBackgroundDrawable(Theme.createSelectorDrawable(itemsBackgroundColor));
        if (itemsColor != 0) {
            backButtonImageView.setColorFilter(new PorterDuffColorFilter(itemsColor, PorterDuff.Mode.MULTIPLY));
        }
        backButtonImageView.setPadding(AndroidUtilities.dp(1), 0, 0, 0);
        addView(backButtonImageView, LayoutHelper.createFrame(54, 54, Gravity.LEFT | Gravity.TOP));

        backButtonImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!actionModeVisible && isSearchFieldVisible) {
                    ActionBar.this.closeSearchField();
                    return;
                }
                if (actionBarMenuOnItemClick != null) {
                    actionBarMenuOnItemClick.onItemClick(-1);
                }
            }
        });
        backButtonImageView.setContentDescription("Go back");
    }

    private void createBackButtonText(String text) {
        if (backButtonTextView != null){
            return;
        }

        backContainer = new FrameLayout(getContext());
        backButtonTextView = new TextView(getContext());
        backButtonTextView.setText(text);
        backButtonTextView.setTextSize(18);
        backButtonTextView.setTextColor(0xffffffff);
        backButtonTextView.setGravity(Gravity.CENTER_VERTICAL);
        LayoutParams textParams = LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.MATCH_PARENT, Gravity.LEFT | Gravity.TOP,8,0,0,0);
        backButtonTextView.setLayoutParams(textParams);
        backContainer.addView(backButtonTextView);
        backContainer.setLayoutParams(LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.MATCH_PARENT));
        addView(backContainer);

        backContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSearchFieldVisible) {
                    closeSearchField();
                    return;
                }
                if (actionBarMenuOnItemClick != null) {
                    actionBarMenuOnItemClick.onItemClick(-1);
                }
            }
        });

    }

    public void setBackButtonDrawable(Drawable drawable) {
        if (backButtonImageView == null) {
            createBackButtonImage();
        }
        backButtonImageView.setVisibility(drawable == null ? GONE : VISIBLE);
        backButtonImageView.setImageDrawable(drawable);
        if (drawable instanceof BackDrawable) {
            BackDrawable backDrawable = (BackDrawable) drawable;
            backDrawable.setRotation(isActionModeShowed() ? 1 : 0, false);
            backDrawable.setRotatedColor(itemsActionModeColor);
            backDrawable.setColor(itemsColor);
        }
    }

    public void setBackButtonImage(int resource) {
        if (backButtonImageView == null) {
            createBackButtonImage();
        }
        backButtonImageView.setVisibility(resource == 0 ? GONE : VISIBLE);
        backButtonImageView.setBackgroundResource(resource);
    }

    public void setBackText(String text) {
        if (backButtonTextView == null) {
            createBackButtonText(text);
        }
    }

    private void createsubtitleTextView() {
        if (subtitleTextView != null) {
            return;
        }
        subtitleTextView = new SimpleTextView(getContext());
        subtitleTextView.setGravity(Gravity.LEFT);
        subtitleTextView.setTextColor(Theme.ACTION_BAR_SUBTITLE_COLOR);
        addView(subtitleTextView, 0, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT,
                LayoutHelper.WRAP_CONTENT, Gravity.LEFT | Gravity.TOP));
    }

    public void setAddToContainer(boolean value) {
        addToContainer = value;
    }

    public boolean shouldAddToContainer() {
        return addToContainer;
    }


    public boolean getAddToContainer() {
        return addToContainer;
    }

    public void setSubtitle(CharSequence value) {
        if (value != null && subtitleTextView == null) {
            createsubtitleTextView();
        }
        if (subtitleTextView != null) {
            subtitleTextView
                    .setVisibility(value != null && !isSearchFieldVisible ? VISIBLE : INVISIBLE);
            subtitleTextView.setText(value);
        }
    }

   private void createTitleTextView(int i) {
        if (titleTextView[i] != null) {
            return;
        }
        titleTextView[i] = new SimpleTextView(getContext());
        titleTextView[i].setGravity(Gravity.LEFT);
        if (titleColorToSet != 0) {
            titleTextView[i].setTextColor(titleColorToSet);
        } else {
            titleTextView[i].setTextColor(Theme.getColor(Theme.key_actionBarDefaultTitle));
        }
        //titleTextView[i].setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        addView(titleTextView[i], 0, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.LEFT | Gravity.TOP));
    }
	
    public void setTitle(CharSequence value) {
        if (value != null && titleTextView[0] == null) {
            createTitleTextView(0);
        }
        if (titleTextView[0] != null) {
            lastTitle = value;
            titleTextView[0].setVisibility(value != null && !isSearchFieldVisible ? VISIBLE : INVISIBLE);
            titleTextView[0].setText(value);
        }
        fromBottom = false;
    }

    public SimpleTextView getSubtitleTextView() {
        return subtitleTextView;
    }

    public SimpleTextView getTitleTextView() {
        return titleTextView[0];
    }

    public String getTitle() {
        if (titleTextView[0] == null) {
            return null;
        }
        return titleTextView[0].getText().toString();
    }

    public ActionBarMenu createMenu() {
        if (menu != null) {
            return menu;
        }
        screenSize = AndroidUtilities.getRealScreenSize();
        menu = new ActionBarMenu(getContext(), this);
        addView(menu, 0, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT,
                LayoutHelper.MATCH_PARENT, Gravity.RIGHT));
        return menu;
    }

    public void setActionBarMenuOnItemClick(ActionBarMenuOnItemClick listener) {
        actionBarMenuOnItemClick = listener;
    }

    public ActionBarMenu createActionMode() {
        if (actionMode != null) {
            return actionMode;
        }
        actionMode = new ActionBarMenu(getContext(), this);
        actionMode.setBackgroundColor(0xffffffff);
        addView(actionMode, indexOfChild(backContainer));
        actionMode.setPadding(0, occupyStatusBar ? AndroidUtilities.statusBarHeight : 0, 0, 0);
        LayoutParams layoutParams = (LayoutParams) actionMode.getLayoutParams();
        layoutParams.height = LayoutHelper.MATCH_PARENT;
        layoutParams.width = LayoutHelper.MATCH_PARENT;
        layoutParams.gravity = Gravity.RIGHT;
        actionMode.setLayoutParams(layoutParams);
        actionMode.setVisibility(INVISIBLE);

        if (occupyStatusBar && actionModeTop == null) {
            actionModeTop = new View(getContext());
            actionModeTop.setBackgroundColor(0x99000000);
            addView(actionModeTop);
            layoutParams = (LayoutParams) actionModeTop.getLayoutParams();
            layoutParams.height = AndroidUtilities.statusBarHeight;
            layoutParams.width = LayoutHelper.MATCH_PARENT;
            layoutParams.gravity = Gravity.TOP | Gravity.LEFT;
            actionModeTop.setLayoutParams(layoutParams);
            actionModeTop.setVisibility(INVISIBLE);
        }

        return actionMode;
    }
/*
    public void showActionMode() {
        if (actionMode == null || actionModeVisible) {
            return;
        }
        actionModeVisible = true;
        ArrayList<Animator> animators = new ArrayList<>();
        animators.add(ObjectAnimator.ofFloat(actionMode, "alpha", 0.0f, 1.0f));
        if (occupyStatusBar && actionModeTop != null) {
            animators.add(ObjectAnimator.ofFloat(actionModeTop, "alpha", 0.0f, 1.0f));
        }
        if (actionModeAnimation != null) {
            actionModeAnimation.cancel();
        }
        actionModeAnimation = new AnimatorSet();
        actionModeAnimation.playTogether(animators);
        actionModeAnimation.setDuration(200);
        actionModeAnimation.addListener(new AnimatorListenerAdapterProxy() {
            @Override
            public void onAnimationStart(Animator animation) {
                actionMode.setVisibility(VISIBLE);
                if (occupyStatusBar && actionModeTop != null) {
                    actionModeTop.setVisibility(VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (actionModeAnimation != null && actionModeAnimation.equals(animation)) {
                    actionModeAnimation = null;
                    if (titleTextView != null) {
                        titleTextView.setVisibility(INVISIBLE);
                    }
                    if (subtitleTextView != null) {
                        subtitleTextView.setVisibility(INVISIBLE);
                    }
                    if (menu != null) {
                        menu.setVisibility(INVISIBLE);
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                if (actionModeAnimation != null && actionModeAnimation.equals(animation)) {
                    actionModeAnimation = null;
                }
            }
        });
        actionModeAnimation.start();
        if (backButtonImageView != null) {
            Drawable drawable = backButtonImageView.getDrawable();
            if (drawable instanceof BackDrawable) {
                ((BackDrawable) drawable).setRotation(1, true);
            }
            backButtonImageView.setBackgroundDrawable(
                    Theme.createBarSelectorDrawable(Theme.ACTION_BAR_MODE_SELECTOR_COLOR));
        }
    }*/
	
	public void setItemsColor(int color, boolean isActionMode) {
        if (isActionMode) {
            itemsActionModeColor = color;
            if (actionMode != null) {
                actionMode.updateItemsColor();
            }
            if (backButtonImageView != null) {
                Drawable drawable = backButtonImageView.getDrawable();
                if (drawable instanceof BackDrawable) {
                    ((BackDrawable) drawable).setRotatedColor(color);
                }
            }
        } else {
            itemsColor = color;
            if (backButtonImageView != null) {
                if (itemsColor != 0) {
                    backButtonImageView.setColorFilter(new PorterDuffColorFilter(itemsColor, PorterDuff.Mode.MULTIPLY));
                    Drawable drawable = backButtonImageView.getDrawable();
                    if (drawable instanceof BackDrawable) {
                        ((BackDrawable) drawable).setColor(color);
                    } else if (drawable instanceof  MenuDrawable) {
                        ((MenuDrawable) drawable).setIconColor(color);
                    }
                }
            }
            if (menu != null) {
                menu.updateItemsColor();
            }
        }
    }
	
	public void showActionMode(View extraView, View showingView, View[] hidingViews, final boolean[] hideView, View translationView, int translation) {
        if (actionMode == null || actionModeVisible) {
            return;
        }
        actionModeVisible = true;
        ArrayList<Animator> animators = new ArrayList<>();
        animators.add(ObjectAnimator.ofFloat(actionMode, View.ALPHA, 0.0f, 1.0f));
        if (hidingViews != null) {
            for (int a = 0; a < hidingViews.length; a++) {
                if (hidingViews[a] != null) {
                    animators.add(ObjectAnimator.ofFloat(hidingViews[a], View.ALPHA, 1.0f, 0.0f));
                }
            }
        }
        if (showingView != null) {
            animators.add(ObjectAnimator.ofFloat(showingView, View.ALPHA, 0.0f, 1.0f));
        }
        if (translationView != null) {
            animators.add(ObjectAnimator.ofFloat(translationView, View.TRANSLATION_Y, translation));
            actionModeTranslationView = translationView;
        }
        actionModeExtraView = extraView;
        actionModeShowingView = showingView;
        actionModeHidingViews = hidingViews;
        if (occupyStatusBar && actionModeTop != null) {
            animators.add(ObjectAnimator.ofFloat(actionModeTop, View.ALPHA, 0.0f, 1.0f));
        }
        if (actionModeAnimation != null) {
            actionModeAnimation.cancel();
        }
        actionModeAnimation = new AnimatorSet();
        actionModeAnimation.playTogether(animators);
        actionModeAnimation.setDuration(200);
        actionModeAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                actionMode.setVisibility(VISIBLE);
                if (occupyStatusBar && actionModeTop != null) {
                    actionModeTop.setVisibility(VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (actionModeAnimation != null && actionModeAnimation.equals(animation)) {
                    actionModeAnimation = null;
                    if (titleTextView[0] != null) {
                        titleTextView[0].setVisibility(INVISIBLE);
                    }
                    if (subtitleTextView != null && !TextUtils.isEmpty(subtitleTextView.getText())) {
                        subtitleTextView.setVisibility(INVISIBLE);
                    }
                    if (menu != null) {
                        menu.setVisibility(INVISIBLE);
                    }
                    if (actionModeHidingViews != null) {
                        for (int a = 0; a < actionModeHidingViews.length; a++) {
                            if (actionModeHidingViews[a] != null) {
                                if (hideView == null || a >= hideView.length || hideView[a]) {
                                    actionModeHidingViews[a].setVisibility(INVISIBLE);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                if (actionModeAnimation != null && actionModeAnimation.equals(animation)) {
                    actionModeAnimation = null;
                }
            }
        });
        actionModeAnimation.start();
        if (backButtonImageView != null) {
            Drawable drawable = backButtonImageView.getDrawable();
            if (drawable instanceof BackDrawable) {
                ((BackDrawable) drawable).setRotation(1, true);
            }
            backButtonImageView.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_MODE_SELECTOR_COLOR));
        }
    }

    public void hideActionMode() {
        if (actionMode == null || !actionModeVisible) {
            return;
        }
        actionMode.hideAllPopupMenus();
        actionModeVisible = false;
        ArrayList<Animator> animators = new ArrayList<>();
        animators.add(ObjectAnimator.ofFloat(actionMode, View.ALPHA, 0.0f));
        if (actionModeHidingViews != null) {
            for (int a = 0; a < actionModeHidingViews.length; a++) {
                if (actionModeHidingViews != null) {
                    actionModeHidingViews[a].setVisibility(VISIBLE);
                    animators.add(ObjectAnimator.ofFloat(actionModeHidingViews[a], View.ALPHA, 1.0f));
                }
            }
        }
        if (actionModeTranslationView != null) {
            animators.add(ObjectAnimator.ofFloat(actionModeTranslationView, View.TRANSLATION_Y, 0.0f));
            actionModeTranslationView = null;
        }
        if (actionModeShowingView != null) {
            animators.add(ObjectAnimator.ofFloat(actionModeShowingView, View.ALPHA, 0.0f));
        }
        if (occupyStatusBar && actionModeTop != null) {
            animators.add(ObjectAnimator.ofFloat(actionModeTop, View.ALPHA, 0.0f));
        }
        if (actionModeAnimation != null) {
            actionModeAnimation.cancel();
        }
        actionModeAnimation = new AnimatorSet();
        actionModeAnimation.playTogether(animators);
        actionModeAnimation.setDuration(200);
        actionModeAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (actionModeAnimation != null && actionModeAnimation.equals(animation)) {
                    actionModeAnimation = null;
                    actionMode.setVisibility(INVISIBLE);
                    if (occupyStatusBar && actionModeTop != null) {
                        actionModeTop.setVisibility(INVISIBLE);
                    }
                    if (actionModeExtraView != null) {
                        actionModeExtraView.setVisibility(INVISIBLE);
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                if (actionModeAnimation != null && actionModeAnimation.equals(animation)) {
                    actionModeAnimation = null;
                }
            }
        });
        actionModeAnimation.start();
        if (!isSearchFieldVisible) {
            if (titleTextView[0] != null) {
                titleTextView[0].setVisibility(VISIBLE);
            }
            if (subtitleTextView != null && !TextUtils.isEmpty(subtitleTextView.getText())) {
                subtitleTextView.setVisibility(VISIBLE);
            }
        }
        if (menu != null) {
            menu.setVisibility(VISIBLE);
        }
        if (backButtonImageView != null) {
            Drawable drawable = backButtonImageView.getDrawable();
            if (drawable instanceof BackDrawable) {
                ((BackDrawable) drawable).setRotation(0, true);
            }
            backButtonImageView.setBackgroundDrawable(Theme.createSelectorDrawable(itemsBackgroundColor));
        }
    }
    public void showActionModeTop() {
        if (occupyStatusBar && actionModeTop == null) {
            actionModeTop = new View(getContext());
            actionModeTop.setBackgroundColor(0x99000000);
            addView(actionModeTop);
            LayoutParams layoutParams = (LayoutParams) actionModeTop.getLayoutParams();
            layoutParams.height = AndroidUtilities.statusBarHeight;
            layoutParams.width = LayoutHelper.MATCH_PARENT;
            layoutParams.gravity = Gravity.TOP | Gravity.LEFT;
            actionModeTop.setLayoutParams(layoutParams);
        }
    }

    public boolean isActionModeShowed() {
        return actionMode != null && actionModeVisible;
    }

    public void onSearchFieldVisibilityChanged(boolean visible) {
        isSearchFieldVisible = visible;
        if (titleTextView[0] != null) {
            titleTextView[0].setVisibility(visible ? INVISIBLE : VISIBLE);
        }
        if (subtitleTextView != null && !TextUtils.isEmpty(subtitleTextView.getText())) {
            subtitleTextView.setVisibility(visible ? INVISIBLE : VISIBLE);
        }
        Drawable drawable = backButtonImageView.getDrawable();
        if (drawable instanceof MenuDrawable) {
            MenuDrawable menuDrawable = (MenuDrawable) drawable;
            menuDrawable.setRotateToBack(true);
            menuDrawable.setRotation(visible ? 1 : 0, true);
        }
    }

    public void setInterceptTouches(boolean value) {
        interceptTouches = value;
    }

    public void setExtraHeight(int value) {
        extraHeight = value;
    }

    public void closeSearchField() {
        closeSearchField(true);
    }

    public void closeSearchField(boolean closeKeyboard) {
        if (!isSearchFieldVisible || menu == null) {
            return;
        }
        menu.closeSearchField(closeKeyboard);
    }

    public void openSearchField(String text) {
        if (menu == null || text == null) {
            return;
        }
        menu.openSearchField(!isSearchFieldVisible, text);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int actionBarHeight = getCurrentActionBarHeight();
        int actionBarHeightSpec = MeasureSpec.makeMeasureSpec(actionBarHeight, MeasureSpec.EXACTLY);

        ignoreLayoutRequest = true;
        if (actionModeTop != null) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) actionModeTop.getLayoutParams();
            layoutParams.height = AndroidUtilities.statusBarHeight;
        }
        if (actionMode != null) {
            actionMode.setPadding(0, occupyStatusBar ? AndroidUtilities.statusBarHeight : 0, 0, 0);
        }
        ignoreLayoutRequest = false;

        setMeasuredDimension(width, actionBarHeight + (occupyStatusBar ? AndroidUtilities.statusBarHeight : 0) + extraHeight);

        int textLeft;
        if (backButtonImageView != null && backButtonImageView.getVisibility() != GONE) {
            backButtonImageView.measure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(54), MeasureSpec.EXACTLY), actionBarHeightSpec);
            textLeft = AndroidUtilities.dp(AndroidUtilities.isTablet() ? 80 : 72);
        } else {
            textLeft = AndroidUtilities.dp(AndroidUtilities.isTablet() ? 26 : 18);
        }

        if (menu != null && menu.getVisibility() != GONE) {
            int menuWidth;
            if (isSearchFieldVisible) {
                menuWidth = MeasureSpec.makeMeasureSpec(width - AndroidUtilities.dp(AndroidUtilities.isTablet() ? 74 : 66), MeasureSpec.EXACTLY);
            } else {
                menuWidth = MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST);
            }
            menu.measure(menuWidth, actionBarHeightSpec);
        }

        for (int i = 0; i < 2; i++) {
            if (titleTextView[0] != null && titleTextView[0].getVisibility() != GONE || subtitleTextView != null && subtitleTextView.getVisibility() != GONE) {
                int availableWidth = width - (menu != null ? menu.getMeasuredWidth() : 0) - AndroidUtilities.dp(16) - textLeft - titleRightMargin;

                if (((fromBottom && i == 0) || (!fromBottom && i == 1)) && overlayTitleAnimation && titleAnimationRunning) {
                    titleTextView[i].setTextSize(!AndroidUtilities.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 18 : 20);
                } else {
                    if (titleTextView[0] != null && titleTextView[0].getVisibility() != GONE && subtitleTextView != null && subtitleTextView.getVisibility() != GONE) {
                        if (titleTextView[i] != null) {
                            titleTextView[i].setTextSize(AndroidUtilities.isTablet() ? 20 : 18);
                        }
                        subtitleTextView.setTextSize(AndroidUtilities.isTablet() ? 16 : 14);
                    } else {
                        if (titleTextView[i] != null && titleTextView[i].getVisibility() != GONE) {
                            titleTextView[i].setTextSize(!AndroidUtilities.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 18 : 20);
                        }
                        if (subtitleTextView != null && subtitleTextView.getVisibility() != GONE) {
                            subtitleTextView.setTextSize(!AndroidUtilities.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 14 : 16);
                        }
                    }
                }

                if (titleTextView[i] != null && titleTextView[i].getVisibility() != GONE) {
                    CharSequence text = titleTextView[i].getText();
                    titleTextView[i].setPivotX(titleTextView[i].getTextPaint().measureText(text, 0, text.length()) / 2f);
                    titleTextView[i].setPivotY((AndroidUtilities.dp(24) >> 1));
                    titleTextView[i].measure(MeasureSpec.makeMeasureSpec(availableWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(24), MeasureSpec.AT_MOST));
                }
                if (subtitleTextView != null && subtitleTextView.getVisibility() != GONE) {
                    subtitleTextView.measure(MeasureSpec.makeMeasureSpec(availableWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(20), MeasureSpec.AT_MOST));
                }
            }
        }

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE || child == titleTextView[0] || child == titleTextView[1] || child == subtitleTextView || child == menu || child == backButtonImageView) {
                continue;
            }
            measureChildWithMargins(child, widthMeasureSpec, 0, MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY), 0);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int additionalTop = occupyStatusBar ? AndroidUtilities.statusBarHeight : 0;

        int textLeft;
        if (backButtonImageView != null && backButtonImageView.getVisibility() != GONE) {
            backButtonImageView.layout(0, additionalTop, backButtonImageView.getMeasuredWidth(), additionalTop + backButtonImageView.getMeasuredHeight());
            textLeft = AndroidUtilities.dp(AndroidUtilities.isTablet() ? 80 : 72);
        } else {
            textLeft = AndroidUtilities.dp(AndroidUtilities.isTablet() ? 26 : 18);
        }

        if (menu != null && menu.getVisibility() != GONE) {
            int menuLeft = isSearchFieldVisible ? AndroidUtilities.dp(AndroidUtilities.isTablet() ? 74 : 66) : (right - left) - menu.getMeasuredWidth();
            menu.layout(menuLeft, additionalTop, menuLeft + menu.getMeasuredWidth(), additionalTop + menu.getMeasuredHeight());
        }

        for (int i = 0; i < 2; i++) {
            if (titleTextView[i] != null && titleTextView[i].getVisibility() != GONE) {
                int textTop;
                if (((fromBottom && i == 0) || (!fromBottom && i == 1)) && overlayTitleAnimation && titleAnimationRunning) {
                    textTop = (getCurrentActionBarHeight() - titleTextView[i].getTextHeight()) / 2;
                } else {
                    if ((subtitleTextView != null && subtitleTextView.getVisibility() != GONE)) {
                        textTop = (getCurrentActionBarHeight() / 2 - titleTextView[i].getTextHeight()) / 2 + AndroidUtilities.dp(!AndroidUtilities.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 2 : 3);
                    } else {
                        textTop = (getCurrentActionBarHeight() - titleTextView[i].getTextHeight()) / 2;
                    }
                }
                titleTextView[i].layout(textLeft, additionalTop + textTop, textLeft + titleTextView[i].getMeasuredWidth(), additionalTop + textTop + titleTextView[i].getTextHeight());
            }
        }
        if (subtitleTextView != null && subtitleTextView.getVisibility() != GONE) {
            int textTop = getCurrentActionBarHeight() / 2 + (getCurrentActionBarHeight() / 2 - subtitleTextView.getTextHeight()) / 2 - AndroidUtilities.dp(!AndroidUtilities.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 1 : 1);
            subtitleTextView.layout(textLeft, additionalTop + textTop, textLeft + subtitleTextView.getMeasuredWidth(), additionalTop + textTop + subtitleTextView.getTextHeight());
        }

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE || child == titleTextView[0] || child == titleTextView[1] || child == subtitleTextView || child == menu || child == backButtonImageView) {
                continue;
            }

            LayoutParams lp = (LayoutParams) child.getLayoutParams();

            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            int childLeft;
            int childTop;

            int gravity = lp.gravity;
            if (gravity == -1) {
                gravity = Gravity.TOP | Gravity.LEFT;
            }

            final int absoluteGravity = gravity & Gravity.HORIZONTAL_GRAVITY_MASK;
            final int verticalGravity = gravity & Gravity.VERTICAL_GRAVITY_MASK;

            switch (absoluteGravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
                case Gravity.CENTER_HORIZONTAL:
                    childLeft = (right - left - width) / 2 + lp.leftMargin - lp.rightMargin;
                    break;
                case Gravity.RIGHT:
                    childLeft = right - width - lp.rightMargin;
                    break;
                case Gravity.LEFT:
                default:
                    childLeft = lp.leftMargin;
            }

            switch (verticalGravity) {
                case Gravity.CENTER_VERTICAL:
                    childTop = (bottom - top - height) / 2 + lp.topMargin - lp.bottomMargin;
                    break;
                case Gravity.BOTTOM:
                    childTop = (bottom - top) - height - lp.bottomMargin;
                    break;
                default:
                    childTop = lp.topMargin;
            }
            child.layout(childLeft, childTop, childLeft + width, childTop + height);
        }
    }
    

    public void onMenuButtonPressed() {
        if (menu != null) {
            menu.onMenuButtonPressed();
        }
    }

    protected void onPause() {
        if (menu != null) {
            menu.hideAllPopupMenus();
        }
    }
	
	boolean overlayTitleAnimationInProgress;

    public void setAllowOverlayTitle(boolean value) {
        allowOverlayTitle = value;
    }

    public void setTitleOverlayText(String title, int titleId, Runnable action) {
        if (!allowOverlayTitle || parentFragment.parentLayout == null) {
            return;
        }
        overlayTitleToSet[0] = title;
        overlayTitleToSet[1] = titleId;
        overlayTitleToSet[2] = action;
        if (overlayTitleAnimationInProgress) {
            return;
        }
        if (lastOverlayTitle == null && title == null || (lastOverlayTitle != null && lastOverlayTitle.equals(title))) {
            return;
        }
        lastOverlayTitle = title;

        CharSequence textToSet = title != null ? "Sketchub" : lastTitle;
        boolean ellipsize = false;
        if (title != null) {

            int index = TextUtils.indexOf(textToSet, "...");
            if (index >= 0) {
                SpannableString spannableString = SpannableString.valueOf(textToSet);
                ellipsizeSpanAnimator.wrap(spannableString, index);
                textToSet = spannableString;
                ellipsize = true;
            }
        }
        titleOverlayShown = title != null;
        if ((textToSet != null && titleTextView[0] == null) || getMeasuredWidth() == 0 || (titleTextView[0] != null && titleTextView[0].getVisibility() != View.VISIBLE)) {
            createTitleTextView(0);
            if (supportsHolidayImage) {
                titleTextView[0].invalidate();
                invalidate();
            }
            titleTextView[0].setText(textToSet);
            if (ellipsize) {
                ellipsizeSpanAnimator.addView(titleTextView[0]);
            } else {
                ellipsizeSpanAnimator.removeView(titleTextView[0]);
            }
        } else if (titleTextView[0] != null) {
            titleTextView[0].animate().cancel();
            if (titleTextView[1] != null) {
                titleTextView[1].animate().cancel();
            }
            if (titleTextView[1] == null) {
                createTitleTextView(1);
            }
            titleTextView[1].setText(textToSet);
            if (ellipsize) {
                ellipsizeSpanAnimator.addView(titleTextView[1]);
            }
            overlayTitleAnimationInProgress = true;
            SimpleTextView tmp = titleTextView[1];
            titleTextView[1] = titleTextView[0];
            titleTextView[0] = tmp;
            titleTextView[0].setAlpha(0);
            titleTextView[0].setTranslationY(-AndroidUtilities.dp(20));
            titleTextView[0].animate()
                    .alpha(1f)
                    .translationY(0)
                    .setDuration(220).start();
            ViewPropertyAnimator animator = titleTextView[1].animate()
                    .alpha(0);
            if (subtitleTextView == null) {
                animator.translationY(AndroidUtilities.dp(20));
            } else {
                animator.scaleY(0.7f).scaleX(0.7f);
            }
            animator.setDuration(220).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (titleTextView[1] != null && titleTextView[1].getParent() != null) {
                        ViewGroup viewGroup = (ViewGroup) titleTextView[1].getParent();
                        viewGroup.removeView(titleTextView[1]);
                    }
                    ellipsizeSpanAnimator.removeView(titleTextView[1]);
                    titleTextView[1] = null;
                    overlayTitleAnimationInProgress = false;
                    setTitleOverlayText((String) overlayTitleToSet[0], (int) overlayTitleToSet[1], (Runnable) overlayTitleToSet[2]);
                }
            }).start();
        }
        titleActionRunnable = action != null ? action : lastRunnable;
    }
    public boolean isSearchFieldVisible() {
        return isSearchFieldVisible;
    }

    public void setOccupyStatusBar(boolean value) {
        occupyStatusBar = value;
        if (actionMode != null) {
            actionMode.setPadding(0, occupyStatusBar ? AndroidUtilities.statusBarHeight : 0, 0, 0);
        }
    }

    public boolean getOccupyStatusBar() {
        return occupyStatusBar;
    }

    public void setItemsBackgroundColor(int color) {
        itemsBackgroundColor = color;
        if (backButtonImageView != null) {
            backButtonImageView
                    .setBackgroundDrawable(Theme.createBarSelectorDrawable(itemsBackgroundColor));
        }
    }

    public void setCastShadows(boolean value) {
        castShadows = value;
    }

    public boolean getCastShadows() {
        return castShadows;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event) || interceptTouches;
    }

    public static int getCurrentActionBarHeight() {
        if (AndroidUtilities.isTablet()) {
            return AndroidUtilities.dp(64);
        } else if (MoonMeetApplication.applicationContext.getResources()
                .getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return AndroidUtilities.dp(48);
        } else {
            return AndroidUtilities.dp(48);
        }
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }
}