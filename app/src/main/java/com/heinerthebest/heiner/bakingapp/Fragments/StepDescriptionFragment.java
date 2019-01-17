package com.heinerthebest.heiner.bakingapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heinerthebest.heiner.bakingapp.DataBase.AppDataBase;
import com.heinerthebest.heiner.bakingapp.Models.Step;
import com.heinerthebest.heiner.bakingapp.R;

import java.util.List;

public class StepDescriptionFragment extends android.support.v4.app.Fragment
{
    private String TAG = StepDescriptionFragment.class.getSimpleName();
    private List<Step> steps;
    private int index;
    TextView tvStepDescription;
    int recipeId = -1;
    TextView title;


    private static final String RECIPE_ARRAY_id_KEY = "recipearraykey";
    private static final String STEP_ARRAY_id_KEY = "steparraykey";



    public StepDescriptionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_step_description, container, false);
        tvStepDescription = rootView.findViewById(R.id.tv_steps_description);
        title = rootView.findViewById(R.id.tv_steps_description_head);


        if(savedInstanceState != null) {
            if(savedInstanceState.containsKey(RECIPE_ARRAY_id_KEY) && savedInstanceState.containsKey(STEP_ARRAY_id_KEY)) {
                index = savedInstanceState.getInt(STEP_ARRAY_id_KEY);
                recipeId = savedInstanceState.getInt(RECIPE_ARRAY_id_KEY);
                Log.d(TAG, "My recipe id saved is:" + savedInstanceState.getInt(RECIPE_ARRAY_id_KEY) + " and my local recipe id is:" + recipeId);
                setDescription(index);
            }
        }
        setDescription(index);


        return rootView;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(RECIPE_ARRAY_id_KEY,recipeId);
        outState.putInt(STEP_ARRAY_id_KEY,index);
    }

    public void setDescription(int i) {
        Log.d(TAG,"I'm with live");
        if(steps != null) {
            tvStepDescription.setText(steps.get(i).getDescription());
            title.setText(steps.get(i).getShortDescription());
        }
    }


    public void setSteps(List<Step> steps,int position, int recipeId) {
        this.steps = steps;
        index = position;
        this.recipeId = recipeId;
    }

}
