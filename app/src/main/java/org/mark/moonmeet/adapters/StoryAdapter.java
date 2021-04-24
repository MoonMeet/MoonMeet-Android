package org.mark.moonmeet.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.mark.moonmeet.LaunchActivity;
import org.mark.moonmeet.R;
import org.mark.moonmeet.SketchwareUtil;
import org.mark.moonmeet.StoryActivity;
import org.mark.moonmeet.utils.AndroidUtilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {

    ArrayList<HashMap<String, Object>> _data;
    Context mContext;
    Intent toAddStory = new Intent();
    Calendar calendar;
    TimerTask TimeString;
    SharedPreferences sp_seen;
    Timer _timer = new Timer();
    ArrayList<String> StoriesUID;
    LaunchActivity launchActivity = new LaunchActivity();
    HashMap<String, Object> storiesMap = new HashMap<>();

    public StoryAdapter(Context context, HashMap<String, Object> map, ArrayList<String> storyUid, ArrayList<HashMap<String, Object>> _arr) {
        _data = _arr;
        mContext = context;
        storiesMap = map;
        StoriesUID = storyUid;
    }

    @Override
    public StoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater _inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View _v = _inflater.inflate(R.layout.storiesc, null);
        RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        _v.setLayoutParams(_lp);
        return new StoryAdapter.ViewHolder(_v);
    }

    @Override
    public void onBindViewHolder(StoryAdapter.ViewHolder _holder, final int _position) {
        View _view = _holder.itemView;

        sp_seen = mContext.getSharedPreferences("sp_seen", Activity.MODE_PRIVATE);
        final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
        final LinearLayout storylin = (LinearLayout) _view.findViewById(R.id.storylin);
        final TextView textview1 = (TextView) _view.findViewById(R.id.textview1);
        final LinearLayout propic_bg = (LinearLayout) _view.findViewById(R.id.propic_bg);
        final de.hdodenhof.circleimageview.CircleImageView circleimageview1 = (de.hdodenhof.circleimageview.CircleImageView) _view.findViewById(R.id.circleimageview1);
        final LinearLayout linear2 = (LinearLayout) _view.findViewById(R.id.linear2);

        RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        _view.setLayoutParams(_lp);
        if (_data.get((int)_position).containsKey("uid")) {
            // Define
            if (StoriesUID.contains(_data.get((int)_position).get("uid").toString())) {
                linear1.setVisibility(View.GONE);
                TimeString = new TimerTask() {
                    @Override
                    public void run() {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                StoriesUID.clear();
                            }
                        });
                    }
                };
                _timer.schedule(TimeString, (int)(250));
            }
            else {
                StoriesUID.add(_data.get((int)_position).get("uid").toString());
                if (storiesMap.containsKey(_data.get((int)_position).get("uid").toString())) {
                    if (_data.get((int)_position).containsKey("firstname") && _data.get((int)_position).containsKey("lastname")) {
                        textview1.setText(_data.get((int)_position).get("firstname").toString().concat(" ".concat(_data.get((int)_position).get("lastname").toString())));
                    }
                    calendar = Calendar.getInstance();
                    if ((calendar.getTimeInMillis() - Double.parseDouble(_data.get((int)_position).get("time").toString())) < 30000) {
                        linear2.setVisibility(View.VISIBLE);
                    }
                    else {
                        linear2.setVisibility(View.INVISIBLE);
                    }
                    if (sp_seen.getString(storiesMap.get(_data.get((int)_position).get("uid").toString()).toString(), "").equals("")) {
                        android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
                        gd.setColor(Color.parseColor("#FFECF0F3"));
                        gd.setStroke((int)5, Color.parseColor("#FF193566"));
                        gd.setCornerRadii(new float[]{(int) SketchwareUtil.getDip(mContext, (int)(100)),(int)SketchwareUtil.getDip(mContext, (int)(100)),(int)SketchwareUtil.getDip(mContext, (int)(100)),(int)SketchwareUtil.getDip(mContext, (int)(100)),(int)SketchwareUtil.getDip(mContext, (int)(100)),(int)SketchwareUtil.getDip(mContext, (int)(100)),(int)SketchwareUtil.getDip(mContext, (int)(100)),(int)SketchwareUtil.getDip(mContext, (int)(100))});
                        storylin.setBackground(gd);
                    }
                    else {
                        android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();

                        gd.setColor(Color.parseColor("#FFECF0F3"));

                        gd.setStroke((int)5, Color.parseColor("#FFDADADA"));

                        gd.setCornerRadii(new float[]{(int)SketchwareUtil.getDip(mContext, (int)(100)),(int)SketchwareUtil.getDip(mContext, (int)(100)),(int)SketchwareUtil.getDip(mContext, (int)(100)),(int)SketchwareUtil.getDip(mContext, (int)(100)),(int)SketchwareUtil.getDip(mContext, (int)(100)),(int)SketchwareUtil.getDip(mContext, (int)(100)),(int)SketchwareUtil.getDip(mContext, (int)(100)),(int)SketchwareUtil.getDip(mContext, (int)(100))});

                        storylin.setBackground(gd);
                    }
                    if (_data.get((int)_position).containsKey("image")) {
                        circleimageview1.setVisibility(View.VISIBLE);
                        linear2.setVisibility(View.INVISIBLE);
                        com.bumptech.glide.Glide.with(mContext)
                                .load(_data.get((int)_position).get("image").toString())
                                .override(50, 50)
                                .into(circleimageview1);
                    }
                    else {
                        if (_data.get((int)_position).containsKey("text")) {
                            com.bumptech.glide.Glide.with(mContext)
                                    .load(_data.get((int)_position).get("avatar").toString())
                                    .override(50, 50)
                                    .into(circleimageview1);
                            circleimageview1.setVisibility(View.VISIBLE);
                            linear2.setVisibility(View.INVISIBLE);
                        }
                    }
                    linear1.setOnClickListener(_view1 -> {
                        toAddStory.setClass(mContext, StoryActivity.class);
                        toAddStory.putExtra("uid", _data.get((int)_position).get("uid").toString());
                        mContext.startActivity(toAddStory);
                    });
                }
                else {
                    linear1.setVisibility(View.GONE);
                }
            }
        }
        else {
            linear1.setVisibility(View.GONE);
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