package com.example.kowshick.managecar.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String TAG=DBHelper.class.getSimpleName();
    public static final String DB_NAME="myapp.db";
    public static final int DB_VERSION=1;
    //User Table
    public static final String USER_TABLE="users";
    public static final String TBL_USER_ID="_id";
    public static final String TBL_USER_EMAIL="email";
    public static final String TBL_USER_PASS="password";
    public static final String TBL_USER_TYPE="type";



    public static final String CREATE_TABLE_USER="CREATE TABLE "+ USER_TABLE +"("
            + TBL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TBL_USER_EMAIL + " TEXT,"
            + TBL_USER_PASS + " TEXT,"
            + TBL_USER_TYPE + " TEXT);";

    //Car Table
    public static final String CAR_TABLE="cars";
    public static final String TBL_CAR_ID="_id";
    public static final String TBL_CAR_CARTYPE="carType";
    public static final String TBL_CAR_CARMODEL="carModel";
    public static final String TBL_CAR_ENGINETYPE="engineType";
    public static final String TBL_CAR_YEARMAN="yearMan";
    public static final String TBL_CAR_PRICE="price";
    public static final String TBL_CAR_CONDITION="condition";
    public static final String TBL_CAR_MILEAGE="mileage";
    public static final String TBL_CAR_AREA="area";
    public static final String TBL_CAR_COLOR="color";
    public static final String TBL_CAR_FUEL="fuel";

    public static final String CREATE_TABLE_CAR="CREATE TABLE "+ CAR_TABLE +"("
            + TBL_CAR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TBL_CAR_CARTYPE + " TEXT,"
            + TBL_CAR_CARMODEL + " TEXT,"
            + TBL_CAR_ENGINETYPE + " TEXT,"
            + TBL_CAR_YEARMAN + " TEXT,"
            + TBL_CAR_PRICE + " NUMBER(8,2),"
            + TBL_CAR_CONDITION + " TEXT,"
            + TBL_CAR_MILEAGE + " TEXT,"
            + TBL_CAR_AREA + " TEXT,"
            + TBL_CAR_COLOR + " TEXT,"
            + TBL_CAR_FUEL + " TEXT);";



    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_CAR);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
