package com.alumno.reto;

public class Partner {
    String appellido1;
    String apellido2;
    String nombre;
    String direccion;
    String poblacion;
    String provincia;
    String formpago;
    String telefono;

    public Partner() {

    }

    public Partner(String appellido1, String apellido2, String nombre, String direccion, String poblacion, String provincia, String formpago, String telefono) {
        this.appellido1 = appellido1;
        this.apellido2 = apellido2;
        this.nombre = nombre;
        this.direccion = direccion;
        this.poblacion = poblacion;
        this.provincia = provincia;
        this.formpago = formpago;
        this.telefono = telefono;
    }

    public String getAppellido1() {
        return appellido1;
    }

    public void setAppellido1(String appellido1) {
        this.appellido1 = appellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getFormpago() {
        return formpago;
    }

    public void setFormpago(String formpago) {
        this.formpago = formpago;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
