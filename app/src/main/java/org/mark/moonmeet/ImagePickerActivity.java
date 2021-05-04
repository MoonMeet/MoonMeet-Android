package org.mark.moonmeet;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.FirebaseApp;

import org.mark.moonmeet.ui.BaseFragment;
import org.mark.moonmeet.utils.FilePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ImagePickerActivity extends BaseFragment {

    public static final int REQ_CD_IMAGEPICKER = 102;

    private String imageFormats = "";
    private double selected = 0;
    private boolean MultiPicker = false;
    private String a = "";
    private String folderPaths = "";
    private boolean inFolder = false;
    private double n1 = 0;
    private double n2 = 0;
    private HashMap<String, Object> cacheMap = new HashMap<>();

    private ArrayList<HashMap<String, Object>> itemsList = new ArrayList<>();
    private ArrayList<String> cache = new ArrayList<>();

    private LinearLayout topbar;
    private LinearLayout linear_back_holder;
    private ImageView back;
    private TextView topbar_txt;
    private LinearLayout topbar_space;
    private TextView toolbar_subtitle;
    private ImageView external_picker_img;
    private LinearLayout nophtsyet_grid;
    private LinearLayout linear_bottom;
    private MaterialTextView nophtsyet_full_txt;
    private MaterialTextView nophtsyet_mini_txt;
    private TextView textview_bottom;

    private Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    private File _file_cam;
    private Intent ImagePicker = new Intent(Intent.ACTION_GET_CONTENT);
    private SharedPreferences CatchedImagePath;
    private Intent getMultipleData = new Intent();
    private android.widget.GridView gridview1;

    public ImagePickerActivity(Bundle args) {
        super(args);
    }

    @Override
    public void onActivityResultFragment(final int _requestCode, int _resultCode, Intent _data) {
        if (_requestCode == REQ_CD_IMAGEPICKER) {
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

                    CatchedImagePath.edit().putString("LatestImagePath", _filePath.get((int) (0))).apply();
                    finishFragment();
                } catch (Exception e) {

                    SketchwareUtil.showMessage(getApplicationContext(), (e.toString()));
                }
            }
        }
    }

    @Override
    public View createView(Context context) {
        fragmentView = new FrameLayout(context);
        actionBar.setAddToContainer(false);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.image_picker, (ViewGroup) fragmentView, false);
        ((ViewGroup) fragmentView).addView(view);
        initialize(context);
        FirebaseApp.initializeApp(getParentActivity());
        initializeLogic();
        return fragmentView;
    }

    private void initialize(Context context) {
        topbar = findViewById(R.id.topbar);
        linear_back_holder = findViewById(R.id.linear_back_holder);
        back = findViewById(R.id.back);
        topbar_txt = findViewById(R.id.topbar_txt);
        topbar_space = findViewById(R.id.topbar_space);
        toolbar_subtitle = findViewById(R.id.toolbar_subtitle);
        external_picker_img = findViewById(R.id.external_picker_img);
        nophtsyet_grid = findViewById(R.id.nophtsyet_grid);
        linear_bottom = findViewById(R.id.linear_bottom);
        nophtsyet_full_txt = findViewById(R.id.nophtsyet_full_txt);
        nophtsyet_mini_txt = findViewById(R.id.nophtsyet_mini_txt);
        textview_bottom = findViewById(R.id.textview_bottom);
        _file_cam = FileUtil.createNewPictureFile(getApplicationContext());
        Uri _uri_cam = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            _uri_cam = FileProvider.getUriForFile(getApplicationContext(), getParentActivity().getPackageName() + ".provider", _file_cam);
        } else {
            _uri_cam = Uri.fromFile(_file_cam);
        }
        cam.putExtra(MediaStore.EXTRA_OUTPUT, _uri_cam);
        cam.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        ImagePicker.setType("image/*");
        ImagePicker.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        CatchedImagePath = getParentActivity().getSharedPreferences("CatchedImagePath", Activity.MODE_PRIVATE);

        back.setOnClickListener(_view -> finishFragment(true));

        external_picker_img.setOnClickListener(_view -> {
            FilePicker.pickSingleFile(this, "image/*", 102);
        });
    }

    private void initializeLogic() {
        back.setImageTintList(new ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor("#FF193566"),
                Color.parseColor("#FF193566")}));
        external_picker_img.setImageTintList(new ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor("#FF193566"),
                Color.parseColor("#FF193566")}));
        topbar.setElevation((int) 2);
        imageFormats = "|.jpeg|.bmp|.png|.jpg|";
        MultiPicker = false;
        selected = 0;
        if (getArguments().containsKey("multiple_images")) {
            if (getArguments().getString("multiple_images").equals("true")) {
                MultiPicker = true;
            } else {
                MultiPicker = false;
            }
        } else {
            MultiPicker = false;
        }
        getAllShownImagesPath(getParentActivity());
        gridview1 = new GridView(getParentActivity());

        gridview1.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));

        gridview1.setAdapter(new CustomGridViewAdapter(itemsList));

        gridview1.setNumColumns(2);

        gridview1.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);

        gridview1.setGravity(Gravity.CENTER);

        gridview1.setBackgroundColor(0xFFFFFFFF);

        nophtsyet_grid.addView(gridview1);

        gridview1.setOnItemClickListener((a, b, c, d) -> _onItemClick(c));
        toolbar_subtitle.setText("Storage");
        if (MultiPicker) {
            _refreshSelected();
        } else {
            linear_bottom.setVisibility(View.GONE);
        }
        nophtsyet_full_txt.setVisibility(View.GONE);
        nophtsyet_mini_txt.setVisibility(View.GONE);
        topbar_txt.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(), "fonts/roboto_regular.ttf"), 0);
        toolbar_subtitle.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(), "fonts/royal_404.ttf"), 0);
        nophtsyet_full_txt.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(), "fonts/roboto_bold.ttf"), 0);
        nophtsyet_mini_txt.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(), "fonts/roboto_regular.ttf"), 0);
        textview_bottom.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(), "fonts/en_light.ttf"), 0);
    }

    @Override
    public boolean onBackPressed() {
        if (inFolder) {
            if (selected > 0) {
                selected = 0;
                n1 = 0;
                for (int _repeat17 = 0; _repeat17 < (int) (itemsList.size()); _repeat17++) {
                    itemsList.get((int) n1).put("select", "0");
                    n1++;
                }
                ((BaseAdapter) gridview1.getAdapter()).notifyDataSetChanged();
                _refreshSelected();
            } else {
                selected = 0;
                folderPaths = "";
                inFolder = false;
                _refreshSelected();
                itemsList.clear();
                getAllShownImagesPath(getParentActivity());
                ((BaseAdapter) gridview1.getAdapter()).notifyDataSetChanged();
                gridview1.setVisibility(View.VISIBLE);
                nophtsyet_full_txt.setVisibility(View.GONE);
                nophtsyet_mini_txt.setVisibility(View.GONE);
                toolbar_subtitle.setText("Storage");
            }
        } else {
            finishFragment();
        }
        return false;
    }

    public void getAllShownImagesPath(Activity activity) {
        android.net.Uri uri;
        android.database.Cursor cursor;
        int column_index_data, column_index_folder_name;
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {android.provider.MediaStore.MediaColumns.DATA, android.provider.MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        cursor = activity.getContentResolver().query(uri, projection, null, null, null);
        column_index_data = cursor.getColumnIndexOrThrow(android.provider.MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(android.provider.MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            _filterToFolder(absolutePathOfImage);
        }
        ;
    }

    public void _filterToFolder(final String _path) {
        a = _path.substring((int) (0), (int) (_path.lastIndexOf("/")));
        if (folderPaths.contains(":".concat(a.concat(":")))) {

        } else {
            folderPaths = folderPaths.concat(":".concat(a.concat(":")));
            {
                HashMap<String, Object> _item = new HashMap<>();
                _item.put("type", "folder");
                itemsList.add(_item);
            }

            itemsList.get((int) itemsList.size() - 1).put("path", a);
            itemsList.get((int) itemsList.size() - 1).put("name", Uri.parse(a).getLastPathSegment());
            itemsList.get((int) itemsList.size() - 1).put("icon", _path);
        }
    }

    public void _onItemClick(final double _pos) {
        if (itemsList.get((int) _pos).get("type").toString().equals("folder")) {
            _loadFolder(itemsList.get((int) _pos).get("path").toString());
            inFolder = true;
        } else {
            if (MultiPicker) {
                if (itemsList.get((int) _pos).get("select").toString().equals("1")) {
                    itemsList.get((int) _pos).put("select", "0");
                    selected--;
                } else {
                    itemsList.get((int) _pos).put("select", "1");
                    selected++;
                }
                _refreshSelected();
                ((BaseAdapter) gridview1.getAdapter()).notifyDataSetChanged();
            } else {
                CatchedImagePath.edit().putString("LatestImagePath", itemsList.get((int) _pos).get("path").toString()).apply();
                finishFragment();
            }
        }
    }

    public void _removeView(final View _v) {
        if (_v.getParent() != null) {

            ((ViewGroup) _v.getParent()).removeView(_v);

        }
        ;
    }

    public void _loadFolder(final String _path) {
        n1 = 0;
        selected = 0;
        _refreshSelected();
        cache.clear();
        itemsList.clear();
        FileUtil.listDir(_path, cache);
        if (cache.size() > 0) {
            nophtsyet_mini_txt.setVisibility(View.GONE);
            nophtsyet_full_txt.setVisibility(View.GONE);
            gridview1.setVisibility(View.VISIBLE);
            for (int _repeat22 = 0; _repeat22 < (int) (cache.size()); _repeat22++) {
                if (FileUtil.isFile(cache.get((int) (n1))) && (cache.get((int) (n1)).contains(".") && imageFormats.contains("|".concat(cache.get((int) (n1)).substring((int) (cache.get((int) (n1)).lastIndexOf(".")), (int) (cache.get((int) (n1)).length())).concat("|"))))) {
                    {
                        HashMap<String, Object> _item = new HashMap<>();
                        _item.put("type", "file");
                        itemsList.add(_item);
                    }

                    itemsList.get((int) itemsList.size() - 1).put("path", cache.get((int) (n1)));
                    itemsList.get((int) itemsList.size() - 1).put("name", Uri.parse(cache.get((int) (n1))).getLastPathSegment());
                    itemsList.get((int) itemsList.size() - 1).put("select", "0");
                }
                n1++;
            }
            cache.clear();
        } else {
            gridview1.setVisibility(View.GONE);
            nophtsyet_full_txt.setVisibility(View.VISIBLE);
            nophtsyet_mini_txt.setVisibility(View.VISIBLE);
        }
        ((BaseAdapter) gridview1.getAdapter()).notifyDataSetChanged();
        toolbar_subtitle.setText(Uri.parse(_path).getLastPathSegment());
    }

    public void _refreshSelected() {
        if (selected > 0) {
            linear_bottom.setEnabled(true);
            textview_bottom.setText(String.valueOf((long) (selected)).concat(" IMAGES SELECTED, TAP HERE TO CONTINUE"));
        } else {
            linear_bottom.setEnabled(false);
            textview_bottom.setText("NO IMAGE SELECTED");
        }
    }

    public void _applySetItem(final double _pos, final View _a, final View _b, final ImageView _c, final TextView _d, final View _e) {
        _d.setTypeface(Typeface.createFromAsset(getParentActivity().getAssets(), "fonts/royal_404.ttf"), 0);
        _a.setElevation(9f);
        _removeView(_b);
        _removeView(_c);
        _removeView(_e);
        _e.setVisibility(View.GONE);
        if (itemsList.get((int) _pos).get("type").toString().equals("folder")) {
            if (FileUtil.getFileLength(itemsList.get((int) _pos).get("icon").toString()) > 0) {
                _setImageFromFile(_c, itemsList.get((int) _pos).get("icon").toString());
            } else {
                _e.setVisibility(View.GONE);
            }
        } else {
            _setImageFromFile(_c, itemsList.get((int) _pos).get("path").toString());
            if (itemsList.get((int) _pos).get("select").toString().equals("1")) {
                _e.setVisibility(View.VISIBLE);
            }
        }
        _e.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        ((LinearLayout) _a).removeAllViews();
        android.widget.RelativeLayout rl = new android.widget.RelativeLayout(getParentActivity());
        rl.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        rl.addView(_c);
        rl.addView(_b);
        rl.addView(_e);
        ((LinearLayout) _a).addView(rl);
        _d.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        _d.setText(itemsList.get((int) _pos).get("name").toString());
    }

    public void _setImageFromFile(final ImageView _img, final String _path) {
        Uri imageUri = Uri.fromFile(new java.io.File(_path));
        Glide.with(getApplicationContext()).load(imageUri).into(_img);
    }

    public class CustomGridViewAdapter extends BaseAdapter {
        ArrayList<HashMap<String, Object>> _data;

        public CustomGridViewAdapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }

        @Override
        public int getCount() {
            return _data.size();
        }


        @Override
        public Object getItem(int position) {
            return _data.get(position);
        }

        @Override
        public long getItemId(int _index) {
            return _index;
        }

        @Override
        public View getView(final int _position, View _view, ViewGroup _viewGroup) {
            LayoutInflater _inflater = (LayoutInflater) getParentActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View _v = _view;
            if (_v == null) {
                _v = _inflater.inflate(R.layout.grid_item, null);
            }
            final LinearLayout linear1 = (LinearLayout) _v.findViewById(R.id.linear1);
            final LinearLayout linear2 = (LinearLayout) _v.findViewById(R.id.linear2);
            final LinearLayout linear3 = (LinearLayout) _v.findViewById(R.id.linear3);
            final ImageView imageview1 = (ImageView) _v.findViewById(R.id.imageview1);
            final TextView textview1 = (TextView) _v.findViewById(R.id.textview1);
            final ImageView checked_img = (ImageView) _v.findViewById(R.id.imageview2);
            checked_img.setImageTintList(new android.content.res.ColorStateList(new int[][]{{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[]{Color.parseColor("#FF193566"),
                    Color.parseColor("#FF193566")}));
            _applySetItem(_position, linear1, linear2, imageview1, textview1, linear3);

            return _v;
        }
    }
}
