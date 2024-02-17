package com.observers.ecosentry_mobile.models.user;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class User implements Serializable {

    // ======================
    // == Fields
    // ======================
    private String address;
    private String email;
    private String firstName;
    private String lastName;
    private String photoURL;
    private String role;
    private String uid;
    private String username;

    // ======================
    // == Constructors
    // ======================

    /**
     * Constructor for firebase
     */
    public User() {
    }

    // ======================
    // == Methods
    // ======================

    @Override
    public String toString() {
        return "User{" +
                "address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", photoURL='" + photoURL + '\'' +
                ", role='" + role + '\'' +
                ", uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    // ======================
    // == Getters & Setters
    // ======================
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
