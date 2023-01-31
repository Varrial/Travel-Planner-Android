package com.example.travelplanner;

public class EventModel {
    private int eventID;
    private String eventName;
    private String startTime;
    private String endTime;
    private String date;
    private int tripID;

    public EventModel(int eventID, String eventName, String startTime, String endTime, String date, int tripID) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.tripID = tripID;
    }

    public EventModel() {
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }
}

