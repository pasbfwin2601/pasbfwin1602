package com.bit.creciendojuntos.Logica;


import com.bit.creciendojuntos.models.Medico;

public class LMedico {
    private String key;
    private Medico medico;

    public LMedico(String key, Medico medico) {
        this.key = key;
        this.medico = medico;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }
}
