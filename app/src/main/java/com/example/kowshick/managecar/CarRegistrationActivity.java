package com.example.kowshick.managecar;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kowshick.managecar.database.DatabaseSource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class CarRegistrationActivity extends AppCompatActivity {
    private EditText carTypeET,carModelET,engineTypeET,yearmanET,priceET,mileageET,colorET,fuelET;
    private RadioGroup rg;
    Button add;
    private Spinner areaSp;
    private String condition="Used";
    private String area;
    private int rowId = 0;
    DatabaseSource helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper=new DatabaseSource(this);
        setContentView(R.layout.activity_car_registration);
        carTypeET=findViewById(R.id.carType);
        carModelET=findViewById(R.id.carModel);
        engineTypeET=findViewById(R.id.engineType);
        yearmanET=findViewById(R.id.yearManufacture);
        priceET=findViewById(R.id.price);
        mileageET=findViewById(R.id.mileage);
        colorET=findViewById(R.id.color);
        fuelET=findViewById(R.id.fuel);
        add=findViewById(R.id.addBtn);
        rg=findViewById(R.id.radioGroup);
        areaSp=findViewById(R.id.areaSp);



        ((RadioButton)findViewById(R.id.used)).setChecked(true);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = findViewById(i);
                condition = rb.getText().toString();
                Toast.makeText(CarRegistrationActivity.this, condition, Toast.LENGTH_SHORT).show();
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,getAllAreas());
        areaSp.setAdapter(adapter);
        areaSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                area = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//Update
        try {
            Intent i = getIntent();
            rowId = i.getIntExtra("rowId", 0);
            if(rowId>0) {
                CarInformation queriedCar = helper.getCarById(rowId);
                carTypeET.setText(queriedCar.getCarType());
                carModelET.setText(queriedCar.getCarModel());
                engineTypeET.setText(queriedCar.getEngineType());
                yearmanET.setText(queriedCar.getYearMan());
                priceET.setText(Double.toString(queriedCar.getPrice()));

                mileageET.setText(queriedCar.getMileage());
                colorET.setText(queriedCar.getColor());
                fuelET.setText(queriedCar.getFuel());
                add.setText("Update");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private final List<String> getAllAreas(){
        List<String>areas = new ArrayList<>();
        areas.add("Agargaon");
        areas.add("Dhanmondi");
        areas.add("Uttara");
        areas.add("Banani");
        areas.add("Rampura");
        areas.add("Mirpur");
        areas.add("Mohammadpur");
        areas.add("Tejgaon");
        Collections.sort(areas);
        return areas;
    }

    public void AddCar(View view) {
        if (rowId > 0) {
            String carType = carTypeET.getText().toString();
            String carModel = carModelET.getText().toString();
            String engineType = engineTypeET.getText().toString();
            String yearMan = yearmanET.getText().toString();
            double price=Double.parseDouble(priceET.getText().toString());
            String mileage=mileageET.getText().toString();
            String color=colorET.getText().toString();
            String fuel=fuelET.getText().toString();
            if(carType.isEmpty()||carModel.isEmpty()||engineType.isEmpty()||price==0){
                displayToast("Please fill the * mark field");
                return;
            }
            else {
                CarInformation carInfo = new CarInformation(rowId,carType, carModel, engineType, yearMan, price, condition, mileage, area, color, fuel);
                boolean status = helper.updateCar(carInfo);
                if (status) {
                    Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "could not save", Toast.LENGTH_SHORT).show();
                }
                //explicit intent
                Intent intent = new Intent(CarRegistrationActivity.this, CarRecyclerViewActivity.class);
                //intent.putExtra("msg",employee);
                startActivity(intent);
            }

        } else {
            String carType = carTypeET.getText().toString();
            String carModel = carModelET.getText().toString();
            String engineType = engineTypeET.getText().toString();
            String yearMan = yearmanET.getText().toString();
            double price = Double.parseDouble(priceET.getText().toString());
            String mileage = mileageET.getText().toString();
            String color = colorET.getText().toString();
            String fuel = fuelET.getText().toString();
            if (carType.isEmpty() || carModel.isEmpty() || engineType.isEmpty() || price == 0) {
                displayToast("Please fill the * mark field");
                return;
            } else {
                CarInformation carInfo = new CarInformation(carType, carModel, engineType, yearMan, price, condition, mileage, area, color, fuel);
                boolean status = helper.insertCar(carInfo);
                if (status) {
                    Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "could not save", Toast.LENGTH_SHORT).show();
                }
                //explicit intent
                Intent intent = new Intent(CarRegistrationActivity.this, CarRecyclerViewActivity.class);
                //intent.putExtra("msg",employee);
                startActivity(intent);
            }
        }
    }

    public void back(View view) {
        startActivity(new Intent(CarRegistrationActivity.this,CarRecyclerViewActivity.class));
    }

    private void displayToast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
