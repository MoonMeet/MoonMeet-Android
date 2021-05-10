package org.mark.moonmeet.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import org.mark.axemojiview.view.AXEmojiTextView;
import org.mark.moonmeet.R;
import org.mark.moonmeet.utils.NotificationCenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class LastChatsAdapter extends RecyclerView.Adapter<LastChatsAdapter.ViewHolder> {

    ArrayList<HashMap<String, Object>> _data;
    Intent toGoChat = new Intent();
    Calendar Cal;
    Intent toViewStory = new Intent();
    Context mContext;
    Calendar c;
    double time;
    SharedPreferences sp_seen;
    HashMap<String, Object> storiesMap = new HashMap<>();
    HashMap<String, Object> UserDataGetAvatar = new HashMap<>();
    HashMap<String, Object> UserDataGetName = new HashMap<>();

    public LastChatsAdapter(Context context, HashMap<String, Object> UserDataGetAvatar, HashMap<String, Object> UserDataGetName, HashMap<String, Object> map, ArrayList<HashMap<String, Object>> _arr) {
        _data = _arr;
        mContext = context;
        storiesMap = map;
        this.UserDataGetAvatar = UserDataGetAvatar;
        this.UserDataGetName=  UserDataGetName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater _inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View _v = _inflater.inflate(R.layout.chatsc, null);
        RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        _v.setLayoutParams(_lp);
        return new ViewHolder(_v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder _holder, final int _position) {
        View _view = _holder.itemView;

        final LinearLayout linear9 = (LinearLayout) _view.findViewById(R.id.linear9);
        final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
        final LinearLayout nothing = (LinearLayout) _view.findViewById(R.id.nothing);
        final LinearLayout storylin = (LinearLayout) _view.findViewById(R.id.storylin);
        final LinearLayout linear2 = (LinearLayout) _view.findViewById(R.id.linear2);
        final LinearLayout propic_bg = (LinearLayout) _view.findViewById(R.id.propic_bg);
        final de.hdodenhof.circleimageview.CircleImageView circleimageview2 = (de.hdodenhof.circleimageview.CircleImageView) _view.findViewById(R.id.circleimageview2);
        final LinearLayout linear8 = (LinearLayout) _view.findViewById(R.id.linear8);
        final LinearLayout linear5 = (LinearLayout) _view.findViewById(R.id.linear5);
        final LinearLayout linear4 = (LinearLayout) _view.findViewById(R.id.linear4);
        final TextView name = (TextView) _view.findViewById(R.id.name);
        final TextView time = (TextView) _view.findViewById(R.id.time);
        final LinearLayout linear10 = (LinearLayout) _view.findViewById(R.id.linear10);
        final LinearLayout unread_lin = (LinearLayout) _view.findViewById(R.id.unread_lin);
        final AXEmojiTextView content = (AXEmojiTextView) _view.findViewById(R.id.content);
        final ImageView imageview2 = (ImageView) _view.findViewById(R.id.imageview2);
        final TextView textview1 = (TextView) _view.findViewById(R.id.textview1);

        RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        _view.setLayoutParams(_lp);
        sp_seen = mContext.getSharedPreferences("sp_seen", Activity.MODE_PRIVATE);
        _round(propic_bg, 360, "#193566");
        _round(unread_lin, 360, "#193566");
        name.setTextColor(0xFF607D8B);
        name.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/roboto_regular.ttf"), 0);
        time.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/roboto_light.ttf"), 0);
        content.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/roboto_regular.ttf"), 0);
        textview1.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/roboto_light.ttf"), 0);
        unread_lin.setVisibility(View.INVISIBLE);
        imageview2.setVisibility(View.GONE);
        time.setVisibility(View.INVISIBLE);
        if (_data.get((int) _position).containsKey("uid") && (_data.get((int) _position).containsKey("firstname") && (_data.get((int) _position).containsKey("lastname") && (_data.get((int) _position).containsKey("avatar") && _data.get((int) _position).containsKey("last_message_uid"))))) {
            if (!_data.get((int) _position).get("uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                imageview2.setColorFilter(0xFF193566, PorterDuff.Mode.MULTIPLY);
                _radius_4("#64bb6a", "#ffffff", 6, 40, 40, 40, 40, linear8);
                if (UserDataGetName.get(_data.get((int) _position).get("uid").toString()).equals("") && UserDataGetName.get(_data.get((int) _position).get("uid").toString()).equals("")) {
                    linear9.setVisibility(View.VISIBLE);
                } else {
                    name.setText((String) UserDataGetName.get(_data.get(_position).get("uid").toString()));
                }
                if (UserDataGetAvatar.get(_data.get((int) _position).get("uid").toString()).toString().equals("")) {
                    linear9.setVisibility(View.VISIBLE);
                } else {
                    Glide.with(mContext)
                            .load(UserDataGetAvatar.get(_data.get((int) _position).get("uid").toString()))
                            .override(50, 50)
                            .into(circleimageview2);
                }
                linear8.setVisibility(View.INVISIBLE);
                if (!_data.get((int) _position).get("lastseen").toString().equals("")) {
                    Cal = Calendar.getInstance();
                    if ((Cal.getTimeInMillis() - Double.parseDouble(_data.get((int) _position).get("lastseen").toString())) < 30000) {
                        linear8.setVisibility(View.VISIBLE);
                    }
                }
                if (_data.get((int) _position).containsKey("last_message_time")) {
                    _Time(Double.parseDouble(_data.get((int) _position).get("last_message_time").toString()), time);
                    time.setVisibility(View.VISIBLE);
                }
                if (_data.get((int) _position).containsKey("last_message_uid")) {
                    if (_data.get((int) _position).containsKey("last_message_deleted")) {
                        if (_data.get((int) _position).get("last_message_deleted").toString().equals("true")) {
                            imageview2.setVisibility(View.GONE);
                            content.setText("Message Deleted.");
                            if (!_data.get((int) _position).get("firstname").toString().equals("")) {
                                content.setText(_data.get((int) _position).get("firstname").toString().concat(" Deleted a message."));
                            }
                        }
                    } else {
                        if (_data.get((int) _position).get("last_message_uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                            content.setText("You : ");
                        } else {
                            content.setText("");
                            if (_data.get((int) _position).containsKey("last_message_status")) {
                                if (_data.get((int) _position).get("last_message_status").toString().equals("Sent")) {
                                    content.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/roboto_light.ttf"), 1);
                                } else {
                                    content.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/roboto_light.ttf"), 0);
                                }
                            }
                        }
                    }
                    if (_data.get((int) _position).containsKey("last_message_type")) {
                        if (_data.get((int) _position).get("last_message_type").toString().equals("image")) {
                            if (_data.get((int) _position).get("last_message_uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                content.setText("You sent an image.");
                                content.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/roboto_regular.ttf"), 2);
                            } else {
                                content.setText(_data.get((int) _position).get("firstname").toString().concat(" Sent a photo."));
                                if (_data.get((int) _position).containsKey("last_message_status")) {
                                    if (_data.get((int) _position).get("last_message_status").toString().equals("Sent")) {
                                        content.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/roboto_light.ttf"), 3);
                                    } else {
                                        content.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/roboto_light.ttf"), 2);
                                    }
                                }
                            }
                        } else {
                            if (_data.get((int) _position).get("last_message_type").toString().equals("love")) {
                                imageview2.setVisibility(View.VISIBLE);
                            } else {
                                if (_data.get((int) _position).get("last_message_type").toString().equals("text")) {
                                    if (_data.get((int) _position).containsKey("last_message_text")) {
                                        content.setText(content.getText().toString().concat(_data.get((int) _position).get("last_message_text").toString()));
                                    } else {
                                        content.setText("Start chatting with ".concat(_data.get((int) _position).get("firstname").toString()));
                                    }
                                } else {
                                    content.setText("Start chatting with ".concat(_data.get((int) _position).get("firstname").toString()));
                                }
                            }
                            if (_data.get((int) _position).containsKey("UnreadMessagesNum")) {
                                if (_data.get((int) _position).get("UnreadMessagesNum").toString().equals("")) {
                                    unread_lin.setVisibility(View.INVISIBLE);
                                } else {
                                    if (_data.get((int) _position).get("UnreadMessagesNum").toString().equals("0")) {
                                        unread_lin.setVisibility(View.INVISIBLE);
                                    } else {
                                        textview1.setText(_data.get((int) _position).get("UnreadMessagesNum").toString());
                                        unread_lin.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                unread_lin.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                } else {
                    linear9.setVisibility(View.GONE);
                }
                if (storiesMap.containsKey(_data.get((int) _position).get("uid").toString())) {
                    if (sp_seen.getString(storiesMap.get(_data.get((int) _position).get("uid").toString()).toString(), "").equals("")) {
                        _radius_4("#00000000", "#FF193566", 8, 100, 100, 100, 100, storylin);
                    } else {
                        _radius_4("#00000000", "#FFDADADA", 8, 100, 100, 100, 100, storylin);
                    }
                    storylin.setOnClickListener(_view12 -> {
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.didClickStory, _data.get((int) _position).get("uid").toString());
                    });
                } else {
                    _radius_4("#00000000", "#00000000", 8, 100, 100, 100, 100, storylin);
                }
                linear1.setOnClickListener(_view1 -> {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.didClickConversation, _data.get(_position).get("uid").toString(), "private");
                });
            } else {
                linear9.setVisibility(View.GONE);
            }
        } else {
            linear9.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return _data.size();
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
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Error")
                    .setMessage(error_code)
                    .setCancelable(false)
                    .setNegativeButton("ОК", (dialog, id) -> {
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public void _Time(final double _position, final TextView _textview) {
        c = Calendar.getInstance();
        time = c.getTimeInMillis() - _position;
        if (((time / 1000) / 3600) < 24) {
            c.setTimeInMillis((long) (_position));
            _textview.setText(new SimpleDateFormat("hh:mm a").format(c.getTime()));
        } else {
            c.setTimeInMillis((long) (_position));
            _textview.setText(new SimpleDateFormat("MMM d 'at' hh:mm a").format(c.getTime()));
        }
    }

    public void _radius_4(final String _color1, final String _color2, final double _str, final double _n1, final double _n2, final double _n3, final double _n4, final View _view) {
        android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
        gd.setColor(Color.parseColor(_color1));
        gd.setStroke((int) _str, Color.parseColor(_color2));
        gd.setCornerRadii(new float[]{(int) _n1, (int) _n1, (int) _n2, (int) _n2, (int) _n3, (int) _n3, (int) _n4, (int) _n4});
        _view.setBackground(gd);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }

}