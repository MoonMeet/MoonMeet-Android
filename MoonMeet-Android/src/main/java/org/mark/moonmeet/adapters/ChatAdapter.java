package org.mark.moonmeet.adapters;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.mark.axemojiview.view.AXEmojiTextView;
import org.mark.moonmeet.R;
import org.mark.moonmeet.SketchwareUtil;
import org.mark.moonmeet.utils.AndroidUtilities;
import org.mark.moonmeet.utils.NotificationCenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    ArrayList<HashMap<String, Object>> _data;
    Context mContext;
    Calendar CALENDAR_EXTRA;
    Intent toView = new Intent();
    RecyclerView mRecyclerView;
    Timer _timer = new Timer();
    FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
    DatabaseReference reports = _firebase.getReference("reports");
    ChildEventListener _reports_child_listener;
    HashMap<String, Object> ChatSettingsMap = new HashMap<>();
    String mUid;
    double time = 0;
    DatabaseReference Chat1 = _firebase.getReference("Chatroom");
    ChildEventListener _Chat1_child_listener;
    DatabaseReference Chat2 = _firebase.getReference("Chatcopy");
    ChildEventListener _Chat2_child_listener;
    String type = "private";
    FirebaseAuth Fauth;
    TimerTask ScrollingTimer;
    Boolean replying;
    String btmsheet_uid = "";
    double btmsheet_pos = 0;
    double CurrentDeletingPosition = 0;
    String btmsheet_id = "";

    public ChatAdapter(String uid, RecyclerView recyclerView, Context context, ArrayList<HashMap<String, Object>> _arr) {
        _data = _arr;
        mContext = context;
        mRecyclerView = recyclerView;
        mUid = uid;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater _inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View _v = _inflater.inflate(R.layout.messagec, null);
        RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        _v.setLayoutParams(_lp);
        return new ViewHolder(_v);
    }

    @Override
    public void onBindViewHolder(ViewHolder _holder, final int _position) {
        View _view = _holder.itemView;
        try {
            final LinearLayout bg = (LinearLayout) _view.findViewById(R.id.bg);
            final LinearLayout background = (LinearLayout) _view.findViewById(R.id.background);
            final ImageView love = (ImageView) _view.findViewById(R.id.love);
            final androidx.cardview.widget.CardView cardview = (androidx.cardview.widget.CardView) _view.findViewById(R.id.cardview);
            final LinearLayout corner = (LinearLayout) _view.findViewById(R.id.corner);
            final LinearLayout in_message_holder = (LinearLayout) _view.findViewById(R.id.in_message_holder);
            final LinearLayout corner2 = (LinearLayout) _view.findViewById(R.id.corner2);
            final LinearLayout second_all_in_message_holder = (LinearLayout) _view.findViewById(R.id.second_all_in_message_holder);
            final LinearLayout reply_top = (LinearLayout) _view.findViewById(R.id.reply_top);
            final LinearLayout message_down = (LinearLayout) _view.findViewById(R.id.message_down);
            final LinearLayout reply_in_top_divider = (LinearLayout) _view.findViewById(R.id.reply_in_top_divider);
            final LinearLayout small_in_reply = (LinearLayout) _view.findViewById(R.id.small_in_reply);
            final TextView reply_name = (TextView) _view.findViewById(R.id.reply_name);
            final LinearLayout message_holder_reply = (LinearLayout) _view.findViewById(R.id.message_holder_reply);
            final ImageView reply_img = (ImageView) _view.findViewById(R.id.reply_img);
            final AXEmojiTextView reply_message = (AXEmojiTextView) _view.findViewById(R.id.reply_message);
            final TextView time2 = (TextView) _view.findViewById(R.id.time2);
            final AXEmojiTextView message = (AXEmojiTextView) _view.findViewById(R.id.message);
            final TextView time = (TextView) _view.findViewById(R.id.time);
            final LinearLayout image_holder = (LinearLayout) _view.findViewById(R.id.image_holder);
            final ImageView image = (ImageView) _view.findViewById(R.id.image);

            image_holder.setBackgroundColor(0xFF193566);
            cardview.setCardBackgroundColor(Color.TRANSPARENT);
            cardview.setRadius((float) 10);
            cardview.setCardElevation((float) 1);
            cardview.setPreventCornerOverlap(true);
            cardview.setUseCompatPadding(true);
            _ICC(love, "#FF193566", "#FF193566");
            if (_data.get((int) _position).containsKey("uid")) {
                if (_data.get((int) _position).get("uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    bg.setGravity(Gravity.RIGHT);
                    love.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor("#193566"),
                            Color.parseColor("#193566")}));
                    corner.setVisibility(View.GONE);
                    time.setVisibility(View.GONE);
                    corner2.setVisibility(View.VISIBLE);
                    time2.setVisibility(View.VISIBLE);
                    reply_img.setVisibility(View.GONE);
                    _radius_4("#FF193566", "#00000000", 0, SketchwareUtil.getDip(mContext, (int) (8)), SketchwareUtil.getDip(mContext, (int) (8)), SketchwareUtil.getDip(mContext, (int) (8)), SketchwareUtil.getDip(mContext, (int) (8)), background);
                    _radius_4("#FF193566", "#00000000", 0, SketchwareUtil.getDip(mContext, (int) (8)), SketchwareUtil.getDip(mContext, (int) (8)), 0, SketchwareUtil.getDip(mContext, (int) (8)), second_all_in_message_holder);
                    _radius_4("#FFFFFFFF", "#00000000", 0, 0, 0, 0, SketchwareUtil.getDip(mContext, (int) (16)), corner2);
                    _radius_4("#FFFFFFFF", "#00000000", 0, 6, 6, 6, 6, reply_in_top_divider);
                    reply_name.setTextColor(0xFFFFFFFF);
                    reply_message.setTextColor(0xFFFFFFFF);
                    time2.setTextColor(0xFFFFFFFF);
                    message.setTextColor(0xFFFFFFFF);
                    time.setTextColor(0xFFFFFFFF);
                    image_holder.setBackgroundColor(0xFF193566);
                    background.setElevation((int) 1);
                } else {
                    bg.setGravity(Gravity.LEFT);
                    love.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor("#8C92AC"),
                            Color.parseColor("#8C92AC")}));
                    image_holder.setBackgroundColor(0xFF8C92AC);
                    corner2.setVisibility(View.GONE);
                    time2.setVisibility(View.GONE);
                    corner.setVisibility(View.VISIBLE);
                    time.setVisibility(View.VISIBLE);
                    reply_img.setVisibility(View.GONE);
                    _radius_4("#FF8C92AC", "#00000000", 0, SketchwareUtil.getDip(mContext, (int) (8)), SketchwareUtil.getDip(mContext, (int) (8)), SketchwareUtil.getDip(mContext, (int) (8)), SketchwareUtil.getDip(mContext, (int) (8)), background);
                    _radius_4("#FF8C92AC", "#00000000", 0, SketchwareUtil.getDip(mContext, (int) (8)), SketchwareUtil.getDip(mContext, (int) (8)), SketchwareUtil.getDip(mContext, (int) (8)), 0, second_all_in_message_holder);
                    _radius_4("#FFFFFFFF", "#00000000", 0, 0, 0, SketchwareUtil.getDip(mContext, (int) (16)), 0, corner);
                    _radius_4("#FFFFFFFF", "#00000000", 0, 4, 4, 4, 4, reply_in_top_divider);
                    background.setElevation((int) 1);
                    reply_name.setTextColor(0xFFFFFFFF);
                    reply_message.setTextColor(0xFFFFFFFF);
                    time2.setTextColor(0xFFFFFFFF);
                    message.setTextColor(0xFFFFFFFF);
                    time.setTextColor(0xFFFFFFFF);
                }
                if (_data.get((int) _position).containsKey("type")) {
                    if (_data.get((int)_position).containsKey("type")) {
                        if (_data.get((int)_position).get("type").toString().equals("message")) {
                            background.setVisibility(View.VISIBLE);
                            reply_top.setVisibility(View.GONE);
                            message.setText(_data.get((int)_position).get("text").toString());
                            cardview.setVisibility(View.GONE);
                            love.setVisibility(View.GONE);
                        }
                        else {
                            if (_data.get((int)_position).get("type").toString().equals("reply")) {
                                background.setVisibility(View.VISIBLE);
                                reply_top.setVisibility(View.VISIBLE);
                                message.setText(_data.get((int)_position).get("text").toString());
                                reply_name.setText(_data.get((int)_position).get("replyed_name").toString());
                                reply_message.setText(_data.get((int)_position).get("replyed_message").toString());
                                love.setVisibility(View.GONE);
                                cardview.setVisibility(View.GONE);
                            }
                            else {
                                if (_data.get((int)_position).get("type").toString().equals("image")) {
                                    image_holder.setVisibility(View.VISIBLE);
                                    love.setVisibility(View.GONE);
                                    image.setVisibility(View.VISIBLE);
                                    background.setVisibility(View.GONE);
                                    cardview.setVisibility(View.VISIBLE);
                                    Glide.with(mContext).load(Uri.parse(_data.get((int)_position).get("image").toString())).into(image);
                                }
                            }
                        }
                    }
                } else {
                    background.setVisibility(View.GONE);
                    cardview.setVisibility(View.GONE);
                }
                if (_data.get((int) _position).containsKey("time")) {
                    _Time(Double.parseDouble(_data.get((int) _position).get("time").toString()), time);
                    _Time(Double.parseDouble(_data.get((int) _position).get("time").toString()), time2);
                }
                if (_data.get((int) _position).containsKey("love")) {
                    cardview.setVisibility(View.GONE);
                    background.setVisibility(View.GONE);
                    love.setVisibility(View.VISIBLE);
                } else {
                    love.setVisibility(View.GONE);
                }
                image.setOnClickListener(_view14 -> {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.didClickImage, _data.get((int) _position).get("uid").toString(), _data.get((int) _position).get("image").toString(), _data.get((int) _position).get("time").toString(), _data.get((int) _position).get("firstname").toString(), _data.get((int) _position).get("lastname").toString());
                });
                background.setOnLongClickListener(_view1 -> {
                    btmsheet_id = _data.get((int) _position).get("mid").toString();
                    btmsheet_uid = _data.get((int) _position).get("uid").toString();
                    btmsheet_pos = _position;
                    _ChatSettings();
                    return true;
                });
                image.setOnLongClickListener(_view12 -> {
                    btmsheet_id = _data.get((int) _position).get("mid").toString();
                    btmsheet_uid = _data.get((int) _position).get("uid").toString();
                    btmsheet_pos = _position;
                    _ChatSettings();
                    return true;
                });
                love.setOnLongClickListener(_view13 -> {
                    btmsheet_id = _data.get((int) _position).get("mid").toString();
                    btmsheet_uid = _data.get((int) _position).get("uid").toString();
                    btmsheet_pos = _position;
                    _ChatSettings();
                    return true;
                });
            } else {
                cardview.setVisibility(View.GONE);
                background.setVisibility(View.GONE);
                love.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Log.wtf("ChatAdapter", e.toString(), e);
        }
    }

    @Override
    public int getItemCount() {
        return _data.size();
    }

    public void _ChatSettings () {
        final
        com.google.android.material.bottomsheet.BottomSheetDialog bsh = new com.google.android.material.bottomsheet.BottomSheetDialog(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View bshlay = inflater.inflate(R.layout.bshchats, null);

        bsh.setContentView(bshlay);

        bsh.show();
        // Define LinearLayouts
        LinearLayout alllin = (LinearLayout)bshlay.findViewById(R.id.second_holder);

        bsh.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);

        _radius_4("#FFFFFFFF", "#00000000", 0, 20, 20, 0, 0, alllin);
        LinearLayout msglin = (LinearLayout)bshlay.findViewById(R.id.message_lin);

        _RippleEffects("#FFDADADA", msglin);
        LinearLayout item1lin = (LinearLayout)bshlay.findViewById(R.id.item1);

        _RippleEffects("#FFDADADA", item1lin);
        LinearLayout item2lin = (LinearLayout)bshlay.findViewById(R.id.item2);

        _RippleEffects("#FFDADADA", item2lin);
        LinearLayout item3lin = (LinearLayout)bshlay.findViewById(R.id.item3);

        _RippleEffects("#FFDADADA", item3lin);
        LinearLayout item4lin = (LinearLayout)bshlay.findViewById(R.id.item4);

        _RippleEffects("#FFDADADA", item4lin);
        LinearLayout item5lin = (LinearLayout)bshlay.findViewById(R.id.item5);

        _RippleEffects("#FFDADADA", item5lin);
        LinearLayout itemmsg = (LinearLayout)bshlay.findViewById(R.id.itemmessage);
        LinearLayout itemtime = (LinearLayout)bshlay.findViewById(R.id.message_lin_time);
        LinearLayout item1bg = (LinearLayout)bshlay.findViewById(R.id.item1_lin);
        LinearLayout item2bg = (LinearLayout)bshlay.findViewById(R.id.item2_lin);
        LinearLayout item3bg = (LinearLayout)bshlay.findViewById(R.id.item3_lin);
        LinearLayout item4bg = (LinearLayout)bshlay.findViewById(R.id.item4_lin);
        LinearLayout item5bg = (LinearLayout)bshlay.findViewById(R.id.item5_lin);
        // Define ImageViews
        ImageView item1img = (ImageView)bshlay.findViewById(R.id.item1_img);
        item1img.setImageResource(R.drawable.msg_reply);
        ImageView item2img = (ImageView)bshlay.findViewById(R.id.item2_img);
        item2img.setImageResource(R.drawable.msg_copy);
        ImageView item3img = (ImageView)bshlay.findViewById(R.id.item3_img);
        item3img.setImageResource(R.drawable.msg_delete);
        ImageView item4img = (ImageView)bshlay.findViewById(R.id.item4_img);
        item4img.setImageResource(R.drawable.msg_delete);
        ImageView item5img = (ImageView)bshlay.findViewById(R.id.item5_img);
        item5img.setImageResource(R.drawable.msg_report);
        _ICC(item1img, "#FF193566", "#FF193566");
        _ICC(item2img, "#FF193566", "#FF193566");
        _ICC(item3img, "#FF193566", "#FF193566");
        _ICC(item4img, "#FF193566", "#FF193566");
        _ICC(item5img, "#FF193566", "#FF193566");
        // Define TextViews
        TextView msgtext = (TextView)bshlay.findViewById(R.id.message_text);
        msgtext.setTypeface(Typeface.createFromAsset(mContext.getAssets(),"fonts/rmedium.ttf"), 0);
        TextView msgtime = (TextView)bshlay.findViewById(R.id.time);
        msgtime.setTypeface(Typeface.createFromAsset(mContext.getAssets(),"fonts/rmedium.ttf"), 0);
        TextView ttl1txt = (TextView)bshlay.findViewById(R.id.ttl1_txt);

        ttl1txt.setTypeface(Typeface.createFromAsset(mContext.getAssets(),"fonts/rmedium.ttf"), 0);
        ttl1txt.setText("Message By");
        TextView ttl2txt = (TextView)bshlay.findViewById(R.id.ttl2_txt);
        ttl2txt.setTypeface(Typeface.createFromAsset(mContext.getAssets(),"fonts/rmedium.ttf"), 0);
        ttl2txt.setTextColor(0xFF193566);
        if (_data.get((int)btmsheet_pos).containsKey("firstname") && _data.get((int)btmsheet_pos).containsKey("lastname")) {
            ttl2txt.setText(_data.get((int)btmsheet_pos).get("firstname").toString().concat(" ".concat(_data.get((int)btmsheet_pos).get("lastname").toString())));
        }
        TextView item1txt = (TextView)bshlay.findViewById(R.id.item1_txt);
        item1txt.setTypeface(Typeface.createFromAsset(mContext.getAssets(),"fonts/rmedium.ttf"), 0);
        item1txt.setText("Reply");
        TextView item2txt = (TextView)bshlay.findViewById(R.id.item2_txt);
        item2txt.setTypeface(Typeface.createFromAsset(mContext.getAssets(),"fonts/rmedium.ttf"), 0);
        item2txt.setText("Copy Text");
        TextView item3txt = (TextView)bshlay.findViewById(R.id.item3_txt);
        item3txt.setTypeface(Typeface.createFromAsset(mContext.getAssets(),"fonts/rmedium.ttf"), 0);
        item3txt.setText("Delete For You");
        TextView item4txt = (TextView)bshlay.findViewById(R.id.item4_txt);
        item4txt.setTypeface(Typeface.createFromAsset(mContext.getAssets(),"fonts/rmedium.ttf"), 0);
        item4txt.setText("Delete For Everyone");
        TextView item5txt = (TextView)bshlay.findViewById(R.id.item5_txt);
        item5txt.setTypeface(Typeface.createFromAsset(mContext.getAssets(),"fonts/rmedium.ttf"), 0);
        item5txt.setText("Report Message");
        // SetData
        // Start Getting Data
        if (_data.get((int)btmsheet_pos).containsKey("deleted")) {
            if (_data.get((int)btmsheet_pos).get("deleted").toString().equals("true")) {
                item1bg.setVisibility(View.GONE);
                item2bg.setVisibility(View.GONE);
                item4bg.setVisibility(View.GONE);
            }
        }
        if (_data.get((int)btmsheet_pos).containsKey("text")) {
            msgtext.setText(_data.get((int)btmsheet_pos).get("text").toString());
            if (_data.get((int)btmsheet_pos).containsKey("deleted")) {
                if (_data.get((int)btmsheet_pos).get("deleted").toString().equals("true")) {
                    msgtext.setText("Message is deleted.");
                }
            }
        }
        else {
            item2bg.setVisibility(View.GONE);
            if (_data.get((int)btmsheet_pos).containsKey("image")) {
                msgtext.setText("Image");
                if (_data.get((int)btmsheet_pos).containsKey("deleted")) {
                    if (_data.get((int)btmsheet_pos).get("deleted").toString().equals("true")) {
                        msgtext.setText("Image is deleted.");
                    }
                }
            }
            else {
                if (_data.get((int)btmsheet_pos).containsKey("love")) {
                    msgtext.setText("Love Heart");
                    if (_data.get((int)btmsheet_pos).containsKey("deleted")) {
                        if (_data.get((int)btmsheet_pos).get("deleted").toString().equals("true")) {
                            msgtext.setText("Love heart is deleted.");
                        }
                    }
                }
            }
        }
        if (btmsheet_uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            item5bg.setVisibility(View.GONE);
        }
        else {
            item4bg.setVisibility(View.GONE);
        }
        if (!type.equals("private")) {
            item3bg.setVisibility(View.GONE);
            item4bg.setVisibility(View.GONE);
        }
        if (_data.get((int)btmsheet_pos).containsKey("time")) {
            _Time(Double.parseDouble(_data.get((int)btmsheet_pos).get("time").toString()), msgtime);
        }
        // Start onClickListener Methods
        // OnClick
        item1lin.setOnClickListener(v -> {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.getChatReplyData, btmsheet_pos);
            SketchwareUtil.showKeyboard(mContext);
            bsh.dismiss();
        });
        item2lin.setOnClickListener(v -> {
            try {

                ((ClipboardManager) mContext.getSystemService(mContext.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", _data.get((int)btmsheet_pos).get("text").toString()));
                SketchwareUtil.showMessage(mContext, "Copied To Clipboard.");
            } catch(Exception e) {

                SketchwareUtil.showMessage(mContext, (e.toString()));
            }
            bsh.dismiss();
        });
        item3lin.setOnClickListener(v -> {
            // Action Here
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.didDeleteMessage, btmsheet_pos, btmsheet_id);
            bsh.dismiss();
        });
        item4lin.setOnClickListener(v -> {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.didDeleteMessageForever, btmsheet_id, btmsheet_pos, btmsheet_uid);
            bsh.dismiss();
        });
        item5lin.setOnClickListener(v -> {
            if (btmsheet_uid.equals(mUid)) {
                ChatSettingsMap = new HashMap<>();
                ChatSettingsMap.put("id", btmsheet_id);
                ChatSettingsMap.put("reported_by", FirebaseAuth.getInstance().getCurrentUser().getUid());
                ChatSettingsMap.put("uid", btmsheet_uid);
                ChatSettingsMap.put("rid", reports.push().getKey());
                if (_data.get((int)btmsheet_pos).containsKey("text")) {
                    ChatSettingsMap.put("text", _data.get((int)btmsheet_pos).get("text").toString());
                }
                if (_data.get((int)btmsheet_pos).containsKey("image")) {
                    ChatSettingsMap.put("image", _data.get((int)btmsheet_pos).get("image").toString());
                }
                reports.child("Chats/".concat(ChatSettingsMap.get("rid").toString())).updateChildren(ChatSettingsMap);
                ChatSettingsMap.clear();
                SketchwareUtil.showMessage(mContext, "Message Reported.");
            }
            bsh.dismiss();
        });
    }

    public void _RippleEffects (final String _color, @NonNull final View _view) {
        android.content.res.ColorStateList clr = new android.content.res.ColorStateList(new int[][]{new int[]{}},new int[]{Color.parseColor(_color)});
        android.graphics.drawable.RippleDrawable ripdr = new android.graphics.drawable.RippleDrawable(clr, null, null);
        _view.setBackground(ripdr);
    }

    public void _ICC (@NonNull final ImageView _img, final String _c1, final String _c2) {
        _img.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor(_c1), Color.parseColor(_c2)}));
    }

    public void _libsben_animation (@NonNull final View _view, final String _libsben, final Intent _libsben_intent) {
        _view.setTransitionName(_libsben);
        android.app.ActivityOptions optionsCompat = android.app.ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, _view, _libsben);
        mContext.startActivity(_libsben_intent, optionsCompat.toBundle());
    }

    public void _radius_4 (final String _color1, final String _color2, final double _str, final double _n1, final double _n2, final double _n3, final double _n4, @NonNull final View _view) {
        android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();

        gd.setColor(Color.parseColor(_color1));

        gd.setStroke((int)_str, Color.parseColor(_color2));

        gd.setCornerRadii(new float[]{(int)_n1,(int)_n1,(int)_n2,(int)_n2,(int)_n3,(int)_n3,(int)_n4,(int)_n4});

        _view.setBackground(gd);
    }

    public void _Time (final double _position, final TextView _textview) {
        CALENDAR_EXTRA = Calendar.getInstance();
        time = CALENDAR_EXTRA.getTimeInMillis() - _position;
        if (((time / 1000) / 3600) < 24) {
            CALENDAR_EXTRA.setTimeInMillis((long)(_position));
            _textview.setText(new SimpleDateFormat("hh:mm").format(CALENDAR_EXTRA.getTime()));
        }
        else {
            CALENDAR_EXTRA.setTimeInMillis((long)(_position));
            _textview.setText(new SimpleDateFormat("hh:mm").format(CALENDAR_EXTRA.getTime()));
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }

}