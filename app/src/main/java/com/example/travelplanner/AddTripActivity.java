package com.example.travelplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class AddTripActivity extends AppCompatActivity {

    me.ibrahimsn.lib.SmoothBottomBar bottomBar;
    EditText city;
    EditText country;
    EditText startDate;
    EditText endDate;
    Switch isWork;
    Button addButton;

    List<LocationPhoto> locationPhotoList;
    int photoIndex = 0;

    TripModel tripModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        bottomBar = findViewById(R.id.bottomBar);
        city = findViewById(R.id.city);
        country = findViewById(R.id.country);
        startDate = findViewById(R.id.editStartDate);
        endDate = findViewById(R.id.editEndDate);
        isWork = findViewById(R.id.isWork);
        addButton = findViewById(R.id.addButton);

        Calendar calendar = Calendar.getInstance();

        bottomBar.setActiveItem(1);
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

        DatePickerDialog.OnDateSetListener start = new DatePickerDialog.OnDateSetListener() {
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

                startDate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddTripActivity.this, start, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        DatePickerDialog.OnDateSetListener end = new DatePickerDialog.OnDateSetListener() {
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

                endDate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddTripActivity.this, end, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                try {
                    tripModel = new TripModel(-1, city.getText().toString(), country.getText().toString(), startDate.getText().toString(), endDate.getText().toString(), isWork.isChecked(), -1);
                    Toast.makeText(AddTripActivity.this, tripModel.toString(), Toast.LENGTH_SHORT).show();
                    FlickrAPI flickrAPI = new FlickrAPI(AddTripActivity.this);
                    flickrAPI.fetchLocationPhotos(city.getText().toString(), country.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(AddTripActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    tripModel=new TripModel(-1,"","","","",false,-1);
                }

            }
        });
    }

    public void receivedLocationPhotos(List<LocationPhoto> locationPhotos) {
        this.locationPhotoList = locationPhotos;

        if (locationPhotos != null && locationPhotos.size() > 0) {
            LocationPhoto locationPhoto = locationPhotos.get(photoIndex);
            tripModel.setImageURL(locationPhoto.getPhotoURL());

            DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
            boolean b = dataBaseHelper.addOne(tripModel);
            Log.d("TravelPlannerTag", "Result: " + b + " " + tripModel.toString());

        }
    }
}