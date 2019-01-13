package com.heinerthebest.heiner.bakingapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentContainer;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.heinerthebest.heiner.bakingapp.DataBase.AppDataBase;
import com.heinerthebest.heiner.bakingapp.Fragments.IngredientsFragment;
import com.heinerthebest.heiner.bakingapp.Fragments.NavigationFragment;
import com.heinerthebest.heiner.bakingapp.Fragments.StepDescriptionFragment;
import com.heinerthebest.heiner.bakingapp.Fragments.StepFragment;
import com.heinerthebest.heiner.bakingapp.Fragments.VideoFragment;
import com.heinerthebest.heiner.bakingapp.Models.Ingredient;
import com.heinerthebest.heiner.bakingapp.Models.Recipe;
import com.heinerthebest.heiner.bakingapp.Models.Step;
import com.heinerthebest.heiner.bakingapp.R;

import java.util.List;

public class DescriptionActivity extends AppCompatActivity {
    private static final String TAG = DescriptionActivity.class.getSimpleName();
    private Context context;
    List<Recipe> recipes;
    private AppDataBase mDb;
    FragmentManager fragmentManager;
    Thread thread;

    //Fragments
    StepFragment stepFragment;
    IngredientsFragment ingredientsFragment;
    NavigationFragment navigationFragment;
    StepDescriptionFragment stepDescriptionFragment;
    VideoFragment videoFragment;

    FrameLayout navigationContainer;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        //Fragments
        stepFragment = new StepFragment();
        ingredientsFragment = new IngredientsFragment();
        navigationFragment = new NavigationFragment();
        stepDescriptionFragment = new StepDescriptionFragment();
        videoFragment = new VideoFragment();



        context = this;
        mDb = AppDataBase.getsInstance(getApplicationContext());
        fragmentManager = getSupportFragmentManager();
        final int idRecipe = getIntent().getIntExtra(Intent.EXTRA_INDEX,0);
        Log.d("Follow","I got Recipe id:"+idRecipe);

        getRecipes(idRecipe);
        navigationContainer = findViewById(R.id.bottom_container);



    }


    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
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

                            //Create Info for tablet
                            if(isTablet(context))
                            {
                                createStepsDescriptionFragment(idRecipe,0);
                            }
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


    public Recipe getRecipeById(int id)
    {
        for(Recipe recipe:recipes)
        {
            if(recipe.getId() == id)
                return recipe;
        }

        return recipes.get(id);
    }

    public void callStepsFragment(int recipeId)
    {
        Log.d("Follow","Recipe id:"+recipeId+" in StepsFragment Method");

        stepFragment.setRecipes(getRecipeById(recipeId));


        fragmentManager.beginTransaction()
                .add(R.id.body_container, stepFragment)
                .commit();
    }

    public void callStepsDescriptionFragment(int recipeId,int stepId)
    {
        Log.d("Follow","receiving Recipe id:"+recipeId+" in CallStepDescription");

        stepDescriptionFragment.setSteps(getRecipeById(recipeId).getSteps(),stepId);

        if(isTablet(context))
        {
            fragmentManager.beginTransaction()
                    .replace(R.id.step_description_container,stepDescriptionFragment)
                    .commit();
        }
        else
        {
            fragmentManager.beginTransaction()
                    .replace(R.id.body_container,stepDescriptionFragment)
                    .commit();
            callVideoFragment(recipeId,stepId);
            callNavigationFragment(recipeId,stepId);

        }

    }

    public void createStepsDescriptionFragment(int recipeId,int stepId)
    {
        Log.d("Follow","receiving Recipe id:"+recipeId+" in CallStepDescription");

        stepDescriptionFragment.setSteps(getRecipeById(recipeId).getSteps(),stepId);

        fragmentManager.beginTransaction()
                .add(R.id.step_description_container,stepDescriptionFragment)
                .commit();
        createVideoFragment(recipeId,stepId);
        callNavigationFragment(recipeId,stepId);
    }

    public void callVideoFragment(int recipeId, int stepId)
    {
        videoFragment.setSteps(getRecipeById(recipeId).getSteps(),stepId);

        if(isTablet(context))
        {
            fragmentManager.beginTransaction()
                    .replace(R.id.video_container,videoFragment)
                    .commit();
        }
        else {
            fragmentManager.beginTransaction()
                    .replace(R.id.head_container, videoFragment)
                    .commit();
        }
    }

    public void createVideoFragment(int recipeId, int stepId)
    {
        videoFragment.setSteps(getRecipeById(recipeId).getSteps(),stepId);

        fragmentManager.beginTransaction()
                .add(R.id.video_container,videoFragment)
                .commit();
    }

    public void callIngredientsFragment(int recipeId)
    {
        String ingredients = "";
        int tmp =1;
        for (Ingredient ingr : getRecipeById(recipeId).getIngredients())
        {

            ingredients = ingredients+setIngredientText(ingr,tmp);
            tmp++;
        }
        ingredientsFragment.setIngredients(ingredients);
        fragmentManager.beginTransaction()
                .add(R.id.head_container, ingredientsFragment)
                .commit();
    }

    public void callNavigationFragment(int recipeId,int stepId)
    {
        navigationFragment.setSteps(getRecipeById(recipeId).getSteps(),stepId);


        navigationContainer.setVisibility(View.VISIBLE);
        fragmentManager.beginTransaction()
                .add(R.id.bottom_container,navigationFragment)
                .commit();

    }


    public String setIngredientText(Ingredient ingredient,int index)
    {
        return index+") "+ingredient.getQuantity()+" "+ingredient.getMeasure()+" of "+ingredient.getIngredient()+"\n";
    }


}
