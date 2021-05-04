package org.mark.moonmeet;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.mark.moonmeet.ui.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;


public class ChangeusernameActivity extends BaseFragment {

    private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();

    private boolean updating = false;
    private String username = "";
    private HashMap<String, Object> newUsername = new HashMap<>();

    private ArrayList<HashMap<String, Object>> Takedusername = new ArrayList<>();

    private LinearLayout topbar;
    private LinearLayout main;
    private ImageView back;
    private TextView topbar_txt;
    private LinearLayout topbar_space;
    private ImageView apply;
    private LinearLayout space1;
    private LinearLayout editext_holder;
    private LinearLayout space2;
    private TextView check_user_txt;
    private LinearLayout space3;
    private TextView contact_txt;
    private LinearLayout space4;
    private TextView youcanuse_txt;
    private TextView link_title;
    private TextView link_txt;
    private EditText username_edittext;
    private LinearLayout divider;

    private DatabaseReference tk = _firebase.getReference("takedusername");
    private ChildEventListener _tk_child_listener;
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

    @Override
    public View createView(Context context) {
        fragmentView = new FrameLayout(context);
        actionBar.setAddToContainer(false);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.changeusername, ((ViewGroup) fragmentView), false);
        ((ViewGroup) fragmentView).addView(view);
        initialize(context);
        com.google.firebase.FirebaseApp.initializeApp(getParentActivity());
        initializeLogic();
        fragmentView.setOnTouchListener((v, event) -> false);
        return fragmentView;
    }

    private void initialize(Context context) {
        topbar = (LinearLayout) findViewById(R.id.topbar);
        main = (LinearLayout) findViewById(R.id.main);
        back = (ImageView) findViewById(R.id.back);
        topbar_txt = (TextView) findViewById(R.id.topbar_txt);
        topbar_space = (LinearLayout) findViewById(R.id.topbar_space);
        apply = (ImageView) findViewById(R.id.apply);
        space1 = (LinearLayout) findViewById(R.id.space1);
        editext_holder = (LinearLayout) findViewById(R.id.editext_holder);
        space2 = (LinearLayout) findViewById(R.id.space2);
        check_user_txt = (TextView) findViewById(R.id.check_user_txt);
        space3 = (LinearLayout) findViewById(R.id.space3);
        contact_txt = (TextView) findViewById(R.id.contact_txt);
        space4 = (LinearLayout) findViewById(R.id.space4);
        youcanuse_txt = (TextView) findViewById(R.id.youcanuse_txt);
        link_title = (TextView) findViewById(R.id.link_title);
        link_txt = (TextView) findViewById(R.id.link_txt);
        username_edittext = (EditText) findViewById(R.id.username_edittext);
        divider = (LinearLayout) findViewById(R.id.divider);
        Fauth = FirebaseAuth.getInstance();

        back.setOnClickListener(_view -> finishFragment());

        apply.setOnClickListener(_view -> {
            updating = true;
            SketchwareUtil.hideKeyboard(getApplicationContext());
            newUsername = new HashMap<>();
            newUsername.put("username", username_edittext.getText().toString());
            users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(newUsername);
            newUsername.clear();
            newUsername = new HashMap<>();
            newUsername.put("username", username_edittext.getText().toString());
            tk.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(newUsername);
            newUsername.clear();
        });

        username_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
                final String _charSeq = _param1.toString();
                if (_charSeq.length() == 0) {
                    check_user_txt.setVisibility(View.GONE);
                    link_title.setVisibility(View.GONE);
                    link_txt.setVisibility(View.GONE);
                    apply.setEnabled(false);
                } else {
                    if (_charSeq.length() < 5) {
                        check_user_txt.setVisibility(View.VISIBLE);
                        check_user_txt.setTextColor(0xFFD50000);
                        check_user_txt.setText("A username must have at least 5 characters.");
                        link_title.setVisibility(View.VISIBLE);
                        link_txt.setVisibility(View.VISIBLE);
                        link_txt.setText("https://MoonMeet.me/".concat(_charSeq));
                        apply.setEnabled(false);
                    } else {
                        if (_charSeq.length() > 4) {
                            tk.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot _dataSnapshot) {
                                    Takedusername = new ArrayList<>();
                                    try {
                                        GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                                        };
                                        for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                            HashMap<String, Object> _map = _data.getValue(_ind);
                                            Takedusername.add(_map);
                                        }
                                    } catch (Exception _e) {
                                        _e.printStackTrace();
                                    }
                                    if (new Gson().toJson(Takedusername).contains(username_edittext.getText().toString())) {
                                        check_user_txt.setText("Sorry, This username is already taken.");
                                        check_user_txt.setTextColor(0xFFD50000);
                                        check_user_txt.setVisibility(View.VISIBLE);
                                        link_title.setVisibility(View.VISIBLE);
                                        link_txt.setVisibility(View.VISIBLE);
                                        link_txt.setText("https://MoonMeet.me/".concat(_charSeq));
                                        apply.setEnabled(false);
                                    } else {
                                        check_user_txt.setVisibility(View.VISIBLE);
                                        check_user_txt.setText(username_edittext.getText().toString().concat(" ".concat("is available.")));
                                        check_user_txt.setTextColor(0xFF43A047);
                                        link_title.setVisibility(View.VISIBLE);
                                        link_txt.setVisibility(View.VISIBLE);
                                        link_txt.setText("https://MoonMeet.me/".concat(_charSeq));
                                        apply.setEnabled(true);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError _databaseError) {
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {

            }

            @Override
            public void afterTextChanged(Editable _param1) {

            }
        });

        _tk_child_listener = new ChildEventListener() {
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
        tk.addChildEventListener(_tk_child_listener);

        _users_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    if (_childValue.containsKey("uid") && (_childValue.containsKey("firstname") && (_childValue.containsKey("lastname") && (_childValue.containsKey("avatar") && _childValue.containsKey("username"))))) {
                        username_edittext.setText(_childValue.get("username").toString());
                        check_user_txt.setVisibility(View.GONE);
                        link_title.setVisibility(View.GONE);
                        link_txt.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (updating) {
                    updating = false;
                    finishFragment();
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
        back.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor("#FF193566"),
                Color.parseColor("#FF193566")}));
        apply.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor("#FF193566"),
                Color.parseColor("#FF193566")}));
        topbar.setElevation((int) 2);
        username_edittext.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {

                divider.setBackgroundColor(0xFF193566);
            } else {
                divider.setBackgroundColor(0xFFDADADA);
            }
        });
        androidx.appcompat.widget.TooltipCompat.setTooltipText(back, "Back");
        androidx.appcompat.widget.TooltipCompat.setTooltipText(back, "Done");
    }

    @Override
    public boolean onBackPressed() {
        finishFragment();
        return false;
    }
}