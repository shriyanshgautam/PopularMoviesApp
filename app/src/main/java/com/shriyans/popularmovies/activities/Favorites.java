package com.shriyans.popularmovies.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Favorites extends AppCompatActivity {

    RequestQueue requestQueue;
    private List<Movie> favoriteMovieList;
    private RecyclerView recyclerView;
    private MoviesAdapter movieAdapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        init();

        String favorites = sharedPreferences.getString(MainActivity.SHDPFS_FAVORITE_MOVIES,"");
        List<String> favoritesList = new LinkedList<String>(Arrays.asList(favorites.split(",")));
        for(String movie: favoritesList){
            getMovies(URLs.DATA_BASE_URL+URLs.MOVIE_ENDPOINT+movie+ URLs.API_KEY_STRING);
            Log.d("Fav",movie);
        }
    }

    private void init(){
        recyclerView = (RecyclerView)findViewById(R.id.favorite_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),2, LinearLayoutManager.VERTICAL,false);

        requestQueue = Volley.newRequestQueue(this);
        favoriteMovieList = new ArrayList<>();
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        movieAdapter = new MoviesAdapter(Favorites.this,favoriteMovieList);
        recyclerView.setAdapter(movieAdapter);
        sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);
    }


    private void getMovies(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response",response);
                        try {

                            Movie movie = JSONParser.parseMovie(new JSONObject(response));
                            favoriteMovieList.add(movie);
                            movieAdapter.notifyDataSetChanged();
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
