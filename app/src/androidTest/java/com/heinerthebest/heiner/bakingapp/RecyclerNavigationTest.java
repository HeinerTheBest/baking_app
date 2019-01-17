package com.heinerthebest.heiner.bakingapp;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import android.support.test.espresso.contrib.RecyclerViewActions;

public class RecyclerNavigationTest
{
    public static void getMeToStepInfo(int recipePosition) {
        onView(withId(R.id.rv_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(recipePosition, click()));
    }

}
