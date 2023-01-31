package com.example.travelplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

public class PackingListActivity extends AppCompatActivity {

    ImageButton addItem, goBack;
    ItemsAdapter itemArrayAdapter;
    ListView itemsList;

    DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

    int tripID = 0;
    String place;
    String start;
    String end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packing_list);

        Bundle extras = getIntent().getExtras();

        addItem=findViewById(R.id.addItem);
        goBack = findViewById(R.id.backList);
        itemsList = findViewById(R.id.mainPackList);

        if (extras != null) {
            tripID = extras.getInt("tripID");
            place = extras.getString("city");
            start = extras.getString("startDate");
            end = extras.getString("endDate");
            Log.d("TravelPlannerTag", String.valueOf(tripID));
        }

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(addItem.getContext(), AddItemActivity.class);
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
        itemArrayAdapter = new ItemsAdapter(context, dataBaseHelper.getList(tripID));
        itemsList.setAdapter(itemArrayAdapter);
    }
}