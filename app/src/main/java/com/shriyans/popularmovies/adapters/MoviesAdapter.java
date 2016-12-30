package com.shriyans.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.shriyans.popularmovies.R;
import com.shriyans.popularmovies.activities.MainActivity;
import com.shriyans.popularmovies.activities.MovieDetailsActivity;
import com.shriyans.popularmovies.models.Movie;
import com.shriyans.popularmovies.network.URLs;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by shriyans on 7/13/16.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private List<Movie> moviesList;
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public MoviesAdapter(Context context,List<Movie> moviesList) {
        this.moviesList = moviesList;
        this.context = context;
        sharedPreferences = context.getSharedPreferences(MainActivity.SHARED_PREFERENCE_KEY,Context.MODE_PRIVATE);
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView moviePoster,favorite;

        public MoviesViewHolder(View view) {
            super(view);
            moviePoster = (ImageView) view.findViewById(R.id.movie_poster);
            moviePoster.setOnClickListener(this);
            favorite = (ImageView) view.findViewById(R.id.favorite);
            favorite.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Movie movie = moviesList.get(getPosition());
            if(view.getId()==R.id.movie_poster){
                Intent intent = new Intent(context, MovieDetailsActivity.class);

                intent.putExtra(MainActivity.MOVIE_ID,movie.getId());
                intent.putExtra(MainActivity.MOVIE_TITLE,movie.getTitle());
                intent.putExtra(MainActivity.MOVIE_OVERVIEW,movie.getOverview());
                intent.putExtra(MainActivity.MOVIE_POSTER_PATH,movie.getPosterPath());
                intent.putExtra(MainActivity.MOVIE_RELEASE_DATE,movie.getReleaseDate());
                intent.putExtra(MainActivity.MOVIE_VOTE_AVERAGE,movie.getVoteAverage());
                context.startActivity(intent);
            }else if(view.getId()==R.id.favorite){

                String favorites = sharedPreferences.getString(MainActivity.SHDPFS_FAVORITE_MOVIES,"");
                List<String> favoritesList = new LinkedList<String>(Arrays.asList(favorites.split(",")));

                if(MovieDetailsActivity.searchMovieId(favoritesList,movie.getId())){
                    //movie exists, remove it
                    favoritesList = MovieDetailsActivity.removeMovieFromList(favoritesList,movie.getId());
                    editor = sharedPreferences.edit();
                    editor.putString(MainActivity.SHDPFS_FAVORITE_MOVIES,MovieDetailsActivity.listToString(favoritesList));
                    editor.commit();
                    Toast.makeText(context,movie.getTitle()+" removed from Favorites",Toast.LENGTH_SHORT).show();

                }else{
                    //add movie
                    favoritesList = MovieDetailsActivity.addMovieToList(favoritesList,movie.getId());
                    editor = sharedPreferences.edit();
                    editor.putString(MainActivity.SHDPFS_FAVORITE_MOVIES,MovieDetailsActivity.listToString(favoritesList));
                    editor.commit();
                    Toast.makeText(context,movie.getTitle()+" added to Favorites",Toast.LENGTH_SHORT).show();
                }
                notifyDataSetChanged();
            }

        }
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);

        return new MoviesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
        Picasso.with(context)
                .load(URLs.IMAGE_BASE_URL+URLs.IMAGE_SIZE+movie.getPosterPath())
                .placeholder(R.drawable.movie_placeholder)
                .into(holder.moviePoster);

        String favorites = sharedPreferences.getString(MainActivity.SHDPFS_FAVORITE_MOVIES,"");
        List<String> favoritesList = new LinkedList<String>(Arrays.asList(favorites.split(",")));

        if(MovieDetailsActivity.searchMovieId(favoritesList,movie.getId())){
            holder.favorite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_white_24dp));
        }else{
            holder.favorite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }



}
