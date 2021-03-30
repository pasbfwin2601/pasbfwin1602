package com.bit.creciendojuntos.models;

public class Pediatra {

    String id;
    String nombrePediatra;
    String fechaPediatra;
    String presion;
    String peso;
    String talla;
    String imc;
    String rangoPeso;
    String aptitudFisica;
    String proximoControl;

    public Pediatra() {
    }

    public Pediatra(String id, String nombrePediatra, String fechaPediatra, String presion, String peso, String talla, String imc, String rangoPeso, String aptitudFisica, String proximoControl) {
        this.id = id;
        this.nombrePediatra = nombrePediatra;
        this.fechaPediatra = fechaPediatra;
        this.presion = presion;
        this.peso = peso;
        this.talla = talla;
        this.imc = imc;
        this.rangoPeso = rangoPeso;
        this.aptitudFisica = aptitudFisica;
        this.proximoControl = proximoControl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombrePediatra() {
        return nombrePediatra;
    }

    public void setNombrePediatra(String nombrePediatra) {
        this.nombrePediatra = nombrePediatra;
    }

    public String getFechaPediatra() {
        return fechaPediatra;
    }

    public void setFechaPediatra(String fechaPediatra) {
        this.fechaPediatra = fechaPediatra;
    }

    public String getPresion() {
        return presion;
    }

    public void setPresion(String presion) {
        this.presion = presion;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getImc() {
        return imc;
    }

    public void setImc(String imc) {
        this.imc = imc;
    }

    public String getRangoPeso() {
        return rangoPeso;
    }

    public void setRangoPeso(String rangoPeso) {
        this.rangoPeso = rangoPeso;
    }

    public String getAptitudFisica() {
        return aptitudFisica;
    }

    public void setAptitudFisica(String aptitudFisica) {
        this.aptitudFisica = aptitudFisica;
    }

    public String getProximoControl() {
        return proximoControl;
    }

    public void setProximoControl(String proximoControl) {
        this.proximoControl = proximoControl;
    }
}
