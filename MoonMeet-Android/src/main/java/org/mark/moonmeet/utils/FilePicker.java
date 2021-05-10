package org.mark.moonmeet.utils;

import android.content.Intent;

import org.mark.moonmeet.ui.BaseFragment;

public class FilePicker {

    public static void pickSingleFile(BaseFragment fragment, String mimeType, int resultCode) {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType(mimeType);
        fragment.startActivityForResult(Intent.createChooser(intent, "Choose a file"), resultCode);
    }
}
