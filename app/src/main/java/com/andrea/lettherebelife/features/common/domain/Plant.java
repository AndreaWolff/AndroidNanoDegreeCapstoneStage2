package com.andrea.lettherebelife.features.common.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Plant implements Parcelable {

    private String name;
    private String seedDate;
    private String description;
    private String photoUrl;

    public Plant() {
    }

    public Plant(String name, String seedDate, String description, String photoUrl) {
        this.name = name;
        this.seedDate = seedDate;
        this.description = description;
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeedDate() {
        return seedDate;
    }

    public void setSeedDate(String seedDate) {
        this.seedDate = seedDate;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    protected Plant(Parcel in) {
        name = in.readString();
        seedDate = in.readString();
        description = in.readString();
        photoUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(seedDate);
        dest.writeString(description);
        dest.writeString(photoUrl);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Plant> CREATOR = new Parcelable.Creator<Plant>() {
        @Override
        public Plant createFromParcel(Parcel in) {
            return new Plant(in);
        }

        @Override
        public Plant[] newArray(int size) {
            return new Plant[size];
        }
    };
}
