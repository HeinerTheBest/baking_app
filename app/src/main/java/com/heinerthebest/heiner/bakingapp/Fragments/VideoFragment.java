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

        setVideo(index);


        return rootView;
    }


    public void initializePlayer(Uri uri)
    {
        String userAgent = Util.getUserAgent(context,"BakingApp");
        if(mExoPlayer == null)
        {
            // Completed (6): Instantiate a SimpleExoPlayer object using DefaultTrackSelector and DefaultLoadControl.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl   = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(context,trackSelector,loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Completed (7): Prepare the MediaSource using DefaultDataSourceFactory and DefaultExtractorsFactory, as well as the Sample URI you passed in.
            MediaSource mediaSource = new ExtractorMediaSource(uri,new DefaultDataSourceFactory
                    (context,userAgent),new DefaultExtractorsFactory(),null,null);
            // Completed (8): Prepare the ExoPlayer with the MediaSource, start playing the sample and set the SimpleExoPlayer to the SimpleExoPlayerView.
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
        else
        {
            mExoPlayer.stop();
            MediaSource mediaSource = new ExtractorMediaSource(uri,new DefaultDataSourceFactory
                    (context,userAgent),new DefaultExtractorsFactory(),null,null);
            // Completed (8): Prepare the ExoPlayer with the MediaSource, start playing the sample and set the SimpleExoPlayer to the SimpleExoPlayerView.
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }

    }

    public void setVideo(int i)
    {
       /* if(mExoPlayer != null)
        {
            mExoPlayer.release();
        }*/
        Log.d(TAG,"Playing video "+i+" link "+steps.get(i).getVideoURL());
        String uri;
        if(!steps.get(i).getVideoURL().isEmpty())
        {
            uri = steps.get(i).getVideoURL();
        }
        else
        {
            uri = steps.get(i).getThumbnailURL();
        }
        initializePlayer(Uri.parse(uri));
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

    public void setSteps(List<Step> steps, int position) {
        this.steps = steps;
        index = position;
    }

}
