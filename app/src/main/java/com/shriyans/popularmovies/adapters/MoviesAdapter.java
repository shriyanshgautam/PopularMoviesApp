package com.shriyans.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.shriyans.popularmovies.R;
import com.shriyans.popularmovies.activities.MainActivity;
import com.shriyans.popularmovies.activities.MovieDetailsActivity;
import com.shriyans.popularmovies.models.Movie;
import com.shriyans.popularmovies.network.URLs;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by shriyans on 7/13/16.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private List<Movie> moviesList;
    private Context context;

    public MoviesAdapter(Context context,List<Movie> moviesList) {
        this.moviesList = moviesList;
        this.context = context;
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView moviePoster;

        public MoviesViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            moviePoster = (ImageView) view.findViewById(R.id.movie_poster);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            Movie movie = moviesList.get(getPosition());
            intent.putExtra(MainActivity.MOVIE_TITLE,movie.getTitle());
            intent.putExtra(MainActivity.MOVIE_OVERVIEW,movie.getOverview());
            intent.putExtra(MainActivity.MOVIE_POSTER_PATH,movie.getPosterPath());
            intent.putExtra(MainActivity.MOVIE_RELEASE_DATE,movie.getReleaseDate());
            intent.putExtra(MainActivity.MOVIE_VOTE_AVERAGE,movie.getVoteAverage());
            context.startActivity(intent);
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
                .into(holder.moviePoster);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

}
