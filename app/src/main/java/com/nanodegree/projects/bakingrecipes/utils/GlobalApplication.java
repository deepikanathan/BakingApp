package com.nanodegree.projects.bakingrecipes.utils;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;

import com.nanodegree.projects.bakingrecipes.BuildConfig;
import com.nanodegree.projects.bakingrecipes.iIdlingRecource.RecipesIdlingResource;

/**
 * Application to get App Context that is used in utility classes where context is not available.
 * Reference - https://www.dev2qa.com/android-get-application-context-from-anywhere-example/
 */
public class GlobalApplication extends Application {

    @Nullable
    private RecipesIdlingResource recipesIdlingResource;

    @VisibleForTesting
    @NonNull
    private IdlingResource initializeIdlingResource() {
        if (recipesIdlingResource == null) {
            recipesIdlingResource = new RecipesIdlingResource();
        }
        return recipesIdlingResource;
    }

    public GlobalApplication() {

        if (BuildConfig.DEBUG) {
            initializeIdlingResource();
        }
    }

    public void setIdleState(boolean state) {
        if (recipesIdlingResource != null)
            recipesIdlingResource.setIdleState(state);
    }

    @Nullable
    public RecipesIdlingResource getIdlingResource() {
        return recipesIdlingResource;
    }
}
