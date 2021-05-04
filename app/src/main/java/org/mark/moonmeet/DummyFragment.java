package org.mark.moonmeet;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import org.mark.moonmeet.ui.BaseFragment;

public class DummyFragment extends BaseFragment {

    @Override
    public View createView(Context context) {
        fragmentView = new FrameLayout(context);
        return fragmentView;
    }

    @Override
    protected void onTransitionAnimationEnd(boolean isOpen, boolean backward) {
        if(isOpen && !backward) {
            presentNextFragment();
        }
    }

    private void presentNextFragment() {
        Bundle args = new Bundle();
        args.putString("Country", ".");
        args.putString("Code", ".");
        presentFragment(new OtpActivity(args), false);
    }
}