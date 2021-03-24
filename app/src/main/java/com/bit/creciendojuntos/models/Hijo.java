package com.bit.creciendojuntos.models;

public class Hijo {

    String nombreH;
    String documentoH;
    String documentoP;

    public Hijo() {
    }

    public Hijo(String nombreH, String documentoH) {
        this.nombreH = nombreH;
        this.documentoH = documentoH;
    }

    public String getNombreH() {
        return nombreH;
    }

    public void setNombreH(String nombreH) {
        this.nombreH = nombreH;
    }

    public String getDocumentoH() {
        return documentoH;
    }

    public void setDocumentoH(String documentoH) {
        this.documentoH = documentoH;
    }

    public String getDocumentoP() {
        return documentoP;
    }

    public void setDocumentoP(String documentoP) {
        this.documentoP = documentoP;
    }
}
