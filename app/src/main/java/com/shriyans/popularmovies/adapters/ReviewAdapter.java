package com.shriyans.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shriyans.popularmovies.R;
import com.shriyans.popularmovies.activities.MainActivity;
import com.shriyans.popularmovies.activities.MovieDetailsActivity;
import com.shriyans.popularmovies.models.Movie;
import com.shriyans.popularmovies.models.Review;
import com.shriyans.popularmovies.network.URLs;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by shriyanshgautam on 30/12/16.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewsViewHolder> {

    private List<Review> reviewsList;
    private Context context;

    public ReviewAdapter(Context context,List<Review> reviewList) {
        this.reviewsList = reviewList;
        this.context = context;
    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder{
        public TextView reviewTextView,authorTextView;

        public ReviewsViewHolder(View view) {
            super(view);
            reviewTextView = (TextView)view.findViewById(R.id.review);
            authorTextView = (TextView)view.findViewById(R.id.author);
        }
    }

    @Override
    public ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);

        return new ReviewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReviewsViewHolder holder, int position) {
        Review review = reviewsList.get(position);
        holder.authorTextView.setText(review.getAuthor());
        holder.reviewTextView.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }
}
