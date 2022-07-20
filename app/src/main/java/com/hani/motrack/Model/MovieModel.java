package com.hani.motrack.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class MovieModel implements Parcelable {

    public MovieModel() {
    }


    private int id;
    private String Title;
    private String Year;
    private String imdbID;
    private String Type;
    private String Poster;

    protected MovieModel(Parcel in) {
        id = in.readInt();
        Title = in.readString();
        Year = in.readString();
        imdbID = in.readString();
        Type = in.readString();
        Poster = in.readString();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(Title);
        parcel.writeString(Year);
        parcel.writeString(imdbID);
        parcel.writeString(Type);
        parcel.writeString(Poster);
    }

    public static final class KEY {
        public static final String ID_KEY = "id";
        public static final String TITLE_KEY = "Title";
        public static final String YEAR_KEY = "Year";
        public static final String IMDBID_KEY = "imdbID";
        public static final String TYPE_KEY = "Type";
        public static final String POSTER_URL_KEY = "Poster";

    }


    public int getId() {
        return id;
    }

    public String getTitle() {
        return Title;
    }

    public String getYear() {
        return Year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getType() {
        return Type;
    }

    public String getPoster() {
        return Poster;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public void setYear(String year) {
        this.Year = year;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public void setPoster(String poster) {
        this.Poster = poster;
    }

}
