package com.example.travelplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class AddSpendingActivity extends AppCompatActivity {

    EditText spendingName, spendingCost, spendingDate;
    Button addButton;
    Spinner categorySpinner;

    SpendingModel spendingModel;
    Category category;

    int tripID = 0;
    String place;
    String start;
    String end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spending);

        Calendar calendar = Calendar.getInstance();

        Bundle extras = getIntent().getExtras();

        spendingName = findViewById(R.id.spendingName);
        spendingCost = findViewById(R.id.spendingCost);
        spendingDate = findViewById(R.id.spendingDate);
        categorySpinner = findViewById(R.id.spendingCategory);
        categorySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Category.values()));
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = Category.values()[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addButton = findViewById(R.id.addSpendingButton);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateCalendar();
            }

            private void updateCalendar() {
                String format = "dd/MM/yy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.ENGLISH);

                spendingDate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        spendingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddSpendingActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
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
                        spendingModel = new SpendingModel(-1, spendingName.getText().toString(), spendingCost.getText().toString(), category, spendingDate.getText().toString(), tripID);
                    }
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(addButton.getContext());
                    boolean b = dataBaseHelper.addSpending(spendingModel);
                    Log.d("TravelPlannerTag", "Result: " + b + " " + spendingModel.toString());
                } catch (Exception e) {
                    spendingModel = new SpendingModel(-1, "", "", category, "", -1);
                }

                Intent intent = new Intent(addButton.getContext(), BudgetActivity.class);
                intent.putExtra("tripID", tripID);
                intent.putExtra("city", place);
                intent.putExtra("startDate", start);
                intent.putExtra("endDate", end);
                startActivity(intent);

            }
        });
    }
}