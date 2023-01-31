package com.example.travelplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class AddEventActivity extends AppCompatActivity {

    EditText name, eventStartTime, eventEndTime, eventDate;
    Button addButton;

    EventModel eventModel;

    int tripID = 0;
    String place;
    String start1;
    String end1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Bundle extras = getIntent().getExtras();

        Calendar calendar = Calendar.getInstance();

        name = findViewById(R.id.event);
        eventStartTime = findViewById(R.id.event_start);
        eventEndTime = findViewById(R.id.event_end);
        eventDate = findViewById(R.id.eventDate);
        addButton = findViewById(R.id.addEventButton);

        TimePickerDialog.OnTimeSetListener start = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                calendar.set(Calendar.HOUR, hour);
                calendar.set(Calendar.MINUTE, minute);
                eventStartTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }

        };

        TimePickerDialog.OnTimeSetListener end = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                calendar.set(Calendar.HOUR, hour);
                calendar.set(Calendar.MINUTE, minute);
                eventEndTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }

        };

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

                eventDate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        eventStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(AddEventActivity.this, start, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true).show();
            }
        });

        eventEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(AddEventActivity.this, end, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true).show();
            }
        });

        eventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddEventActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    if (extras != null) {
                        tripID = extras.getInt("tripID");
                        place = extras.getString("city");
                        start1 = extras.getString("startDate");
                        end1 = extras.getString("endDate");
                        eventModel = new EventModel(-1, name.getText().toString(), eventStartTime.getText().toString(), eventEndTime.getText().toString(), eventDate.getText().toString(), tripID);
                    }
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(addButton.getContext());
                    boolean b = dataBaseHelper.addEvent(eventModel);
                    Log.d("TravelPlannerTag", "Result: " + b + " " + eventModel.toString());
                } catch (Exception e) {
                    eventModel = new EventModel(-1, "", "", "", "", -1);
                }

                Intent intent = new Intent(addButton.getContext(), ItineraryActivity.class);
                intent.putExtra("tripID", tripID);
                intent.putExtra("city", place);
                intent.putExtra("startDate", start1);
                intent.putExtra("endDate", end1);
                startActivity(intent);

            }
        });
    }
}