package com.heinerthebest.heiner.bakingapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.heinerthebest.heiner.bakingapp.R;

import java.util.List;

public class IngredientsFragment extends Fragment {

    private String TAG = IngredientsFragment.class.getSimpleName();
    private String mIngredients;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);

        TextView tvIngredients = rootView.findViewById(R.id.tv_ingredients);

        // Completed (3) If a list of image ids exists, set the image resource to the correct item in that list
        // Otherwise, create a Log statement that indicates that the list was not found
        if(mIngredients != null){
            // Set the image resource to the list item at the stored index
            tvIngredients.setText(mIngredients);
        } else {
            Log.v(TAG, "This fragment has a null ingredients ");
        }

        // Return the rootView
        return rootView;
    }

    public IngredientsFragment() {

    }


    public void setIngredients(String ingredients) {
        mIngredients = ingredients;
    }

}
