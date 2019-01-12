package com.heinerthebest.heiner.bakingapp.DataBase;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.heinerthebest.heiner.bakingapp.Models.Recipe;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM Recipe")
    List<Recipe> loadRecipes();

    @Query("SELECT * FROM Recipe Where id =(:idRecipe)")
    Recipe loadRecipe(String idRecipe);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipes(List<Recipe> recipes);
}
