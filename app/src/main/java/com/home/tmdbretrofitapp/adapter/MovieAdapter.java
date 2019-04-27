package com.home.tmdbretrofitapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.home.tmdbretrofitapp.R;
import com.home.tmdbretrofitapp.databinding.MovieListItemBinding;
import com.home.tmdbretrofitapp.model.Movie;
import com.home.tmdbretrofitapp.view.AdditionalMovieDetails;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    Context context;
    ArrayList<Movie> movieArrayList;

    public MovieAdapter(Context context, ArrayList<Movie> movieArrayList) {
        this.context = context;
        this.movieArrayList = movieArrayList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MovieListItemBinding movieListItemBinding = DataBindingUtil.inflate(inflater,R.layout.movie_list_item,parent,false);

        return new MovieViewHolder(movieListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        Movie movie = movieArrayList.get(position);

       // movie.setPosterPath(imagePath);
        holder.movieListItemBinding.setMovies(movie);

       // Glide.with(context).load(imagePath).into(holder.movieLogo);

    }

    @Override
    public int getItemCount() {
        return movieArrayList != null ? movieArrayList.size() : 0;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

      private MovieListItemBinding movieListItemBinding;

        public MovieViewHolder(@NonNull MovieListItemBinding movieListItemBinding) {
            super(movieListItemBinding.getRoot());
//            tvRating = itemView.findViewById(R.id.tvRating);
//            tvTitle = itemView.findViewById(R.id.tvTitle);
//            movieLogo = itemView.findViewById(R.id.ivMovie);
              this.movieListItemBinding =movieListItemBinding;

            movieListItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int poistion = getAdapterPosition();
                    if(poistion != RecyclerView.NO_POSITION) {
                        Movie selectedMovie = movieArrayList.get(poistion);
                        Intent intent = new Intent(context,AdditionalMovieDetails.class);
                        intent.putExtra("movie",selectedMovie);
                        context.startActivity(intent);
                    }



                }
            });
        }
    }
}
