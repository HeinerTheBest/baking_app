package com.heinerthebest.heiner.bakingapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentContainer;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.heinerthebest.heiner.bakingapp.AppWidgetService;
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
    String title = "";

    public static final String NAME_TITLE_KEY = "nametitlekey";
    FrameLayout navigationContainer;
    String nameRecipe = "";







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


        if(savedInstanceState != null) {
            if(savedInstanceState.containsKey(NAME_TITLE_KEY)) {
                Log.d(TAG,"Right now I'm in instance");

                nameRecipe = savedInstanceState.getString(NAME_TITLE_KEY);
            }
        }
        else
        {
            Log.d(TAG,"Right now I'm get it from intent");

            nameRecipe = getIntent().getStringExtra(getString(R.string.NAME_RECIPE_KEY));

        }



        Log.d(TAG,"Right now the name is = "+nameRecipe);
        getRecipes(idRecipe,nameRecipe);
        navigationContainer = findViewById(R.id.bottom_container);

        if(isTablet(context))
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        else
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }


    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    private void getRecipes(final int idRecipe, final String name)
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
                            setTitle(getRecipeById(name).getName());
                            title = recipes.get(idRecipe).getName();
                            Log.d(TAG,"you do great "+recipes.get(idRecipe).getIngredients().get(0).getIngredient());
                            Log.d(TAG,"you do great "+recipes.get(idRecipe).getSteps().get(0).getShortDescription());

                            callIngredientsFragment(name);
                            callStepsFragment(name);



                            //Create Info for tablet
                            if(isTablet(context))
                            {
                                createStepsDescriptionFragment(idRecipe,0,name);
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


    public Recipe getRecipeById(String name)
    {
        for(Recipe recipe:recipes)
        {
            if(recipe.getName().equals(name))
                return recipe;
        }

        return recipes.get(0);
    }

    public void callStepsFragment(String name)
    {

        stepFragment.setRecipes(getRecipeById(name));


        fragmentManager.beginTransaction()
                .add(R.id.body_container, stepFragment)
                .commit();
    }


    public void createStepsDescriptionFragment(int recipeId,int stepId,String nameRecipe)
    {
        Log.d("Follow","receiving Recipe id:"+recipeId+" in CallStepDescription");

        stepDescriptionFragment.setSteps(getRecipeById(nameRecipe).getSteps(),stepId,recipeId);

        fragmentManager.beginTransaction()
                .add(R.id.step_description_container,stepDescriptionFragment)
                .commit();
        createVideoFragment(recipeId,stepId,nameRecipe);
        callNavigationFragment(nameRecipe,stepId);
    }


    public void createVideoFragment(int recipeId, int stepId, String name)
    {
        videoFragment.setSteps(getRecipeById(name).getSteps(),stepId,recipeId);

        fragmentManager.beginTransaction()
                .add(R.id.video_container,videoFragment)
                .commit();
    }

    public void callIngredientsFragment(String nameRecipe)
    {
        String ingredients = "";
        int tmp =1;
        for (Ingredient ingr : getRecipeById(nameRecipe).getIngredients())
        {

            ingredients = ingredients+setIngredientText(ingr,tmp);
            tmp++;
        }
        ingredientsFragment.setIngredients(ingredients,title);
        AppWidgetService.updateWidget(this, getRecipeById(nameRecipe).getName(),ingredients);

        fragmentManager.beginTransaction()
                .add(R.id.head_container, ingredientsFragment)
                .commit();
    }

    public void callNavigationFragment( String nameRecipe,int stepId)
    {

        boolean isTablet = isTablet(context);
        navigationFragment.setSteps(isTablet,getRecipeById(nameRecipe).getSteps(),stepId);


        navigationContainer.setVisibility(View.VISIBLE);
        fragmentManager.beginTransaction()
                .add(R.id.bottom_container,navigationFragment)
                .commit();

    }


    public String setIngredientText(Ingredient ingredient,int index)
    {
        return index+") "+ingredient.getQuantity()+" "+ingredient.getMeasure()+" of "+ingredient.getIngredient()+"\n";
    }

    public void callLastActivity(int idRecipe,int idStep)
    {
        Log.d("Follow","Recipe id:"+idRecipe);
        Intent intent = new Intent(context,StepDetailActivity.class);
        intent.putExtra(Intent.EXTRA_INDEX,idRecipe);
        intent.putExtra(Intent.EXTRA_INTENT,idStep);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString(NAME_TITLE_KEY,nameRecipe);

    }
}
