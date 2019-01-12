package com.heinerthebest.heiner.bakingapp.DataBase;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heinerthebest.heiner.bakingapp.Models.Ingredient;

import java.lang.reflect.Type;
import java.util.List;

public class IngredientsConverter {
    @TypeConverter
    public String fromIngredientsList(List<Ingredient> ingredients) {
        if (ingredients == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Ingredient>>() {}.getType();
        String json = gson.toJson(ingredients, type);
        return json;
    }

    @TypeConverter
    public List<Ingredient> toIngredientList(String ingredientString) {
        if (ingredientString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Ingredient>>() {}.getType();
        List<Ingredient> ingredientList = gson.fromJson(ingredientString, type);
        return ingredientList;
    }
}
