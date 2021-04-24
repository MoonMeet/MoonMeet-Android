// Generated by view binder compiler. Do not edit!
package org.mark.moonmeet.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import org.mark.moonmeet.R;

public final class WaitcBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout background;

  @NonNull
  public final LinearLayout linearBase;

  @NonNull
  public final ProgressBar prog;

  private WaitcBinding(@NonNull LinearLayout rootView, @NonNull LinearLayout background,
      @NonNull LinearLayout linearBase, @NonNull ProgressBar prog) {
    this.rootView = rootView;
    this.background = background;
    this.linearBase = linearBase;
    this.prog = prog;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static WaitcBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static WaitcBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
      boolean attachToParent) {
    View root = inflater.inflate(R.layout.waitc, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static WaitcBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.background;
      LinearLayout background = ViewBindings.findChildViewById(rootView, id);
      if (background == null) {
        break missingId;
      }

      id = R.id.linear_base;
      LinearLayout linearBase = ViewBindings.findChildViewById(rootView, id);
      if (linearBase == null) {
        break missingId;
      }

      id = R.id.prog;
      ProgressBar prog = ViewBindings.findChildViewById(rootView, id);
      if (prog == null) {
        break missingId;
      }

      return new WaitcBinding((LinearLayout) rootView, background, linearBase, prog);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}