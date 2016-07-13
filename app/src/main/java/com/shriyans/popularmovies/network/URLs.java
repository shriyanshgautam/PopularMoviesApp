package com.shriyans.popularmovies.network;

import com.shriyans.popularmovies.configs.Keys;

/**
 * Created by shriyans on 7/13/16.
 */
public interface URLs {
    String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    String IMAGE_SIZE = "w185";

    String DATA_BASE_URL = "https://api.themoviedb.org/3";
    String POPULAR_ENDPOINT = "/movie/popular";
    String TOP_RATED_ENDPOINT = "/movie/top_rated";
    String MOVIE_ENDPOINT = "/movie";

    String POPULAR_MOVIES_URL = DATA_BASE_URL+POPULAR_ENDPOINT+"?api_key="+ Keys.THE_MOVIE_DB_API_KEY;
    String TOP_RATED_MOVIES_URL = DATA_BASE_URL+TOP_RATED_ENDPOINT+"?api_key="+ Keys.THE_MOVIE_DB_API_KEY;
}
