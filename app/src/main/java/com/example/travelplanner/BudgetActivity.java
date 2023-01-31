package com.example.travelplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class BudgetActivity extends AppCompatActivity {

    ImageButton addSpending, goBack;
    TextView spendingsAmount, spendingsFood, spendingsAccomodation, spendingsShopping, spendingsTravel, spendingsOther;
    int spendings = 0, food = 0, accomodation = 0, shopping = 0, travel = 0, other = 0;

    DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

    int tripID = 0;
    String place;
    String start;
    String end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        Bundle extras = getIntent().getExtras();

        addSpending = findViewById(R.id.addSpending);
        goBack = findViewById(R.id.backBudget);
        spendingsAmount = findViewById(R.id.spendingsAmount);
        spendingsFood = findViewById(R.id.spendingsFood);
        spendingsAccomodation = findViewById(R.id.spendingsAccomodation);
        spendingsShopping = findViewById(R.id.spendingsShopping);
        spendingsTravel = findViewById(R.id.spendingsTravel);
        spendingsOther = findViewById(R.id.spendingsOther);


        if (extras != null) {
            tripID = extras.getInt("tripID");
            place = extras.getString("city");
            start = extras.getString("startDate");
            end = extras.getString("endDate");
            Log.d("TravelPlannerTag", String.valueOf(tripID));
        }

        List<SpendingModel> spendingModels = dataBaseHelper.getSpendings(tripID);

        for (int i=0; i<spendingModels.size(); i++) {
            spendings += Integer.parseInt(spendingModels.get(i).getSpendingCost());

            if(spendingModels.get(i).getSpendingCategory() == Category.FOOD) {
                food += Integer.parseInt(spendingModels.get(i).getSpendingCost());
            }
            if(spendingModels.get(i).getSpendingCategory() == Category.ACCOMODATION) {
                accomodation += Integer.parseInt(spendingModels.get(i).getSpendingCost());
            }
            if(spendingModels.get(i).getSpendingCategory() == Category.SHOPPING) {
                shopping += Integer.parseInt(spendingModels.get(i).getSpendingCost());
            }
            if(spendingModels.get(i).getSpendingCategory() == Category.TRAVEL) {
                travel += Integer.parseInt(spendingModels.get(i).getSpendingCost());
            }
            if(spendingModels.get(i).getSpendingCategory() == Category.OTHER) {
                other += Integer.parseInt(spendingModels.get(i).getSpendingCost());
            }

        }

        spendingsAmount.setText("$" + spendings);
        spendingsFood.setText("$" + food);
        spendingsAccomodation.setText("$" + accomodation);
        spendingsShopping.setText("$" + shopping);
        spendingsTravel.setText("$" + travel);
        spendingsOther.setText("$" + other);


        addSpending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(addSpending.getContext(), AddSpendingActivity.class);
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
    }
}