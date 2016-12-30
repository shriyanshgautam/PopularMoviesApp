package com.shriyans.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shriyans.popularmovies.R;
import com.shriyans.popularmovies.models.Movie;
import com.shriyans.popularmovies.models.Review;
import com.shriyans.popularmovies.models.Trailer;
import com.shriyans.popularmovies.network.URLs;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

/**
 * Created by shriyanshgautam on 30/12/16.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>{

    private List<Trailer> trailerList;
    private Context context;

    public TrailerAdapter(Context context,List<Trailer> trailerList) {
        this.trailerList = trailerList;
        this.context = context;
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView trailerNameTextView;
        public ImageView trailerImageImageView;

        public TrailerViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            trailerImageImageView = (ImageView)view.findViewById(R.id.trailer_image);
            trailerNameTextView = (TextView)view.findViewById(R.id.trailer_name);
        }

        @Override
        public void onClick(View view) {
            Trailer trailer = trailerList.get(getPosition());
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URLs.YOUTUBE_VIDEO_URL+trailer.getKey())));
        }
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_item, parent, false);

        return new TrailerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        Trailer trailer = trailerList.get(position);
        Picasso.with(context)
                .load(URLs.YOUTUBE_BASE_URL+trailer.getKey()+ URLs.YOUTUBE_SUFFIX)
                .into(holder.trailerImageImageView);
        holder.trailerNameTextView.setText(trailer.getName());
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }
}

