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
    String MOVIE_ENDPOINT = "/movie/";
    String API_KEY_STRING = "?api_key="+ Keys.THE_MOVIE_DB_API_KEY;

    String POPULAR_MOVIES_URL = DATA_BASE_URL+POPULAR_ENDPOINT+"?api_key="+ Keys.THE_MOVIE_DB_API_KEY;
    String TOP_RATED_MOVIES_URL = DATA_BASE_URL+TOP_RATED_ENDPOINT+"?api_key="+ Keys.THE_MOVIE_DB_API_KEY;


    /*
    * Youtube URls
    * */
    String YOUTUBE_BASE_URL = "https://i.ytimg.com/vi/";
    String YOUTUBE_VIDEO_URL = "http://www.youtube.com/watch?v=";
    String YOUTUBE_SUFFIX = "/hqdefault.jpg?custom=true&w=196&h=110&stc=true&jpg444=true&jpgq=90&sp=68&sigh=ZNzLRxOAxrEE7sZyYw-x1bqDGps";


}
