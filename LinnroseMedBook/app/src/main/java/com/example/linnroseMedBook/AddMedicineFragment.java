package com.example.linnroseMedBook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Date;

public class AddMedicineFragment extends DialogFragment {
    // private EditText startDate;
    DatePicker date;
    private EditText name;
    private OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener{
        void onOkPressed(Medicine medicine);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener)  context;
        }
        else{
            throw new RuntimeException(context.toString() +
                    "must implement OnFragmentInteractionListener");
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        super.onCreateDialog(savedInstanceState);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_medicine_fragment_layout, null);
        // startDate = view.findViewById(R.id.editTextDate);
        date = view.findViewById(R.id.simpleDatePicker);
        name = view.findViewById(R.id.editTextName);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder.setView(view).setTitle("Add/Edit city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        // Date dateContent = startDate.getText().toString();
                        // from JavaDocs: year y is represented by y - 1900
                        int year = date.getYear() - 1900;
                        int month = date.getMonth();
                        int day = date.getDayOfMonth();
                        System.out.println("year " + year + "month " + month + "day "+ day);
                        Date dateContent = new Date(year, month, day);
                        String nameContent = name.getText().toString();


                        listener.onOkPressed(new Medicine(dateContent,
                                nameContent, 150, "msg", 2));

                    }
            }).create();
    }
}
