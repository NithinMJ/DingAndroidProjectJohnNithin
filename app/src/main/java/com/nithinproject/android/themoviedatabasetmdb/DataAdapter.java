package com.nithinproject.android.themoviedatabasetmdb;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/*Developed by Nithin John*/

//Adapter class for Recycler View
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<MovieInfo> movie;
    private Context context;

    public DataAdapter(Context context,ArrayList<MovieInfo> movie) {
        this.movie = movie;
        this.context = context;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_trial, viewGroup, false);
        return new ViewHolder(view,context,movie);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {

        //Set UI : ImageView and TextView of MainActivity with respective information to be displayed
        viewHolder.title_movie.setText(movie.get(i).getTitle());
        viewHolder.release_movie.setText((movie.get(i).getRelease()));
        viewHolder.popularity_movie.setText(movie.get(i).getPopularity());
        Picasso.with(context).load(movie.get(i)
                .getPoster())
                .placeholder(R.drawable.loading)
                .into(viewHolder.img_movie);
    }

    @Override
    public int getItemCount() {
        return movie.size();
    }

    //Handing onClick event in Recycler View
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title_movie;
        private ImageView img_movie;
        private TextView release_movie;
        private TextView popularity_movie;
        Context context;
        ArrayList<MovieInfo> movie = new ArrayList<MovieInfo>();
        public ViewHolder(View view,Context context,ArrayList<MovieInfo> movie) {
            super(view);

            this.movie = movie;
            this.context = context;
            view.setOnClickListener(this);

            title_movie = (TextView)view.findViewById(R.id.title_movie);
            img_movie = (ImageView) view.findViewById(R.id.img_movie);
            release_movie = (TextView)view.findViewById((R.id.release_movie));
            popularity_movie = (TextView)view.findViewById((R.id.popularity_movie));

        }

        @Override
        public void onClick(View v) {

            //Passing information to MovieDescription Activity onClick
            int pos = getAdapterPosition();
            MovieInfo movieInfo  = this.movie.get(pos);
            Intent intent = new Intent(this.context,MovieDescription.class);
            intent.putExtra("movie_poster",movieInfo.getPoster());
            intent.putExtra("movie_background",movieInfo.getBackground());
            intent.putExtra("movie_title",movieInfo.getTitle());
            intent.putExtra("movie_popularity",movieInfo.getPopularity());
            intent.putExtra("movie_rating",movieInfo.getRating());
            intent.putExtra("movie_release",movieInfo.getRelease());
            intent.putExtra("movie_description",movieInfo.getDescription());

            this.context.startActivity(intent);

        }
    }
}
