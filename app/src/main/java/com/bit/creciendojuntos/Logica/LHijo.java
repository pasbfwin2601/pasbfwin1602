package com.bit.creciendojuntos.Logica;

import com.bit.creciendojuntos.models.Hijo;


public class LHijo {

    private String key;
    private Hijo hijo;

    public LHijo(String key, Hijo hijo) {
        this.key = key;
        this.hijo = hijo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Hijo getHijo() {
        return hijo;
    }

    public void setHijo(Hijo hijo) {
        this.hijo = hijo;
    }
}
