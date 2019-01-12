package com.heinerthebest.heiner.bakingapp.DataBase;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heinerthebest.heiner.bakingapp.Models.Ingredient;
import com.heinerthebest.heiner.bakingapp.Models.Step;

import java.lang.reflect.Type;
import java.util.List;

public class StepsConverter {
    @TypeConverter
    public String fromStepsList(List<Step> steps) {
        if (steps == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Step>>() {}.getType();
        String json = gson.toJson(steps, type);
        return json;
    }

    @TypeConverter
    public List<Step> toStepsList(String stepString) {
        if (stepString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Step>>() {}.getType();
        List<Step> stepList = gson.fromJson(stepString, type);
        return stepList;
    }




}
