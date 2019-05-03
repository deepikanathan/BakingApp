package com.nanodegree.projects.bakingrecipes.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.nanodegree.projects.bakingrecipes.R;
import com.nanodegree.projects.bakingrecipes.utils.Prefs;
import com.nanodegree.projects.bakingrecipes.models.Recipe;

/**
 * Adapter for the widget
 */
//  REf - https://www.sitepoint.com/killer-way-to-show-a-list-of-items-in-android-collection-widget/
public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private Recipe recipe;

    public ListRemoteViewsFactory(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        recipe = Prefs.GetRecipeFromPreference(mContext);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return recipe.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row = new RemoteViews(mContext.getPackageName(), R.layout.baking_recipes_app_widget_list_item);

        row.setTextViewText(R.id.ingredient_text, recipe.getIngredients().get(position).getIngredient());

        return row;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
