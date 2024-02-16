package com.observers.ecosentry_mobile.models.node;

import java.util.Objects;

/**
 * FIXME Replace this class with Firebase GeoPoint data type
 * - https://firebase.google.com/docs/reference/kotlin/com/google/firebase/firestore/GeoPoint
 */
public class GeoPoint {

    // ======================
    // == Fields
    // ======================
    private Double latitude;
    private Double longitude;

    // ======================
    // == Constructors
    // ======================
    public GeoPoint(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // ======================
    // == Methods
    // ======================
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeoPoint geoPoint = (GeoPoint) o;
        return Objects.equals(latitude, geoPoint.latitude) && Objects.equals(longitude, geoPoint.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }
}
