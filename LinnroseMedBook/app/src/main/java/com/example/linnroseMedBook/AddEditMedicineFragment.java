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

import java.util.Arrays;
import java.util.Date;

/**
 * Dialog Fragment used when adding or editing a medicine
 */
public class AddEditMedicineFragment extends DialogFragment {
    private DatePicker date;
    private EditText name;
    private EditText doseAmount;
    private EditText frequency;
    private Spinner unitSpinner;
    private OnFragmentInteractionListener listener;

    /**
     * Interface to handle when the "OK" button was pressed
     */
    public interface OnFragmentInteractionListener {
        void onOkPressed(Medicine medicine, boolean isNewMedicine);
    }

    /**
     * Called when fragment is associated with main activity
     * and sets up listener
     * @param context
     */
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

    /**
     *
     * @param savedInstanceState contains the medicine data from recent fragment
     * @return
     */
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.add_medicine_fragment_layout, null);

        // setup elements to populate
        date = (DatePicker) view.findViewById(R.id.simpleDatePicker);
        name = view.findViewById(R.id.editTextName);
        doseAmount = view.findViewById(R.id.editTextDoseAmt);
        frequency = view.findViewById(R.id.editTextFreq);

        // setup dose unit selection / spinner
        // user input is restricted to these 3 units
        String[] doseUnits = {"mg", "mcg", "drop"};
        unitSpinner = view.findViewById(R.id.unitSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item, doseUnits);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        unitSpinner.setAdapter(adapter);

        // instantiate alert dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view).setTitle("Add or Edit Medicine")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", null);

        Dialog dialog = builder.create();

        // if this fragment contains existing medicine to be edited
        if (getArguments() != null) {
            // pre-fill the edittext fields with old data to be edited
            Medicine bundledMedicine = (Medicine) getArguments().getSerializable("medicine");
            String existingDate = bundledMedicine.getDate();
            String[] dateParsed = existingDate.split("-");
            date.init(Integer.parseInt(dateParsed[0]), Integer.parseInt(dateParsed[1])-1,
                    Integer.parseInt(dateParsed[2]), null);
            name.setText(bundledMedicine.getName());
            doseAmount.setText(bundledMedicine.getDoseAmt().toString());
            frequency.setText(bundledMedicine.getDailyFrequency().toString());
            int unitIndex = Arrays.asList(doseUnits).indexOf(bundledMedicine.getDoseUnit());
            unitSpinner.setSelection(unitIndex);
        }

        // sets the listener to be invoked when dialog is shown
        // allows us to override the "onclick" behaviour so we can incorporate input validation
        // it prevents dialogue box from automatically sending data to main and dismissing
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                // initializing ok button
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isNewMedicine = false;

                        // denotes a new medicine is being added
                        if (getArguments() == null) {
                            isNewMedicine = true;
                        }

                        // retrieve user input

                        // According to Java documentations year is presented as raw year + 1900
                        int year = date.getYear() - 1900;
                        int month = date.getMonth();
                        int day = date.getDayOfMonth();
                        Date dateContent = new Date(year, month, day);
                        String nameContent = name.getText().toString();
                        String doseUnit = unitSpinner.getSelectedItem().toString();

                        // set numeric values to be invalid values initially
                        // they can be overridden by valid values or promt the error message
                        Double doseAmtContent = -1.0;
                        Integer freqContent = -1;

                        // handle cases where user input is null
                        if(doseAmount.getText().length() != 0){
                            doseAmtContent = Double.parseDouble(doseAmount.getText().toString());
                        }
                        if(frequency.getText().length() != 0){
                            freqContent = Integer.parseInt(frequency.getText().toString());
                        }

                        String errMessage = validateUserInput(nameContent, doseAmtContent, freqContent);
                        boolean valid = errMessage.length() == 0;

                        if (valid) {
                            if(isNewMedicine) {
                                // create new Medicine object
                                listener.onOkPressed(new Medicine(dateContent, nameContent,
                                        doseAmtContent, doseUnit, freqContent), isNewMedicine);
                            } else{
                                // update existing Medicine object
                                Medicine editedMedicine = (Medicine) getArguments().getSerializable("medicine");
                                editedMedicine.setName(nameContent);
                                editedMedicine.setDate(dateContent);
                                editedMedicine.setDoseUnit(doseUnit);
                                editedMedicine.setDailyFreq(freqContent);
                                editedMedicine.setDoseAmt(doseAmtContent);
                                listener.onOkPressed(editedMedicine, isNewMedicine);
                            }
                            // close dialog boxes
                            dialogInterface.dismiss();
                            dialog.dismiss();
                        }
                        else{
//                          // display error message to prompt user to re-enter valid values
                            Toast t = Toast.makeText(getActivity(), errMessage, Toast.LENGTH_LONG);
                            t.show();

                        }
                    }
                });
            }
        });
        return dialog;

    }

    /**
     * Validates that the user input is valid (according to assignment 1 specifications)
     * Date and dosage unit fields are always valid as they are set by the spinner widgets
     * Thus, no further validation is required
     * @param name user inputted name
     * @param doseAmtContent user inputted dosage amount
     * @param freqContent user inputted frequency of consumption
     * @return errorMessage detailing input errors the user must change
     */
    public String validateUserInput(String name, Double doseAmtContent, Integer freqContent){
        // construct detailed error message
        // TODO: optimize with stringbuilder
        String errMsg = "";
        if(name.length() > 40 || name.length() <= 0){
            errMsg += "Name must be between 1 and 40 characters.";
        }
        if(doseAmtContent <= 0){
            errMsg += "Dose Amount must be a positive numeric.";
        }
        if(freqContent <= 0){
            errMsg += "Daily Frequency must be a positive integer.";
        }
        if(name == null || name.length() == 0 || doseAmtContent == null || freqContent == null ){
            errMsg += "You must fill out all fields.";
        }
        return errMsg;
    }

    /**
     * Creates a new instance of the fragment which bundles the existing medicine object
     * @param medicine object to be edited inside the fragment
     * @return new fragment to be used by user to make edits
     */
    public static AddEditMedicineFragment newInstance(Medicine medicine){
        Bundle args = new Bundle(0);
        args.putSerializable("medicine", medicine);
        AddEditMedicineFragment fragment = new AddEditMedicineFragment();
        fragment.setArguments(args);

        return fragment;
    }
}
