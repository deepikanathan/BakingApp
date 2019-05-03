package com.nanodegree.projects.bakingrecipes.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.nanodegree.projects.bakingrecipes.R;
import com.nanodegree.projects.bakingrecipes.models.Recipe;

public class Prefs {
    public static final String PREFS_NAME = "prefs";

    public static void AddRecipeToPreference(Context context, Recipe recipe) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        prefs.putString(context.getString(R.string.recipe_pref_key), Recipe.toBase64String(recipe));
        prefs.apply();
    }

    public static Recipe GetRecipeFromPreference(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String recipeBase64 = prefs.getString(context.getString(R.string.recipe_pref_key), "");
        return "".equals(recipeBase64) ? null : Recipe.fromBase64(prefs.getString(context.getString(R.string.recipe_pref_key), ""));
    }
}
