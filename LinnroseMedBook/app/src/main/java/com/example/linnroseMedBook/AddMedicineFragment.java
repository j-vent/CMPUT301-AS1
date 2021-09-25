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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import java.util.Date;

public class AddMedicineFragment extends DialogFragment {
    // private EditText startDate;
    DatePicker date;
    private EditText name;
    private EditText doseAmount;
    private EditText frequency;
    Spinner unitSpinner;
    private OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener {
        void onOkPressed(Medicine medicine, boolean isNewMedicine);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() +
                    "must implement OnFragmentInteractionListener");
        }
    }
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_medicine_fragment_layout, null);
        // startDate = view.findViewById(R.id.editTextDate);
        date = view.findViewById(R.id.simpleDatePicker);
        name = view.findViewById(R.id.editTextName);
        doseAmount = view.findViewById(R.id.editTextDoseAmt);
        frequency = view.findViewById(R.id.editTextFreq);
        // setup dose unit selection / spinner
        String[] doseUnits = {"mg", "mcg", "drop"};
        unitSpinner = view.findViewById(R.id.unitSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, doseUnits);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        unitSpinner.setAdapter(adapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view).setTitle("Add or Edit Medicine")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", null);
        Dialog dialog = builder.create();

        if (getArguments() != null) {
            System.out.println("!!!!!edit med");
            // pre-fill the edittext fields with old data
            Medicine bundledMedicine = (Medicine) getArguments().getSerializable("medicine");
            // System.out.println("existingmedicine" , )
            System.out.println("existing bundledMedicine" + bundledMedicine.getName());
            // date.updateDate(2021, 9, 25);
            name.setText(bundledMedicine.getName());
            doseAmount.setText(bundledMedicine.getDoseAmt().toString());
            frequency.setText(bundledMedicine.getDailyFrequency().toString());
        }

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                System.out.println("on show");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("in onclick");
                        boolean isNewMedicine = false;
                        // EDIT Case
                   if (getArguments() != null) {
//                            System.out.println("!!!!!edit med");
//                            // pre-fill the edittext fields with old data
//                            Medicine bundledMedicine = (Medicine) getArguments().getSerializable("medicine");
//                            // System.out.println("existingmedicine" , )
//                            System.out.println("existing bundledMedicine" + bundledMedicine.getName());
//
//                            // date.updateDate(2021, 9, 25);
//                            name.setText(bundledMedicine.getName());
//                            doseAmount.setText(bundledMedicine.getDoseAmt().toString());
//                            frequency.setText(bundledMedicine.getDailyFrequency().toString());
                   }
                        else{
                            System.out.println("bundle null new med");
                            isNewMedicine = true;
                        }

                        int year = date.getYear() - 1900;
                        int month = date.getMonth();
                        int day = date.getDayOfMonth();
                        // System.out.println("year " + year + "month " + month + "day " + day);
                        Date dateContent = new Date(year, month, day);
                        String nameContent = name.getText().toString();
                        String doseUnit = unitSpinner.getSelectedItem().toString();
                        Double doseAmtContent = Double.parseDouble(doseAmount.getText().toString());
                        Integer freqContent = Integer.parseInt(frequency.getText().toString());

                        String errMessage = validateUserInput(nameContent, doseAmtContent, freqContent);
                        boolean valid = errMessage.length() == 0;
                        //Dismiss once everything is OK.
                        if (valid) {
                            System.out.println("valid " + valid);
                            // listener.onOkPressed(new Medicine(new Date(), "me", 1.0, "mg", 2), true);
                            if(isNewMedicine) {
                                System.out.println("create new med");
                                listener.onOkPressed(new Medicine(dateContent, nameContent, doseAmtContent, doseUnit, freqContent), true);
                            } else{
                                // update existing Medicine
                                System.out.println("edit med setters");
                                Medicine editedMedicine = (Medicine) getArguments().getSerializable("medicine");
                                editedMedicine.setName(nameContent);
                                System.out.println("new name in obj " + editedMedicine.getName());
                                editedMedicine.setDate(dateContent);
                                editedMedicine.setDoseUnit(doseUnit);
                                editedMedicine.setDailyFreq(freqContent);
                                editedMedicine.setDoseAmt(doseAmtContent);
                                listener.onOkPressed(editedMedicine, false);
                            }
                            dialogInterface.dismiss();
                            dialog.dismiss();
                        }
                        else{
//                            TextView errMsg = view.findViewById(R.id.errorMessage);
//                            errMsg.setText("error in your code");
                            Toast t = Toast.makeText(getActivity(), errMessage, Toast.LENGTH_LONG);
                            // t.setGravity(Gravity.TOP, 0, 250);
                            t.show();
                            System.out.println("err");

                        }

                    }
                });
            }
        });
        return dialog;

    }

    // other 2 fields do not need validation since they are set by the spinner widgets
    public String validateUserInput(String name, Double doseAmtContent, Integer freqContent){
        String errMsg = ""; // TODO: optimize with stringbuilder
        if(name.length() > 40){
            errMsg += "Name must be less than 40 characters.";
        }
        if(doseAmtContent <= 0){
            errMsg += "Dose Amount must be a positive numeric";
        }
        if(freqContent <= 0){
            errMsg += "Daily Frequency must be a positive integer";
        }
        return errMsg;
    }

    /**
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_medicine_fragment_layout, null);
        // startDate = view.findViewById(R.id.editTextDate);
        date = view.findViewById(R.id.simpleDatePicker);
        name = view.findViewById(R.id.editTextName);
        doseAmount = view.findViewById(R.id.editTextDoseAmt);
        frequency = view.findViewById(R.id.editTextFreq);
        // setup dose unit selection / spinner
        String[] doseUnits = {"mg", "mcg", "drop"};
        unitSpinner = view.findViewById(R.id.unitSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, doseUnits);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        unitSpinner.setAdapter(adapter);
        if (getArguments() != null) {
            Medicine bundledMedicine = (Medicine) getArguments().getSerializable("medicine");
            System.out.println("bundledMedicine" + bundledMedicine.getName());
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            // date.updateDate(2021, 9, 25);
            name.setText(bundledMedicine.getName());
            doseAmount.setText(bundledMedicine.getDoseAmt().toString());
            frequency.setText(bundledMedicine.getDailyFrequency().toString());

            return builder.setView(view).setTitle("Add or Edit Medicine")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Medicine editedMedicine = (Medicine) getArguments().getSerializable("medicine");
                            // Date dateContent = startDate.getText().toString();
                            // from JavaDocs: year y is represented by y - 1900
                            int year = date.getYear() - 1900;
                            int month = date.getMonth();
                            int day = date.getDayOfMonth();
                            System.out.println("year " + year + "month " + month + "day " + day);
                            Date dateContent = new Date(year, month, day);
                            String nameContent = name.getText().toString();
                            String doseUnit = unitSpinner.getSelectedItem().toString();
                            Double doseAmtContent = Double.parseDouble(doseAmount.getText().toString());
                            Integer freqContent = Integer.parseInt(frequency.getText().toString());
                            System.out.println("new name " + nameContent);
                            // update Medicine
                            editedMedicine.setName(nameContent);
                            System.out.println("new name in obj" + editedMedicine.getName());
                            editedMedicine.setDate(dateContent);
                            editedMedicine.setDoseUnit(doseUnit);
                            editedMedicine.setDailyFreq(freqContent);
                            editedMedicine.setDoseAmt(doseAmtContent);

                            listener.onOkPressed(editedMedicine, false);
                            dialogInterface.dismiss();

                        }
                    }).create();
        } else {
            System.out.println("else");
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
                            System.out.println("year " + year + "month " + month + "day " + day);
                            Date dateContent = new Date(year, month, day);
                            String nameContent = name.getText().toString();
                            String doseUnit = unitSpinner.getSelectedItem().toString();
                            Double doseAmtContent = Double.parseDouble(doseAmount.getText().toString());
                            Integer freqContent = Integer.parseInt(frequency.getText().toString());
                            boolean isValid = false;
                            if (!isValid) {
                                System.out.println("not valid data");
                                Toast.makeText(getActivity(), "TEST TOAST.", Toast.LENGTH_LONG).show();
                                TextView errMsg = view.findViewById(R.id.errorMessage);
                                errMsg.setText("error in your code");
//                            listener.onOkPressed(new Medicine(dateContent,
//                                    nameContent, doseAmtContent, doseUnit, freqContent), true);
//                            dialogInterface.dismiss();

                            } else {
                                listener.onOkPressed(new Medicine(dateContent,
                                        nameContent, doseAmtContent, doseUnit, freqContent), true);
                                dialogInterface.dismiss();
                            }


                        }
                    }).create();
        }

    }
    **/
    public void showToast() {
        System.out.println("in toast func");
        Toast.makeText(getActivity(), "toasttttt", Toast.LENGTH_SHORT).show();
        System.out.println("in after toast func");
    }
    static AddMedicineFragment newInstance(Medicine medicine){
        System.out.println("new instance ");
        Bundle args = new Bundle(0);
        args.putSerializable("medicine", medicine);
        AddMedicineFragment fragment = new AddMedicineFragment();
        fragment.setArguments(args);
        Medicine testCity = (Medicine) fragment.getArguments().getSerializable("medicine");
        System.out.println(testCity.getName());
        return fragment;
    }
}
