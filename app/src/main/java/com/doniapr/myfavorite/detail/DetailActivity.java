package com.doniapr.myfavorite.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.doniapr.myfavorite.R;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_TYPE = "extra_type";
    public static final String EXTRA_ID = "extra_id";
    ProgressBar progressBar;
    DetailViewModel detailViewModel;
    TextView txtTitle, txtOverview, txtReleaseDate, txtPopularity, txtRuntime;
    ImageView imgPoster, imgPosterAppbar;
    RatingBar ratingBar;
    Toolbar toolbar;
    CollapsingToolbarLayout toolbarLayout;
    boolean isMovie;

    private Observer<ArrayList<Detail>> getDetail = new Observer<ArrayList<Detail>>() {
        @Override
        public void onChanged(@Nullable final ArrayList<Detail> detail) {
            if (detail != null) {
                txtTitle.setText(detail.get(0).getTitle());
                txtOverview.setText(detail.get(0).getOverview());
                txtReleaseDate.setText(detail.get(0).getRelease_date());
                txtPopularity.setText(detail.get(0).getPopularity());
                txtRuntime.setText(detail.get(0).getRuntime());
                Glide.with(DetailActivity.this).load(detail.get(0).getPoster())
                        .into(imgPoster);
                Glide.with(DetailActivity.this).load(detail.get(0).getPoster())
                        .into(imgPosterAppbar);
                final float value = (detail.get(0).getRating() / 10) * 5;
                ratingBar.setRating(value);
                ratingBar.setIsIndicator(true);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(detail.get(0).getTitle());
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
                showLoading(false);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();
        int id = getIntent().getIntExtra(EXTRA_ID, 0);
        isMovie = getIntent().getBooleanExtra(EXTRA_TYPE, true);

        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        detailViewModel.getDetail().observe(this, getDetail);
        detailViewModel.setDetail(this, isMovie, id);
        showLoading(true);
    }

    private void init() {
        txtTitle = findViewById(R.id.txt_title_detail);
        txtRuntime = findViewById(R.id.txt_content_runtime);
        txtPopularity = findViewById(R.id.txt_content_popularity);
        txtReleaseDate = findViewById(R.id.txt_content_releasedate);
        txtOverview = findViewById(R.id.txt_content_overview);
        ratingBar = findViewById(R.id.rating_detail);
        imgPoster = findViewById(R.id.img_poster_detail);
        imgPosterAppbar = findViewById(R.id.img_poster_detail_appbar);
        progressBar = findViewById(R.id.progress_bar_detail);

        toolbarLayout = findViewById(R.id.collapsing_tollbar);
        toolbarLayout.setExpandedTitleColor(Color.argb(0, 0, 0, 0));

        toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
    }

    private void showLoading(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
