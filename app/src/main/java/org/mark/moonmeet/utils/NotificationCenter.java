package org.mark.moonmeet.utils;

import android.util.SparseArray;

import androidx.annotation.UiThread;

import org.mark.moonmeet.MoonMeetApplication;
import org.mark.moonmeet.messenger.FileLog;

import java.util.ArrayList;
import java.util.HashMap;

public class NotificationCenter {

    private static int totalEvents = 1;

    public static final int didChangeText = totalEvents++;

    public static final int didClickStory = totalEvents++;
    public static final int didClickConversation = totalEvents++;

    public static final int didClickImage = totalEvents++;
    public static final int didDeleteMessage = totalEvents++;
    public static final int didClickActiveUser = totalEvents++;
    public static final int didDeleteMessageForever = totalEvents++;


    public static final int getChatReplyData = totalEvents++;

    public static final int getNeedPresentImagePicker = totalEvents++;
    public static final int getNeedPresentCamera = totalEvents++;

    public static final int stopAllHeavyOperations = totalEvents++;
    public static final int startAllHeavyOperations = totalEvents++;
    public static final int didReplacedPhotoInMemCache = totalEvents++;
    private SparseArray<ArrayList<NotificationCenterDelegate>> observers = new SparseArray<>();
    private SparseArray<ArrayList<NotificationCenterDelegate>> removeAfterBroadcast = new SparseArray<>();
    private SparseArray<ArrayList<NotificationCenterDelegate>> addAfterBroadcast = new SparseArray<>();
    private ArrayList<DelayedPost> delayedPosts = new ArrayList<>(10);
    private ArrayList<DelayedPost> delayedPostsTmp = new ArrayList<>(10);
    private ArrayList<PostponeNotificationCallback> postponeCallbackList = new ArrayList<>(10);

    private int broadcasting = 0;

    private int animationInProgressCount;
    private int animationInProgressPointer = 1;

    private final HashMap<Integer, int[]> allowedNotifications = new HashMap<>();

    public interface NotificationCenterDelegate {
        void didReceivedNotification(int id, Object... args);
    }

    private static class DelayedPost {

        private DelayedPost(int id, Object[] args) {
            this.id = id;
            this.args = args;
        }

        private int id;
        private Object[] args;
    }

    private int currentAccount;
    private int currentHeavyOperationFlags;
    private static volatile NotificationCenter[] Instance = new NotificationCenter[1];
    private static volatile NotificationCenter globalInstance;

    @UiThread
    public static NotificationCenter getInstance(int num) {
        NotificationCenter localInstance = Instance[num];
        if (localInstance == null) {
            synchronized (NotificationCenter.class) {
                localInstance = Instance[num];
                if (localInstance == null) {
                    Instance[num] = localInstance = new NotificationCenter(num);
                }
            }
        }
        return localInstance;
    }

    public static NotificationCenter getInstance() {
        NotificationCenter localInstance = Instance[0];
        if (localInstance == null) {
            synchronized (NotificationCenter.class) {
                localInstance = Instance[0];
                if (localInstance == null) {
                    Instance[0] = localInstance = new NotificationCenter(0);
                }
            }
        }
        return localInstance;
    }

    @UiThread
    public static NotificationCenter getGlobalInstance() {
        NotificationCenter localInstance = globalInstance;
        if (localInstance == null) {
            synchronized (NotificationCenter.class) {
                localInstance = globalInstance;
                if (localInstance == null) {
                    globalInstance = localInstance = new NotificationCenter(-1);
                }
            }
        }
        return localInstance;
    }

    public NotificationCenter(int account) {
        currentAccount = account;
    }

    public int setAnimationInProgress(int oldIndex, int[] allowedNotifications) {
        onAnimationFinish(oldIndex);
        if (animationInProgressCount == 0) {
            NotificationCenter.getGlobalInstance().postNotificationName(stopAllHeavyOperations, 512);
        }

        animationInProgressCount++;
        animationInProgressPointer++;

        if (allowedNotifications == null) {
            allowedNotifications = new int[0];
        }

        this.allowedNotifications.put(animationInProgressPointer, allowedNotifications);

        return animationInProgressPointer;
    }

    public void updateAllowedNotifications(int transitionAnimationIndex, int[] allowedNotifications) {
        if (this.allowedNotifications.containsKey(transitionAnimationIndex)) {
            if (allowedNotifications == null) {
                allowedNotifications = new int[0];
            }
            this.allowedNotifications.put(transitionAnimationIndex, allowedNotifications);
        }
    }

    public void onAnimationFinish(int index) {
        int[] notifications = allowedNotifications.remove(index);
        if (notifications != null) {
            animationInProgressCount--;
            if (animationInProgressCount == 0) {
                NotificationCenter.getGlobalInstance().postNotificationName(startAllHeavyOperations, 512);
                runDelayedNotifications();
            }
        }
    }

    public void runDelayedNotifications() {
        if (!delayedPosts.isEmpty()) {
            delayedPostsTmp.clear();
            delayedPostsTmp.addAll(delayedPosts);
            delayedPosts.clear();
            for (int a = 0; a < delayedPostsTmp.size(); a++) {
                DelayedPost delayedPost = delayedPostsTmp.get(a);
                postNotificationNameInternal(delayedPost.id, true, delayedPost.args);
            }
            delayedPostsTmp.clear();
        }
    }

    public boolean isAnimationInProgress() {
        return animationInProgressCount > 0;
    }

    public int getCurrentHeavyOperationFlags() {
        return currentHeavyOperationFlags;
    }

    public void postNotificationName(int id, Object... args) {
        boolean allowDuringAnimation = id == startAllHeavyOperations || id == stopAllHeavyOperations || id == didReplacedPhotoInMemCache;
        if (!allowDuringAnimation && !allowedNotifications.isEmpty()) {
            int size = allowedNotifications.size();
            int allowedCount = 0;
            for(Integer key : allowedNotifications.keySet()) {
                int[] allowed = allowedNotifications.get(key);
                if (allowed != null) {
                    for (int a = 0; a < allowed.length; a++) {
                        if (allowed[a] == id) {
                            allowedCount++;
                            break;
                        }
                    }
                } else {
                    break;
                }
            }
            allowDuringAnimation = size == allowedCount;
        }
        if (id == startAllHeavyOperations) {
            Integer flags = (Integer) args[0];
            currentHeavyOperationFlags &=~ flags;
        } else if (id == stopAllHeavyOperations) {
            Integer flags = (Integer) args[0];
            currentHeavyOperationFlags |= flags;
        }
        postNotificationNameInternal(id, allowDuringAnimation, args);
    }

    @UiThread
    public void postNotificationNameInternal(int id, boolean allowDuringAnimation, Object... args) {
        if (BuildVars.DEBUG_VERSION) {
            if (Thread.currentThread() != MoonMeetApplication.applicationHandler.getLooper().getThread()) {
                throw new RuntimeException("postNotificationName allowed only from MAIN thread");
            }
        }
        if (!allowDuringAnimation && isAnimationInProgress()) {
            DelayedPost delayedPost = new DelayedPost(id, args);
            delayedPosts.add(delayedPost);
            if (BuildVars.LOGS_ENABLED) {
                FileLog.e("delay post notification " + id + " with args count = " + args.length);
            }
            return;
        }
        if (!postponeCallbackList.isEmpty()) {
            for (int i = 0; i < postponeCallbackList.size(); i++) {
                if (postponeCallbackList.get(i).needPostpone(id, currentAccount, args)) {
                    delayedPosts.add(new DelayedPost(id, args));
                    return;
                }
            }
        }
        broadcasting++;
        ArrayList<NotificationCenterDelegate> objects = observers.get(id);
        if (objects != null && !objects.isEmpty()) {
            for (int a = 0; a < objects.size(); a++) {
                NotificationCenterDelegate obj = objects.get(a);
                obj.didReceivedNotification(id, args);
            }
        }
        broadcasting--;
        if (broadcasting == 0) {
            if (removeAfterBroadcast.size() != 0) {
                for (int a = 0; a < removeAfterBroadcast.size(); a++) {
                    int key = removeAfterBroadcast.keyAt(a);
                    ArrayList<NotificationCenterDelegate> arrayList = removeAfterBroadcast.get(key);
                    for (int b = 0; b < arrayList.size(); b++) {
                        removeObserver(arrayList.get(b), key);
                    }
                }
                removeAfterBroadcast.clear();
            }
            if (addAfterBroadcast.size() != 0) {
                for (int a = 0; a < addAfterBroadcast.size(); a++) {
                    int key = addAfterBroadcast.keyAt(a);
                    ArrayList<NotificationCenterDelegate> arrayList = addAfterBroadcast.get(key);
                    for (int b = 0; b < arrayList.size(); b++) {
                        addObserver(arrayList.get(b), key);
                    }
                }
                addAfterBroadcast.clear();
            }
        }
    }

    public void addObserver(NotificationCenterDelegate observer, int id) {
        if (BuildVars.DEBUG_VERSION) {
            if (Thread.currentThread() != MoonMeetApplication.applicationHandler.getLooper().getThread()) {
                throw new RuntimeException("addObserver allowed only from MAIN thread");
            }
        }
        if (broadcasting != 0) {
            ArrayList<NotificationCenterDelegate> arrayList = addAfterBroadcast.get(id);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                addAfterBroadcast.put(id, arrayList);
            }
            arrayList.add(observer);
            return;
        }
        ArrayList<NotificationCenterDelegate> objects = observers.get(id);
        if (objects == null) {
            observers.put(id, (objects = new ArrayList<>()));
        }
        if (objects.contains(observer)) {
            return;
        }
        objects.add(observer);
    }

    public void removeObserver(NotificationCenterDelegate observer, int id) {
        if (BuildVars.DEBUG_VERSION) {
            if (Thread.currentThread() != MoonMeetApplication.applicationHandler.getLooper().getThread()) {
                throw new RuntimeException("removeObserver allowed only from MAIN thread");
            }
        }
        if (broadcasting != 0) {
            ArrayList<NotificationCenterDelegate> arrayList = removeAfterBroadcast.get(id);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                removeAfterBroadcast.put(id, arrayList);
            }
            arrayList.add(observer);
            return;
        }
        ArrayList<NotificationCenterDelegate> objects = observers.get(id);
        if (objects != null) {
            objects.remove(observer);
        }
    }

    public boolean hasObservers(int id) {
        return observers.indexOfKey(id) >= 0;
    }

    public void addPostponeNotificationsCallback(PostponeNotificationCallback callback) {
        if (BuildVars.DEBUG_VERSION) {
            if (Thread.currentThread() != MoonMeetApplication.applicationHandler.getLooper().getThread()) {
                throw new RuntimeException("PostponeNotificationsCallback allowed only from MAIN thread");
            }
        }
        if (!postponeCallbackList.contains(callback)) {
            postponeCallbackList.add(callback);
        }
    }

    public void removePostponeNotificationsCallback(PostponeNotificationCallback callback) {
        if (BuildVars.DEBUG_VERSION) {
            if (Thread.currentThread() != MoonMeetApplication.applicationHandler.getLooper().getThread()) {
                throw new RuntimeException("removePostponeNotificationsCallback allowed only from MAIN thread");
            }
        }
        if (postponeCallbackList.remove(callback)) {
            runDelayedNotifications();
        }
    }

    public interface PostponeNotificationCallback {
        boolean needPostpone(int id, int currentAccount, Object[] args);
    }

    public static class BuildVars{
        public static boolean DEBUG_VERSION = false;
        public static boolean LOGS_ENABLED = false;
    }
}