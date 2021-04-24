package org.mark.moonmeet;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.mark.moonmeet.components.LayoutHelper;
import org.mark.moonmeet.ui.ActionBar;
import org.mark.moonmeet.ui.AdjustPanLayoutHelper;
import org.mark.moonmeet.ui.BackDrawable;
import org.mark.moonmeet.ui.BaseFragment;
import org.mark.moonmeet.utils.AndroidUtilities;
import org.mark.moonmeet.utils.CubicBezierInterpolator;
import org.mark.moonmeet.utils.NotificationCenter;

public class HomeActivity extends BaseFragment implements NotificationCenter.NotificationCenterDelegate {
    private TextView intro;
    private IntroFrame introFrame;
    private ImageView themeSwitchImage;
    private LinearLayout container;

    private AdjustPanLayoutHelper adjustPanLayoutHelper;
    private boolean isLight = true;


    @Override
    protected ActionBar createActionBar(Context context){
        ActionBar actionBar = new ActionBar(context);
        actionBar.setBackgroundColor(Color.TRANSPARENT);
        actionBar.setOccupyStatusBar(true);
        actionBar.setAddToContainer(false);
        actionBar.setBackButtonDrawable(new BackDrawable(false));
        return actionBar;
    }
    @Override
    public boolean onFragmentCreate(){
        return true;
    }


    public void setAdjustPanLayoutHelper(AdjustPanLayoutHelper ad){
        adjustPanLayoutHelper = ad;
    }

    @Override
    public View createView(Context context){

        NotificationCenter.getInstance().addObserver(this, NotificationCenter.didChangeText);

        actionBar.setOccupyStatusBar(true);
        actionBar.setCastShadows(false);
        actionBar.setBackgroundColor(Color.parseColor("#6200EE"));
        //actionBar.setBackButtonDrawable(new BackDrawable(false));

        fragmentView = new FrameLayout(context);


        themeSwitchImage = new ImageView(context);
        //((ViewGroup)fragmentView).addView(themeSwitchImage,0,LayoutHelper.createFrame(-1,-1));
        themeSwitchImage.setVisibility(View.GONE);

        container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setGravity(Gravity.CENTER);
        //fragmentView.setBackgroundColor(Color.parseColor("#6200ee"));

        introFrame = new IntroFrame(context);

        ((ViewGroup)fragmentView).addView(container,0, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT, Gravity.CENTER));

        //	((ViewGroup)container).addView(actionBar);

        ((ViewGroup)container).addView(introFrame,0, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT, Gravity.CENTER));


        fragmentView.setBackgroundColor( Color.parseColor("#6200ee"));

        fragmentView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean  onTouch(View v, MotionEvent event){

                return true;
            }
        });


        return fragmentView;


    }

    private class IntroFrame extends LinearLayout{
        private TextView introText;
        private Button button;
        public IntroFrame(Context context){
            super (context);
            setOrientation(LinearLayout.VERTICAL);
            introText = new TextView(context);
            introText.setText("Tap the button to open the third activity");
            introText.setTextColor(Color.parseColor("#ffffff"));
            introText.setGravity(Gravity.CENTER);

            addView(introText);
            final SecondBase chatActivity = new SecondBase();

            LinearLayout layout = new LinearLayout(context);
            layout.setBackgroundColor(0xff462857);
            container.addView(layout, LayoutHelper.createLinear(50,50));

            //chatActivity.setSharedView(layout);

            button = new Button(context);
            button.setText("Open activity");

            button.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v){
                    presentFragment(chatActivity);
                    return true;
                }
            });

            button.setFocusable(true);
            button.setOnTouchListener(new View.OnTouchListener(){
                                          @Override
                                          public boolean onTouch(View v, MotionEvent ev){
                                              if(ev.getAction() == MotionEvent.ACTION_DOWN){
                                                  button.requestFocus();
                                                  presentFragmentAsPreview(chatActivity);
                                                  button.requestFocus();
                                              }else if(ev.getAction() == MotionEvent.ACTION_UP){
                                                  chatActivity.finishPreviewFragment();
                                                  AndroidUtilities.showToast("Finger moved up");
                                                  presentFragment(chatActivity);
                                              }
                                              return false;
                                          }
                                      }
            );

            addView(button);

            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    presentFragment(chatActivity);
                    //switchTheme();
                }
            });
        }
        @Override
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
            int width = MeasureSpec.getSize(widthMeasureSpec);

            introText.measure(width, MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(42), MeasureSpec.EXACTLY));
            button.measure(width, MeasureSpec.getSize(heightMeasureSpec));
            setMeasuredDimension(width, MeasureSpec.getSize(heightMeasureSpec));

        }

        public void setTitleText(String text){
            introText.setText(text);
        }
        public Button getButton(){
            return this.button;
        }
    }

    @Override
    public void didReceivedNotification (int id, Object... args){
        if(id == NotificationCenter.didChangeText){
            introFrame.setTitleText((String)args[0]);
        }
    }

    private void switchTheme(){

        try{
            int midH =(int) AndroidUtilities.getScreenHeight();
            int midW =(int) AndroidUtilities.getScreenWidth();
            int[] pos = new int[2];
            introFrame.getButton().getLocationInWindow(pos);
            pos[0] = midW / 2;
            pos[1] = midH / 2;

            int w = midW;
            int h = midH;//fragmentView.getMeasuredHeight();

            final Bitmap bitmap = Bitmap.createBitmap(container.getMeasuredWidth(), container.getMeasuredHeight(), Bitmap.Config.ARGB_8888 );
            final Canvas canvas = new Canvas(bitmap);
            container.draw(canvas);
            ((ViewGroup)fragmentView).addView(themeSwitchImage);
            themeSwitchImage.setImageBitmap(bitmap);
            themeSwitchImage.setVisibility(View.VISIBLE);

            float finalRadius = (float) Math.max(Math.sqrt((w - pos[0]) * (w - pos[0]) + (h - pos[1]) * (h - pos[1])), Math.sqrt(pos[0] * pos[0] + (h - pos[1]) * (h - pos[1])));


            Animator anim;
            if(isLight){
                container.setBackgroundColor( Color.parseColor("#6200ee"));

                anim = ViewAnimationUtils.createCircularReveal(container, pos[0], pos[1], 0, finalRadius);

            }else{
                container.setBackgroundColor(Color.parseColor("#f05252"));

                anim = ViewAnimationUtils.createCircularReveal(themeSwitchImage, pos[0], pos[1], finalRadius, 0);

            }
            anim.setDuration(400);
            anim.setInterpolator(CubicBezierInterpolator.EASE_BOTH);
            anim.addListener(new AnimatorListenerAdapter(){
                @Override
                public void onAnimationEnd(Animator animator){
                    themeSwitchImage.setImageDrawable(null);
                    themeSwitchImage.setVisibility(View.GONE);
                    ((ViewGroup)fragmentView).removeView(themeSwitchImage);
                }
            });
            anim.start();
        }catch(Throwable ignore){

        }

        //introFrame.setTitleText("isLight " + isLight);


        //container.setBackgroundColor(isLight? 1 : 0);
        //	container.setBackgroundColor(isLight? Color.parseColor("#f05252") : Color.parseColor("#6200ee"));
        if(isLight){
            isLight = false;
        }else{
            isLight = true;
        }

    }
}
