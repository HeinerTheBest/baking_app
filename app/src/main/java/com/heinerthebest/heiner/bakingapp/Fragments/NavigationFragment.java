package com.heinerthebest.heiner.bakingapp.Fragments;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.heinerthebest.heiner.bakingapp.Activities.DescriptionActivity;
import com.heinerthebest.heiner.bakingapp.Models.Step;
import com.heinerthebest.heiner.bakingapp.R;

import java.util.List;


public class NavigationFragment extends Fragment
{
    private String TAG = NavigationFragment.class.getSimpleName();
    private List<Step> steps;
    private int index;
    int lastIndex;
    StepDescriptionFragment stepDescriptionFragment;
    VideoFragment videoFragment;
    ImageView superLeft,superright,left,right;

    public NavigationFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_navigation, container, false);
         superLeft = rootView.findViewById(R.id.btn_super_left);
         superright = rootView.findViewById(R.id.btn_super_rigt);
         left = rootView.findViewById(R.id.btn_left);
         right = rootView.findViewById(R.id.btn_right);


        lastIndex = steps.size()-1;

        stepDescriptionFragment = (StepDescriptionFragment) getActivity()
                .getSupportFragmentManager()
                .findFragmentById(R.id.body_container);

        videoFragment = (VideoFragment) getActivity()
                .getSupportFragmentManager()
                .findFragmentById(R.id.head_container);


        setButtons();

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                index--;
                stepDescriptionFragment.setDescription(index);
                videoFragment.setVideo(index);
                setButtons();
            }
        });

        superLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 0;
                stepDescriptionFragment.setDescription(index);
                videoFragment.setVideo(index);
                setButtons();
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                stepDescriptionFragment.setDescription(index);
                videoFragment.setVideo(index);
                setButtons();
            }
        });

        superright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = lastIndex;
                stepDescriptionFragment.setDescription(index);
                videoFragment.setVideo(index);
                setButtons();
            }
        });



        return rootView;
    }

    public void setButtons()
    {
        Log.d(TAG,"index is:"+index+" of "+steps.size()+" and last index said:"+lastIndex);

        if((index != 0) && (index != lastIndex))
        {
            superLeft.setVisibility(View.VISIBLE);
            left.setVisibility(View.VISIBLE);
            superright.setVisibility(View.VISIBLE);
            right.setVisibility(View.VISIBLE);
        }
        else {

            if (index == 0) {
                superLeft.setVisibility(View.INVISIBLE);
                left.setVisibility(View.INVISIBLE);
            }
            else
            {
                superLeft.setVisibility(View.VISIBLE);
                left.setVisibility(View.VISIBLE);
            }

            if (index == lastIndex) {
                superright.setVisibility(View.INVISIBLE);
                right.setVisibility(View.INVISIBLE);
            }
            else
            {
                superright.setVisibility(View.VISIBLE);
                right.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setSteps(List<Step> steps,int position) {
        this.steps = steps;
        index = position;
    }

}
