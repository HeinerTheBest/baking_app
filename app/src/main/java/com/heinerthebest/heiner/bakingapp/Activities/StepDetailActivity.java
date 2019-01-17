package com.heinerthebest.heiner.bakingapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import com.heinerthebest.heiner.bakingapp.Models.Recipe;
import com.heinerthebest.heiner.bakingapp.R;

import java.util.List;

import static com.heinerthebest.heiner.bakingapp.Activities.MainActivity.isTablet;

public class StepDetailActivity extends AppCompatActivity {
    private static final String TAG = DescriptionActivity.class.getSimpleName();
    private Context context;
    List<Recipe> recipes;
    private AppDataBase mDb;
    FragmentManager fragmentManager;
    Thread thread;

    //Fragments
    NavigationFragment navigationFragment;
    StepDescriptionFragment stepDescriptionFragment;
    VideoFragment videoFragment;
    String title = "";

    FrameLayout navigationContainer;

    int idRecipe, idStep;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);



        //Fragments
        navigationFragment = new NavigationFragment();
        stepDescriptionFragment = new StepDescriptionFragment();
        videoFragment = new VideoFragment();



        context = this;
        mDb = AppDataBase.getsInstance(getApplicationContext());
        fragmentManager = getSupportFragmentManager();
        idRecipe = getIntent().getIntExtra(Intent.EXTRA_INDEX,0);
        idStep   = getIntent().getIntExtra(Intent.EXTRA_INTENT,0);


        getRecipes();
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

    private void getRecipes()
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
                            setTitle(getRecipeById(idRecipe).getName());
                            title = recipes.get(idRecipe).getName();

                                createStepsDescriptionFragment(idRecipe,0);
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


    public void createStepsDescriptionFragment(int recipeId,int stepId)
    {
        Log.d("Follow","receiving Recipe id:"+recipeId+" in CallStepDescription");

        stepDescriptionFragment.setSteps(getRecipeById(recipeId).getSteps(),stepId,recipeId);

        fragmentManager.beginTransaction()
                .add(R.id.body_container,stepDescriptionFragment)
                .commit();

        createVideoFragment(recipeId,stepId);
        callNavigationFragment(recipeId,stepId);
    }

    public void createVideoFragment(int recipeId, int stepId)
    {
        videoFragment.setSteps(getRecipeById(recipeId).getSteps(),stepId,recipeId);

        fragmentManager.beginTransaction()
                .add(R.id.head_container,videoFragment)
                .commit();
    }

    public void callNavigationFragment(int recipeId,int stepId)
    {
        boolean isTablet = isTablet(context);

        navigationFragment.setSteps(isTablet,getRecipeById(recipeId).getSteps(),stepId);


        navigationContainer.setVisibility(View.VISIBLE);
        fragmentManager.beginTransaction()
                .add(R.id.bottom_container,navigationFragment)
                .commit();

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

}
