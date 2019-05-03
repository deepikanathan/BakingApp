package com.nanodegree.projects.bakingrecipes.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViewsService;

import com.nanodegree.projects.bakingrecipes.utils.Prefs;
import com.nanodegree.projects.bakingrecipes.models.Recipe;

public class RecipeWidgetService extends RemoteViewsService {

    public static void updateWidget(Context context, Recipe recipe) {
        Prefs.AddRecipeToPreference(context, recipe);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, RecipeWidgetProvider.class));
        RecipeWidgetProvider.updateRecipeWidgets(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        return new ListRemoteViewsFactory(getApplicationContext());
    }

}