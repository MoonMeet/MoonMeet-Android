package org.mark.moonmeet;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import org.mark.moonmeet.ui.BaseFragment;
import org.mark.moonmeet.utils.GridSpacingItemDecoration;
import org.mark.moonmeet.utils.NotificationCenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserprofileActivity extends BaseFragment {

    private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();

    private FloatingActionButton _fab;
    private String uid = "";
    private String User_Username = "";
    private String User_Avatar = "";
    private String User_Firstname = "";
    private String User_Lastname = "";
    private String User_Bio = "";
    private String User_Uid = "";
    private String User_LastSeen = "";
    private String User_Phone = "";
    private String User_PhoneStatus = "";
    private double time = 0;
    private double deference = 0;
    private HashMap<String, Object> Seen_Push = new HashMap<>();
    private String fname = "";
    private String savePath = "";
    private String myMoonMeetColor = "";
    private String Chatcopy = "";
    private String Chatroom = "";

    private ArrayList<HashMap<String, Object>> SharedMedia = new ArrayList<>();

    private LinearLayout topbar;
    private ImageView imageview1;
    private TextView settings_text;
    private LinearLayout linear1;
    private ImageView imageview2;
    private NestedScrollView scroller;
    private LinearLayout all_holder;
    private LinearLayout moon_info_holder;
    private LinearLayout divider;
    private LinearLayout part1_holder;
    private LinearLayout divider4;
    private LinearLayout nsm_lin;
    private RecyclerView shared_rv;
    private CircleImageView avatar;
    private LinearLayout name_state_holder;
    private TextView name_moon;
    private TextView state_moon;
    private TextView account;
    private LinearLayout number_holder;
    private LinearLayout divider2;
    private LinearLayout name_holder;
    private LinearLayout divider3;
    private LinearLayout bio_holder;
    private TextView number;
    private TextView number_info;
    private TextView name;
    private TextView name_info;
    private TextView bio;
    private TextView bio_info;
    private TextView textview2;
    private TextView textview3;

    private FirebaseAuth Fauth;
    private OnCompleteListener<Void> Fauth_updateEmailListener;
    private OnCompleteListener<Void> Fauth_updatePasswordListener;
    private OnCompleteListener<Void> Fauth_emailVerificationSentListener;
    private OnCompleteListener<Void> Fauth_deleteUserListener;
    private OnCompleteListener<Void> Fauth_updateProfileListener;
    private OnCompleteListener<AuthResult> Fauth_phoneAuthListener;
    private OnCompleteListener<AuthResult> Fauth_googleSignInListener;
    private OnCompleteListener<AuthResult> _Fauth_create_user_listener;
    private OnCompleteListener<AuthResult> _Fauth_sign_in_listener;
    private OnCompleteListener<Void> _Fauth_reset_password_listener;
    private DatabaseReference users = _firebase.getReference("users");
    private ChildEventListener _users_child_listener;
    private DatabaseReference Chat1 = _firebase.getReference("Chatroom");
    private ChildEventListener _Chat1_child_listener;
    private DatabaseReference Chat2 = _firebase.getReference("Chatcopy");
    private ChildEventListener _Chat2_child_listener;
    private Calendar cal = Calendar.getInstance();
    private Intent toContinue = new Intent();

    public UserprofileActivity(Bundle args) {
        super(args);
    }

    @Override
    public View createView(Context context) {
        fragmentView = new FrameLayout(context);
        actionBar.setAddToContainer(false);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.userprofile, ((ViewGroup) fragmentView), false);
        ((ViewGroup) fragmentView).addView(view);
        initialize(context);
        com.google.firebase.FirebaseApp.initializeApp(getParentActivity());
        initializeLogic();
        return fragmentView;
    }

    private void initialize(Context context) {
        _fab = (FloatingActionButton) findViewById(R.id._fab);
        topbar = (LinearLayout) findViewById(R.id.topbar);
        imageview1 = (ImageView) findViewById(R.id.imageview1);
        settings_text = (TextView) findViewById(R.id.settings_text);
        linear1 = (LinearLayout) findViewById(R.id.linear1);
        imageview2 = (ImageView) findViewById(R.id.imageview2);
        scroller = (NestedScrollView) findViewById(R.id.scroller);
        all_holder = (LinearLayout) findViewById(R.id.all_holder);
        moon_info_holder = (LinearLayout) findViewById(R.id.moon_info_holder);
        divider = (LinearLayout) findViewById(R.id.divider);
        part1_holder = (LinearLayout) findViewById(R.id.part1_holder);
        divider4 = (LinearLayout) findViewById(R.id.divider4);
        nsm_lin = (LinearLayout) findViewById(R.id.nsm_lin);
        shared_rv = (RecyclerView) findViewById(R.id.shared_rv);
        avatar = (CircleImageView) findViewById(R.id.avatar);
        name_state_holder = (LinearLayout) findViewById(R.id.name_state_holder);
        name_moon = (TextView) findViewById(R.id.name_moon);
        state_moon = (TextView) findViewById(R.id.state_moon);
        account = (TextView) findViewById(R.id.account);
        number_holder = (LinearLayout) findViewById(R.id.number_holder);
        divider2 = (LinearLayout) findViewById(R.id.divider2);
        name_holder = (LinearLayout) findViewById(R.id.name_holder);
        divider3 = (LinearLayout) findViewById(R.id.divider3);
        bio_holder = (LinearLayout) findViewById(R.id.bio_holder);
        number = (TextView) findViewById(R.id.number);
        number_info = (TextView) findViewById(R.id.number_info);
        name = (TextView) findViewById(R.id.name);
        name_info = (TextView) findViewById(R.id.name_info);
        bio = (TextView) findViewById(R.id.bio);
        bio_info = (TextView) findViewById(R.id.bio_info);
        textview2 = (TextView) findViewById(R.id.textview2);
        textview3 = (TextView) findViewById(R.id.textview3);
        Fauth = FirebaseAuth.getInstance();
        _fab.setOnClickListener(_view -> finishFragment());

        _users_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childKey.equals(uid)) {
                    if (_childValue.containsKey("avatar") && (_childValue.containsKey("firstname") && (_childValue.containsKey("lastname") && (_childValue.containsKey("username") && (_childValue.containsKey("bio") && (_childValue.containsKey("uid") && (_childValue.containsKey("last_seen") && _childValue.containsKey("phone")))))))) {
                        User_Username = _childValue.get("username").toString();
                        User_Avatar = _childValue.get("avatar").toString();
                        User_Firstname = _childValue.get("firstname").toString();
                        User_Lastname = _childValue.get("lastname").toString();
                        User_Bio = _childValue.get("bio").toString();
                        User_Uid = _childValue.get("uid").toString();
                        User_LastSeen = _childValue.get("last_seen").toString();
                        User_Phone = _childValue.get("phone").toString();
                        User_PhoneStatus = _childValue.get("phone_status").toString();
                        if (User_PhoneStatus.equals("none")) {
                            number.setText(User_Phone);
                        } else {
                            number.setText("Unknown");
                        }
                        if (User_LastSeen.equals("private")) {
                            state_moon.setText("Last seen recently");
                        } else {
                            cal = Calendar.getInstance();
                            Seen_Push = new HashMap<>();
                            Seen_Push.put("last_seen", String.valueOf((long) (cal.getTimeInMillis())));
                            users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(Seen_Push);
                            Seen_Push.clear();
                            _NewTime(Double.parseDouble(User_LastSeen), state_moon);
                        }
                        if (User_Bio.equals("")) {
                            bio.setText("None");
                            bio_info.setText(User_Firstname.concat(" ".concat("Doesn't have a bio yet.")));
                        } else {
                            bio.setText(User_Bio);
                        }
                        name_moon.setText(User_Firstname.concat(" ".concat(User_Lastname)));
                        name.setText(User_Username);
                        com.bumptech.glide.Glide.with(getApplicationContext())
                                .load(User_Avatar)
                                .override(1024, 1024)
                                .into(avatar);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childKey.equals(uid)) {
                    if (_childValue.containsKey("avatar") && (_childValue.containsKey("firstname") && (_childValue.containsKey("lastname") && (_childValue.containsKey("username") && (_childValue.containsKey("bio") && (_childValue.containsKey("uid") && (_childValue.containsKey("last_seen") && _childValue.containsKey("phone")))))))) {
                        User_Username = _childValue.get("username").toString();
                        User_Avatar = _childValue.get("avatar").toString();
                        User_Firstname = _childValue.get("firstname").toString();
                        User_Lastname = _childValue.get("lastname").toString();
                        User_Bio = _childValue.get("bio").toString();
                        User_Uid = _childValue.get("uid").toString();
                        User_LastSeen = _childValue.get("last_seen").toString();
                        User_Phone = _childValue.get("phone").toString();
                        User_Phone = _childValue.get("phone_status").toString();
                        if (User_PhoneStatus.equals("none")) {
                            number.setText(User_Phone);
                        } else {
                            number.setText("Unknown");
                        }
                        if (User_LastSeen.equals("private")) {
                            state_moon.setText("Last seen recently");
                        } else {
                            _NewTime(Double.parseDouble(User_LastSeen), state_moon);
                        }
                        if (User_Bio.equals("")) {
                            bio.setText("None");
                            bio_info.setText(User_Firstname.concat(" ".concat("Doesn't have a bio yet.")));
                        } else {
                            bio.setText(User_Bio);
                        }
                        name_moon.setText(User_Firstname.concat(" ".concat(User_Lastname)));
                        name.setText(User_Username);
                        com.bumptech.glide.Glide.with(getApplicationContext())
                                .load(User_Avatar)
                                .override(1024, 1024)
                                .into(avatar);
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot _param1, String _param2) {

            }

            @Override
            public void onChildRemoved(DataSnapshot _param1) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);

            }

            @Override
            public void onCancelled(DatabaseError _param1) {
                final int _errorCode = _param1.getCode();
                final String _errorMessage = _param1.getMessage();

            }
        };
        users.addChildEventListener(_users_child_listener);

        _Chat1_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childValue.containsKey("type")) {
                    if (_childValue.containsKey("image") && (_childValue.containsKey("firstname") && (_childValue.containsKey("lastname") || _childValue.containsKey("time")))) {
                        SharedMedia.add(_childValue);
                        if (SharedMedia.size() > 0) {
                            shared_rv.setAdapter(new Shared_rvAdapter(SharedMedia));
                            nsm_lin.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);

            }

            @Override
            public void onChildMoved(DataSnapshot _param1, String _param2) {

            }

            @Override
            public void onChildRemoved(DataSnapshot _param1) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);

            }

            @Override
            public void onCancelled(DatabaseError _param1) {
                final int _errorCode = _param1.getCode();
                final String _errorMessage = _param1.getMessage();

            }
        };
        Chat1.addChildEventListener(_Chat1_child_listener);

        _Chat2_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);

            }

            @Override
            public void onChildChanged(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);

            }

            @Override
            public void onChildMoved(DataSnapshot _param1, String _param2) {

            }

            @Override
            public void onChildRemoved(DataSnapshot _param1) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);

            }

            @Override
            public void onCancelled(DatabaseError _param1) {
                final int _errorCode = _param1.getCode();
                final String _errorMessage = _param1.getMessage();

            }
        };
        Chat2.addChildEventListener(_Chat2_child_listener);

        Fauth_updateEmailListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

            }
        };

        Fauth_updatePasswordListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

            }
        };

        Fauth_emailVerificationSentListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

            }
        };

        Fauth_deleteUserListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

            }
        };

        Fauth_phoneAuthListener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                final boolean _success = task.isSuccessful();
                final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";

            }
        };

        Fauth_updateProfileListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

            }
        };

        Fauth_googleSignInListener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                final boolean _success = task.isSuccessful();
                final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";

            }
        };

        _Fauth_create_user_listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

            }
        };

        _Fauth_sign_in_listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

            }
        };

        _Fauth_reset_password_listener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> _param1) {
                final boolean _success = _param1.isSuccessful();

            }
        };
    }

    private void initializeLogic() {
        topbar.setElevation(2);
        // Getting data
        uid = getArguments().getString("uid");
        // Configure RecycerViewGridLayout Adapter
        shared_rv.setAdapter(new Shared_rvAdapter(SharedMedia));
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getParentActivity(), 3);

        shared_rv.setLayoutManager(mGridLayoutManager);

        int spanCount = 3; // 3 columns
        int spacing = 10; // 50px
        boolean includeEdge = false;
        shared_rv.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        // Database Configuration
        _DatabaseConfiguration();
        // Design
        _fab.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#FF193566")));
        myMoonMeetColor = "#FF193566";
        imageview1.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor(myMoonMeetColor),
                Color.parseColor(myMoonMeetColor)}));
        imageview2.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor(myMoonMeetColor),
                Color.parseColor(myMoonMeetColor)}));
        androidx.appcompat.widget.TooltipCompat.setTooltipText(imageview1,"Back");
        androidx.appcompat.widget.TooltipCompat.setTooltipText(imageview2,"Options");
        scroller.getViewTreeObserver()
                .addOnScrollChangedListener(() -> {
                    if (scroller.getChildAt(0).getBottom()
                            <= (scroller.getHeight() + scroller.getScrollY())) {
                        _fab.hide();
                    } else {
                        _fab.show();
                    }
                });
    }

    public void _ICC(final ImageView _img, final String _c1, final String _c2) {
        _img.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor(_c1), Color.parseColor(_c2)}));
    }


    public void _NewTime(final double _position, final TextView _textview) {
        time = _position;
        cal = Calendar.getInstance();
        deference = cal.getTimeInMillis() - time;
        if (deference < 60000) {
            _textview.setText("Active now");
        } else {
            if (deference < (60 * 60000)) {
                _textview.setText("Active ".concat(String.valueOf((long) (deference / 60000)).concat(" Minutes ago")));
            } else {
                if (deference < (24 * (60 * 60000))) {
                    _textview.setText("Active ".concat(String.valueOf((long) (deference / (60 * 60000))).concat(" Hours ago")));
                } else {
                    cal.setTimeInMillis((long) (time));
                    _textview.setText("Active on ".concat(new SimpleDateFormat("EEEE, MMMM d").format(cal.getTime())));
                }
            }
        }
    }


    public void _DatabaseConfiguration() {
        // Database Configuration
        Chat1.removeEventListener(_Chat1_child_listener);
        Chat2.removeEventListener(_Chat2_child_listener);
        Chatroom = "chat/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("/".concat(uid)));
        Chatcopy = "chat/".concat(uid.concat("/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid())));
        Chat1 =
                _firebase.getReference(Chatroom);
        Chat2 =
                _firebase.getReference(Chatcopy);
        Chat1.addChildEventListener(_Chat1_child_listener);
        Chat2.addChildEventListener(_Chat2_child_listener);
    }


    public void _libsben_animation(final View _view, final String _libsben, final Intent _libsben_intent) {
        _view.setTransitionName(_libsben);

        android.app.ActivityOptions optionsCompat = android.app.ActivityOptions.makeSceneTransitionAnimation(getParentActivity(), _view, _libsben);
        startActivity(_libsben_intent, optionsCompat.toBundle());
    }

    public void _Popup() {
        View popupView = getParentActivity().getLayoutInflater().inflate(R.layout.ptools, null);
        final PopupWindow popup = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        TextView tx1 = popupView.findViewById(R.id.tx1);

        TextView tx2 = popupView.findViewById(R.id.tx2);

        TextView tx3 = popupView.findViewById(R.id.tx3);

        LinearLayout ly = popupView.findViewById(R.id.ly);

        LinearLayout lin1 = popupView.findViewById(R.id.lin1);

        LinearLayout lin2 = popupView.findViewById(R.id.lin2);

        LinearLayout lin3 = popupView.findViewById(R.id.lin3);

        ImageView img1 = popupView.findViewById(R.id.pen);

        ImageView img2 = popupView.findViewById(R.id.door);

        ImageView img3 = popupView.findViewById(R.id.copy);
        img1.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor("#FF616161"),
                Color.parseColor("#FF616161")}));
        img2.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor("#FF616161"),
                Color.parseColor("#FF616161")}));
        img3.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor("#FF616161"),
                Color.parseColor("#FF616161")}));
        tx1.setTextColor(0xFF616161);
        tx2.setTextColor(0xFF616161);
        tx3.setTextColor(0xFF616161);
        lin1.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ((ClipboardManager) getParentActivity().getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", User_Firstname.concat(" ".concat(User_Lastname))));
                popup.dismiss();
            }
        });

        lin2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ((ClipboardManager) getParentActivity().getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", User_Username));
                popup.dismiss();
            }
        });

        lin3.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ((ClipboardManager) getParentActivity().getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", User_Bio));
                popup.dismiss();
            }
        });

        popup.setAnimationStyle(R.style.PopupAnimation);

        popup.showAsDropDown(imageview2, 0, 0);

        popup.setBackgroundDrawable(new BitmapDrawable());
        android.graphics.drawable.GradientDrawable ln = new android.graphics.drawable.GradientDrawable();
        ln.setColor(Color.parseColor("#FFFFFF"));

        ln.setCornerRadius(20);

        ly.setBackground(ln);

        ly.setElevation(5);
    }


    public class Shared_rvAdapter extends RecyclerView.Adapter<Shared_rvAdapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;

        public Shared_rvAdapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View _v = _inflater.inflate(R.layout.sharedphotoc, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new ViewHolder(_v);
        }

        @Override
        public void onBindViewHolder(ViewHolder _holder, final int _position) {
            View _view = _holder.itemView;

            final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
            final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);

            if (_data.get((int) _position).containsKey("image")) {
                Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int) _position).get("image").toString())).into(imageview1);
                imageview1.setOnClickListener(_view1 -> {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.didClickImage, _data.get((int) _position).get("uid").toString(), _data.get((int) _position).get("image").toString(), _data.get((int) _position).get("time").toString(), _data.get((int) _position).get("firstname").toString(), _data.get((int) _position).get("lastname").toString());
                });
            }
        }

        @Override
        public int getItemCount() {
            return _data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View v) {
                super(v);
            }
        }

    }
}