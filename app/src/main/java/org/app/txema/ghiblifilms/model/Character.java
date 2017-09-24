package org.app.txema.ghiblifilms.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Txema on 22/09/2017.
 */

public class Character implements Parcelable {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("gender")
    private String gender;

    @SerializedName("age")
    private String age;

    @SerializedName("eye_color")
    private String eyeColor;

    @SerializedName("hair_color")
    private String hairColor;

    public Character(String id, String name, String gender, String age, String eyeColor, String haiColor) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.eyeColor = eyeColor;
        this.hairColor = haiColor;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    private String getPosterName() {
        return getThumbnailName();
    }

    public int getPoster(Context context) {
        return context.getResources().getIdentifier(getPosterName(), "drawable", context.getPackageName());
    }

    private String getThumbnailName() {
        //example: totoro = portrait_totoro
        return "portrait_" + this.getName().toLowerCase().replaceAll(" ", "_").replaceAll("'", "").replaceAll("-", "_");
    }

    public int getThumbnail(Context context) {
        return context.getResources().getIdentifier(getThumbnailName(), "drawable", context.getPackageName());
    }

    public boolean isValid() {
        return id != null
                && name != null
                && gender != null
                && age != null
                && eyeColor != null
                && hairColor != null;
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
        dest.writeString(gender);
        dest.writeString(age);
        dest.writeString(eyeColor);
        dest.writeString(hairColor);
    }

    /**
     * Retrieving Movie data from Parcel object
     * This constructor is invoked by the method createFromParcel(Parcel source) of
     * the object CREATOR
     **/
    private Character(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.gender = in.readString();
        this.age = in.readString();
        this.eyeColor = in.readString();
        this.hairColor = in.readString();
    }

    public static final Parcelable.Creator<Character> CREATOR = new Parcelable.Creator<Character>() {
        @Override
        public Character createFromParcel(Parcel source) {
            return new Character(source);
        }

        @Override
        public Character[] newArray(int size) {
            return new Character[size];
        }
    };
}
