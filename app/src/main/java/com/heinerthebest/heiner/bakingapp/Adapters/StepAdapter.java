package com.heinerthebest.heiner.bakingapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heinerthebest.heiner.bakingapp.Interfaces.RecipeClickListener;
import com.heinerthebest.heiner.bakingapp.Models.Step;
import com.heinerthebest.heiner.bakingapp.R;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder>
{

    private List<Step> steps;
    private Context context;
    private RecipeClickListener mRecipeClickListener;


    public StepAdapter(List<Step> steps, Context context, RecipeClickListener mRecipeClickListener) {
        this.steps = steps;
        this.context = context;
        this.mRecipeClickListener = mRecipeClickListener;
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tv_title;


        public StepViewHolder(View itemView) {
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
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recipe_list_item, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        Step step = steps.get(position);
        holder.tv_title.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return (steps == null) ? 0 : steps.size();
    }
}
