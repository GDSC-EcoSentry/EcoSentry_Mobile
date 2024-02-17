package com.observers.ecosentry_mobile.models.station;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;
import com.google.type.Date;

import java.io.Serializable;

public class Station implements Serializable {

    // ======================
    // == Fields
    // ======================
    private String location;
    private String name;
    private Timestamp lastUpdate;
    private Timestamp dateCreated;
    private GeoPoint geopoint;

    // ======================
    // == Constructors
    // ======================

    /**
     * Constructor for fetching data from firestore
     */
    public Station() {
    }

    /**
     * Constructor for fetching fake station
     *
     * @param location
     * @param name
     */
    public Station(String location, String name) {
        this.location = location;
        this.name = name;
    }

    // ======================
    // == Methods
    // ======================

    @Override
    public String toString() {
        return "Station{" +
                "location='" + location + '\'' +
                ", name='" + name + '\'' +
                ", lastUpdate=" + lastUpdate +
                ", dateCreated=" + dateCreated +
                ", geopoint=" + geopoint +
                '}';
    }

    // ======================
    // == Getters & Setters
    // ======================

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public GeoPoint getGeopoint() {
        return geopoint;
    }

    public void setGeopoint(GeoPoint geopoint) {
        this.geopoint = geopoint;
    }
}
