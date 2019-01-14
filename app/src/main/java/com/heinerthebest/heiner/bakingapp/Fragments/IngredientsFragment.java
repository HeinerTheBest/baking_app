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


        if(mIngredients != null){
            tvIngredients.setText(mIngredients);
            Log.d(TAG, "This fragment hasn't a null ingredients ");

        } else {
            Log.d(TAG, "This fragment has a null ingredients ");
        }

        return rootView;
    }

    public IngredientsFragment() {

    }


    public void setIngredients(String ingredients) {
        mIngredients = ingredients;
    }

}
