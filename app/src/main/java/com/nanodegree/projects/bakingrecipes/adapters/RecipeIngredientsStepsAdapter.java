package com.nanodegree.projects.bakingrecipes.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.nanodegree.projects.bakingrecipes.R;
import com.nanodegree.projects.bakingrecipes.models.Ingredients;
import com.nanodegree.projects.bakingrecipes.models.Recipe;
import com.nanodegree.projects.bakingrecipes.ui.Listeners;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeIngredientsStepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Recipe recipe;
    private Listeners.OnItemClickListener onItemClickListener;

    public RecipeIngredientsStepsAdapter(Recipe recipe, Listeners.OnItemClickListener onItemClickListener) {
        this.recipe = recipe;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //  set the Ingredients list
        if (viewType == 0) {
            return new IngredientsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_ingredient_list_item, parent, false));
        }
        //  set the recipe steps
        else {
            return new StepViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_step_list_item, parent, false));
        }
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int pos) {
        //
        if (holder instanceof IngredientsViewHolder) {
            IngredientsViewHolder viewHolder = (IngredientsViewHolder) holder;
            StringBuilder ingValue = new StringBuilder();
            for (int i = 0; i < recipe.getIngredients().size(); i++) {
                Ingredients ingredients = recipe.getIngredients().get(i);
                ingValue.append(String.format(Locale.getDefault(), "• %s (%d %s)", ingredients.getIngredient(), ingredients.getQuantity(), ingredients.getMeasure()));
                if (i != recipe.getIngredients().size() - 1)
                    ingValue.append("\n");
            }
            //  set the Ingredients Title
            viewHolder.ingredientsTitle.setText(ingValue.toString());
        }
        //  set the Recipe Steps
        else if (holder instanceof StepViewHolder) {
            StepViewHolder viewHolder = (StepViewHolder) holder;
            viewHolder.recipeStepNumber.setText(String.valueOf(pos) + ".");
            viewHolder.recipeStepTitle.setText(recipe.getSteps().get(pos).getShortDescription());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClick(pos - 1);
                }
            });
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;
        else
            return 1;
    }

    @Override
    public int getItemCount() {
        return recipe.getSteps().size() + 1;
    }

    /**
     * ViewHolder for Ingredients
     */
    public class IngredientsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredients_items_textview)
        public TextView ingredientsTitle;

        public IngredientsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class StepViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.step_order_text)
        public TextView recipeStepNumber;

        @BindView(R.id.step_name_text)
        public TextView recipeStepTitle;

        public StepViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

    }
}
