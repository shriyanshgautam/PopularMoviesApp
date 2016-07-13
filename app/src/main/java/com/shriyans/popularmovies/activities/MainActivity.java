package com.shriyans.popularmovies.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shriyans.popularmovies.R;
import com.shriyans.popularmovies.adapters.MoviesAdapter;
import com.shriyans.popularmovies.models.Movie;
import com.shriyans.popularmovies.network.JSONParser;
import com.shriyans.popularmovies.network.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    private List<Movie> popularMovieList,topRatedMovieList;
    private RecyclerView recyclerView;
    private MoviesAdapter movieAdapter;
    private int POPULAR_MOVIES = 1;
    private int TOP_RATED_MOVIES = 2;
    private int CURRENT_SORT_TYPE;
    private LinearLayout loaderLayout;
    private TextView loaderTextView;

    //Intent Constants
    public static String MOVIE_TITLE = "title";
    public static String MOVIE_OVERVIEW = "overview";
    public static String MOVIE_POSTER_PATH = "poster_path";
    public static String MOVIE_RELEASE_DATE = "release_date";
    public static String MOVIE_VOTE_AVERAGE = "vote_average";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        getMovies(URLs.POPULAR_MOVIES_URL,POPULAR_MOVIES);
        CURRENT_SORT_TYPE = POPULAR_MOVIES;

    }

    private void init(){
        requestQueue = Volley.newRequestQueue(this);
        popularMovieList = new ArrayList<>();
        topRatedMovieList = new ArrayList<>();
        loaderLayout = (LinearLayout)findViewById(R.id.loader_layout);
        loaderTextView = (TextView)findViewById(R.id.loading_text);
        recyclerView = (RecyclerView)findViewById(R.id.movie_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),2,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sort_type:
                if(CURRENT_SORT_TYPE == POPULAR_MOVIES){
                    getMovies(URLs.TOP_RATED_MOVIES_URL,TOP_RATED_MOVIES);
                    item.setTitle("POPULAR");
                    item.setIcon(R.drawable.ic_trending_up_white_24dp);
                }else if(CURRENT_SORT_TYPE == TOP_RATED_MOVIES){
                    getMovies(URLs.POPULAR_MOVIES_URL,POPULAR_MOVIES);
                    item.setTitle("TOP RATED");
                    item.setIcon(R.drawable.ic_star_rate_white_18dp);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getMovies(String url, final int sortType){

        loaderLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);


        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            if(sortType==POPULAR_MOVIES){
                                popularMovieList = JSONParser.parseMovies(response);
                                movieAdapter = new MoviesAdapter(MainActivity.this,popularMovieList);
                                setTitle(R.string.title_popular);
                                CURRENT_SORT_TYPE = POPULAR_MOVIES;
                            }else if(sortType==TOP_RATED_MOVIES){
                                topRatedMovieList = JSONParser.parseMovies(response);
                                movieAdapter = new MoviesAdapter(MainActivity.this,topRatedMovieList);
                                setTitle(R.string.title_top_rated);
                                CURRENT_SORT_TYPE = TOP_RATED_MOVIES;
                            }

                            recyclerView.setAdapter(movieAdapter);

                            loaderLayout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);



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
}
