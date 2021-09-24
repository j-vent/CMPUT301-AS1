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

public class MedicineList extends ArrayAdapter<Medicine> {
    private ArrayList<Medicine> medicines;
    private Context context;
    public MedicineList(@NonNull Context context, ArrayList<Medicine> medicines) {
        super(context, 0, medicines);
        this.context = context;
        this.medicines = medicines;
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup
            parent) {
        // return super.getView(position, convertView, parent);
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content, parent,false);
        }
        Medicine medicine = medicines.get(position);
        TextView date = view.findViewById(R.id.date);
        TextView name = view.findViewById(R.id.name);
        date.setText(medicine.getDate());
        name.setText(medicine.getName());
        return view;
    }
}
