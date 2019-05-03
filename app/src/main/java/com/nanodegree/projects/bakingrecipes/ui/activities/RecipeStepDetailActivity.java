package com.nanodegree.projects.bakingrecipes.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.nanodegree.projects.bakingrecipes.R;
import com.nanodegree.projects.bakingrecipes.adapters.StepsFragmentPagerAdapter;
import com.nanodegree.projects.bakingrecipes.models.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity to scroll through Recipe steps
 */
public class RecipeStepDetailActivity extends AppCompatActivity {

    private Recipe recipe;
    private int stepNumber;

    @BindView(android.R.id.content)
    View view;

    @BindView(R.id.recipe_steps_tab)
    TabLayout tabLayout;

    @BindView(R.id.recipe_step_viewpager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);

        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        //  get the recipe info from the bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(getString(R.string.recipe_step_key)) && bundle.containsKey(getString(R.string.recipe_step_number_key))) {
            recipe = bundle.getParcelable(getString(R.string.recipe_step_key));
            stepNumber = bundle.getInt(getString(R.string.recipe_step_number_key));
        } else {
            Toast.makeText(this, getString(R.string.unable_to_load_data), Toast.LENGTH_SHORT).show();
            finish();
        }

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(recipe.getName());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //  setup the Fragment Pager
        StepsFragmentPagerAdapter adapter = new StepsFragmentPagerAdapter(getApplicationContext(), getSupportFragmentManager(), recipe.getSteps());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageScrollStateChanged(int state) {}
            @Override
            public void onPageSelected(int position) {
                if (actionBar != null) {
                    actionBar.setTitle(recipe.getSteps().get(position).getShortDescription());
                }
            }
        });
        viewPager.setCurrentItem(stepNumber);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
