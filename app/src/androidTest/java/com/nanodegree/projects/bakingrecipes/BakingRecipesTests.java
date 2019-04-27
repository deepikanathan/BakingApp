package com.nanodegree.projects.bakingrecipes;

import android.content.Context;
import android.support.test.espresso.intent.Intents;
import android.support.test.runner.AndroidJUnit4;

import com.nanodegree.projects.bakingrecipes.models.Recipe;
import com.nanodegree.projects.bakingrecipes.ui.activities.MainActivity;
import com.nanodegree.projects.bakingrecipes.ui.activities.RecipeStepDetailActivity;
import com.nanodegree.projects.bakingrecipes.utils.BaseTest;
import com.nanodegree.projects.bakingrecipes.utils.Navigation;

import org.junit.Test;
import org.junit.runner.RunWith;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class BakingRecipesTests extends BaseTest {

    @Test
    public void clickRecyclerViewItemHasIntentWithAKey() {
        //Checks if the key is present
        Intents.init();

        Navigation.getMeToRecipeInfo(0);
        intended(hasExtraWithKey(MainActivity.RECIPE_PARCEL_KEY));

        Intents.release();

    }

    @Test
    public void clickOnRecyclerViewItem_opensRecipeInfoActivity() {

        Navigation.getMeToRecipeInfo(0);

        onView(withId(R.id.ingredients_items_textview))
                .check(matches(isDisplayed()));

        onView(withId(R.id.recipe_steps))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickOnRecyclerViewStepItem_opensRecipeStepActivity_orFragment() {
        Navigation.getMeToRecipeInfo(0);

        boolean twoPaneMode = globalApplication.getResources().getBoolean(R.bool.twoPaneMode);
        if (!twoPaneMode) {
            // Checks if the keys are present and the intent launched is RecipeStepDetailActivity
            Intents.init();
            Navigation.selectRecipeStep(1);
            intended(hasComponent(RecipeStepDetailActivity.class.getName()));
            intended(hasExtraWithKey(RecipeStepDetailActivity.RECIPE_KEY));
            intended(hasExtraWithKey(RecipeStepDetailActivity.STEP_SELECTED_KEY));
            Intents.release();

            // Check TabLayout
            onView(withId(R.id.recipe_step_tab_layout))
                    .check(matches(isCompletelyDisplayed()));
        } else {
            Navigation.selectRecipeStep(1);

            onView(withId(R.id.exo_player_view))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void checkAddWidgetButtonFunctionality() {
        // Clear the preferences values
        globalApplication.getSharedPreferences(Prefs.PREFS_NAME, Context.MODE_PRIVATE).edit()
                .clear()
                .commit();

        Navigation.getMeToRecipeInfo(0);

        onView(withId(R.id.action_add_to_widget))
                .check(matches(isDisplayed()))
                .perform(click());

        // Get the recipe base64 string from the sharedPrefs
        Recipe recipe = Prefs.loadRecipe(globalApplication);

        // Assert recipe is not null
        assertNotNull(recipe);
    }

}
