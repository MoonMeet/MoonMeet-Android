package org.mark.moonmeet;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.mark.moonmeet.components.LayoutHelper;
import org.mark.moonmeet.ui.ActionBarMenu;
import org.mark.moonmeet.ui.ActionBarMenuItem;
import org.mark.moonmeet.ui.AdjustPanLayoutHelper;
import org.mark.moonmeet.ui.BackDrawable;
import org.mark.moonmeet.ui.BaseFragment;

public class SecondBase extends BaseFragment {

        private HomeActivity homeActivity;
        private TermsandprivacyActivity t;
        private FrameLayout contentView;
        private ActionBarMenuItem searchItem;

        @Override
        public View createView(Context context){
            ActionBarMenu menu = actionBar.createMenu();

            searchItem = menu.addItem(1,
                    R.drawable.ghost).setIsSearchField(true);
            searchItem.setSearchFieldHint("Search for messages");


            actionBar.setOccupyStatusBar(true);
            actionBar.setBackButtonDrawable(new BackDrawable(false));

            actionBar.setItemsColor(0xffffffff, false);
            fragmentView = new FrameLayout(context){
                AdjustPanLayoutHelper adjustPanLayoutHelper = new AdjustPanLayoutHelper(this){
                    @Override
                    public void onTransitionStart(boolean keybkardVisible){

                    }

                    @Override
                    public void onPanTranslationUpdate(float y, float progress, boolean keyboardVisible){
                        contentView.setTranslationY(y);
                        actionBar.setTranslationY(y);
                        setFragmentPanTranslationOffset((int)y);
                    }

                    @Override
                    public boolean heightAnimationEnabled(){
                        if (inPreviewMode || inBubbleMode) {
                            return false;
                        }
                        return true;
                    }
                };


                @Override
                protected void onAttachedToWindow() {
                    super.onAttachedToWindow();
                    adjustPanLayoutHelper.onAttach();
                    homeActivity.setAdjustPanLayoutHelper(adjustPanLayoutHelper);
                }


            };

            contentView = (FrameLayout) fragmentView;

            fragmentView.setOnTouchListener((view, ev) -> true);
            actionBar.setBackgroundColor(0xfffe6262);
            actionBar.setTitle("BaseFragment example");
            homeActivity = new HomeActivity();
            t = new TermsandprivacyActivity();
            TextView intro = new TextView(context);
            intro.setText("Tap here to open the next fragment");
            intro.setTextSize(18);
            intro.setOnClickListener(v -> presentFragment(new TermsandprivacyActivity()));
            intro.setOnLongClickListener(v -> {
                presentFragment(homeActivity);
                return true;
            });
            ((ViewGroup)fragmentView).addView(intro, LayoutHelper.createFrame(-2,-2, Gravity.CENTER));
            //EditText editText = new EditText(context);

            //((ViewGroup)fragmentView).addView(editText, LayoutHelper.createFrame(-1,-2, Gravity.CENTER));
            return fragmentView;
        }

// removed on activity result
    }
