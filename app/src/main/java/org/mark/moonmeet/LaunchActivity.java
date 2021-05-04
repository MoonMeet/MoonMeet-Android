package org.mark.moonmeet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.TooltipCompat;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import org.mark.moonmeet.adapters.ActiveAdapter;
import org.mark.moonmeet.adapters.LastChatsAdapter;
import org.mark.moonmeet.adapters.StoryAdapter;
import org.mark.moonmeet.ui.BaseFragment;
import org.mark.moonmeet.utils.AndroidUtilities;
import org.mark.moonmeet.utils.MoonMeetItemAnimator;
import org.mark.moonmeet.utils.NotificationCenter;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class LaunchActivity extends BaseFragment implements NotificationCenter.NotificationCenterDelegate {

    private Timer _timer = new Timer();
    private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();

    private Toolbar _toolbar;
    private AppBarLayout _app_bar;
    private CoordinatorLayout _coordinator;
    private FloatingActionButton _fab;
    private DrawerLayout _drawer;
    private HashMap<String, Object> storiesMap = new HashMap<>();
    private HashMap<String, Object> mapped = new HashMap<>();
    private double time = 0;
    private String UserChats_str = "";
    private String OneSignalUserID = "";
    private String OneSignalPushToken = "";
    private HashMap<String, Object> OneSignalMap = new HashMap<>();
    private HashMap<String, Object> FcmMap = new HashMap<>();

    private ArrayList<HashMap<String, Object>> toDeliveryStory = new ArrayList<>();
    private ArrayList<String> StoriesUID = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> toDeliveryLastChats = new ArrayList<>();
    private ArrayList<String> UserChats_LS = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> toDelivryActive = new ArrayList<>();
    private ArrayList<String> Active_LS = new ArrayList<>();

    private LinearLayout linear2;
    private SwipeRefreshLayout swipe_layout;
    private TextView no_internet1;
    private TextView no_internet2;
    private TextView no_internet3;
    private TextView no_internet4;
    private NestedScrollView scroll;
    private LinearLayout main_layout;
    private LinearLayout stories_online_linear;
    private LinearLayout stories_active_holder;
    private LinearLayout chats_no;
    private RecyclerView chats;
    private TextView stories_txt;
    private TextView active_txt;
    private LinearLayout ppl_holder;
    private LinearLayout rv_holder;
    private ImageView ppl_add_icon;
    private TextView ppl_new_txt;
    private RecyclerView stories_rv;
    private RecyclerView active_rv;
    private TextView chats_no1;
    private TextView chats_no2;
    private LinearLayout _drawer_main;
    private LinearLayout _drawer_top_holder;
    private LinearLayout _drawer_divider1;
    private LinearLayout _drawer_new_group_holder;
    private LinearLayout _drawer_contacts_holder;
    private LinearLayout _drawer_saved_message_holder;
    private LinearLayout _drawer_settings_holder;
    private LinearLayout _drawer_announcement_holder;
    private LinearLayout _drawer_divider2;
    private LinearLayout _drawer_invite_friends_holder;
    private LinearLayout _drawer_faq_holder;
    private LinearLayout _drawer_part_1;
    private LinearLayout _drawer_part2;
    private LinearLayout _drawer_avatar_holder;
    private ImageView _drawer_theme_switcher;
    private CircleImageView _drawer_avatar;
    private LinearLayout _drawer_name_phone_holder;
    private TextView _drawer_name;
    private TextView _drawer_phone;
    private ImageView _drawer_group_icon;
    private TextView _drawer_new_group_txt;
    private ImageView _drawer_contact_icon;
    private TextView _drawer_contact_txt;
    private ImageView _drawer_saved_icon;
    private TextView _drawer_saved_txt;
    private ImageView _drawer_settings_icon;
    private TextView _drawer_settings_txt;
    private ImageView _drawer_announcement_icon;
    private ImageView _drawer_invite_icon;
    private TextView _drawer_invite_txt;
    private ImageView _drawer_faq_icon;
    private TextView _drawer_faq_txt;

    private Intent toAddStory = new Intent();
    private DatabaseReference stories = _firebase.getReference("stories");
    private ChildEventListener _stories_child_listener;
    private Calendar calendar = Calendar.getInstance();
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
    private DatabaseReference StoryViews = _firebase.getReference("StoryViews");
    private ChildEventListener _StoryViews_child_listener;
    private SharedPreferences sp_mydt;
    private DatabaseReference users = _firebase.getReference("users");
    private ChildEventListener _users_child_listener;
    private TimerTask TimeString;
    private SharedPreferences sp_seen;
    private Calendar Cal = Calendar.getInstance();
    private Intent toGoChat = new Intent();
    private Intent toViewStory = new Intent();
    private Calendar c = Calendar.getInstance();
    private DatabaseReference UserChats = _firebase.getReference("UserChats");
    private ChildEventListener _UserChats_child_listener;
    private Intent toDrawer = new Intent();
    private RequestNetwork rn;
    private RequestNetwork.RequestListener _rn_request_listener;
    private SharedPreferences MyStoryData;
    private SharedPreferences ban_reason;
    private RequestNetwork InternetCheck;
    private RequestNetwork.RequestListener _InternetCheck_request_listener;
    private LinearLayout topbar;
    private ImageView imageview1;
    private TextView topbar_txt;
    private LinearLayout topbar_space;
    private ImageView search;
    private SharedPreferences CatchedImagePath;

    public boolean isSwipeBackEnabled(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onFragmentCreate() {
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.didClickConversation);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.didClickStory);
        return super.onFragmentCreate();
    }

    @Override
    public void onFragmentDestroy() {
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didClickConversation);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didClickStory);
        super.onFragmentDestroy();
    }

    @Override
    public View createView(Context context) {
        fragmentView = new FrameLayout(context);
        actionBar.setAddToContainer(false);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.launch, (ViewGroup) fragmentView, false);
        ((ViewGroup) fragmentView).addView(view);
        initialize(context);
        initializeLogic();
        return fragmentView;
    }

    private void initialize(Context context) {

        _fab = (FloatingActionButton) findViewById(R.id._fab);

        _drawer = (DrawerLayout) findViewById(R.id._drawer);
        ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(getParentActivity(), _drawer, _toolbar, R.string.app_name, R.string.app_name);
        _drawer.addDrawerListener(_toggle);
        _toggle.syncState();
        topbar = (LinearLayout) findViewById(R.id.topbar);
        LinearLayout _nav_view = (LinearLayout) findViewById(R.id._nav_view);
        imageview1 = findViewById(R.id.imageview1);
        topbar_txt = (TextView) findViewById(R.id.topbar_txt);
        topbar_space = (LinearLayout) findViewById(R.id.topbar_space);
        search = (ImageView) findViewById(R.id.search);
        linear2 = (LinearLayout) findViewById(R.id.linear2);
        swipe_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        no_internet1 = (TextView) findViewById(R.id.no_internet1);
        no_internet2 = (TextView) findViewById(R.id.no_internet2);
        no_internet3 = (TextView) findViewById(R.id.no_internet3);
        no_internet4 = (TextView) findViewById(R.id.no_internet4);
        scroll = (NestedScrollView) findViewById(R.id.scroll);
        main_layout = (LinearLayout) findViewById(R.id.main_layout);
        stories_online_linear = (LinearLayout) findViewById(R.id.stories_online_linear);
        stories_active_holder = (LinearLayout) findViewById(R.id.stories_active_holder);
        chats_no = (LinearLayout) findViewById(R.id.chats_no);
        chats = (RecyclerView) findViewById(R.id.chats);
        stories_txt = (TextView) findViewById(R.id.stories_txt);
        active_txt = (TextView) findViewById(R.id.active_txt);
        ppl_holder = (LinearLayout) findViewById(R.id.ppl_holder);
        rv_holder = (LinearLayout) findViewById(R.id.rv_holder);
        ppl_add_icon = (ImageView) findViewById(R.id.ppl_add_icon);
        ppl_add_icon = (ImageView) findViewById(R.id.ppl_add_icon);
        ppl_new_txt = (TextView) findViewById(R.id.ppl_new_txt);
        stories_rv = (RecyclerView) findViewById(R.id.stories_rv);
        active_rv = (RecyclerView) findViewById(R.id.active_rv);
        chats_no1 = (TextView) findViewById(R.id.chats_no1);
        chats_no2 = (TextView) findViewById(R.id.chats_no2);
        _drawer_main = (LinearLayout) _nav_view.findViewById(R.id.main);
        _drawer_top_holder = (LinearLayout) _nav_view.findViewById(R.id.top_holder);
        _drawer_divider1 = (LinearLayout) _nav_view.findViewById(R.id.divider1);
        _drawer_new_group_holder = (LinearLayout) _nav_view.findViewById(R.id.new_group_holder);
        _drawer_contacts_holder = (LinearLayout) _nav_view.findViewById(R.id.contacts_holder);
        _drawer_saved_message_holder = (LinearLayout) _nav_view.findViewById(R.id.saved_message_holder);
        _drawer_settings_holder = (LinearLayout) _nav_view.findViewById(R.id.settings_holder);
        _drawer_announcement_holder = (LinearLayout) _nav_view.findViewById(R.id.announcement_holder);
        _drawer_divider2 = (LinearLayout) _nav_view.findViewById(R.id.divider2);
        _drawer_invite_friends_holder = (LinearLayout) _nav_view.findViewById(R.id.invite_friends_holder);
        _drawer_faq_holder = (LinearLayout) _nav_view.findViewById(R.id.faq_holder);
        _drawer_part_1 = (LinearLayout) _nav_view.findViewById(R.id.part_1);
        _drawer_part2 = (LinearLayout) _nav_view.findViewById(R.id.part2);
        _drawer_avatar_holder = (LinearLayout) _nav_view.findViewById(R.id.avatar_holder);
        _drawer_theme_switcher = (ImageView) _nav_view.findViewById(R.id.theme_switcher);
        _drawer_avatar = (CircleImageView) _nav_view.findViewById(R.id.avatar);
        _drawer_name_phone_holder = (LinearLayout) _nav_view.findViewById(R.id.name_phone_holder);
        _drawer_name = (TextView) _nav_view.findViewById(R.id.name);
        _drawer_phone = (TextView) _nav_view.findViewById(R.id.phone);
        _drawer_group_icon = (ImageView) _nav_view.findViewById(R.id.group_icon);
        _drawer_new_group_txt = (TextView) _nav_view.findViewById(R.id.new_group_txt);
        _drawer_contact_icon = (ImageView) _nav_view.findViewById(R.id.contact_icon);
        _drawer_contact_txt = (TextView) _nav_view.findViewById(R.id.contact_txt);
        _drawer_saved_icon = (ImageView) _nav_view.findViewById(R.id.saved_icon);
        _drawer_saved_txt = (TextView) _nav_view.findViewById(R.id.saved_txt);
        _drawer_settings_icon = (ImageView) _nav_view.findViewById(R.id.settings_icon);
        _drawer_settings_txt = (TextView) _nav_view.findViewById(R.id.settings_txt);
        _drawer_announcement_icon = (ImageView) _nav_view.findViewById(R.id.announcement_icon);
        _drawer_invite_icon = (ImageView) _nav_view.findViewById(R.id.invite_icon);
        _drawer_invite_txt = (TextView) _nav_view.findViewById(R.id.invite_txt);
        _drawer_faq_icon = (ImageView) _nav_view.findViewById(R.id.faq_icon);
        _drawer_faq_txt = (TextView) _nav_view.findViewById(R.id.faq_txt);
        Fauth = FirebaseAuth.getInstance();
        sp_mydt = MoonMeetApplication.applicationContext.getSharedPreferences("sp_mydt", Activity.MODE_PRIVATE);
        sp_seen = MoonMeetApplication.applicationContext.getSharedPreferences("sp_seen", Activity.MODE_PRIVATE);
        CatchedImagePath = getParentActivity().getSharedPreferences("CatchedImagePath", Activity.MODE_PRIVATE);
        rn = new RequestNetwork(getParentActivity());
        MyStoryData = MoonMeetApplication.applicationContext.getSharedPreferences("MyStoryData", Activity.MODE_PRIVATE);
        ban_reason = context.getSharedPreferences("ban_reason", Activity.MODE_PRIVATE);
        InternetCheck = new RequestNetwork(getParentActivity());

        imageview1.setOnClickListener(_view -> _drawer.openDrawer(GravityCompat.START));

        search.setOnClickListener(_view -> {
            presentFragment(new SearchActivity(), false);
        });

        no_internet4.setOnClickListener(_view -> InternetCheck.startRequestNetwork(RequestNetworkController.GET, "https://www.google.com/", "connecting", _InternetCheck_request_listener));

        stories_txt.setOnClickListener(_view -> {
            stories_rv.setVisibility(View.VISIBLE);
            active_rv.setVisibility(View.GONE);
            stories_txt.setTextColor(0xFF193566);
            active_txt.setTextColor(0xFF546E7A);
            ppl_new_txt.setText("Add Story");
        });

        active_txt.setOnClickListener(_view -> {
            stories_rv.setVisibility(View.GONE);
            active_rv.setVisibility(View.VISIBLE);
            stories_txt.setTextColor(0xFF546E7A);
            active_txt.setTextColor(0xFF193566);
            ppl_new_txt.setText("Discover");
            scroll.post(() -> scroll.fullScroll(scroll.FOCUS_UP));
        });

        ppl_holder.setOnClickListener(_view -> {
            if (ppl_new_txt.getText().toString().equals("Add Story")) {
                presentFragment(new NewstoryActivity(), false);
            } else {
                presentFragment(new DiscoverActivity(), false);
            }
        });

        _fab.setOnClickListener(_view -> {
            presentFragment(new DiscoverActivity(), false);
        });

        _stories_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childValue.containsKey("uid") && (_childValue.containsKey("sid") && (_childValue.containsKey("time") && (_childValue.containsKey("avatar") && (_childValue.containsKey("text") || _childValue.containsKey("image")))))) {
                    calendar = Calendar.getInstance();
                    if ((calendar.getTimeInMillis() - Double.parseDouble(_childValue.get("time").toString())) > 86400000) {
                        stories.child(_childValue.get("sid").toString()).removeValue();
                        StoryViews.child(_childValue.get("sid").toString()).removeValue();
                    } else {
                        storiesMap.put(_childValue.get("uid").toString(), _childValue.get("sid").toString());
                        toDeliveryStory.add(_childValue);
                    }
                }
                if (toDeliveryStory.size() > 0) {
                    stories_rv.setAdapter(new StoryAdapter(MoonMeetApplication.applicationContext, storiesMap, StoriesUID, toDeliveryStory));
                    stories_rv.getAdapter().notifyDataSetChanged();
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
        stories.addChildEventListener(_stories_child_listener);

        _StoryViews_child_listener = new ChildEventListener() {
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
        StoryViews.addChildEventListener(_StoryViews_child_listener);

        _users_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    calendar = Calendar.getInstance();
                    if (_childValue.containsKey("uid") && (_childValue.containsKey("avatar") && (_childValue.containsKey("firstname") && (_childValue.containsKey("lastname") && (_childValue.containsKey("phone") && (_childValue.containsKey("phone_status") && _childValue.containsKey("username"))))))) {
                        _drawer_name.setText(_childValue.get("firstname").toString().concat(" ".concat(_childValue.get("lastname").toString())));
                        _drawer_phone.setText(_childValue.get("phone").toString());
                        com.bumptech.glide.Glide.with(MoonMeetApplication.applicationContext)
                                .load(_childValue.get("avatar").toString())
                                .override(1000, 1000)
                                .into(_drawer_avatar);
                        MyStoryData.edit().putString("firstname", _childValue.get("firstname").toString()).apply();
                        MyStoryData.edit().putString("lastname", _childValue.get("lastname").toString()).apply();
                        MyStoryData.edit().putString("avatar", _childValue.get("avatar").toString()).apply();
                        MyStoryData.edit().putString("uid", _childValue.get("uid").toString()).apply();
                        MyStoryData.edit().putString("username", _childValue.get("username").toString()).apply();
                    }
                    if (!_childValue.containsKey("ban")) {
                        mapped = new HashMap<>();
                        mapped.put("ban", "nope");
                        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(mapped);
                        mapped.clear();
                    }
                    if (!_childValue.containsKey("ban_reason")) {
                        mapped = new HashMap<>();
                        mapped.put("ban_reason", "nope");
                        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(mapped);
                        mapped.clear();
                    }
                    if (!_childValue.containsKey("last_seen")) {
                        calendar = Calendar.getInstance();
                        mapped = new HashMap<>();
                        mapped.put("last_seen", String.valueOf((long) (calendar.getTimeInMillis())));
                        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(mapped);
                        mapped.clear();
                    } else {
                        if (_childValue.get("last_seen").toString().equals("private")) {

                        } else {
                            calendar = Calendar.getInstance();
                            mapped = new HashMap<>();
                            mapped.put("last_seen", String.valueOf((long) (calendar.getTimeInMillis())));
                            users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(mapped);
                            mapped.clear();
                        }
                    }
                    if (_childValue.containsKey("ban") && _childValue.containsKey("ban_reason")) {
                        if (!_childValue.get("ban_reason").toString().equals("nope")) {
                            ban_reason.edit().putString("reason", _childValue.get("ban_reason").toString()).apply();
                        }
                        if (!_childValue.get("ban").toString().equals("nope")) {
                            BannedBottomdialogFragmentActivity dialog = new BannedBottomdialogFragmentActivity();
                            dialog.setCancelable(false);
                            // ialog.show(getParentActivity().getFragmentManager(), "1");
                        }
                    }
					/*
if (_childValue.containsKey("OneSignalUserID")) {

}
else {
OneSignalMap = new HashMap<>();
OneSignalMap.put("OneSignalUserID", OneSignalUserID);
users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(OneSignalMap);
OneSignalMap.clear();
}
if (_childValue.containsKey("OneSignalPushToken")) {

}
else {
OneSignalMap = new HashMap<>();
OneSignalMap.put("OneSignalPushToken", OneSignalPushToken);
users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(OneSignalMap);
OneSignalMap.clear();
}
if (_childValue.containsKey("FCM")) {

}
else {
FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(_onCompleteListener);
}
*/
                }
                if (_childValue.containsKey("last_seen")) {
                    if (_childValue.get("last_seen").toString().equals("private")) {
                        Log.d("LaunchActivity", "Catched some priavte last seen");
                    } else {
                        if (!_childValue.get("last_seen").toString().equals("private")) {
                            calendar = Calendar.getInstance();
                            if ((calendar.getTimeInMillis() - Double.parseDouble(_childValue.get("last_seen").toString())) < 30000) {
                                if (Active_LS.contains(_childValue.get("uid").toString())) {
                                    Log.d("LaunchActivity", "Active ListString contains all UID");
                                } else {
                                    toDelivryActive.add(_childValue);
                                    Active_LS.add(_childValue.get("uid").toString());
                                    if (toDelivryActive.size() > 0) {
                                        ActiveAdapter activeAdapter = new ActiveAdapter(MoonMeetApplication.applicationContext, storiesMap, toDelivryActive);
                                        active_rv.setAdapter(activeAdapter);
                                        active_rv.getAdapter().notifyDataSetChanged();
                                    }
                                }
                            }
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
                /* TODO : caught a null value of uid here when the app stay untouched for some minutes.
                 * stack trace :  java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.String com.google.firebase.auth.FirebaseUser.getUid()' on a null object reference
                 * at org.mark.moonmeet.LaunchActivity$3.onChildChanged(LaunchActivity.java:566)
                 */
                if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    calendar = Calendar.getInstance();
                    if (_childValue.containsKey("uid") && (_childValue.containsKey("avatar") && (_childValue.containsKey("firstname") && (_childValue.containsKey("lastname") && (_childValue.containsKey("phone") && (_childValue.containsKey("phone_status") && _childValue.containsKey("username"))))))) {
                        _drawer_name.setText(_childValue.get("firstname").toString().concat(" ".concat(_childValue.get("lastname").toString())));
                        _drawer_phone.setText(_childValue.get("phone").toString());
                        com.bumptech.glide.Glide.with(MoonMeetApplication.applicationContext)
                                .load(_childValue.get("avatar").toString())
                                .override(1000, 1000)
                                .into(_drawer_avatar);
                    }
                    if (!_childValue.containsKey("last_seen")) {
                        calendar = Calendar.getInstance();
                        mapped = new HashMap<>();
                        mapped.put("last_seen", String.valueOf((long) (calendar.getTimeInMillis())));
                        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(mapped);
                        mapped.clear();
                    }
                }
                if (_childValue.containsKey("last_seen")) {
                    if (_childValue.get("last_seen").toString().equals("private")) {

                    } else {
                        if (!_childValue.get("last_seen").toString().equals("private")) {
                            calendar = Calendar.getInstance();
                            if ((calendar.getTimeInMillis() - Double.parseDouble(_childValue.get("last_seen").toString())) < 30000) {
                                if (Active_LS.contains(_childValue.get("uid").toString())) {

                                } else {
                                    toDelivryActive.add(_childValue);
                                    Active_LS.add(_childValue.get("uid").toString());
                                    if (toDelivryActive.size() > 0) {
                                        active_rv.setAdapter(new ActiveAdapter(MoonMeetApplication.applicationContext, storiesMap, toDelivryActive));
                                        active_rv.getAdapter().notifyDataSetChanged();
                                    }
                                }
                            }
                        }
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

        _UserChats_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childValue.containsKey("uid")) {
                    if (UserChats_LS.contains(_childValue.get("uid").toString())) {
                        Log.d("LaunchActivity", "UserChats ListMap kinda contains all UIDs");
                    } else {
                        if (_childValue.containsKey("last_message_time")) {
                            toDeliveryLastChats.add(_childValue);
                            UserChats_LS.add(_childValue.get("uid").toString());
                            SketchwareUtil.sortListMap(toDeliveryLastChats, "last_message_time", false, false);
                        }
                        chats.setAdapter(new LastChatsAdapter(getParentActivity(), storiesMap, toDeliveryLastChats));
                        chats.getAdapter().notifyDataSetChanged();
                        chats_no.setVisibility(View.GONE);
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
        UserChats.addChildEventListener(_UserChats_child_listener);

        _rn_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;

            }

            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;

            }
        };

        _InternetCheck_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                swipe_layout.setVisibility(View.VISIBLE);
                linear2.setVisibility(View.GONE);
                topbar.setVisibility(View.VISIBLE);
                InternetCheck.startRequestNetwork(RequestNetworkController.GET, "https://www.google.com/", "connecting", _InternetCheck_request_listener);
            }

            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
                linear2.setVisibility(View.VISIBLE);
                topbar.setVisibility(View.GONE);
                swipe_layout.setVisibility(View.GONE);
                linear2.setLayoutParams(new LinearLayout.LayoutParams((int) LinearLayout.LayoutParams.MATCH_PARENT, (int) LinearLayout.LayoutParams.MATCH_PARENT));
            }
        };

        _drawer_main.setOnClickListener(_view -> {

        });

        _drawer_top_holder.setOnClickListener(_view -> {

        });

        _drawer_divider1.setOnClickListener(_view -> {

        });

        _drawer_new_group_holder.setOnClickListener(_view -> AndroidUtilities.showToast("Coming Soon !"));

        _drawer_contacts_holder.setOnClickListener(_view -> AndroidUtilities.showToast("Coming Soon !"));

        _drawer_saved_message_holder.setOnClickListener(_view -> AndroidUtilities.showToast("Coming Soon !"));

        _drawer_settings_holder.setOnClickListener(_view -> {
            presentFragment(new SettingsActivity(), false, false);
        });

        _drawer_announcement_holder.setOnClickListener(_view -> {
            presentFragment(new AnnouncementsActivity(), false, false);
        });

        _drawer_divider2.setOnClickListener(_view -> {

        });

        _drawer_invite_friends_holder.setOnClickListener(_view -> {
            String apk = "";
            String uri = ("org.mark.moonmeet");
            try {
                android.content.pm.PackageInfo pi = MoonMeetApplication.applicationContext.getPackageManager().getPackageInfo(uri, android.content.pm.PackageManager.GET_ACTIVITIES);
                apk = pi.applicationInfo.publicSourceDir;
            } catch (Exception e) {
                AndroidUtilities.showToast(e.toString());
            }
            Intent iten = new Intent(Intent.ACTION_SEND);
            iten.setType("*/*");
            iten.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apk)));
            iten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MoonMeetApplication.applicationContext.startActivity(Intent.createChooser(iten, "Hey, I'm MoonMeet would you like to share me with your friends ?"));
        });

        _drawer_faq_holder.setOnClickListener(_view -> {
            presentFragment(new FaqInfoActivity(), false);
        });

        _drawer_part_1.setOnClickListener(_view -> {

        });

        _drawer_part2.setOnClickListener(_view -> {

        });

        _drawer_avatar_holder.setOnClickListener(_view -> {

        });

        _drawer_theme_switcher.setOnClickListener(_view -> {

        });

        _drawer_avatar.setOnClickListener(_view -> {

        });

        _drawer_name_phone_holder.setOnClickListener(_view -> {

        });

        _drawer_name.setOnClickListener(_view -> {

        });

        _drawer_phone.setOnClickListener(_view -> {

        });

        _drawer_group_icon.setOnClickListener(_view -> {

        });

        _drawer_new_group_txt.setOnClickListener(_view -> {

        });

        _drawer_contact_icon.setOnClickListener(_view -> {

        });

        _drawer_contact_txt.setOnClickListener(_view -> {

        });

        _drawer_saved_icon.setOnClickListener(_view -> {

        });

        _drawer_saved_txt.setOnClickListener(_view -> {

        });

        _drawer_settings_icon.setOnClickListener(_view -> {
            presentFragment(new SettingsActivity(), false, false);
        });

        _drawer_settings_txt.setOnClickListener(_view -> {
            presentFragment(new SettingsActivity(), false, false);
        });

        _drawer_invite_icon.setOnClickListener(_view -> {

        });

        _drawer_invite_txt.setOnClickListener(_view -> {

        });

        _drawer_faq_icon.setOnClickListener(_view -> {
            presentFragment(new FaqInfoActivity(), false);
        });

        _drawer_faq_txt.setOnClickListener(_view -> {
            presentFragment(new FaqInfoActivity(), false);
        });

        Fauth_updateEmailListener = _param1 -> {
            final boolean _success = _param1.isSuccessful();
            final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

        };

        Fauth_updatePasswordListener = _param1 -> {
            final boolean _success = _param1.isSuccessful();
            final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

        };

        Fauth_emailVerificationSentListener = _param1 -> {
            final boolean _success = _param1.isSuccessful();
            final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

        };

        Fauth_deleteUserListener = _param1 -> {
            final boolean _success = _param1.isSuccessful();
            final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

        };

        Fauth_phoneAuthListener = task -> {
            final boolean _success = task.isSuccessful();
            final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";

        };

        Fauth_updateProfileListener = _param1 -> {
            final boolean _success = _param1.isSuccessful();
            final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

        };

        Fauth_googleSignInListener = task -> {
            final boolean _success = task.isSuccessful();
            final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";

        };

        _Fauth_create_user_listener = _param1 -> {
            final boolean _success = _param1.isSuccessful();
            final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

        };

        _Fauth_sign_in_listener = _param1 -> {
            final boolean _success = _param1.isSuccessful();
            final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

        };

        _Fauth_reset_password_listener = _param1 -> {
            final boolean _success = _param1.isSuccessful();

        };
    }

    private void initializeLogic() {
		/*
OneSignal.init(LaunchActivity.this, "AIzaSyBDLpxX9YYtP9i7w1MaHR2ZZIDE-n4P0Bc", "02c27ebc-a96d-4005-80d5-dc08294131a6");
 OneSignal.getCurrentOrNewInitBuilder()
.inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
.unsubscribeWhenNotificationsAreDisabled(true).init();
OneSignal.startInit(LaunchActivity.this)
.inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
.unsubscribeWhenNotificationsAreDisabled(true).init();
OSPermissionSubscriptionState  = OneSignal.getPermissionSubscriptionState();

boolean isEnabled = .getPermissionStatus().getEnabled();
boolean isSubscribed = .getSubscriptionStatus().getSubscribed();
boolean subscriptionSetting = .getSubscriptionStatus().getUserSubscriptionSetting();
String userID = .getSubscriptionStatus().getUserId();
String pushToken = .getSubscriptionStatus().getPushToken();

OneSignal.setSubscription(true);
OneSignalUserID = userID;
OneSignalPushToken = pushToken;
*/
        // Internet check
        InternetCheck.startRequestNetwork(RequestNetworkController.GET, "https://www.google.com/", "connecting", _InternetCheck_request_listener);
        // Check if User Logged-in or not
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Bundle args = new Bundle();
            args.putString("Country", ".");
            args.putString("Code", ".");
            presentFragment(new OtpActivity(args), false);
            AndroidUtilities.showToast("Session Expired, please re-login.");
        }
        // Strict VM Policy
        StrictMode.VmPolicy.Builder builder =
                new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m =
                        StrictMode.class.getMethod(
                                "disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                AndroidUtilities.showToast(e.toString());
            }
        }
        // ActionBar
        // Get Firebase Reference
        UserChats.removeEventListener(_UserChats_child_listener);
        UserChats_str = "UserChats/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid());
        UserChats = _firebase.getReference(UserChats_str);
        UserChats.addChildEventListener(_UserChats_child_listener);
        // Navigation Drawer
        _Drawer();
        // Recyclerview
        LinearLayoutManager cLinearLayoutManager = new LinearLayoutManager(MoonMeetApplication.applicationContext, LinearLayoutManager.VERTICAL, false);
        chats.setLayoutManager(cLinearLayoutManager);
        chats.setItemViewCacheSize(60);
        chats.setDrawingCacheEnabled(true);
        chats.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        chats.setItemAnimator(new MoonMeetItemAnimator());

        LinearLayoutManager sLinearLayoutManager = new LinearLayoutManager(MoonMeetApplication.applicationContext, LinearLayoutManager.HORIZONTAL, false);
        stories_rv.setLayoutManager(sLinearLayoutManager);
        stories_rv.setItemViewCacheSize(60);
        stories_rv.setDrawingCacheEnabled(true);
        stories_rv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        stories_rv.setItemAnimator(new MoonMeetItemAnimator());
        LinearLayoutManager aLinearLayoutManager = new LinearLayoutManager(MoonMeetApplication.applicationContext, LinearLayoutManager.HORIZONTAL, false);
        active_rv.setLayoutManager(aLinearLayoutManager);
        active_rv.setItemViewCacheSize(60);
        active_rv.setDrawingCacheEnabled(true);
        active_rv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        active_rv.setItemAnimator(new MoonMeetItemAnimator());
        // Fonts
        _round(ppl_add_icon, SketchwareUtil.getDip(MoonMeetApplication.applicationContext, (int) (360)), "#FFDADADA");
        stories_txt.setTypeface(Typeface.createFromAsset(MoonMeetApplication.applicationContext.getAssets(), "fonts/rmedium.ttf"), 0);
        active_txt.setTypeface(Typeface.createFromAsset(MoonMeetApplication.applicationContext.getAssets(), "fonts/rmedium.ttf"), 0);
        ppl_new_txt.setTypeface(Typeface.createFromAsset(MoonMeetApplication.applicationContext.getAssets(), "fonts/rmedium.ttf"), 0);
        topbar_txt.setTypeface(Typeface.createFromAsset(MoonMeetApplication.applicationContext.getAssets(), "fonts/royal_404.ttf"), 1);
        // Color Filter
        search.setColorFilter(0xFF193566, PorterDuff.Mode.MULTIPLY);
        imageview1.setColorFilter(0xFF193566, PorterDuff.Mode.MULTIPLY);
        ppl_add_icon.setColorFilter(0xFF000000, PorterDuff.Mode.MULTIPLY);
        // Design
        topbar.setElevation((int) 2);
        no_internet4.setBackground(new GradientDrawable() {
            public GradientDrawable getIns(int a, int b) {
                this.setCornerRadius(a);
                this.setColor(b);
                return this;
            }
        }.getIns((int) SketchwareUtil.getDip(MoonMeetApplication.applicationContext, (int) (20)), 0xFF193566));
        // UI
        stories_txt.performClick();
        // FAB
        _fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#193566")));
        // Swipe Refresh
        swipe_layout.setOnRefreshListener(() -> {
            toDeliveryStory.clear();
            StoriesUID.clear();
            toDeliveryLastChats.clear();
            UserChats_LS.clear();
            toDelivryActive.clear();
            Active_LS.clear();
            stories.addChildEventListener(_stories_child_listener);
            users.addChildEventListener(_users_child_listener);
            UserChats.addChildEventListener(_UserChats_child_listener);
            swipe_layout.setRefreshing(false);
        });
        TooltipCompat.setTooltipText(search, "Media");
        TooltipCompat.setTooltipText(imageview1, "Drawer");
        _RippleEffects("#FFDADADA", imageview1);
        _RippleEffects("#FFDADADA", search);
        _fab.show();
    }

    @Override
    public boolean onBackPressed() {
        if (_drawer.isDrawerOpen(GravityCompat.START)) {
            _drawer.closeDrawer(GravityCompat.START);
        } else {
            finishAffinity();
        }
        return false;
    }

    @Override
    public void onTransitionAnimationEnd(boolean isOpen, boolean backward) {
        // Refreshing Data
        toDeliveryStory.clear();
        StoriesUID.clear();
        toDeliveryLastChats.clear();
        UserChats_LS.clear();
        toDelivryActive.clear();
        Active_LS.clear();
        stories.addChildEventListener(_stories_child_listener);
        users.addChildEventListener(_users_child_listener);
        UserChats.addChildEventListener(_UserChats_child_listener);
        if (!CatchedImagePath.getString("LatestImagePath", "").equals("-")) {
            CatchedImagePath.edit().putString("LatestImagePath", "").apply();
        }
    }

    public void _Drawer() {
        _drawer_divider1.setVisibility(View.GONE);
        _drawer_theme_switcher.setVisibility(View.INVISIBLE);
        _drawer_theme_switcher.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor("#FFFFFF"),
                Color.parseColor("#FFFFFF")}));
        _drawer_group_icon.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor("#FF193566"),
                Color.parseColor("#FF193566")}));
        _drawer_contact_icon.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor("#FF193566"),
                Color.parseColor("#FF193566")}));
        _drawer_saved_icon.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor("#FF193566"),
                Color.parseColor("#FF193566")}));
        _drawer_settings_icon.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor("#FF193566"),
                Color.parseColor("#FF193566")}));
        _drawer_invite_icon.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor("#FF193566"),
                Color.parseColor("#FF193566")}));
        _drawer_faq_icon.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor("#FF193566"),
                Color.parseColor("#FF193566")}));
        _drawer_announcement_icon.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor("#FF193566"),
                Color.parseColor("#FF193566")}));
    }

    public void _round(final View _view, final double _num, final String _color) {
        try {
            android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
            gd.setColor(Color.parseColor(_color));


            float f = (float) _num;

            gd.setCornerRadius(f);
            _view.setBackground(gd);

        } catch (Exception e) {
            Log.e("Error: ", e.toString());
            String error_code = e.toString();

            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setTitle("Error")
                    .setMessage(error_code)
                    .setCancelable(false)
                    .setNegativeButton("ОК", (dialog, id) -> {

                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }


    public void _RippleEffects(final String _color, final View _view) {
        android.content.res.ColorStateList clr = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(_color)});
        android.graphics.drawable.RippleDrawable ripdr = new android.graphics.drawable.RippleDrawable(clr, null, null);
        _view.setBackground(ripdr);
    }

    @Override
    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.didClickConversation) {
            presentFragment(new ChatActivity((String) args[0]), false);
        }
        if (id == NotificationCenter.didClickStory) {
            presentFragment(new StoryActivity((String) args[0]), false);
        }
    }
}