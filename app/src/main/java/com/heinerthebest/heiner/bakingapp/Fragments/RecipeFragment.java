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

import com.heinerthebest.heiner.bakingapp.Activities.MainActivity;
import com.heinerthebest.heiner.bakingapp.Adapters.RecipeAdapter;
import com.heinerthebest.heiner.bakingapp.Interfaces.RecipeClickListener;
import com.heinerthebest.heiner.bakingapp.Models.Recipe;
import com.heinerthebest.heiner.bakingapp.R;

import java.util.List;

public class RecipeFragment extends Fragment implements RecipeClickListener
{

    // Tag for logging
    private static final String TAG = RecipeFragment.class.getSimpleName();

    // Variables to store a list of recipes
    private List<Recipe> recipes;
    RecyclerView.LayoutManager layoutManager;
    RecipeClickListener recipeClickListener;
    Boolean isTablet;

    public RecipeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);
        isTablet = ((MainActivity)getActivity()).isTablet(rootView.getContext());

                        RecyclerView recyclerView = rootView.findViewById(R.id.rv_recipes);


        if(isTablet)
            {
                recyclerView.setLayoutManager(new android.support.v7.widget.GridLayoutManager(rootView.getContext(), 3));
            }
            else
             {
                        layoutManager = new LinearLayoutManager(rootView.getContext());
                        recyclerView.setLayoutManager(layoutManager);

             }
        recipeClickListener = this;

        RecipeAdapter adapter = new RecipeAdapter(rootView.getContext(), recipes, recipeClickListener);
        recyclerView.setAdapter(adapter);
        return rootView;
    }


    @Override
    public void onMovieClick(int clickedMovieIndex) {
        ((MainActivity)getActivity()).callNextActivity(clickedMovieIndex,recipes.get(clickedMovieIndex).getName());
        Log.d(TAG,"You click one : "+ recipes.get(clickedMovieIndex).getId());
    }


    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
