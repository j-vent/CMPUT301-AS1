package com.example.linnroseMedBook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements AddEditMedicineFragment.OnFragmentInteractionListener{
    private ListView medicineList;
    private ArrayAdapter<Medicine> medicineAdapter;
    private ArrayList<Medicine> medicineDataList;
    private TextView dailyDoseDisplay;
    private Medicine selectedMedicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize dose counter element
        dailyDoseDisplay = findViewById(R.id.dailyDose);

        // setup data and adapter
        medicineList = findViewById(R.id.medicineList);
        medicineDataList = new ArrayList<>();
        medicineAdapter = new MedicineList(this, medicineDataList);
        medicineList.setAdapter(medicineAdapter);

        // setup add button
        final FloatingActionButton addMedicineBtn = findViewById(R.id.addMedicineBtn);
        addMedicineBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addMedicine();
            }
        });

        // update which medicine is selected
        medicineList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedMedicine = (Medicine) medicineList.getItemAtPosition(position);
            }
        });

        // setup edit button
        Button editBtn = findViewById(R.id.editMedicineBtn);
        editBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // TODO: add check: if selected != null
                if(selectedMedicine != null){
                    editMedicine(selectedMedicine);
                }
            }
        });

        // setup delete button
        Button deleteBtn = findViewById(R.id.deleteMedicineBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(selectedMedicine != null){
                    deleteMedicine(selectedMedicine);
                }
            }
        });
    }

    /**
     * create a fragment to create a new medicine
     */
    public void addMedicine(){
       DialogFragment fr = new AddEditMedicineFragment();
       fr.show(getSupportFragmentManager(), "ADD Medicine");
    }

    /**
     * create a fragment to edit an existing medicine
     * @param selectedMedicine the medicine selected by the user in the listview
     */
    public void editMedicine(Medicine selectedMedicine){
        DialogFragment fr = new AddEditMedicineFragment().newInstance(selectedMedicine);
        fr.show(getSupportFragmentManager(), "EDIT MEDICINE");
    }

    /**
     * delete a medicine from the listview and update the daily dose
     * @param selectedMedicine
     */
    public void deleteMedicine(Medicine selectedMedicine){
        medicineDataList.remove(selectedMedicine);
        medicineAdapter.notifyDataSetChanged();
        updateDailyDose();
    }

    /**
     * Called when fragment "OK" button is clicked.
     * Adds new or edits existing medicine object
     * @param medicine medicine to add or update
     * @param isNewMedicine check if we need to add this medicine to list
     */
    @Override
    public void onOkPressed(Medicine medicine, boolean isNewMedicine) {
        if(isNewMedicine){
            medicineAdapter.add(medicine);
        }
        updateDailyDose();
        medicineAdapter.notifyDataSetChanged();
    }

    /**
     * Computes the daily sum of frequencies and updates value displayed on the activity
     */
    public void updateDailyDose(){
        Integer dailyDose = 0;
        for(Medicine med: medicineDataList){
            dailyDose += med.getDailyFrequency();
        }
        dailyDoseDisplay.setText(dailyDose.toString());
    }

}