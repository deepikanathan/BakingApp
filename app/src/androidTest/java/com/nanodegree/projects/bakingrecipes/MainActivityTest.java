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
    private IdlingResource mIdlingResource;

    @Rule
    public ActivityTestRule<com.nanodegree.projects.bakingrecipes.ui.activities.MainActivity> activityTestRule = new ActivityTestRule<>(com.nanodegree.projects.bakingrecipes.ui.activities.MainActivity.class);

    @Before
    public void registerIdlingResource() {
        globalApplication = (GlobalApplication) activityTestRule.getActivity().getApplicationContext();
        mIdlingResource = globalApplication.getIdlingResource();
        // Register Idling Resources
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

    public static void getMeToRecipeInfo(int recipePosition) {
        onView(withId(R.id.recipes_list_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(recipePosition, click()));
    }

    public static void selectRecipeStep(int recipeStep) {
        onView(withId(R.id.recipe_steps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(recipeStep, click()));
    }
}