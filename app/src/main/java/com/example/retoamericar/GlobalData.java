package com.example.retoamericar;

import android.app.Application;

public class GlobalData extends Application {
    private int idComercial;

    public int getIdComercial() {
        return idComercial;
    }

    public void setIdComercial(int idComercial) {
        this.idComercial = idComercial;
    }
}
