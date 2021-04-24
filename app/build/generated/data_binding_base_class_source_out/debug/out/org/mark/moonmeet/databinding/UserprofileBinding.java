// Generated by view binder compiler. Do not edit!
package org.mark.moonmeet.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import org.mark.moonmeet.R;

public final class UserprofileBinding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final CoordinatorLayout Coordinator;

  @NonNull
  public final FloatingActionButton Fab;

  @NonNull
  public final TextView account;

  @NonNull
  public final LinearLayout allHolder;

  @NonNull
  public final CircleImageView avatar;

  @NonNull
  public final TextView bio;

  @NonNull
  public final LinearLayout bioHolder;

  @NonNull
  public final TextView bioInfo;

  @NonNull
  public final LinearLayout divider;

  @NonNull
  public final LinearLayout divider2;

  @NonNull
  public final LinearLayout divider3;

  @NonNull
  public final LinearLayout divider4;

  @NonNull
  public final ImageView imageview1;

  @NonNull
  public final ImageView imageview2;

  @NonNull
  public final LinearLayout linear1;

  @NonNull
  public final LinearLayout moonInfoHolder;

  @NonNull
  public final TextView name;

  @NonNull
  public final LinearLayout nameHolder;

  @NonNull
  public final TextView nameInfo;

  @NonNull
  public final TextView nameMoon;

  @NonNull
  public final LinearLayout nameStateHolder;

  @NonNull
  public final LinearLayout nsmLin;

  @NonNull
  public final TextView number;

  @NonNull
  public final LinearLayout numberHolder;

  @NonNull
  public final TextView numberInfo;

  @NonNull
  public final LinearLayout part1Holder;

  @NonNull
  public final NestedScrollView scroller;

  @NonNull
  public final TextView settingsText;

  @NonNull
  public final RecyclerView sharedRv;

  @NonNull
  public final TextView stateMoon;

  @NonNull
  public final TextView textview2;

  @NonNull
  public final TextView textview3;

  @NonNull
  public final LinearLayout topbar;

  private UserprofileBinding(@NonNull CoordinatorLayout rootView,
      @NonNull CoordinatorLayout Coordinator, @NonNull FloatingActionButton Fab,
      @NonNull TextView account, @NonNull LinearLayout allHolder, @NonNull CircleImageView avatar,
      @NonNull TextView bio, @NonNull LinearLayout bioHolder, @NonNull TextView bioInfo,
      @NonNull LinearLayout divider, @NonNull LinearLayout divider2, @NonNull LinearLayout divider3,
      @NonNull LinearLayout divider4, @NonNull ImageView imageview1, @NonNull ImageView imageview2,
      @NonNull LinearLayout linear1, @NonNull LinearLayout moonInfoHolder, @NonNull TextView name,
      @NonNull LinearLayout nameHolder, @NonNull TextView nameInfo, @NonNull TextView nameMoon,
      @NonNull LinearLayout nameStateHolder, @NonNull LinearLayout nsmLin, @NonNull TextView number,
      @NonNull LinearLayout numberHolder, @NonNull TextView numberInfo,
      @NonNull LinearLayout part1Holder, @NonNull NestedScrollView scroller,
      @NonNull TextView settingsText, @NonNull RecyclerView sharedRv, @NonNull TextView stateMoon,
      @NonNull TextView textview2, @NonNull TextView textview3, @NonNull LinearLayout topbar) {
    this.rootView = rootView;
    this.Coordinator = Coordinator;
    this.Fab = Fab;
    this.account = account;
    this.allHolder = allHolder;
    this.avatar = avatar;
    this.bio = bio;
    this.bioHolder = bioHolder;
    this.bioInfo = bioInfo;
    this.divider = divider;
    this.divider2 = divider2;
    this.divider3 = divider3;
    this.divider4 = divider4;
    this.imageview1 = imageview1;
    this.imageview2 = imageview2;
    this.linear1 = linear1;
    this.moonInfoHolder = moonInfoHolder;
    this.name = name;
    this.nameHolder = nameHolder;
    this.nameInfo = nameInfo;
    this.nameMoon = nameMoon;
    this.nameStateHolder = nameStateHolder;
    this.nsmLin = nsmLin;
    this.number = number;
    this.numberHolder = numberHolder;
    this.numberInfo = numberInfo;
    this.part1Holder = part1Holder;
    this.scroller = scroller;
    this.settingsText = settingsText;
    this.sharedRv = sharedRv;
    this.stateMoon = stateMoon;
    this.textview2 = textview2;
    this.textview3 = textview3;
    this.topbar = topbar;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static UserprofileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static UserprofileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.userprofile, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static UserprofileBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      CoordinatorLayout Coordinator = (CoordinatorLayout) rootView;

      id = R.id._fab;
      FloatingActionButton Fab = ViewBindings.findChildViewById(rootView, id);
      if (Fab == null) {
        break missingId;
      }

      id = R.id.account;
      TextView account = ViewBindings.findChildViewById(rootView, id);
      if (account == null) {
        break missingId;
      }

      id = R.id.all_holder;
      LinearLayout allHolder = ViewBindings.findChildViewById(rootView, id);
      if (allHolder == null) {
        break missingId;
      }

      id = R.id.avatar;
      CircleImageView avatar = ViewBindings.findChildViewById(rootView, id);
      if (avatar == null) {
        break missingId;
      }

      id = R.id.bio;
      TextView bio = ViewBindings.findChildViewById(rootView, id);
      if (bio == null) {
        break missingId;
      }

      id = R.id.bio_holder;
      LinearLayout bioHolder = ViewBindings.findChildViewById(rootView, id);
      if (bioHolder == null) {
        break missingId;
      }

      id = R.id.bio_info;
      TextView bioInfo = ViewBindings.findChildViewById(rootView, id);
      if (bioInfo == null) {
        break missingId;
      }

      id = R.id.divider;
      LinearLayout divider = ViewBindings.findChildViewById(rootView, id);
      if (divider == null) {
        break missingId;
      }

      id = R.id.divider2;
      LinearLayout divider2 = ViewBindings.findChildViewById(rootView, id);
      if (divider2 == null) {
        break missingId;
      }

      id = R.id.divider3;
      LinearLayout divider3 = ViewBindings.findChildViewById(rootView, id);
      if (divider3 == null) {
        break missingId;
      }

      id = R.id.divider4;
      LinearLayout divider4 = ViewBindings.findChildViewById(rootView, id);
      if (divider4 == null) {
        break missingId;
      }

      id = R.id.imageview1;
      ImageView imageview1 = ViewBindings.findChildViewById(rootView, id);
      if (imageview1 == null) {
        break missingId;
      }

      id = R.id.imageview2;
      ImageView imageview2 = ViewBindings.findChildViewById(rootView, id);
      if (imageview2 == null) {
        break missingId;
      }

      id = R.id.linear1;
      LinearLayout linear1 = ViewBindings.findChildViewById(rootView, id);
      if (linear1 == null) {
        break missingId;
      }

      id = R.id.moon_info_holder;
      LinearLayout moonInfoHolder = ViewBindings.findChildViewById(rootView, id);
      if (moonInfoHolder == null) {
        break missingId;
      }

      id = R.id.name;
      TextView name = ViewBindings.findChildViewById(rootView, id);
      if (name == null) {
        break missingId;
      }

      id = R.id.name_holder;
      LinearLayout nameHolder = ViewBindings.findChildViewById(rootView, id);
      if (nameHolder == null) {
        break missingId;
      }

      id = R.id.name_info;
      TextView nameInfo = ViewBindings.findChildViewById(rootView, id);
      if (nameInfo == null) {
        break missingId;
      }

      id = R.id.name_moon;
      TextView nameMoon = ViewBindings.findChildViewById(rootView, id);
      if (nameMoon == null) {
        break missingId;
      }

      id = R.id.name_state_holder;
      LinearLayout nameStateHolder = ViewBindings.findChildViewById(rootView, id);
      if (nameStateHolder == null) {
        break missingId;
      }

      id = R.id.nsm_lin;
      LinearLayout nsmLin = ViewBindings.findChildViewById(rootView, id);
      if (nsmLin == null) {
        break missingId;
      }

      id = R.id.number;
      TextView number = ViewBindings.findChildViewById(rootView, id);
      if (number == null) {
        break missingId;
      }

      id = R.id.number_holder;
      LinearLayout numberHolder = ViewBindings.findChildViewById(rootView, id);
      if (numberHolder == null) {
        break missingId;
      }

      id = R.id.number_info;
      TextView numberInfo = ViewBindings.findChildViewById(rootView, id);
      if (numberInfo == null) {
        break missingId;
      }

      id = R.id.part1_holder;
      LinearLayout part1Holder = ViewBindings.findChildViewById(rootView, id);
      if (part1Holder == null) {
        break missingId;
      }

      id = R.id.scroller;
      NestedScrollView scroller = ViewBindings.findChildViewById(rootView, id);
      if (scroller == null) {
        break missingId;
      }

      id = R.id.settings_text;
      TextView settingsText = ViewBindings.findChildViewById(rootView, id);
      if (settingsText == null) {
        break missingId;
      }

      id = R.id.shared_rv;
      RecyclerView sharedRv = ViewBindings.findChildViewById(rootView, id);
      if (sharedRv == null) {
        break missingId;
      }

      id = R.id.state_moon;
      TextView stateMoon = ViewBindings.findChildViewById(rootView, id);
      if (stateMoon == null) {
        break missingId;
      }

      id = R.id.textview2;
      TextView textview2 = ViewBindings.findChildViewById(rootView, id);
      if (textview2 == null) {
        break missingId;
      }

      id = R.id.textview3;
      TextView textview3 = ViewBindings.findChildViewById(rootView, id);
      if (textview3 == null) {
        break missingId;
      }

      id = R.id.topbar;
      LinearLayout topbar = ViewBindings.findChildViewById(rootView, id);
      if (topbar == null) {
        break missingId;
      }

      return new UserprofileBinding((CoordinatorLayout) rootView, Coordinator, Fab, account,
          allHolder, avatar, bio, bioHolder, bioInfo, divider, divider2, divider3, divider4,
          imageview1, imageview2, linear1, moonInfoHolder, name, nameHolder, nameInfo, nameMoon,
          nameStateHolder, nsmLin, number, numberHolder, numberInfo, part1Holder, scroller,
          settingsText, sharedRv, stateMoon, textview2, textview3, topbar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}