package org.mark.moonmeet.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

public class CustomLifeCycle implements LifecycleOwner {

    private LifecycleRegistry mLifecycleRegistry;

    public CustomLifeCycle() {
        mLifecycleRegistry = new LifecycleRegistry(this);
    }

    public void doOnCreate() {
        mLifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
    }

    public void doOnResume() {
        mLifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);
    }

    public void doOnStart() {
        mLifecycleRegistry.setCurrentState(Lifecycle.State.STARTED);
    }

    public void doOnDestroy() {
        mLifecycleRegistry.setCurrentState(Lifecycle.State.DESTROYED);
    }

    @NonNull
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }
}