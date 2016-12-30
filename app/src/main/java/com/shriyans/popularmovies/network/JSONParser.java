package com.shriyans.popularmovies.network;

import android.util.Log;

import com.shriyans.popularmovies.models.Movie;
import com.shriyans.popularmovies.models.Review;
import com.shriyans.popularmovies.models.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shriyans on 7/14/16.
 */
public class JSONParser {

    public static  Movie parseMovie(JSONObject movieJSON) throws JSONException{
        String movieTitle,movieOverview,moviePosterPath,movieReleaseDate;
        double movieVoteAverage;
        long movieId;
        movieId = movieJSON.getLong(JSONConstants.MOVIE_ID);
        movieTitle = movieJSON.getString(JSONConstants.MOVIE_TITLE);
        movieOverview = movieJSON.getString(JSONConstants.MOVIE_OVERVIEW);
        moviePosterPath = movieJSON.getString(JSONConstants.MOVIE_POSTER_PATH);
        movieReleaseDate = movieJSON.getString(JSONConstants.MOVIE_RELEASE_DATE);
        movieVoteAverage = movieJSON.getDouble(JSONConstants.MOVIE_VOTE_AVERAGE);
        return new Movie(movieId,movieTitle,moviePosterPath,movieOverview,movieVoteAverage,movieReleaseDate);
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

    public static Review parseReview(JSONObject reviewJSON) throws JSONException{
        String id;
        String author,content;
        id = reviewJSON.getString(JSONConstants.REVIEW_ID);
        author = reviewJSON.getString(JSONConstants.REVIEW_AUTHOR);
        content = reviewJSON.getString(JSONConstants.REVIEW_CONTENT);

        return new Review(id,author,content);
    }

    public static List<Review> parseReviews(String jsonResponse) throws JSONException{
        List<Review> reviewList = new ArrayList<>();
        JSONObject response = new JSONObject(jsonResponse);
        JSONArray reviewArray = response.getJSONArray(JSONConstants.REVIEW_RESULTS);
        for(int counter=0;counter<reviewArray.length();counter++){
            JSONObject reviewJson = reviewArray.getJSONObject(counter);
            Review review = parseReview(reviewJson);
            reviewList.add(review);
        }
        return reviewList;
    }


    public static Trailer parseTrailer(JSONObject reviewJSON) throws JSONException{
        String id,key,name;
        id = reviewJSON.getString(JSONConstants.TRAILER_ID);
        key = reviewJSON.getString(JSONConstants.TRAILER_KEY);
        name = reviewJSON.getString(JSONConstants.TRAILER_NAME);

        return new Trailer(id,key,name);
    }

    public static List<Trailer> parseTrailers(String jsonResponse) throws JSONException{
        List<Trailer> trailerList = new ArrayList<>();
        JSONObject response = new JSONObject(jsonResponse);
        JSONArray trailerArray = response.getJSONArray(JSONConstants.TRAILER_RESULTS);
        for(int counter=0;counter<trailerArray.length();counter++){
            JSONObject trailerJson = trailerArray.getJSONObject(counter);
            Trailer trailer = parseTrailer(trailerJson);
            trailerList.add(trailer);
        }
        return trailerList;
    }


}
