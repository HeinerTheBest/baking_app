package com.heinerthebest.heiner.bakingapp;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    private static final String PREFS_NAME = "prefs";
    private static final String RECIPE_KEY_TITLE = "ecipe_key_title";
    private static final String INGREDIENTS_KEY = "ingredients_key";


    //news
    private static final String CURRENT_POSITION_KEY = "currentpositionkey";
    private static final String PLAY_WHEN_READY_KEY = "playwhenreadykey";
    private static final String STEP_ID_KEY = "stepidkey";




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

    public static void setCurrentVideoPosition(Context context,Long currentProgress)
    {
        SharedPreferences.Editor preference = context.getSharedPreferences(PREFS_NAME,context.MODE_PRIVATE).edit();
        preference.putLong(CURRENT_POSITION_KEY,currentProgress);
        preference.apply();
    }

    public static Long getCurrentVideoPosition(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME,context.MODE_PRIVATE);
        Long currentProgress = preferences.getLong(CURRENT_POSITION_KEY,0);
        return currentProgress;
    }

    public static Boolean isPlaying(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME,context.MODE_PRIVATE);
        Boolean isPlay = preferences.getBoolean(PLAY_WHEN_READY_KEY,true);
        return isPlay;
    }

    public static void setPlaying(Context context, Boolean isPlaying)
    {
        SharedPreferences.Editor preference = context.getSharedPreferences(PREFS_NAME,context.MODE_PRIVATE).edit();
        preference.putBoolean(PLAY_WHEN_READY_KEY,isPlaying);
        preference.apply();
    }

    public static void setStepId(Context context,int stepId)
    {
        SharedPreferences.Editor preference = context.getSharedPreferences(PREFS_NAME,context.MODE_PRIVATE).edit();
        preference.putInt(STEP_ID_KEY,stepId);
        preference.apply();
    }

    public static int getStepId(Context context)
    {
        SharedPreferences preference = context.getSharedPreferences(PREFS_NAME,context.MODE_PRIVATE);
        int stepId = preference.getInt(STEP_ID_KEY,0);
        return stepId;
    }


}
