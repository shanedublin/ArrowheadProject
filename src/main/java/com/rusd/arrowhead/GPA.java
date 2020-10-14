package com.rusd.arrowhead;

public class GPA {
    private float scale;
    private float grade;

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public float getPercent(){
        return grade / scale;
    }
}
