package com.example.travelplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity implements SensorEventListener {


    ViewPager2 locationsViewPager;
    me.ibrahimsn.lib.SmoothBottomBar bottomBar;
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private boolean isAccelerometerSensorAvailable;
    private float currentX, currentY, currentZ;
    private float lastX, lastY, lastZ;
    private float xDifference, yDifference, zDifference;
    private boolean isNotFirstTime = false;
    private float shakeThreshold = 5f;
    private Vibrator vibrator;
    Dialog dialog;
    ImageButton mess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isAccelerometerSensorAvailable = true;
        } else {
            Log.d("TravelPlannerTag", "Accelerometer not available");
            isAccelerometerSensorAvailable = false;
        }
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        List<TripModel> trips = dataBaseHelper.getEverything();

        dialog = new Dialog(this);


        bottomBar = findViewById(R.id.bottomBar);

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


        locationsViewPager = findViewById(R.id.locationsViewPager);

        locationsViewPager.setAdapter(new TripsAdapter(trips));

        locationsViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "CLICKED", Toast.LENGTH_SHORT).show();
            }
        });

        locationsViewPager.setClipToPadding(false);
        locationsViewPager.setClipChildren(false);
        locationsViewPager.setOffscreenPageLimit(3);
        locationsViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.95f + r * 0.05f);
            }
        });

        locationsViewPager.setPageTransformer(compositePageTransformer);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        currentX = sensorEvent.values[0];
        currentY = sensorEvent.values[1];
        currentZ = sensorEvent.values[2];

        if (isNotFirstTime) {
            xDifference = Math.abs(lastX - currentX);
            yDifference = Math.abs(lastY - currentY);
            zDifference = Math.abs(lastZ - currentZ);

            if ((xDifference > shakeThreshold && yDifference > shakeThreshold) || (xDifference > shakeThreshold && zDifference > shakeThreshold) || (yDifference > shakeThreshold && zDifference > shakeThreshold)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                    Log.d("TravelPlannerTag", "Accelerometer is working");
                    dialog.setContentView(R.layout.popup);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                    mess = dialog.findViewById(R.id.messButton);
                    mess.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Uri uri = Uri.parse("https://www.messenger.com");
                            startActivity(new Intent(Intent.ACTION_VIEW, uri));
                        }
                    });
                }
            }
        }

        lastX = currentX;
        lastY = currentY;
        lastZ = currentZ;
        isNotFirstTime = true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isAccelerometerSensorAvailable) {
            sensorManager.registerListener(this,accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!isAccelerometerSensorAvailable) {
            sensorManager.unregisterListener(this);
        }
    }
}