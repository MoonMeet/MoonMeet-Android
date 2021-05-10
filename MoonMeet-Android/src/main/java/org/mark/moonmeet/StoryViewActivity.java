package org.mark.moonmeet;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;
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
import java.util.Timer;
import java.util.TimerTask;

public class StoryViewActivity extends BaseFragment {

    private Timer TimerComponent = new Timer();
    private final FirebaseDatabase firebase = FirebaseDatabase.getInstance();

    private String sid = "";
    private String StoryViews_str = "";

    private ArrayList<HashMap<String, Object>> StoryViews_map = new ArrayList<>();

    private LinearLayout linear1;
    private LinearLayout noviewersyet;
    private RecyclerView recyclerview1;
    private ImageView imageview1;
    private TextView textview1;
    private MaterialTextView noviewersyet_full_txt;
    private MaterialTextView noviewersyet_mini_txt;

    private FirebaseAuth Fauth;

    private DatabaseReference StoryViews = firebase.getReference("StoryViews");
    private ChildEventListener StoryViews_child_listener;
    private TimerTask timer;

    public StoryViewActivity(Bundle args) {
        super(args);
    }

    @Override
    public View createView(Context context) {
        fragmentView = new FrameLayout(context);
        actionBar.setAddToContainer(false);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.storyview, ((ViewGroup) fragmentView), false);
        ((ViewGroup) fragmentView).addView(view);
        initialize(context);
        com.google.firebase.FirebaseApp.initializeApp(getParentActivity());
        initializeLogic();
        return fragmentView;
    }

    private void initialize(Context context) {
        linear1 = findViewById(R.id.linear1);
        noviewersyet = findViewById(R.id.noviewersyet);
        recyclerview1 = findViewById(R.id.recyclerview1);
        imageview1 = findViewById(R.id.imageview1);
        textview1 = findViewById(R.id.textview1);
        noviewersyet_full_txt = findViewById(R.id.noviewersyet_full_txt);
        noviewersyet_mini_txt = findViewById(R.id.noviewersyet_mini_txt);
        Fauth = FirebaseAuth.getInstance();

        imageview1.setOnClickListener(view -> finishFragment());

        StoryViews_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot param1, String param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String childKey = param1.getKey();
                final HashMap<String, Object> childValue = param1.getValue(_ind);
                StoryViews_map.add(childValue);
                if (StoryViews_map.size() > 0) {
                    noviewersyet.setVisibility(View.GONE);
                    recyclerview1.setAdapter(new Recyclerview1Adapter(StoryViews_map));
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        };
        StoryViews.addChildEventListener(StoryViews_child_listener);
    }

    private void initializeLogic() {
        linear1.setElevation((int) 2);
        imageview1.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor("#FF193566"),
                Color.parseColor("#FFDADADA")}));
        sid = getArguments().getString("sid");
        StoryViews.removeEventListener(StoryViews_child_listener);
        StoryViews_str = "StoryViews/".concat(sid);
        StoryViews = firebase.getReference(StoryViews_str);
        recyclerview1.setLayoutManager(new LinearLayoutManager(getParentActivity()));
        StoryViews.addChildEventListener(StoryViews_child_listener);
        androidx.appcompat.widget.TooltipCompat.setTooltipText(imageview1, "Back");
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }


    public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> data;

        public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> arr) {
            data = arr;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.viewsc, null);
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            v.setLayoutParams(layoutParams);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            View view = holder.itemView;

            final LinearLayout linear4 = view.findViewById(R.id.linear4);
            final LinearLayout linear1 = view.findViewById(R.id.linear1);
            final LinearLayout nothing = view.findViewById(R.id.nothing);
            final LinearLayout propic_bg = view.findViewById(R.id.propic_bg);
            final LinearLayout linear3 = view.findViewById(R.id.linear3);
            final de.hdodenhof.circleimageview.CircleImageView circleimageview2 = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.circleimageview2);
            final TextView textview1 = view.findViewById(R.id.textview1);
            final TextView textview2 = view.findViewById(R.id.textview2);

            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(layoutParams);
            if (data.get((int) position).containsKey("uid")) {
                //  Data
                if (data.get((int) position).containsKey("firstname") && data.get((int) position).containsKey("lastname")) {
                    textview1.setText(data.get((int) position).get("firstname").toString().concat(" ".concat(data.get((int) position).get("lastname").toString())));
                }
                if (data.get((int) position).containsKey("avatar")) {
                    circleimageview2.setVisibility(View.VISIBLE);
                    Glide.with(getApplicationContext()).load(Uri.parse(data.get((int) position).get("avatar").toString())).into(circleimageview2);
                }
                if (data.get((int) position).containsKey("username")) {
                    textview2.setText(" @".concat(data.get((int) position).get("username").toString()));
                }
                linear4.setOnClickListener(v -> {
                    presentFragment(new ChatActivity(data.get((int) position).get("uid").toString(), "private"));
                });
            } else {
                linear4.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View v) {
                super(v);
            }
        }

    }
}