package org.mark.moonmeet;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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

import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import org.mark.moonmeet.ui.BaseFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class SetupActivity extends BaseFragment {

    public final int REQ_CD_FP = 101;
    public final int REQ_CD_CAM = 102;
    private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
    private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();

    private String image_path = "";
    private String image_name = "";
    private boolean is_image_picked = false;
    private String taked_photo = "";
    private String firstname = "";
    private String lastname = "";
    private String user_propic = "";
    private HashMap<String, Object> details = new HashMap<>();
    private String session = "";
    private String Manufacturer = "";
    private String Device = "";
    private String SDK = "";
    private String Model = "";
    private String Release = "";
    private String mid = "";
    private String uid = "";
    private HashMap<String, Object> message_map = new HashMap<>();
    private String BotMessage = "";
    private String Chatroom = "";
    private String Chatcopy = "";
    private String midchats = "";
    private String BotAvatar = "";
    private String BotFirstname = "";
    private String BotLastname = "";
    private HashMap<String, Object> FcmMap = new HashMap<>();
    private String OneSignalUserID = "";
    private String OneSignalPushToken = "";

    private LinearLayout topbar;
    private LinearLayout main;
    private TextView topbar_txt;
    private LinearLayout space;
    private ImageView done;
    private TextView information;
    private LinearLayout all_holder;
    private LinearLayout width_linear;
    private LinearLayout bottom_linear;
    private LinearLayout image_holder;
    private LinearLayout all_edittext_holder;
    private LinearLayout avatar_holder;
    private CircleImageView avatar;
    private LinearLayout firstname_holder;
    private LinearLayout lastname_holder;
    private EditText firstname_edittext;
    private LinearLayout divider_first;
    private EditText lastname_edittext;
    private LinearLayout divider_last;
    private ImageView imageview2;
    private ImageView imageview1;
    private TextView btm_txt1;
    private TextView btm_txt2;

    private Intent fp = new Intent(Intent.ACTION_GET_CONTENT);
    private Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    private File _file_cam;
    private Intent toPhoto = new Intent();
    private Intent toContinue = new Intent();
    private Intent toPick = new Intent();
    private DatabaseReference users = _firebase.getReference("users");
    private ChildEventListener _users_child_listener;
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
    private StorageReference propic = _firebase_storage.getReference("propics");
    private OnCompleteListener<Uri> _propic_upload_success_listener;
    private OnSuccessListener<FileDownloadTask.TaskSnapshot> _propic_download_success_listener;
    private OnSuccessListener _propic_delete_success_listener;
    private OnProgressListener _propic_upload_progress_listener;
    private OnProgressListener _propic_download_progress_listener;
    private OnFailureListener _propic_failure_listener;
    private Calendar time = Calendar.getInstance();
    private SharedPreferences my_data;
    private SharedPreferences sp_mydt;
    private SharedPreferences sp_pc;
    private Calendar CALENDAR_EXTRA = Calendar.getInstance();
    private DatabaseReference UserChats = _firebase.getReference("UserChats");
    private ChildEventListener _UserChats_child_listener;
    private DatabaseReference Chat1 = _firebase.getReference("Chatroom");
    private ChildEventListener _Chat1_child_listener;
    private DatabaseReference Chat2 = _firebase.getReference("Chatcopy");
    private ChildEventListener _Chat2_child_listener;
    private SharedPreferences CatchedImagePath;
    private Intent toImage = new Intent();

    public SetupActivity(Bundle args) {
        super(args);
    }

    @Override
    public boolean isSwipeBackEnabled(MotionEvent event) {
        return false;
    }

    @Override
    public View createView(Context context) {
        fragmentView = new FrameLayout(context);
        actionBar.setAddToContainer(false);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.setup, (ViewGroup) fragmentView, false);
        ((ViewGroup) fragmentView).addView(view);
        initialize(context);
        initializeLogic();
        return fragmentView;
    }

    @Override
    protected void onTransitionAnimationEnd(boolean isOpen, boolean backward) {
        if (isOpen && !backward) {
            if (!CatchedImagePath.getString("LatestImagePath", "").equals("-")) {
                taked_photo = CatchedImagePath.getString("LatestImagePath", "");
                image_path = taked_photo;
                image_name = "MoonMeetSetup".concat(String.valueOf((long) (SketchwareUtil.getRandom((int) (11111111111111d), (int) (99999999999999d)))));
                is_image_picked = true;
                avatar.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(image_path, 1024, 1024));
                avatar_holder.setBackground(new GradientDrawable() {
                    public GradientDrawable getIns(int a, int b) {
                        this.setCornerRadius(a);
                        this.setColor(b);
                        return this;
                    }
                }.getIns((int) SketchwareUtil.getDip(getApplicationContext(), (int) (100)), 0xFFFFFF));
                avatar.setCircleBackgroundColor(Color.TRANSPARENT);
                if (CatchedImagePath.getString("BackFromPreview", "").equals("true")) {
                    CatchedImagePath.edit().putString("BackFromPreview", "false").apply();
                }
                CatchedImagePath.edit().putString("LatestImagePath", "-").apply();
            }
            if (sp_pc.getString("Profile", "").equals("Completed")) {
                presentFragment(new LaunchActivity(), false);
            }
        } else if (isOpen && backward) {
            if (!CatchedImagePath.getString("LatestImagePath", "").equals("-")) {
                taked_photo = CatchedImagePath.getString("LatestImagePath", "");
                image_path = taked_photo;
                image_name = "MoonMeetSetup".concat(String.valueOf((long) (SketchwareUtil.getRandom((int) (11111111111111d), (int) (99999999999999d)))));
                is_image_picked = true;
                avatar.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(image_path, 1024, 1024));
                avatar_holder.setBackground(new GradientDrawable() {
                    public GradientDrawable getIns(int a, int b) {
                        this.setCornerRadius(a);
                        this.setColor(b);
                        return this;
                    }
                }.getIns((int) SketchwareUtil.getDip(getApplicationContext(), (int) (100)), 0xFFFFFF));
                avatar.setCircleBackgroundColor(Color.TRANSPARENT);
                if (CatchedImagePath.getString("BackFromPreview", "").equals("true")) {
                    CatchedImagePath.edit().putString("BackFromPreview", "false").apply();
                }
                CatchedImagePath.edit().putString("LatestImagePath", "-").apply();
            }
        }
    }

    private void initialize(Context context) {
        topbar = findViewById(R.id.topbar);
        main = findViewById(R.id.main);
        topbar_txt = findViewById(R.id.topbar_txt);
        space = findViewById(R.id.space);
        done = findViewById(R.id.done);
        information = findViewById(R.id.information);
        all_holder = findViewById(R.id.all_holder);
        width_linear = findViewById(R.id.width_linear);
        bottom_linear = findViewById(R.id.bottom_linear);
        image_holder = findViewById(R.id.image_holder);
        all_edittext_holder = findViewById(R.id.all_edittext_holder);
        avatar_holder = findViewById(R.id.avatar_holder);
        avatar = findViewById(R.id.avatar);
        firstname_holder = findViewById(R.id.firstname_holder);
        lastname_holder = findViewById(R.id.lastname_holder);
        firstname_edittext = findViewById(R.id.firstname_edittext);
        divider_first = findViewById(R.id.divider_first);
        lastname_edittext = findViewById(R.id.lastname_edittext);
        divider_last = findViewById(R.id.divider_last);
        imageview2 = findViewById(R.id.imageview2);
        imageview1 = findViewById(R.id.imageview1);
        btm_txt1 = findViewById(R.id.btm_txt1);
        btm_txt2 = findViewById(R.id.btm_txt2);
        fp.setType("image/*");
        fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        _file_cam = FileUtil.createNewPictureFile(context);
        Uri _uri_cam = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            _uri_cam = FileProvider.getUriForFile(getApplicationContext(), context.getPackageName() + ".provider", _file_cam);
        } else {
            _uri_cam = Uri.fromFile(_file_cam);
        }
        cam.putExtra(MediaStore.EXTRA_OUTPUT, _uri_cam);
        cam.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Fauth = FirebaseAuth.getInstance();
        my_data = context.getSharedPreferences("my_data", Activity.MODE_PRIVATE);
        sp_mydt = context.getSharedPreferences("sp_mydt", Activity.MODE_PRIVATE);
        sp_pc = context.getSharedPreferences("sp_pc", Activity.MODE_PRIVATE);
        CatchedImagePath = context.getSharedPreferences("CatchedImagePath", Activity.MODE_PRIVATE);

        done.setOnClickListener(_view -> {
            if (firstname_edittext.getText().toString().equals("")) {
                SketchwareUtil.showMessage(getApplicationContext(), "Please type your first name.");
            } else {
                if (lastname_edittext.getText().toString().equals("")) {
                    SketchwareUtil.showMessage(getApplicationContext(), "Please type your last name.");
                } else {
                    if (is_image_picked) {
                        propic.child(image_name).putFile(Uri.fromFile(new File(image_path))).addOnFailureListener(_propic_failure_listener).addOnProgressListener(_propic_upload_progress_listener).continueWithTask((Continuation<UploadTask.TaskSnapshot, Task<Uri>>) task -> propic.child(image_name).getDownloadUrl()).addOnCompleteListener(_propic_upload_success_listener);
                    } else {
                        SketchwareUtil.showMessage(getApplicationContext(), "Please pick an profile picture.");
                    }
                }
            }
        });

        avatar.setOnClickListener(_view -> _BottomSheet());

        firstname_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
                final String _charSeq = _param1.toString();
                if (_charSeq.length() > 2) {
                    done.setVisibility(View.VISIBLE);
                } else {
                    done.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {

            }

            @Override
            public void afterTextChanged(Editable _param1) {

            }
        });

        btm_txt2.setOnClickListener(_view -> {
            presentFragment(new TermsandprivacyActivity(), false);
        });

        _users_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    if (_childValue.containsKey("avatar") && (_childValue.containsKey("firstname") && _childValue.containsKey("lastname"))) {
                        firstname_edittext.setText(_childValue.get("firstname").toString());
                        lastname_edittext.setText(_childValue.get("lastname").toString());
                        sp_pc.edit().putString("Profile", "Completed").apply();
                        presentFragment(new LaunchActivity(), false);
                        com.bumptech.glide.Glide.with(getApplicationContext())
                                .load(_childValue.get("avatar").toString())
                                .override(1024, 1024)
                                .into(avatar);
                    }
                }
                if (_childKey.equals(uid)) {
                    if (_childValue.containsKey("avatar") && (_childValue.containsKey("firstname") && _childValue.containsKey("lastname"))) {
                        BotAvatar = _childValue.get("avatar").toString();
                        BotFirstname = _childValue.get("firstname").toString();
                        BotLastname = _childValue.get("lastname").toString();
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

            }

            @Override
            public void onCancelled(DatabaseError _param1) {
                final int _errorCode = _param1.getCode();
                final String _errorMessage = _param1.getMessage();

            }
        };
        users.addChildEventListener(_users_child_listener);

        _propic_upload_progress_listener = (OnProgressListener<UploadTask.TaskSnapshot>) _param1 -> {
            double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();

        };

        _propic_download_progress_listener = (OnProgressListener<FileDownloadTask.TaskSnapshot>) _param1 -> {
            double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();

        };

        _propic_upload_success_listener = _param1 -> {
            final String _downloadUrl = _param1.getResult().toString();
			/*
FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(_onCompleteListener);
*/
            time = Calendar.getInstance();
            SDK = Build.VERSION.SDK;
            Manufacturer = Build.MANUFACTURER;
            Device = Build.DEVICE;
            Model = Build.MODEL;
            Release = Build.VERSION.RELEASE;
            firstname = firstname_edittext.getText().toString();
            lastname = lastname_edittext.getText().toString();
            user_propic = _downloadUrl;
            session = Manufacturer.toUpperCase().concat(" ".concat(Device.concat(", ".concat(Model.concat(" ".concat(" Android ".concat(Release.concat(" ".concat("(".concat(SDK.concat(")")))))))))));
            details = new HashMap<>();
            details.put("firstname", firstname);
            details.put("lastname", lastname);
            details.put("avatar", user_propic);
            details.put("ban", "nope");
            details.put("last_seen", String.valueOf((long) (time.getTimeInMillis())));
            details.put("verified", "nope");
            details.put("bio", "");
            details.put("session", session);
            if (!(OneSignalUserID.contains("") && OneSignalPushToken.contains(""))) {
                details.put("OneSignalUserID", OneSignalUserID);
                details.put("OneSignalPushToken", OneSignalPushToken);
            }
            details.put("session_time", String.valueOf((long) (time.getTimeInMillis())));
            users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(details);
            details.clear();
            _CreateBotMessage();
            sp_mydt.edit().putString("firstname", firstname).apply();
            sp_mydt.edit().putString("lastname", lastname).apply();
            sp_mydt.edit().putString("avatar", _downloadUrl).apply();
            sp_mydt.edit().putString("session", session).apply();
            sp_mydt.edit().putString("session_time", String.valueOf((long) (time.getTimeInMillis()))).apply();
            sp_mydt.edit().putString("bio", "").apply();
            presentFragment(new LaunchActivity(), false);
        };

        _propic_download_success_listener = _param1 -> {
            final long _totalByteCount = _param1.getTotalByteCount();

        };

        _propic_delete_success_listener = _param1 -> {

        };

        _propic_failure_listener = _param1 -> {
            final String _message = _param1.getMessage();

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

        _Chat1_child_listener = new ChildEventListener() {
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
        CatchedImagePath.edit().putString("LatestImagePath", "-").apply();
		/*
OneSignal.init(SetupActivity.this, "AIzaSyBDLpxX9YYtP9i7w1MaHR2ZZIDE-n4P0Bc", "02c27ebc-a96d-4005-80d5-dc08294131a6");
 OneSignal.getCurrentOrNewInitBuilder()
.inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
.unsubscribeWhenNotificationsAreDisabled(true).init();
OneSignal.startInit(SetupActivity.this)
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
        if (sp_pc.getString("Profile", "").equals("Completed")) {
            presentFragment(new LaunchActivity(), false);
        }
        BotMessage = "Hi there!\nWelcome to Moon Meet.\nTelegram profile :\n@RayenMark\n@AzizVirus\nthis is an automated message.\nEnjoy!";
        uid = "acmH9sOFXjc7V9e0WoPeWBv3JTC3";
        mid = UserChats.push().getKey();
        midchats = Chat1.push().getKey();
        Chat1.removeEventListener(_Chat1_child_listener);
        Chat2.removeEventListener(_Chat2_child_listener);
        Chatroom = "chat/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("/".concat(uid)));
        Chatcopy = "chat/".concat(uid.concat("/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid())));
        Chat1 =
                _firebase.getReference(Chatroom);
        Chat2 =
                _firebase.getReference(Chatcopy);
        Chat1.addChildEventListener(_Chat1_child_listener);
        Chat2.addChildEventListener(_Chat2_child_listener);
        topbar.setElevation(2);
        done.setColorFilter(0xFF193566, PorterDuff.Mode.MULTIPLY);
        done.setVisibility(View.GONE);
        avatar_holder.setBackground(new GradientDrawable() {
            public GradientDrawable getIns(int a, int b) {
                this.setCornerRadius(a);
                this.setColor(b);
                return this;
            }
        }.getIns((int) SketchwareUtil.getDip(getApplicationContext(), (int) (100)), 0xFF193566));
        avatar_holder.setVisibility(View.VISIBLE);
        avatar.setVisibility(View.VISIBLE);
        imageview1.setVisibility(View.INVISIBLE);
        avatar.setCircleBackgroundColor(0xFF193566);
        is_image_picked = false;
        if (getArguments().getString("taked_photo").equals(".")) {
            is_image_picked = false;
            avatar_holder.setBackground(new GradientDrawable() {
                public GradientDrawable getIns(int a, int b) {
                    this.setCornerRadius(a);
                    this.setColor(b);
                    return this;
                }
            }.getIns((int) SketchwareUtil.getDip(getApplicationContext(), (int) (100)), 0xFF193566));
            avatar.setCircleBackgroundColor(0xFF193566);
            avatar_holder.setVisibility(View.VISIBLE);
            avatar.setVisibility(View.VISIBLE);
        } else {
            taked_photo = getArguments().getString("taked_photo");
            image_path = taked_photo;
            image_name = Uri.parse(taked_photo).getLastPathSegment();
            avatar.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(taked_photo, 1024, 1024));
            is_image_picked = true;
            avatar.setCircleBackgroundColor(Color.TRANSPARENT);
            avatar_holder.setBackground(new GradientDrawable() {
                public GradientDrawable getIns(int a, int b) {
                    this.setCornerRadius(a);
                    this.setColor(b);
                    return this;
                }
            }.getIns((int) SketchwareUtil.getDip(getApplicationContext(), (int) (100)), 0xFFFFFF));
        }
        firstname_edittext.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {

                divider_first.setBackgroundColor(0xFF193566);
                divider_last.setBackgroundColor(0xFFDADADA);
            } else {
                lastname_edittext.setOnFocusChangeListener((v1, hasFocus1) -> {
                    if (hasFocus1) {

                        divider_first.setBackgroundColor(0xFFDADADA);
                        divider_last.setBackgroundColor(0xFF193566);
                    } else {
                        divider_first.setBackgroundColor(0xFFDADADA);
                        divider_last.setBackgroundColor(0xFFDADADA);
                    }
                });
            }
        });
        androidx.appcompat.widget.TooltipCompat.setTooltipText(done, "Done");
    }

    @Override
    public void onActivityResultFragment(int _requestCode, int _resultCode, Intent _data) {
        switch (_requestCode) {
            case REQ_CD_FP:
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
                    image_path = _filePath.get((int) (0));
                    image_name = Uri.parse(_filePath.get((int) (0))).getLastPathSegment();
                    is_image_picked = true;
                    avatar.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(image_path, 1024, 1024));
                }
                break;

            case REQ_CD_CAM:
                if (_resultCode == Activity.RESULT_OK) {
                    String _filePath = _file_cam.getAbsolutePath();

                    image_path = _filePath;
                    image_name = Uri.parse(_filePath).getLastPathSegment();
                    is_image_picked = true;
                    avatar.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(image_path, 1024, 1024));
                }
                break;
            default:
                break;
        }
    }

    public void _BottomSheet() {
        final com.google.android.material.bottomsheet.BottomSheetDialog dialog = new com.google.android.material.bottomsheet.BottomSheetDialog(getParentActivity());
        View lay = getParentActivity().getLayoutInflater().inflate(R.layout.image_options, null);
        dialog.setContentView(lay);
        dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
        LinearLayout l1 = lay.findViewById(R.id.take_linear);
        LinearLayout l2 = lay.findViewById(R.id.upload_linear);
        LinearLayout l3 = lay.findViewById(R.id.remove_linear);
        ImageView i1 = lay.findViewById(R.id.take_img);
        ImageView i2 = lay.findViewById(R.id.upload_img);
        ImageView i3 = lay.findViewById(R.id.remove_img);
        TextView t1 = lay.findViewById(R.id.take_txt);
        TextView t2 = lay.findViewById(R.id.upload_txt);
        TextView t3 = lay.findViewById(R.id.remove_txt);
        i1.setColorFilter(0xFF726E69, PorterDuff.Mode.MULTIPLY);
        i2.setColorFilter(0xFF726E69, PorterDuff.Mode.MULTIPLY);
        i3.setColorFilter(0xFFE25050, PorterDuff.Mode.MULTIPLY);
        t1.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(), "fonts/rmedium.ttf"), 0);
        t2.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(), "fonts/rmedium.ttf"), 0);
        t3.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(), "fonts/rmedium.ttf"), 0);
        _RippleEffects("#FFDADADA", l1);
        _RippleEffects("#FFDADADA", l2);
        _RippleEffects("#FFDADADA", l3);
        if (is_image_picked) {
            l3.setVisibility(View.VISIBLE);
        } else {
            l3.setVisibility(View.GONE);
        }
        l1.setOnClickListener(_view -> {
            presentFragment(new CameraActivity(), false);
            dialog.dismiss();
        });
        l2.setOnClickListener(_view -> {
            Bundle args = new Bundle();
            args.putString("multiple_images", "false");
            presentFragment(new ImagePickerActivity(args), false);
            dialog.dismiss();
        });
        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                avatar.setImageResource(R.drawable.actions_setphoto);
                is_image_picked = false;
                avatar.setCircleBackgroundColor(0xFF193566);
                avatar_holder.setBackground(new GradientDrawable() {
                    public GradientDrawable getIns(int a, int b) {
                        this.setCornerRadius(a);
                        this.setColor(b);
                        return this;
                    }
                }.getIns((int) SketchwareUtil.getDip(getApplicationContext(), (int) (100)), 0xFF193566));
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public void _RippleEffects(final String _color, final View _view) {
        android.content.res.ColorStateList clr = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(_color)});
        android.graphics.drawable.RippleDrawable ripdr = new android.graphics.drawable.RippleDrawable(clr, null, null);
        _view.setBackground(ripdr);
    }


    public void _CreateBotMessage() {
        CALENDAR_EXTRA = Calendar.getInstance();
        message_map = new HashMap<>();
        message_map.put("firstname", BotAvatar);
        message_map.put("lastname", BotLastname);
        message_map.put("tofirstname", firstname);
        message_map.put("tolastname", lastname);
        message_map.put("uid", uid);
        message_map.put("text", BotMessage);
        message_map.put("time", String.valueOf((long) (CALENDAR_EXTRA.getTimeInMillis())));
        message_map.put("mid", mid);
        message_map.put("type", "message");
        message_map.put("status", "Sent");
        Chat1.child(message_map.get("mid").toString()).updateChildren(message_map);
        if (!FirebaseAuth.getInstance().getCurrentUser().getUid().equals(uid)) {
            Chat2.child(message_map.get("mid").toString()).updateChildren(message_map);
        }
        message_map.clear();
        CALENDAR_EXTRA = Calendar.getInstance();
        message_map = new HashMap<>();
        message_map.put("last_message_uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
        message_map.put("last_message_mid", mid);
        message_map.put("last_message_status", "Sent");
        message_map.put("last_message_type", "text");
        message_map.put("last_message_time", String.valueOf((long) (CALENDAR_EXTRA.getTimeInMillis())));
        message_map.put("last_message_text", BotMessage);
        message_map.put("lastseen", String.valueOf((long) (CALENDAR_EXTRA.getTimeInMillis())));
        message_map.put("uid", uid);
        message_map.put("firstname", BotFirstname);
        message_map.put("lastname", BotLastname);
        message_map.put("avatar", BotAvatar);
        UserChats.child(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("/".concat(uid))).updateChildren(message_map);
        if (!uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            message_map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
            message_map.put("firstname", firstname);
            message_map.put("lastname", lastname);
            message_map.put("avatar", user_propic);
            UserChats.child(uid.concat("/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid()))).updateChildren(message_map);
        }
    }
}