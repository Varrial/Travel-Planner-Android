package com.example.travelplanner;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.TripsViewHolder> {

    private List<TripModel> trips;
    private int tripId;
    public static RecyclerView.Adapter adapter;

    public TripsAdapter(List<TripModel> trips) {
        this.trips = trips;
        adapter = this;
    }


    @NonNull
    @Override
    public TripsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new TripsViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_location,
                        parent,
                        false
                ), parent.getContext()
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TripsViewHolder holder, int position) {
        holder.setLocationData(trips.get(position));
        tripId = trips.get(position).getTripID();
    }

    public int getTripId() {
        return tripId;
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    class TripsViewHolder extends RecyclerView.ViewHolder {

        private KenBurnsView kbvLocation;
        private TextView textTitle;
        private TextView textLocation;
        private TextView textDates;
        private ImageButton deleteButton;
        private ImageView workIcon;
        private View locationView;
        private LinearLayout datesBackground;
        private ImageView locationIcon;
        private Context context;
        private int tripID;
        private ItemsAdapter itemArrayAdapter;
        private DataBaseHelper dataBaseHelper;

        public TripsViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            kbvLocation = itemView.findViewById(R.id.kbvLocation);
            textTitle = itemView.findViewById(R.id.textTitle);
            textLocation = itemView.findViewById(R.id.textLocation);
            textDates = itemView.findViewById(R.id.textDates);
            deleteButton = itemView.findViewById(R.id.button);
            workIcon = itemView.findViewById(R.id.imageWork);
            locationView = itemView.findViewById(R.id.view);
            datesBackground = itemView.findViewById(R.id.datesBackground);
            locationIcon = itemView.findViewById(R.id.imageLocation);
            dataBaseHelper = new DataBaseHelper(context);
            this.context = context;
        }


        /*public void showItemsOnListView(Context context, int tripID) {
            itemArrayAdapter = new ItemsAdapter(context, dataBaseHelper.getList(tripID));
            itemsList.setAdapter(itemArrayAdapter);
        }*/

        public int getTripID() {
            return tripID;
        }

        void setLocationData(TripModel trip) {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(deleteButton.getContext());
            Picasso.get().load(trip.getImageURL()).into(kbvLocation);
            kbvLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(kbvLocation.getContext(), "CLICKED kbv", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(kbvLocation.getContext(), OverviewActivity.class);
                    intent.putExtra("tripID", trip.getTripID());
                    intent.putExtra("city", trip.getCity());
                    intent.putExtra("startDate", trip.getStartDate());
                    intent.putExtra("endDate", trip.getEndDate());
                    context.startActivity(intent);
                }
            });
            textTitle.setText(trip.getCountry());
            textLocation.setText(trip.getCity());
            textDates.setText(trip.getStartDate() + "-" + trip.getEndDate());

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dataBaseHelper.deleteOne(trip);
                    Intent intent = new Intent(kbvLocation.getContext(), MainActivity.class);
                    context.startActivity(intent);

                }
            });

            /*addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tripID = trip.getTripID();
                    Intent intent = new Intent(addButton.getContext(), AddItemActivity.class);
                    intent.putExtra("tripID", tripID);
                    context.startActivity(intent);
                }
            });*/

            if (trip.isWork()) {
                workIcon.setVisibility(View.VISIBLE);
            }

            //showItemsOnListView(itemView.getContext(), trip.getTripID());

        }
    }

}
