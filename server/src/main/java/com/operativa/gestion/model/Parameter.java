package com.operativa.gestion.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
public class Parameter {

    @Id
    @JsonProperty(value = "parameterId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parameterId;
    private String parameterName;
    private String parameterValue;
    private Boolean isDeleted;

}

