package com.example.hitesh.project1_1;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hitesh on 9/16/15.
 */

public class MovieDetails {
    String movieId;
    private HashMap<String, ArrayList<Movie>> moviesListLookup;
    private List<Movie> movies_default;
    private List<Movie> movies_ratings;
    private List<Movie> movies_votes;

    private String sortKey;

    public MovieDetails(String defaultData, String ratingsData, String votesData) {
        this.moviesListLookup = new HashMap<String, ArrayList<Movie>>();
        try {
            this.moviesListLookup.put("default", this.setMovies(defaultData));
            this.moviesListLookup.put("ratings", this.setMovies(ratingsData));
            this.moviesListLookup.put("votes", this.setMovies(votesData));
        }catch (JSONException e){

        }

    }
    public ArrayList<Movie> setMovies(String responseData)
            throws JSONException {
        ArrayList<Movie> moviesList = new ArrayList<Movie>();
        JSONObject respJson = new JSONObject(responseData);
        JSONArray movies = respJson.getJSONArray("results");
        String[] imgurls = new String[movies.length()];
        for(int i=0; i < movies.length(); ++i){
            Movie movie = new Movie( Boolean.parseBoolean(movies.getJSONObject(i).getString("adult")),
                        movies.getJSONObject(i).getString("id"),
                        movies.getJSONObject(i).getString("backdrop_path"),
                        movies.getJSONObject(i).getString("original_title"),
                        movies.getJSONObject(i).getString("overview"),
                        Double.parseDouble(movies.getJSONObject(i).getString("popularity")),
                        movies.getJSONObject(i).getString("poster_path"),
                        movies.getJSONObject(i).getString("release_date"),
                        movies.getJSONObject(i).getString("title"),
                        Double.parseDouble(movies.getJSONObject(i).getString("vote_average")),
                        Integer.parseInt(movies.getJSONObject(i).getString("vote_count"))
            );
            moviesList.add( movie );
            imgurls[i] = "http://image.tmdb.org/t/p/w185//"+movies.getJSONObject(i).getString("backdrop_path");

        }
        return moviesList;

    }

    private MovieDetails(Parcel in){
        movieId = in.readString();
    }
    public String toString(){
        return this.movieId;
    }

    public List<Movie> getMovies_default() {
        return movies_default;
    }

    public List<Movie> getMovies_ratings() {
        return movies_ratings;
    }

    public List<Movie> getMovies_votes() {
        return movies_votes;
    }

    public HashMap<String, ArrayList<Movie>> getMoviesListLookup() {
        return moviesListLookup;
    }

}
