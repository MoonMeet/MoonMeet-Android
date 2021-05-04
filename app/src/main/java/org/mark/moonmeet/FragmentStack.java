package org.mark.moonmeet;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import org.mark.moonmeet.ui.ActionBarLayout;
import org.mark.moonmeet.ui.BaseFragment;
import org.mark.moonmeet.utils.NotificationCenter;

import java.util.ArrayList;

public class FragmentStack extends AppCompatActivity implements ActionBarLayout.ActionBarLayoutDelegate, NotificationCenter.NotificationCenterDelegate {

    private final ArrayList<BaseFragment> mainFragmentStack = new ArrayList<>();
    private ActionBarLayout actionBarLayout;
    private FrameLayout fragmentView;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentView = new FrameLayout(this);
        actionBarLayout = new ActionBarLayout(this);
        fragmentView.addView(actionBarLayout);
        actionBarLayout.init(mainFragmentStack);
        actionBarLayout.setDelegate(this);
        setContentView(fragmentView, new FrameLayout.LayoutParams(-1, -1));

    }

    @Override
    public void onStart() {
        super.onStart();
        presentNextFragment();
    }

    private void presentNextFragment() {
        actionBarLayout.presentFragment(new DummyFragment(), false, true, true, false);
    }

    @Override
    public void onBackPressed() {
        actionBarLayout.onBackPressed();
        super.onBackPressed();
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
    public void didReceivedNotification(int id, Object... args) {

    }
}