package com.example.linnroseMedBook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements AddMedicineFragment.OnFragmentInteractionListener{
    ListView medicineList;
    ArrayAdapter<Medicine> medicineAdapter;
    ArrayList<Medicine> medicineDataList;
    Integer dailyDose = 0;
    TextView dailyDoseDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        medicineList = findViewById(R.id.medicineList);

        // test medicines in list
        Medicine m1 = new Medicine(new Date(), "Advil",1.0,"mg",1);
        Medicine m2 = new Medicine(new Date(), "Tylenol",2.0,"dose",2);
        medicineDataList = new ArrayList<>();
        // medicineDataList.add(m1);
        // medicineDataList.add(m2);
        // Initialize to 0 since app initializes as a blank list
        dailyDoseDisplay = findViewById(R.id.dailyDose);
        // System.out.println("daily dose disp " + dailyDoseDisplay);
        // dailyDoseDisplay.setText(0);
        medicineAdapter = new MedicineList(this, medicineDataList);
        medicineList.setAdapter(medicineAdapter);
        Toast.makeText(getApplicationContext(), "testtoasttttt", Toast.LENGTH_SHORT).show();
        System.out.println("toast in main");
        final FloatingActionButton addMedicineBtn = findViewById(R.id.addMedicineBtn);
        addMedicineBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new AddMedicineFragment().show(getSupportFragmentManager(), "ADD Medicine");
            }
        });
    }

    @Override
    public void onOkPressed(Medicine medicine) {
        validateMedicine(medicine);
        medicineAdapter.add(medicine);
        updateDailyDose(medicine);
    }

    // TODO: extend this method for the delete scenario
    public void updateDailyDose(Medicine medicine){
        dailyDose += medicine.getDailyFrequency();
        dailyDoseDisplay.setText(dailyDose.toString());
    }

    public void validateMedicine(Medicine medicine){
        Toast.makeText(this, "toasttttt", Toast.LENGTH_SHORT).show();
        StringBuilder errorMsg = new StringBuilder();
        if(medicine.getName().length() > 40){
            System.out.println("bad name");
        }


    }
}