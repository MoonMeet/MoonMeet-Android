package org.mark.moonmeet;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Handler;

import androidx.multidex.MultiDex;


import org.mark.axemojiview.AXEmojiManager;
import org.mark.axemojiview.emoji.iosprovider.AXIOSEmojiProvider;

public class MoonMeetApplication extends Application {

	@SuppressLint("StaticFieldLeak")
	public static Context applicationContext;

	public static volatile Handler applicationHandler;

	@Override
	public void onCreate() {
		super.onCreate();
		applicationContext = getApplicationContext();
		AXEmojiManager.install(applicationContext, new AXIOSEmojiProvider(applicationContext));
		applicationHandler = new Handler(applicationContext.getMainLooper());
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(applicationContext);
	}
}
