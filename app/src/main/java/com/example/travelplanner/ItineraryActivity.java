package com.example.travelplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

public class ItineraryActivity extends AppCompatActivity {

    ImageButton addEvent, goBack;
    EventAdapter eventsArrayAdapter;
    ListView eventsList;

    DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

    int tripID = 0;
    String place;
    String start;
    String end;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);

        Bundle extras = getIntent().getExtras();

        addEvent = findViewById(R.id.addEvent);
        goBack = findViewById(R.id.backEvent);
        eventsList = findViewById(R.id.mainEventsList);

        if (extras != null) {
            tripID = extras.getInt("tripID");
            place = extras.getString("city");
            start = extras.getString("startDate");
            end = extras.getString("endDate");
            Log.d("TravelPlannerTag", String.valueOf(tripID));
        }

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(addEvent.getContext(), AddEventActivity.class);
                intent.putExtra("tripID", tripID);
                intent.putExtra("city", place);
                intent.putExtra("startDate", start);
                intent.putExtra("endDate", end);
                startActivity(intent);
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(goBack.getContext(), OverviewActivity.class);
                intent.putExtra("tripID", tripID);
                intent.putExtra("city", place);
                intent.putExtra("startDate", start);
                intent.putExtra("endDate", end);
                startActivity(intent);
            }
        });
        showItemsOnListView(this, tripID);
    }

    public void showItemsOnListView(Context context, int tripID) {
        eventsArrayAdapter = new EventAdapter(context, dataBaseHelper.getEvents(tripID));
        eventsList.setAdapter(eventsArrayAdapter);
    }
}