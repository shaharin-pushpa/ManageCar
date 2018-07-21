package com.example.kowshick.managecar.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kowshick.managecar.CarInformation;

import java.util.ArrayList;
import java.util.List;



public class DatabaseSource {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DatabaseSource(Context context){
        helper=new DBHelper(context);

    }

    public void addUser(String email,String password,String type){

        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DBHelper.TBL_USER_EMAIL,email);
        values.put(DBHelper.TBL_USER_PASS,password);
        values.put(DBHelper.TBL_USER_TYPE,type);
        long id=db.insert(DBHelper.USER_TABLE,null,values);
        db.close();
        //Log.d(TAG, "user inserted "+ id);
    }

    public boolean getUser(String email,String password){
        String selectQuery="select "+ DBHelper.TBL_USER_EMAIL + " , " + DBHelper.TBL_USER_PASS + " from " + DBHelper.USER_TABLE + " where " +
                DBHelper.TBL_USER_EMAIL + " = " + "'"+email+"'" + " and " + DBHelper.TBL_USER_PASS + " = " + "'"+password+"'";
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            //return cursor.getString(cursor.getColumnIndex(TBL_USER_TYPE));
            return true;
        }
        cursor.close();
        db.close();
        // return "Not Found";
        return false;
    }

    public boolean insertCar(CarInformation carInfo){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.TBL_CAR_CARTYPE,carInfo.getCarType());
        values.put(DBHelper.TBL_CAR_CARMODEL,carInfo.getCarModel());
        values.put(DBHelper.TBL_CAR_ENGINETYPE,carInfo.getEngineType());
        values.put(DBHelper.TBL_CAR_YEARMAN,carInfo.getYearMan());
        values.put(DBHelper.TBL_CAR_PRICE,carInfo.getPrice());
        values.put(DBHelper.TBL_CAR_CONDITION,carInfo.getCondition());
        values.put(DBHelper.TBL_CAR_MILEAGE,carInfo.getMileage());
        values.put(DBHelper.TBL_CAR_AREA,carInfo.getArea());
        values.put(DBHelper.TBL_CAR_COLOR,carInfo.getColor());
        values.put(DBHelper.TBL_CAR_FUEL,carInfo.getFuel());


        long insertedRow = db.insert(DBHelper.CAR_TABLE,null,values);
        db.close();
        if(insertedRow > 0){
            return true;
        }else{
            return false;
        }
    }

    public List<CarInformation> getAllCars(){
        SQLiteDatabase db = helper.getReadableDatabase();
        List<CarInformation> cars = new ArrayList<>();
        Cursor c = db.query(DBHelper.CAR_TABLE,null,null,null,null,null,null);

        if(c != null && c.getCount() > 0){
            c.moveToFirst();
            do{
                int id = c.getInt(c.getColumnIndex(DBHelper.TBL_CAR_ID));
                String carType = c.getString(c.getColumnIndex(DBHelper.TBL_CAR_CARTYPE));
                String carModel = c.getString(c.getColumnIndex(DBHelper.TBL_CAR_CARMODEL));
                String engineType = c.getString(c.getColumnIndex(DBHelper.TBL_CAR_ENGINETYPE));
                String yearMan= c.getString(c.getColumnIndex(DBHelper.TBL_CAR_YEARMAN));
                double price = c.getDouble(c.getColumnIndex(DBHelper.TBL_CAR_PRICE));
                String condition = c.getString(c.getColumnIndex(DBHelper.TBL_CAR_CONDITION));
                String mileage = c.getString(c.getColumnIndex(DBHelper.TBL_CAR_MILEAGE));
                String area = c.getString(c.getColumnIndex(DBHelper.TBL_CAR_AREA));
                String color = c.getString(c.getColumnIndex(DBHelper.TBL_CAR_COLOR));
                String fuel = c.getString(c.getColumnIndex(DBHelper.TBL_CAR_FUEL));
                CarInformation carInfo = new CarInformation(id,carType,carModel,engineType,yearMan,price,condition,mileage,area,color,fuel);


                cars.add(carInfo);

            }while (c.moveToNext());
        }
        c.close();
        db.close();
        return cars;
    }

    public boolean deleteCar(int carId){
        SQLiteDatabase db=helper.getWritableDatabase();
        int deletedRow = db.delete(DBHelper.CAR_TABLE,DBHelper.TBL_CAR_ID+" = "+carId,null);
        db.close();
        if(deletedRow > 0){
            return true;
        }else{
            return false;
        }
    }

    public CarInformation getCarById(long rowId){
        SQLiteDatabase db = helper.getReadableDatabase();

        CarInformation car = null;
        Cursor c = db.query(DBHelper.CAR_TABLE,null,DBHelper.TBL_CAR_ID+" = "+rowId,null,null,null,null);
        if(c != null && c.getCount() > 0){
            c.moveToFirst();
            int id = c.getInt(c.getColumnIndex(DBHelper.TBL_CAR_ID));
            String carType = c.getString(c.getColumnIndex(DBHelper.TBL_CAR_CARTYPE));
            String carModel = c.getString(c.getColumnIndex(DBHelper.TBL_CAR_CARMODEL));
            String engineType = c.getString(c.getColumnIndex(DBHelper.TBL_CAR_ENGINETYPE));
            String yearMan= c.getString(c.getColumnIndex(DBHelper.TBL_CAR_YEARMAN));
            double price = c.getDouble(c.getColumnIndex(DBHelper.TBL_CAR_PRICE));
            String condition = c.getString(c.getColumnIndex(DBHelper.TBL_CAR_CONDITION));
            String mileage = c.getString(c.getColumnIndex(DBHelper.TBL_CAR_MILEAGE));
            String area = c.getString(c.getColumnIndex(DBHelper.TBL_CAR_AREA));
            String color = c.getString(c.getColumnIndex(DBHelper.TBL_CAR_COLOR));
            String fuel = c.getString(c.getColumnIndex(DBHelper.TBL_CAR_FUEL));
            CarInformation carInfo = new CarInformation(id,carType,carModel,engineType,yearMan,price,condition,mileage,area,color,fuel);
            car=carInfo;
        }
        c.close();
        db.close();
        return car;
    }

    public boolean updateCar(CarInformation carInfo){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.TBL_CAR_CARTYPE,carInfo.getCarType());
        values.put(DBHelper.TBL_CAR_CARMODEL,carInfo.getCarModel());
        values.put(DBHelper.TBL_CAR_ENGINETYPE,carInfo.getEngineType());
        values.put(DBHelper.TBL_CAR_YEARMAN,carInfo.getYearMan());
        values.put(DBHelper.TBL_CAR_PRICE,carInfo.getPrice());
        values.put(DBHelper.TBL_CAR_CONDITION,carInfo.getCondition());
        values.put(DBHelper.TBL_CAR_MILEAGE,carInfo.getMileage());
        values.put(DBHelper.TBL_CAR_AREA,carInfo.getArea());
        values.put(DBHelper.TBL_CAR_COLOR,carInfo.getColor());
        values.put(DBHelper.TBL_CAR_FUEL,carInfo.getFuel());
        int updatedRow = db.update(DBHelper.CAR_TABLE,values,DBHelper.TBL_CAR_ID+" = "+carInfo.getCarId(),null);
        db.close();
        if(updatedRow > 0){
            return true;
        }else{
            return false;
        }
    }

}
