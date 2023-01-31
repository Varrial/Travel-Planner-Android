package com.example.travelplanner;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String TRIP_TABLE = "TRIP_TABLE";
    public static final String COLUMN_TRIP_ID = "TRIP_ID";
    public static final String COLUMN_CITY = "CITY";
    public static final String COLUMN_COUNTRY = "COUNTRY";
    public static final String COLUMN_START_DATE = "START_DATE";
    public static final String COLUMN_END_DATE = "END_DATE";
    public static final String COLUMN_BUDGET = "BUDGET";
    public static final String COLUMN_IS_WORK = "IS_WORK";
    public static final String COLUMN_IMAGE_URL = "IMAGE_URL";
    public static final String COLUMN_USER_ID = "USER_ID";
    public static final String ITEM_TABLE = "ITEM_TABLE";
    public static final String COLUMN_ITEM_ID = "ITEM_ID";
    public static final String COLUMN_ITEM_NAME = "ITEM_NAME";
    public static final String COLUMN_IS_PACKED = "IS_PACKED";
    public static final String EVENT_TABLE = "EVENT_TABLE";
    public static final String COLUMN_EVENT_ID = "EVENT_ID";
    public static final String COLUMN_EVENT_NAME = "EVENT_NAME";
    public static final String COLUMN_EVENT_START_TIME = "EVENT_START_TIME";
    public static final String COLUMN_EVENT_END_TIME = "EVENT_END_TIME";
    public static final String COLUMN_EVENT_DATE = "EVENT_DATE";
    public static final String SPENDING_TABLE = "SPENDING_TABLE";
    public static final String COLUMN_SPENDING_ID = "SPENDING_ID";
    public static final String COLUMN_SPENDING_NAME = "SPENDING_NAME";
    public static final String COLUMN_SPENDING_COST = "SPENDING_COST";
    public static final String COLUMN_SPENDING_CATEGORY = "SPENDING_CATEGORY";
    public static final String COLUMN_SPENDING_DATE = "SPENDING_DATE";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "travelPlanner.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTripTableStatement = "CREATE TABLE " + TRIP_TABLE + " (" + COLUMN_TRIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CITY + " TEXT, " + COLUMN_COUNTRY + " TEXT, " + COLUMN_START_DATE + " TEXT, " + COLUMN_END_DATE + " TEXT, " + COLUMN_BUDGET + " INT, " + COLUMN_IS_WORK + " BOOL, " + COLUMN_IMAGE_URL + " TEXT, " + COLUMN_USER_ID + " INT)";
        sqLiteDatabase.execSQL(createTripTableStatement);

        String createItemTableStatement = "CREATE TABLE " + ITEM_TABLE + " (" + COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_ITEM_NAME + " TEXT, " + COLUMN_IS_PACKED + " BOOL, " + COLUMN_TRIP_ID + " INTEGER, FOREIGN KEY(" + COLUMN_TRIP_ID + ") REFERENCES "+ TRIP_TABLE +"(" + COLUMN_TRIP_ID + "))";
        sqLiteDatabase.execSQL(createItemTableStatement);

        String createEventTableStatement = "CREATE TABLE " + EVENT_TABLE + " (" + COLUMN_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EVENT_NAME + " TEXT, " + COLUMN_EVENT_START_TIME + " TEXT, " + COLUMN_EVENT_END_TIME + " TEXT, " + COLUMN_EVENT_DATE + " TEXT, " + COLUMN_TRIP_ID + " INTEGER, FOREIGN KEY(" + COLUMN_TRIP_ID + ") REFERENCES "+ TRIP_TABLE +"(" + COLUMN_TRIP_ID + "))";
        sqLiteDatabase.execSQL(createEventTableStatement);

        String createSpendingTableStatement = "CREATE TABLE " + SPENDING_TABLE + " (" + COLUMN_SPENDING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_SPENDING_NAME + " TEXT, " + COLUMN_SPENDING_COST + " TEXT, " + COLUMN_SPENDING_CATEGORY + " TEXT, " + COLUMN_SPENDING_DATE + " TEXT, " + COLUMN_TRIP_ID + " INTEGER, FOREIGN KEY(" + COLUMN_TRIP_ID + ") REFERENCES "+ TRIP_TABLE +"(" + COLUMN_TRIP_ID + "))";
        sqLiteDatabase.execSQL(createSpendingTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(TripModel tripModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CITY, tripModel.getCity());
        cv.put(COLUMN_COUNTRY, tripModel.getCountry());
        cv.put(COLUMN_START_DATE, tripModel.getStartDate());
        cv.put(COLUMN_END_DATE, tripModel.getEndDate());
        cv.put(COLUMN_IS_WORK, tripModel.isWork());
        cv.put(COLUMN_IMAGE_URL, tripModel.getImageURL());
        cv.put(COLUMN_USER_ID, tripModel.getUserID());
        long insert = db.insert(TRIP_TABLE, null, cv);

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteOne(TripModel tripModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + TRIP_TABLE + " WHERE " + COLUMN_TRIP_ID + " = " + tripModel.getTripID();

        Cursor cursor = db.rawQuery(queryString, null);



        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public List<TripModel> getEverything() {
        List<TripModel> list = new ArrayList<>();

        String queryString = "SELECT * FROM " + TRIP_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            do {
                int tripID = cursor.getInt(0);
                String city = cursor.getString(1);
                String country = cursor.getString(2);
                String startDate = cursor.getString(3);
                String endDate = cursor.getString(4);
                boolean isWork = cursor.getInt(5) == 1;
                String imageURL = cursor.getString(6);
                int userID = cursor.getInt(7);

                TripModel tripModel = new TripModel(tripID, city, country, startDate, endDate, isWork, imageURL, userID);
                list.add(tripModel);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return list;
    }

    public boolean updateData(ItemModel itemModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ITEM_ID,itemModel.getItemID());
        cv.put(COLUMN_ITEM_NAME, itemModel.getName());
        cv.put(COLUMN_IS_PACKED, itemModel.isPacked());
        cv.put(COLUMN_TRIP_ID, itemModel.getTripID());

        db.update(ITEM_TABLE, cv, "ITEM_ID = ?", new String[] {String.valueOf(itemModel.getItemID())});
        return true;
    }

    public TripModel getOneTrip(int tripID) {
        TripModel tripModel = new TripModel();
        String queryString = "SELECT * FROM " + TRIP_TABLE + " WHERE " + COLUMN_TRIP_ID + " = " + tripID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        tripModel.setTripID(cursor.getInt(0));
        tripModel.setCity(cursor.getString(1));
        tripModel.setCountry(cursor.getString(2));
        tripModel.setStartDate(cursor.getString(3));
        tripModel.setEndDate(cursor.getString(4));
        tripModel.setWork(cursor.getInt(5) == 1);
        tripModel.setImageURL(cursor.getString(6));
        tripModel.setUserID(cursor.getInt(7));



        Log.d("TravelPlannerTag", String.valueOf(tripModel));


        cursor.close();
        db.close();

        return tripModel;
    }

    public boolean addListItem(ItemModel itemModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ITEM_NAME, itemModel.getName());
        cv.put(COLUMN_IS_PACKED, itemModel.isPacked());
        cv.put(COLUMN_TRIP_ID, itemModel.getTripID());

        long insert = db.insert(ITEM_TABLE, null, cv);

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteListItem(ItemModel itemModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + ITEM_TABLE + " WHERE " + COLUMN_ITEM_ID + " = " + itemModel.getItemID();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public List<ItemModel> getList(int tripID) {
        List<ItemModel> list = new ArrayList<>();

        String queryString = "SELECT * FROM " + ITEM_TABLE + " WHERE " + COLUMN_TRIP_ID + "=" + tripID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            do {
                int userId = cursor.getInt(0);
                String name = cursor.getString(1);
                boolean isPacked = cursor.getInt(2) == 1;
                int tripId = cursor.getInt(3);


                ItemModel itemModel = new ItemModel(userId, name, isPacked, tripId);
                list.add(itemModel);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return list;
    }

    public boolean addEvent(EventModel eventModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EVENT_NAME, eventModel.getEventName());
        cv.put(COLUMN_EVENT_START_TIME, eventModel.getStartTime());
        cv.put(COLUMN_EVENT_END_TIME, eventModel.getEndTime());
        cv.put(COLUMN_EVENT_DATE, eventModel.getDate());
        cv.put(COLUMN_TRIP_ID, eventModel.getTripID());

        long insert = db.insert(EVENT_TABLE, null, cv);

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteEvent(EventModel eventModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + EVENT_TABLE + " WHERE " + COLUMN_EVENT_ID + " = " + eventModel.getEventID();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public List<EventModel> getEvents(int tripID) {
        List<EventModel> list = new ArrayList<>();

        String queryString = "SELECT * FROM " + EVENT_TABLE + " WHERE " + COLUMN_TRIP_ID + "=" + tripID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            do {
                int eventID = cursor.getInt(0);
                String eventName = cursor.getString(1);
                String startTime = cursor.getString(2);
                String endTime = cursor.getString(3);
                String date = cursor.getString(4);
                int tripId = cursor.getInt(5);

                EventModel eventModel = new EventModel(eventID, eventName, startTime, endTime, date, tripID);
                list.add(eventModel);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return list;
    }

    public boolean addSpending(SpendingModel spendingModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SPENDING_NAME, spendingModel.getSpendingName());
        cv.put(COLUMN_SPENDING_COST, spendingModel.getSpendingCost());
        cv.put(COLUMN_SPENDING_CATEGORY, spendingModel.getSpendingCategory().toString());
        cv.put(COLUMN_SPENDING_DATE, spendingModel.getSpendingDate());
        cv.put(COLUMN_TRIP_ID, spendingModel.getTripID());

        long insert = db.insert(SPENDING_TABLE, null, cv);

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteSpending(SpendingModel spendingModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + SPENDING_TABLE + " WHERE " + COLUMN_SPENDING_ID + " = " + spendingModel.getSpendingID();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public List<SpendingModel> getSpendings(int tripID) {
        List<SpendingModel> list = new ArrayList<>();

        String queryString = "SELECT * FROM " + SPENDING_TABLE + " WHERE " + COLUMN_TRIP_ID + "=" + tripID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            do {
                int spendingID = cursor.getInt(0);
                String spendingName = cursor.getString(1);
                String spendingCost = cursor.getString(2);
                Category spendingCategory = Category.valueOf(cursor.getString(3));
                String spendingDate = cursor.getString(4);
                int tripId = cursor.getInt(5);

                SpendingModel spendingModel = new SpendingModel(spendingID, spendingName, spendingCost, spendingCategory, spendingDate, tripId);
                list.add(spendingModel);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return list;
    }

    public List<SpendingModel> getSpendingsByCategory(int tripID, Category category) {
        List<SpendingModel> list = new ArrayList<>();

        String queryString = "SELECT * FROM " + SPENDING_TABLE + " WHERE " + COLUMN_TRIP_ID + "=" + tripID + " WHERE " + COLUMN_SPENDING_CATEGORY + " = " + category.toString();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            do {
                int spendingID = cursor.getInt(0);
                String spendingName = cursor.getString(1);
                String spendingCost = cursor.getString(2);
                Category spendingCategory = Category.valueOf(cursor.getString(3));
                String spendingDate = cursor.getString(4);
                int tripId = cursor.getInt(5);

                SpendingModel spendingModel = new SpendingModel(spendingID, spendingName, spendingCost, spendingCategory, spendingDate, tripId);
                list.add(spendingModel);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return list;
    }
}
