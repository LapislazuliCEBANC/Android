package com.example.retoamericar;

public class Articulo {

    private int id;
    private String desc;
    private int prCost;
    private int prVent;
    private int existencias;
    private int bajoMinimo;
    private int sobreMaximo;
    private String fecUltEnt;
    private String fecUltSal;

    public Articulo() {

    }

    public Articulo(String[] articulo){
        this.id = Integer.parseInt(articulo[0]);
        this.desc = articulo[1];
        this.prCost = Integer.parseInt(articulo[2]);
        this.prVent = Integer.parseInt(articulo[3]);
        this.existencias = Integer.parseInt(articulo[4]);
        this.bajoMinimo = Integer.parseInt(articulo[5]);
        this.sobreMaximo = Integer.parseInt(articulo[6]);
        this.fecUltEnt = articulo[7];
        this.fecUltSal = articulo[8];
    }

    public Articulo(int id, String desc, int prCost, int prVent, int existencias, int bajoMinimo, int sobreMaximo, String fecUltEnt, String fecUltSal) {
        this.id = id;
        this.desc = desc;
        this.prCost = prCost;
        this.prVent = prVent;
        this.existencias = existencias;
        this.bajoMinimo = bajoMinimo;
        this.sobreMaximo = sobreMaximo;
        this.fecUltEnt = fecUltEnt;
        this.fecUltSal = fecUltSal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPrCost() {
        return prCost;
    }

    public void setPrCost(int prCost) {
        this.prCost = prCost;
    }

    public int getPrVent() {
        return prVent;
    }

    public void setPrVent(int prVent) {
        this.prVent = prVent;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    public int getBajoMinimo() {
        return bajoMinimo;
    }

    public void setBajoMinimo(int bajoMinimo) {
        this.bajoMinimo = bajoMinimo;
    }

    public int getSobreMaximo() {
        return sobreMaximo;
    }

    public void setSobreMaximo(int sobreMaximo) {
        this.sobreMaximo = sobreMaximo;
    }

    public String getFecUltEnt() {
        return fecUltEnt;
    }

    public void setFecUltEnt(String fecUltEnt) {
        this.fecUltEnt = fecUltEnt;
    }

    public String getFecUltSal() {
        return fecUltSal;
    }

    public void setFecUltSal(String fecUltSal) {
        this.fecUltSal = fecUltSal;
    }

}
