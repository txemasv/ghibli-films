package org.app.txema.ghiblifilms.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Txema on 20/06/2017.
 */

public class Film implements Parcelable {
    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("director")
    private String director;

    @SerializedName("producer")
    private String producer;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("rt_score")
    private String score;

    public Film(String id, String title, String description, String director, String producer, String releaseDate, String score) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.director = director;
        this.producer = producer;
        this.releaseDate = releaseDate;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    private String getPosterName() {
        //The name of the image resource is the
        //name of the film in lowercase and changing spaces by _
        //example: My Neighbor Totoro = my_neighbor_totoro
        return this.getTitle().toLowerCase().replaceAll(" ", "_").replaceAll("'", "");
    }

    public int getPoster(Context context) {
        return context.getResources().getIdentifier(getPosterName(), "drawable", context.getPackageName());
    }

    public boolean isValid() {
        return id != null
                && title != null
                && description != null
                && director != null
                && producer != null
                && releaseDate != null
                && score != null;
    }

    // *****************************************************
    // Parcelable functions to pass Film object in an Intent
    // *****************************************************

    @Override
    public int describeContents() {
        return 0;
    }

    // Storing the Film data to Parcel object
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(director);
        dest.writeString(producer);
        dest.writeString(releaseDate);
        dest.writeString(score);
    }

    /**
     * Retrieving Movie data from Parcel object
     * This constructor is invoked by the method createFromParcel(Parcel source) of
     * the object CREATOR
     **/
    private Film(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.director = in.readString();
        this.producer = in.readString();
        this.releaseDate = in.readString();
        this.score = in.readString();
    }

    public static final Parcelable.Creator<Film> CREATOR = new Parcelable.Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel source) {
            return new Film(source);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };
}
