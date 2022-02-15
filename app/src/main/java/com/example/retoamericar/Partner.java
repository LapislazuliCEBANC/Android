package com.example.retoamericar;

public class Partner {

    private int idPartner;
    private int idComercial;
    private String nombre;
    private String direccion;
    private String poblacion;
    private String cif;
    private String telefono;
    private String email;


    public Partner() {

    }

    public Partner(String[] partner){
        this.idPartner = Integer.parseInt(partner[0]);
        this.idComercial = Integer.parseInt(partner[1]);
        this.nombre = partner[2];
        this.direccion = partner[3];
        this.poblacion = partner[4];
        this.cif = partner[5];
        this.telefono = partner[6];
        this.email = partner[7];
    }


    public int getIdPartner() {
        return idPartner;
    }

    public void setIdPartner(int idPartner) {
        this.idPartner = idPartner;
    }

    public int getIdComercial() {
        return idComercial;
    }

    public void setIdComercial(int idComercial) {
        this.idComercial = idComercial;
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

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
