package com.ensa;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Personne implements Serializable {
    private String nom;
    private String prenom;
    private Date date_naissance;

    public Personne(String nom, String prenom, Date date_naissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.date_naissance = date_naissance;
    }

    public int calcul_age(Date dateReference) {
        // Get the year from both dates
        int birthYear = date_naissance.getYear() + 1900; // Since getYear() returns year - 1900
        int referenceYear = dateReference.getYear() + 1900;

        int age = referenceYear - birthYear;

        // Calculate if the birthday has occurred this year
        if (date_naissance.getMonth() > dateReference.getMonth() ||
                (date_naissance.getMonth() == dateReference.getMonth() && date_naissance.getDate() > dateReference.getDate())) {
            age--;
        }

        return age;
    }

    @Override
    public String toString() {
        return "Personne[nom=" + nom + ", prenom=" + prenom + ", date_naissance=" + date_naissance + "]";
    }
}