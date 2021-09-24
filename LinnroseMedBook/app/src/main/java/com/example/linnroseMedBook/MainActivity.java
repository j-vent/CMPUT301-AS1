package com.example.linnroseMedBook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements AddMedicineFragment.OnFragmentInteractionListener{
    ListView medicineList;
    ArrayAdapter<Medicine> medicineAdapter;
    ArrayList<Medicine> medicineDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        medicineList = findViewById(R.id.medicineList);

        // test medicines in list
        Medicine m1 = new Medicine(new Date(), "m1",1,"mg",1);
        Medicine m2 = new Medicine(new Date(), "m2",2,"mg",2);
        medicineDataList = new ArrayList<>();
        medicineDataList.add(m1);
        medicineDataList.add(m2);
        medicineAdapter = new MedicineList(this, medicineDataList);
        medicineList.setAdapter(medicineAdapter);

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
        medicineAdapter.add(medicine);
    }
}