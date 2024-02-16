package com.observers.ecosentry_mobile.models.node;

import java.io.Serializable;
import java.util.Date;

public class Node implements Serializable {

    // ======================
    // == Fields
    // ======================
    private String mName;
    private double mCo;
    private double mDust;
    private double mHumidity;
    private double mRain;
    private double mSoilMoisture;
    private double mTemperature;
    private GeoPoint mGeoPoint;

    /**
     * FIXME: Change to Date (Timestamp) type in Firebase
     * - https://firebase.google.com/docs/reference/android/com/google/firebase/Timestamp
     */
    private Date mCensoredDate;

    // ======================
    // == Constructors
    // ======================

    /**
     * Constructor for Data Fetching from FireStore
     *
     * @param mName
     * @param mCo
     * @param mDust
     * @param mHumidity
     * @param mRain
     * @param mSoilMoisture
     * @param mTemperature
     * @param mGeoPoint
     * @param mCensoredDate
     */
    public Node(String mName, double mCo, double mDust, double mHumidity, double mRain, double mSoilMoisture, double mTemperature, GeoPoint mGeoPoint, Date mCensoredDate) {
        this.mName = mName;
        this.mCo = mCo;
        this.mDust = mDust;
        this.mHumidity = mHumidity;
        this.mRain = mRain;
        this.mSoilMoisture = mSoilMoisture;
        this.mTemperature = mTemperature;
        this.mGeoPoint = mGeoPoint;
        this.mCensoredDate = mCensoredDate;
    }


    /**
     * Constructor for ViewHolder
     */
    public Node(String mName, double mCo, double mDust, double mHumidity, double mRain, double mSoilMoisture, double mTemperature) {
        this.mName = mName;
        this.mCo = mCo;
        this.mDust = mDust;
        this.mHumidity = mHumidity;
        this.mRain = mRain;
        this.mSoilMoisture = mSoilMoisture;
        this.mTemperature = mTemperature;
    }

    // ======================
    // == Getters & Setters
    // ======================

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public double getmCo() {
        return mCo;
    }

    public void setmCo(double mCo) {
        this.mCo = mCo;
    }

    public double getmDust() {
        return mDust;
    }

    public void setmDust(double mDust) {
        this.mDust = mDust;
    }

    public double getmHumidity() {
        return mHumidity;
    }

    public void setmHumidity(double mHumidity) {
        this.mHumidity = mHumidity;
    }

    public double getmRain() {
        return mRain;
    }

    public void setmRain(double mRain) {
        this.mRain = mRain;
    }

    public double getmSoilMoisture() {
        return mSoilMoisture;
    }

    public void setmSoilMoisture(double mSoilMoisture) {
        this.mSoilMoisture = mSoilMoisture;
    }

    public double getmTemperature() {
        return mTemperature;
    }

    public void setmTemperature(double mTemperature) {
        this.mTemperature = mTemperature;
    }

    public GeoPoint getmGeoPoint() {
        return mGeoPoint;
    }

    public void setmGeoPoint(GeoPoint mGeoPoint) {
        this.mGeoPoint = mGeoPoint;
    }

    public Date getmCensoredDate() {
        return mCensoredDate;
    }

    public void setmCensoredDate(Date mCensoredDate) {
        this.mCensoredDate = mCensoredDate;
    }
}
