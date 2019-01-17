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
import android.widget.Toast;

import com.heinerthebest.heiner.bakingapp.AppWidgetService;
import com.heinerthebest.heiner.bakingapp.R;

import java.util.List;

public class IngredientsFragment extends Fragment {

    private String TAG = IngredientsFragment.class.getSimpleName();
    private String mIngredients;
    private String title;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);

        TextView tvIngredients = rootView.findViewById(R.id.tv_ingredients);
        TextView addWidget = rootView.findViewById(R.id.tv_add_widget);


        if(mIngredients != null){
            tvIngredients.setText(mIngredients);
            Log.d(TAG, "This fragment hasn't a null ingredients ");

        } else {
            Log.d(TAG, "This fragment has a null ingredients ");
        }

        addWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIngredients != null)
                {
                    AppWidgetService.updateWidget(rootView.getContext(), title,mIngredients);

                    Toast.makeText(getContext(),getString(R.string.done),
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

        return rootView;
    }

    public IngredientsFragment() {

    }


    public void setIngredients(String ingredients,String title) {
        mIngredients = ingredients;
        this.title = title;
    }

}
