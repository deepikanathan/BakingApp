package com.nanodegree.projects.bakingrecipes.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.nanodegree.projects.bakingrecipes.R;
import com.nanodegree.projects.bakingrecipes.models.Step;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment to show the Recipe instruction and video, if available.
 */
public class RecipeStepDetailFragment extends Fragment {

    private long currentStepNumber = 0;
    private boolean isExoPlayerReady = true;
    private Step step;

    @BindView(R.id.exo_player)
    SimpleExoPlayerView exoPlayerView;
    @BindView(R.id.instructions_scrollview)
    NestedScrollView nestedScrollView;
    @BindView(R.id.instruction_text)
    TextView instructionsView;
    @BindView(R.id.step_thumbnail_image)
    ImageView imageView;

    private SimpleExoPlayer exoPlayer;
    private Unbinder unbinder;

    public RecipeStepDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  get the Step from bundle
        if (getArguments() != null && getArguments().containsKey(getResources().getString(R.string.step))) {
            step = getArguments().getParcelable(getResources().getString(R.string.step));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_step_detail, container, false);
        //  get current step number
        if (savedInstanceState != null && savedInstanceState.containsKey(getString(R.string.position))) {
            currentStepNumber = savedInstanceState.getLong(getString(R.string.position));
            isExoPlayerReady = savedInstanceState.getBoolean(getString(R.string.video_ready));
        }

        unbinder = ButterKnife.bind(this, rootView);
        instructionsView.setText(step.getDescription());
        if (!step.getThumbnailURL().isEmpty()) {
            Picasso.get()
                    .load(step.getThumbnailURL())
                    .placeholder(R.drawable.widget_recipe)
                    .into(imageView);
            imageView.setVisibility(View.VISIBLE);
        }

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(step.getVideoURL()))
            initializePlayer(Uri.parse(step.getVideoURL()));
        else {
            nestedScrollView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //  add the position and boolean to the bundle
        outState.putLong(getString(R.string.position), currentStepNumber);
        outState.putBoolean(getString(R.string.video_ready), isExoPlayerReady);
    }

    private void initializePlayer(Uri mediaUri) {
        if (exoPlayer == null) {
            // Create a default TrackSelector
            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

            // Initialize the player
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
            exoPlayerView.setPlayer(exoPlayer);

            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), getString(R.string.app_name)), bandwidthMeter);
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(mediaUri);
            exoPlayer.prepare(videoSource);

            if (currentStepNumber != 0) {
                exoPlayer.seekTo(currentStepNumber);
            }

            exoPlayer.setPlayWhenReady(isExoPlayerReady);
            exoPlayerView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if (exoPlayer != null) {
            currentStepNumber = exoPlayer.getCurrentPosition();
            isExoPlayerReady = exoPlayer.getPlayWhenReady();
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }
}
