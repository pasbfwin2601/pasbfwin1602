package com.bit.creciendojuntos.models;

public class Usuario {

    String id;
    String nombre;
    String email;
    String documento;
    String domicilio;
    String telefono;
    String image;
    Hijo hijo;

    public Usuario() {
    }

    public Usuario(String id, Hijo hijo) {
        this.id = id;
        this.hijo = hijo;
    }

    public Usuario(String id, String nombre, String email, String documento, String domicilio, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.documento = documento;
        this.domicilio = domicilio;
        this.telefono = telefono;
    }

    public Usuario(String id, String nombre, String email, String documento, String domicilio, String telefono, String image) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.documento = documento;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.image = image;
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

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Hijo getHijo() {
        return hijo;
    }

    public void setHijo(Hijo hijo) {
        this.hijo = hijo;
    }
}
