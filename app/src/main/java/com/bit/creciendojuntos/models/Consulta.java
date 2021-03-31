package com.bit.creciendojuntos.models;

public class Consulta {

    private String id;
    private String fechaConsulta;
    private String documentoH;


    public Consulta() {
    }

    public Consulta(String id, String fechaConsulta, String documentoH) {
        this.id = id;
        this.fechaConsulta = fechaConsulta;
        this.documentoH = documentoH;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(String fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public String getDocumentoH() {
        return documentoH;
    }

    public void setDocumentoH(String documentoH) {
        this.documentoH = documentoH;
    }
}
