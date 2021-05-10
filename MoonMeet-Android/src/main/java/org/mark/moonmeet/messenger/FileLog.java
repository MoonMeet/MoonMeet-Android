package org.mark.moonmeet.messenger;

import android.util.Log;

public class FileLog {
    public static void e(StackTraceElement e){
        Log.e("MoonMeet", e.toString());
    }
    public static void d(StackTraceElement e){
        Log.e("MoonMeet", e.toString());
    }
    public static void d(String e){
        Log.e("MoonMeet", e);
    }

    public static void e(Exception e) {
        Log.e("MoonMeet", e.toString());
    }

    public static void e(String toString) {
        Log.e("MoonMeet", toString);
    }
	public static void e(Throwable e){
        Log.e("MoonMeet", e.toString());
    }

}
