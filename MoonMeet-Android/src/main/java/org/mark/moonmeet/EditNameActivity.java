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
import android.widget.RelativeLayout;
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

import org.mark.moonmeet.ui.BaseFragment;

import java.util.HashMap;


public class EditNameActivity extends BaseFragment {

    private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();

    private HashMap<String, Object> NamePush = new HashMap<>();
    private boolean updatingName = false;
    private String My_Firstname = "";
    private String My_Lastname = "";
    private String My_FirebaseUID = "";

    private LinearLayout topbar;
    private LinearLayout divider;
    private RelativeLayout holder;
    private ImageView back;
    private TextView topbar_txt;
    private LinearLayout space_between_top;
    private ImageView done;
    private LinearLayout base;
    private LinearLayout firstname_holder;
    private LinearLayout space_between;
    private LinearLayout lastname_holder;
    private EditText firstname_edittext;
    private LinearLayout firstname_divider;
    private EditText lastname_edittext;
    private LinearLayout lastname_divider;

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
        View view = inflater.inflate(R.layout.edit_name, ((ViewGroup) fragmentView), false);
        ((ViewGroup) fragmentView).addView(view);
        initialize(context);
        com.google.firebase.FirebaseApp.initializeApp(context);
        initializeLogic();
        fragmentView.setOnTouchListener((v, event) -> false);
        return fragmentView;
    }

    private void initialize(Context context) {
        topbar = (LinearLayout) findViewById(R.id.topbar);
        divider = (LinearLayout) findViewById(R.id.divider);
        holder = (RelativeLayout) findViewById(R.id.holder);
        back = (ImageView) findViewById(R.id.back);
        topbar_txt = (TextView) findViewById(R.id.topbar_txt);
        space_between_top = (LinearLayout) findViewById(R.id.space_between_top);
        done = (ImageView) findViewById(R.id.done);
        base = (LinearLayout) findViewById(R.id.base);
        firstname_holder = (LinearLayout) findViewById(R.id.firstname_holder);
        space_between = (LinearLayout) findViewById(R.id.space_between);
        lastname_holder = (LinearLayout) findViewById(R.id.lastname_holder);
        firstname_edittext = (EditText) findViewById(R.id.firstname_edittext);
        firstname_divider = (LinearLayout) findViewById(R.id.firstname_divider);
        lastname_edittext = (EditText) findViewById(R.id.lastname_edittext);
        lastname_divider = (LinearLayout) findViewById(R.id.lastname_divider);
        Fauth = FirebaseAuth.getInstance();

        back.setOnClickListener(_view -> finishFragment());

        done.setOnClickListener(_view -> {
            if (firstname_edittext.getText().toString().equals("")) {
                SketchwareUtil.showMessage(getApplicationContext(), "Please fill your first name.");
            } else {
                if (lastname_edittext.getText().toString().equals("")) {
                    SketchwareUtil.showMessage(getApplicationContext(), "Please fill your last name.");
                } else {
                    updatingName = true;
                    NamePush = new HashMap<>();
                    NamePush.put("firstname", firstname_edittext.getText().toString());
                    NamePush.put("lastname", lastname_edittext.getText().toString());
                    users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(NamePush);
                    NamePush.clear();
                }
            }
        });

        firstname_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
                final String _charSeq = _param1.toString();
                if (_charSeq.length() < 1) {
                    done.setEnabled(false);
                } else {
                    done.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {

            }

            @Override
            public void afterTextChanged(Editable _param1) {

            }
        });

        _users_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    if (_childValue.containsKey("uid") && (_childValue.containsKey("firstname") && _childValue.containsKey("lastname"))) {
                        My_Firstname = _childValue.get("firstname").toString();
                        My_Lastname = _childValue.get("lastname").toString();
                        My_FirebaseUID = _childValue.get("uid").toString();
                        firstname_edittext.setText(My_Firstname);
                        lastname_edittext.setText(My_Lastname);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (updatingName) {
                    updatingName = false;
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
        done.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor("#FF193566"),
                Color.parseColor("#FF193566")}));
        topbar.setElevation((int) 2);
        androidx.appcompat.widget.TooltipCompat.setTooltipText(back, "Back");
        androidx.appcompat.widget.TooltipCompat.setTooltipText(done, "Done");
        firstname_edittext.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {

                firstname_divider.setBackgroundColor(0xFF193566);
                lastname_divider.setBackgroundColor(0xFFDADADA);
            } else {
                lastname_edittext.setOnFocusChangeListener((v1, hasFocus1) -> {
                    if (hasFocus1) {

                        firstname_divider.setBackgroundColor(0xFFDADADA);
                        lastname_divider.setBackgroundColor(0xFF193566);
                    } else {
                        firstname_divider.setBackgroundColor(0xFFDADADA);
                        lastname_divider.setBackgroundColor(0xFFDADADA);
                    }
                });
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        finishFragment();
        return false;
    }
}