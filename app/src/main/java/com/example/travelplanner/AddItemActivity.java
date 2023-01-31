package com.example.travelplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class AddItemActivity extends AppCompatActivity {

    me.ibrahimsn.lib.SmoothBottomBar bottomBar;
    EditText name;
    Switch isPacked;
    Button addButton;

    ItemModel itemModel;

    int tripID = 0;
    String place;
    String start;
    String end;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Bundle extras = getIntent().getExtras();

        bottomBar = findViewById(R.id.bottomBar);
        name = findViewById(R.id.name);
        isPacked = findViewById(R.id.isPacked);
        addButton = findViewById(R.id.addEventButton);

        bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelect(int i) {
                switch (i) {
                    case 0:
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(getApplicationContext(), AddTripActivity.class);
                        startActivity(intent1);
                        break;
                    default:
                        Intent intent2 = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent2);
                        break;
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    if (extras != null) {
                        tripID = extras.getInt("tripID");
                        place = extras.getString("city");
                        start = extras.getString("startDate");
                        end = extras.getString("endDate");
                        itemModel = new ItemModel(-1,name.getText().toString(),isPacked.isChecked(), tripID);
                    }
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(addButton.getContext());
                    boolean b = dataBaseHelper.addListItem(itemModel);
                    Log.d("TravelPlannerTag", "Result: " + b + " " + itemModel.toString());
                } catch (Exception e) {
                    itemModel=new ItemModel(-1,"",false,-1);
                }

                Intent intent = new Intent(addButton.getContext(), PackingListActivity.class);
                intent.putExtra("tripID", tripID);
                intent.putExtra("city", place);
                intent.putExtra("startDate", start);
                intent.putExtra("endDate", end);
                startActivity(intent);

            }
        });
    }

}