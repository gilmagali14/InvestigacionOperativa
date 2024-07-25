package com.operativa.gestion.dto;

public class PeriodosDTO {
    int cantidadPeriodo;
    int[] peso;

    public int getCantidadPeriodo() {
        return cantidadPeriodo;
    }

    public void setCantidadPeriodo(int cantidadPeriodo) {
        this.cantidadPeriodo = cantidadPeriodo;
    }

    public int[] getPeso() {
        return peso;
    }

    public void setPeso(int[] peso) {
        this.peso = peso;
    }
}
