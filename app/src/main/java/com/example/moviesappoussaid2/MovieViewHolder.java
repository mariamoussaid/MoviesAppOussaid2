package com.example.moviesappoussaid2;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    public TextView titleTextView;
    public TextView releaseDateTextView;
    public TextView overviewTextView;

    public ImageView posterImageView;


    public MovieViewHolder(View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.movie_title_textView);
        releaseDateTextView = itemView.findViewById(R.id.movie_release_date_textView);
        overviewTextView = itemView.findViewById(R.id.movie_overview_textView);
        posterImageView = itemView.findViewById(R.id.movie_poster_imageView);

    }
}
