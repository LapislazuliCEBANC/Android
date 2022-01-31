package com.example.retoamericar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class retoSQLiteHelper extends SQLiteOpenHelper {
    public retoSQLiteHelper(Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String crearComerciales = "CREATE TABLE Comerciales ("+
                " idComercial INTEGER,"+
                " usuario TEXT,"+
                " contrasena TEXT,"+
                " nombre TEXT,"+
                " apellido1 TEXT,"+
                " apellido2 TEXT,"+
                " dni TEXT,"+
                " telefono TEXT,"+
                " email TEXT,"+
                " CONSTRAINT pkComerciales"+
                " PRIMARY KEY (idComercial)"+
                ")";
        String crearPartners = "CREATE TABLE Partners(" +
                " idPartner INTEGER," +
                " idComercial INTEGER," +
                " nombre TEXT," +
                " direccion TEXT," +
                " poblacion TEXT," +
                " cif TEXT," +
                " telefono TEXT," +
                " email TEXT," +
                " CONSTRAINT pkPartners" +
                " PRIMARY KEY (idPartner)," +
                " CONSTRAINT fkPartnersComerciales" +
                " FOREIGN KEY (idComercial)" +
                " REFERENCES Comerciales(idComercial)" +
                ")";
        String crearAlbaranes = "CREATE TABLE Albaranes(" +
                " idAlbaran INTEGER," +
                " idPartner INTEGER," +
                " fechaAlbaran TEXT," +
                " fechaEnvio TEXT," +
                " fechaPago TEXT," +
                " CONSTRAINT pkAlbaranes" +
                " PRIMARY KEY (idAlbaran)," +
                " CONSTRAINT fkAlbaranesPartners" +
                " FOREIGN KEY (idPArtner)" +
                " REFERENCES Partners(idPartner)" +
                ")";
        String crearArticulos = "CREATE TABLE Articulos(" +
                " idArticulo INTEGER," +
                " descripcion TEXT," +
                " prCost INTEGER," +
                " prVent INTEGER," +
                " existencias INTEGER," +
                " bajoMinimo INTEGER," +
                " sobreMaximo INTEGER," +
                " fecUltEnt TEXT," +
                " fecUltSal TEXT," +
                " CONSTRAINT pkArticulos" +
                " PRIMARY KEY (idArticulo)" +
                ")";
        String crearLineas = "CREATE TABLE Lineas(" +
                " idLinea INTEGER," +
                " idAlbaran INTEGER," +
                " idArticulo INTEGER," +
                " cantidad INTEGER," +
                " descuento INTEGER," +
                " precio INTEGER," +
                " CONSTRAINT pkLineas" +
                " PRIMARY KEY (idLinea)," +
                " CONSTRAINT fkLineasAlbaran" +
                " FOREIGN KEY (idAlbaran)" +
                " REFERENCES Albaranes(idAlbaran) ON DELETE CASCADE," +
                " CONSTRAINT fkLineasArticulos" +
                " FOREIGN KEY (idArticulo)" +
                " REFERENCES Articulos(idArticulo) ON DELETE CASCADE" +
                ")";

        sqLiteDatabase.execSQL(crearComerciales);
        sqLiteDatabase.execSQL(crearPartners);
        sqLiteDatabase.execSQL(crearAlbaranes);
        sqLiteDatabase.execSQL(crearArticulos);
        sqLiteDatabase.execSQL(crearLineas);

        sqLiteDatabase.execSQL("INSERT INTO Comerciales (nombre,usuario,contrasena) VALUES ('Prueba','Prueba','prueba')");
        //TODO: Meter los datos por defecto de articulos y comerciales
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }
}
