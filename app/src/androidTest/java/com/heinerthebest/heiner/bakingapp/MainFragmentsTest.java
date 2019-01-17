package com.heinerthebest.heiner.bakingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.heinerthebest.heiner.bakingapp.Activities.DescriptionActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainFragmentsTest
{

    @Rule
    public ActivityTestRule<DescriptionActivity> activityTestRule = new ActivityTestRule<>(DescriptionActivity.class);


    @Test
    public void clickOnRecyclerViewItem_opensRecipeInfoActivity() {

        RecyclerNavigationTest.getMeToStepInfo(0);

        onView(withId(R.id.tv_steps_description_head))
                .check(matches(isDisplayed()));

        onView(withId(R.id.tv_steps_description))
                .check(matches(isDisplayed()));
    }
}