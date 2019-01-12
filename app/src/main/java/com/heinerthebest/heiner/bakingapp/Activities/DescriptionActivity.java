package com.heinerthebest.heiner.bakingapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.heinerthebest.heiner.bakingapp.DataBase.AppDataBase;
import com.heinerthebest.heiner.bakingapp.Fragments.IngredientsFragment;
import com.heinerthebest.heiner.bakingapp.Fragments.StepFragment;
import com.heinerthebest.heiner.bakingapp.Models.Ingredient;
import com.heinerthebest.heiner.bakingapp.Models.Recipe;
import com.heinerthebest.heiner.bakingapp.R;

import java.util.List;

public class DescriptionActivity extends AppCompatActivity {
    private static final String TAG = DescriptionActivity.class.getSimpleName();
    private Context context;
    List<Recipe> recipes;
    StepFragment stepFragment;
    IngredientsFragment ingredientsFragment;
    private AppDataBase mDb;
    FragmentManager fragmentManager;
    Thread thread;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        context = this;
        stepFragment = new StepFragment();
        ingredientsFragment = new IngredientsFragment();
        mDb = AppDataBase.getsInstance(getApplicationContext());
        fragmentManager = getSupportFragmentManager();
        final int idRecipe = getIntent().getIntExtra(Intent.EXTRA_INDEX,0);
        getRecipes(idRecipe);
    }

    private void getRecipes(final int idRecipe)
    {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if(mDb.recipeDao().loadRecipes().size() > 0)
                {
                    Log.d(TAG,"DB is not Empty, Have "+mDb.recipeDao().loadRecipes().size());
                    recipes = mDb.recipeDao().loadRecipes();
                    Log.d(TAG,"You click the recipe: "+recipes.get(idRecipe).getName());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setTitle(recipes.get(idRecipe).getName());
                            Log.d(TAG,"you do great "+recipes.get(idRecipe).getIngredients().get(0).getIngredient());
                            Log.d(TAG,"you do great "+recipes.get(idRecipe).getSteps().get(0).getShortDescription());

                            callIngredientsFragment(idRecipe);
                            callStepsFragment(idRecipe);
                        }
                    });
                }
                else
                {
                    Log.d(TAG,"DB is empy");
                }
            }
        });
        thread.start();
    }


    public void callStepsFragment(int recipeId)
    {
        stepFragment.setRecipes(recipes.get(recipeId));


        fragmentManager.beginTransaction()
                .add(R.id.steps_container, stepFragment)
                .commit();
    }

    public void callIngredientsFragment(int recipeId)
    {
        String ingredients = "";
        int tmp =1;
        for (Ingredient ingr : recipes.get(recipeId).getIngredients())
        {

            ingredients = ingredients+setIngredientText(ingr,tmp);
            tmp++;
        }
        ingredientsFragment.setIngredients(ingredients);
        fragmentManager.beginTransaction()
                .add(R.id.ingredients_container, ingredientsFragment)
                .commit();
    }

    public String setIngredientText(Ingredient ingredient,int index)
    {
        return index+") "+ingredient.getQuantity()+" "+ingredient.getMeasure()+" of "+ingredient.getIngredient()+"\n";
    }


}
