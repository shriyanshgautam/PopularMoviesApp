package com.shriyans.popularmovies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shriyans.popularmovies.R;
import com.shriyans.popularmovies.network.URLs;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MovieDetailsActivity extends AppCompatActivity {
    TextView overviewTv,releaseDateTv,ratingTv;
    ImageView posterIv,moviePoster;
    int DEFAULT_AVERAGE_VOTE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String title,posterPath,overview,release_date;
        double vote_average;
        title = intent.getStringExtra(MainActivity.MOVIE_TITLE);
        posterPath = intent.getStringExtra(MainActivity.MOVIE_POSTER_PATH);
        overview = intent.getStringExtra(MainActivity.MOVIE_OVERVIEW);
        release_date = intent.getStringExtra(MainActivity.MOVIE_RELEASE_DATE);
        vote_average = intent.getDoubleExtra(MainActivity.MOVIE_VOTE_AVERAGE,DEFAULT_AVERAGE_VOTE);

        setTitle(title);

        init();

        overviewTv.setText(overview);
        releaseDateTv.setText(formatDate(release_date));
        ratingTv.setText(String.format(getResources().getString(R.string.rating_out_of), vote_average));

        Picasso.with(MovieDetailsActivity.this)
                .load(URLs.IMAGE_BASE_URL+URLs.IMAGE_SIZE+posterPath)
                .into(posterIv);

        Picasso.with(MovieDetailsActivity.this)
                .load(URLs.IMAGE_BASE_URL+URLs.IMAGE_SIZE+posterPath)
                .into(moviePoster);

    }

    private void init(){
        overviewTv = (TextView)findViewById(R.id.overview);
        releaseDateTv = (TextView)findViewById(R.id.release_date);
        posterIv = (ImageView)findViewById(R.id.top_poster);
        moviePoster = (ImageView)findViewById(R.id.movie_poster);
        ratingTv = (TextView)findViewById(R.id.rating);

    }


    private String formatDate(String dateString){
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = (Date)((DateFormat) formatter).parse(dateString);
            formatter = new SimpleDateFormat("MMMM dd, yyyy");
            return formatter.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            return dateString;
        }

    }
}
