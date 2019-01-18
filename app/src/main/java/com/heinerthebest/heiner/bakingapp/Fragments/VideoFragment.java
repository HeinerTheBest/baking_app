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
import com.heinerthebest.heiner.bakingapp.Prefs;
import com.heinerthebest.heiner.bakingapp.R;

import java.util.List;

public class VideoFragment extends Fragment
{
    private String TAG = VideoFragment.class.getSimpleName();
    private List<Step> steps;
    Context context;

    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;

    private long mCurrentPosition = 0;
    private boolean mPlayWhenReady = true;
    private int stepId = 0;








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

        mCurrentPosition = Prefs.getCurrentVideoPosition(context);
        mPlayWhenReady   = Prefs.isPlaying(context);


            stepId = Prefs.getStepId(context);
            setVideo(stepId);

        stepId = Prefs.getStepId(context);
        setVideo(stepId);
        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            setVideo(stepId);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 )) {
            setVideo(stepId);
        }

    }

    public void initializePlayer(Uri uri)
    {
        Log.d(TAG,"My boolean start is:"+mPlayWhenReady+" in the position:"+mCurrentPosition+"and the preff is: "+Prefs.getCurrentVideoPosition(context));
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

            Log.d(TAG,"Insside and progres is "+mCurrentPosition);

            if (mCurrentPosition != 0)
                mExoPlayer.seekTo(mCurrentPosition);
            mExoPlayer.setPlayWhenReady(mPlayWhenReady);
        }
        else
        {
            Log.d(TAG,"in the else and progres is "+mCurrentPosition);

            mExoPlayer.stop();
            MediaSource mediaSource = new ExtractorMediaSource(uri,new DefaultDataSourceFactory
                    (context,userAgent),new DefaultExtractorsFactory(),null,null);
            mExoPlayer.prepare(mediaSource);
            if (mCurrentPosition != 0)
                mExoPlayer.seekTo(mCurrentPosition);
            mExoPlayer.setPlayWhenReady(mPlayWhenReady);
        }





    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
        Prefs.setCurrentVideoPosition(context,mCurrentPosition);
        Prefs.setPlaying(context,mPlayWhenReady);
        Log.d(TAG,"Befor destroy my current is "+mCurrentPosition);

    }

    public void setVideo(int i)
    {
        stepId = i;
        mCurrentPosition = Prefs.getCurrentVideoPosition(context);
        mPlayWhenReady   = Prefs.isPlaying(context);
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
            Prefs.setCurrentVideoPosition(context,mCurrentPosition);
            Prefs.setPlaying(context,mPlayWhenReady);
        }
        Log.d(TAG,"On pause my current is:"+mCurrentPosition);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
            Prefs.setCurrentVideoPosition(context,mCurrentPosition);
            Prefs.setPlaying(context,mPlayWhenReady);
        }
        Log.d(TAG,"On stop my current is:"+mCurrentPosition);

    }

    private void releasePlayer()
    {
        if (mExoPlayer != null)
        {
            mPlayWhenReady = mExoPlayer.getPlayWhenReady();
            mCurrentPosition = mExoPlayer.getCurrentPosition();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
        else
        {
            Log.d(TAG,"EXOPLAYER is null the boolean is:null All is null ");
        }

    }

    public void setSteps(List<Step> steps, int position, int recipeId) {
        this.steps = steps;

        //this.StepId = recipeId;


    }

}
