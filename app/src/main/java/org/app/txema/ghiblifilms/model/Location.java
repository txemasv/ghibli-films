package org.app.txema.ghiblifilms.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Txema on 22/09/2017.
 */

public class Location implements Parcelable {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("climate")
    private String climate;

    @SerializedName("terrain")
    private String terrain;

    @SerializedName("surface_water")
    private String surfaceWater;

    public Location(String id, String name, String climate, String terrain, String surfaceWater) {
        this.id = id;
        this.name = name;
        this.climate = climate;
        this.terrain = terrain;
        this.surfaceWater = surfaceWater;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public String getSurfaceWater() {
        return surfaceWater;
    }

    public void setSurfaceWater(String surfaceWater) {
        this.surfaceWater = surfaceWater;
    }

    private String getPosterName() {
        return getThumbnailName();
    }

    public int getPoster(Context context) {
        return context.getResources().getIdentifier(getPosterName(), "drawable", context.getPackageName());
    }

    private String getThumbnailName() {
        //example: Laputa = location_laputa
        return "location_" + this.getName().toLowerCase()
                .replaceAll(" ", "_")
                .replaceAll("'", "")
                .replaceAll("\\.", "")
                .replaceAll("-", "_");
    }

    public int getThumbnail(Context context) {
        return context.getResources().getIdentifier(getThumbnailName(), "drawable", context.getPackageName());
    }

    public boolean isValid() {
        return id != null
                && name != null
                && climate != null
                && terrain != null
                && surfaceWater != null;
    }

    // *****************************************************
    // Parcelable functions to pass character object in an Intent
    // *****************************************************

    @Override
    public int describeContents() {
        return 0;
    }

    // Storing the character data to Parcel object
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(climate);
        dest.writeString(terrain);
        dest.writeString(surfaceWater);
    }

    /**
     * Retrieving Movie data from Parcel object
     * This constructor is invoked by the method createFromParcel(Parcel source) of
     * the object CREATOR
     **/
    private Location(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.climate = in.readString();
        this.terrain = in.readString();
        this.surfaceWater = in.readString();
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
