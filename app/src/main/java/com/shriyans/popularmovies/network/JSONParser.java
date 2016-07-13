package com.shriyans.popularmovies.network;

import android.util.Log;

import com.shriyans.popularmovies.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shriyans on 7/14/16.
 */
public class JSONParser {

    private static  Movie parseMovie(JSONObject movieJSON) throws JSONException{
        String movieTitle,movieOverview,moviePosterPath,movieReleaseDate;
        double movieVoteAverage;
        movieTitle = movieJSON.getString(JSONConstants.MOVIE_TITLE);
        movieOverview = movieJSON.getString(JSONConstants.MOVIE_OVERVIEW);
        moviePosterPath = movieJSON.getString(JSONConstants.MOVIE_POSTER_PATH);
        movieReleaseDate = movieJSON.getString(JSONConstants.MOVIE_RELEASE_DATE);
        movieVoteAverage = movieJSON.getDouble(JSONConstants.MOVIE_VOTE_AVERAGE);
        return new Movie(movieTitle,moviePosterPath,movieOverview,movieVoteAverage,movieReleaseDate);
    }

    public static List<Movie> parseMovies(String jsonResponse) throws JSONException{
        List<Movie> movieList = new ArrayList<>();
        JSONObject responseString = new JSONObject(jsonResponse);
        JSONArray result = responseString.getJSONArray(JSONConstants.MOVIE_RESULTS);
        for(int counter=0;counter<result.length();counter++) {
            JSONObject jsonMovie = result.getJSONObject(counter);
            Movie movie = parseMovie(jsonMovie);
            movieList.add(movie);
        }

        return movieList;
    }
}
