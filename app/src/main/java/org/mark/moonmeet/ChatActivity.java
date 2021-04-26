package org.mark.moonmeet;

import static com.google.firebase.FirebaseApp.initializeApp;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.mark.axemojiview.listener.SimplePopupAdapter;
import org.mark.axemojiview.view.AXEmojiEditText;
import org.mark.axemojiview.view.AXEmojiPopupLayout;
import org.mark.axemojiview.view.AXEmojiTextView;
import org.mark.axemojiview.view.AXSingleEmojiView;
import org.mark.moonmeet.adapters.ChatAdapter;
import org.mark.moonmeet.components.SoftKeyboardPopup;
import org.mark.moonmeet.utils.AndroidUtilities;
import org.mark.moonmeet.utils.CubicBezierInterpolator;
import org.mark.moonmeet.utils.ISwipeControllerActions;
import org.mark.moonmeet.utils.MoonMeetItemAnimator;
import org.mark.moonmeet.utils.SwipeController;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    public final int REQ_CD_IMAGE_PICKER = 101;
    public final int REQ_CD_VIDEO_PICKER = 102;
    public final int REQ_CD_CAMERA_PICKER = 103;
    public final int REQ_CD_ATTACH_PICKER = 104;
    private Timer _timer = new Timer();
    private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
    private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
    private String uid = "";
    private String My_Firstname = "";
    private String My_Lastname = "";
    private String My_Avatar = "";
    private String My_Username = "";
    private String Chatroom = "";
    private String Chatcopy = "";
    private HashMap<String, Object> message_map = new HashMap<>();
    private String MyColor = "";
    private String UserColor = "";
    private String type = "";
    private double time = 0;
    private double deference = 0;
    private boolean isEmojiSHow = false;
    private HashMap<String, Object> ChatMap = new HashMap<>();
    private String picurl = "";
    private String picpath = "";
    private String picname = "";
    private boolean replying = false;
    private double um_n = 0;
    private String mid = "";
    private boolean sending_image = false;
    private HashMap<String, Object> reply_data = new HashMap<>();
    private boolean recording = false;
    private String videopath = "";
    private String videoname = "";
    private String videourl = "";
    private String FilePath = "";
    private String mid_audio = "";
    private String User_Avatar = "";
    private String User_Firstname = "";
    private String User_Lastname = "";
    private String User_Username = "";
    private double AudioLengthVar = 0;
    private String url_string = "";
    private boolean isMediaPlaying = false;
    private double MediaPlayerPosition = 0;
    private boolean isDeletingMessage = false;
    private String dragData = "";
    private String positions = "";
    private String mid_love = "";
    private String reply_mid = "";
    private String reply_uid = "";
    private double reply_position = 0;
    private double poss = 0;
    private String attach_path = "";
    private String attach_name = "";
    private String camera_path = "";
    private String camera_name = "";
    private String image_mid = "";
    private int keyboard_height;
    private HashMap<String, Object> ChatSettingsMap = new HashMap<>();
    private double CurrentDeletingPosition = 0;
    private HashMap<String, Object> UnReadMap = new HashMap<>();
    private String User_LastSeen = "";
    private String My_LastSeen = "";
    private HashMap<String, Object> RecentMediaMap = new HashMap<>();
    private String OneSignalPushToken = "";
    private String OneSignalUserID = "";
    private String OneSignalChatID = "";
    private String OneSignalChatPushToken = "";
    private HashMap<String, Object> OneSignalMap = new HashMap<>();
    private String OneSignalMessage = "";
    private String OneSignalTitle = "";
    private String OneSignalImage = "";
    private String OneSignalSendingID = "";
    private String MyOSUserID = "";
    private String ONSTitle = "";
    private String ONSMessage = "";
    private String ONSUserID = "";
    private String ONSImage = "";
    private String MyOSPushToken = "";
    private int replyHeight;
    private SoftKeyboardPopup menuKeyboard;
    private MediaRecorder myAudioRecorder;
    private boolean is_replying = false;
    private String user2_name = "";
    private boolean firstLoad = false;
    private double limit = 0;
    private boolean roundLast = false;

    private ArrayList<HashMap<String, Object>> Private_Map = new ArrayList<>();
    private ArrayList<String> msgs_str = new ArrayList<>();
    private ArrayList<String> user_unreadmessages = new ArrayList<>();

    private LinearLayout mLinearContent;
    private LinearLayout bar;
    private LinearLayout topbar_divider;
    private LinearLayout chats_rv_holder;
    private LinearLayout img_lin;
    private LinearLayout divider_bottom;
    private LinearLayout reply;
    private LinearLayout reply_divider;
    private LinearLayout bottom_message_linear;
    private LinearLayout micro_divider;
    private LinearLayout micro_linear;
    private AXEmojiPopupLayout layout;
    private LinearLayout alternativetoolbar;
    private ImageView back;
    private CircleImageView avatar;
    private LinearLayout name_holder;
    private ImageView info;
    private MaterialTextView name_moon;
    private MaterialTextView state_moon;
    private LinearLayout nomsgyet;
    public RecyclerView chats_rv;
    private MaterialTextView nomsgyet_full_txt;
    private MaterialTextView nomsgyet_mini_txt;
    private LinearLayout img_lin_top_divider;
    private ImageView close_img_lin;
    private ShapeableImageView image_preview;
    private LinearLayout divider_in_img_lin;
    private LinearLayout img_tools_lin;
    private LinearLayout img_prog_lin;
    private MaterialTextView choose_image;
    private LinearLayout space_in_img_lin;
    private MaterialTextView send_image;
    private ProgressBar image_send_progress;
    private MaterialTextView upload_status;
    private ShapeableImageView reply_in_linear;
    private ShapeableImageView reply_img;
    private LinearLayout in_reply;
    private ImageView close_reply;
    private MaterialTextView reply_name;
    private AXEmojiTextView reply_message;
    private ShapeableImageView reply_emoji;
    private ShapeableImageView stickers;
    private LinearLayout message_holder;
    private ShapeableImageView attach;
    private ShapeableImageView mic_and_send;
    private AXEmojiEditText message;
    private ShapeableImageView close_voice;
    private MaterialTextView record_length;
    private MaterialTextView point;
    private ShapeableImageView send_voice;

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
    private StorageReference video_fs = _firebase_storage.getReference("Messages/Video");
    private OnCompleteListener<Uri> _video_fs_upload_success_listener;
    private OnSuccessListener<FileDownloadTask.TaskSnapshot> _video_fs_download_success_listener;
    private OnSuccessListener _video_fs_delete_success_listener;
    private OnProgressListener _video_fs_upload_progress_listener;
    private OnProgressListener _video_fs_download_progress_listener;
    private OnFailureListener _video_fs_failure_listener;
    private StorageReference image_fs = _firebase_storage.getReference("Messages/Images");
    private OnCompleteListener<Uri> _image_fs_upload_success_listener;
    private OnSuccessListener<FileDownloadTask.TaskSnapshot> _image_fs_download_success_listener;
    private OnSuccessListener _image_fs_delete_success_listener;
    private OnProgressListener _image_fs_upload_progress_listener;
    private OnProgressListener _image_fs_download_progress_listener;
    private OnFailureListener _image_fs_failure_listener;
    private Calendar CALENDAR = Calendar.getInstance();
    private Calendar CALENDAR_EXTRA = Calendar.getInstance();
    private DatabaseReference Chat1 = _firebase.getReference("Chatroom");
    private ChildEventListener _Chat1_child_listener;
    private DatabaseReference Chat2 = _firebase.getReference("Chatcopy");
    private ChildEventListener _Chat2_child_listener;
    private SharedPreferences sp_lm;
    private Intent image_picker = new Intent(Intent.ACTION_GET_CONTENT);
    private Intent video_picker = new Intent(Intent.ACTION_GET_CONTENT);
    private AlertDialog.Builder D;
    private Vibrator Vibrator;
    private TimerTask AudioRecorderTimer;
    private StorageReference audio_fs = _firebase_storage.getReference("Messages/Audio");
    private OnCompleteListener<Uri> _audio_fs_upload_success_listener;
    private OnSuccessListener<FileDownloadTask.TaskSnapshot> _audio_fs_download_success_listener;
    private OnSuccessListener _audio_fs_delete_success_listener;
    private OnProgressListener _audio_fs_upload_progress_listener;
    private OnProgressListener _audio_fs_download_progress_listener;
    private OnFailureListener _audio_fs_failure_listener;
    private MediaPlayer MediaPlayer;
    private TimerTask MediPlayerTimer;
    private Intent Camera_Picker = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    private File _file_Camera_Picker;
    private Intent attach_picker = new Intent(Intent.ACTION_GET_CONTENT);
    private DatabaseReference UserChats = _firebase.getReference("UserChats");
    private ChildEventListener _UserChats_child_listener;
    private final Intent toView = new Intent();
    private Intent toViewProfile = new Intent();
    private DatabaseReference reports = _firebase.getReference("reports");
    private ChildEventListener _reports_child_listener;
    public TimerTask ScrollingTimer;
    private DatabaseReference allmessages = _firebase.getReference("chat");
    private ChildEventListener _allmessages_child_listener;
    private DatabaseReference RecentMedia = _firebase.getReference("RecentMedia");
    private ChildEventListener _RecentMedia_child_listener;
    private Intent toCamera = new Intent();
    private SharedPreferences CatchedImagePath;
    private Intent toPickImage = new Intent();
    private ObjectAnimator obj = new ObjectAnimator();
    private ChatAdapter chatAdapter;
    private String Manufacturer;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.chat);
        initialize(_savedInstanceState);
        initializeApp(this);
        initializeLogic();
        Manufacturer = android.os.Build.MANUFACTURER;
        if (!Manufacturer.toLowerCase().equals("Xiaomi")) {
            Log.d("ChatActivity", Manufacturer.toString());
            updateStatusBar();
            _initKeyboardAnim();
        } else {
            Log.d("ChatActivity", Manufacturer.toString());
        }
    }

    private void initialize(Bundle _savedInstanceState) {
        mLinearContent = (LinearLayout) findViewById(R.id.mLinearContent);
        bar = (LinearLayout) findViewById(R.id.bar);
        topbar_divider = (LinearLayout) findViewById(R.id.topbar_divider);
        chats_rv_holder = (LinearLayout) findViewById(R.id.chats_rv_holder);
        img_lin = findViewById(R.id.img_lin);
        divider_bottom = (LinearLayout) findViewById(R.id.divider_bottom);
        reply = (LinearLayout) findViewById(R.id.reply);
        reply_divider = (LinearLayout) findViewById(R.id.reply_divider);
        bottom_message_linear = findViewById(R.id.bottom_message_linear);
        micro_divider = (LinearLayout) findViewById(R.id.micro_divider);
        micro_linear = (LinearLayout) findViewById(R.id.micro_linear);
        layout = findViewById(R.id.layout);
        alternativetoolbar = (LinearLayout) findViewById(R.id.alternativetoolbar);
        back = (ImageView) findViewById(R.id.back);
        avatar = (CircleImageView) findViewById(R.id.avatar);
        name_holder = (LinearLayout) findViewById(R.id.name_holder);
        info = (ImageView) findViewById(R.id.info);
        name_moon = (MaterialTextView) findViewById(R.id.name_moon);
        state_moon = (MaterialTextView) findViewById(R.id.state_moon);
        nomsgyet = (LinearLayout) findViewById(R.id.nomsgyet);
        chats_rv = (RecyclerView) findViewById(R.id.chats_rv);
        nomsgyet_full_txt = (MaterialTextView) findViewById(R.id.nomsgyet_full_txt);
        nomsgyet_mini_txt = (MaterialTextView) findViewById(R.id.nomsgyet_mini_txt);
        img_lin_top_divider = (LinearLayout) findViewById(R.id.img_lin_top_divider);
        close_img_lin = (ImageView) findViewById(R.id.close_img_lin);
        image_preview = (ShapeableImageView) findViewById(R.id.image_preview);
        divider_in_img_lin = (LinearLayout) findViewById(R.id.divider_in_img_lin);
        img_tools_lin = (LinearLayout) findViewById(R.id.img_tools_lin);
        img_prog_lin = (LinearLayout) findViewById(R.id.img_prog_lin);
        choose_image = (MaterialTextView) findViewById(R.id.choose_image);
        space_in_img_lin = (LinearLayout) findViewById(R.id.space_in_img_lin);
        send_image = (MaterialTextView) findViewById(R.id.send_image);
        image_send_progress = (ProgressBar) findViewById(R.id.image_send_progress);
        upload_status = (MaterialTextView) findViewById(R.id.upload_status);
        reply_in_linear = (ShapeableImageView) findViewById(R.id.reply_in_linear);
        reply_img = (ShapeableImageView) findViewById(R.id.reply_img);
        in_reply = (LinearLayout) findViewById(R.id.in_reply);
        close_reply = (ImageView) findViewById(R.id.close_reply);
        reply_name = (MaterialTextView) findViewById(R.id.reply_name);
        reply_message = (AXEmojiTextView) findViewById(R.id.reply_message);
        reply_emoji = (ShapeableImageView) findViewById(R.id.reply_emoji);
        stickers = (ShapeableImageView) findViewById(R.id.stickers);
        message_holder = (LinearLayout) findViewById(R.id.message_holder);
        attach = (ShapeableImageView) findViewById(R.id.attach);
        mic_and_send = (ShapeableImageView) findViewById(R.id.mic_and_send);
        message = (AXEmojiEditText) findViewById(R.id.message);
        close_voice = (ShapeableImageView) findViewById(R.id.close_voice);
        record_length = (MaterialTextView) findViewById(R.id.record_length);
        point = (MaterialTextView) findViewById(R.id.point);
        send_voice = (ShapeableImageView) findViewById(R.id.send_voice);
        Fauth = FirebaseAuth.getInstance();
        sp_lm = getSharedPreferences("lastmessage", Activity.MODE_PRIVATE);
        image_picker.setType("image/*");
        image_picker.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        video_picker.setType("videos/*");
        video_picker.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        D = new AlertDialog.Builder(this);
        Vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        _file_Camera_Picker = FileUtil.createNewPictureFile(getApplicationContext());
        Uri _uri_Camera_Picker = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            _uri_Camera_Picker = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", _file_Camera_Picker);
        } else {
            _uri_Camera_Picker = Uri.fromFile(_file_Camera_Picker);
        }
        Camera_Picker.putExtra(MediaStore.EXTRA_OUTPUT, _uri_Camera_Picker);
        Camera_Picker.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        attach_picker.setType("*/*");
        attach_picker.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        CatchedImagePath = getSharedPreferences("CatchedImagePath", Activity.MODE_PRIVATE);

        back.setOnClickListener(_view -> finish());

        name_holder.setOnClickListener(_view -> {
            toViewProfile.setClass(getApplicationContext(), UserprofileActivity.class);
            toViewProfile.putExtra("uid", uid);
            startActivity(toViewProfile);
        });

        info.setOnClickListener(_view -> {
            toViewProfile.setClass(getApplicationContext(), UserprofileActivity.class);
            toViewProfile.putExtra("uid", uid);
            startActivity(toViewProfile);
        });

        close_img_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                picurl = "";
                picpath = "";
                picname = "";
                img_lin.setVisibility(View.GONE);
            }
        });

        choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                img_lin.setVisibility(View.GONE);
                startActivityForResult(image_picker, REQ_CD_IMAGE_PICKER);
            }
        });

        send_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (!picpath.equals("") && !picname.equals("")) {
                    try {

                        image_fs.child(picname).putFile(Uri.fromFile(new File(picpath))).addOnFailureListener(_image_fs_failure_listener).addOnProgressListener(_image_fs_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
                                return image_fs.child(picname).getDownloadUrl();
                            }
                        }).addOnCompleteListener(_image_fs_upload_success_listener);
                        img_tools_lin.setVisibility(View.GONE);
                        img_prog_lin.setVisibility(View.VISIBLE);
                        upload_status.setText("Uploading...");
                    } catch (Exception e) {

                        SketchwareUtil.showMessage(getApplicationContext(), (e.toString()));
                    }
                } else {
                    if (!camera_path.equals("") && !camera_name.equals("")) {
                        try {

                            image_fs.child(camera_name).putFile(Uri.fromFile(new File(camera_path))).addOnFailureListener(_image_fs_failure_listener).addOnProgressListener(_image_fs_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    return image_fs.child(camera_name).getDownloadUrl();
                                }
                            }).addOnCompleteListener(_image_fs_upload_success_listener);
                            img_tools_lin.setVisibility(View.GONE);
                            img_prog_lin.setVisibility(View.VISIBLE);
                            upload_status.setText("Uploading...");
                        } catch (Exception e) {

                            SketchwareUtil.showMessage(getApplicationContext(), (e.toString()));
                        }
                    } else {
						/*
if (!"".equals("") && !"".equals("")) {

}
else {

}
*/
                    }
                }
            }
        });

        close_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                _Replying(false);
            }
        });

        stickers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (layout.getVisibility() == View.GONE) {
                    layout.setVisibility(View.VISIBLE);
                    layout.show();
                } else {
                    layout.setVisibility(View.GONE);
                    layout.openKeyboard();
                }
            }
        });

        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                menuKeyboard.show();
            }
        });

        mic_and_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (message.getText().toString().trim().equals("")) {
                    _setUpLoveSend();
                } else {
                    if (message.getText().toString().trim().length() > 550) {
                        SketchwareUtil.showMessage(getApplicationContext(), "Sorry, you message is too long ! ( limit is 550 characters )");
                    } else {
                        _SendMessage();
                    }
                }
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (layout.getVisibility() == View.VISIBLE) {
                    layout.dismiss();
                    layout.setVisibility(View.GONE);
                }
            }
        });

        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
                final String _charSeq = _param1.toString();
                sp_lm.edit().putString(uid.concat("'s type"), _charSeq).apply();
                if (_charSeq.trim().equals("")) {
                    attach.setVisibility(View.VISIBLE);
                    mic_and_send.setImageResource(R.drawable.ic_favorite_outline_white);
                } else {
                    attach.setVisibility(View.GONE);
                    mic_and_send.setImageResource(R.drawable.ic_send);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {

            }

            @Override
            public void afterTextChanged(Editable _param1) {

            }
        });

        close_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                bottom_message_linear.setVisibility(View.VISIBLE);
                micro_divider.setVisibility(View.GONE);
                micro_linear.setVisibility(View.GONE);
                record_length.setText("0");
                _Recording(false);
            }
        });

        send_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                record_length.setText("0");
                bottom_message_linear.setVisibility(View.VISIBLE);
                micro_divider.setVisibility(View.GONE);
                micro_linear.setVisibility(View.GONE);
                _sendAudioRecord();
            }
        });

        _users_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childKey.equals(uid)) {
                    if (_childValue.containsKey("avatar") && (_childValue.containsKey("firstname") && (_childValue.containsKey("username") && (_childValue.containsKey("username") && _childValue.containsKey("last_seen"))))) {
                        User_Firstname = _childValue.get("firstname").toString();
                        User_Lastname = _childValue.get("lastname").toString();
                        User_Username = _childValue.get("username").toString();
                        User_Avatar = _childValue.get("avatar").toString();
                        User_LastSeen = _childValue.get("last_seen").toString();
                        name_moon.setText(_childValue.get("firstname").toString().concat(" ".concat(_childValue.get("lastname").toString())));
                        if (_childValue.get("last_seen").toString().equals("private")) {
                            state_moon.setText("Last seen recently");
                        } else {
                            _NewTime(Double.parseDouble(_childValue.get("last_seen").toString()), state_moon);
                        }
                        com.bumptech.glide.Glide.with(getApplicationContext())
                                .load(_childValue.get("avatar").toString())
                                .override(1024, 1024)
                                .into(avatar);
						/*
if (_childValue.containsKey("OneSignalUserID")) {
OneSignalChatID = _childValue.get("OneSignalUserID").toString();
}
else {
OneSignalMap = new HashMap<>();
OneSignalMap.put("OneSignalUserID", OneSignalUserID);
users.child(uid).updateChildren(OneSignalMap);
OneSignalMap.clear();
}
if (_childValue.containsKey("OneSignalPushToken")) {
OneSignalChatPushToken = _childValue.get("OneSignalPushToken").toString();
}
else {
OneSignalMap = new HashMap<>();
OneSignalMap.put("OneSignalPushToken", OneSignalPushToken);
users.child(uid).updateChildren(OneSignalMap);
OneSignalMap.clear();
}
*/
                    }
                }
                if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    if (_childValue.containsKey("avatar") && (_childValue.containsKey("firstname") && (_childValue.containsKey("username") && _childValue.containsKey("lastname")))) {
                        My_Firstname = _childValue.get("firstname").toString();
                        My_Lastname = _childValue.get("lastname").toString();
                        My_Avatar = _childValue.get("avatar").toString();
                        My_Username = _childValue.get("username").toString();
                        My_LastSeen = _childValue.get("last_seen").toString();
						/*
if (_childValue.containsKey("OneSignalUserID")) {
MyOSUserID = _childValue.get("OneSignalUserID").toString();
}
if (_childValue.containsKey("OneSignalPushToken")) {
MyOSPushToken = _childValue.get("OneSignalPushToken").toString();
}
*/
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childKey.equals(uid)) {
                    if (_childValue.containsKey("avatar") && (_childValue.containsKey("firstname") && (_childValue.containsKey("username") && (_childValue.containsKey("username") && _childValue.containsKey("last_seen"))))) {
                        User_Firstname = _childValue.get("firstname").toString();
                        User_Lastname = _childValue.get("lastname").toString();
                        User_Username = _childValue.get("username").toString();
                        User_Avatar = _childValue.get("avatar").toString();
                        name_moon.setText(_childValue.get("firstname").toString().concat(" ".concat(_childValue.get("lastname").toString())));
                        if (_childValue.get("last_seen").toString().equals("private")) {
                            state_moon.setText("Last seen recently");
                        } else {
                            _NewTime(Double.parseDouble(_childValue.get("last_seen").toString()), state_moon);
                        }
                        com.bumptech.glide.Glide.with(getApplicationContext())
                                .load(_childValue.get("avatar").toString())
                                .override(1024, 1024)
                                .into(avatar);
                    }
                }
                if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    if (_childValue.containsKey("avatar") && (_childValue.containsKey("firstname") && (_childValue.containsKey("username") && _childValue.containsKey("lastname")))) {
                        My_Firstname = _childValue.get("firstname").toString();
                        My_Lastname = _childValue.get("lastname").toString();
                        My_Avatar = _childValue.get("avatar").toString();
                        My_Username = _childValue.get("username").toString();
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

        _video_fs_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot _param1) {
                double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();

            }
        };

        _video_fs_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
                double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();

            }
        };

        _video_fs_upload_success_listener = new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(Task<Uri> _param1) {
                final String _downloadUrl = _param1.getResult().toString();

            }
        };

        _video_fs_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
                final long _totalByteCount = _param1.getTotalByteCount();

            }
        };

        _video_fs_delete_success_listener = new OnSuccessListener() {
            @Override
            public void onSuccess(Object _param1) {

            }
        };

        _video_fs_failure_listener = new OnFailureListener() {
            @Override
            public void onFailure(Exception _param1) {
                final String _message = _param1.getMessage();

            }
        };

        _image_fs_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot _param1) {
                double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
                image_send_progress.setProgress((int) _progressValue);
            }
        };

        _image_fs_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
                double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();

            }
        };

        _image_fs_upload_success_listener = new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(Task<Uri> _param1) {
                final String _downloadUrl = _param1.getResult().toString();
                CALENDAR = Calendar.getInstance();
                CALENDAR_EXTRA = Calendar.getInstance();
                image_mid = Chat1.push().getKey();
                RecentMediaMap = new HashMap<>();
                RecentMediaMap.put("image", _downloadUrl);
                RecentMediaMap.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                RecentMediaMap.put("mid", image_mid);
                RecentMediaMap.put("firstname", My_Firstname);
                RecentMediaMap.put("lastname", My_Lastname);
                RecentMediaMap.put("tofirstname", User_Firstname);
                RecentMediaMap.put("tolastname", User_Lastname);
                if (!picname.equals("")) {
                    RecentMediaMap.put("image_name", picname);
                } else {
                    if (!camera_name.equals("")) {
                        RecentMediaMap.put("image_name", camera_name);
                    }
                }
                RecentMediaMap.put("type", "image");
                RecentMediaMap.put("status", "Sent");
                RecentMediaMap.put("time", String.valueOf((long) (CALENDAR.getTimeInMillis())));
                RecentMedia.child(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("/".concat(RecentMediaMap.get("mid").toString()))).updateChildren(RecentMediaMap);
                RecentMediaMap.clear();
                message_map = new HashMap<>();
                message_map.put("mid", image_mid);
                message_map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                message_map.put("firstname", My_Firstname);
                message_map.put("lastname", My_Lastname);
                message_map.put("tofirstname", User_Firstname);
                message_map.put("tolastname", User_Lastname);
                if (!picname.equals("")) {
                    message_map.put("image_name", picname);
                } else {
                    if (!camera_name.equals("")) {
                        message_map.put("image_name", camera_name);
                    }
                }
                message_map.put("image", _downloadUrl);
                message_map.put("type", "image");
                message_map.put("status", "Sent");
                message_map.put("time", String.valueOf((long) (CALENDAR.getTimeInMillis())));
                Chat1.child(message_map.get("mid").toString()).updateChildren(message_map);
                if (!uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    Chat2.child(message_map.get("mid").toString()).updateChildren(message_map);
                }
                message_map.clear();
                message_map = new HashMap<>();
                message_map.put("last_message_uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                message_map.put("last_message_mid", mid);
                message_map.put("last_message_status", "Sent");
                message_map.put("last_message_type", "image");
                message_map.put("last_message_time", String.valueOf((long) (CALENDAR_EXTRA.getTimeInMillis())));
                message_map.put("lastseen", String.valueOf((long) (CALENDAR_EXTRA.getTimeInMillis())));
                message_map.put("uid", uid);
                message_map.put("firstname", User_Firstname);
                message_map.put("lastname", User_Lastname);
                message_map.put("avatar", User_Avatar);
                UserChats.child(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("/".concat(uid))).updateChildren(message_map);
                if (!uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    message_map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    message_map.put("firstname", My_Firstname);
                    message_map.put("lastname", My_Lastname);
                    message_map.put("avatar", My_Avatar);
                    UserChats.child(uid.concat("/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid()))).updateChildren(message_map);
                    message_map.clear();
                }
                if (!uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    um_n = 0;
                    for (int _repeat118 = 0; _repeat118 < (int) (Private_Map.size()); _repeat118++) {
                        if (Private_Map.get((int) um_n).get("uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()) && Private_Map.get((int) um_n).get("status").toString().equals("Sent")) {
                            user_unreadmessages.add(Private_Map.get((int) um_n).get("mid").toString());
                        }
                        um_n++;
                    }
                    message_map.put("UnreadMessagesNum", String.valueOf((long) (user_unreadmessages.size() + 1)));
                    UserChats.child(uid.concat("/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid()))).updateChildren(message_map);
                    user_unreadmessages.clear();
                }
                img_lin.setVisibility(View.GONE);
            }
        };

        _image_fs_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
                final long _totalByteCount = _param1.getTotalByteCount();

            }
        };

        _image_fs_delete_success_listener = new OnSuccessListener() {
            @Override
            public void onSuccess(Object _param1) {

            }
        };

        _image_fs_failure_listener = new OnFailureListener() {
            @Override
            public void onFailure(Exception _param1) {
                final String _message = _param1.getMessage();

            }
        };

        _Chat1_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (msgs_str.contains(_childValue.get("mid").toString())) {

                } else {
                    Private_Map.add(_childValue);
                    msgs_str.add(_childValue.get("mid").toString());
                    chats_rv.getAdapter().notifyItemInserted(chats_rv.getAdapter().getItemCount() - 1);
                    if (Private_Map.size() > 0) {
                        ((LinearLayoutManager) chats_rv.getLayoutManager()).scrollToPositionWithOffset((int) Private_Map.size() - 1, (int) 0);
                        nomsgyet.setVisibility(View.GONE);
                    } else {
                        if (Private_Map.size() < 1) {
                            nomsgyet.setVisibility(View.VISIBLE);
                        }
                    }
                    if (_childValue.containsKey("status") && (_childValue.containsKey("mid") && _childValue.containsKey("uid"))) {
                        if (_childValue.get("status").toString().equals("Sent") && _childValue.get("uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                            UnReadMap = new HashMap<>();
                            UnReadMap.put("status", "Seen");
                            allmessages.child(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("/".concat(uid.concat("/".concat(_childValue.get("mid").toString()))))).updateChildren(UnReadMap);
                            if (!FirebaseAuth.getInstance().getCurrentUser().getUid().equals(uid)) {
                                allmessages.child(uid.concat("/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("/".concat(_childValue.get("mid").toString()))))).updateChildren(UnReadMap);
                            }
                        }
                        UnReadMap.clear();
                        UnReadMap = new HashMap<>();
                        UnReadMap.put("UnreadMessagesNum", String.valueOf((long) (0)));
                        UserChats.child(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("/".concat(uid))).updateChildren(UnReadMap);
                        UnReadMap.clear();
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
                Private_Map.clear();
                msgs_str.clear();
                Chat1.addChildEventListener(_Chat1_child_listener);
                chats_rv.getAdapter().notifyDataSetChanged();
                ScrollingTimer = new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((LinearLayoutManager) chats_rv.getLayoutManager()).scrollToPositionWithOffset((int) CurrentDeletingPosition, (int) 0);
                            }
                        });
                    }
                };
                _timer.schedule(ScrollingTimer, (int) (120));
            }

            @Override
            public void onCancelled(DatabaseError _param1) {
                final int _errorCode = _param1.getCode();
                final String _errorMessage = _param1.getMessage();
                nomsgyet.setVisibility(View.VISIBLE);
            }
        };
        Chat1.addChildEventListener(_Chat1_child_listener);

        _Chat2_child_listener = new ChildEventListener() {
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
        Chat2.addChildEventListener(_Chat2_child_listener);

        _audio_fs_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot _param1) {
                double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();

            }
        };

        _audio_fs_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
                double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();

            }
        };

        _audio_fs_upload_success_listener = new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(Task<Uri> _param1) {
                final String _downloadUrl = _param1.getResult().toString();
                CALENDAR = Calendar.getInstance();
                message_map = new HashMap<>();
                mid_audio = Chat1.push().getKey();
                message_map.put("firstname", My_Firstname);
                message_map.put("lastname", My_Lastname);
                message_map.put("tofirstname", User_Firstname);
                message_map.put("tolastname", User_Lastname);
                message_map.put("audio", _downloadUrl);
                message_map.put("mid", mid_audio);
                message_map.put("type", "audio");
                message_map.put("time", String.valueOf((long) (CALENDAR.getTimeInMillis())));
                Chat1.child(message_map.get("mid").toString()).updateChildren(message_map);
                if (!uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    Chat2.child(message_map.get("mid").toString()).updateChildren(message_map);
                    SketchwareUtil.showMessage(getApplicationContext(), "success upload audio");
                }
                message_map.clear();
                AudioRecorderTimer.cancel();
                FilePath = FileUtil.getExternalStorageDir().concat("/".concat("MoonMeet".concat("/".concat("AudioRecords".concat("/".concat(String.valueOf((long) (SketchwareUtil.getRandom((int) (1111111111), (int) (9999999999d)))).concat(".mp3")))))));
                MediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_out);
                MediaPlayer.start();
            }
        };

        _audio_fs_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
                final long _totalByteCount = _param1.getTotalByteCount();

            }
        };

        _audio_fs_delete_success_listener = new OnSuccessListener() {
            @Override
            public void onSuccess(Object _param1) {

            }
        };

        _audio_fs_failure_listener = new OnFailureListener() {
            @Override
            public void onFailure(Exception _param1) {
                final String _message = _param1.getMessage();

            }
        };

        _UserChats_child_listener = new ChildEventListener() {
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
        UserChats.addChildEventListener(_UserChats_child_listener);

        _reports_child_listener = new ChildEventListener() {
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
        reports.addChildEventListener(_reports_child_listener);

        _allmessages_child_listener = new ChildEventListener() {
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
        allmessages.addChildEventListener(_allmessages_child_listener);

        _RecentMedia_child_listener = new ChildEventListener() {
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
        RecentMedia.addChildEventListener(_RecentMedia_child_listener);

        Fauth_updateEmailListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

            }
        };

        Fauth_updatePasswordListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

            }
        };

        Fauth_emailVerificationSentListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

            }
        };

        Fauth_deleteUserListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

            }
        };

        Fauth_phoneAuthListener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                final boolean _success = task.isSuccessful();
                final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";

            }
        };

        Fauth_updateProfileListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

            }
        };

        Fauth_googleSignInListener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                final boolean _success = task.isSuccessful();
                final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";

            }
        };

        _Fauth_create_user_listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

            }
        };

        _Fauth_sign_in_listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

            }
        };

        _Fauth_reset_password_listener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> _param1) {
                final boolean _success = _param1.isSuccessful();

            }
        };
    }

    private void initializeLogic() {
		/*
OneSignal.init(ChatActivity.this, "AIzaSyBDLpxX9YYtP9i7w1MaHR2ZZIDE-n4P0Bc", "02c27ebc-a96d-4005-80d5-dc08294131a6");
 OneSignal.getCurrentOrNewInitBuilder()
.inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
.unsubscribeWhenNotificationsAreDisabled(true).init();
OneSignal.startInit(ChatActivity.this)
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
        final ViewGroup cView = getWindow().findViewById(android.R.id.content);
        menuKeyboard = new SoftKeyboardPopup(this, chats_rv_holder, message, message, attach);
        // Initialize Emoji Keyboard
        // AXEmoji Set-up
        AXSingleEmojiView emojiView = new AXSingleEmojiView(this);
        emojiView.setEditText(message);
        layout.initPopupView(emojiView);
        // Listeners EmojiPager
        // Optional
        isEmojiSHow = layout.isShowing();
        layout.setVisibility(View.GONE);
        layout.setPopupAnimationEnabled(false);
        layout.setPopupAnimationDuration(250);
        layout.setSearchViewAnimationEnabled(false);
        layout.setSearchViewAnimationDuration(250);
        layout.setPopupListener(new SimplePopupAdapter() {
            @Override
            public void onShow() {
                if (!Manufacturer.equals("Xiaomi")) {
                    resizeView(cView, cView.getHeight(), AndroidUtilities.getScreenHeight() - keyboard_height);
                }
                stickers.setImageResource(R.drawable.input_keyboard);
            }

            @Override
            public void onDismiss() {
                stickers.setImageResource(R.drawable.smiles);
                if (!Manufacturer.equals("Xiaomi")) {
                    _initKeyboardAnim();
                }
            }

            @Override
            public void onKeyboardOpened(int height) {
            }

            @Override
            public void onKeyboardClosed() {
                if (!Manufacturer.equals("Xiaomi")) {
                    resizeView(cView, cView.getHeight(), AndroidUtilities.getScreenHeight());
                }
            }
        });
        // getExtra Key
        uid = getIntent().getStringExtra("uid");
        type = getIntent().getStringExtra("type");
        // Colors Set-up
        MyColor = "#FF193566";
        UserColor = "#FFF0F0F0";
        // Database Configuration
        Chat1.removeEventListener(_Chat1_child_listener);
        Chat2.removeEventListener(_Chat2_child_listener);
        Chatroom = "chat/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("/".concat(uid)));
        Chatcopy = "chat/".concat(uid.concat("/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid())));
        Chat1 = _firebase.getReference(Chatroom);
        Chat2 = _firebase.getReference(Chatcopy);
        /**
         firstLoad = true;
         roundLast = true;
         **/
        Chat1.addChildEventListener(_Chat1_child_listener);
        Chat2.addChildEventListener(_Chat2_child_listener);
        // Booleans
        replying = false;
        recording = false;
        // Recycler View
        chatAdapter = new ChatAdapter(uid, chats_rv, this, Private_Map);
        chats_rv.setAdapter(chatAdapter);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //mLinearLayoutManager.setStackFromEnd(true);
        chats_rv.setLayoutManager(mLinearLayoutManager);
        chats_rv.setItemViewCacheSize(60);
        chats_rv.setDrawingCacheEnabled(true);
        chats_rv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        chats_rv.setItemAnimator(new MoonMeetItemAnimator());
        // initialize ItemTouchHelper
        SwipeController controller = new SwipeController(this, position -> {
            int mPosition = position;
            _replyValuesPositions();
            _getReplyData(mPosition);
            _Replying(true);
            message.requestFocus();
            SketchwareUtil.showKeyboard(getApplicationContext());
        });
        androidx.recyclerview.widget.ItemTouchHelper itemTouchHelper = new androidx.recyclerview.widget.ItemTouchHelper(controller);
        itemTouchHelper.attachToRecyclerView(chats_rv);
        // Design
        bar.setElevation((int) 2);
        close_img_lin.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor(MyColor),
                Color.parseColor(MyColor)}));
        back.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor(MyColor),
                Color.parseColor(MyColor)}));
        info.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor(MyColor),
                Color.parseColor(MyColor)}));
        close_reply.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor(MyColor),
                Color.parseColor(MyColor)}));
        stickers.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor(MyColor),
                Color.parseColor(MyColor)}));
        attach.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor(MyColor),
                Color.parseColor(MyColor)}));
        mic_and_send.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor(MyColor),
                Color.parseColor(MyColor)}));
        close_voice.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor(MyColor),
                Color.parseColor(MyColor)}));
        send_voice.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor(MyColor),
                Color.parseColor(MyColor)}));
        reply_emoji.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor(MyColor),
                Color.parseColor(MyColor)}));
        // Customizations
        _RippleEffects("#FFDADADA", back);
        _RippleEffects("#FFDADADA", info);
        _RippleEffects("#FFDADADA", reply_in_linear);
        _RippleEffects("#FFDADADA", close_reply);
        _RippleEffects("#FFDADADA", stickers);
        _RippleEffects("#FFDADADA", attach);
        _RippleEffects("#FFDADADA", mic_and_send);
        _RippleEffects("#FFDADADA", close_voice);
        _RippleEffects("#FFDADADA", send_voice);
        _RippleEffects("#FFDADADA", close_img_lin);
        // Activity Logic
        ((EditText) message).setMaxLines((int) 3);
        micro_linear.setVisibility(View.GONE);
        reply.setVisibility(View.GONE);
        micro_divider.setVisibility(View.GONE);
        divider_bottom.setVisibility(View.GONE);
        img_lin.setVisibility(View.GONE);
        // Request Focus
        message.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {

                message.setHint("Type a Message");
            } else {
                message.setHint("Aa");
            }
        });
        // Last Typing
        if (!sp_lm.getString(uid.concat("'s type"), "").equals("")) {
            message.setText(sp_lm.getString(uid.concat("'s type"), ""));
        }
        // Initialize AudioRecorder
        FilePath = FileUtil.getExternalStorageDir().concat("/".concat("MoonMeet".concat("/".concat("AudioRecords".concat("/".concat(String.valueOf((long) (SketchwareUtil.getRandom((int) (1111111111), (int) (9999999999d)))).concat(".mp3")))))));
    }

    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);
        switch (_requestCode) {
            case REQ_CD_IMAGE_PICKER:
                if (_resultCode == Activity.RESULT_OK) {
                    ArrayList<String> _filePath = new ArrayList<>();
                    if (_data != null) {
                        if (_data.getClipData() != null) {
                            for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
                                ClipData.Item _item = _data.getClipData().getItemAt(_index);
                                _filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
                            }
                        } else {
                            _filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
                        }
                    }
                    try {

                        if ((FileUtil.getFileLength(_filePath.get((int) (0))) / 1024) > 5120) {
                            D.setTitle("File too big");
                            D.setMessage("File size should be less than 5 MB");
                            D.setPositiveButton("I understand", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface _dialog, int _which) {

                                }
                            });
                            D.create().show();
                        } else {
                            picpath = _filePath.get((int) (0));
                            picname = "MoonMeetImage".concat(String.valueOf((long) (SketchwareUtil.getRandom((int) (11111), (int) (99999)))));
                            image_preview.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(picpath, 1024, 1024));
                            img_lin.setVisibility(View.VISIBLE);
                            img_prog_lin.setVisibility(View.GONE);
                            img_tools_lin.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {

                        SketchwareUtil.showMessage(getApplicationContext(), (e.toString()));
                    }
                }
                break;

            case REQ_CD_VIDEO_PICKER:
                if (_resultCode == Activity.RESULT_OK) {
                    ArrayList<String> _filePath = new ArrayList<>();
                    if (_data != null) {
                        if (_data.getClipData() != null) {
                            for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
                                ClipData.Item _item = _data.getClipData().getItemAt(_index);
                                _filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
                            }
                        } else {
                            _filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
                        }
                    }
                    if ((FileUtil.getFileLength(_filePath.get((int) (0))) / 1024) > 10240) {
                        D.setTitle("File too big");
                        D.setMessage("File size should be less than 5 MB");
                        D.setPositiveButton("I understand", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface _dialog, int _which) {

                            }
                        });
                        D.create().show();
                    } else {
                        videopath = _filePath.get((int) (0));
                        videoname = "MoonMeetVideo".concat(String.valueOf((long) (SketchwareUtil.getRandom((int) (11111), (int) (99999)))));
                    }
                }
                break;

            case REQ_CD_CAMERA_PICKER:
                if (_resultCode == Activity.RESULT_OK) {
                    String _filePath = _file_Camera_Picker.getAbsolutePath();

                    try {

                        if ((FileUtil.getFileLength(_filePath) / 1024) > 5120) {
                            D.setTitle("File too big");
                            D.setMessage("File size should be less than 5 MB");
                            D.setPositiveButton("I understand", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface _dialog, int _which) {

                                }
                            });
                            D.create().show();
                        } else {
                            camera_path = _filePath;
                            camera_name = "MoonMeetCamera".concat(String.valueOf((long) (SketchwareUtil.getRandom((int) (11111), (int) (999999)))));
                            image_preview.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(camera_path, 1024, 1024));
                            img_lin.setVisibility(View.VISIBLE);
                            img_prog_lin.setVisibility(View.GONE);
                            img_tools_lin.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {

                        SketchwareUtil.showMessage(getApplicationContext(), (e.toString()));
                    }
                }
                break;

            case REQ_CD_ATTACH_PICKER:
                if (_resultCode == Activity.RESULT_OK) {
                    ArrayList<String> _filePath = new ArrayList<>();
                    if (_data != null) {
                        if (_data.getClipData() != null) {
                            for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
                                ClipData.Item _item = _data.getClipData().getItemAt(_index);
                                _filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
                            }
                        } else {
                            _filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
                        }
                    }
                    if ((FileUtil.getFileLength(_filePath.get((int) (0))) / 1024) > 10240) {
                        D.setTitle("Attach File Too Big");
                        D.setMessage("Attach size should be less than 10 MB");
                        D.setPositiveButton("I understand", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface _dialog, int _which) {

                            }
                        });
                        D.create().show();
                    } else {
                        attach_path = _filePath.get((int) (0));
                        attach_name = "MoonMeetAttach".concat(String.valueOf((long) (SketchwareUtil.getRandom((int) (11111), (int) (99999)))));
                        img_lin.setVisibility(View.VISIBLE);
                        img_prog_lin.setVisibility(View.GONE);
                        img_tools_lin.setVisibility(View.VISIBLE);
                    }
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (layout.isShowing()) {
            layout.setVisibility(View.GONE);
            layout.dismiss();
        } else {
            finish();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (layout.getVisibility() == View.VISIBLE) {
            layout.dismiss();
            layout.setVisibility(View.GONE);
        } else if (layout.getVisibility() == View.GONE) {
            layout.dismiss();
            layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (layout.getVisibility() == View.VISIBLE) {
            layout.dismiss();
            layout.setVisibility(View.GONE);
        } else if (layout.getVisibility() == View.GONE) {
            layout.dismiss();
            layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!CatchedImagePath.getString("LatestImagePath", "").equals("-")) {
            try {

                camera_path = CatchedImagePath.getString("LatestImagePath", "");
                camera_name = "MoonMeetCamera".concat(String.valueOf((long) (SketchwareUtil.getRandom((int) (11111111111111d), (int) (99999999999999d)))));
                image_preview.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(camera_path, 1024, 1024));
                img_lin.setVisibility(View.VISIBLE);
                img_prog_lin.setVisibility(View.GONE);
                img_tools_lin.setVisibility(View.VISIBLE);
                if (CatchedImagePath.getString("BackFromPreview", "").equals("true")) {
                    CatchedImagePath.edit().putString("BackFromPreview", "false").apply();
                }
                CatchedImagePath.edit().putString("LatestImagePath", "-").apply();
            } catch (Exception e) {

                SketchwareUtil.showMessage(getApplicationContext(), (e.toString()));
            }
        }
    }

    public void _Recording(final boolean _trueOrFalse) {
        if (_trueOrFalse) {
            recording = true;
            AudioLengthVar = Double.parseDouble(record_length.getText().toString());
            record_length.setText(String.valueOf((long) (AudioLengthVar)));
            AudioRecorderTimer = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(() -> {
                        AudioLengthVar++;
                        record_length.setText(String.valueOf((long) (AudioLengthVar)));
                    });
                }
            };
            _timer.scheduleAtFixedRate(AudioRecorderTimer, (int) (0), (int) (1000));
            myAudioRecorder = new MediaRecorder();
            myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            myAudioRecorder.setOutputFile(FilePath);
            try {

                myAudioRecorder.prepare();
                myAudioRecorder.start();
            } catch (Exception e) {

                SketchwareUtil.showMessage(getApplicationContext(), (e.toString()));
            }
            SketchwareUtil.showMessage(getApplicationContext(), "Recording start.");
        } else {
            recording = false;
            record_length.setText("0");
            AudioRecorderTimer.cancel();
            try {

                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder = null;
            } catch (Exception e) {

                SketchwareUtil.showMessage(getApplicationContext(), (e.toString()));
            }
            SketchwareUtil.showMessage(getApplicationContext(), "Recording cancelled.");
        }
    }


    public void _Replying(final boolean _trueOrFalse) {
        if (_trueOrFalse) {
            replying = true;
            divider_bottom.setVisibility(View.VISIBLE);
            AndroidUtilities.expand(reply, 230, AndroidUtilities.dp(getApplicationContext(), 100));
        } else {
            replying = false;
            AndroidUtilities.collapse(reply, 230, 0);
            reply_divider.setVisibility(View.GONE);
        }
    }


    public void _RippleEffects(final String _color, final View _view) {
        android.content.res.ColorStateList clr = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(_color)});
        android.graphics.drawable.RippleDrawable ripdr = new android.graphics.drawable.RippleDrawable(clr, null, null);
        _view.setBackground(ripdr);
    }


    public void _SendMessage() {
        mid = Chat1.push().getKey();
        CALENDAR_EXTRA = Calendar.getInstance();
        if (replying) {
            mid = Chat1.push().getKey();
            message_map = new HashMap<>();
            message_map.put("reply_uid", reply_data.get("uid").toString());
            message_map.put("reply_mid", reply_data.get("mid").toString());
            if (reply_data.containsKey("image")) {
                message_map.put("reply_image", reply_data.get("image").toString());
            }
            if (reply_data.containsKey("emoji")) {
                message_map.put("reply_emoji", reply_data.get("emoji").toString());
            }
            if (reply_data.containsKey("text")) {
                message_map.put("replyed_message", reply_data.get("text").toString());
            }
            if (!(message.getText().toString().length() < 1)) {
                message_map.put("text", message.getText().toString());
            }
            message_map.put("firstname", My_Firstname);
            message_map.put("lastname", My_Lastname);
            message_map.put("tofirstname", User_Firstname);
            message_map.put("tolastname", User_Lastname);
            message_map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
            message_map.put("replyed_name", reply_data.get("firstname").toString().concat(" ".concat(reply_data.get("lastname").toString())));
            message_map.put("time", String.valueOf((long) (CALENDAR_EXTRA.getTimeInMillis())));
            message_map.put("mid", mid);
            message_map.put("status", "Sent");
            message_map.put("type", "reply");
            Chat1.child(message_map.get("mid").toString()).updateChildren(message_map);
            if (!FirebaseAuth.getInstance().getCurrentUser().getUid().equals(uid)) {
                Chat2.child(message_map.get("mid").toString()).updateChildren(message_map);
            }
            message_map.clear();
            MediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_out);
            MediaPlayer.start();
            message_map = new HashMap<>();
            message_map.put("last_message_uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
            message_map.put("last_message_mid", mid);
            message_map.put("last_message_status", "Sent");
            message_map.put("last_message_type", "text");
            message_map.put("last_message_time", String.valueOf((long) (CALENDAR_EXTRA.getTimeInMillis())));
            message_map.put("last_message_text", message.getText().toString().trim());
            message_map.put("lastseen", String.valueOf((long) (CALENDAR_EXTRA.getTimeInMillis())));
            message_map.put("uid", uid);
            message_map.put("firstname", User_Firstname);
            message_map.put("lastname", User_Lastname);
            message_map.put("avatar", User_Avatar);
            UserChats.child(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("/".concat(uid))).updateChildren(message_map);
            if (!uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                message_map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                message_map.put("firstname", My_Firstname);
                message_map.put("lastname", My_Lastname);
                message_map.put("avatar", My_Avatar);
                UserChats.child(uid.concat("/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid()))).updateChildren(message_map);
            }
        } else {
            CALENDAR_EXTRA = Calendar.getInstance();
            message_map = new HashMap<>();
            mid = Chat1.push().getKey();
            message_map.put("firstname", My_Firstname);
            message_map.put("lastname", My_Lastname);
            message_map.put("tofirstname", User_Firstname);
            message_map.put("tolastname", User_Lastname);
            message_map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
            message_map.put("text", message.getText().toString().trim());
            message_map.put("time", String.valueOf((long) (CALENDAR_EXTRA.getTimeInMillis())));
            message_map.put("mid", mid);
            message_map.put("status", "Sent");
            message_map.put("type", "message");
            Chat1.child(message_map.get("mid").toString()).updateChildren(message_map);
            if (!FirebaseAuth.getInstance().getCurrentUser().getUid().equals(uid)) {
                Chat2.child(message_map.get("mid").toString()).updateChildren(message_map);
            }
            message_map.clear();
            MediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_out);
            MediaPlayer.start();
            message_map = new HashMap<>();
            message_map.put("last_message_uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
            message_map.put("last_message_mid", mid);
            message_map.put("last_message_status", "Sent");
            message_map.put("last_message_type", "text");
            message_map.put("last_message_time", String.valueOf((long) (CALENDAR_EXTRA.getTimeInMillis())));
            message_map.put("last_message_text", message.getText().toString().trim());
            message_map.put("lastseen", String.valueOf((long) (CALENDAR_EXTRA.getTimeInMillis())));
            message_map.put("uid", uid);
            message_map.put("firstname", User_Firstname);
            message_map.put("lastname", User_Lastname);
            message_map.put("avatar", User_Avatar);
            UserChats.child(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("/".concat(uid))).updateChildren(message_map);
            if (!uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                message_map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                message_map.put("firstname", My_Firstname);
                message_map.put("lastname", My_Lastname);
                message_map.put("avatar", My_Avatar);
                UserChats.child(uid.concat("/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid()))).updateChildren(message_map);
            }
            message_map.clear();
        }
        if (!uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            um_n = 0;
            for (int _repeat477 = 0; _repeat477 < (int) (Private_Map.size()); _repeat477++) {
                if (Private_Map.get((int) um_n).get("uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()) && Private_Map.get((int) um_n).get("status").toString().equals("Sent")) {
                    user_unreadmessages.add(Private_Map.get((int) um_n).get("mid").toString());
                }
                um_n++;
            }
            message_map.put("UnreadMessagesNum", String.valueOf((long) (user_unreadmessages.size() + 1)));
            UserChats.child(uid.concat("/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid()))).updateChildren(message_map);
            user_unreadmessages.clear();
        }
        if (picurl.equals("")) {
            message.setText("");
        } else {
            picurl = "";
            picpath = "";
            picname = "";
            attach_path = "";
            attach_name = "";
            camera_path = "";
            camera_name = "";
            videopath = "";
            videoname = "";
            videourl = "";
        }
        if (OneSignalChatID.equals("")) {

        } else {
            OneSignalMessage = "You got a new message from : ".concat(My_Firstname.concat(" ".concat(My_Lastname)));
            OneSignalSendingID = OneSignalChatID;
            OneSignalTitle = "Moon Meet";
            OneSignalImage = My_Avatar;
            _NotificationSenderServiceTask(OneSignalTitle, OneSignalMessage, OneSignalSendingID, OneSignalImage);
        }
        if (replying) {
            replying = false;
            _Replying(false);
        }
        if (recording) {
            recording = false;
            _Recording(false);
        }
        if (!My_LastSeen.equals("private")) {
            CALENDAR_EXTRA = Calendar.getInstance();
            message_map = new HashMap<>();
            message_map.put("last_seen", String.valueOf((long) (CALENDAR_EXTRA.getTimeInMillis())));
            users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(ChatMap);
            message_map.clear();
        }
    }


    public void _NewTime(final double _position, final TextView _textview) {
        time = _position;
        CALENDAR = Calendar.getInstance();
        deference = CALENDAR.getTimeInMillis() - time;
        if (deference < 60000) {
            _textview.setText("Active now");
        } else {
            if (deference < (60 * 60000)) {
                _textview.setText("Active ".concat(String.valueOf((long) (deference / 60000)).concat(" Minutes ago")));
            } else {
                if (deference < (24 * (60 * 60000))) {
                    _textview.setText("Active ".concat(String.valueOf((long) (deference / (60 * 60000))).concat(" Hours ago")));
                } else {
                    CALENDAR.setTimeInMillis((long) (time));
                    _textview.setText("Active on ".concat(new SimpleDateFormat("EEEE, MMMM d").format(CALENDAR.getTime())));
                }
            }
        }
    }


    public void _sendAudioRecord() {
        AudioRecorderTimer = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> audio_fs.child(Uri.parse(FilePath).getLastPathSegment()).putFile(Uri.fromFile(new File(FileUtil.getExternalStorageDir().concat("/".concat("MoonMeet/".concat("AudioRecords/".concat(Uri.parse(FilePath).getLastPathSegment()))))))).addOnFailureListener(_audio_fs_failure_listener).addOnProgressListener(_audio_fs_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
                        return audio_fs.child(Uri.parse(FilePath).getLastPathSegment()).getDownloadUrl();
                    }
                }).addOnCompleteListener(_audio_fs_upload_success_listener));
            }
        };
        _timer.schedule(AudioRecorderTimer, (int) (1000));
        SketchwareUtil.showMessage(getApplicationContext(), "Record Sent.");
        _Recording(false);
    }


    public void _getReplyData(final double _Position) {
        reply_data.clear();
        reply_data = new HashMap<>();
        reply_data.put("firstname", Private_Map.get((int) _Position).get("firstname").toString());
        reply_data.put("lastname", Private_Map.get((int) _Position).get("lastname").toString());
        reply_data.put("uid", Private_Map.get((int) _Position).get("uid").toString());
        if (Private_Map.get((int) _Position).containsKey("image")) {
            reply_data.put("image", Private_Map.get((int) _Position).get("image").toString());
            reply_message.setText("Image");
            Glide.with(getApplicationContext()).load(Uri.parse(Private_Map.get((int) _Position).get("image").toString())).into(reply_img);
            reply_message.setVisibility(View.VISIBLE);
            reply_emoji.setVisibility(View.GONE);
            reply_img.setVisibility(View.VISIBLE);
        } else {
            if (Private_Map.get((int) _Position).containsKey("love")) {
                reply_data.put("emoji", Private_Map.get((int) _Position).get("love").toString());
                reply_emoji.setVisibility(View.VISIBLE);
                reply_img.setVisibility(View.GONE);
                reply_message.setVisibility(View.GONE);
            } else {
                if (Private_Map.get((int) _Position).containsKey("text")) {
                    reply_data.put("text", Private_Map.get((int) _Position).get("text").toString());
                    reply_message.setText(Private_Map.get((int) _Position).get("text").toString());
                    reply_emoji.setVisibility(View.GONE);
                    reply_img.setVisibility(View.GONE);
                    reply_message.setVisibility(View.VISIBLE);
                }
            }
        }
        reply_data.put("mid", Private_Map.get((int) _Position).get("mid").toString());
        reply_name.setText(Private_Map.get((int) _Position).get("firstname").toString().concat(" ".concat(Private_Map.get((int) _Position).get("lastname").toString())));
    }


    public void _setUpLoveSend() {
        CALENDAR = Calendar.getInstance();
        message_map = new HashMap<>();
        mid_love = Chat1.push().getKey();
        message_map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
        message_map.put("firstname", My_Firstname);
        message_map.put("lastname", My_Lastname);
        message_map.put("tofirstname", User_Firstname);
        message_map.put("tolastname", User_Username);
        message_map.put("love", "true");
        message_map.put("mid", mid_love);
        message_map.put("status", "Sent");
        message_map.put("time", String.valueOf((long) (CALENDAR.getTimeInMillis())));
        Chat1.child(message_map.get("mid").toString()).updateChildren(message_map);
        if (!uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            Chat2.child(message_map.get("mid").toString()).updateChildren(message_map);
        }
        message_map.clear();
        message_map = new HashMap<>();
        message_map.put("last_message_uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
        message_map.put("last_message_mid", mid);
        message_map.put("last_message_status", "Sent");
        message_map.put("last_message_type", "love");
        message_map.put("last_message_time", String.valueOf((long) (CALENDAR_EXTRA.getTimeInMillis())));
        message_map.put("lastseen", String.valueOf((long) (CALENDAR_EXTRA.getTimeInMillis())));
        message_map.put("uid", uid);
        message_map.put("firstname", User_Firstname);
        message_map.put("status", "Sent");
        message_map.put("lastname", User_Lastname);
        message_map.put("avatar", User_Avatar);
        UserChats.child(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("/".concat(uid))).updateChildren(message_map);
        if (!uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            message_map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
            message_map.put("firstname", My_Firstname);
            message_map.put("lastname", My_Lastname);
            message_map.put("avatar", My_Avatar);
            UserChats.child(uid.concat("/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid()))).updateChildren(message_map);
            message_map.clear();
        }
        if (!uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            um_n = 0;
            for (int _repeat99 = 0; _repeat99 < (int) (Private_Map.size()); _repeat99++) {
                if (Private_Map.get((int) um_n).get("uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()) && Private_Map.get((int) um_n).get("status").toString().equals("Sent")) {
                    user_unreadmessages.add(Private_Map.get((int) um_n).get("mid").toString());
                }
                um_n++;
            }
            message_map.put("UnreadMessagesNum", String.valueOf((long) (user_unreadmessages.size() + 1)));
            UserChats.child(uid.concat("/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid()))).updateChildren(message_map);
            user_unreadmessages.clear();
        }
    }


    public void _replyValuesPositions() {
        reply_mid = Private_Map.get((int) poss).get("mid").toString();
        reply_uid = Private_Map.get((int) poss).get("uid").toString();
        reply_position = poss;
    }


    public void _circleRipple(final String _color, final View _v) {
        android.content.res.ColorStateList clrb = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(_color)});
        android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrb, null, null);
        _v.setBackground(ripdrb);
    }

    public void _initKeyboardAnim() {
        if (!Manufacturer.equals("Xiaomi")) {
            final int keyboardOffset = 300;
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            final ViewGroup contentV = getWindow().findViewById(android.R.id.content);

            getWindow().getDecorView().setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {

                @Override
                public WindowInsets onApplyWindowInsets(View view, WindowInsets inset) {
                    if (view != null) {


                        bar.setPadding(8, inset.getSystemWindowInsetTop(), 8, 8);
                        int offset = inset.getSystemWindowInsetBottom() - inset.getStableInsetBottom();
                        if (inset.getSystemWindowInsetBottom() > keyboardOffset) {
                            int height = AndroidUtilities.getScreenHeight();

                            int start = contentV.getHeight();
                            int end = height - offset;
                            resizeView(contentV, start, end);
                        } else {
                            resizeView(contentV, contentV.getHeight(), AndroidUtilities.getScreenHeight());
                        }
                    }
                    return view.onApplyWindowInsets(inset);

                }
            });
        }
    }

    //a function that resizes the decor view with animation
    private void resizeView(final View view, int start, int end) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        valueAnimator.addUpdateListener(animation -> {
            view.getLayoutParams().height = (int) animation.getAnimatedValue();
            view.requestLayout();
        });
        valueAnimator.setInterpolator(CubicBezierInterpolator.EASE_BOTH);
        valueAnimator.setDuration(260);
        valueAnimator.start();
    }

    private void updateStatusBar() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }
        window.setStatusBarColor(getResources().getColor(R.color.StatusBarColor));
    }

    public void _NotificationSenderServiceTask(final String _title, final String _message, final String _receiverID, final String _image) {
		/*
if (OneSignalSendingID.equals("")) {
SketchwareUtil.showMessage(getApplicationContext(), "clear");
}
else {
if (OneSignalSendingID.equals(MyOSUserID)) {
SketchwareUtil.showMessage(getApplicationContext(), "equals");
}
else {
SketchwareUtil.showMessage(getApplicationContext(), "sended");
ONSTitle = _title;
ONSMessage = _message;
ONSUserID = _receiverID;
ONSImage = _image;
OSPermissionSubscriptionState  = OneSignal.getPermissionSubscriptionState();

boolean isEnabled = .getPermissionStatus().getEnabled();
boolean isSubscribed = .getSubscriptionStatus().getSubscribed();
boolean subscriptionSetting = .getSubscriptionStatus().getUserSubscriptionSetting();
String userID = .getSubscriptionStatus().getUserId();
String pushToken = .getSubscriptionStatus().getPushToken();

if (!isSubscribed)
return;

try {
   JSONObject notificationContent = new JSONObject("{'contents': {'en': '" + OneSignalMessage + "'}," +
           "'include_player_ids': ['" + OneSignalSendingID + "'], " +
           "'headings': {'en': '" + OneSignalTitle + "'}, " +
           "'big_picture': '" + OneSignalImage + "'}");
   OneSignal.postNotification(notificationContent, null);
} catch (org.json.JSONException e) {
   e.printStackTrace();
}
}
}
*/
    }

    @Deprecated
    public void showMessage(String _s) {
        Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
    }

    @Deprecated
    public float getDip(int _input) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
    }

    @Deprecated
    public int getDisplayWidthPixels() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    @Deprecated
    public int getDisplayHeightPixels() {
        return getResources().getDisplayMetrics().heightPixels;
    }
}
