package com.example.travelplanner;

public class SpendingModel {
    private int spendingID;
    private String spendingName;
    private String spendingCost;
    private Category spendingCategory;
    private String spendingDate;
    private int tripID;

    public SpendingModel(int spendingID, String spendingName, String spendingCost, Category spendingCategory, String spendingDate, int tripID) {
        this.spendingID = spendingID;
        this.spendingName = spendingName;
        this.spendingCost = spendingCost;
        this.spendingCategory = spendingCategory;
        this.spendingDate = spendingDate;
        this.tripID = tripID;
    }

    public SpendingModel() {
    }

    public int getSpendingID() {
        return spendingID;
    }

    public void setSpendingID(int spendingID) {
        this.spendingID = spendingID;
    }

    public String getSpendingName() {
        return spendingName;
    }

    public void setSpendingName(String spendingName) {
        this.spendingName = spendingName;
    }

    public String getSpendingCost() {
        return spendingCost;
    }

    public void setSpendingCost(String spendingCost) {
        this.spendingCost = spendingCost;
    }

    public Category getSpendingCategory() {
        return spendingCategory;
    }

    public void setSpendingCategory(Category spendingCategory) {
        this.spendingCategory = spendingCategory;
    }

    public String getSpendingDate() {
        return spendingDate;
    }

    public void setSpendingDate(String spendingDate) {
        this.spendingDate = spendingDate;
    }

    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }
}