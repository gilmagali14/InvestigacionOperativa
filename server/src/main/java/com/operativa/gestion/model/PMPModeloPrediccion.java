package com.operativa.gestion.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.Collection;

import java.util.regex.Pattern;

@Entity

@DiscriminatorValue("PMP")
public class PMPModeloPrediccion extends PrediccionDemanda{

    private String ponderations = "1";

    public String getPonderations() {
        return ponderations;
    }

    public void setPonderations(String ponderations) throws Exception {
        ArrayList<String> arr = new ArrayList<>(Arrays.asList(ponderations.split(";")));
        Pattern pattern = Pattern.compile("\\d+(,\\d+)?");
        for (String s : arr) {
            if(!pattern.matcher(s).matches()) {
                throw new Exception("El valor \"" + s + "\" no es una ponderación válida");
            }
        }
        this.ponderations = ponderations;
    }

}
