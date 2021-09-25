package com.example.linnroseMedBook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Date;

public class AddMedicineFragment extends DialogFragment {
    // private EditText startDate;
    DatePicker date;
    private EditText name;
    private EditText doseAmount;
    private EditText frequency;
    Spinner unitSpinner;
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
        doseAmount = view.findViewById(R.id.editTextDoseAmt);
        frequency = view.findViewById(R.id.editTextFreq);
        // setup dose unit selection / spinner
        String [] doseUnits = {"mg", "mcg", "drop"};
        unitSpinner = view.findViewById(R.id.unitSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, doseUnits);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        unitSpinner.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder.setView(view).setTitle("Add or Edit Medicine")
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
                        String doseUnit = unitSpinner.getSelectedItem().toString();
                        Double doseAmtContent = Double.parseDouble(doseAmount.getText().toString());
                        Integer freqContent = Integer.parseInt(frequency.getText().toString());
//                      Toast toast = Toast.makeText(getActivity().getApplicationContext(),
//                                "I`m a toast on top of a dialog.",
//                                Toast.LENGTH_LONG);
//                        // toast.setGravity(Gravity.CENTER,0,0);
//                        toast.show();
//                        showToast();
//                        System.out.println("toast");
                        System.out.println("name content "+ nameContent);
                        if(nameContent.length() == 0){
                            name.requestFocus();
                            name.setError("wrong name");

                            System.out.println("in name");
                        }


                        listener.onOkPressed(new Medicine(dateContent,
                                nameContent, doseAmtContent, doseUnit, freqContent));
                        dialogInterface.dismiss();

                        TextView errMsg = view.findViewById(R.id.errorMessage);
                        errMsg.setText("error in your code");


                    }
            }).create();
    }
    public void showToast() {
        System.out.println("in toast func");
        Toast.makeText(getActivity(), "toasttttt", Toast.LENGTH_SHORT).show();
        System.out.println("in after toast func");
    }
}
