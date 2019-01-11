package com.heinerthebest.heiner.bakingapp.Interfaces;

import com.heinerthebest.heiner.bakingapp.Models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService
{

    @GET("baking.json")
    Call<List<Recipe>> getAllRecipes();
}
