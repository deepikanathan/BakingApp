package com.nanodegree.projects.bakingrecipes.api;

/**
 * Interface to handle Retrofit Callback methods
 * @param <T> Recipe List
 */
public interface RecipesApiCallback<T> {
    void onResponse(T result);

    void onCancel();
}
