package com.example.travelplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class OverviewActivity extends AppCompatActivity {

    EditText editLocation;
    TextView startDate;
    TextView endDate;
    ImageButton goToEvents, goToBudget, goToList, backToMain;
    ItemsAdapter itemArrayAdapter;
    EventAdapter eventArrayAdapter;
    ListView itemsList, eventsList;
    TextView budgetInfo;

    DataBaseHelper dataBaseHelper = new DataBaseHelper(this);


    TripModel tripModel;

    int tripID = 0;
    int spendings = 0;
    String place;
    String start;
    String end;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        Bundle extras = getIntent().getExtras();

        editLocation = findViewById(R.id.editLocationText);
        startDate = findViewById(R.id.startDateDate);
        endDate = findViewById(R.id.endDateDate);
        goToList = findViewById(R.id.goToList);
        goToEvents = findViewById(R.id.goToEvents);
        goToBudget = findViewById(R.id.goToBudget);
        backToMain = findViewById(R.id.backToMain);
        itemsList = findViewById(R.id.packingList);
        eventsList = findViewById(R.id.eventsList);
        budgetInfo = findViewById(R.id.budgetInfo);

        if (extras != null) {
            tripID = extras.getInt("tripID");
            place = extras.getString("city");
            start = extras.getString("startDate");
            end = extras.getString("endDate");
            editLocation.setText(place);
            startDate.setText(start);
            endDate.setText(end);
        }

        List<SpendingModel> spendingModels = dataBaseHelper.getSpendings(tripID);

        for (int i=0; i<spendingModels.size(); i++) {
                spendings += Integer.parseInt(spendingModels.get(i).getSpendingCost());
        }

        budgetInfo.setText("$" + spendings);

        goToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TravelPlannerTag", String.valueOf(tripID));
                Intent intent = new Intent(goToList.getContext(), PackingListActivity.class);
                intent.putExtra("tripID", tripID);
                intent.putExtra("city", place);
                intent.putExtra("startDate", start);
                intent.putExtra("endDate", end);
                startActivity(intent);
            }
        });

        goToEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(goToEvents.getContext(), ItineraryActivity.class);
                intent.putExtra("tripID", tripID);
                intent.putExtra("city", place);
                intent.putExtra("startDate", start);
                intent.putExtra("endDate", end);
                startActivity(intent);
            }
        });
        goToBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(goToEvents.getContext(), BudgetActivity.class);
                intent.putExtra("tripID", tripID);
                intent.putExtra("city", place);
                intent.putExtra("startDate", start);
                intent.putExtra("endDate", end);
                startActivity(intent);
            }
        });


        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TravelPlannerTag", String.valueOf(tripID));
                Intent intent = new Intent(backToMain.getContext(), MainActivity.class);
                intent.putExtra("tripID", tripID);
                startActivity(intent);
            }
        });


        showItemsOnListView(this, tripID);
        showEventsOnListView(this, tripID);

    }

    public void showItemsOnListView(Context context, int tripID) {
        itemArrayAdapter = new ItemsAdapter(context, dataBaseHelper.getList(tripID));
        itemsList.setAdapter(itemArrayAdapter);
    }

    public void showEventsOnListView(Context context, int tripID) {
        eventArrayAdapter = new EventAdapter(context, dataBaseHelper.getEvents(tripID));
        eventsList.setAdapter(eventArrayAdapter);
    }
}