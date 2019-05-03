package com.nanodegree.projects.bakingrecipes.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nanodegree.projects.bakingrecipes.R;
import com.nanodegree.projects.bakingrecipes.models.Recipe;
import com.nanodegree.projects.bakingrecipes.listeners.ItemClickListener;
import com.nanodegree.projects.bakingrecipes.ui.activities.RecipeViewHolder;

import java.util.List;

/**
 * Recipe adapter
 */
public class RecipesAdapter extends RecyclerView.Adapter<RecipeViewHolder> {
    private Context ctx;
    private List<Recipe> recipeList;
    private ItemClickListener.OnItemClickListener onItemClickListener;

    public RecipesAdapter(Context context, List<Recipe> recipes, ItemClickListener.OnItemClickListener onItemClickListener) {
        this.ctx = context;
        this.recipeList = recipes;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list, parent, false);
        return new RecipeViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, final int position) {

        holder.recipeName.setText(recipeList.get(position).getName());

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }
}
