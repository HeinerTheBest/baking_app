package com.heinerthebest.heiner.bakingapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heinerthebest.heiner.bakingapp.Activities.DescriptionActivity;
import com.heinerthebest.heiner.bakingapp.Activities.MainActivity;
import com.heinerthebest.heiner.bakingapp.Adapters.StepAdapter;
import com.heinerthebest.heiner.bakingapp.Interfaces.RecipeClickListener;
import com.heinerthebest.heiner.bakingapp.Models.Recipe;
import com.heinerthebest.heiner.bakingapp.Models.Step;
import com.heinerthebest.heiner.bakingapp.R;

import java.util.List;

public class StepFragment extends Fragment implements RecipeClickListener
{

    // Tag for logging
    private static final String TAG = StepFragment.class.getSimpleName();

    // Variables to store a list of recipes
    private Recipe recipe;
    private List<Step> steps;
    private StepAdapter adapter;
    private RecyclerView recyclerView;
    private final String RECIPE_ID = "recipe_id";
    RecyclerView.LayoutManager layoutManager;
    RecipeClickListener recipeClickListener;

    public StepFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);

        recyclerView = rootView.findViewById(R.id.rv_recipes);
        layoutManager = new LinearLayoutManager(rootView.getContext());
        recipeClickListener = this;

        adapter = new StepAdapter(steps,rootView.getContext(),recipeClickListener);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return rootView;
    }


    @Override
    public void onMovieClick(int clickedMovieIndex) {
        Log.d("Follow","Recipe id:"+recipe.getId()+" in "+StepFragment.class.getSimpleName());

        ((DescriptionActivity)getActivity()).callStepsDescriptionFragment(recipe.getId(),steps.get(clickedMovieIndex).getId());
        Log.d(TAG,"You click Step: "+ steps.get(clickedMovieIndex).getId()+" of "+steps.size()+" of Recipe: "+recipe.getId());
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(RECIPE_ID,0);
    }

    public void setRecipes(Recipe recipe) {
        Log.d("Follow","Receive in the Recipe id:"+recipe.getId()+" in StepFragment Fragment");

        this.recipe = recipe;
        steps = recipe.getSteps();
    }

}
