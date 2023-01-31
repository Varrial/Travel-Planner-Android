package com.example.travelplanner;

public class LocationPhoto {
    String id;
    String photoURL;

    public LocationPhoto(String id, String photoURL) {
        this.id = id;
        this.photoURL = photoURL;
    }

    @Override
    public String toString() {
        return "LocationPhoto{" +
                "id='" + id + '\'' +
                ", photoURL='" + photoURL + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}
