package com.heinerthebest.heiner.bakingapp.Models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.heinerthebest.heiner.bakingapp.DataBase.AppDataBase;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private List<Recipe> recipes;

    public MainViewModel(@NonNull Application application) {
        super(application);
        Log.d("heinerTheBest","constructor");
        AppDataBase appDataBase = AppDataBase.getsInstance(this.getApplication());
        recipes = appDataBase.recipeDao().loadRecipes();
    }

    public List<Recipe> getRecipe() {
        Log.d("heinerTheBest","Obtenemos pelicula");
        return recipes;

    }

    public void setMovies(List<Recipe> recipes) {
        this.recipes = recipes;
        Log.d("heinerTheBest","Seteamos pelicula");

    }
}
