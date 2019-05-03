package com.nanodegree.projects.bakingrecipes.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nanodegree.projects.bakingrecipes.R;
import com.nanodegree.projects.bakingrecipes.models.Step;
import com.nanodegree.projects.bakingrecipes.ui.fragments.RecipeStepDetailFragment;

import java.util.List;

/**
 * Recipe Steps adapter
 */
public class StepsFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context ctx;
    private List<Step> recipeSteps;

    public StepsFragmentPagerAdapter(Context context, FragmentManager fragmentManager, List<Step> steps) {
        super(fragmentManager);
        this.ctx = context;
        this.recipeSteps = steps;
    }

    @Override
    public int getCount() {
        return recipeSteps.size();
    }

   @Override
    public Fragment getItem(int position) {
        Bundle arguments = new Bundle();
        //  pass the Recipe Step Number and Step Description to the succeeding Step
        arguments.putParcelable(ctx.getResources().getString(R.string.step), recipeSteps.get(position));
        RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
        {
            return ("Intro");
        }
        else {
            return String.format(ctx.getString(R.string.step_number), position);
        }
    }




}
