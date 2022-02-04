package com.example.retoamericar;

import android.app.Application;

public class GlobalData extends Application {
    private int idComercial;
    private int idEliminar;

    public int getIdEliminar() {
        return idEliminar;
    }

    public void setIdEliminar(int idEliminar) {
        this.idEliminar = idEliminar;
    }

    public int getIdComercial() {
        return idComercial;
    }

    public void setIdComercial(int idComercial) {
        this.idComercial = idComercial;
    }
}
