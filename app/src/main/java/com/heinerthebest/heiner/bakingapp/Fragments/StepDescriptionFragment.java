package com.heinerthebest.heiner.bakingapp.Fragments;

import android.app.Fragment;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.heinerthebest.heiner.bakingapp.Models.Recipe;
import com.heinerthebest.heiner.bakingapp.Models.Step;
import com.heinerthebest.heiner.bakingapp.R;

import java.util.List;

public class StepDescriptionFragment extends android.support.v4.app.Fragment
{
    private String TAG = StepDescriptionFragment.class.getSimpleName();
    private List<Step> steps;
    private int index;
    TextView tvStepDescription;
    TextView title;

    public StepDescriptionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_step_description, container, false);
        tvStepDescription = rootView.findViewById(R.id.tv_steps_description);
        title = rootView.findViewById(R.id.tv_steps_description_head);

        setDescription(index);


        return rootView;
    }

    public void setDescription(int i) {
        tvStepDescription.setText(steps.get(i).getDescription());
        title.setText(steps.get(i).getShortDescription());
    }


    public void setSteps(List<Step> steps,int position) {
        this.steps = steps;
        index = position;
    }

}
