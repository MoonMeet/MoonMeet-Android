package org.mark.moonmeet.utils;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;

import org.mark.moonmeet.ChatActivity;
import org.mark.moonmeet.R;

public class SwipeController extends ItemTouchHelper.Callback {

    private Context mContext;
    private ISwipeControllerActions mSwipeControllerActions;

    private android.graphics.drawable.Drawable mReplyIcon;
    private android.graphics.drawable.Drawable mReplyIconBackground;

    private androidx.recyclerview.widget.RecyclerView.ViewHolder mCurrentViewHolder;
    private View mView;

    private float mDx = 0f;

    private float mReplyButtonProgress = 0f;
    private long  mLastReplyButtonAnimationTime = 0;

    private boolean mSwipeBack = false;
    private boolean mIsVibrating = false;
    private boolean mStartTracking = false;

    private int mBackgroundColor = 0x20606060;

    private int mReplyBackgroundOffset = 18;

    private int mReplyIconXOffset = 12;
    private int mReplyIconYOffset = 11;

    public SwipeController(Context context, ISwipeControllerActions swipeControllerActions){
        mContext = context;
        mSwipeControllerActions = swipeControllerActions;

        mReplyIcon = ContextCompat.getDrawable(mContext, R.drawable.ic_reply);
        mReplyIconBackground = androidx.core.content.res.ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_round_shape,null);
    }


    public SwipeController(Context context, ISwipeControllerActions swipeControllerActions, int replyIcon, int replyIconBackground, int backgroundColor){
        mContext = context;
        mSwipeControllerActions = swipeControllerActions;

        mReplyIcon = mContext.getResources().getDrawable(replyIcon);
        mReplyIconBackground = mContext.getResources().getDrawable(replyIconBackground);
        mBackgroundColor = backgroundColor;
    }

    @Override
    public int getMovementFlags(@androidx.annotation.NonNull androidx.recyclerview.widget.RecyclerView recyclerView, @androidx.annotation.NonNull androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
        mView = viewHolder.itemView;

        return androidx.recyclerview.widget.ItemTouchHelper.Callback.makeMovementFlags(androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_IDLE, androidx.recyclerview.widget.ItemTouchHelper.LEFT);
    }

    @Override
    public boolean onMove(@androidx.annotation.NonNull androidx.recyclerview.widget.RecyclerView recyclerView, @androidx.annotation.NonNull androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder, @androidx.annotation.NonNull androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(@androidx.annotation.NonNull androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection){
        if (mSwipeBack){
            mSwipeBack = false;
            return 0;
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onChildDraw(@androidx.annotation.NonNull Canvas c, @androidx.annotation.NonNull androidx.recyclerview.widget.RecyclerView recyclerView, @androidx.annotation.NonNull androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){
        if (actionState == androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE){
            setTouchListener(recyclerView, viewHolder);
        }
        if (mView.getTranslationX() < convertToDp(130) || dX > mDx ){

            float width = getDisplayWidthPixels();
            float widthCenter = (width / 2) - 170;
            float offset = dX / width;

            float newX = widthCenter * offset;
            super.onChildDraw(c, recyclerView, viewHolder, newX, dY, actionState, isCurrentlyActive);
            mDx = newX;
            mStartTracking = true;
        }
        mCurrentViewHolder = viewHolder;
        drawReplyButton(c);
    }

    private void setTouchListener(androidx.recyclerview.widget.RecyclerView recyclerView, final androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder){
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP) {
                    mSwipeBack = true;
                } else {
                    mSwipeBack = false;
                }
                if (mSwipeBack) {
                    if (Math.abs(mView.getTranslationX()) >= convertToDp(35)) {
                        mSwipeControllerActions.onSwipePerformed(viewHolder.getAdapterPosition());
                    }
                }
                return false;
            }
        });
    }

    private int convertToDp(int pixels){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixels, mContext.getResources().getDisplayMetrics());
    }


    private void drawReplyButton(Canvas canvas){

        int width = (int)getDisplayWidthPixels();
        if (mCurrentViewHolder == null){
            return;
        }

        float translationX = -mView.getTranslationX();

        long newTime = System.currentTimeMillis();
        long dt = Math.min(17, newTime - mLastReplyButtonAnimationTime);
        mLastReplyButtonAnimationTime = newTime;
        boolean showing = false;
        if (translationX >= convertToDp(10)){
            showing = true;
        }
        if (showing){
            if (mReplyButtonProgress < 1.0f){
                mReplyButtonProgress += dt / 180.0f;
                if (mReplyButtonProgress > 1.0f){
                    mReplyButtonProgress = 1.0f;
                } else {
                    mView.invalidate();
                }
            }
        } else if (translationX <= 0.0f){
            mReplyButtonProgress = 0f;
            mStartTracking = false;
            mIsVibrating = false;
        } else {
            if (mReplyButtonProgress > 0.0f){
                mReplyButtonProgress -= dt / 180.0f;
                if (mReplyButtonProgress < 0.1f){
                    mReplyButtonProgress = 0f;
                }
            }
            mView.invalidate();
        }
        int alpha;
        float scale;
        if (showing){
            if (mReplyButtonProgress <= 0.8f){
                scale = 1.2f * (mReplyButtonProgress / 0.8f);
            } else{
                scale = 1.2f - 0.2f * ((mReplyButtonProgress - 0.8f) / 0.2f);
            }
            alpha = Math.min(255, 255 * ((int)(mReplyButtonProgress / 0.8f)));
        } else{
            scale = mReplyButtonProgress;
            alpha = Math.min(255, 255 * (int)mReplyButtonProgress);
        }
        mReplyIconBackground.setAlpha(alpha);
        mReplyIcon.setAlpha(alpha);
        if (mStartTracking){
            if (!mIsVibrating && -mView.getTranslationX() >= convertToDp(6)){
                mView.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
            }
            mIsVibrating = true;
        }

        int x;
        float y;
        //        if (-mView.getTranslationX() > convertToDp(30)){
        //            x = width - ((int)-mView.getTranslationX()) + 40;
        //        }else{
        //            x = (int)mView.getTranslationX() + 40;
        //        }
        x = width - ((int)-mView.getTranslationX()) + 40;

        y = mView.getTop() + ((float) mView.getMeasuredHeight() /2);
        mReplyIconBackground.setColorFilter(mBackgroundColor, PorterDuff.Mode.MULTIPLY);

        mReplyIconBackground.setBounds(new Rect(
                (int)(x - convertToDp(mReplyBackgroundOffset) * scale),
                (int)(y - convertToDp(mReplyBackgroundOffset) * scale),
                (int)(x + convertToDp(mReplyBackgroundOffset) * scale),
                (int)(y + convertToDp(mReplyBackgroundOffset) * scale)
        ));
        mReplyIconBackground.draw(canvas);

        mReplyIcon.setBounds(new Rect(
                (int)(x - convertToDp(mReplyIconXOffset) * scale),
                (int)(y - convertToDp(mReplyIconYOffset) * scale),
                (int)(x + convertToDp(mReplyIconXOffset) * scale),
                (int)(y + convertToDp(mReplyIconYOffset) * scale)
        ));
        mReplyIcon.draw(canvas);

        mReplyIconBackground.setAlpha(255);
        mReplyIcon.setAlpha(255);
    }
    public int getDisplayWidthPixels() {
        return mContext.getResources().getDisplayMetrics().widthPixels;
    }

    public int getDisplayHeightPixels() {
        return mContext.getResources().getDisplayMetrics().heightPixels;
    }
}