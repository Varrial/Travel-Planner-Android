package com.example.travelplanner;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

public class TripModel {
    private int tripID;
    private String city;
    private String country;
    private String startDate;
    private String endDate;
    private int budget;
    private boolean isWork;
    private String imageURL;
    private int userID;


    public TripModel(int tripID, String city, String country, String startDate, String endDate, boolean isWork, int userID) {
        this.tripID = tripID;
        this.city = city;
        this.country = country;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isWork = isWork;
        this.userID = userID;

    }

    public TripModel(int tripID, String city, String country, String startDate, String endDate, boolean isWork, String imageURL, int userID) {
        this.tripID = tripID;
        this.city = city;
        this.country = country;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isWork = isWork;
        this.imageURL = imageURL;
        this.userID = userID;
    }

    public TripModel() {
    }

    @Override
    public String toString() {
        return "TripModel{" +
                "tripID=" + tripID +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", budget=" + budget +
                ", isWork=" + isWork +
                ", imageURL='" + imageURL + '\'' +
                ", userID=" + userID +
                '}';
    }

    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isWork() {
        return isWork;
    }

    public void setWork(boolean work) {
        isWork = work;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
}
