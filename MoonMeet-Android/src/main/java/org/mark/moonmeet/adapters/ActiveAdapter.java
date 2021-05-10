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
import org.mark.moonmeet.utils.AndroidUtilities;
import org.mark.moonmeet.utils.NotificationCenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActiveAdapter extends RecyclerView.Adapter<ActiveAdapter.ViewHolder> {

    ArrayList<HashMap<String, Object>> data;
    Calendar Cal;
    Context mContext;
    HashMap<String, Object> storiesMap = new HashMap<>();

    public ActiveAdapter(Context context, HashMap<String, Object> map, ArrayList<HashMap<String, Object>> arr) {
        data = arr;
        mContext = context;
        storiesMap = map;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater _inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = _inflater.inflate(R.layout.storiesc, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(layoutParams);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder _holder, final int _position) {
        View view = _holder.itemView;

        final LinearLayout linear1 = view.findViewById(R.id.linear1);
        final LinearLayout storylin = view.findViewById(R.id.storylin);
        final TextView textview1 = view.findViewById(R.id.textview1);
        final LinearLayout propic_bg = view.findViewById(R.id.propic_bg);
        final de.hdodenhof.circleimageview.CircleImageView circleimageview1 = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.circleimageview1);
        final LinearLayout linear2 = view.findViewById(R.id.linear2);

        radius_4("#FF64BBA6", "#FFFFFF", 8, 360, 360, 360, 360, linear2);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(layoutParams);
        if (data.get((int) _position).containsKey("uid") && (data.get((int) _position).containsKey("firstname") && (data.get((int) _position).containsKey("lastname") && (data.get((int) _position).containsKey("avatar") && data.get((int) _position).containsKey("last_seen"))))) {
            // Define
            if (!data.get((int) _position).get("uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                if (data.get((int) _position).get("last_seen").toString().equals("private") || data.get((int) _position).get("last_seen").toString().equals("")) {
                    linear1.setVisibility(View.GONE);
                } else {
                    Cal = Calendar.getInstance();
                    if ((Cal.getTimeInMillis() - Double.parseDouble(data.get((int) _position).get("last_seen").toString())) < 30000) {
                        if (storiesMap.containsKey("uid")) {
                            if (storiesMap.containsKey(storiesMap.get(data.get((int) _position).get("uid").toString()).toString())) {
                                radius_4("#00000000", "#FF193566", 8, 360, 360, 360, 360, storylin);
                            } else {
                                radius_4("#00000000", "#DADADA", 8, 360, 360, 360, 360, storylin);
                            }
                        }
                        // Data
                        if (data.get((int) _position).containsKey("avatar")) {
                            com.bumptech.glide.Glide.with(mContext)
                                    .load(data.get(_position).get("avatar").toString())
                                    .override(50, 50)
                                    .into(circleimageview1);
                        }
                        if (data.get((int) _position).containsKey("firstname") && data.get((int) _position).containsKey("lastname")) {
                            textview1.setText(data.get(_position).get("firstname").toString().concat(" ".concat(data.get((int) _position).get("lastname").toString())));
                        }
                        linear1.setOnClickListener(view1 -> {
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.didClickActiveUser, data.get(_position).get("uid").toString(), "private");
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

    public void radius_4(final String color1, final String color2, final double str, final double n1, final double n2, final double n3, final double n4, final View view) {
        android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
        gd.setColor(Color.parseColor(color1));
        gd.setStroke((int) str, Color.parseColor(color2));
        gd.setCornerRadii(new float[]{(int) n1, (int) n1, (int) n2, (int) n2, (int) n3, (int) n3, (int) n4, (int) n4});
        view.setBackground(gd);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linear1;
        LinearLayout storylin;
        TextView textview1;
        CircleImageView circleimageview1;
        LinearLayout linear2;

        public ViewHolder(View root) {
            super(root);

            root.setLayoutParams(
                    new RecyclerView.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                    )
            );

            linear2 = root.findViewById(R.id.linear2);
            circleimageview1 = root.findViewById(R.id.circleimageview1);
            textview1 = root.findViewById(R.id.textview1);
            storylin = root.findViewById(R.id.storylin);
            linear1 = root.findViewById(R.id.linear1);
        }
    }

}