package com.bit.creciendojuntos.models;

public class Medico {

    String id;
    String nombre;
    String email;
    String documento;
    String nroMatricula;
    String telefono;
    String especialidad;

    public Medico() {
    }

    public Medico(String id, String nombre, String email, String documento, String nroMatricula, String telefono, String especialidad) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.documento = documento;
        this.nroMatricula = nroMatricula;
        this.telefono = telefono;
        this.especialidad = especialidad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNroMatricula() {
        return nroMatricula;
    }

    public void setNroMatricula(String nroMatricula) {
        this.nroMatricula = nroMatricula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}
