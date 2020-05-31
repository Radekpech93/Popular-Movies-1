package com.example.wizi.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.wizi.popularmovies.utilities.QueryUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();

    private List<Movie> mMovies;

    private final Context mContext;


    public MovieAdapter(Context context, List<Movie> movies) {
        mContext = context;
        mMovies = movies;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new MovieHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder movieHolder, int position) {

        String picassoUrl = QueryUtils.buildPosterUrl(mMovies.get(position).getPoster());

        Picasso.with(mContext)
                .load(picassoUrl)
                .into(movieHolder.movieView);
    }


    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    class MovieHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        ImageView movieView;

        public MovieHolder(View view) {
            super(view);

            movieView = view.findViewById(R.id.iv_movie);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, DetailActivity.class);
            int position = getAdapterPosition();

            String title = mMovies.get(position).getTitle();
            String poster = mMovies.get(position).getPoster();
            String overview = mMovies.get(position).getOverview();
            String averageVote = mMovies.get(position).getAverageVote();
            String releaseDate = mMovies.get(position).getReleaseDate();

            intent.putExtra("title", title);
            intent.putExtra("poster", poster);
            intent.putExtra("overview", overview);
            intent.putExtra("averageVote", averageVote);
            intent.putExtra("releaseDate", releaseDate);

            mContext.startActivity(intent);
        }
    }

    public void setMovieData(List<Movie> movieData) {
        mMovies = movieData;
        notifyDataSetChanged();
    }


}
