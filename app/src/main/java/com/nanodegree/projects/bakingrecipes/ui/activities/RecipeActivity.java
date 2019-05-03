package com.nanodegree.projects.bakingrecipes.ui.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.nanodegree.projects.bakingrecipes.R;
import com.nanodegree.projects.bakingrecipes.adapters.RecipeIngredientsStepsAdapter;
import com.nanodegree.projects.bakingrecipes.models.Recipe;
import com.nanodegree.projects.bakingrecipes.ui.fragments.RecipeStepDetailFragment;
import com.nanodegree.projects.bakingrecipes.utils.RecyclerSpacingItemDecoration;
import com.nanodegree.projects.bakingrecipes.widget.RecipeWidgetService;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Recipe Ingredients and Steps activity
 */
public class RecipeActivity extends AppCompatActivity {

    private boolean isTabletLayout;
    private Recipe recipe;
    @BindView(R.id.recipe_steps)
    RecyclerView recyclerView;
    @BindView(android.R.id.content)
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  get saved Recipe from Bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(getResources().getString(R.string.recipe_parcelable_key))) {
            recipe = bundle.getParcelable(getResources().getString(R.string.recipe_parcelable_key));
        } else {
            Toast.makeText(this, getString(R.string.unable_to_load_data), Toast.LENGTH_SHORT).show();
            finish();
        }

        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

        //  add toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setActionBarTitle();

        isTabletLayout = getResources().getBoolean(R.bool.isTabletLayout);
        if (isTabletLayout) {
            if (savedInstanceState == null &&
                    recipe!=null &&
                    !recipe.getSteps().isEmpty()) {
                showRecipeStep(0);
            }
        }
        setRecyclerView();
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

    private void showRecipeStep(int position) {
        if (isTabletLayout) {
            showRecipeStepForTablet(position);
        } else {
            showRecipeStepForPhone(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //  add recipe to widget
        if (item.getItemId() == R.id.action_add_to_widget) {
            RecipeWidgetService.updateWidget(this, recipe);
            Toast.makeText(this, String.format(getString(R.string.added_to_widget_toast), recipe.getName()), Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void setActionBarTitle() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(recipe.getName());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void showRecipeStepForPhone(int position){
        Intent intent = new Intent(this, RecipeStepDetailActivity.class);
        intent.putExtra(getString(R.string.recipe_step_key), recipe);
        intent.putExtra(getString(R.string.recipe_step_number_key), position);
        startActivity(intent);
    }

    private void showRecipeStepForTablet(int position)
    {
        Bundle arguments = new Bundle();
        arguments.putParcelable(getResources().getString(R.string.step), recipe.getSteps().get(position));
        RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_step_detail_container, fragment)
                .commit();
    }
    private void setRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerSpacingItemDecoration recylerViewDecoration = new RecyclerSpacingItemDecoration((int) getResources().getDimension(R.dimen.medium_padding));
        recyclerView.addItemDecoration(recylerViewDecoration);
        recyclerView.setAdapter(new RecipeIngredientsStepsAdapter(recipe, position -> showRecipeStep(position)));
    }
}
