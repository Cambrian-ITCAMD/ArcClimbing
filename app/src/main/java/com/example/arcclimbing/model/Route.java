package com.example.arcclimbing.model;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Route implements Serializable {

    private String documentId;
    private String name;
    private String grade;
    private String barNumber;
    private String colour;
    private String setter;
    private String setDate;
    private String removedDate;
    private String status;

    public Route() {
        // Required for Firestore's Automatic Data Mapping
    }

    public Route(String name, String grade, String barNumber, String colour, String setter, String setDate, String removedDate, String status) {
        this.name = name;
        this.grade = grade;
        this.barNumber = barNumber;
        this.colour = colour;
        this.setter = setter;
        this.setDate = setDate;
        this.removedDate = removedDate;
        this.status = status;
    }

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getBarNumber() {
        return barNumber;
    }

    public void setBarNumber(String barNumber) {
        this.barNumber = barNumber;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getSetter() {
        return setter;
    }

    public void setSetter(String setter) {
        this.setter = setter;
    }

    public String getSetDate() {
        return setDate;
    }

    public void setSetDate(String setDate) {
        this.setDate = setDate;
    }

    public String getRemovedDate() {
        return removedDate;
    }

    public void setRemovedDate(String removedDate) {
        this.removedDate = removedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
