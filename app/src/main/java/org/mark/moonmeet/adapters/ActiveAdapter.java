package org.mark.moonmeet.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import org.mark.moonmeet.ChatActivity;
import org.mark.moonmeet.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ActiveAdapter extends RecyclerView.Adapter<ActiveAdapter.ViewHolder> {

    ArrayList<HashMap<String, Object>> _data;
    Calendar Cal;
    Context mContext;
    HashMap<String, Object> storiesMap = new HashMap<>();

    public ActiveAdapter(Context context, HashMap<String, Object> map, ArrayList<HashMap<String, Object>> _arr) {
        _data = _arr;
        mContext = context;
        storiesMap = map;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater _inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View _v = _inflater.inflate(R.layout.storiesc, null);
        RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        _v.setLayoutParams(_lp);
        return new ViewHolder(_v);
    }

    @Override
    public void onBindViewHolder(ViewHolder _holder, final int _position) {
        View _view = _holder.itemView;

        final LinearLayout linear1 = _view.findViewById(R.id.linear1);
        final LinearLayout storylin = _view.findViewById(R.id.storylin);
        final TextView textview1 = _view.findViewById(R.id.textview1);
        final LinearLayout propic_bg = _view.findViewById(R.id.propic_bg);
        final de.hdodenhof.circleimageview.CircleImageView circleimageview1 = (de.hdodenhof.circleimageview.CircleImageView) _view.findViewById(R.id.circleimageview1);
        final LinearLayout linear2 = _view.findViewById(R.id.linear2);

        _radius_4("#FF64BBA6", "#FFECF0F3", 8, 360, 360, 360, 360, linear2);
        RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        _view.setLayoutParams(_lp);
        if (_data.get((int) _position).containsKey("uid") && (_data.get((int) _position).containsKey("firstname") && (_data.get((int) _position).containsKey("lastname") && (_data.get((int) _position).containsKey("avatar") && _data.get((int) _position).containsKey("last_seen"))))) {
            // Define
            if (!_data.get((int) _position).get("uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                if (_data.get((int) _position).get("last_seen").toString().equals("private") || _data.get((int) _position).get("last_seen").toString().equals("")) {
                    linear1.setVisibility(View.GONE);
                } else {
                    Cal = Calendar.getInstance();
                    if ((Cal.getTimeInMillis() - Double.parseDouble(_data.get((int) _position).get("last_seen").toString())) < 30000) {
                        if (storiesMap.containsKey("uid")) {
                            if (storiesMap.containsKey(storiesMap.get(_data.get((int) _position).get("uid").toString()).toString())) {
                                _radius_4("#00000000", "#FF193566", 8, 360, 360, 360, 360, storylin);
                            } else {
                                _radius_4("#00000000", "#DADADA", 8, 360, 360, 360, 360, storylin);
                            }
                        }
                        // Data
                        if (_data.get((int) _position).containsKey("avatar")) {
                            com.bumptech.glide.Glide.with(mContext)
                                    .load(_data.get(_position).get("avatar").toString())
                                    .override(50, 50)
                                    .into(circleimageview1);
                        }
                        if (_data.get((int) _position).containsKey("firstname") && _data.get((int) _position).containsKey("lastname")) {
                            textview1.setText(_data.get(_position).get("firstname").toString().concat(" ".concat(_data.get((int) _position).get("lastname").toString())));
                        }
                        linear1.setOnClickListener(_view1 -> {
                            try {
                                Intent toGoChat = new Intent(mContext, ChatActivity.class);
                                toGoChat.putExtra("uid", _data.get(_position).get("uid").toString());
                                toGoChat.putExtra("type", "private");
                                mContext.startActivity(toGoChat);
                            } catch (Exception e) {
                                Toast.makeText(mContext, e.toString(), 0).show();
                            }
                        });
                    } else {
                        linear1.setVisibility(View.GONE);
                    }
                }
            } else {
                linear1.setVisibility(View.GONE);
            }
        } else {
            linear1.setVisibility(View.GONE);
        }
    }

    public void _radius_4(final String _color1, final String _color2, final double _str, final double _n1, final double _n2, final double _n3, final double _n4, final View _view) {
        android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
        gd.setColor(Color.parseColor(_color1));
        gd.setStroke((int) _str, Color.parseColor(_color2));
        gd.setCornerRadii(new float[]{(int) _n1, (int) _n1, (int) _n2, (int) _n2, (int) _n3, (int) _n3, (int) _n4, (int) _n4});
        _view.setBackground(gd);
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