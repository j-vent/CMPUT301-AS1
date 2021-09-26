package com.example.linnroseMedBook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * Represents the list of medicine displayed
 */
public class MedicineList extends ArrayAdapter<Medicine> {
    private ArrayList<Medicine> medicines;
    private Context context;

    public MedicineList(@NonNull Context context, ArrayList<Medicine> medicines) {
        super(context, 0, medicines);
        this.context = context;
        this.medicines = medicines;
    }

    /**
     * Gets a custom view to display the data
     * @param position position of item in adapter dataset
     * @param convertView old view to reuse
     * @param parent the parent that this view will be attached to
     * @return
     */
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup
            parent) {

        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content, parent,false);
        }

        // get medicine object
        Medicine medicine = medicines.get(position);

        // setup elements to populate
        TextView name = view.findViewById(R.id.list_name);
        TextView date = view.findViewById(R.id.list_date);
        TextView dose_unit_frequency = view.findViewById(R.id.list_dose_unit_frequency);

        // add data from medicine object
        name.setText(medicine.getName());
        date.setText(" Started on " +medicine.getDate());
        dose_unit_frequency.setText(medicine.getDoseAmt().toString() + " "
                + medicine.getDoseUnit() + " " + medicine.getDailyFrequency().toString()
        + "x daily");

        return view;
    }
}
