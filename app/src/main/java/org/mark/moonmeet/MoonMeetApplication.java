package org.mark.moonmeet;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import androidx.multidex.MultiDex;

import org.mark.axemojiview.AXEmojiManager;
import org.mark.axemojiview.emoji.iosprovider.AXIOSEmojiProvider;
import org.mark.moonmeet.utils.AndroidUtilities;

public class MoonMeetApplication extends Application {

	@SuppressLint("StaticFieldLeak")
	public static Context applicationContext;
	public static volatile Handler applicationHandler;

	@Override
	public void onCreate() {
		super.onCreate();
		applicationContext = getApplicationContext();
		AXEmojiManager.install(applicationContext, new AXIOSEmojiProvider(applicationContext));
		// AndroidUtilities.fillStatusBarHeight(applicationContext);
		applicationHandler = new Handler(applicationContext.getMainLooper());
		Runnable runnable = () -> Log.d("MoonMeetApplication", "Starting runOnUIThread");
		AndroidUtilities.runOnUIThread(runnable, 200);
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(applicationContext);
	}

}
