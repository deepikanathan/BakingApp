package com.nanodegree.projects.bakingrecipes;

import android.content.Context;
import android.support.test.espresso.intent.Intents;
import android.support.test.runner.AndroidJUnit4;
import com.nanodegree.projects.bakingrecipes.models.Recipe;
import com.nanodegree.projects.bakingrecipes.ui.activities.RecipeStepDetailActivity;
import com.nanodegree.projects.bakingrecipes.utils.BaseTest;
import com.nanodegree.projects.bakingrecipes.utils.Prefs;
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

    private String RECIPE_PARCL_KEY="RECIPE_PARCL_KEY";
    private String RECIPE_STEP_KEY="RECIPE_STEP_KEY";
    private String RECIPE_STEP_NUMBER_KEY="RECIPE_STEP_NUMBER_KEY";
    @Test
    public void clickRecyclerViewItemHasIntentWithAKey() {
        //Checks if the key is present
        Intents.init();

        BaseTest.getMeToRecipeInfo(0);
        intended(hasExtraWithKey(RECIPE_PARCL_KEY));

        Intents.release();

    }

    @Test
    public void clickOnRecyclerViewItem_opensRecipeInfoActivity() {

        BaseTest.getMeToRecipeInfo(0);

        onView(withId(R.id.ingredients_items_textview))
                .check(matches(isDisplayed()));

        onView(withId(R.id.recipe_steps))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickOnRecyclerViewStepItem_opensRecipeStepActivity_orFragment() {
        BaseTest.getMeToRecipeInfo(0);

        boolean twoPaneMode = globalApplication.getResources().getBoolean(R.bool.isTabletLayout);
        if (!twoPaneMode) {
            // Checks if the keys are present and the intent launched is RecipeStepDetailActivity
            Intents.init();
            BaseTest.selectRecipeStep(1);
            intended(hasComponent(RecipeStepDetailActivity.class.getName()));
            intended(hasExtraWithKey(RECIPE_STEP_KEY));
            intended(hasExtraWithKey(RECIPE_STEP_NUMBER_KEY));
            Intents.release();

            // Check TabLayout
            onView(withId(R.id.recipe_steps_tab))
                    .check(matches(isCompletelyDisplayed()));
        } else {
            BaseTest.selectRecipeStep(1);

            onView(withId(R.id.exo_player))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void checkAddWidgetButtonFunctionality() {
        // Clear the preferences values
        globalApplication.getSharedPreferences(Prefs.PREFS_NAME, Context.MODE_PRIVATE).edit()
                .clear()
                .commit();

        BaseTest.getMeToRecipeInfo(0);

        onView(withId(R.id.action_add_to_widget))
                .check(matches(isDisplayed()))
                .perform(click());

        // Get the recipe base64 string from the sharedPrefs
        Recipe recipe = Prefs.GetRecipeFromPreference(globalApplication);

        // Assert recipe is not null
        assertNotNull(recipe);
    }

}
