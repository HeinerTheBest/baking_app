package com.heinerthebest.heiner.bakingapp;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    private static final String PREFS_NAME = "prefs";
    private static final String RECIPE_KEY_TITLE = "ecipe_key_title";
    private static final String INGREDIENTS_KEY = "ingredients_key";


    public static void saveRecipeTitle(Context context, String title)
    {
        SharedPreferences.Editor preferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
        preferences.putString(RECIPE_KEY_TITLE,title);
        preferences.apply();
    }

    public static void saveRecipeIngredients(Context context, String ingredients)
    {
        SharedPreferences.Editor preferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
        preferences.putString(INGREDIENTS_KEY,ingredients);
        preferences.apply();
    }

    public static String loadRecipeTitle(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        String title = preferences.getString(RECIPE_KEY_TITLE,"");
        return title;
    }

    public static String loadIngredients(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        String ingredients = preferences.getString(INGREDIENTS_KEY,"");
        return ingredients;
    }

    public static String[] loadIngredientsList(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        String ingredients = preferences.getString(INGREDIENTS_KEY,"");
        return ingredients.split("\n");
    }


}
