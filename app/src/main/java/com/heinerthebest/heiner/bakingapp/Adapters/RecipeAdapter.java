package com.heinerthebest.heiner.bakingapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heinerthebest.heiner.bakingapp.Models.Recipe;
import com.heinerthebest.heiner.bakingapp.R;
import com.heinerthebest.heiner.bakingapp.Interfaces.RecipeClickListener;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>
{

    private List<Recipe> recipes;
    private Context context;
    private RecipeClickListener mRecipeClickListener;

    public RecipeAdapter(Context context, List<Recipe> recipes, RecipeClickListener recipeClickListener) {
        this.recipes = recipes;
        this.context = context;
        mRecipeClickListener = recipeClickListener;

    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tv_title;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.recipe_name_text_view);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int clickPosition = getAdapterPosition();
            mRecipeClickListener.onMovieClick(clickPosition);
        }

    }




    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recipe_list_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.tv_title.setText(recipe.getName());

    }

    @Override
    public int getItemCount() {
        return (recipes == null) ? 0 : recipes.size();
    }
}
