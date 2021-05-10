package org.mark.moonmeet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import org.mark.moonmeet.ui.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;

public class DiscoverActivity extends BaseFragment {

    private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();

    private ArrayList<HashMap<String, Object>> discover_map = new ArrayList<>();
    private ArrayList<String> discover_string = new ArrayList<>();

    private LinearLayout linear1;
    private FrameLayout holder;
    private ImageView imageview1;
    private TextView textview1;
    private LinearLayout nopplyet;
    private RecyclerView recyclerview1;
    private MaterialTextView nopplyet_full_txt;
    private MaterialTextView nopplyet_mini_txt;

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
    private Intent toChat = new Intent();

    @Override
    public View createView(Context context) {
        fragmentView = new FrameLayout(context);
        actionBar.setAddToContainer(false);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.discover, ((ViewGroup) fragmentView), false);
        ((ViewGroup) fragmentView).addView(view);
        initialize(context);
        com.google.firebase.FirebaseApp.initializeApp(getParentActivity());
        initializeLogic();
        return fragmentView;
    }

    private void initialize(Context context) {
        linear1 = (LinearLayout) findViewById(R.id.linear1);
        holder = (FrameLayout) findViewById(R.id.holder);
        imageview1 = (ImageView) findViewById(R.id.imageview1);
        textview1 = (TextView) findViewById(R.id.textview1);
        nopplyet = (LinearLayout) findViewById(R.id.nopplyet);
        recyclerview1 = (RecyclerView) findViewById(R.id.recyclerview1);
        nopplyet_full_txt = (MaterialTextView) findViewById(R.id.nopplyet_full_txt);
        nopplyet_mini_txt = (MaterialTextView) findViewById(R.id.nopplyet_mini_txt);
        Fauth = FirebaseAuth.getInstance();

        imageview1.setOnClickListener(_view -> finishFragment());

        _users_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childValue.containsKey("uid")) {
                    if (discover_string.contains(_childValue.get("uid").toString())) {

                    } else {
                        discover_map.add(_childValue);
                        discover_string.add(_childValue.get("uid").toString());
                        recyclerview1.setAdapter(new Recyclerview1Adapter(discover_map));
                        recyclerview1.getAdapter().notifyDataSetChanged();
                        nopplyet.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childValue.containsKey("uid")) {
                    if (discover_string.contains(_childValue.get("uid").toString())) {

                    } else {
                        discover_map.add(_childValue);
                        discover_string.add(_childValue.get("uid").toString());
                        recyclerview1.setAdapter(new Recyclerview1Adapter(discover_map));
                        recyclerview1.getAdapter().notifyDataSetChanged();
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
        linear1.setElevation((int) 2);
        imageview1.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor("#FF193566"),
                Color.parseColor("#FFDADADA")}));
        LinearLayoutManager aLinearLayoutManager = new LinearLayoutManager(getParentActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerview1.setLayoutManager(aLinearLayoutManager);
        recyclerview1.setItemViewCacheSize(60);
        recyclerview1.setDrawingCacheEnabled(true);
        recyclerview1.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerview1.setItemAnimator(new DefaultItemAnimator());
        recyclerview1.setHasFixedSize(true);
        androidx.appcompat.widget.TooltipCompat.setTooltipText(imageview1, "Back");
    }

    @Override
    public boolean onBackPressed() {
        finishFragment();
        return false;
    }


    public void _RippleEffects(final String _color, final View _view) {
        android.content.res.ColorStateList clr = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(_color)});
        android.graphics.drawable.RippleDrawable ripdr = new android.graphics.drawable.RippleDrawable(clr, null, null);
        _view.setBackground(ripdr);
    }

    public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;

        public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = (LayoutInflater) getParentActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View _v = _inflater.inflate(R.layout.viewsc, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new ViewHolder(_v);
        }

        @Override
        public void onBindViewHolder(ViewHolder _holder, final int _position) {
            View _view = _holder.itemView;

            final LinearLayout linear4 = (LinearLayout) _view.findViewById(R.id.linear4);
            final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
            final LinearLayout nothing = (LinearLayout) _view.findViewById(R.id.nothing);
            final LinearLayout propic_bg = (LinearLayout) _view.findViewById(R.id.propic_bg);
            final LinearLayout linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
            final de.hdodenhof.circleimageview.CircleImageView circleimageview2 = (de.hdodenhof.circleimageview.CircleImageView) _view.findViewById(R.id.circleimageview2);
            final TextView textview1 = (TextView) _view.findViewById(R.id.textview1);
            final TextView textview2 = (TextView) _view.findViewById(R.id.textview2);

            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _view.setLayoutParams(_lp);
            if (_data.get((int) _position).containsKey("uid") && (_data.get((int) _position).containsKey("firstname") && (_data.get((int) _position).containsKey("lastname") && (_data.get((int) _position).containsKey("session") && (_data.get((int) _position).containsKey("last_seen") && _data.get((int) _position).containsKey("username")))))) {
                if (!_data.get((int) _position).get("uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    if (_data.get((int) _position).containsKey("firstname") && _data.get((int) _position).containsKey("lastname")) {
                        textview1.setText(_data.get((int) _position).get("firstname").toString().concat(" ".concat(_data.get((int) _position).get("lastname").toString())));
                    }
                    if (_data.get((int) _position).containsKey("username")) {
                        textview2.setText(" @".concat(_data.get((int) _position).get("username").toString()));
                    }
                    if (_data.get((int) _position).containsKey("avatar")) {
                        circleimageview2.setVisibility(View.VISIBLE);
                        com.bumptech.glide.Glide.with(getApplicationContext())
                                .load(_data.get((int) _position).get("avatar").toString())
                                .override(50, 50)
                                .into(circleimageview2);
                    }
                    linear4.setOnClickListener(_view1 -> {
                        presentFragment(new ChatActivity(_data.get((int) _position).get("uid").toString(), "private"));
                    });
                } else {
                    linear4.setVisibility(View.GONE);
                }
            } else {
                linear4.setVisibility(View.GONE);
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