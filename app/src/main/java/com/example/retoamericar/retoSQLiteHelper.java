package com.example.retoamericar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.PreparedStatement;

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
        int tupu = 3;
        sqLiteDatabase.execSQL("INSERT INTO Comerciales (nombre,usuario,contrasena) VALUES ('Prueba','Prueba',tupu)");
        //TODO: Meter los datos por defecto de articulos y comerciales
        //Artículos
        String[] descripcion = {"'Ford Mustang Mach-E'","'Tesla Model Y'","'GMC Sierra AT4 2019'","'Fiskher' ","'Ford Mustang GT'",
        "'Cadillac Eldorado'","'Chevrolet Bolt EV'","'Chrysler Voyager'","'Apollo Intensa Emozione 2018'","'Dodge Charger SE'","'Ford F-150'","'Ford GT'"};
        int[] prVent = {33521,21360,25506,40119,37189,36651,18049,43366,35058,38113,33191,42153};
        int[] prCost = {67042,42721,51013,80238,74378,73302,36098,86733,70116,76226,66382,84307};
        int[] existencias = {27,30,74,47,71,32,54,37,44,86,38,92};
        int[] bajoMinimo = {18,21,51,32,49,22,37,25,30,60,26,64};
        int[] sobreMaximo = {35,39,96,61,92,41,70,48,57,111,49,119};
        String[] fechaUltimaEntrada = {"'09-06-1988'","'01-07-1988'","'22-03-1988'","'21-11-1988'","'28-09-1988'","'05-10-1988'",
        "'09-07-1988'","'09-07-1988'","'25-04-1988'","'13-06-1988'","'27-12-1988'","'03-12-1988'"};
        String[] fehcaUltimaSalida = {"'02-12-1988'","'15-04-1988'","'19-02-1988'","'25-04-1988'","'06-02-1988'","'13-11-1988'",
        "'19-02-1988'","'28-01-1988'","'14-06-1988'","'20-01-1988'","'22-11-1988'","'05-12-1988'"};

        for(int i=0; i<12;i++){
            String peru = descripcion[i];

            sqLiteDatabase.execSQL("INSERT INTO Articulos (descripcion,prcost,prvent,existencias,bajoMinimo,sobreMaximo,fecUltEnt,fecUltSal) VALUES (?,"+prVent[i]+",23434,23423,32434,234234,'78-98','98-988')");
            PreparedStatement pstm =
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }
}
