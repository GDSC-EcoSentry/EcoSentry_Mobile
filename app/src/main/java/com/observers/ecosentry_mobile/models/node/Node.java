package com.observers.ecosentry_mobile.models.node;

import com.google.firebase.firestore.GeoPoint;

import java.io.Serializable;
import java.util.Date;

public class Node implements Serializable {

    // ======================
    // == Fields
    // ======================
    private String name;
    private double co;
    private double dust;
    private double humidity;
    private double rain;
    private double soil_moisture;
    private double temperature;
    private GeoPoint geopoint;

    /**
     * FIXME: Change to Date (Timestamp) type in Firebase
     * - https://firebase.google.com/docs/reference/android/com/google/firebase/Timestamp
     */
    private Date mCensoredDate;

    // ======================
    // == Constructors
    // ======================

    public Node(String name, double co, double dust, double humidity, double rain, double soil_moisture, double temperature, GeoPoint geopoint, Date mCensoredDate) {
        this.name = name;
        this.co = co;
        this.dust = dust;
        this.humidity = humidity;
        this.rain = rain;
        this.soil_moisture = soil_moisture;
        this.temperature = temperature;
        this.geopoint = geopoint;
        this.mCensoredDate = mCensoredDate;
    }

    public Node() {
    }

    /**
     * Constructor for ViewHolder
     */
    public Node(String mName, double mCo, double mDust, double mHumidity, double mRain, double mSoilMoisture, double mTemperature) {
        this.name = mName;
        this.co = mCo;
        this.dust = mDust;
        this.humidity = mHumidity;
        this.rain = mRain;
        this.soil_moisture = mSoilMoisture;
        this.temperature = mTemperature;
    }

    // ======================
    // == Getters & Setters
    // ======================


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCo() {
        return co;
    }

    public void setCo(double co) {
        this.co = co;
    }

    public double getDust() {
        return dust;
    }

    public void setDust(double dust) {
        this.dust = dust;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public double getSoil_moisture() {
        return soil_moisture;
    }

    public void setSoil_moisture(double soil_moisture) {
        this.soil_moisture = soil_moisture;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public GeoPoint getGeopoint() {
        return geopoint;
    }

    public void setGeopoint(GeoPoint geopoint) {
        this.geopoint = geopoint;
    }

    public Date getmCensoredDate() {
        return mCensoredDate;
    }

    public void setmCensoredDate(Date mCensoredDate) {
        this.mCensoredDate = mCensoredDate;
    }
}
