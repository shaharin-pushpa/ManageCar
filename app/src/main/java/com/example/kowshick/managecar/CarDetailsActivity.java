package com.example.kowshick.managecar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CarDetailsActivity extends AppCompatActivity {
    private TextView carTypeTv,carModelTv,engineTypeTv,yearmanTv,priceTv,conditionTv,mileageTv,areaTv,colorTv,fuelTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);
        carTypeTv=findViewById(R.id.tvCarType);
        carModelTv=findViewById(R.id.tvCarModel);
        engineTypeTv=findViewById(R.id.tvEngine);
        yearmanTv=findViewById(R.id.tvYearman);
        priceTv=findViewById(R.id.tvprice);
        conditionTv=findViewById(R.id.tvcondition);
        mileageTv=findViewById(R.id.tvMileage);
        areaTv=findViewById(R.id.tvArea);
        colorTv=findViewById(R.id.tvColor);
        fuelTv=findViewById(R.id.tvFuel);
        try {
            Intent i = getIntent();
            CarInformation fc = (CarInformation) i.getSerializableExtra("msg");
            carTypeTv.setText(fc.getCarType());
            carModelTv.setText(fc.getCarModel());
            engineTypeTv.setText(fc.getEngineType());
            yearmanTv.setText(fc.getYearMan());
            priceTv.setText(Double.toString(fc.getPrice()));
            conditionTv.setText(fc.getCondition());
            mileageTv.setText(fc.getMileage());
            areaTv.setText(fc.getArea());
            colorTv.setText(fc.getColor());
            fuelTv.setText(fc.getFuel());
        }
        catch (Exception e){
            e.printStackTrace();
        }



    }

    public void backTo(View view) {
        startActivity(new Intent(CarDetailsActivity.this,CarRecyclerViewActivity.class));
    }
}
