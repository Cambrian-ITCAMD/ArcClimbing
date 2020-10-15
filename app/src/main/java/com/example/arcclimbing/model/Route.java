package com.example.arcclimbing.model;

public class Route {

    private String name;
    private String grade;
    private String barNumber;

    public Route() {
        // Required for Firestore's Automatic Data Mapping
    }

    public Route(String name, String grade, String barNumber) {
        this.name = name;
        this.grade = grade;
        this.barNumber = barNumber;
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
}
