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

    private static final String RECIPE_ARRAY_id_KEY = "recipearraykey";
    private static final String STEP_ARRAY_id_KEY = "steparraykey";

    private long mCurrentPosition = 0;
    private boolean mPlayWhenReady = true;


    private static final String CURRENT_POSITION_KEY = "currentpositionkey";
    private static final String PLAY_WHEN_READY_KEY = "playwhenreadykey";
    private static final String LOAD_INFO_KEY = "loadinfokey";




    public VideoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_video, container, false);
        context = rootView.getContext();
        mPlayerView = rootView.findViewById(R.id.player_view);
        Log.d(TAG,"On Crate VIew");
        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                (getResources(),R.drawable.question_mark));


        if(savedInstanceState != null) {


            if(savedInstanceState.containsKey(CURRENT_POSITION_KEY) && savedInstanceState.containsKey(PLAY_WHEN_READY_KEY))
            {
                mCurrentPosition = savedInstanceState.getLong(CURRENT_POSITION_KEY);
                mPlayWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY_KEY);
                index = savedInstanceState.getInt(LOAD_INFO_KEY);
                Log.d(TAG, "My position saved is:" + savedInstanceState.getLong(CURRENT_POSITION_KEY) + " and ready "+savedInstanceState.getBoolean(PLAY_WHEN_READY_KEY) );
            }


            if(savedInstanceState.containsKey(RECIPE_ARRAY_id_KEY) && savedInstanceState.containsKey(STEP_ARRAY_id_KEY)) {
                index = savedInstanceState.getInt(STEP_ARRAY_id_KEY);
                Log.d(TAG, "My recipe id saved is:" + savedInstanceState.getInt(RECIPE_ARRAY_id_KEY) + " I'm here tooo yeah " );
                setVideo(index);
            }
        }


        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            setVideo(index);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 )) {
            setVideo(index);
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(RECIPE_ARRAY_id_KEY,recipeId);
        outState.putInt(STEP_ARRAY_id_KEY,index);
        outState.putLong(CURRENT_POSITION_KEY, mCurrentPosition);
        outState.putBoolean(PLAY_WHEN_READY_KEY, mPlayWhenReady);
        outState.putInt(LOAD_INFO_KEY,index);
    }

    public void initializePlayer(Uri uri)
    {
        Log.d(TAG,"My boolean start is:"+mPlayWhenReady+" in the position:"+mCurrentPosition);
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


            if (mCurrentPosition != 0)
                mExoPlayer.seekTo(mCurrentPosition);
            mExoPlayer.setPlayWhenReady(mPlayWhenReady);
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
        index = i;
        if(steps != null) {
            String uri;
            if (!steps.get(i).getVideoURL().isEmpty()) {
                uri = steps.get(i).getVideoURL();
            } else {
                uri = steps.get(i).getThumbnailURL();
            }

            Log.d(TAG,"this is the link: "+uri+"is video: "+steps.get(i).getShortDescription());
            initializePlayer(Uri.parse(uri));
        }


    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }

      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getActivity().enterPictureInPictureMode();
        }*/

    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {

        Log.d(TAG,"the boolean is: "+mExoPlayer.getPlayWhenReady()+" in the position: "+mExoPlayer.getCurrentPosition());

        if (mExoPlayer != null) {
            mPlayWhenReady = mExoPlayer.getPlayWhenReady();
            mCurrentPosition = mExoPlayer.getCurrentPosition();

            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }

    }

    public void setSteps(List<Step> steps, int position, int recipeId) {
        Log.d(TAG,"I was called");
        this.steps = steps;
        index = position;
        this.recipeId = recipeId;

        Log.d(TAG, "My position saved is:" + mCurrentPosition+ " and ready "+mPlayWhenReady);

    }

}
