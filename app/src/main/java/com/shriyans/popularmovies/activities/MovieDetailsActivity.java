package com.shriyans.popularmovies.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shriyans.popularmovies.R;
import com.shriyans.popularmovies.adapters.MoviesAdapter;
import com.shriyans.popularmovies.adapters.ReviewAdapter;
import com.shriyans.popularmovies.adapters.TrailerAdapter;
import com.shriyans.popularmovies.models.Review;
import com.shriyans.popularmovies.models.Trailer;
import com.shriyans.popularmovies.network.JSONParser;
import com.shriyans.popularmovies.network.URLs;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    private RecyclerView trailerRecyclerView,reviewRecyclerView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private List<Review> reviewList;
    private List<Trailer>trailerList;
    private ReviewAdapter reviewAdapter;
    private TrailerAdapter trailerAdapter;
    TextView overviewTv,releaseDateTv,ratingTv;
    ImageView posterIv,moviePoster,favorite;
    int DEFAULT_AVERAGE_VOTE = 0;
    int DEFAULT_ID = 0;

    String title,posterPath,overview,release_date;
    double vote_average;
    long id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        id = intent.getLongExtra(MainActivity.MOVIE_ID,DEFAULT_ID);
        title = intent.getStringExtra(MainActivity.MOVIE_TITLE);
        posterPath = intent.getStringExtra(MainActivity.MOVIE_POSTER_PATH);
        overview = intent.getStringExtra(MainActivity.MOVIE_OVERVIEW);
        release_date = intent.getStringExtra(MainActivity.MOVIE_RELEASE_DATE);
        vote_average = intent.getDoubleExtra(MainActivity.MOVIE_VOTE_AVERAGE,DEFAULT_AVERAGE_VOTE);

        setTitle(title);

        init();

        getMovieReviews(URLs.DATA_BASE_URL+URLs.MOVIE_ENDPOINT+id+"/reviews"+URLs.API_KEY_STRING);
        getMovieTrailers(URLs.DATA_BASE_URL+URLs.MOVIE_ENDPOINT+id+"/videos"+URLs.API_KEY_STRING);

        overviewTv.setText(overview);
        releaseDateTv.setText(formatDate(release_date));
        ratingTv.setText(String.format(getResources().getString(R.string.rating_out_of), vote_average));

        String favorites = sharedPreferences.getString(MainActivity.SHDPFS_FAVORITE_MOVIES,"");
        List<String> favoritesList = new LinkedList<String>(Arrays.asList(favorites.split(",")));

        if(searchMovieId(favoritesList,id)){
            favorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
        }else{
            favorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
        }

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String favorites = sharedPreferences.getString(MainActivity.SHDPFS_FAVORITE_MOVIES,"");
                List<String> favoritesList = new LinkedList<String>(Arrays.asList(favorites.split(",")));

                if(MovieDetailsActivity.searchMovieId(favoritesList,id)){
                    //movie exists, remove it
                    favoritesList = MovieDetailsActivity.removeMovieFromList(favoritesList,id);
                    editor = sharedPreferences.edit();
                    editor.putString(MainActivity.SHDPFS_FAVORITE_MOVIES,MovieDetailsActivity.listToString(favoritesList));
                    editor.commit();
                    favorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
                    Toast.makeText(MovieDetailsActivity.this,title+" removed from Favorites",Toast.LENGTH_SHORT).show();

                }else{
                    //add movie
                    favoritesList = MovieDetailsActivity.addMovieToList(favoritesList,id);
                    editor = sharedPreferences.edit();
                    editor.putString(MainActivity.SHDPFS_FAVORITE_MOVIES,MovieDetailsActivity.listToString(favoritesList));
                    editor.commit();
                    favorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                    Toast.makeText(MovieDetailsActivity.this,title+" added to Favorites",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Picasso.with(MovieDetailsActivity.this)
                .load(URLs.IMAGE_BASE_URL+URLs.IMAGE_SIZE+posterPath)
                .placeholder(R.drawable.movie_placeholder)
                .into(posterIv);

        Picasso.with(MovieDetailsActivity.this)
                .load(URLs.IMAGE_BASE_URL+URLs.IMAGE_SIZE+posterPath)
                .placeholder(R.drawable.movie_placeholder)
                .into(moviePoster);

    }

    private void init(){
        requestQueue = Volley.newRequestQueue(this);
        overviewTv = (TextView)findViewById(R.id.overview);
        releaseDateTv = (TextView)findViewById(R.id.release_date);
        posterIv = (ImageView)findViewById(R.id.top_poster);
        moviePoster = (ImageView)findViewById(R.id.movie_poster);
        ratingTv = (TextView)findViewById(R.id.rating);
        favorite = (ImageView)findViewById(R.id.favorite);

        reviewRecyclerView = (RecyclerView)findViewById(R.id.review_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        reviewRecyclerView.setLayoutManager(mLayoutManager);
        reviewRecyclerView.setItemAnimator(new DefaultItemAnimator());
        reviewList = new ArrayList<>();

        trailerRecyclerView = (RecyclerView)findViewById(R.id.trailer_recycler_view);
        RecyclerView.LayoutManager nLayoutManager = new LinearLayoutManager(getApplicationContext());
        trailerRecyclerView.setLayoutManager(nLayoutManager);
        trailerRecyclerView.setItemAnimator(new DefaultItemAnimator());
        trailerList = new ArrayList<>();

        sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);

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

    private void getMovieReviews(String url){

        Log.d("Review Url",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response",response);
                        try {
                            reviewList = JSONParser.parseReviews(response);
                            reviewAdapter = new ReviewAdapter(MovieDetailsActivity.this,reviewList);
                            reviewRecyclerView.setAdapter(reviewAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);

    }

    private void getMovieTrailers(String url){

        Log.d("Review Url",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Trailor Response",response);
                        try {
                            trailerList = JSONParser.parseTrailers(response);
                            trailerAdapter = new TrailerAdapter(MovieDetailsActivity.this,trailerList);
                            trailerRecyclerView.setAdapter(trailerAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);

    }

    public static boolean searchMovieId(List<String> movieList,long movieId){


        for (String movie : movieList) {
            if(!movie.equals("")){
                if(Long.parseLong(movie)==movieId){
                    return true;
                }
            }
        }
        return false;
    }

    public static List<String> addMovieToList(List<String> movieList,long movieId){
        movieList.add(movieId+"");
        return movieList;
    }

    public static List<String> removeMovieFromList(List<String> movieList,long movieId){
        movieList.remove(movieId+"");
        return movieList;
    }

    public static String listToString(List<String> movieList){
        String output = "";
        for(String movie : movieList){
            output+=movie+",";
        }
        return output.substring(0,output.length()-1);
    }
}
