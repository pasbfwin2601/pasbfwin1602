package com.bit.creciendojuntos.models;

public class Ocular {

    String id;
    String nombreOculista;
    String astigmatismo;
    String diagnostico;
    String fechaControl;
    String graduacionOI;
    String graduacionOD;
    String hipermetropia;
    String proximoControl;
    String tratamiento;


    public Ocular() {
    }

    public Ocular(String id, String nombreOculista, String astigmatismo, String diagnostico, String fechaControl, String graduacionOD,String graduacionOI, String hipermetropia, String proximoControl, String tratamiento) {
        this.id = id;
        this.nombreOculista = nombreOculista;
        this.astigmatismo = astigmatismo;
        this.diagnostico = diagnostico;
        this.fechaControl = fechaControl;
        this.graduacionOI = graduacionOI;
        this.graduacionOD = graduacionOD;
        this.hipermetropia = hipermetropia;
        this.proximoControl = proximoControl;
        this.tratamiento = tratamiento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreOculista() {
        return nombreOculista;
    }

    public void setNombreOculista(String nombreOculista) {
        this.nombreOculista = nombreOculista;
    }

    public String getAstigmatismo() {
        return astigmatismo;
    }

    public void setAstigmatismo(String astigmatismo) {
        this.astigmatismo = astigmatismo;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getFechaControl() {
        return fechaControl;
    }

    public void setFechaControl(String fechaControl) {
        this.fechaControl = fechaControl;
    }

    public String getGraduacionOI() {
        return graduacionOI;
    }

    public void setGraduacionOI(String graduacionOI) {
        this.graduacionOI = graduacionOI;
    }

    public String getGraduacionOD() {
        return graduacionOD;
    }

    public void setGraduacionOD(String graduacionOD) {
        this.graduacionOD = graduacionOD;
    }

    public String getHipermetropia() {
        return hipermetropia;
    }

    public void setHipermetropia(String hipermetropia) {
        this.hipermetropia = hipermetropia;
    }

    public String getProximoControl() {
        return proximoControl;
    }

    public void setProximoControl(String proximoControl) {
        this.proximoControl = proximoControl;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }
}
