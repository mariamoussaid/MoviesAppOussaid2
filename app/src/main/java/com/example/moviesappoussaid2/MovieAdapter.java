package com.example.moviesappoussaid2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import com.squareup.picasso.Picasso;


public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private final Context context;
    private List<Movie> movies;

    public MovieAdapter(Context context) {
        this.context = context;
        movies = new ArrayList<>();
    }
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.titleTextView.setText(movie.getTitle());
        holder.releaseDateTextView.setText(movie.getReleaseDate());
        holder.overviewTextView.setText(movie.getOverview());

        // Load and display movie poster image using Picasso or Glide library
        // Example with Picasso:
        // Picasso.get().load(movie.getPosterUrl()).into(holder.posterImageView);
        // Example with Glide:
        // Glide.with(context).load(movie.getPosterUrl()).into(holder.posterImageView);
        String imageUrl = "https://image.tmdb.org/t/p/w500" + movie.getImageUrl();
        Picasso.get().load(imageUrl).into(holder.posterImageView);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }
}
