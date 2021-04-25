package org.mark.moonmeet;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import org.mark.moonmeet.ui.ActionBarLayout;
import org.mark.moonmeet.ui.BaseFragment;
import org.mark.moonmeet.utils.NotificationCenter;

import java.util.ArrayList;

public class BaseActivity extends Activity implements ActionBarLayout.ActionBarLayoutDelegate, NotificationCenter.NotificationCenterDelegate{

    private ActionBarLayout actionBarLayout;

    private final ArrayList<BaseFragment> mainFragmentStack = new ArrayList<>();

    @Override
    public void onAttachedToWindow() {
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.didChangeText);
        super.onAttachedToWindow();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout container = new FrameLayout(this);
        actionBarLayout = new ActionBarLayout(this);
        container.addView(actionBarLayout);
        actionBarLayout.init(mainFragmentStack);
        actionBarLayout.setDelegate(this);

        String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";

        if (checkCallingOrSelfPermission(
                READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= 23) {
                requestPermissions(new String[] {
                        READ_EXTERNAL_STORAGE
                }, 1);
                return;
            }
        }
        showContent();
        updateStatusBar();
        setContentView(container, new ViewGroup.LayoutParams(-1, -1));
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        showContent();
    }

    private void showContent() {

        // albumPickerActivity.setDelegate(mPhotoAlbumPickerActivityDelegate);
        actionBarLayout.presentFragment(new SecondBase(), false);
        //  actionBarLayout.presentFragment(new SettingsActivity());
        //actionBarLayout.presentFragment(pickerBottomLayout);
        // actionBarLayout.presentFragment(albumPickerActivity,false,true,true);
    }


    @Override
    public boolean onPreIme() {
        return false;
    }

    @Override
    public boolean needPresentFragment(BaseFragment fragment, boolean removeLast, boolean forceWithoutAnimation, ActionBarLayout layout) {
        return true;
    }

    @Override
    public boolean needAddFragmentToStack(BaseFragment fragment, ActionBarLayout layout) {
        return true;
    }

    @Override
    public boolean needCloseLastFragment(ActionBarLayout layout) {
        if(layout.fragmentsStack.size() <= 1){
            finish();
            return false;
        }
        return true;
    }

    @Override
    public void onRebuildAllFragments(ActionBarLayout layout, boolean all) {

    }

    @Override
    public void onBackPressed() {
        actionBarLayout.onBackPressed();
    }

    private void updateStatusBar() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @Override
    protected void onDestroy() {
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didChangeText);
        super.onDestroy();
    }

    @Override
    public void didReceivedNotification(int id, Object... args) {
        if(id == NotificationCenter.didChangeText){
            updateStatusBar();
        }
    }
}
