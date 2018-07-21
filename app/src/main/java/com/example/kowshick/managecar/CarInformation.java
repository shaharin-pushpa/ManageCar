package com.example.kowshick.managecar;

import java.io.Serializable;

public class CarInformation implements Serializable {

    private int carId;
    private String carType;
    private String carModel;
    private String engineType;
    private String yearMan;
    private double price;
    private String condition;
    private String mileage;
    private String area;
    private String color;
    private String fuel;

    public CarInformation() {
    }

    public CarInformation(String carType, String carModel, String engineType, String yearMan, double price, String condition, String mileage, String area, String color, String fuel) {
        this.carType = carType;
        this.carModel = carModel;
        this.engineType = engineType;
        this.yearMan = yearMan;
        this.price = price;
        this.condition = condition;
        this.mileage = mileage;
        this.area = area;
        this.color = color;
        this.fuel = fuel;
    }

    public CarInformation(int carId, String carType, String carModel, String engineType, String yearMan, double price, String condition, String mileage, String area, String color, String fuel) {
        this.carId = carId;
        this.carType = carType;
        this.carModel = carModel;
        this.engineType = engineType;
        this.yearMan = yearMan;
        this.price = price;
        this.condition = condition;
        this.mileage = mileage;
        this.area = area;
        this.color = color;
        this.fuel = fuel;
    }

    public int getCarId() {
        return carId;
    }

    public String getCarType() {
        return carType;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getEngineType() {
        return engineType;
    }

    public String getYearMan() {
        return yearMan;
    }

    public double getPrice() {
        return price;
    }

    public String getCondition() {
        return condition;
    }

    public String getMileage() {
        return mileage;
    }

    public String getArea() {
        return area;
    }

    public String getColor() {
        return color;
    }

    public String getFuel() {
        return fuel;
    }
}
