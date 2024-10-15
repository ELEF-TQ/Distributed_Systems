package com.ensa;

import java.io.Serializable;

public class Voiture implements Serializable {
    private String mat;
    private int carburant;

    public Voiture(String mat, int carburant) {
        this.mat = mat;
        this.carburant = carburant;
    }

    public String getMat() {
        return mat;
    }

    public void setMat(String mat) {
        this.mat = mat;
    }

    public int getCarburant() {
        return carburant;
    }

    public void setCarburant(int carburant) {
        this.carburant = carburant;
    }

    @Override
    public String toString() {
        return "Voiture[mat=" + mat + ", carburant=" + carburant + "]";
    }
}
