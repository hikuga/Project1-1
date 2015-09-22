package com.example.hitesh.project1_1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hitesh on 9/22/15.
 */
public class Movie implements Parcelable {
    private String adult;
    private String id;
    private String backdrop_path;
    private String original_title;
    private String overview;
    private Double popularity;
    private String poster_path;
    private String release_date;
    private String title;
    private Double vote_average;
    private int vote_count;

    public Movie(String adult, String id, String backdrop_path, String original_title, String overview,
                 Double popularity, String poster_path, String release_date, String title, Double vote_average,
                 int vote_count) {
        this.adult = adult;
        this.id = id;
        this.backdrop_path = backdrop_path;
        this.original_title = original_title;
        this.overview = overview;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.title = title;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }

    private Movie(Parcel in) {
        this.adult = in.readString();
        this.id = in.readString();
    }


    public String getImageUrl() {
        return "http://image.tmdb.org/t/p/w185//" + this.backdrop_path;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public Double getPopularity() {
        return popularity;
    }

    public Boolean getAdult() {
        return Boolean.parseBoolean(this.adult);
    }

    public String getId() {
        return id;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getTitle() {
        return title;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.adult);
        parcel.writeString(this.id);
    }

    public final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };
}
