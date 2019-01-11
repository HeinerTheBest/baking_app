package com.heinerthebest.heiner.bakingapp.Activities;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.heinerthebest.heiner.bakingapp.Fragments.IngredientsFragment;
import com.heinerthebest.heiner.bakingapp.Fragments.RecipeFragment;
import com.heinerthebest.heiner.bakingapp.Fragments.StepFragment;
import com.heinerthebest.heiner.bakingapp.Interfaces.GetDataService;
import com.heinerthebest.heiner.bakingapp.Models.Ingredient;
import com.heinerthebest.heiner.bakingapp.Models.Recipe;
import com.heinerthebest.heiner.bakingapp.R;
import com.heinerthebest.heiner.bakingapp.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Context context;
    List<Recipe> recipes;
    FragmentManager fragmentManager;
    RecipeFragment recipeFragment;
    StepFragment stepFragment;
    IngredientsFragment ingredientsFragment;
    FrameLayout head;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeFragment = new RecipeFragment();
        stepFragment = new StepFragment();
        ingredientsFragment = new IngredientsFragment();
        head = findViewById(R.id.recipe_container);

        context = this;
            fragmentManager = getSupportFragmentManager();


            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<List<Recipe>> call = service.getAllRecipes();
            call.enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    recipes = response.body();
                    callRecipeFragment(recipes);
                    Log.d(TAG,"We got the recipes");
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    Toast.makeText(context, "Something went wrong...Please try later! "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"error was: "+t.getMessage());
                }
            });
    }


    public void callRecipeFragment(List<Recipe> recipes)
    {
        recipeFragment.setRecipes(recipes);
        fragmentManager.beginTransaction()
                .add(R.id.recipe_container,recipeFragment)
                .commit();
    }

    public void callStepsFragment(int recipeId)
    {
        stepFragment.setRecipes(recipes.get(recipeId));


        fragmentManager.beginTransaction()
                .add(R.id.steps_container, stepFragment)
                .commit();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        head.setVisibility(View.VISIBLE);
    }

    public void callIngredientsFragment(int recipeId)
    {
        setTitle(recipes.get(recipeId).getName());
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
        head.setVisibility(View.GONE);


        callStepsFragment(recipeId);
    }

    public String setIngredientText(Ingredient ingredient,int index)
    {
        return index+") "+ingredient.getQuantity()+" "+ingredient.getMeasure()+" of "+ingredient.getIngredient()+"\n";
    }

}
