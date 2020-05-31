package com.example.wizi.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wizi.popularmovies.utilities.QueryUtils;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Context mContext = this;

        Intent receivedIntent = getIntent();

        Movie current = new Movie();

        ImageView detailPoster = findViewById(R.id.iv_detail_poster);
        TextView detailTitle = findViewById(R.id.tv_detail_title);
        TextView detailOverview = findViewById(R.id.tv_detail_overview);
        TextView detailAverageVote = findViewById(R.id.tv_detail_average_vote);
        TextView detailReleaseDate = findViewById(R.id.tv_detail_release_date);


        current.setTitle(receivedIntent.getStringExtra("title"));
        current.setPoster(receivedIntent.getStringExtra("poster"));
        current.setOverview(receivedIntent.getStringExtra("overview"));
        current.setAverageVote(receivedIntent.getStringExtra("averageVote"));
        current.setReleaseDate(receivedIntent.getStringExtra("releaseDate"));

        detailTitle.setText(current.getTitle());
        detailOverview.setText(current.getOverview());
        detailAverageVote.setText(current.getAverageVote());
        detailReleaseDate.setText(current.getReleaseDate());

        Picasso.with(mContext)
                .load(QueryUtils.buildPosterUrl(current.getPoster()))
                .into(detailPoster);
    }
}
