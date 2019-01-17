package com.heinerthebest.heiner.bakingapp;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.heinerthebest.heiner.bakingapp.Activities.DescriptionActivity;
import com.heinerthebest.heiner.bakingapp.Fragments.IngredientsFragment;
import com.heinerthebest.heiner.bakingapp.Fragments.StepFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class StepTest {

    @Rule
    public ActivityTestRule<DescriptionActivity> activityActivityTestRule = new ActivityTestRule<DescriptionActivity>(DescriptionActivity.class);


    @Before
    public void init(){
        activityActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }


    @Test
    public void checkShowHeadContainerWithIngredients() {
        IngredientsFragment fragment = new IngredientsFragment();
        activityActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction().add(R.id.head_container, fragment).commit();
    }

    @Test
    public void checkShowBodyContainerWithSteps() {
        StepFragment fragment = new StepFragment();
        activityActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction().add(R.id.body_container, fragment).commit();
    }

}
