package com.nanodegree.projects.bakingrecipes.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.nanodegree.projects.bakingrecipes.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.recipe_name_text)
    public TextView recipeName;

    public RecipeViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

}
