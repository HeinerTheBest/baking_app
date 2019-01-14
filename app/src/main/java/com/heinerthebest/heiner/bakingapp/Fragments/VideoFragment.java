package com.heinerthebest.heiner.bakingapp.Fragments;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.heinerthebest.heiner.bakingapp.DataBase.AppDataBase;
import com.heinerthebest.heiner.bakingapp.Models.Step;
import com.heinerthebest.heiner.bakingapp.R;

import java.util.List;

public class VideoFragment extends Fragment
{
    private String TAG = VideoFragment.class.getSimpleName();
    private List<Step> steps;
    private int index;
    Context context;

    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;
    int recipeId = -1;

    private AppDataBase mDb;


    private static final String RECIPE_ARRAY_id_KEY = "recipearraykey";
    private static final String STEP_ARRAY_id_KEY = "steparraykey";


    public VideoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_video, container, false);
        context = rootView.getContext();
        mPlayerView = rootView.findViewById(R.id.player_view);

        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                (getResources(),R.drawable.question_mark));

        mDb = AppDataBase.getsInstance(rootView.getContext());



        if(savedInstanceState != null) {
            if(savedInstanceState.containsKey(RECIPE_ARRAY_id_KEY) && savedInstanceState.containsKey(STEP_ARRAY_id_KEY)) {
                index = savedInstanceState.getInt(STEP_ARRAY_id_KEY);
                Log.d(TAG, "My recipe id saved is:" + savedInstanceState.getInt(RECIPE_ARRAY_id_KEY) + " I'm here tooo yeah " );
                getRecipes(savedInstanceState.getInt(RECIPE_ARRAY_id_KEY));
            }
        }


        setVideo(index);


        return rootView;
    }


    private void getRecipes(final int idRecipe)
    {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if(mDb.recipeDao().loadRecipes().size() > 0)
                {
                    Log.d(TAG,"DB is not Empty, Have "+mDb.recipeDao().loadRecipes().size());
                    steps = mDb.recipeDao().loadRecipes().get(idRecipe).getSteps();
                    setVideo(index);

                }
                else
                {
                    Log.d(TAG,"DB is empy");
                }
            }
        });
        thread.start();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(RECIPE_ARRAY_id_KEY,recipeId);
        outState.putInt(STEP_ARRAY_id_KEY,index);
    }

    public void initializePlayer(Uri uri)
    {
        String userAgent = Util.getUserAgent(context,"BakingApp");
        if(mExoPlayer == null)
        {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl   = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(context,trackSelector,loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            MediaSource mediaSource = new ExtractorMediaSource(uri,new DefaultDataSourceFactory
                    (context,userAgent),new DefaultExtractorsFactory(),null,null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
        else
        {
            mExoPlayer.stop();
            MediaSource mediaSource = new ExtractorMediaSource(uri,new DefaultDataSourceFactory
                    (context,userAgent),new DefaultExtractorsFactory(),null,null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }

    }

    public void setVideo(int i)
    {
        if(steps != null) {
            String uri;
            if (!steps.get(i).getVideoURL().isEmpty()) {
                uri = steps.get(i).getVideoURL();
            } else {
                uri = steps.get(i).getThumbnailURL();
            }
            initializePlayer(Uri.parse(uri));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    public void setSteps(List<Step> steps, int position, int recipeId) {
        this.steps = steps;
        index = position;
        this.recipeId = recipeId;
    }

}
