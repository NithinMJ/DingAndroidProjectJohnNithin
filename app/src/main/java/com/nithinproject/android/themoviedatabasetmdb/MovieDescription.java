package com.nithinproject.android.themoviedatabasetmdb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.nithinproject.android.themoviedatabasetmdb.R.id.movie_description;

/*Developed by Nithin John*/

public class MovieDescription extends AppCompatActivity {

    ImageView poster;
    ImageView thumbnail;
    TextView title;
    TextView description;
    TextView release;
    TextView popularity;
    TextView rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_description);

        //Initialize UI : Views
        poster = (ImageView)findViewById(R.id.movie_backdrop);
        thumbnail = (ImageView)findViewById(R.id.movie_poster);
        title = (TextView) findViewById(R.id.movie_title);
        description = (TextView)findViewById(movie_description);
        release = (TextView)findViewById(R.id.movie_release);
        popularity = (TextView)findViewById(R.id.movie_popularity);
        rating = (TextView)findViewById(R.id.movie_rating);

        //Set Relevant Data to each view
        String strBackdrop = String.valueOf(getIntent().getStringExtra("movie_background"));
        Picasso.with(this)
                .load(strBackdrop)
                .into(poster);

        String strThumbnail = String.valueOf(getIntent().getStringExtra("movie_poster"));
        Picasso.with(this)
                .load(strThumbnail)
                .into(thumbnail);
        title.setText(getIntent().getStringExtra("movie_title"));
        description.setText(getIntent().getStringExtra("movie_description"));
        release.setText(getIntent().getStringExtra("movie_release"));
        popularity.setText(getIntent().getStringExtra("movie_popularity"));
        rating.setText(getIntent().getStringExtra("movie_rating"));

    }




}
