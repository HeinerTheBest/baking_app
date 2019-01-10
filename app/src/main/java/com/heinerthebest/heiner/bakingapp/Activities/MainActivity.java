package com.heinerthebest.heiner.bakingapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.heinerthebest.heiner.bakingapp.Adapters.RecipeAdapter;
import com.heinerthebest.heiner.bakingapp.Fragments.RecipeFragment;
import com.heinerthebest.heiner.bakingapp.Interface.GetDataService;
import com.heinerthebest.heiner.bakingapp.Interfaces.RecipeClickListener;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        final RecipeFragment recipeFragment = new RecipeFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.recipe_container, recipeFragment)
                .commit();




        //Demo
        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipes = recipeFragment.getRecipes();
                Log.d(TAG,"Heiner guess what? that work i can said the second recipe is: "+recipes.get(1).getName());

            }
        });

    }

}
