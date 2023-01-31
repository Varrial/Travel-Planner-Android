package com.example.travelplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class EventAdapter extends ArrayAdapter<EventModel> {

    public EventAdapter(@NonNull Context context, List<EventModel> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_events, parent, false);
        }

        EventModel currentItem = getItem(position);

        TextView name = currentItemView.findViewById(R.id.event_name);
        TextView date = currentItemView.findViewById(R.id.event_date);
        TextView time = currentItemView.findViewById(R.id.event_time);


        name.setText(currentItem.getEventName());
        date.setText(currentItem.getDate());
        time.setText(currentItem.getStartTime());

        currentItemView.setOnClickListener(view -> {
            dataBaseHelper.deleteEvent(currentItem);
            EventAdapter.this.notifyDataSetChanged();
        });

        return currentItemView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

}
