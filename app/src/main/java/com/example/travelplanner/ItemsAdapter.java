package com.example.travelplanner;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends ArrayAdapter<ItemModel> {

    public ItemsAdapter(@NonNull Context context, List<ItemModel> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        ItemModel currentItem = getItem(position);

        CheckBox isPacked = currentItemView.findViewById(R.id.item_checkbox);
        TextView name = currentItemView.findViewById(R.id.item_name);

        if (currentItem.isPacked()) {
            isPacked.setChecked(true);
        } else {
            isPacked.setChecked(false);
        }

        isPacked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentItem.isPacked()) {
                    currentItem.setPacked(false);
                    boolean isUpdate = dataBaseHelper.updateData(currentItem);

                    if(isUpdate == true)
                        Log.d("TravelPlannerTag", "Data Updated");
                    else
                        Log.d("TravelPlannerTag", "Data NOT Updated");
                } else {
                    currentItem.setPacked(true);
                    boolean isUpdate = dataBaseHelper.updateData(currentItem);

                    if(isUpdate == true)
                        Log.d("TravelPlannerTag", "Data Updated");
                    else
                        Log.d("TravelPlannerTag", "Data NOT Updated");
                }

            }
        });

        name.setText(currentItem.getName());

        currentItemView.setOnClickListener(view -> {
            dataBaseHelper.deleteListItem(currentItem);
            ItemsAdapter.this.notifyDataSetChanged();
        });

        return currentItemView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
