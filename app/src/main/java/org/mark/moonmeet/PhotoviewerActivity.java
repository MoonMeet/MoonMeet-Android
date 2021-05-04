package org.mark.moonmeet;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import org.mark.moonmeet.utils.AndroidUtilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class PhotoviewerActivity extends BaseFragment {

    private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
    private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();

    private double time = 0;
    private String image_link = "";
    private String uid = "";
    private String firstname = "";
    private String lastname = "";
    private String image_time_str = "";
    private String Chatroom = "";
    private String Chatcopy = "";
    private boolean error = false;
    private String downloadFilePath = "";
    private String getPath = "";

    private ArrayList<HashMap<String, Object>> lengthMedia_LM = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> Media_LM = new ArrayList<>();

    private LinearLayout base;
    private LinearLayout top_description;
    private LinearLayout divider_top;
    private ImageView photoviewer;
    private LinearLayout bottom_description;
    private ImageView back;
    private TextView length;
    private LinearLayout space;
    private ImageView download;
    private LinearLayout divider_bottom;
    private TextView name;
    private TextView image_time;

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
    private Calendar c = Calendar.getInstance();
    private DatabaseReference Chat1 = _firebase.getReference("Chatroom");
    private ChildEventListener _Chat1_child_listener;
    private DatabaseReference Chat2 = _firebase.getReference("Chatcopy");
    private ChildEventListener _Chat2_child_listener;
    private StorageReference image_fs = _firebase_storage.getReference("Messages/Images");
    private OnCompleteListener<Uri> _image_fs_upload_success_listener;
    private OnSuccessListener<FileDownloadTask.TaskSnapshot> _image_fs_download_success_listener;
    private OnSuccessListener _image_fs_delete_success_listener;
    private OnProgressListener _image_fs_upload_progress_listener;
    private OnProgressListener _image_fs_download_progress_listener;
    private OnFailureListener _image_fs_failure_listener;
    private SharedPreferences sp_paths;
    private StorageReference storageReference1;
    private FileDownloadTask downloadTask1;

    {
    }

    public PhotoviewerActivity(String user_uid, String img_link, String img_time, String firstname, String lastname) {
        uid = user_uid;
        image_time_str = img_time;
        image_link = img_link;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    @Override
    public View createView(Context context) {
        fragmentView = new FrameLayout(context);
        actionBar.setAddToContainer(false);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.photoviewer, ((ViewGroup) fragmentView), false);
        ((ViewGroup) fragmentView).addView(view);
        initialize(context);
        com.google.firebase.FirebaseApp.initializeApp(getParentActivity());
        initializeLogic();
        return fragmentView;
    }

    private void initialize(Context context) {
        base = (LinearLayout) findViewById(R.id.base);
        top_description = (LinearLayout) findViewById(R.id.top_description);
        divider_top = (LinearLayout) findViewById(R.id.divider_top);
        photoviewer = (ImageView) findViewById(R.id.photoviewer);
        bottom_description = (LinearLayout) findViewById(R.id.bottom_description);
        back = (ImageView) findViewById(R.id.back);
        length = (TextView) findViewById(R.id.length);
        space = (LinearLayout) findViewById(R.id.space);
        download = (ImageView) findViewById(R.id.download);
        divider_bottom = (LinearLayout) findViewById(R.id.divider_bottom);
        name = (TextView) findViewById(R.id.name);
        image_time = (TextView) findViewById(R.id.image_time);
        Fauth = FirebaseAuth.getInstance();
        sp_paths = context.getSharedPreferences("MoonMeetPaths", Activity.MODE_PRIVATE);

        back.setOnClickListener(_view -> finishFragment());

        download.setOnClickListener(_view -> {
            try {

                _download(image_link);
            } catch (Exception e) {

                SketchwareUtil.showMessage(getApplicationContext(), (e.toString()));
            }
        });

        _Chat1_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    lengthMedia_LM.add(_childValue);
                    Media_LM.add(_childValue);
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
        Chat1.addChildEventListener(_Chat1_child_listener);

        _Chat2_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (!uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    lengthMedia_LM.add(_childValue);
                    Media_LM.add(_childValue);
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
        Chat2.addChildEventListener(_Chat2_child_listener);

        _image_fs_upload_progress_listener = (OnProgressListener<UploadTask.TaskSnapshot>) _param1 -> {
            double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();

        };

        _image_fs_download_progress_listener = (OnProgressListener<FileDownloadTask.TaskSnapshot>) _param1 -> {
            double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();

        };

        _image_fs_upload_success_listener = _param1 -> {
            final String _downloadUrl = _param1.getResult().toString();

        };

        _image_fs_download_success_listener = _param1 -> {
            final long _totalByteCount = _param1.getTotalByteCount();
            SketchwareUtil.showMessage(getApplicationContext(), "Image saved on MoonMeet Folder, Total Download Size is : ".concat(String.valueOf(_totalByteCount)));
            FileUtil.moveFile(getPath, sp_paths.getString("ImagePath", "").concat("/".concat(Uri.parse(getPath).getLastPathSegment())));
        };

        _image_fs_delete_success_listener = _param1 -> {

        };

        _image_fs_failure_listener = _param1 -> {
            final String _message = _param1.getMessage();
            SketchwareUtil.showMessage(getApplicationContext(), _message);
        };

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
        // getExtraKey
		/*uid = getIntent().getStringExtra("uid");
		image_time_str = getIntent().getStringExtra("image_time");
		image_link = getIntent().getStringExtra("image_link");
		firstname = getIntent().getStringExtra("firstname");
		lastname = getIntent().getStringExtra("lastname");*/
        // setUp Configuration
        _transitionComplete(photoviewer, "image");
        Glide.with(getApplicationContext()).load(Uri.parse(image_link)).into(photoviewer);
        name.setText(firstname.concat(" ".concat(lastname)));
        _Time(Double.parseDouble(image_time_str), image_time);
        // Database Configuration
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
        // setUpDesign
        top_description.setElevation((int) 2);
        download.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor("#193566"),
                Color.parseColor("#193566")}));
        back.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor("#193566"),
                Color.parseColor("#193566")}));
        androidx.appcompat.widget.TooltipCompat.setTooltipText(back, "Back");
        androidx.appcompat.widget.TooltipCompat.setTooltipText(download, "Download");
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

    public void _transitionComplete(final View _view, final String _transitionName) {
        _view.setTransitionName(_transitionName);
    }

    public void _download(final String _url) {
        error = false;
        try {
            storageReference1 = _firebase_storage.getReferenceFromUrl(_url);

            downloadFilePath = getParentActivity().getExternalFilesDir(null).getAbsolutePath().concat("Messages/Images/").concat(storageReference1.getName());
            FileUtil.writeFile(downloadFilePath, "");
            downloadTask1 = storageReference1.getFile(new File(downloadFilePath));
        } catch (Exception e) {
            error = true;
            AndroidUtilities.showToast(e.toString());
            //Do something here

        } finally {
            if (!error) {
                //AddOnListener
                //_userdata is your firebase storage component name
                downloadTask1.addOnSuccessListener(_image_fs_download_success_listener).addOnFailureListener(_image_fs_failure_listener).addOnProgressListener(_image_fs_download_progress_listener);
                getPath = downloadFilePath;
            }
        }
    }

    public void _init() {
    }
}