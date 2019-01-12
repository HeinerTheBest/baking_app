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

    public RecipeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.rv_recipes);
        layoutManager = new LinearLayoutManager(rootView.getContext());
        recipeClickListener = this;

        RecipeAdapter adapter = new RecipeAdapter(rootView.getContext(), recipes, recipeClickListener);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return rootView;
    }


    @Override
    public void onMovieClick(int clickedMovieIndex) {
//        Intent intent = new Intent(context,RecipeDetailActivity.class);
//        final int clickedRecipeId = recipes.get(clickedMovieIndex).getId();
//        intent.putExtra(Intent.EXTRA_INDEX,clickedRecipeId);
//        startActivity(intent);
        ((MainActivity)getActivity()).callNextActivity(clickedMovieIndex);
        Log.d(TAG,"You click one : "+ recipes.get(clickedMovieIndex).getId());
    }


    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
