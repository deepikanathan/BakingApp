package com.nanodegree.projects.bakingrecipes;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import com.nanodegree.projects.bakingrecipes.utils.GlobalApplication;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public abstract class MainActivityTest {
    protected GlobalApplication globalApplication;
    private IdlingResource idlingResource;

    @Rule
    public ActivityTestRule<com.nanodegree.projects.bakingrecipes.ui.activities.MainActivity> activityTestRule = new ActivityTestRule<>(com.nanodegree.projects.bakingrecipes.ui.activities.MainActivity.class);

    @Before
    public void registerIdlingResource() {
        globalApplication = (GlobalApplication) activityTestRule.getActivity().getApplicationContext();
        idlingResource = globalApplication.getIdlingResource();
        // Register Idling Resources
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @After
    public void unregisterIdlingResource() {
        if (idlingResource != null) {
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }

    public static void checkRecipeIsLoaded(int recipePosition) {
        onView(withId(R.id.recipes_list_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(recipePosition, click()));
    }

    public static void selectRecipe(int recipeStep) {
        onView(withId(R.id.recipe_steps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(recipeStep, click()));
    }
}
