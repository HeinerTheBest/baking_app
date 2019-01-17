package com.heinerthebest.heiner.bakingapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.heinerthebest.heiner.bakingapp.DataBase.AppDataBase;
import com.heinerthebest.heiner.bakingapp.Fragments.RecipeFragment;
import com.heinerthebest.heiner.bakingapp.Interfaces.GetDataService;
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

    private AppDataBase mDb;
    Thread thread;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recipeFragment = new RecipeFragment();
        context = this;
        mDb = AppDataBase.getsInstance(getApplicationContext());
        fragmentManager = getSupportFragmentManager();
        setData();

        if(isTablet(context))
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        else
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }


    }

    private void setData()
    {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if(mDb.recipeDao().loadRecipes().size() == 0)
                {
                    Log.d(TAG,"Getting From Web");
                    getDataFromWeb();
                }
                else
                {
                    Log.d(TAG,"Getting From Local");
                    getDataFromLocal();
                }
            }
        });
        thread.start();
    }

    private void getDataFromWeb()
    {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Recipe>> call = service.getAllRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipes = response.body();
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mDb.recipeDao().insertRecipes(recipes);
                        callRecipeFragment(recipes);
                    }
                });
                thread.start();
                // callRecipeFragment(recipes);
                Log.d(TAG,"We got the recipes");
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(context, "Something went wrong...Please try later! "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG,"error was: "+t.getMessage());
            }
        });
    }

    private void getDataFromLocal()
    {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if(mDb.recipeDao().loadRecipes().size() > 0)
                {
                    Log.d(TAG,"DB is not Empty, Have "+mDb.recipeDao().loadRecipes().size());
                    final List<Recipe> tmp = mDb.recipeDao().loadRecipes();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callRecipeFragment(tmp);
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

    public void callRecipeFragment(List<Recipe> recipes)
    {
        recipeFragment.setRecipes(recipes);
        fragmentManager.beginTransaction()
                .add(R.id.recipe_container,recipeFragment)
                .commit();
    }


    public void callNextActivity(int idRecipe,String name)
    {
        Log.d("Follow","Recipe id:"+idRecipe);
        Intent intent = new Intent(context,DescriptionActivity.class);
        intent.putExtra(Intent.EXTRA_INDEX,idRecipe);
        intent.putExtra(getString(R.string.NAME_RECIPE_KEY),name);
        startActivity(intent);
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
