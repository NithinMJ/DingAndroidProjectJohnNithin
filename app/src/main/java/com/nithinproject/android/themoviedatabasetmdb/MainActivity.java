package com.nithinproject.android.themoviedatabasetmdb;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.nithinproject.android.themoviedatabasetmdb.R.id.fab;

/*Developed by Nithin John*/

public class MainActivity extends AppCompatActivity{

    static String API = "33eaa774c3c9d738dbede19862118d3d";  // Set API variable to the API-key
    private ArrayList<MovieInfo> movieInfos;
    private DataAdapter adapter;
    int pageNumber=1;
    long lastPress;
    Toast backpressToast;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialPage();  //Method Call to initialise
        new FetchMoviesJson().execute(pageNumber);  //Call AsyncTask


        //FloatButton : OnClick move to top and disappear
        final FloatingActionButton floatButton = (FloatingActionButton) findViewById(fab);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.scrollToPosition(0);
                floatButton.hide();
            }
        });

        //Pagination - Updates page number on scroll after page 1
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy>0){
                    pageNumber++;
                    new FetchMoviesJson().execute(pageNumber);
                    floatButton.show();
                }

                else if(dy<0){
                    System.out.print("No More pages");
                }


            }
        });

    }

    // Initialisation
    private void initialPage(){
        recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        movieInfos = new ArrayList<>();
        adapter = new DataAdapter(this,movieInfos);
        recyclerView.setAdapter(adapter);
    }

    //Double tap on back button to exit
    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastPress > 5000){
            backpressToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_LONG);
            backpressToast.show();
            lastPress = currentTime;

        } else {
            if (backpressToast != null) backpressToast.cancel();
            super.onBackPressed();
            //Exit Application on back press instead of moving to the first Activity
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    //Json Parsing and Data Fetch from the Json information
    private class FetchMoviesJson extends AsyncTask<Integer,Void,Void>{

        private String myMovieString;
        @Override
        protected Void doInBackground(Integer... params) {
            int pos = params[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            InputStream inputStream;
            Uri api_uri = Uri.parse("https://api.themoviedb.org/3/movie/popular?api_key="+API+"&page="+pos);
            URL url;

            try{
                url = new URL(api_uri.toString());
                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                int responseCode = urlConnection.getResponseCode();
                urlConnection.connect();

                //Report Error if a bad request is attempted : Else extract information
                if (responseCode >= HttpURLConnection.HTTP_BAD_REQUEST){
                    inputStream = urlConnection.getErrorStream();
                }
                else{
                    inputStream = urlConnection.getInputStream();
                }
                StringBuffer buffer = new StringBuffer();
                if(inputStream == null){
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while((line = reader.readLine())!= null){
                    buffer.append(line+"\n");
                }

                if(buffer.length() == 0){
                    return null;
                }

                myMovieString = buffer.toString(); //Json content assigned to the variable myMovieString

                JSONObject jsonObject = new JSONObject(myMovieString); //Create Json object

                JSONArray myMovies = jsonObject.getJSONArray("results"); //Fetch results array inside Json Object

                //Loop through and set respective required information
                for(int i =0;i<myMovies.length();i++){

                    String poster;
                    String title;
                    String popularity;
                    String release;
                    String rating;
                    String description;
                    String background;

                    JSONObject movies_list = (JSONObject) myMovies.get(i);
                    poster = movies_list.getString("poster_path");
                    title = movies_list.getString("title");
                    popularity = movies_list.getString("popularity");
                    release = movies_list.getString("release_date");
                    rating = movies_list.getString("vote_average");
                    description = movies_list.getString("overview");
                    background = movies_list.getString("backdrop_path");

                    MovieInfo movie_info = new MovieInfo();
                    movie_info.setPoster(poster);
                    movie_info.setTitle(title);
                    movie_info.setPopularity(popularity);
                    movie_info.setRelease(release);
                    movie_info.setRating(rating);
                    movie_info.setDescription(description);
                    movie_info.setBackground(background);
                    movieInfos.add(movie_info);

                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
                if(urlConnection != null){
                    urlConnection.disconnect();
                }
                if(reader != null){
                    try{
                        reader.close();
                    }catch (final IOException e){
                        Log.e("MainActivity","Error",e);
                    }
                }
            }
            return  null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {

            adapter.notifyDataSetChanged();

        }
    }



}
