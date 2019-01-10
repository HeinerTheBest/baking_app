package com.heinerthebest.heiner.bakingapp.Fragments;

import android.content.Context;
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
import android.widget.Toast;

import com.heinerthebest.heiner.bakingapp.Adapters.RecipeAdapter;
import com.heinerthebest.heiner.bakingapp.Interface.GetDataService;
import com.heinerthebest.heiner.bakingapp.Interfaces.RecipeClickListener;
import com.heinerthebest.heiner.bakingapp.Models.Recipe;
import com.heinerthebest.heiner.bakingapp.R;
import com.heinerthebest.heiner.bakingapp.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeFragment extends Fragment implements RecipeClickListener
{

    // Tag for logging
    private static final String TAG = RecipeFragment.class.getSimpleName();

    // Variables to store a list of recipes
    private List<Recipe> recipes;
    private RecipeAdapter adapter;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecipeClickListener recipeClickListener;

    public RecipeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);

        recyclerView = rootView.findViewById(R.id.rv_recipes);
        layoutManager = new LinearLayoutManager(rootView.getContext());
        recipeClickListener = this;




        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Recipe>> call = service.getAllRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipes = response.body();

                adapter = new RecipeAdapter(rootView.getContext(),recipes,recipeClickListener);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(rootView.getContext(), "Something went wrong...Please try later! "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG,"error was: "+t.getMessage());
            }
        });



        // Return the rootView
        return rootView;
    }


    @Override
    public void onMovieClick(int clickedMovieIndex) {
//        Intent intent = new Intent(context,RecipeDetailActivity.class);
//        final int clickedRecipeId = recipes.get(clickedMovieIndex).getId();
//        intent.putExtra(Intent.EXTRA_INDEX,clickedRecipeId);
//        startActivity(intent);

        Log.d(TAG,"You click one : "+ recipes.get(clickedMovieIndex).getId());
    }


    public List<Recipe> getRecipes() {
        return recipes;
    }
}
