package com.nanodegree.projects.bakingrecipes.iIdlingRecource;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Recipe idling resource which waits while Fragment has not been loaded.
 * Keeps the basic functionality of IdlingResource
 * Ref - https://riptutorial.com/android/example/12329/idlingresource
 */
public class RecipesIdlingResource implements IdlingResource {

    @Nullable
    private volatile ResourceCallback resourceCallback;
    private AtomicBoolean isIdleNow = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }

    public void setIdleState(boolean idleState) {
        isIdleNow.set(idleState);
        if (idleState && resourceCallback != null) resourceCallback.onTransitionToIdle();
    }
}