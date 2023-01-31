package com.example.travelplanner;

public class ItemModel {
    private int itemID;
    private String name;
    private boolean isPacked;
    private int tripID;

    public ItemModel(int itemID, String name, boolean isPacked, int tripID) {
        this.itemID = itemID;
        this.name = name;
        this.isPacked = isPacked;
        this.tripID = tripID;
    }

    public ItemModel(String name) {
        this.name = name;
    }

    public ItemModel() {
    }

    @Override
    public String toString() {
        return name;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPacked() {
        return isPacked;
    }

    public void setPacked(boolean packed) {
        isPacked = packed;
    }

    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }
}
