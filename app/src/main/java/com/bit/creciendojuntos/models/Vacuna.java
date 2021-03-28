package com.bit.creciendojuntos.models;

public class Vacuna {

    String id;
    String nombreVacuna;
    String fechaDesde;
    String proximaDosis;


    public Vacuna(String id, String nombreVacuna, String fechaDesde, String proximaDosis) {
        this.id = id;
        this.nombreVacuna = nombreVacuna;
        this.fechaDesde = fechaDesde;
        this.proximaDosis = proximaDosis;
    }

    public Vacuna() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreVacuna() {
        return nombreVacuna;
    }

    public void setNombreVacuna(String nombreVacuna) {
        this.nombreVacuna = nombreVacuna;
    }

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getProximaDosis() {
        return proximaDosis;
    }

    public void setProximaDosis(String proximaDosis) {
        this.proximaDosis = proximaDosis;
    }
}
