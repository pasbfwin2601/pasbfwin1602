package com.bit.creciendojuntos.models;

public class Dentista {

    String id;
    String nombreDentista;
    String carie;
    String extraccion;
    String fechaControl;
    String gingivitis;
    String tratamiento;
    String proximaVisita;

    public Dentista() {
    }

    public Dentista(String id, String nombreDentista, String carie, String extraccion, String fechaControl, String gingivitis, String tratamiento, String proximaVisita) {
        this.id = id;
        this.nombreDentista = nombreDentista;
        this.carie = carie;
        this.extraccion = extraccion;
        this.fechaControl = fechaControl;
        this.gingivitis = gingivitis;
        this.tratamiento = tratamiento;
        this.proximaVisita = proximaVisita;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreDentista() {
        return nombreDentista;
    }

    public void setNombreDentista(String nombreDentista) {
        this.nombreDentista = nombreDentista;
    }

    public String getCarie() {
        return carie;
    }

    public void setCarie(String carie) {
        this.carie = carie;
    }

    public String getExtraccion() {
        return extraccion;
    }

    public void setExtraccion(String extraccion) {
        this.extraccion = extraccion;
    }

    public String getFechaControl() {
        return fechaControl;
    }

    public void setFechaControl(String fechaControl) {
        this.fechaControl = fechaControl;
    }

    public String getGingivitis() {
        return gingivitis;
    }

    public void setGingivitis(String gingivitis) {
        this.gingivitis = gingivitis;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getProximaVisita() {
        return proximaVisita;
    }

    public void setProximaVisita(String proximaVisita) {
        this.proximaVisita = proximaVisita;
    }
}
