package com.nithinproject.android.themoviedatabasetmdb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/*Developed by Nithin John*/

//Getters and Setters for all information fetched from Json
public class MovieInfo {
    private String poster;
    private String title;
    private String popularity;
    private String release;
    private String description;
    private String rating;
    private String background;

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {

        //URL to images : value retrieved appended to this to fetch images
        this.poster = "https://image.tmdb.org/t/p/w500"+poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title.toUpperCase();
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {

        //Round up the popularity value for better UI Display
        popularity = String.valueOf(Math.round(Float.parseFloat(popularity)));
        this.popularity = "\t"+popularity;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {


        if(release != null && !release.isEmpty()){

            //Date conversion to display Release Date in "dd MMM yyyy" format
            SimpleDateFormat rel = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date date = null;
            try {
                date = rel.parse(release);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            SimpleDateFormat fmtOut = new SimpleDateFormat("dd MMM yyyy",Locale.ENGLISH);
            release = fmtOut.format(date);

        }

        this.release = "\t"+release;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {

        //Conversion of the rating value to Float so that: for example, "7" changes to "7.0"
        if(!Objects.equals(String.valueOf(rating), "10")){
            rating = String.valueOf(Float.parseFloat(String.valueOf(rating)));
        }
        this.rating = rating;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {

        //URL to images : value retrieved appended to this to fetch images
        this.background = "https://image.tmdb.org/t/p/w500"+background;
    }

}
