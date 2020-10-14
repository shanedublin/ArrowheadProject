package com.rusd.arrowhead;

public class Applicant {

    private float age;
    private boolean felonies;
    private GPA gpa = new GPA();
    private float satScore;
    private float actScore;
    private String firstName;
    private String lastName;
    private String state;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public GPA getGpa() {
        return gpa;
    }

    public void setGpa(GPA gpa) {
        this.gpa = gpa;
    }

    public boolean hasFelonies() {
        return felonies;
    }

    public float getAge() {
        return age;
    }

    public void setAge(float age) {
        this.age = age;
    }

    public void setFelonies(boolean felonies) {
        this.felonies = felonies;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public float getSatScore() {
        return satScore;
    }

    public void setSatScore(float satScore) {
        this.satScore = satScore;
    }

    public float getActScore() {
        return actScore;
    }

    public void setActScore(float actScore) {
        this.actScore = actScore;
    }

    @Override
    public String toString() {
        return "Name: " + firstName + ' ' + lastName ;
    }
}
